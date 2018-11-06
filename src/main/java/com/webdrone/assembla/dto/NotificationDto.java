package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "notification")
public class NotificationDto {

	private String Id;

	private String notificationHeader;

	private String notificationMessage;

	@XmlElement(name = "id")
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	@XmlElement(name = "header")
	public String getNotificationHeader() {
		return notificationHeader;
	}

	public void setNotificationHeader(String notificationHeader) {
		this.notificationHeader = notificationHeader;
	}

	@XmlElement(name = "message")
	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

}
