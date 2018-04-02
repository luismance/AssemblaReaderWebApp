package com.webdrone.main;

import java.io.StringReader;

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

import com.webdrone.assembla.dto.SpaceTicketCountDto;
import com.webdrone.assembla.dto.TicketAssemblaDto;
import com.webdrone.assembla.dto.TicketListAssemblaDto;
import com.webdrone.model.Space;
import com.webdrone.model.Ticket;
import com.webdrone.model.User;
import com.webdrone.service.SpaceService;
import com.webdrone.service.TicketService;
import com.webdrone.service.UserService;
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

		try {
			String ticketsXml = RESTServiceUtil
					.sendGET(
							"https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets.xml?per_page="
									+ ticketsPerPage + "&page=" + page,
							true, "Bearer " + valResult.getUser().getBearerToken());

			/* System.out.println("Tickets XML : " + ticketsXml); */

			JAXBContext jxb = JAXBContext.newInstance(TicketListAssemblaDto.class);

			Unmarshaller unmarshaller = jxb.createUnmarshaller();

			unmarshaller.setEventHandler(new ValidationEventHandler() {
				public boolean handleEvent(ValidationEvent event) {
					throw new RuntimeException(event.getMessage(), event.getLinkedException());
				}
			});

			TicketListAssemblaDto ticketListAssemblaDto = (TicketListAssemblaDto) unmarshaller
					.unmarshal(new StringReader(ticketsXml));

			for (TicketAssemblaDto ticketAssemblaDto : ticketListAssemblaDto.getTickets()) {
				Object ticketObj = ticketService.findByExternalRefId(Ticket.class, ticketAssemblaDto.getId());
				Ticket currentTicket = ticketObj != null ? (Ticket) ticketObj : null;
				User reporter = (User) userService.findByExternalRefId(User.class, ticketAssemblaDto.getReporterId());
				User assignedTo = (User) userService.findByExternalRefId(User.class,
						ticketAssemblaDto.getAssignedToId());
				// TODO link milestone and workflow
				if (currentTicket != null) {
					currentTicket.setAssignedTo(assignedTo);
					currentTicket.setCompletedDate(ticketAssemblaDto.getCompletedDate() != null
							? ticketAssemblaDto.getCompletedDate().toDate() : null);
					currentTicket.setDescription(ticketAssemblaDto.getDescription());
					currentTicket.setEstimate(ticketAssemblaDto.getEstimate());
					currentTicket.setExternalRefId(ticketAssemblaDto.getId());
					currentTicket.setImportance(ticketAssemblaDto.getImportance());
					// currentTicket.setMilestone(milestone);
					currentTicket.setPriorityTypeId(ticketAssemblaDto.getPriority());
					currentTicket.setRemotelyCreated(ticketAssemblaDto.getCreatedOn() != null
							? ticketAssemblaDto.getCreatedOn().toDate() : null);
					currentTicket.setRemotelyUpdated(ticketAssemblaDto.getUpdatedAt() != null
							? ticketAssemblaDto.getUpdatedAt().toDate() : null);
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
					// currentTicket.setWorkflow(workflow);
					currentTicket.setWorkingHours(ticketAssemblaDto.getWorkingHours());
				} else {
					currentTicket = new Ticket(ticketAssemblaDto, space, null, reporter, assignedTo, null);
					ticketService.create(currentTicket);
				}
			}

			return Response.status(200).entity(ticketListAssemblaDto).build();

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return Response.status(500).entity("An error occured while trying to retrieve data").build();
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
			int pageIncrement = 10;

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
}
