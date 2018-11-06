package com.webdrone.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.webdrone.model.Space;

@Stateless
public class SpaceService extends BaseService<Space> {

	public SpaceService() {
		super();
	}

	public SpaceService(EntityManager em) {
		super();
		this.setEntityManager(em);
	}

}
