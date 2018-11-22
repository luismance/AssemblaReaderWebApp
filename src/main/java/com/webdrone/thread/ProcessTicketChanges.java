package com.webdrone.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.webdrone.assembla.dto.TicketChangesDto;
import com.webdrone.assembla.dto.TicketChangesListDto;
import com.webdrone.model.Notification;
import com.webdrone.model.Space;
import com.webdrone.model.Ticket;
import com.webdrone.model.User;
import com.webdrone.model.WorkflowTransition;
import com.webdrone.model.WorkflowTransitionInstance;
import com.webdrone.service.NotificationService;
import com.webdrone.service.TicketService;
import com.webdrone.service.UserService;
import com.webdrone.service.WorkflowTransitionInstanceService;
import com.webdrone.service.WorkflowTransitionService;
import com.webdrone.util.ExpressionLanguageResultEnum;
import com.webdrone.util.ExpressionLanguageUtils;
import com.webdrone.util.RESTServiceUtil;

@Stateless
public class ProcessTicketChanges {

	public ProcessTicketChanges() {
	}

	public static void processTicketChanges(UserTransaction utx, EntityManager entityManager, String username) {

		try {
			System.out.println("Starting Ticket Changes Processing");

			UserService userService = new UserService();
			TicketService ticketService = new TicketService();
			WorkflowTransitionService workflowTransitionService = new WorkflowTransitionService();
			WorkflowTransitionInstanceService workflowTransitionInstanceService = new WorkflowTransitionInstanceService();
			NotificationService notificationService = new NotificationService();

			User currentUser = userService.findUserByUsername(entityManager, username);

			// RETRIEVE TICKET COMMENTS

			List<Space> spaceList = new ArrayList<Space>(currentUser.getSpaces());
			for (Space space : spaceList) {

				if (space.isCanProcessTicketChanges()) {
					List<Ticket> ticketList = ticketService.getTicketsBySpace(entityManager, space.getExternalRefId(), -1, 0, "", "", "");
					for (Ticket ticket : ticketList) {

						currentUser.setSyncStatus("Processing ticket #" + ticket.getTicketNumber() + " from " + space.getWikiname());
						userService.threadUpdate(utx, entityManager, currentUser);

						TicketChangesListDto ticketChangesList = new TicketChangesListDto();

						String ticketChangesXml = sendRequest(userService, entityManager, utx, currentUser,
								"https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets/" + ticket.getTicketNumber() + "/ticket_comments.xml?per_page=100", true,
								"Bearer " + currentUser.getBearerToken());

						ticketChangesList = (TicketChangesListDto) RESTServiceUtil.unmarshaller(TicketChangesListDto.class, ticketChangesXml);

						if (ticketChangesList != null) {

							// Reverse list because assembla returns the changes in reverse
							List<TicketChangesDto> ticketChangesReversed = new ArrayList<TicketChangesDto>();
							List<TicketChangesDto> newTicketChangesList = ticketChangesList.getTicketChanges();

							for (int i = newTicketChangesList.size() - 1; i >= 0; i--) {
								ticketChangesReversed.add(newTicketChangesList.get(i));
							}
							List<WorkflowTransition> workflowTransitions = ticket.getWorkflow() != null
									? workflowTransitionService.getStartingWorkflowTransitions(entityManager, ticket.getWorkflow(), ticket.getPriorityTypeId())
									: new ArrayList<WorkflowTransition>();

							Map<String, String> fieldMap = new HashMap<String, String>();
							fieldMap.put("ticket_created", ticket.getRemotelyCreated().getTime() / 6000 + "");
							fieldMap.put("ticket_priority", ticket.getPriorityTypeId() + "");

							int currentWorkflowIndex = 0;

							long lastDateUpdate = ticket.getDateCreated().getTime();
							User lastUpdater = ticket.getReporter();
							String violationType = "";
							System.out.println("Processing " + ticket.getTicketNumber());
							for (TicketChangesDto ticketChanges : ticketChangesReversed) {
								if (ticketChanges.getTicketChanges().contains("- - ")) {
									User causedBy = lastUpdater;
									String[] fields = ticketChanges.getTicketChanges().replace("---\n", "").split("- - ");

									for (int i = 1; i < fields.length; i++) {
										String[] fieldArray = fields[i].split("  - ");
										String fieldName = fieldArray[0].replace("- - ", "").replace("\n", "");
										String previousValue = fieldArray[1].replace("\n", "");
										String newValue = fieldArray[2].replace("\n", "").isEmpty() ? "''" : fieldArray[2].replace("\n", "");

										StringBuilder originState = new StringBuilder();
										StringBuilder targetState = new StringBuilder();

										originState.append(fieldName).append(":").append(previousValue).append(System.getProperty("line.separator"));
										targetState.append(fieldName).append(":").append(newValue).append(System.getProperty("line.separator"));

										List<WorkflowTransitionInstance> wtiList = workflowTransitionInstanceService.getWorkflowTransitionInstanceByExternalRefAndTransition(entityManager,
												ticketChanges.getId(), originState.toString(), targetState.toString());

										WorkflowTransitionInstance wti = null;

										if (wtiList.size() > 0) {
											for (WorkflowTransitionInstance wtInstance : wtiList) {
												if (wtInstance.getOriginState().equals(originState.toString()) && wtInstance.getTargetState().equals(targetState.toString())) {
													wti = wtInstance;
													break;
												}
											}
										}

										if (wti == null) {
											wti = new WorkflowTransitionInstance();
										}

										wti.setExternalRefId(ticketChanges.getId());
										wti.setMessage(ticketChanges.getComment());
										wti.setSpace(space);
										wti.setTicket(ticket);
										wti.setRemotelyCreated(ticketChanges.getCreatedOn() != null ? ticketChanges.getCreatedOn().toDate() : null);
										wti.setRemotelyUpdated(ticketChanges.getUpdatedAt() != null ? ticketChanges.getUpdatedAt().toDate() : null);

										String notifMessage = "";

										if (workflowTransitions.size() > 0) {

											currentWorkflowIndex = 0;

											ExpressionLanguageResultEnum evalResult = null;
											int correctIndex = -1;
											int wrongCounter = 0;
											for (int wtIndex = 0; wtIndex < workflowTransitions.size(); wtIndex++) {

												String expressionLanguage = workflowTransitions.get(wtIndex).getExpressionLanguage();
												wti.setWorkflowTransition(workflowTransitions.get(wtIndex));

												if (expressionLanguage.contains(fieldName)) {

													System.out.println("[" + fieldName + ":" + previousValue.toLowerCase() + "=>" + newValue.toLowerCase() + "] Current WT : "
															+ workflowTransitions.get(wtIndex).getExpressionLanguage());

													/* DATE VALUE PROCESSING START */
													if (previousValue.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\dZ")) {
														SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
														previousValue = TimeUnit.MILLISECONDS.toMinutes(sdf.parse(previousValue).getTime()) + "";
													}

													if (newValue.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\dZ")) {
														SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
														newValue = TimeUnit.MILLISECONDS.toMinutes(sdf.parse(newValue).getTime()) + "";
													}

													if (fieldMap.containsKey("new_updated_at")) {
														fieldMap.replace("new_updated_at", (ticketChanges.getUpdatedAt().toDate().getTime() / 6000) + "");
													} else {
														fieldMap.put("new_updated_at", (ticketChanges.getUpdatedAt().toDate().getTime() / 6000) + "");
													}

													/* DATE VALUE PROCESSING START */

													if (!fieldMap.containsKey("old_" + fieldName) && !fieldMap.containsKey("new_" + fieldName)) {
														fieldMap.put("old_" + fieldName, previousValue.isEmpty() ? "" : previousValue.toLowerCase());
														fieldMap.put("new_" + fieldName, newValue.toLowerCase());
													} else {
														fieldMap.replace("old_" + fieldName, previousValue.isEmpty() ? "" : previousValue.toLowerCase());
														fieldMap.replace("new_" + fieldName, newValue.toLowerCase());
													}

													evalResult = ExpressionLanguageUtils.evaluate(fieldMap, workflowTransitions.get(wtIndex).getExpressionLanguage());

													if (evalResult == ExpressionLanguageResultEnum.COMPLETE_TRUE) {
														correctIndex = wtIndex;
														break;
													} else {
														wrongCounter++;
													}

													System.out.println("EVAL RESULT : " + evalResult);

												}
												currentWorkflowIndex = wtIndex;
											}

											if (correctIndex > -1 && wrongCounter < workflowTransitions.size()) {
												System.out.println("[" + ticket.getTicketNumber() + "," + ticketChanges.getId() + "]EVAL RESULT : " + evalResult);
												wti.setHasViolation(false);
												wti.setWorkflowTransition(workflowTransitions.get(correctIndex));
												fieldMap = new HashMap<String, String>();
												fieldMap.put("ticket_created", ticket.getRemotelyCreated().getTime() / 60000 + "");
												fieldMap.put("ticket_priority", ticket.getPriorityTypeId() + "");
												fieldMap.put("last_updated", (ticketChanges.getUpdatedAt().toDate().getTime() / 60000) + "");

												long ticketDelay = ((ticketChanges.getUpdatedAt().toDate().getTime() - lastDateUpdate) / 60000) - workflowTransitions.get(correctIndex).getMaxDelay();
												long monthsDelay = ticketDelay > 43200 ? (ticketDelay / 43200) : 0;
												long daysDelay = monthsDelay > 0 ? ((ticketDelay - (monthsDelay * 43200)) / 1440) : (ticketDelay > 1440 ? (ticketDelay / 1440) : 0);
												long hoursDelay = (ticketDelay - ((monthsDelay * 43200) + (daysDelay * 1440))) > 0 ? ((ticketDelay - ((monthsDelay * 43200) + (daysDelay * 1440))) / 60)
														: 0;
												long minsDelay = ticketDelay
														- ((monthsDelay > 0 ? monthsDelay * 43200 : 0) + (daysDelay > 0 ? daysDelay * 1440 : 0) + (hoursDelay > 0 ? hoursDelay * 60 : 0));
												if (ticketDelay > workflowTransitions.get(correctIndex).getMaxDelay()) {
													wti.setHasViolation(true);
													notifMessage = "Delayed by " + (monthsDelay > 0 ? (monthsDelay + " month(s), ") : "") + (daysDelay > 0 ? (daysDelay + " day(s), ") : "")
															+ (hoursDelay > 0 ? (hoursDelay + " hour(s), ") : "") + minsDelay + " minute(s).";
													System.out.println("Notif Message : " + notifMessage);
													violationType = "SLA";
													causedBy = lastUpdater;
												}

												workflowTransitions = workflowTransitions.get(correctIndex).getWorkflowTransitions();
												lastDateUpdate = ticketChanges.getUpdatedAt().toDate().getTime();
											}

											if (wrongCounter == workflowTransitions.size() && workflowTransitions.size() > 0) {
												System.out.println("[" + ticket.getTicketNumber() + "," + ticketChanges.getId() + "]EVAL RESULT : " + evalResult);
												wti.setHasViolation(true);
												wti.setWorkflowTransition(workflowTransitions.get(0));

												fieldMap = new HashMap<String, String>();
												fieldMap.put("ticket_created", ticket.getRemotelyCreated().getTime() / 60000 + "");
												fieldMap.put("ticket_priority", ticket.getPriorityTypeId() + "");
												fieldMap.put("last_updated", (ticketChanges.getUpdatedAt().toDate().getTime() / 60000) + "");

												workflowTransitions = workflowTransitions.get(0).getWorkflowTransitions();
												lastDateUpdate = ticketChanges.getUpdatedAt().toDate().getTime();
												causedBy = (User) userService.findByExternalRefId(entityManager, User.class, ticketChanges.getUserId());
											}

										} else {
											System.out.println("No workflow transitions");
											wti.setHasViolation(false);
										}

										wti.setOriginState(originState.toString());
										wti.setTargetState(targetState.toString());

										workflowTransitionInstanceService.threadCreate(utx, entityManager, wti);

										if (wti.isHasViolation()) {
											Notification notification = new Notification();
											notification.setSpace(space);
											notification.setTicket(ticket);

											if (!notifMessage.isEmpty()) {
												notification.setMessage(notifMessage);
											} else {
												notification.setMessage(wti.getWorkflowTransition().getErrorMessage());
											}

											notification.setWorkflowTransitionInstance(wti);
											notification.setWorkflowTransitionViolated(wti.getWorkflowTransition());
											if (violationType.isEmpty()) {
												notification.setViolationType(wti.getWorkflowTransition().getViolationType());
											} else {
												notification.setViolationType(violationType);
											}

											notification.setCausedBy(causedBy);

											notificationService.threadCreate(utx, entityManager, notification);
										}

										lastUpdater = (User) userService.findByExternalRefId(entityManager, User.class, ticketChanges.getUserId());
									}

								} else {

									if (!ticketChanges.getComment().isEmpty()) {
										WorkflowTransitionInstance wti = (WorkflowTransitionInstance) workflowTransitionInstanceService.findByExternalRefId(entityManager,
												WorkflowTransitionInstance.class, ticketChanges.getId());

										if (wti == null) {
											wti = new WorkflowTransitionInstance();
										}

										wti.setExternalRefId(ticketChanges.getId());
										wti.setMessage(ticketChanges.getComment());
										wti.setSpace(space);
										wti.setTicket(ticket);
										wti.setRemotelyCreated(ticketChanges.getCreatedOn() != null ? ticketChanges.getCreatedOn().toDate() : null);
										wti.setRemotelyUpdated(ticketChanges.getUpdatedAt() != null ? ticketChanges.getUpdatedAt().toDate() : null);
										if (workflowTransitions.size() > 0 && currentWorkflowIndex < workflowTransitions.size()) {
											wti.setWorkflowTransition(workflowTransitions.get(currentWorkflowIndex));
										}
										wti.setOriginState("comment : ''");
										wti.setTargetState("comment : " + ticketChanges.getComment());

										if (wti.getId() != null) {
											workflowTransitionInstanceService.threadUpdate(utx, entityManager, wti);

											if (wti.isHasViolation()) {
												Notification notification = new Notification();
												notification.setSpace(space);
												notification.setTicket(ticket);
												notification.setWorkflowTransitionInstance(wti);
												notification.setWorkflowTransitionViolated(wti.getWorkflowTransition());
												notification.setViolationType(wti.getWorkflowTransition().getViolationType());
												notificationService.threadCreate(utx, entityManager, notification);
											}
										} else {
											workflowTransitionInstanceService.threadCreate(utx, entityManager, wti);
										}

									}
								}

							}

						}

					}
				}
			}

			currentUser.setSyncStatus("Ready to start");
			userService.threadUpdate(utx, entityManager, currentUser);
		} catch (Exception e) {
			UserService userService = new UserService();
			User currentUser = userService.findUserByUsername(entityManager, username);
			currentUser.setSyncStatus("An error occured while trying to sync");
			try {
				userService.threadUpdate(utx, entityManager, currentUser);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	private static String sendRequest(UserService userService, EntityManager entityManager, UserTransaction utx, User currentUser, String url, boolean requireAuthorization, String authorization)
			throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		String sendReq = RESTServiceUtil.sendGET(url, requireAuthorization, authorization);

		if (sendReq.equals("401")) {

			RESTServiceUtil.refreshBearerToken(currentUser);

			sendReq = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces.xml", true, "Bearer " + currentUser.getBearerToken());
			userService.threadUpdate(utx, entityManager, currentUser);
		}

		return sendReq;
	}
}
