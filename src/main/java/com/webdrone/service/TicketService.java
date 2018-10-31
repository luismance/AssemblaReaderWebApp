package com.webdrone.service;

import java.util.List;

import javax.ejb.Stateless;

import com.webdrone.model.Ticket;

@Stateless
public class TicketService extends BaseService<Ticket> {

	public Ticket getTicketBySpaceAndNumber(long spaceId, String ticketNumber) {
		List<Ticket> resultList = getEntityManager()
				.createQuery("SELECT t FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.id = '" + spaceId + "' AND t.ticketNumber = '" + ticketNumber + "'", Ticket.class).getResultList();
		Ticket result = resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
		return result;
	}

	public List<Ticket> getTicketsBySpace(String spaceId) {
		List<Ticket> resultList = getEntityManager().createQuery("SELECT t FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.externalRefId = '" + spaceId + "'", Ticket.class).getResultList();
		return resultList;
	}

	public List<Ticket> getTicketsBySpace(String spaceId, int limit, int page) {
		List<Ticket> resultList = getEntityManager().createQuery("SELECT t FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.externalRefId = '" + spaceId + "'", Ticket.class)
				.setFirstResult(page * limit).setMaxResults(limit).getResultList();
		return resultList;
	}

	public long getTicketCountBySpace(String spaceId) {

		return Long.parseLong(
				getEntityManager().createQuery("SELECT COUNT(t.id) FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.externalRefId = '" + spaceId + "'").getSingleResult().toString());
	}

}
