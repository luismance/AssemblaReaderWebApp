package com.webdrone.main;

import java.util.ArrayList;
import java.util.List;

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

import org.joda.time.DateTime;

import com.webdrone.assembla.dto.SpaceTicketChangesCountDto;
import com.webdrone.assembla.dto.SpaceTicketCountDto;
import com.webdrone.assembla.dto.TicketAssemblaDto;
import com.webdrone.assembla.dto.TicketChangesDto;
import com.webdrone.assembla.dto.TicketChangesListDto;
import com.webdrone.assembla.dto.TicketListAssemblaDto;
import com.webdrone.job.TicketJobService;
import com.webdrone.model.Space;
import com.webdrone.model.Ticket;
import com.webdrone.model.WorkflowTransitionInstance;
import com.webdrone.service.SpaceService;
import com.webdrone.service.TicketService;
import com.webdrone.service.UserService;
import com.webdrone.service.WorkflowTransitionInstanceService;
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
	private WorkflowTransitionInstanceService workflowTranstionInstanceService;

	@GET
	@Path("/list")
	public Response getTickets(@HeaderParam("Authorization") String authorization, @QueryParam("space_id") String spaceId, @QueryParam("per_page") int ticketsPerPage, @QueryParam("page") int page,
			@QueryParam("sort_by") String sortBy, @QueryParam("violation_type") String violationType, @QueryParam("priority") String priority) {

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

		List<Ticket> ticketList = ticketService.getTicketsBySpace(spaceId, ticketsPerPage, page - 1, sortBy, violationType, priority);
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
	public Response getTicketCount(@HeaderParam("Authorization") String authorization, @QueryParam("space_id") String spaceId, @QueryParam("violation_type") String violationType,
			@QueryParam("priority") String priority) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		Object obj = spaceService.findByExternalRefId(Space.class, spaceId);
		Space space = obj != null ? (Space) obj : null;

		if (space == null) {
			return Response.status(500).entity("Invalid space id!").build();
		}

		long ticketCount = ticketService.getTicketCountBySpace(spaceId, violationType, priority);
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
			ticketCount += ticketService.getTicketCountBySpace(space.getExternalRefId(), "", "");
		}

		SpaceTicketCountDto ticketCountDto = new SpaceTicketCountDto();
		ticketCountDto.setTicketCount(ticketCount);
		ticketCountDto.setSyncStatus(valResult.getUser().getSyncStatus());
		return Response.status(200).entity(ticketCountDto).build();

	}

	@GET
	@Path("/userTicketChangesCount")
	public Response totalTicketChangesCount(@HeaderParam("Authorization") String authorization) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		long ticketChangesCount = 0;
		for (Space space : valResult.getUser().getSpaces()) {
			ticketChangesCount += workflowTranstionInstanceService.getTicketChangesCountBySpace(space.getExternalRefId());
		}

		SpaceTicketChangesCountDto ticketChangesCountDto = new SpaceTicketChangesCountDto();
		ticketChangesCountDto.setTicketChangesCount(ticketChangesCount);
		ticketChangesCountDto.setSyncStatus(valResult.getUser().getSyncStatus());
		return Response.status(200).entity(ticketChangesCountDto).build();

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

		if (space.isCanProcessTicketChanges()) {
			TicketChangesListDto ticketChangesList = new TicketChangesListDto();
			List<TicketChangesDto> ticketChangesDtoList = new ArrayList<TicketChangesDto>();
			List<WorkflowTransitionInstance> wtiList = workflowTranstionInstanceService.getWorkflowTransitionInstanceBySpaceAndTicket(space.getExternalRefId(), ticketNumber);

			for (WorkflowTransitionInstance wti : wtiList) {
				TicketChangesDto ticketChangesDto = new TicketChangesDto();
				ticketChangesDto.setComment(wti.getMessage());
				ticketChangesDto.setCreatedOn(new DateTime(wti.getRemotelyCreated()));
				ticketChangesDto.setHasViolation(wti.isHasViolation());
				ticketChangesDto.setId(wti.getExternalRefId());

				ticketChangesDto.setTicketChanges("- - " + wti.getOriginState().split(":")[0] + System.lineSeparator() + "- " + wti.getOriginState().split(":")[1] + System.lineSeparator() + "- "
						+ wti.getTargetState().split(":")[1]);
				ticketChangesDto.setId(wti.getExternalRefId());
				ticketChangesDto.setTicketId(wti.getTicket().getExternalRefId());
				ticketChangesDto.setUpdatedAt(new DateTime(wti.getRemotelyUpdated()));
				ticketChangesDto.setViolationCode(wti.isHasViolation() ? wti.getWorkflowTransition().getErrorCode() : "");
				ticketChangesDto.setViolationMessage(wti.isHasViolation() ? wti.getWorkflowTransition().getErrorMessage() : "");
				ticketChangesDtoList.add(ticketChangesDto);
			}
			ticketChangesList.setTicketChanges(ticketChangesDtoList);
			return Response.status(200).entity(ticketChangesList).build();
		} else {
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
				// Reverse list because assembla returns the changes in reverse
				List<TicketChangesDto> ticketChangesReversed = new ArrayList<TicketChangesDto>();
				List<TicketChangesDto> newTicketChangesList = ticketChangesList.getTicketChanges();

				for (int i = newTicketChangesList.size() - 1; i >= 0; i--) {
					ticketChangesReversed.add(newTicketChangesList.get(i));
				}
				ticketChangesDtoResponse.setTicketChanges(ticketChangesReversed);

			}

			return Response.status(200).entity(ticketChangesDtoResponse).build();

		}
	}

	@POST
	@Path("/triggerTicketSync")
	public Response triggerTicketSync(@HeaderParam("Authorization") String authorization, @QueryParam("space_id") String spaceId) {

		TicketJobService ticketJobService = new TicketJobService(authorization, spaceId);
		ticketJobService.start();
		return Response.status(200).entity("Okay").build();
	}
}
