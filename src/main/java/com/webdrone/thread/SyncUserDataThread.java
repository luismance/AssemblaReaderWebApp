package com.webdrone.thread;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.webdrone.assembla.dto.MilestoneAssemblaDto;
import com.webdrone.assembla.dto.SpaceAssemblaDto;
import com.webdrone.assembla.dto.SpaceListAssemblaDto;
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
import com.webdrone.util.RESTServiceUtil;

@Stateless
public class SyncUserDataThread implements Runnable {

	private UserService userService;

	private String username;

	private EntityManager entityManager;

	public SyncUserDataThread() {

	}

	public SyncUserDataThread(EntityManager em, String username) {
		this.username = username;
		this.entityManager = em;
	}

	public void run() {

		try {
			System.out.println("Starting Space Sync");

			userService = new UserService();
			SpaceService spaceService = new SpaceService();
			TicketService ticketService = new TicketService();
			MilestoneService milestoneService = new MilestoneService();
			WorkflowService workflowService = new WorkflowService();

			User currentUser = userService.findUserByUsername(entityManager, username);

			List<Space> spaceList = new ArrayList<Space>();
			if (currentUser != null) {
				String spacesXml = sendRequest(currentUser, "https://api.assembla.com/v1/spaces.xml", true, "Bearer " + currentUser.getBearerToken());

				SpaceListAssemblaDto spaceListAssemblaDto = (SpaceListAssemblaDto) RESTServiceUtil.unmarshaller(SpaceListAssemblaDto.class, spacesXml);

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

						spaceList.add((Space) spaceService.threadCreate(entityManager, new Space(spaceAssemblaDto)));
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
						spaceList.add((Space) spaceService.threadUpdate(entityManager, currentSpace));
						System.out.println("Space Updated : " + spaceAssemblaDto.getId());
					}

				}

				int ticketsPerPage = 100;

				for (Space space : spaceList) {
					System.out.println("Current Space : " + space.getExternalRefId());
					if (space != null) {
						TicketListAssemblaDto ticketListAssemblaDto = new TicketListAssemblaDto();
						int page = 1;
						int ticketListSize = 0;
						do {
							String ticketsXml = sendRequest(currentUser, "https://api.assembla.com/v1/spaces/" + space.getExternalRefId() + "/tickets.xml?per_page=" + ticketsPerPage + "&page=" + page, true, "Bearer " + currentUser.getBearerToken());

							if (ticketsXml.length() < 30) {
								System.out.println("Tickets XML : " + ticketsXml);
							}

							if (!ticketsXml.isEmpty() && !ticketsXml.equals("204")) {
								ticketListAssemblaDto = (TicketListAssemblaDto) RESTServiceUtil.unmarshaller(TicketListAssemblaDto.class, ticketsXml);
								ticketListSize = ticketListAssemblaDto.getTickets().size();
								for (TicketAssemblaDto ticketAssemblaDto : ticketListAssemblaDto.getTickets()) {
									Object ticketObj = ticketService.findByExternalRefId(entityManager, Ticket.class, ticketAssemblaDto.getId());
									Ticket currentTicket = ticketObj != null ? (Ticket) ticketObj : null;

									Object reporterObj = userService.findByExternalRefId(entityManager, User.class, ticketAssemblaDto.getReporterId());
									User reporter = reporterObj != null ? (User) reporterObj : RESTServiceUtil.convertUserXml(ticketAssemblaDto.getReporterId(), currentUser.getBearerToken());

									if (reporterObj == null) {
										userService.threadCreate(entityManager, reporter);
										System.out.println("Reporter Created : " + reporter.getUsername());
									} else {
										userService.threadUpdate(entityManager, reporter);
										System.out.println("Reporter Updated : " + reporter.getUsername());
									}

									Object assignedObj = userService.findByExternalRefId(entityManager, User.class, ticketAssemblaDto.getAssignedToId());
									User assignedTo = null;

									if (!ticketAssemblaDto.getAssignedToId().isEmpty()) {
										assignedTo = assignedObj != null ? (User) assignedObj : RESTServiceUtil.convertUserXml(ticketAssemblaDto.getAssignedToId(), currentUser.getBearerToken());

										if (assignedObj == null) {
											userService.threadCreate(entityManager, assignedTo);
											System.out.println("AssignedTo Created : " + assignedTo.getUsername());
										} else {
											userService.threadUpdate(entityManager, assignedTo);
											System.out.println("AssignedTo Updated : " + assignedTo.getUsername());
										}
									}
									/*
									 * START : MILESTONE SYNC
									 */

									Milestone milestone = null;

									if (!ticketAssemblaDto.getMilestoneId().isEmpty()) {
										MilestoneAssemblaDto milestoneAssemblaDto = RESTServiceUtil.convertMilestonXml(space.getExternalRefId(), ticketAssemblaDto.getMilestoneId(), currentUser.getBearerToken());

										Object milestonObj = milestoneService.findByExternalRefId(entityManager, Milestone.class, ticketAssemblaDto.getMilestoneId());
										milestone = milestonObj != null ? (Milestone) milestonObj : null;

										if (milestone == null) {
											milestone = new Milestone();
										}

										milestone.setExternalRefId(milestoneAssemblaDto.getId());
										milestone.setPlannerType(milestoneAssemblaDto.getPlannerType());
										milestone.setDescription(milestoneAssemblaDto.getDescription());
										milestone.setReleaseNotes(milestoneAssemblaDto.getReleaseNotes());
										milestone.setPrettyReleaseLevel(milestoneAssemblaDto.getPrettyReleaseLevel());
										milestone.setCompletedDate(milestoneAssemblaDto.getCompletedDate() != null ? milestoneAssemblaDto.getCompletedDate().toDate() : null);
										milestone.setDueDate(milestoneAssemblaDto.getDueDate() != null ? milestoneAssemblaDto.getDueDate().toDate() : null);
										milestone.setCompleted(milestoneAssemblaDto.isCompleted());
										milestone.setTitle(milestoneAssemblaDto.getTitle());

										Object userCreatedObj = userService.findByExternalRefId(entityManager, User.class, milestoneAssemblaDto.getCreatedBy());
										User mUserCreatedBy = userCreatedObj != null ? (User) userCreatedObj : RESTServiceUtil.convertUserXml(milestoneAssemblaDto.getCreatedBy(), currentUser.getBearerToken());

										if (userCreatedObj == null) {
											userService.threadCreate(entityManager, mUserCreatedBy);
											System.out.println("Milestone Created By User Created : " + mUserCreatedBy.getUsername());
										}

										Object userUpdatedObj = userService.findByExternalRefId(entityManager, User.class, milestoneAssemblaDto.getUpdatedBy());
										User mUserUpdatedBy = userUpdatedObj != null ? (User) userUpdatedObj : RESTServiceUtil.convertUserXml(milestoneAssemblaDto.getUpdatedBy(), currentUser.getBearerToken());

										if (userUpdatedObj == null) {
											userService.threadCreate(entityManager, mUserUpdatedBy);
											System.out.println("Milestone Updated By User Created : " + mUserUpdatedBy.getUsername());
										}

										milestone.setCreatedBy(mUserCreatedBy);
										milestone.setUpdatedBy(mUserUpdatedBy);

										milestone.setSpace(space);
										if (milestonObj == null) {
											milestoneService.threadCreate(entityManager, milestone);
											System.out.println("Milestone Created : " + milestone.getExternalRefId());
										} else {
											milestoneService.threadUpdate(entityManager, milestone);
											System.out.println("Milestone Updated : " + milestone.getExternalRefId());
										}
									}
									/*
									 * END : MILESTONE SYNC
									 */

									Workflow workflow = workflowService.getWorkflowByName(entityManager, ticketAssemblaDto.getCustomFields().getType());

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

										ticketService.threadUpdate(entityManager, currentTicket);

										System.out.println("Ticket Updated : " + currentTicket.getExternalRefId());
									} else {
										currentTicket = new Ticket(ticketAssemblaDto, space, milestone, reporter, assignedTo, workflow);
										ticketService.threadCreate(entityManager, currentTicket);
										System.out.println("Ticket Created : " + currentTicket.getExternalRefId());
									}

									/*
									 * END : TICKET SYNC
									 */
								}
								System.out.println("Page : " + page + ", Ticket List Size : " + ticketListSize);
								page++;
							} else {
								return;
							}
						} while (ticketListSize > 0);
					}
				}
			} else {
				System.out.println("User is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String sendRequest(User currentUser, String url, boolean requireAuthorization, String authorization) {
		String sendReq = RESTServiceUtil.sendGET(url, requireAuthorization, authorization);

		if (sendReq.equals("401")) {

			RESTServiceUtil.refreshBearerToken(currentUser);

			sendReq = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces.xml", true, "Bearer " + currentUser.getBearerToken());
			userService.threadUpdate(entityManager, currentUser);
		}

		return sendReq;
	}
}
