package com.webdrone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION")
public class Notification extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SPACE_ID", nullable = false)
	private Space space;

	@ManyToOne(optional = false)
	@JoinColumn(name = "TICKET_ID", nullable = false)
	private Ticket ticket;

	@ManyToOne(optional = false)
	@JoinColumn(name = "WORKFLOW_TRANSITION_INSTANCE_ID", nullable = false)
	private WorkflowTransitionInstance workflowTransitionInstance;

	@ManyToOne(optional = false)
	@JoinColumn(name = "WORKFLOW_TRANSITION_VIOLATED_ID", nullable = false)
	private WorkflowTransition workflowTransitionViolated;

	@Column(name = "MESSAGE", columnDefinition = "TEXT")
	private String message;

	@Column(name = "VIOLATION_TYPE")
	private String violationType;

	@Column(name = "IS_VERIFIED")
	private boolean verified = false;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CAUSED_BY_ID", nullable = false)
	private User causedBy;

	public Notification() {
		super();
	}

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public WorkflowTransitionInstance getWorkflowTransitionInstance() {
		return workflowTransitionInstance;
	}

	public void setWorkflowTransitionInstance(WorkflowTransitionInstance workflowTransitionInstance) {
		this.workflowTransitionInstance = workflowTransitionInstance;
	}

	public WorkflowTransition getWorkflowTransitionViolated() {
		return workflowTransitionViolated;
	}

	public void setWorkflowTransitionViolated(WorkflowTransition workflowTransitionViolated) {
		this.workflowTransitionViolated = workflowTransitionViolated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public User getCausedBy() {
		return causedBy;
	}

	public void setCausedBy(User causedBy) {
		this.causedBy = causedBy;
	}

}
