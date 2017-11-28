package com.webdrone.assembla.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "milestones")
public class MilestoneListAssemblaDto {

	private List<MilestoneAssemblaDto> milestones = new ArrayList<MilestoneAssemblaDto>();

	@XmlElement(name = "milestone")
	public List<MilestoneAssemblaDto> getMilestones() {
		return milestones;
	}

	public void setMilestones(List<MilestoneAssemblaDto> milestones) {
		this.milestones = milestones;
	}

}
