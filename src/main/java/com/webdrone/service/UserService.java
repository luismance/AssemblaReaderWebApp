package com.webdrone.service;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.webdrone.model.User;
import com.webdrone.util.UserAuthResult;

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

	public UserAuthResult validateUserAuthorization(String authorization) {
		authorization = authorization.split("Basic ")[1];
		String credentials = new String(Base64.getDecoder().decode(authorization), Charset.forName("UTF-8"));
		final String[] values = credentials.split(":", 2);

		String username = values[0];
		String password = values[1];
		User user = findUserByUsername(username);
		
		if (user == null) {
			UserAuthResult res = new UserAuthResult(500, "Username not found", null);
			return res;
		}

		if (!user.getPassword().equals(password)) {
			UserAuthResult res = new UserAuthResult(401, "Username and password does not match!", null);
			return res;
		}

		UserAuthResult res = new UserAuthResult(200, "Success!", user);
		return res;
	}
}
