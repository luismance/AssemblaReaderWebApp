package com.webdrone.service;

import java.util.List;

import javax.ejb.Stateless;

import com.webdrone.model.Workflow;

@Stateless
public class WorkflowService extends BaseService<Workflow> {

	public Workflow getWorkflowByName(String name) {
		List<Workflow> resultList = getEntityManager()
				.createQuery("SELECT w FROM " + Workflow.class.getSimpleName() + " w WHERE w.name = '" + name + "'",
						Workflow.class)
				.getResultList();
		Workflow result = resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
		return result;
	}

}
