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
@Table(name = "TICKET")
public class Ticket extends RemoteEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SPACE_ID", nullable = false)
	private Space space;

	@Column(name = "TICKET_NUMBER")
	private Integer ticketNumber;

	@Column(name = "SUMMARY", length = 255)
	private String summary;

	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	@Column(name = "STATUS", length = 255)
	private String status;

	@Column(name = "PRIORITY_TYPE_ID")
	private int priorityTypeId;

	@Column(name = "TOTAL_WORKING_HOURS")
	private float totalWorkingHours;

	@ManyToOne
	@JoinColumn(name = "MILESTONE_ID")
	private Milestone milestone;

	@Column(name = "ESTIMATE")
	private float estimate;

	@Column(name = "IS_STORY")
	private boolean isStory;

	@ManyToOne(optional = false)
	@JoinColumn(name = "REPORTER_ID", nullable = false)
	private User reporter;

	@ManyToOne
	@JoinColumn(name = "ASSIGNED_TO_ID")
	private User assignedTo;

	@Column(name = "STORY_IMPORTANCE")
	private Integer storyImportance;

	@Column(name = "TOTAL_INVESTED_HOURS")
	private float totalInvestedHours;

	@Column(name = "TOTAL_ESTIMATE")
	private float totalEstimate;

	@Column(name = "WORKING_HOURS")
	private float workingHours;

	@Column(name = "IMPORTANCE")
	private float importance;

	@Column(name = "COMPLETED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completedDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "WORKFLOW_ID", nullable = false)
	private Workflow workflow;

	public Ticket(){
		super();
	}
	
	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public Integer getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriorityTypeId() {
		return priorityTypeId;
	}

	public void setPriorityTypeId(int priorityTypeId) {
		this.priorityTypeId = priorityTypeId;
	}

	public float getTotalWorkingHours() {
		return totalWorkingHours;
	}

	public void setTotalWorkingHours(float totalWorkingHours) {
		this.totalWorkingHours = totalWorkingHours;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public float getEstimate() {
		return estimate;
	}

	public void setEstimate(float estimate) {
		this.estimate = estimate;
	}

	public boolean isStory() {
		return isStory;
	}

	public void setStory(boolean isStory) {
		this.isStory = isStory;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Integer getStoryImportance() {
		return storyImportance;
	}

	public void setStoryImportance(Integer storyImportance) {
		this.storyImportance = storyImportance;
	}

	public float getTotalInvestedHours() {
		return totalInvestedHours;
	}

	public void setTotalInvestedHours(float totalInvestedHours) {
		this.totalInvestedHours = totalInvestedHours;
	}

	public float getTotalEstimate() {
		return totalEstimate;
	}

	public void setTotalEstimate(float totalEstimate) {
		this.totalEstimate = totalEstimate;
	}

	public float getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(float workingHours) {
		this.workingHours = workingHours;
	}

	public float getImportance() {
		return importance;
	}

	public void setImportance(float importance) {
		this.importance = importance;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

}
