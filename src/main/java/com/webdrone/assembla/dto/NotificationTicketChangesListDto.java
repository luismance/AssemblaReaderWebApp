package com.webdrone.assembla.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "notification-changes")
public class NotificationTicketChangesListDto {
	
	private List<NotificationTicketChangeDto> notificationListDto = new ArrayList<NotificationTicketChangeDto>();

	@XmlElement(name = "notification-change")
	public List<NotificationTicketChangeDto> getNotificationListDto() {
		return notificationListDto;
	}

	public void setNotificationListDto(List<NotificationTicketChangeDto> notificationListDto) {
		this.notificationListDto = notificationListDto;
	}
}
