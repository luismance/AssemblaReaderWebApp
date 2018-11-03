package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "spaceMilestoneCount")
public class SpaceMilestoneCountDto {
	private long milestoneCount;

	private String syncStatus;

	@XmlElement(name = "count")
	public long getMilestoneCount() {
		return milestoneCount;
	}

	public void setMilestoneCount(long milestoneCount) {
		this.milestoneCount = milestoneCount;
	}

	@XmlElement(name = "sync_status")
	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}
}
