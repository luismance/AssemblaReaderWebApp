package com.webdrone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.webdrone.model.Notification;
import com.webdrone.model.Space;

@Stateless
public class NotificationService extends BaseService<Notification> {

	public List<Notification> getNotifications(Set<Space> spaces, int pageNum, int maxResult, String spaceId, String violationType, String verifyFilter) {
		return getNotifications(getEntityManager(), spaces, pageNum, maxResult, spaceId, violationType, verifyFilter);
	}

	public List<Notification> getNotifications(EntityManager em, Set<Space> spaces, int pageNum, int maxResult, String spaceId, String violationType, String verifyFilter) {

		if (spaces.size() > 0) {
			StringBuilder spaceIds = new StringBuilder();

			if (spaceId != null && spaceId.isEmpty()) {
				for (Space space : spaces) {
					if (spaceIds.length() > 0) {
						spaceIds.append(",");
					}
					spaceIds.append("'").append(space.getExternalRefId()).append("'");
				}
			} else {
				spaceIds.append("'").append(spaceId).append("'");
			}

			String verificationFilter = "";
			if (verifyFilter.equals("verified")) {
				verificationFilter = "true";
			} else if (verifyFilter.equals("not_verified")) {
				verificationFilter = "false";
			}

			String sqlQuery = "SELECT n FROM " + Notification.class.getSimpleName() + " n WHERE n.workflowTransitionInstance.ticket.space.externalRefId IN (" + spaceIds.toString() + ") "
					+ (!violationType.isEmpty() ? " AND n.violationType='" + violationType + "'" : "") + (!verificationFilter.isEmpty() ? " AND n.verified=" + verificationFilter : "")
					+ " ORDER BY n.dateCreated DESC";

			TypedQuery<Notification> query = em.createQuery(sqlQuery, Notification.class);

			if (pageNum > -1 && maxResult > 0) {
				query.setFirstResult(pageNum * maxResult).setMaxResults(maxResult);
			}
			List<Notification> resultList = query.getResultList();
			return resultList;
		}

		return new ArrayList<Notification>();
	}

	public long getNotificationCount(Set<Space> spaces, String spaceId, String violationType, String verifyFilter) {
		return getNotificationCount(getEntityManager(), spaces, spaceId, violationType, verifyFilter);
	}

	public long getNotificationCount(EntityManager em, Set<Space> spaces, String spaceId, String violationType, String verifyFilter) {

		if (spaces.size() > 0) {
			StringBuilder spaceIds = new StringBuilder();
			if (spaceId != null && spaceId.isEmpty()) {
				for (Space space : spaces) {
					if (spaceIds.length() > 0) {
						spaceIds.append(",");
					}
					spaceIds.append("'").append(space.getExternalRefId()).append("'");
				}
			} else {
				spaceIds.append("'").append(spaceId).append("'");
			}

			String verificationFilter = "";
			if (verifyFilter.equals("verified")) {
				verificationFilter = "true";
			} else if (verifyFilter.equals("not_verified")) {
				verificationFilter = "false";
			}

			return Long.parseLong(em
					.createQuery("SELECT COUNT(n.id) FROM " + Notification.class.getSimpleName() + " n WHERE n.workflowTransitionInstance.ticket.space.externalRefId IN (" + spaceIds.toString() + ") "
							+ (!violationType.isEmpty() ? " AND n.violationType='" + violationType + "'" : "") + (!verificationFilter.isEmpty() ? " AND n.verified=" + verificationFilter : ""))
					.getSingleResult().toString());
		}

		return 0;
	}
}
