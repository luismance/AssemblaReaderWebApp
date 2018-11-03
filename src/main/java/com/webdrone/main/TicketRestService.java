package com.webdrone.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.webdrone.assembla.dto.SpaceTicketCountDto;
import com.webdrone.assembla.dto.TicketAssemblaDto;
import com.webdrone.assembla.dto.TicketChangesDto;
import com.webdrone.assembla.dto.TicketChangesListDto;
import com.webdrone.assembla.dto.TicketListAssemblaDto;
import com.webdrone.job.TicketJobService;
import com.webdrone.model.Space;
import com.webdrone.model.Ticket;
import com.webdrone.model.WorkflowTransition;
import com.webdrone.model.WorkflowTransitionInstance;
import com.webdrone.service.SpaceService;
import com.webdrone.service.TicketService;
import com.webdrone.service.UserService;
import com.webdrone.service.WorkflowTransitionInstanceService;
import com.webdrone.service.WorkflowTransitionService;
import com.webdrone.util.ExpressionLanguageResultEnum;
import com.webdrone.util.ExpressionLanguageUtils;
import com.webdrone.util.RESTServiceUtil;
import com.webdrone.util.UserAuthResult;

@Path("/ticket")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class TicketRestService {

	@Inject
	private UserService userService;

	@Inject
	private SpaceService spaceService;

	@Inject
	private TicketService ticketService;

	@Inject
	private WorkflowTransitionService workflowTransitionService;

	@Inject
	private WorkflowTransitionInstanceService workflowTranstionInstanceService;

	@GET
	@Path("/list")
	public Response getTickets(@HeaderParam("Authorization") String authorization, @QueryParam("space_id") String spaceId, @QueryParam("per_page") int ticketsPerPage, @QueryParam("page") int page) {

		if (ticketsPerPage == 0) {
			ticketsPerPage = 10;
		}

		if (page == 0) {
			page = 0;
		}
		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		System.out.println("Retrieving Ticket List for " + valResult.getUser().getUsername());

		Object obj = spaceService.findByExternalRefId(Space.class, spaceId);
		Space space = obj != null ? (Space) obj : null;

		if (space == null) {
			return Response.status(500).entity("Invalid space id!").build();
		}

		List<Ticket> ticketList = ticketService.getTicketsBySpace(spaceId, ticketsPerPage, page - 1);
		List<TicketAssemblaDto> result = new ArrayList<TicketAssemblaDto>();
		for (Ticket ticket : ticketList) {
			result.add(ticket.toDto());
		}

		TicketListAssemblaDto ticketListAssemblaDto = new TicketListAssemblaDto();
		ticketListAssemblaDto.setTickets(result);

		return Response.status(200).entity(ticketListAssemblaDto).build();
	}

	@GET
	@Path("/ticketCount")
	public Response getTicketCount(@HeaderParam("Authorization") String authorization, @QueryParam("space_id") String spaceId) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		Object obj = spaceService.findByExternalRefId(Space.class, spaceId);
		Space space = obj != null ? (Space) obj : null;

		if (space == null) {
			return Response.status(500).entity("Invalid space id!").build();
		}

		long ticketCount = ticketService.getTicketCountBySpace(spaceId);
		SpaceTicketCountDto ticketCountDto = new SpaceTicketCountDto();
		ticketCountDto.setTicketCount(ticketCount);
		ticketCountDto.setSyncStatus(valResult.getResponseMessage());
		return Response.status(200).entity(ticketCountDto).build();

	}

	@GET
	@Path("/userTicketCount")
	public Response totalTicketCount(@HeaderParam("Authorization") String authorization) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		long ticketCount = 0;
		for (Space space : valResult.getUser().getSpaces()) {
			ticketCount += ticketService.getTicketCountBySpace(space.getExternalRefId());
		}

		SpaceTicketCountDto ticketCountDto = new SpaceTicketCountDto();
		ticketCountDto.setTicketCount(ticketCount);
		ticketCountDto.setSyncStatus(valResult.getUser().getSyncStatus());
		return Response.status(200).entity(ticketCountDto).build();

	}

	@GET
	@Path("/ticketChanges")
	public Response getTicketChanges(@HeaderParam("Authorization") String authorization, @QueryParam("space_id") String spaceId, @QueryParam("ticket_num") String ticketNumber) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		System.out.println("Retrieving Ticket Changes for " + valResult.getUser().getUsername());

		Object obj = spaceService.findByExternalRefId(Space.class, spaceId);
		Space space = obj != null ? (Space) obj : null;

		if (space == null) {
			return Response.status(500).entity("Invalid space id!").build();
		}

		try {

			TicketChangesListDto ticketChangesList = new TicketChangesListDto();
			TicketChangesListDto ticketChangesDtoResponse = new TicketChangesListDto();

			String ticketChangesXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets/" + ticketNumber + "/ticket_comments.xml?per_page=100", true,
					"Bearer " + valResult.getUser().getBearerToken());

			ticketChangesList = (TicketChangesListDto) RESTServiceUtil.unmarshaller(TicketChangesListDto.class, ticketChangesXml);

			if (ticketChangesList == null) {
				RESTServiceUtil.refreshBearerToken(valResult.getUser());
				ticketChangesXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets/" + ticketNumber + "/ticket_comments.xml?per_page=100", true,
						"Bearer " + valResult.getUser().getBearerToken());
				ticketChangesList = (TicketChangesListDto) RESTServiceUtil.unmarshaller(TicketChangesListDto.class, ticketChangesXml);
			}

			if (ticketChangesList != null) {
				Ticket ticket = ticketService.getTicketBySpaceAndNumber(space.getId(), ticketNumber);

				// Temporary Workflow Transition
				WorkflowTransition wt = workflowTransitionService.listAll(WorkflowTransition.class).get(0);

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
					WorkflowTransitionInstance wti = (WorkflowTransitionInstance) workflowTranstionInstanceService.findByExternalRefId(WorkflowTransitionInstance.class, ticketChanges.getId());

					if (wti == null) {
						wti = new WorkflowTransitionInstance();
					}

					wti.setExternalRefId(ticketChanges.getId());
					wti.setMessage(ticketChanges.getComment());
					wti.setSpace(space);
					wti.setTicket(ticket);
					wti.setRemotelyCreated(ticketChanges.getCreatedOn() != null ? ticketChanges.getCreatedOn().toDate() : null);
					wti.setRemotelyUpdated(ticketChanges.getUpdatedAt() != null ? ticketChanges.getUpdatedAt().toDate() : null);

					wti.setWorkflowTransition(wt);

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

							if (currentWorkflow < workflowTransitions.size() && workflowTransitions.size() > 0) {
								if (previousValue.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\dZ")) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
									previousValue = TimeUnit.MILLISECONDS.toMinutes(sdf.parse(previousValue).getTime()) + "";
								}

								if (newValue.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\dZ")) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
									newValue = TimeUnit.MILLISECONDS.toMinutes(sdf.parse(newValue).getTime()) + "";
								}

								fieldMap.put("new_update_at", ticketChanges.getUpdatedAt().toDate().getTime() + "");

								if (fieldMap.get("old_" + fieldName) != null && fieldMap.get("new_" + fieldName) != null) {
									fieldMap = new HashMap<String, String>();
									currentWorkflow++;
									if (currentWorkflow > workflowTransitions.size()) {
										ticketChanges.setHasViolation(true);
										ticketChanges.setViolationCode(workflowTransitions.get(currentWorkflow).getErrorCode());
										ticketChanges.setViolationMessage(workflowTransitions.get(currentWorkflow).getErrorMessage());
									}
									fieldMap.put("old_" + fieldName, previousValue);
									fieldMap.put("new_" + fieldName, newValue);
								} else {
									fieldMap.put("old_" + fieldName, previousValue);
									fieldMap.put("new_" + fieldName, newValue);
									ExpressionLanguageResultEnum evalResult = ExpressionLanguageUtils.evaluate(fieldMap, workflowTransitions.get(currentWorkflow).getExpressionLanguage());
									System.out.println("EVAL RESULT : " + evalResult);
									if (evalResult == ExpressionLanguageResultEnum.COMPLETE_FALSE) {
										ticketChanges.setHasViolation(true);
										ticketChanges.setViolationCode(workflowTransitions.get(currentWorkflow).getErrorCode());
										ticketChanges.setViolationMessage(workflowTransitions.get(currentWorkflow).getErrorMessage());
										fieldMap = new HashMap<String, String>();
										currentWorkflow++;
									} else if (evalResult == ExpressionLanguageResultEnum.COMPLETE_TRUE) {
										ticketChanges.setHasViolation(false);
										fieldMap = new HashMap<String, String>();
										currentWorkflow++;
									} else {
										ticketChanges.setHasViolation(false);
									}
								}
							} else {
								ticketChanges.setHasViolation(false);
							}
							wti.setOriginState(originState.toString());
							wti.setTargetState(targetState.toString());
						}
					} else {
						wti.setOriginState("Comment : ''");
						wti.setTargetState("Comment : " + ticketChanges.getComment());
					}

					if (wti.getId() != null) {
						workflowTranstionInstanceService.update(wti);
					} else {
						workflowTranstionInstanceService.create(wti);
					}
				}

				ticketChangesDtoResponse.setTicketChanges(ticketChangesReversed);

			}

			return Response.status(200).entity(ticketChangesDtoResponse).build();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Response.status(500).entity("An error occured while trying to retrieve data").build();
	}

	@POST
	@Path("/triggerTicketSync")
	public Response triggerTicketSync(@HeaderParam("Authorization") String authorization, @QueryParam("space_id") String spaceId) {

		TicketJobService ticketJobService = new TicketJobService(authorization, spaceId);
		ticketJobService.start();
		return Response.status(200).entity("Okay").build();
	}
}
