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

	public List<Notification> getNotifications(Set<Space> spaces, int pageNum, int maxResult) {
		return getNotifications(getEntityManager(), spaces, pageNum, maxResult);
	}

	public List<Notification> getNotifications(EntityManager em, Set<Space> spaces, int pageNum, int maxResult) {

		if (spaces.size() > 0) {
			StringBuilder spaceIds = new StringBuilder();
			for (Space space : spaces) {
				if (spaceIds.length() > 0) {
					spaceIds.append(",");
				}
				spaceIds.append("'").append(space.getExternalRefId()).append("'");
			}
			TypedQuery<Notification> query = em.createQuery("SELECT n FROM " + Notification.class.getSimpleName() + " n WHERE n.workflowTransitionInstance.ticket.space.externalRefId IN (" + spaceIds.toString() + ") ORDER BY n.dateCreated DESC",
					Notification.class);

			if (pageNum > -1 && maxResult > 0) {
				query.setFirstResult(pageNum * maxResult).setMaxResults(maxResult);
			}
			List<Notification> resultList = query.getResultList();
			return resultList;
		}

		return new ArrayList<Notification>();
	}

	public long getNotificationCount(Set<Space> spaces) {
		return getNotificationCount(getEntityManager(), spaces);
	}

	public long getNotificationCount(EntityManager em, Set<Space> spaces) {

		if (spaces.size() > 0) {
			StringBuilder spaceIds = new StringBuilder();
			for (Space space : spaces) {
				if (spaceIds.length() > 0) {
					spaceIds.append(",");
				}
				spaceIds.append("'").append(space.getExternalRefId()).append("'");
			}

			return Long.parseLong(em.createQuery("SELECT COUNT(n.id) FROM " + Notification.class.getSimpleName() + " n WHERE n.workflowTransitionInstance.ticket.space.externalRefId IN (" + spaceIds.toString() + ") ORDER BY n.dateCreated DESC")
					.getSingleResult().toString());
		}

		return 0;
	}
}
