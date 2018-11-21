package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ticket-notification")
public class NotificationTicketDto {

	private int number;

	private String description;

	private String summary;

	private int priority;

	private NotificationTicketChangesListDto notificationTicketChangesList = new NotificationTicketChangesListDto();

	@XmlElement(name = "number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@XmlElement(name = "priority")
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@XmlElement(name = "notification-changes")
	public NotificationTicketChangesListDto getNotificationTicketChangesList() {
		return notificationTicketChangesList;
	}

	public void setNotificationTicketChangesList(NotificationTicketChangesListDto notificationTicketChangesList) {
		this.notificationTicketChangesList = notificationTicketChangesList;
	}

}
