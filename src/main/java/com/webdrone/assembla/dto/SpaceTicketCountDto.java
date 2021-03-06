package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "spaceTicketCount")
public class SpaceTicketCountDto {

	private long ticketCount;

	private String syncStatus;

	@XmlElement(name = "count")
	public long getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(long ticketCount) {
		this.ticketCount = ticketCount;
	}

	@XmlElement(name = "sync_status")
	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

}
