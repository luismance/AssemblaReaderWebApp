package com.webdrone.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.webdrone.model.User;

@XmlRootElement(name = "user")
public class UserDto extends BaseDto {

	private String username;

	private String password;

	private String externalRefId;

	private String bearerToken;

	private String refreshToken;

	private String name;

	private String email;

	private String phoneNum;

	public UserDto() {
	}

	public UserDto(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.externalRefId = user.getExternalRefId();
		this.bearerToken = user.getBearerToken();
		this.refreshToken = user.getRefreshToken();
		this.name = user.getName();
		this.email = user.getEmail();
		this.phoneNum = user.getPhoneNum();
		setId(user.getId());
		setCreated(user.getDateCreated());
		setUpdated(user.getDateUpdated());
	}

	public UserDto(String username, String password, String externalRefId, String bearerToken, String refreshToken,
			String name, String email, String phoneNum) {
		super();
		this.username = username;
		this.password = password;
		this.externalRefId = externalRefId;
		this.bearerToken = bearerToken;
		this.refreshToken = refreshToken;
		this.name = name;
		this.email = email;
		this.phoneNum = phoneNum;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement
	public String getExternalRefId() {
		return externalRefId;
	}

	public void setExternalRefId(String externalRefId) {
		this.externalRefId = externalRefId;
	}

	@XmlElement
	public String getBearerToken() {
		return bearerToken;
	}

	public void setBearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
	}

	@XmlElement
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
