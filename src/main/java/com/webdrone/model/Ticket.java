package com.webdrone.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.webdrone.assembla.dto.CustomFieldAssemblaDto;
import com.webdrone.assembla.dto.TicketAssemblaDto;

@Entity
@Table(name = "TICKET")
public class Ticket extends RemoteEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SPACE_ID", nullable = false)
	private Space space;

	@Column(name = "TICKET_NUMBER")
	private Integer ticketNumber;

	@Column(name = "SUMMARY", columnDefinition = "TEXT")
	private String summary;

	@Lob
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
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

	@ManyToOne(optional = true)
	@JoinColumn(name = "REPORTER_ID")
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

	@ManyToOne(optional = true)
	@JoinColumn(name = "WORKFLOW_ID", nullable = true)
	private Workflow workflow;

	public Ticket() {
		super();
	}

	public Ticket(TicketAssemblaDto ticketAssemblaDto, Space space, Milestone milestone, User reporter, User assignedTo, Workflow workflow) {
		super();
		this.ticketNumber = ticketAssemblaDto.getNumber();
		this.summary = ticketAssemblaDto.getSummary();
		this.description = ticketAssemblaDto.getDescription();
		this.status = ticketAssemblaDto.getStatus();
		this.priorityTypeId = ticketAssemblaDto.getPriority();
		this.totalWorkingHours = ticketAssemblaDto.getTotalWorkingHours();
		this.isStory = ticketAssemblaDto.isStory();
		this.storyImportance = ticketAssemblaDto.getStoryImportance();
		this.totalInvestedHours = ticketAssemblaDto.getTotalInvestedHours();
		this.totalEstimate = ticketAssemblaDto.getTotalEstimate();
		this.workingHours = ticketAssemblaDto.getWorkingHours();
		this.importance = ticketAssemblaDto.getImportance();
		this.completedDate = ticketAssemblaDto.getCompletedDate() != null ? ticketAssemblaDto.getCompletedDate().toDate() : null;
		this.space = space;
		this.milestone = milestone;
		this.reporter = reporter;
		this.assignedTo = assignedTo;
		this.workflow = workflow;
		this.setExternalRefId(ticketAssemblaDto.getId());
		this.setRemotelyCreated(ticketAssemblaDto.getCreatedOn() != null ? ticketAssemblaDto.getCreatedOn().toDate() : new Date());
		this.setRemotelyUpdated(ticketAssemblaDto.getUpdatedAt() != null ? ticketAssemblaDto.getUpdatedAt().toDate() : new Date());
	}

	public TicketAssemblaDto toDto() {
		TicketAssemblaDto dto = new TicketAssemblaDto();
		dto.setAssignedToId(assignedTo != null ? this.assignedTo.getId().toString() : null);
		dto.setAsssignedToName(assignedTo != null ? this.getAssignedTo().getName() : null);
		dto.setCompletedDate(this.getCompletedDate() != null ? new DateTime(this.getCompletedDate()) : null);
		dto.setCreatedOn(this.getRemotelyCreated() != null ? new DateTime(this.getRemotelyCreated()) : null);
		dto.setDescription(this.getDescription());
		dto.setEstimate(this.getEstimate());
		dto.setId(this.getExternalRefId());
		dto.setImportance(this.getImportance());
		dto.setMilestoneId(this.getMilestone() != null ? this.getMilestone().getId().toString() : null);
		dto.setNumber(this.ticketNumber);
		dto.setPriority(this.priorityTypeId);
		dto.setReporterId(this.reporter != null ? this.reporter.getId().toString() : null);
		dto.setSpaceId(this.space != null ? this.space.getExternalRefId() : null);
		dto.setStatus(this.status);
		dto.setStory(this.isStory);
		dto.setStoryImportance(this.storyImportance);
		dto.setSummary(this.summary);
		dto.setTotalEstimate(this.totalEstimate);
		dto.setTotalInvestedHours(this.totalInvestedHours);
		dto.setTotalWorkingHours(this.totalWorkingHours);
		dto.setUpdatedAt(new DateTime(this.getRemotelyUpdated()));
		dto.setWorkingHours(this.workingHours);
		CustomFieldAssemblaDto customFieldDto = new CustomFieldAssemblaDto();
		customFieldDto.setType(this.getWorkflow() != null ? this.getWorkflow().getName() : "");
		dto.setCustomFields(customFieldDto);

		return dto;
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
