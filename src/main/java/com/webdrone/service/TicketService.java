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

	public List<Ticket> getTicketsBySpace(long spaceId, String ticketNumber) {
		List<Ticket> resultList = getEntityManager().createQuery("SELECT t FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.id = '" + spaceId + "'", Ticket.class).getResultList();
		return resultList;
	}

}
