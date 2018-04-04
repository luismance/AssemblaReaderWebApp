package com.webdrone.main;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import com.webdrone.assembla.dto.MilestoneAssemblaDto;
import com.webdrone.assembla.dto.SpaceTicketCountDto;
import com.webdrone.assembla.dto.TicketAssemblaDto;
import com.webdrone.assembla.dto.TicketChangesDto;
import com.webdrone.assembla.dto.TicketChangesListDto;
import com.webdrone.assembla.dto.TicketListAssemblaDto;
import com.webdrone.model.Milestone;
import com.webdrone.model.Space;
import com.webdrone.model.Ticket;
import com.webdrone.model.User;
import com.webdrone.model.Workflow;
import com.webdrone.model.WorkflowTransition;
import com.webdrone.model.WorkflowTransitionInstance;
import com.webdrone.service.MilestoneService;
import com.webdrone.service.SpaceService;
import com.webdrone.service.TicketService;
import com.webdrone.service.UserService;
import com.webdrone.service.WorkflowService;
import com.webdrone.service.WorkflowTransitionInstanceService;
import com.webdrone.service.WorkflowTransitionService;
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
	private MilestoneService milestoneService;

	@Inject
	private WorkflowService workflowService;

	@Inject
	private WorkflowTransitionService workflowTransitionService;

	@Inject
	private WorkflowTransitionInstanceService workflowTranstionInstanceService;

	@GET
	@Path("/list")
	public Response getTickets(@HeaderParam("Authorization") String authorization,
			@QueryParam("space_id") String spaceId, @QueryParam("per_page") int ticketsPerPage,
			@QueryParam("page") int page) {

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

		Object obj = spaceService.findByExternalRefId(Space.class, spaceId);
		Space space = obj != null ? (Space) obj : null;

		if (space == null) {
			return Response.status(500).entity("Invalid space id!").build();
		}

		TicketListAssemblaDto ticketListAssemblaDto = RESTServiceUtil.convertTicketListXml(space.getExternalRefId(),
				ticketsPerPage, page, valResult.getUser().getBearerToken());

		if (ticketListAssemblaDto == null) {
			return Response.status(500).entity("An error occured while trying to retrieve ticket list").build();
		}

		for (TicketAssemblaDto ticketAssemblaDto : ticketListAssemblaDto.getTickets()) {
			Object ticketObj = ticketService.findByExternalRefId(Ticket.class, ticketAssemblaDto.getId());
			Ticket currentTicket = ticketObj != null ? (Ticket) ticketObj : null;

			Object reporterObj = userService.findByExternalRefId(User.class, ticketAssemblaDto.getReporterId());
			User reporter = reporterObj != null ? (User) reporterObj
					: RESTServiceUtil.convertUserXml(ticketAssemblaDto.getReporterId(),
							valResult.getUser().getBearerToken());

			if (reporterObj == null) {
				userService.create(reporter);
			} else {
				userService.update(reporter);
			}

			Object assignedObj = userService.findByExternalRefId(User.class, ticketAssemblaDto.getAssignedToId());
			User assignedTo = assignedObj != null ? (User) assignedObj
					: RESTServiceUtil.convertUserXml(ticketAssemblaDto.getAssignedToId(),
							valResult.getUser().getBearerToken());

			if (assignedObj == null) {
				userService.create(assignedTo);
			} else {
				userService.update(assignedTo);
			}
			/*
			 * START : MILESTONE SYNC
			 */

			MilestoneAssemblaDto milestoneAssemblaDto = RESTServiceUtil.convertMilestonXml(spaceId,
					ticketAssemblaDto.getMilestoneId(), valResult.getUser().getBearerToken());

			if (milestoneAssemblaDto == null) {
				return Response.status(500)
						.entity("An error occured while trying to retrieve milestone detail for ticket id : "
								+ ticketAssemblaDto.getId())
						.build();
			}

			Object milestonObj = milestoneService.findByExternalRefId(Milestone.class,
					ticketAssemblaDto.getMilestoneId());
			Milestone milestone = milestonObj != null ? (Milestone) milestonObj : null;

			if (milestone == null) {
				milestone = new Milestone();
			}

			milestone.setExternalRefId(milestoneAssemblaDto.getId());
			milestone.setPlannerType(milestoneAssemblaDto.getPlannerType());
			milestone.setDescription(milestoneAssemblaDto.getDescription());
			milestone.setReleaseNotes(milestoneAssemblaDto.getReleaseNotes());
			milestone.setPrettyReleaseLevel(milestoneAssemblaDto.getPrettyReleaseLevel());
			milestone.setCompletedDate(milestoneAssemblaDto.getCompletedDate() != null
					? milestoneAssemblaDto.getCompletedDate().toDate() : null);
			milestone.setDueDate(
					milestoneAssemblaDto.getDueDate() != null ? milestoneAssemblaDto.getDueDate().toDate() : null);
			milestone.setCompleted(milestoneAssemblaDto.isCompleted());
			milestone.setTitle(milestoneAssemblaDto.getTitle());

			Object userCreatedObj = userService.findByExternalRefId(User.class, milestoneAssemblaDto.getCreatedBy());
			User mUserCreatedBy = userCreatedObj != null ? (User) userCreatedObj
					: RESTServiceUtil.convertUserXml(milestoneAssemblaDto.getCreatedBy(),
							valResult.getUser().getBearerToken());

			if (userCreatedObj == null) {
				userService.create(mUserCreatedBy);
			}

			Object userUpdatedObj = userService.findByExternalRefId(User.class, milestoneAssemblaDto.getCreatedBy());
			User mUserUpdatedBy = userUpdatedObj != null ? (User) userUpdatedObj
					: RESTServiceUtil.convertUserXml(milestoneAssemblaDto.getUpdatedBy(),
							valResult.getUser().getBearerToken());

			if (userUpdatedObj == null) {
				userService.create(mUserUpdatedBy);
			}

			milestone.setCreatedBy(mUserCreatedBy);
			milestone.setUpdatedBy(mUserUpdatedBy);

			milestone.setSpace(space);
			if (milestonObj == null) {
				milestoneService.create(milestone);
			} else {
				milestoneService.update(milestone);
			}

			/*
			 * END : MILESTONE SYNC
			 */

			Workflow workflow = workflowService.getWorkflowByName(ticketAssemblaDto.getCustomFields().getType());

			/*
			 * START : TICKET SYNC
			 */
			if (currentTicket != null) {
				currentTicket.setAssignedTo(assignedTo);
				currentTicket.setCompletedDate(ticketAssemblaDto.getCompletedDate() != null
						? ticketAssemblaDto.getCompletedDate().toDate() : null);
				currentTicket.setDescription(
						new String(ticketAssemblaDto.getDescription().getBytes(StandardCharsets.UTF_8)));
				currentTicket.setEstimate(ticketAssemblaDto.getEstimate());
				currentTicket.setExternalRefId(ticketAssemblaDto.getId());
				currentTicket.setImportance(ticketAssemblaDto.getImportance());
				// currentTicket.setMilestone(milestone);
				currentTicket.setPriorityTypeId(ticketAssemblaDto.getPriority());
				currentTicket.setRemotelyCreated(
						ticketAssemblaDto.getCreatedOn() != null ? ticketAssemblaDto.getCreatedOn().toDate() : null);
				currentTicket.setRemotelyUpdated(
						ticketAssemblaDto.getUpdatedAt() != null ? ticketAssemblaDto.getUpdatedAt().toDate() : null);
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

				ticketService.update(currentTicket);
			} else {
				currentTicket = new Ticket(ticketAssemblaDto, space, milestone, reporter, assignedTo, workflow);
				ticketService.create(currentTicket);
			}

			/*
			 * END : TICKET SYNC
			 */
		}

		return Response.status(200).entity(ticketListAssemblaDto).build();
	}

	@GET
	@Path("/ticketCount")
	public Response getTicketCount(@HeaderParam("Authorization") String authorization,
			@QueryParam("space_id") String spaceId) {

		int ticketsPerPage = 100;

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		Object obj = spaceService.findByExternalRefId(Space.class, spaceId);
		Space space = obj != null ? (Space) obj : null;

		if (space == null) {
			return Response.status(500).entity("Invalid space id!").build();
		}

		try {

			TicketListAssemblaDto ticketListAssemblaDto = new TicketListAssemblaDto();
			int page = 1;
			int result = 0;
			int ticketListSize = 0;
			int pageIncrement = 5;

			boolean wasNegative = false;
			do {
				String ticketsXml = RESTServiceUtil.sendGET(
						"https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets.xml?per_page="
								+ ticketsPerPage + "&page=" + page,
						true, "Bearer " + valResult.getUser().getBearerToken());

				if (!ticketsXml.isEmpty()) {
					JAXBContext jxb = JAXBContext.newInstance(TicketListAssemblaDto.class);

					Unmarshaller unmarshaller = jxb.createUnmarshaller();

					unmarshaller.setEventHandler(new ValidationEventHandler() {
						public boolean handleEvent(ValidationEvent event) {
							throw new RuntimeException(event.getMessage(), event.getLinkedException());
						}
					});

					ticketListAssemblaDto = (TicketListAssemblaDto) unmarshaller
							.unmarshal(new StringReader(ticketsXml));
					ticketListSize = ticketListAssemblaDto.getTickets().size();

					System.out.println("Page : " + page + ", Page Increment : " + pageIncrement + ", Result : " + result
							+ ", Ticket List Size : " + ticketListSize);

				} else {
					ticketListSize = 0;
				}

				if (ticketListSize == 0) {
					page -= 1;
					wasNegative = true;
				} else {
					if (page == 1 && ticketListSize < ticketsPerPage) {
						result = ticketListSize;
						break;
					} else if (ticketListSize <= ticketsPerPage && ticketListSize > 0 && wasNegative) {
						result = ((page - 1) * ticketsPerPage) + ticketListSize;
						break;
					}

					page += pageIncrement;
				}

				System.out.println("Page : " + page + ", Page Increment : " + pageIncrement + ", Result : " + result
						+ ", Ticket List Size : " + ticketListSize);
			} while (result <= 0);

			SpaceTicketCountDto ticketCountDto = new SpaceTicketCountDto();
			ticketCountDto.setTicketCount(result);
			System.out.println("Ticket Count : " + result);
			return Response.status(200).entity(ticketCountDto).build();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return Response.status(500).entity("An error occured while trying to retrieve data").build();
	}

	@GET
	@Path("/ticketChanges")
	public Response getTicketChanges(@HeaderParam("Authorization") String authorization,
			@QueryParam("space_id") String spaceId, @QueryParam("ticket_num") String ticketNumber) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		Object obj = spaceService.findByExternalRefId(Space.class, spaceId);
		Space space = obj != null ? (Space) obj : null;

		if (space == null) {
			return Response.status(500).entity("Invalid space id!").build();
		}

		try {

			TicketChangesListDto ticketChangesList = new TicketChangesListDto();

			String ticketChangesXml = RESTServiceUtil
					.sendGET(
							"https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets/"
									+ ticketNumber + "/ticket_comments.xml",
							true, "Bearer " + valResult.getUser().getBearerToken());

			if (!ticketChangesXml.isEmpty()) {
				JAXBContext jxb = JAXBContext.newInstance(TicketChangesListDto.class);

				Unmarshaller unmarshaller = jxb.createUnmarshaller();

				unmarshaller.setEventHandler(new ValidationEventHandler() {
					public boolean handleEvent(ValidationEvent event) {
						throw new RuntimeException(event.getMessage(), event.getLinkedException());
					}
				});

				ticketChangesList = (TicketChangesListDto) unmarshaller.unmarshal(new StringReader(ticketChangesXml));

				Ticket ticket = ticketService.getTicketBySpaceAndNumber(space.getId(), ticketNumber);

				// Temporary Workflow Transition
				WorkflowTransition wt = workflowTransitionService.listAll(WorkflowTransition.class).get(0);

				for (TicketChangesDto ticketChanges : ticketChangesList.getTicketChanges()) {
					WorkflowTransitionInstance wti = (WorkflowTransitionInstance) workflowTranstionInstanceService
							.findByExternalRefId(WorkflowTransitionInstance.class, ticketChanges.getId());

					if (wti == null) {
						wti = new WorkflowTransitionInstance();
					}

					wti.setExternalRefId(ticketChanges.getId());
					wti.setMessage(ticketChanges.getComment());
					wti.setSpace(space);
					wti.setTicket(ticket);
					wti.setRemotelyCreated(
							ticketChanges.getCreatedOn() != null ? ticketChanges.getCreatedOn().toDate() : null);
					wti.setRemotelyUpdated(
							ticketChanges.getUpdatedAt() != null ? ticketChanges.getUpdatedAt().toDate() : null);

					wti.setWorkflowTransition(wt);

					System.out.println("Ticket Changes : " + ticketChanges.getTicketChanges());

					if (ticketChanges.getTicketChanges().contains("- - ")) {
						String[] fields = ticketChanges.getTicketChanges().split(" - -");

						StringBuilder originState = new StringBuilder();
						StringBuilder targetState = new StringBuilder();

						for (int i = 0; i < fields.length; i++) {
							String fieldName = fields[i].split("  - ")[0].replace("---\n- - ", "");
							String previousValue = fields[i].split("  - ")[1];
							String newValue = fields[i].split("  - ")[2];

							originState.append(fieldName).append(":").append(previousValue)
									.append(System.getProperty("line.separator"));
							targetState.append(fieldName).append(":").append(newValue)
									.append(System.getProperty("line.separator"));

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

					ticketChanges.setHasViolation(true);

				}

			}

			return Response.status(200).entity(ticketChangesList).build();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return Response.status(500).entity("An error occured while trying to retrieve data").build();
	}
}
