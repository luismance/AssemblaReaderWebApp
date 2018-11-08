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

import com.webdrone.assembla.dto.MilestoneAssemblaDto;
import com.webdrone.assembla.dto.MilestoneListAssemblaDto;
import com.webdrone.assembla.dto.SpaceAssemblaDto;
import com.webdrone.assembla.dto.SpaceListAssemblaDto;
import com.webdrone.assembla.dto.TicketAssemblaDto;
import com.webdrone.assembla.dto.TicketChangesDto;
import com.webdrone.assembla.dto.TicketChangesListDto;
import com.webdrone.assembla.dto.TicketListAssemblaDto;
import com.webdrone.model.Milestone;
import com.webdrone.model.Notification;
import com.webdrone.model.Space;
import com.webdrone.model.Ticket;
import com.webdrone.model.User;
import com.webdrone.model.Workflow;
import com.webdrone.model.WorkflowTransition;
import com.webdrone.model.WorkflowTransitionInstance;
import com.webdrone.service.MilestoneService;
import com.webdrone.service.NotificationService;
import com.webdrone.service.SpaceService;
import com.webdrone.service.TicketService;
import com.webdrone.service.UserService;
import com.webdrone.service.WorkflowService;
import com.webdrone.service.WorkflowTransitionInstanceService;
import com.webdrone.service.WorkflowTransitionService;
import com.webdrone.util.ExpressionLanguageResultEnum;
import com.webdrone.util.ExpressionLanguageUtils;
import com.webdrone.util.RESTServiceUtil;

@Stateless
public class SyncUserDataThread implements Runnable {

	private UserService userService;

	private String username;

	private EntityManager entityManager;

	private UserTransaction utx;

	public SyncUserDataThread() {

	}

	public SyncUserDataThread(UserTransaction utx, EntityManager em, String username) {
		this.username = username;
		this.entityManager = em;
		this.utx = utx;
	}

