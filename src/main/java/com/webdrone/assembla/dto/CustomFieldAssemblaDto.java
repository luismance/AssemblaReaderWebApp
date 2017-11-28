package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="custom-fields")
public class CustomFieldAssemblaDto {

	private String type;
	
	private String component;
	
	private String detectionVersion;
	
	private String pokerEstimation;
	
	private String redmine;

	@XmlElement(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name="component")
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	@XmlElement(name="Detection-version")
	public String getDetectionVersion() {
		return detectionVersion;
	}

	public void setDetectionVersion(String detectionVersion) {
		this.detectionVersion = detectionVersion;
	}

	@XmlElement(name="Poker-estimation")
	public String getPokerEstimation() {
		return pokerEstimation;
	}

	public void setPokerEstimation(String pokerEstimation) {
		this.pokerEstimation = pokerEstimation;
	}

	@XmlElement(name="redmine_")
	public String getRedmine() {
		return redmine;
	}

	public void setRedmine(String redmine) {
		this.redmine = redmine;
	}
	
	
}
