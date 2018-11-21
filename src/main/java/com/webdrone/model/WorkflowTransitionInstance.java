package com.webdrone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WORKFLOW_TRANSITION_INSTANCE")
public class WorkflowTransitionInstance extends RemoteEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "TICKET_ID", nullable = false)
	private Ticket ticket;

	@Column(name = "MESSAGE", columnDefinition = "TEXT")
	private String message;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SPACE_ID", nullable = false)
	private Space space;

	@Column(name = "HAS_VIOLATION")
	private boolean hasViolation = false;

	@Column(name = "ORIGIN_STATE", columnDefinition = "TEXT")
	private String originState;

	@Column(name = "TARGET_STATE", columnDefinition = "TEXT")
	private String targetState;

	@ManyToOne(optional = true)
	@JoinColumn(name = "WORKFLOW_TRANSITION_ID", nullable = true)
	private WorkflowTransition workflowTransition;

	public WorkflowTransitionInstance() {
		super();
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public boolean isHasViolation() {
		return hasViolation;
	}

	public void setHasViolation(boolean hasViolation) {
		this.hasViolation = hasViolation;
	}

	public String getOriginState() {
		return originState;
	}

	public void setOriginState(String originState) {
		this.originState = originState;
	}

	public String getTargetState() {
		return targetState;
	}

	public void setTargetState(String targetState) {
		this.targetState = targetState;
	}

	public WorkflowTransition getWorkflowTransition() {
		return workflowTransition;
	}

	public void setWorkflowTransition(WorkflowTransition workflowTransition) {
		this.workflowTransition = workflowTransition;
	}

}
