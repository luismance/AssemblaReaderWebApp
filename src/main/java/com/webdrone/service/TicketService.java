package com.webdrone.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;

import com.webdrone.model.Space;
import com.webdrone.model.Ticket;

@Stateless
public class TicketService extends BaseService<Ticket> {

	public Ticket getTicketBySpaceAndNumber(EntityManager em, long spaceId, String ticketNumber) {
		List<Ticket> resultList = em.createQuery("SELECT t FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.id = '" + spaceId + "' AND t.ticketNumber = '" + ticketNumber + "'", Ticket.class)
				.getResultList();
		Ticket result = resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
		return result;
	}

	public Ticket getTicketBySpaceAndNumber(long spaceId, String ticketNumber) {
		return getTicketBySpaceAndNumber(getEntityManager(), spaceId, ticketNumber);
	}

	public List<Ticket> getTicketsBySpace(String spaceId, int limit, int page, String sortBy, String violation, String priority) {
		return getTicketsBySpace(getEntityManager(), spaceId, limit, page, sortBy, violation, priority);
	}

	public List<Ticket> getTicketsBySpace(EntityManager em, String spaceId, int limit, int page, String sortBy, String violation, String priority) {

		String queryString = "SELECT t FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.externalRefId = '" + spaceId + "'";

		if (!violation.isEmpty()) {

		}

		if (!priority.isEmpty() && priority != "0") {
			queryString += " AND t.priorityTypeId=" + priority;
		}

		if (!sortBy.isEmpty()) {
			String sortCol = sortBy.split("-")[0];
			String sortOrder = sortBy.split("-")[1];
			if (sortCol.equals("last_updated")) {
				queryString += " ORDER BY t.remotelyUpdated " + sortOrder;
			} else if (sortCol.equals("milestone")) {
				queryString += " ORDER BY t.milestone.title " + sortOrder;
			} else if (sortCol.equals("ticket_num")) {
				queryString += " ORDER BY t.ticketNumber " + sortOrder;
			} else if (sortCol.equals("priority")) {
				queryString += " ORDER BY t.priorityTypeId " + sortOrder;
			}
		}

		TypedQuery<Ticket> query = em.createQuery(queryString, Ticket.class);

		if (page > -1 && limit > 0) {
			query.setFirstResult(page * limit).setMaxResults(limit);
		}

		List<Ticket> resultList = query.getResultList();
		return resultList;
	}

	public long getTicketCountBySpace(String spaceId, String violation, String priority) {

		String queryString = "SELECT COUNT(t.id) FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.externalRefId = '" + spaceId + "'";
		if (!violation.isEmpty()) {

		}

		if (!priority.isEmpty() && priority != "0") {
			queryString += " AND t.priorityTypeId=" + priority;
		}
		return Long.parseLong(getEntityManager().createQuery(queryString).getSingleResult().toString());
	}

	public Date getLatestTicketUpdate(EntityManager em, Set<Space> spaces) throws ParseException {

		if (spaces.size() > 0) {
			StringBuilder spaceIds = new StringBuilder();

			for (Space space : spaces) {
				if (spaceIds.length() > 0) {
					spaceIds.append(",");
				}
				spaceIds.append("'").append(space.getExternalRefId()).append("'");
			}
			String queryString = "SELECT t.dateUpdated FROM " + Ticket.class.getSimpleName() + " t WHERE t.space.externalRefId IN(" + spaceIds.toString() + ") ORDER BY t.dateUpdated DESC";

			String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String dateToParse = em.createQuery(queryString).getResultList().size() > 0 ? em.createQuery(queryString).getResultList().get(0).toString() : "";
			if (dateToParse.isEmpty()) {
				return null;
			}
			return simpleDateFormat.parse(em.createQuery(queryString).getResultList().get(0).toString());
		}

		return null;

	}

}
