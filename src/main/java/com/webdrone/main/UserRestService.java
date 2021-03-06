package com.webdrone.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webdrone.assembla.dto.UserAssemblaDto;
import com.webdrone.dto.RequestTokenObject;
import com.webdrone.dto.UserDto;
import com.webdrone.dto.UserListDto;
import com.webdrone.model.User;
import com.webdrone.service.UserService;
import com.webdrone.util.RESTServiceUtil;

@Path("/user")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class UserRestService {

	private static String REQUEST_REFRESH_TOKEN_URL = "https://api.assembla.com/token?grant_type=authorization_code&code=";

	@Inject
	private UserService userService;

	@POST
	@Path("/login")
	public Response loginUser(String requestBody) {

		try {

			UserDto userDto = (UserDto) RESTServiceUtil.unmarshaller(UserDto.class, requestBody);

			if (userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty()) {
				return Response.status(500).type(MediaType.TEXT_PLAIN).entity("Username or Password cannot be empty!").build();
			}

			User result = (User) userService.findUserByUsernameAndPassword(userDto.getUsername(), RESTServiceUtil.encryptToSHA256(userDto.getPassword()));

			if (result != null) {
				userDto = new UserDto(result);
				return Response.status(200).entity(userDto).build();
			} else {
				return Response.status(500).entity("Incorrect Username or Password").build();
			}

		} catch (EJBException e) {
			return Response.status(500).entity("Errror looking for account!").build();
		}

	}

	@POST
	@Path("/update")
	public Response updateUser(String requestBody) {

		try {

			UserDto userDto = (UserDto) RESTServiceUtil.unmarshaller(UserDto.class, requestBody);

			if (userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty()) {
				return Response.status(500).type(MediaType.TEXT_PLAIN).entity("Username or Password cannot be empty!").build();
			}

			User user = (User) userService.findUserByUsername(userDto.getUsername());
			if (user != null) {
				user.setEmail(userDto.getEmail());

				user.setPassword(RESTServiceUtil.encryptToSHA256(userDto.getPassword()));
				user.setName(userDto.getName());
				user.setPhoneNum(userDto.getPhoneNum());
				user.setUsername(userDto.getUsername());

				userService.update(user);

				userDto = new UserDto(user);
				return Response.status(200).entity(userDto).build();
			} else {
				return Response.status(500).entity("Username not found!").build();
			}

		} catch (EJBException e) {
			return Response.status(500).entity("Username not found!").build();
		}

	}

	@POST
	@Path("/updatePassword")
	public Response updatePassword(String requestBody) {

		try {

			UserDto userDto = (UserDto) RESTServiceUtil.unmarshaller(UserDto.class, requestBody);

			if (userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty()) {
				return Response.status(500).type(MediaType.TEXT_PLAIN).entity("Username or Password cannot be empty!").build();
			}

			User user = (User) userService.findUserByUsername(userDto.getUsername());
			if (user != null) {

				user.setPassword(RESTServiceUtil.encryptToSHA256(userDto.getPassword()));
				user.setSyncStatus("Ready to start");
				userService.update(user);

				userDto = new UserDto(user);
				return Response.status(200).entity(userDto).build();
			} else {
				return Response.status(500).entity("Username not found!").build();
			}

		} catch (EJBException e) {
			return Response.status(500).entity("Username not found!").build();
		}

	}

	@POST
	@Path("/create")
	public Response createUser(String requestBody) {

		Random rand = new Random();

		long n = rand.nextInt(50) + 1;
		n = n + System.currentTimeMillis();
		User user = new User("temp_username_" + n, "temp_password" + n, "", "access_token", "", "", "", "");

		User result = (User) userService.create(user);
		UserDto userDto = new UserDto(result);
		return Response.status(201).entity(userDto).build();
	}

	@POST
	@Path("/registerToken")
	public Response registerToken(String requestBody) {

		try {

			String authorizationToken = "Basic " + new String(Base64.encodeBase64("baX24QXs4r56RcacwqjQXA:040301aea16521d342ed8de1b9d12c9d".getBytes()));

			String response = RESTServiceUtil.sendPOST(REQUEST_REFRESH_TOKEN_URL + requestBody, true, authorizationToken);

			ObjectMapper objMap = new ObjectMapper();

			RequestTokenObject rto = objMap.readValue(response, RequestTokenObject.class);

			String userDetailsEndpoint = "https://api.assembla.com/v1/user.xml";

			String userDetails = RESTServiceUtil.sendGET(userDetailsEndpoint, true, "Bearer " + rto.getAccess_token());

			UserAssemblaDto userAssemblaDto = (UserAssemblaDto) RESTServiceUtil.unmarshaller(UserAssemblaDto.class, userDetails);

			User user = new User();
			user.setExternalRefId(userAssemblaDto.getId());
			user.setBearerToken(rto.getAccess_token());
			user.setRefreshToken(rto.getRefresh_token());
			user.setName(userAssemblaDto.getName());
			user.setEmail(userAssemblaDto.getEmail());
			user.setUsername(userAssemblaDto.getLogin());
			user.setPhoneNum(userAssemblaDto.getPhone());

			Random rand = new Random();

			long n = rand.nextInt(50) + 1;
			n = n + System.currentTimeMillis();

			user.setPassword("temp_password" + n);
			userService.create(user);

			UserDto userDto = new UserDto(user);

			return Response.status(200).entity(userDto).build();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(500).build();

	}

	@GET
	@Path("/list")
	public Response getUsers() {

		List<User> users = userService.listAll(User.class);
		List<UserDto> usersDto = new ArrayList<UserDto>();
		for (User user : users) {
			usersDto.add(new UserDto(user));
		}

		UserListDto userList = new UserListDto();
		userList.setUserDtos(usersDto);
		return Response.status(200).entity(userList).build();
	}
}
