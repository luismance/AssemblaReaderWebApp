package com.webdrone.main;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response getNotifications(@HeaderParam("Authorization") String authorization) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		List<Notification> notifications = notificationService.getNotifications(valResult.getUser().getSpaces());

		List<NotificationDto> notificationsDto = new ArrayList<NotificationDto>();

		for (Notification notification : notifications) {
			NotificationDto ndto = new NotificationDto();
			ndto.setId(notification.getId() + "");
			ndto.setNotificationHeader("Ticket #" + notification.getWorkflowTransitionInstance().getTicket().getTicketNumber());
			ndto.setNotificationMessage(notification.getWorkflowTransitionViolated().getErrorMessage());
			notificationsDto.add(ndto);
		}

		NotificationListDto notificationListDto = new NotificationListDto();
		notificationListDto.setNotificationListDto(notificationsDto);
		return Response.status(200).entity(notificationListDto).build();

	}
}