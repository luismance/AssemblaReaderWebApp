package com.webdrone.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "USERNAME", length = 255, nullable = false)
	private String username;

	@Column(name = "PASSWORD", length = 255, nullable = false)
	private String password;

	@Column(name = "EXTERNAL_REF_ID", length = 255, nullable = false)
	private String externalRefId;

	@Column(name = "BEARER_TOKEN", length = 255, nullable = false)
	private String bearerToken;

	@Column(name = "REFRESH_TOKEN", length = 255, nullable = false)
	private String refreshToken;

	@Column(name = "NAME", length = 255, nullable = false)
	private String name;

	@Column(name = "EMAIL", length = 255, nullable = false)
	private String email;

	@Column(name = "PHONE_NUM", length = 255, nullable = true)
	private String phoneNum;

	@OneToMany
	private List<Role> userRoles = new ArrayList<Role>();

	public User() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExternalRefId() {
		return externalRefId;
	}

	public void setExternalRefId(String externalRefId) {
		this.externalRefId = externalRefId;
	}

	public String getBearerToken() {
		return bearerToken;
	}

	public void setBearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public List<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
	}

}
