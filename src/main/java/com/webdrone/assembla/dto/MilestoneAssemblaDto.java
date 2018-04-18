package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.webdrone.util.DateAdapter;
import com.webdrone.util.DoubleAdapter;

@XmlRootElement(name = "milestone")
public class MilestoneAssemblaDto {

	private String id;

	private DateTime startDate;

	private DateTime dueDate;

	private Double budget;

	private String title;

	private String userId;

	private DateTime createdAt;

	private String createdBy;

	private String spaceId;

	private String description;

	private boolean completed;

	private DateTime completedDate;

	private DateTime updatedAt;

	private String updatedBy;

	private String releaseLevel;

	private String releaseNotes;

	private int plannerType;

	private String prettyReleaseLevel;

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "start-date")
	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "due-date")
	public DateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(DateTime dueDate) {
		this.dueDate = dueDate;
	}
	
	@XmlJavaTypeAdapter(DoubleAdapter.class)
	@XmlElement(nillable = true)
	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement(name = "user-id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "created-at")
	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	@XmlElement(name = "created-by")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@XmlElement(name = "space-id")
	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "is-completed")
	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "completed-date")
	public DateTime getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(DateTime completedDate) {
		this.completedDate = completedDate;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	@XmlElement(name = "updated-at")
	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlElement(name = "updated-by")
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@XmlElement(name = "release-level")
	public String getReleaseLevel() {
		return releaseLevel;
	}

	public void setReleaseLevel(String releaseLevel) {
		this.releaseLevel = releaseLevel;
	}

	@XmlElement(name = "release-notes")
	public String getReleaseNotes() {
		return releaseNotes;
	}

	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}

	@XmlElement(name = "planner-type")
	public int getPlannerType() {
		return plannerType;
	}

	public void setPlannerType(int plannerType) {
		this.plannerType = plannerType;
	}

	@XmlElement(name = "pretty-release-level")
	public String getPrettyReleaseLevel() {
		return prettyReleaseLevel;
	}

	public void setPrettyReleaseLevel(String prettyReleaseLevel) {
		this.prettyReleaseLevel = prettyReleaseLevel;
	}

}
