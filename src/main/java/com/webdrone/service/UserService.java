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

	public User findUserByUsernameAndPassword(String username, String password) {
		System.out.println(username+":"+password);
		User result = (User) getEntityManager().createQuery("SELECT u FROM " + User.class.getSimpleName()
				+ " u WHERE u.username = '" + username + "' AND u.password = '" + password + "'", User.class)
				.getSingleResult();
		return result;
	}
}
