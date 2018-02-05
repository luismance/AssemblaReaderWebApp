package com.webdrone.assembla.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name = "ticket")
public class TicketAssemblaDto {

	private String id;

	private int number;

	private String summary;

	private String description;

	private int priority;

	private DateTime completedDate;

	private String componentId;

	private DateTime createdOn;

	private int permissionType;

	private float importance;

	private boolean story;

	private String milestoneId;

	private String notificationList;

	private String spaceId;

	private int state;

	private String status;

	private int storyImportance;

	private DateTime updatedAt;

	private float workingHours;

	private float estimate;

	private float totalEstimate;

	private float totalInvestedHours;

	private float totalWorkingHours;

	private String assignedToId;

	private String asssignedToName;

	private String reporterId;

	private CustomFieldAssemblaDto customFields;

	private int hierarchyType;

	private DateTime dueDate;

	private String pictureUrl;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@XmlElement(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "priority")
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@XmlElement(name = "completed-date")
	public DateTime getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(DateTime completedDate) {
		this.completedDate = completedDate;
	}

	@XmlElement(name = "component-id")
	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	@XmlElement(name = "created-on")
	public DateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(DateTime createdOn) {
		this.createdOn = createdOn;
	}

	@XmlElement(name = "permission-type")
	public int getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}

	@XmlElement(name = "importance")
	public float getImportance() {
		return importance;
	}

	public void setImportance(float importance) {
		this.importance = importance;
	}

	@XmlElement(name = "is-story")
	public boolean isStory() {
		return story;
	}

	public void setStory(boolean story) {
		this.story = story;
	}

	@XmlElement(name = "milestone-id")
	public String getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(String milestoneId) {
		this.milestoneId = milestoneId;
	}

	@XmlElement(name = "notification-list")
	public String getNotificationList() {
		return notificationList;
	}

	public void setNotificationList(String notificationList) {
		this.notificationList = notificationList;
	}

	@XmlElement(name = "space-id")
	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	@XmlElement(name = "state")
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@XmlElement(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@XmlElement(name = "story-importance")
	public int getStoryImportance() {
		return storyImportance;
	}

	public void setStoryImportance(int storyImportance) {
		this.storyImportance = storyImportance;
	}

	@XmlElement(name = "updated-at")
	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlElement(name = "working-hours")
	public float getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(float workingHours) {
		this.workingHours = workingHours;
	}

	@XmlElement(name = "estimate")
	public float getEstimate() {
		return estimate;
	}

	public void setEstimate(float estimate) {
		this.estimate = estimate;
	}

	@XmlElement(name = "total-estimate")
	public float getTotalEstimate() {
		return totalEstimate;
	}

	public void setTotalEstimate(float totalEstimate) {
		this.totalEstimate = totalEstimate;
	}

	@XmlElement(name = "total-invested-hours")
	public float getTotalInvestedHours() {
		return totalInvestedHours;
	}

	public void setTotalInvestedHours(float totalInvestedHours) {
		this.totalInvestedHours = totalInvestedHours;
	}

	@XmlElement(name = "total-working-hours")
	public float getTotalWorkingHours() {
		return totalWorkingHours;
	}

	public void setTotalWorkingHours(float totalWorkingHours) {
		this.totalWorkingHours = totalWorkingHours;
	}

	@XmlElement(name = "assigned-to-id")
	public String getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(String assignedToId) {
		this.assignedToId = assignedToId;
	}

	@XmlElement(name = "assigned-to-name")
	public String getAsssignedToName() {
		return asssignedToName;
	}

	public void setAsssignedToName(String asssignedToName) {
		this.asssignedToName = asssignedToName;
	}

	@XmlElement(name = "reporter-id")
	public String getReporterId() {
		return reporterId;
	}

	public void setReporterId(String reporterId) {
		this.reporterId = reporterId;
	}

	@XmlElement(name = "custom-fields")
	public CustomFieldAssemblaDto getCustomFields() {
		return customFields;
	}

	public void setCustomFields(CustomFieldAssemblaDto customFields) {
		this.customFields = customFields;
	}

	@XmlElement(name = "hierarchy-type")
	public int getHierarchyType() {
		return hierarchyType;
	}

	public void setHierarchyType(int hierarchyType) {
		this.hierarchyType = hierarchyType;
	}

	@XmlElement(name = "due-date")
	public DateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(DateTime dueDate) {
		this.dueDate = dueDate;
	}

	@XmlElement(name = "picture-url")
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

}
