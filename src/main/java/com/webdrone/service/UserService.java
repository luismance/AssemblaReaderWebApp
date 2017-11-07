package com.webdrone.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.webdrone.model.User;

@Stateless
public class UserService extends BaseService<User> {

	public UserService() {
	}

	public UserService(EntityManager em) {
		this.setEntityManager(em);
	}
}
