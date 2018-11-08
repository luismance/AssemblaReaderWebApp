package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "notificationCount")
public class NotificationCountDto {

	private long notificationCount;

	@XmlElement(name = "count")
	public long getNotificationCount() {
		return notificationCount;
	}

	public void setNotificationCount(long notificationCount) {
		this.notificationCount = notificationCount;
	}

}