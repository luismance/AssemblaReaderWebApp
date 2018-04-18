package com.webdrone.job;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.EntityNameResolver;

import com.webdrone.assembla.dto.MilestoneAssemblaDto;
import com.webdrone.assembla.dto.TicketAssemblaDto;
import com.webdrone.assembla.dto.TicketListAssemblaDto;
import com.webdrone.model.Milestone;
import com.webdrone.model.Space;
import com.webdrone.model.Ticket;
import com.webdrone.model.User;
import com.webdrone.model.Workflow;
import com.webdrone.service.MilestoneService;
import com.webdrone.service.SpaceService;
import com.webdrone.service.TicketService;
import com.webdrone.service.UserService;
import com.webdrone.service.WorkflowService;
import com.webdrone.util.EntityManagerHelper;
import com.webdrone.util.RESTServiceUtil;
import com.webdrone.util.UserAuthResult;

public class TicketJobService extends Thread {

	private int ticketsPerPage = 100;
	private String authorization = "";
	private String spaceId = "";

	private EntityManagerFactory emf;
	private EntityManager entityManager;

	private TicketService ticketService;

	private UserService userService;

	private SpaceService spaceService;

	private MilestoneService milestoneService;

	private WorkflowService workflowService;

	public TicketJobService() {
		super();
	}

	public TicketJobService(String authorization, String spaceId) {
		super();
		this.authorization = authorization;
		this.spaceId = spaceId;
	}

	public void run() {

		entityManager = EntityManagerHelper.getEntityManager();
		ticketService = new TicketService();
		userService = new UserService();
		spaceService = new SpaceService();
		milestoneService = new MilestoneService();
		workflowService = new WorkflowService();

		ticketService.setEntityManager(entityManager);
		userService.setEntityManager(entityManager);
		spaceService.setEntityManager(entityManager);
		milestoneService.setEntityManager(entityManager);
		workflowService.setEntityManager(entityManager);

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			System.out.println("ERROR : " + valResult.getResponseMessage());
		}

		Object obj = spaceService.findByExternalRefId(Space.class, spaceId);
		Space space = obj != null ? (Space) obj : null;

		if (space == null) {
			System.out.println("ERROR : Invalid space id!");
		}

		int page = 0;

		TicketListAssemblaDto ticketListAssemblaDto = new TicketListAssemblaDto();

		do {
			ticketListAssemblaDto = RESTServiceUtil.convertTicketListXml(space.getExternalRefId(), ticketsPerPage, page,
					valResult.getUser().getBearerToken());

			if (ticketListAssemblaDto == null) {
				System.out.println("ERROR : An error occured while trying to retrieve ticket list");
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
				User assignedTo = null;

				if (!ticketAssemblaDto.getAssignedToId().isEmpty()) {
					assignedTo = assignedObj != null ? (User) assignedObj
							: RESTServiceUtil.convertUserXml(ticketAssemblaDto.getAssignedToId(),
									valResult.getUser().getBearerToken());

					if (assignedObj == null) {
						userService.create(assignedTo);
					} else {
						userService.update(assignedTo);
					}
				}
				/*
				 * START : MILESTONE SYNC
				 */

				Milestone milestone = null;

				if (!ticketAssemblaDto.getMilestoneId().isEmpty()) {
					MilestoneAssemblaDto milestoneAssemblaDto = RESTServiceUtil.convertMilestonXml(spaceId,
							ticketAssemblaDto.getMilestoneId(), valResult.getUser().getBearerToken());

					if (milestoneAssemblaDto == null) {
						System.out.println(
								"ERROR : An error occured while trying to retrieve milestone detail for ticket id : "
										+ ticketAssemblaDto.getId());
					}

					Object milestonObj = milestoneService.findByExternalRefId(Milestone.class,
							ticketAssemblaDto.getMilestoneId());
					milestone = milestonObj != null ? (Milestone) milestonObj : null;

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
					milestone.setDueDate(milestoneAssemblaDto.getDueDate() != null
							? milestoneAssemblaDto.getDueDate().toDate() : null);
					milestone.setCompleted(milestoneAssemblaDto.isCompleted());
					milestone.setTitle(milestoneAssemblaDto.getTitle());

					Object userCreatedObj = userService.findByExternalRefId(User.class,
							milestoneAssemblaDto.getCreatedBy());
					User mUserCreatedBy = userCreatedObj != null ? (User) userCreatedObj
							: RESTServiceUtil.convertUserXml(milestoneAssemblaDto.getCreatedBy(),
									valResult.getUser().getBearerToken());

					if (userCreatedObj == null) {
						userService.create(mUserCreatedBy);
					}

					Object userUpdatedObj = userService.findByExternalRefId(User.class,
							milestoneAssemblaDto.getUpdatedBy());
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
				}
				/*
				 * END : MILESTONE SYNC
				 */

				Workflow workflow = workflowService.getWorkflowByName(ticketAssemblaDto.getCustomFields().getType());

				/*
				 * START : TICKET SYNC
				 */
				if (currentTicket != null) {
					if (!ticketAssemblaDto.getAssignedToId().isEmpty()) {
						currentTicket.setAssignedTo(assignedTo);
					}
					currentTicket.setCompletedDate(ticketAssemblaDto.getCompletedDate() != null
							? ticketAssemblaDto.getCompletedDate().toDate() : null);
					currentTicket.setDescription(new String(ticketAssemblaDto.getDescription()));
					currentTicket.setEstimate(ticketAssemblaDto.getEstimate());
					currentTicket.setExternalRefId(ticketAssemblaDto.getId());
					currentTicket.setImportance(ticketAssemblaDto.getImportance());
					currentTicket.setMilestone(milestone);
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
					currentTicket.setWorkflow(workflow);
					currentTicket.setWorkingHours(ticketAssemblaDto.getWorkingHours());

					ticketService.update(currentTicket);
				} else {
					currentTicket = new Ticket(ticketAssemblaDto, space, milestone, reporter, assignedTo, workflow);
					ticketService.create(currentTicket);
				}
			}
			page++;
		} while (ticketListAssemblaDto.getTickets().size() == 100);

	}
}
