package com.webdrone.assembla.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "spaces")
public class SpaceListAssemblaDto {

	private List<SpaceAssemblaDto> spaceDtos = new ArrayList<SpaceAssemblaDto>();

	@XmlElement(name="space")
	public List<SpaceAssemblaDto> getSpaceDtos() {
		return spaceDtos;
	}

	public void setSpaceDtos(List<SpaceAssemblaDto> spaceDtos) {
		this.spaceDtos = spaceDtos;
	}

}
