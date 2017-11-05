package com.webdrone.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "WORKFLOW")
public class Workflow extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 255, nullable = false)
	private String name;

	@OneToMany(mappedBy = "WORKFLOW")
	private List<WorkflowInstance> workflowInstances = new ArrayList<WorkflowInstance>();

	public Workflow() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WorkflowInstance> getWorkflowInstances() {
		return workflowInstances;
	}

	public void setWorkflowInstances(List<WorkflowInstance> workflowInstances) {
		this.workflowInstances = workflowInstances;
	}

}
