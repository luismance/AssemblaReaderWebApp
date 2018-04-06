package com.webdrone.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "WORKFLOW")
public class Workflow extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 255, nullable = false)
	private String name;

	@OneToMany(mappedBy = "workflow", fetch = FetchType.EAGER)
	private List<WorkflowTransition> workflowTransitions = new ArrayList<WorkflowTransition>();

	public Workflow() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WorkflowTransition> getWorkflowTransitions() {
		return workflowTransitions;
	}

	public void setWorkflowTransitions(List<WorkflowTransition> workflowTransitions) {
		this.workflowTransitions = workflowTransitions;
	}

}
