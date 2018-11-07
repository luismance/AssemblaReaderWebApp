package com.webdrone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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

}
