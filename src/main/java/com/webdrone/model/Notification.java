package com.webdrone.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION")
public class Notification extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "WORKFLOW_TRANSITION_INSTANCE_ID", nullable = false)
	private WorkflowTransitionInstance workflowTransitionInstance;

	@ManyToOne(optional = false)
	@JoinColumn(name = "WORKFLOW_TRANSITION_VIOLATED_ID", nullable = false)
	private WorkflowTransition workflowTransitionViolated;

	public Notification(){
		super();
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

}
