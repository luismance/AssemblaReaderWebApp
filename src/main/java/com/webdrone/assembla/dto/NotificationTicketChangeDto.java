package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.webdrone.util.DateAdapter;

@XmlRootElement(name = "notification-change")
public class NotificationTicketChangeDto {

	private String fieldName;

	private String originState;

	private String targetState;

	private DateTime updateDateTime;

	private String violationMessage;

	@XmlElement(name = "field-name")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@XmlElement(name = "origin-state")
	public String getOriginState() {
		return originState;
	}

	public void setOriginState(String originState) {
		this.originState = originState;
	}

	@XmlElement(name = "target-state")
	public String getTargetState() {
		return targetState;
	}

	public void setTargetState(String targetState) {
		this.targetState = targetState;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "updated-at")
	public DateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(DateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@XmlElement(name = "violation-message")
	public String getViolationMessage() {
		return violationMessage;
	}

	public void setViolationMessage(String violationMessage) {
		this.violationMessage = violationMessage;
	}

}
