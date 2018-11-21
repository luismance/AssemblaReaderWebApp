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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;

import com.webdrone.assembla.dto.NotificationCountDto;
import com.webdrone.assembla.dto.NotificationDto;
import com.webdrone.assembla.dto.NotificationListDto;
import com.webdrone.assembla.dto.NotificationTicketChangeDto;
import com.webdrone.assembla.dto.NotificationTicketChangesListDto;
import com.webdrone.assembla.dto.NotificationTicketDto;
import com.webdrone.model.Notification;
import com.webdrone.model.WorkflowTransitionInstance;
import com.webdrone.service.NotificationService;
import com.webdrone.service.UserService;
import com.webdrone.service.WorkflowTransitionInstanceService;
import com.webdrone.util.UserAuthResult;

@Path("/notification")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class NotificationRestService {

	@Inject
	private UserService userService;

	@Inject
	private NotificationService notificationService;

	@Inject
	private WorkflowTransitionInstanceService wtiService;

	@GET
	@Path("/list")
	public Response getNotifications(@HeaderParam("Authorization") String authorization, @QueryParam("per_page") int notifsPerPage, @QueryParam("page") int page, @QueryParam("spaceid") String spaceId,
			@QueryParam("violation_type") String violationType, @QueryParam("verify_filter") String verifyFilter) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		if (violationType == null) {
			violationType = "";
		}

		if (verifyFilter == null) {
			verifyFilter = "";
		}

		List<Notification> notifications = notificationService.getNotifications(valResult.getUser().getSpaces(), page - 1, notifsPerPage, spaceId, violationType, verifyFilter);

		List<NotificationDto> notificationsDto = new ArrayList<NotificationDto>();

		for (Notification notification : notifications) {
			NotificationDto ndto = new NotificationDto();
			ndto.setId(notification.getId() + "");
			ndto.setVerified(notification.isVerified());
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
	public Response getNotificationTotalCount(@HeaderParam("Authorization") String authorization, @QueryParam("spaceid") String spaceId, @QueryParam("violation_type") String violationType,
			@QueryParam("verify_filter") String verifyFilter) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		if (violationType == null) {
			violationType = "";
		}
		NotificationCountDto notificationCountDto = new NotificationCountDto();
		notificationCountDto.setNotificationCount(notificationService.getNotificationCount(valResult.getUser().getSpaces(), spaceId, violationType, verifyFilter));
		return Response.status(200).entity(notificationCountDto).build();

	}

	@GET
	@Path("/export")
	public Response exportNotifications(@HeaderParam("Authorization") String authorization, @QueryParam("spaceid") String spaceId, @QueryParam("violation_type") String violationType,
			@QueryParam("verify_filter") String verifyFilter) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		if (violationType == null) {
			violationType = "";
		}
		List<Notification> notifications = notificationService.getNotifications(valResult.getUser().getSpaces(), -1, 0, spaceId, violationType, verifyFilter);

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

	@POST
	@Path("/verify")
	public Response verifyNotification(@HeaderParam("Authorization") String authorization, @QueryParam("notification_id") String notificationId) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		Notification notification = (Notification) notificationService.find(Notification.class, Long.parseLong(notificationId));
		if (notification.isVerified()) {
			notification.setVerified(false);
		} else {
			notification.setVerified(true);
		}

		notificationService.update(notification);

		return Response.status(200).entity("SUCCESS").build();
	}

	@GET
	@Path("/details")
	public Response loadNotification(@HeaderParam("Authorization") String authorization, @QueryParam("notification_id") String notificationId) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		Notification notification = (Notification) notificationService.find(Notification.class, Long.parseLong(notificationId));

		List<WorkflowTransitionInstance> wtiList = wtiService.getWorkflowTransitionInstanceBySpaceAndTicket(notification.getSpace().getExternalRefId(),
				notification.getTicket().getTicketNumber() + "");
		NotificationTicketDto dto = new NotificationTicketDto();
		dto.setDescription(notification.getTicket().getDescription());
		dto.setPriority(notification.getTicket().getPriorityTypeId());
		dto.setSummary(notification.getTicket().getSummary());
		dto.setNumber(notification.getTicket().getTicketNumber());
		List<NotificationTicketChangeDto> notificationListDto = new ArrayList<NotificationTicketChangeDto>();

		for (WorkflowTransitionInstance wti : wtiList) {
			NotificationTicketChangeDto ntcd = new NotificationTicketChangeDto();
			ntcd.setFieldName(wti.getOriginState().split(":")[0]);
			ntcd.setOriginState(wti.getOriginState().split(":")[1]);
			ntcd.setTargetState(wti.getTargetState().split(":")[1]);
			ntcd.setUpdateDateTime(new DateTime(wti.getRemotelyUpdated()));

			Notification notifMessage = notificationService.getByWorkflowTransitionInstance(wti.getId());
			ntcd.setViolationMessage(notifMessage != null ? notifMessage.getMessage() : "");

			notificationListDto.add(ntcd);

		}

		NotificationTicketChangesListDto ntcl = new NotificationTicketChangesListDto();
		ntcl.setNotificationListDto(notificationListDto);

		dto.setNotificationTicketChangesList(ntcl);
		return Response.status(200).entity(dto).build();
	}
}
