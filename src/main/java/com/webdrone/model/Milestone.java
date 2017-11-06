package com.webdrone.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MILESTONE")
public class Milestone extends RemoteEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "PLANNER_TYPE")
	private Integer plannerType;

	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	@Column(name = "RELEASE_NOTES", length = 10000)
	private String releaseNotes;

	@Column(name = "PRETTY_RELEASE_LEVEL", length = 10000)
	private String prettyReleaseLevel;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CREATED_BY_ID", nullable = false)
	private User createdBy;

	@Column(name = "COMPLETED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completedDate;

	@Column(name = "DUE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	@Column(name = "IS_COMPLETED")
	private boolean isCompleted;

	@Column(name = "TITLE", length = 255)
	private String title;

	@ManyToOne(optional = false)
	@JoinColumn(name = "UPDATED_BY_ID", nullable = false)
	private User updatedBy;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SPACE_ID", nullable = false)
	private Space space;

	public Milestone(){
		super();
	}
	
	public Integer getPlannerType() {
		return plannerType;
	}

	public void setPlannerType(Integer plannerType) {
		this.plannerType = plannerType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReleaseNotes() {
		return releaseNotes;
	}

	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}

	public String getPrettyReleaseLevel() {
		return prettyReleaseLevel;
	}

	public void setPrettyReleaseLevel(String prettyReleaseLevel) {
		this.prettyReleaseLevel = prettyReleaseLevel;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

}
