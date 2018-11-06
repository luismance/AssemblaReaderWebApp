package com.webdrone.assembla.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "notifications")
public class NotificationListDto {

	private List<NotificationDto> notificationListDto = new ArrayList<NotificationDto>();

	@XmlElement(name = "notification")
	public List<NotificationDto> getNotificationListDto() {
		return notificationListDto;
	}

	public void setNotificationListDto(List<NotificationDto> notificationListDto) {
		this.notificationListDto = notificationListDto;
	}

}
