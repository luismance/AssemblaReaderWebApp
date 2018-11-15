package com.webdrone.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.webdrone.assembla.dto.NotificationCountDto;
import com.webdrone.assembla.dto.NotificationDto;
import com.webdrone.assembla.dto.NotificationListDto;
import com.webdrone.model.Notification;
import com.webdrone.service.NotificationService;
import com.webdrone.service.UserService;
import com.webdrone.util.UserAuthResult;

@Path("/notification")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class NotificationRestService {

	@Inject
	private UserService userService;

	@Inject
	private NotificationService notificationService;

	@GET
	@Path("/list")
	public Response getNotifications(@HeaderParam("Authorization") String authorization, @QueryParam("per_page") int notifsPerPage, @QueryParam("page") int page, @QueryParam("spaceid") String spaceId,
			@QueryParam("violation_type") String violationType) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		if (violationType == null) {
			violationType = "";
		}
		List<Notification> notifications = notificationService.getNotifications(valResult.getUser().getSpaces(), page - 1, notifsPerPage, spaceId, violationType);

		List<NotificationDto> notificationsDto = new ArrayList<NotificationDto>();

		for (Notification notification : notifications) {
			NotificationDto ndto = new NotificationDto();
			ndto.setId(notification.getId() + "");
			ndto.setNotificationHeader("Ticket #" + notification.getWorkflowTransitionInstance().getTicket().getTicketNumber());
			ndto.setNotificationMessage(notification.getMessage().isEmpty() ? notification.getWorkflowTransitionViolated().getErrorMessage() : notification.getMessage());
			ndto.setViolationType(notification.getViolationType());
			notificationsDto.add(ndto);
		}

		NotificationListDto notificationListDto = new NotificationListDto();
		notificationListDto.setNotificationListDto(notificationsDto);
		return Response.status(200).entity(notificationListDto).build();

	}

	@GET
	@Path("/count")
	public Response getNotificationTotalCount(@HeaderParam("Authorization") String authorization, @QueryParam("spaceid") String spaceId, @QueryParam("violation_type") String violationType) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		if (violationType == null) {
			violationType = "";
		}
		NotificationCountDto notificationCountDto = new NotificationCountDto();
		notificationCountDto.setNotificationCount(notificationService.getNotificationCount(valResult.getUser().getSpaces(), spaceId, violationType));
		return Response.status(200).entity(notificationCountDto).build();

	}

	@GET
	@Path("/export")
	public Response exportNotifications(@HeaderParam("Authorization") String authorization, @QueryParam("spaceid") String spaceId, @QueryParam("violation_type") String violationType) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		if (violationType == null) {
			violationType = "";
		}
		List<Notification> notifications = notificationService.getNotifications(valResult.getUser().getSpaces(), -1, 0, spaceId, violationType);

		String[] priorityLabel = { "Highest", "High", "Normal", "Low", "Lowest" };

		StringBuilder sb = new StringBuilder();
		sb.append("date_updated;violation_type;ticket_number;milestone;priority;user;ticket_type").append(System.lineSeparator());
		for (Notification notification : notifications) {
			sb.append(notification.getDateCreated()).append(";");
			sb.append(notification.getViolationType()).append(";");
			sb.append(notification.getTicket().getTicketNumber()).append(";");
			sb.append(notification.getTicket().getMilestone() != null ? notification.getTicket().getMilestone().getTitle() : "").append(";");
			sb.append(priorityLabel[notification.getTicket().getPriorityTypeId() - 1]).append(";");
			sb.append(notification.getWorkflowTransitionInstance().getWorkflowTransition().getWorkflow().getName()).append(System.lineSeparator());
		}

		return Response.status(200).entity(sb.toString()).build();
	}
}
