package com.webdrone.service;

import javax.ejb.Stateless;

import com.webdrone.model.Milestone;

@Stateless
public class MilestoneService extends BaseService<Milestone> {

	public long getMilestoneCountBySpace(String spaceId) {

		return Long.parseLong(
				getEntityManager().createQuery("SELECT COUNT(t.id) FROM " + Milestone.class.getSimpleName() + " t WHERE t.space.externalRefId = '" + spaceId + "'").getSingleResult().toString());
	}
}
