package com.webdrone.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.webdrone.model.Notification;
import com.webdrone.model.Space;

@Stateless
public class NotificationService extends BaseService<Notification> {

	public List<Notification> getNotifications(Set<Space> spaces) {
		return getNotifications(getEntityManager(), spaces);
	}

	public List<Notification> getNotifications(EntityManager em, Set<Space> spaces) {

		StringBuilder spaceIds = new StringBuilder();
		for (Space space : spaces) {
			if (spaceIds.length() > 0) {
				spaceIds.append(",");
			}
			spaceIds.append("'").append(space.getExternalRefId()).append("'");
		}
		TypedQuery<Notification> query = em.createQuery("SELECT n FROM " + Notification.class.getSimpleName() + " n WHERE n.workflowTransitionInstance.ticket.space.externalRefId IN (" + spaceIds.toString() + ") ORDER BY n.dateCreated DESC", Notification.class);

		List<Notification> resultList = query.getResultList();
		return resultList;
	}
}
