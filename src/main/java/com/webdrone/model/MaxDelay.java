package com.webdrone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MAX_DELAY")
public class MaxDelay extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "PRIORITY_TYPE_ID", nullable = false)
	private String priorityTypeId;

	@Column(name = "EXPRESSION_LANGUAGE", columnDefinition = "TEXT", nullable = false)
	private String expressionLanguage;

	@Column(name = "MAX_DELAY")
	private long maxDelay;

	public MaxDelay() {
		super();
	}

	public String getPriorityTypeId() {
		return priorityTypeId;
	}

	public void setPriorityTypeId(String priorityTypeId) {
		this.priorityTypeId = priorityTypeId;
	}

	public String getExpressionLanguage() {
		return expressionLanguage;
	}

	public void setExpressionLanguage(String expressionLanguage) {
		this.expressionLanguage = expressionLanguage;
	}

	public long getMaxDelay() {
		return maxDelay;
	}

	public void setMaxDelay(long maxDelay) {
		this.maxDelay = maxDelay;
	}

}
