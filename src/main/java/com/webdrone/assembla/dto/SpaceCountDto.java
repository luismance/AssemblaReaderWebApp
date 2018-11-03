package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "spaceCount")
public class SpaceCountDto {

	private long spaceCount;

	@XmlElement(name = "count")
	public long getSpaceCount() {
		return spaceCount;
	}

	public void setSpaceCount(long spaceCount) {
		this.spaceCount = spaceCount;
	}

}
