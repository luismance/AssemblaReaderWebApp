package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "spaceTicketChangesCount")
public class SpaceTicketChangesCountDto {

	private long ticketChangesCount;

	private String syncStatus;

	@XmlElement(name = "count")
	public long getTicketChangesCount() {
		return ticketChangesCount;
	}

	public void setTicketChangesCount(long ticketChangesCount) {
		this.ticketChangesCount = ticketChangesCount;
	}

	@XmlElement(name = "sync_status")
	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

}
