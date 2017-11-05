package com.webdrone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MAX_DELAY")
public class MaxDelay extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "EXPRESSION_LANGUAGE", length = 65535, nullable = false)
	private String expressionLanguage;

	@Column(name = "MAX_DELAY")
	private long maxDelay;

	public MaxDelay() {
		super();
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
