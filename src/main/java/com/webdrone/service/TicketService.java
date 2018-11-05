package com.webdrone.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.webdrone.model.Ticket;

@Stateless
public class TicketService extends BaseService<Ticket> {

	public Ticket getTicketBySpaceAndNumber(EntityManager em, long spaceId, String ticketNumber) {
		List<Ticket> resultList = em.createQuery("SELECT t FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.id = '" + spaceId + "' AND t.ticketNumber = '" + ticketNumber + "'", Ticket.class).getResultList();
		Ticket result = resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
		return result;
	}

	public Ticket getTicketBySpaceAndNumber(long spaceId, String ticketNumber) {
		return getTicketBySpaceAndNumber(getEntityManager(), spaceId, ticketNumber);
	}

	public List<Ticket> getTicketsBySpace(String spaceId, int limit, int page) {
		return getTicketsBySpace(getEntityManager(), spaceId, limit, page);
	}

	public List<Ticket> getTicketsBySpace(EntityManager em, String spaceId, int limit, int page) {
		TypedQuery<Ticket> query = em.createQuery("SELECT t FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.externalRefId = '" + spaceId + "'", Ticket.class);

		if (page > -1 && limit > 0) {
			query.setFirstResult(page * limit).setMaxResults(limit);
		}

		List<Ticket> resultList = query.getResultList();
		return resultList;
	}

	public long getTicketCountBySpace(String spaceId) {

		return Long.parseLong(getEntityManager().createQuery("SELECT COUNT(t.id) FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.externalRefId = '" + spaceId + "'").getSingleResult().toString());
	}

}
