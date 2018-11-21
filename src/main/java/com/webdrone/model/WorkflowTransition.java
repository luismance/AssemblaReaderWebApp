package com.webdrone.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WORKFLOW_TRANSITION")
public class WorkflowTransition extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WORKFLOW_ID")
	private Workflow workflow;

	@Column(name = "EXPRESSION_LANGUAGE", columnDefinition = "TEXT", nullable = false)
	private String expressionLanguage;

	@Column(name = "ERROR_CODE", length = 255, nullable = false)
	private String errorCode;

	@Column(name = "ERROR_MESSAGE", length = 255, nullable = false)
	private String errorMessage;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "workflow_transition_parent_link", joinColumns = @JoinColumn(name = "workflow_transition_id"), inverseJoinColumns = @JoinColumn(name = "parent_workflow_transition_id"))
	private List<WorkflowTransition> workflowTransitions = new ArrayList<WorkflowTransition>();

	@ManyToMany(mappedBy = "workflowTransitions")
	private List<WorkflowTransition> parentWorkflowTransitions = new ArrayList<WorkflowTransition>();

	@Column(name = "IS_START")
	private boolean isFirstStep = false;

	@Column(name = "VIOLATION_TYPE")
	private String violationType;

	@Column(name = "MAX_DELAY")
	private long maxDelay;

	@Column(name = "PRIORITY_TYPE_ID")
	private int priorityTypeId;

	public WorkflowTransition() {
		super();
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public String getExpressionLanguage() {
		return expressionLanguage;
	}

	public void setExpressionLanguage(String expressionLanguage) {
		this.expressionLanguage = expressionLanguage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<WorkflowTransition> getWorkflowTransitions() {
		return workflowTransitions;
	}

	public void setWorkflowTransitions(List<WorkflowTransition> workflowTransitions) {
		this.workflowTransitions = workflowTransitions;
	}

	public List<WorkflowTransition> getParentWorkflowTransitions() {
		return parentWorkflowTransitions;
	}

	public void setParentWorkflowTransitions(List<WorkflowTransition> parentWorkflowTransitions) {
		this.parentWorkflowTransitions = parentWorkflowTransitions;
	}

	public boolean isFirstStep() {
		return isFirstStep;
	}

	public void setFirstStep(boolean isFirstStep) {
		this.isFirstStep = isFirstStep;
	}

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public long getMaxDelay() {
		return maxDelay;
	}

	public void setMaxDelay(long maxDelay) {
		this.maxDelay = maxDelay;
	}

	public int getPriorityTypeId() {
		return priorityTypeId;
	}

	public void setPriorityTypeId(int priorityTypeId) {
		this.priorityTypeId = priorityTypeId;
	}

}
