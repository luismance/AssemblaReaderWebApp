package com.webdrone.service;

import java.util.List;

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

	public User findUserByUsername(String username) {
		List<User> listResult = getEntityManager()
				.createQuery("SELECT u FROM " + User.class.getSimpleName() + " u WHERE u.username = '" + username + "'",
						User.class)
				.getResultList();

		if (listResult != null && listResult.size() > 0) {
			return listResult.get(0);
		}
		return null;
	}

	public User findUserByUsernameAndPassword(String username, String password) {
		User result = (User) getEntityManager().createQuery("SELECT u FROM " + User.class.getSimpleName()
				+ " u WHERE u.username = '" + username + "' AND u.password = '" + password + "'", User.class)
				.getSingleResult();
		return result;
	}
}
