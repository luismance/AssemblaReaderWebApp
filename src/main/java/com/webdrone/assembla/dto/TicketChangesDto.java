package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.webdrone.util.DateAdapter;

@XmlRootElement(name = "ticket-comment")
public class TicketChangesDto {

	private String id;

	private String comment;

	private String ticketId;

	private String userId;

	private DateTime createdOn;

	private DateTime updatedAt;

	private String ticketChanges;

	private String userName;

	private String userAvatarUrl;

	private boolean hasViolation;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@XmlElement(name = "ticket-id")
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	@XmlElement(name = "user-id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "created-on")
	public DateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(DateTime createdOn) {
		this.createdOn = createdOn;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "updated-at")
	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlElement(name = "ticket-changes")
	public String getTicketChanges() {
		return ticketChanges;
	}

	public void setTicketChanges(String ticketChanges) {
		this.ticketChanges = ticketChanges;
	}

	@XmlElement(name = "user-name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlElement(name = "user-avatar-url")
	public String getUserAvatarUrl() {
		return userAvatarUrl;
	}

	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}

	@XmlElement(name = "has-violation")
	public boolean isHasViolation() {
		return hasViolation;
	}

	public void setHasViolation(boolean hasViolation) {
		this.hasViolation = hasViolation;
	}

}