	public void run() {

		try {
			System.out.println("Starting Background Sync");

			userService = new UserService();
			SpaceService spaceService = new SpaceService();
			TicketService ticketService = new TicketService();
			MilestoneService milestoneService = new MilestoneService();
			WorkflowService workflowService = new WorkflowService();
			WorkflowTransitionService workflowTransitionService = new WorkflowTransitionService();
			WorkflowTransitionInstanceService workflowTransitionInstanceService = new WorkflowTransitionInstanceService();
			NotificationService notificationService = new NotificationService();

			User currentUser = userService.findUserByUsername(entityManager, username);

			if (currentUser != null) {

				System.out.println("Starting SPACE Sync");
				String spacesXml = sendRequest(currentUser, "https://api.assembla.com/v1/spaces.xml", true, "Bearer " + currentUser.getBearerToken());

				SpaceListAssemblaDto spaceListAssemblaDto = (SpaceListAssemblaDto) RESTServiceUtil.unmarshaller(SpaceListAssemblaDto.class, spacesXml);

				currentUser.setSyncStatus("Retrieving Spaces");
				userService.threadUpdate(utx, entityManager, currentUser);

				for (SpaceAssemblaDto spaceAssemblaDto : spaceListAssemblaDto.getSpaceDtos()) {

					Object resultSpace = spaceService.findByExternalRefId(entityManager, Space.class, spaceAssemblaDto.getId());
					Space currentSpace = resultSpace != null ? ((Space) resultSpace) : null;
					Object resultParentSpace = spaceService.findByExternalRefId(entityManager, Space.class, spaceAssemblaDto.getParentId());
					Space parentSpace = resultParentSpace != null ? ((Space) resultParentSpace) : null;
					if (currentSpace == null) {
						Space newSpace = new Space(spaceAssemblaDto);
						if (parentSpace != null) {
							newSpace.setParentSpace(parentSpace);
						}
						newSpace.addUser(currentUser);
						currentUser.addSpace(newSpace);

						spaceService.threadCreate(utx, entityManager, newSpace);

						System.out.println("Space Created : " + spaceAssemblaDto.getId());
					} else {
						currentSpace.setApproved(spaceAssemblaDto.isApproved());
						currentSpace.setBannerHeight(spaceAssemblaDto.getBannerHeight() != null ? spaceAssemblaDto.getBannerHeight().longValue() : 0);
						currentSpace.setBannerLink(spaceAssemblaDto.getBannerLink());
						currentSpace.setBannerPath(spaceAssemblaDto.getBanner());
						currentSpace.setBannerText(spaceAssemblaDto.getBannerText());
						currentSpace.setCanApply(spaceAssemblaDto.isCanApply());
						currentSpace.setCanJoin(spaceAssemblaDto.isCanJoin());
						currentSpace.setCommercial(spaceAssemblaDto.isCommercial());
						currentSpace.setCommercialFrom(spaceAssemblaDto.getCommercialFrom() != null ? spaceAssemblaDto.getCommercialFrom().toDate() : null);
						currentSpace.setDefaultShowPage(spaceAssemblaDto.getDefaultShowpage());
						currentSpace.setDescription(spaceAssemblaDto.getDescription());
						currentSpace.setExternalRefId(spaceAssemblaDto.getId());
						currentSpace.setLastPayerChangedAt(spaceAssemblaDto.getLastPayerChangedAt() != null ? spaceAssemblaDto.getLastPayerChangedAt().toDate() : null);
						currentSpace.setManager(spaceAssemblaDto.isManager());
						if (parentSpace != null) {
							currentSpace.setParentSpace(parentSpace);
						}
						currentSpace.setPublicPermissions(spaceAssemblaDto.getPublicPermissions());
						currentSpace.setRemotelyCreated(spaceAssemblaDto.getCreatedAt() != null ? spaceAssemblaDto.getCreatedAt().toDate() : null);
						currentSpace.setRemotelyUpdated(spaceAssemblaDto.getUpdatedAt() != null ? spaceAssemblaDto.getUpdatedAt().toDate() : null);
						currentSpace.setRestricted(spaceAssemblaDto.isRestricted());
						currentSpace.setRestrictedDate(spaceAssemblaDto.getRestrictedDate() != null ? spaceAssemblaDto.getRestrictedDate().toDate() : null);
						currentSpace.setStatus(spaceAssemblaDto.getStatus());
						currentSpace.setStyle(spaceAssemblaDto.getStyle());
						currentSpace.setTabsOrder(spaceAssemblaDto.getTabsOrder());
						currentSpace.setTeamPermissions(spaceAssemblaDto.getTeamPermissions());
						currentSpace.setTeamTabRole(spaceAssemblaDto.getTeamTabRole());
						currentSpace.setVolunteer(spaceAssemblaDto.isVolunteer());
						currentSpace.setWatcherPermissions(spaceAssemblaDto.getWatcherPermissions());
						currentSpace.setWikiname(spaceAssemblaDto.getWikiName());

						currentSpace.addUser(currentUser);
						currentUser.addSpace(currentSpace);
						spaceService.threadUpdate(utx, entityManager, currentSpace);
						System.out.println("Space Updated : " + spaceAssemblaDto.getId());
					}

				}

				currentUser.setSyncStatus("Space sync done");

				userService.threadUpdate(utx, entityManager, currentUser);

				System.out.println("DONE SPACE Sync");

				System.out.println("START MILESTONE Sync");

				// refresh currentUser
				// calling the threadUpdate again causes the user to space link to create again
				currentUser = userService.findUserByUsername(entityManager, username);

				currentUser.setSyncStatus("Start milestones sync");
				userService.threadUpdate(utx, entityManager, currentUser);
				for (Space space : currentUser.getSpaces()) {
					System.out.println("Starting MILESTONE Sync for SPACE " + space.getExternalRefId());

					currentUser.setSyncStatus("Syncing milestones from " + space.getWikiname());
					userService.threadUpdate(utx, entityManager, currentUser);

					int milestonesPerPage = 100;
					int milestonePage = 1;
					Object milestoneObj = null;
					String milestonesXml = "";

					do {
						milestonesXml = sendRequest(currentUser, "https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/milestones.xml?per_page=" + milestonesPerPage + "&page=" + milestonePage, true,
								"Bearer " + currentUser.getBearerToken());

						milestoneObj = RESTServiceUtil.unmarshaller(MilestoneListAssemblaDto.class, milestonesXml);
						if (milestoneObj != null) {
							MilestoneListAssemblaDto milestoneDtoList = (MilestoneListAssemblaDto) milestoneObj;

							if (milestoneDtoList != null && milestoneDtoList.getMilestones().size() > 0) {
								for (MilestoneAssemblaDto mad : milestoneDtoList.getMilestones()) {
									Object obj = milestoneService.findByExternalRefId(entityManager, Milestone.class, mad.getId());
									Milestone milestone = (obj != null ? (Milestone) obj : null);

									if (milestone == null) {
										milestone = new Milestone();
									}

									milestone.setExternalRefId(mad.getId());
									milestone.setPlannerType(mad.getPlannerType());
									milestone.setDescription(mad.getDescription());
									milestone.setReleaseNotes(mad.getReleaseNotes());
									milestone.setPrettyReleaseLevel(mad.getPrettyReleaseLevel());
									milestone.setCompletedDate(mad.getCompletedDate() != null ? mad.getCompletedDate().toDate() : null);
									milestone.setDueDate(mad.getDueDate() != null ? mad.getDueDate().toDate() : null);
									milestone.setCompleted(mad.isCompleted());
									milestone.setTitle(mad.getTitle());

									Object userCreatedObj = userService.findByExternalRefId(entityManager, User.class, mad.getCreatedBy());
									User mUserCreatedBy = userCreatedObj != null ? (User) userCreatedObj : RESTServiceUtil.convertUserXml(mad.getCreatedBy(), currentUser.getBearerToken());

									if (userCreatedObj == null) {
										userService.threadCreate(utx, entityManager, mUserCreatedBy);
										System.out.println("Milestone Created By User Created : " + mUserCreatedBy.getUsername());
									}

									Object userUpdatedObj = userService.findByExternalRefId(entityManager, User.class, mad.getUpdatedBy());
									User mUserUpdatedBy = userUpdatedObj != null ? (User) userUpdatedObj : RESTServiceUtil.convertUserXml(mad.getUpdatedBy(), currentUser.getBearerToken());

									if (userUpdatedObj == null) {
										userService.threadCreate(utx, entityManager, mUserUpdatedBy);
										System.out.println("Milestone Updated By User Created : " + mUserUpdatedBy.getUsername());
									}

									milestone.setCreatedBy(mUserCreatedBy);
									milestone.setUpdatedBy(mUserUpdatedBy);

									milestone.setSpace(space);

									if (milestone.getId() == null) {
										milestoneService.threadCreate(utx, entityManager, milestone);
									} else {
										milestoneService.threadUpdate(utx, entityManager, milestone);
									}

								}
							}
							milestonePage++;
						}

					} while (milestoneObj != null);
					System.out.println("MILESTONE Sync for SPACE " + space.getExternalRefId() + " DONE");
				}

				currentUser.setSyncStatus("Milestone sync done");
				userService.threadUpdate(utx, entityManager, currentUser);
				System.out.println("END MILESTONE Sync");

				System.out.println("START TICKET Sync");
				currentUser.setSyncStatus("Start tickets sync");
				userService.threadUpdate(utx, entityManager, currentUser);
				int ticketsPerPage = 100;

				for (Space space : currentUser.getSpaces()) {
					System.out.println("Current Space : " + space.getExternalRefId());
					currentUser.setSyncStatus("Syncing tickets from " + space.getWikiname());
					userService.threadUpdate(utx, entityManager, currentUser);
					if (space != null) {
						TicketListAssemblaDto ticketListAssemblaDto = new TicketListAssemblaDto();
						int page = 1;
						int ticketListSize = 0;
						do {
							String ticketsXml = sendRequest(currentUser, "https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets.xml?per_page=" + ticketsPerPage + "&page=" + page, true, "Bearer " + currentUser.getBearerToken());

							Object ticketListObj = RESTServiceUtil.unmarshaller(TicketListAssemblaDto.class, ticketsXml);

							if (ticketListObj != null) {
								ticketListAssemblaDto = (TicketListAssemblaDto) ticketListObj;
								ticketListSize = ticketListAssemblaDto.getTickets().size();
								for (TicketAssemblaDto ticketAssemblaDto : ticketListAssemblaDto.getTickets()) {
									Object ticketObj = ticketService.findByExternalRefId(entityManager, Ticket.class, ticketAssemblaDto.getId());
									Ticket currentTicket = ticketObj != null ? (Ticket) ticketObj : null;

									Object reporterObj = userService.findByExternalRefId(entityManager, User.class, ticketAssemblaDto.getReporterId());
									User reporter = reporterObj != null ? (User) reporterObj : RESTServiceUtil.convertUserXml(ticketAssemblaDto.getReporterId(), currentUser.getBearerToken());

									if (reporterObj == null) {
										userService.threadCreate(utx, entityManager, reporter);
									} else {
										userService.threadUpdate(utx, entityManager, reporter);
									}

									Object assignedObj = userService.findByExternalRefId(entityManager, User.class, ticketAssemblaDto.getAssignedToId());
									User assignedTo = null;

									if (!ticketAssemblaDto.getAssignedToId().isEmpty()) {
										assignedTo = assignedObj != null ? (User) assignedObj : RESTServiceUtil.convertUserXml(ticketAssemblaDto.getAssignedToId(), currentUser.getBearerToken());

										if (assignedObj == null) {
											userService.threadCreate(utx, entityManager, assignedTo);
										} else {
											userService.threadUpdate(utx, entityManager, assignedTo);
										}
									}

									Milestone milestone = null;

									if (!ticketAssemblaDto.getMilestoneId().isEmpty()) {
										Object milestonObj = milestoneService.findByExternalRefId(entityManager, Milestone.class, ticketAssemblaDto.getMilestoneId());
										milestone = milestonObj != null ? (Milestone) milestonObj : null;
									}

									Workflow workflow = workflowService.getWorkflowByName(entityManager, ticketAssemblaDto.getCustomFields().getType());

									if (workflow != null) {
										space.setCanProcessTicketChanges(true);
									} else {
										space.setCanProcessTicketChanges(false);
									}
									spaceService.threadUpdate(utx, entityManager, space);
									/*
									 * START : TICKET SYNC
									 */
									if (currentTicket != null) {
										if (!ticketAssemblaDto.getAssignedToId().isEmpty()) {
											currentTicket.setAssignedTo(assignedTo);
										}
										currentTicket.setCompletedDate(ticketAssemblaDto.getCompletedDate() != null ? ticketAssemblaDto.getCompletedDate().toDate() : null);
										currentTicket.setDescription(new String(ticketAssemblaDto.getDescription()));
										currentTicket.setEstimate(ticketAssemblaDto.getEstimate());
										currentTicket.setExternalRefId(ticketAssemblaDto.getId());
										currentTicket.setImportance(ticketAssemblaDto.getImportance());
										currentTicket.setMilestone(milestone);
										currentTicket.setPriorityTypeId(ticketAssemblaDto.getPriority());
										currentTicket.setRemotelyCreated(ticketAssemblaDto.getCreatedOn() != null ? ticketAssemblaDto.getCreatedOn().toDate() : null);
										currentTicket.setRemotelyUpdated(ticketAssemblaDto.getUpdatedAt() != null ? ticketAssemblaDto.getUpdatedAt().toDate() : null);
										currentTicket.setReporter(reporter);
										currentTicket.setSpace(space);
										currentTicket.setStatus(ticketAssemblaDto.getStatus());
										currentTicket.setStory(ticketAssemblaDto.isStory());
										currentTicket.setStoryImportance(ticketAssemblaDto.getStoryImportance());
										currentTicket.setSummary(ticketAssemblaDto.getSummary());
										currentTicket.setTicketNumber(ticketAssemblaDto.getNumber());
										currentTicket.setTotalEstimate(ticketAssemblaDto.getTotalEstimate());
										currentTicket.setTotalInvestedHours(ticketAssemblaDto.getTotalInvestedHours());
										currentTicket.setTotalWorkingHours(ticketAssemblaDto.getTotalWorkingHours());
										currentTicket.setWorkflow(workflow);
										currentTicket.setWorkingHours(ticketAssemblaDto.getWorkingHours());

										ticketService.threadUpdate(utx, entityManager, currentTicket);
									} else {
										currentTicket = new Ticket(ticketAssemblaDto, space, milestone, reporter, assignedTo, workflow);
										ticketService.threadCreate(utx, entityManager, currentTicket);
									}

									/*
									 * END : TICKET SYNC
									 */
								}
								System.out.println("Page : " + page + ", Ticket List Size : " + ticketListSize);
								page++;
							} else {
								ticketListSize = 0;
							}
						} while (ticketListSize > 0);
					}
				}

				System.out.println("END TICKET Sync");

			} else {
				System.out.println("User is null");
			}

			// RETRIEVE TICKET COMMENTS

			for (Space space : currentUser.getSpaces()) {

				if (space.isCanProcessTicketChanges()) {
					List<Ticket> ticketList = ticketService.getTicketsBySpace(entityManager, space.getExternalRefId(), -1, 0);
					for (Ticket ticket : ticketList) {

						currentUser.setSyncStatus("Processing ticket #" + ticket.getTicketNumber() + " from " + space.getWikiname());
						userService.threadUpdate(utx, entityManager, currentUser);

						TicketChangesListDto ticketChangesList = new TicketChangesListDto();

						String ticketChangesXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets/" + ticket.getTicketNumber() + "/ticket_comments.xml?per_page=100", true,
								"Bearer " + currentUser.getBearerToken());

						ticketChangesList = (TicketChangesListDto) RESTServiceUtil.unmarshaller(TicketChangesListDto.class, ticketChangesXml);

						if (ticketChangesList != null) {

							// Temporary Workflow Transition
							WorkflowTransition wt = workflowTransitionService.listAll(entityManager, WorkflowTransition.class).get(0);

							Map<String, String> fieldMap = new HashMap<String, String>();
							fieldMap.put("ticket_created", ticket.getRemotelyCreated().getTime() + "");
							fieldMap.put("ticket_priority", ticket.getPriorityTypeId() + "");

							// Reverse list because assembla returns the changes in reverse
							List<TicketChangesDto> ticketChangesReversed = new ArrayList<TicketChangesDto>();
							List<TicketChangesDto> newTicketChangesList = ticketChangesList.getTicketChanges();

							for (int i = newTicketChangesList.size() - 1; i >= 0; i--) {
								ticketChangesReversed.add(newTicketChangesList.get(i));
							}
							List<WorkflowTransition> workflowTransitions = ticket.getWorkflow() != null ? ticket.getWorkflow().getWorkflowTransitions() : new ArrayList<WorkflowTransition>();
							int currentWorkflow = 0;
							for (TicketChangesDto ticketChanges : ticketChangesReversed) {
								if (ticketChanges.getTicketChanges().contains("- - ")) {
									String[] fields = ticketChanges.getTicketChanges().replace("---\n", "").split("- - ");

									StringBuilder originState = new StringBuilder();
									StringBuilder targetState = new StringBuilder();

									for (int i = 1; i < fields.length; i++) {
										String[] fieldArray = fields[i].split("  - ");
										String fieldName = fieldArray[0].replace("- - ", "").replace("\n", "");
										String previousValue = fieldArray[1].replace("\n", "");
										String newValue = fieldArray[2].replace("\n", "");

										originState.append(fieldName).append(":").append(previousValue).append(System.getProperty("line.separator"));
										targetState.append(fieldName).append(":").append(newValue).append(System.getProperty("line.separator"));

										WorkflowTransitionInstance wti = (WorkflowTransitionInstance) workflowTransitionInstanceService.findByExternalRefId(entityManager, WorkflowTransitionInstance.class, ticketChanges.getId());

										if (wti == null) {
											wti = new WorkflowTransitionInstance();
										}

										wti.setExternalRefId(ticketChanges.getId());
										wti.setMessage(ticketChanges.getComment());
										wti.setSpace(space);
										wti.setTicket(ticket);
										wti.setRemotelyCreated(ticketChanges.getCreatedOn() != null ? ticketChanges.getCreatedOn().toDate() : null);
										wti.setRemotelyUpdated(ticketChanges.getUpdatedAt() != null ? ticketChanges.getUpdatedAt().toDate() : null);
										if (workflowTransitions.size() > 0) {
											if (currentWorkflow < workflowTransitions.size() && workflowTransitions.size() > 0) {

												wti.setWorkflowTransition(workflowTransitions.get(currentWorkflow));

												/* DATE VALUE PROCESSING START */
												if (previousValue.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\dZ")) {
													SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
													previousValue = TimeUnit.MILLISECONDS.toMinutes(sdf.parse(previousValue).getTime()) + "";
												}

												if (newValue.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\dZ")) {
													SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
													newValue = TimeUnit.MILLISECONDS.toMinutes(sdf.parse(newValue).getTime()) + "";
												}

												fieldMap.put("new_update_at", ticketChanges.getUpdatedAt().toDate().getTime() + "");
												/* DATE VALUE PROCESSING START */

												if (fieldMap.get("old_" + fieldName) != null && fieldMap.get("new_" + fieldName) != null) {
													fieldMap = new HashMap<String, String>();
													currentWorkflow++;
													if (currentWorkflow > workflowTransitions.size()) {
														wti.setHasViolation(true);
													}
													fieldMap.put("old_" + fieldName, previousValue);
													fieldMap.put("new_" + fieldName, newValue);
												} else {
													fieldMap.put("old_" + fieldName, previousValue);
													fieldMap.put("new_" + fieldName, newValue);
													ExpressionLanguageResultEnum evalResult = ExpressionLanguageUtils.evaluate(fieldMap, workflowTransitions.get(currentWorkflow).getExpressionLanguage());
													System.out.println("EVAL RESULT : " + evalResult);
													if (evalResult == ExpressionLanguageResultEnum.COMPLETE_FALSE) {
														wti.setHasViolation(true);
														fieldMap = new HashMap<String, String>();
														currentWorkflow++;
													} else if (evalResult == ExpressionLanguageResultEnum.COMPLETE_TRUE) {
														wti.setHasViolation(false);
														fieldMap = new HashMap<String, String>();
														currentWorkflow++;
													} else {
														wti.setHasViolation(false);
													}
												}
											} else {
												wti.setHasViolation(false);
											}
										}
										wti.setOriginState(originState.toString());
										wti.setTargetState(targetState.toString());

										if (wti.getId() != null) {
											workflowTransitionInstanceService.threadUpdate(utx, entityManager, wti);
										} else {
											workflowTransitionInstanceService.threadCreate(utx, entityManager, wti);

											if (wti.isHasViolation()) {
												Notification notification = new Notification();
												notification.setWorkflowTransitionInstance(wti);
												notification.setWorkflowTransitionViolated(wti.getWorkflowTransition());
												notificationService.threadCreate(utx, entityManager, notification);
											}
										}
									}
								} else {

									WorkflowTransitionInstance wti = (WorkflowTransitionInstance) workflowTransitionInstanceService.findByExternalRefId(entityManager, WorkflowTransitionInstance.class, ticketChanges.getId());

									if (wti == null) {
										wti = new WorkflowTransitionInstance();
									}

									wti.setExternalRefId(ticketChanges.getId());
									wti.setMessage(ticketChanges.getComment());
									wti.setSpace(space);
									wti.setTicket(ticket);
									wti.setRemotelyCreated(ticketChanges.getCreatedOn() != null ? ticketChanges.getCreatedOn().toDate() : null);
									wti.setRemotelyUpdated(ticketChanges.getUpdatedAt() != null ? ticketChanges.getUpdatedAt().toDate() : null);
									if (workflowTransitions.size() > 0) {
										if (currentWorkflow < workflowTransitions.size() && workflowTransitions.size() > 0) {
											wti.setWorkflowTransition(workflowTransitions.get(currentWorkflow));
										}
									}
									wti.setOriginState("comment : ''");
									wti.setTargetState("comment : " + ticketChanges.getComment());

									if (wti.getId() != null) {
										workflowTransitionInstanceService.threadUpdate(utx, entityManager, wti);

										if (wti.isHasViolation()) {
											Notification notification = new Notification();
											notification.setWorkflowTransitionInstance(wti);
											notification.setWorkflowTransitionViolated(wti.getWorkflowTransition());
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

			currentUser.setSyncStatus("Ready to start");
			userService.threadUpdate(utx, entityManager, currentUser);
		} catch (Exception e) {
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

	private String sendRequest(User currentUser, String url, boolean requireAuthorization, String authorization)
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
