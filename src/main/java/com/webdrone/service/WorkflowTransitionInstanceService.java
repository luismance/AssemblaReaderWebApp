package com.webdrone.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.webdrone.model.WorkflowTransitionInstance;

@Stateless
public class WorkflowTransitionInstanceService extends BaseService<WorkflowTransitionInstance> {

	public List<WorkflowTransitionInstance> getWorkflowTransitionInstanceBySpaceAndTicket(String spaceId, String ticketNumber) {
		return getWorkflowTransitionInstanceBySpaceAndTicket(getEntityManager(), spaceId, ticketNumber);
	}

	public List<WorkflowTransitionInstance> getWorkflowTransitionInstanceBySpaceAndTicket(EntityManager em, String spaceId, String ticketNumber) {
		TypedQuery<WorkflowTransitionInstance> query = em.createQuery(
				"SELECT wti FROM " + WorkflowTransitionInstance.class.getSimpleName() + " wti WHERE wti.ticket.space.externalRefId = '" + spaceId + "' AND wti.ticket.ticketNumber = " + ticketNumber + " ORDER BY wti.remotelyCreated ASC",
				WorkflowTransitionInstance.class);

		return query.getResultList();
	}

	public long getTicketChangesCountBySpace(String spaceId) {

		return Long.parseLong(getEntityManager().createQuery("SELECT COUNT(wti.id) FROM " + WorkflowTransitionInstance.class.getSimpleName() + " wti WHERE wti.space.externalRefId = '" + spaceId + "'").getSingleResult().toString());
	}
}
