package com.webdrone.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.webdrone.model.Workflow;
import com.webdrone.model.WorkflowTransition;

@Stateless
public class WorkflowTransitionService extends BaseService<WorkflowTransition> {

	public List<WorkflowTransition> getStartingWorkflowTransitions(EntityManager em, Workflow workflow, int priorityTypeId) {
		TypedQuery<WorkflowTransition> query = em.createQuery(
				"SELECT wt FROM " + WorkflowTransition.class.getSimpleName() + " wt WHERE wt.workflow.id= " + workflow.getId() + " AND wt.isFirstStep=true AND wt.priorityTypeId=" + priorityTypeId,
				WorkflowTransition.class);

		return query.getResultList();
	}

}
