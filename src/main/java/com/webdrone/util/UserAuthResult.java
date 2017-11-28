package com.webdrone.util;

import com.webdrone.model.User;

public class UserAuthResult {

	private int responseCode;

	private String responseMessage;

	private User user;

	public UserAuthResult(int responseCode, String responseMessage, User user) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.user = user;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
