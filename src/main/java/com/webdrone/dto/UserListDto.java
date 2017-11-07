package com.webdrone.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
public class UserListDto {

	private List<UserDto> userDtos = new ArrayList<UserDto>();

	@XmlElement
	public List<UserDto> getUserDtos() {
		return userDtos;
	}

	public void setUserDtos(List<UserDto> userDtos) {
		this.userDtos = userDtos;
	}

}
