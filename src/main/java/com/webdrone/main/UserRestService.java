package com.webdrone.main;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webdrone.assembla.dto.UserAssemblaDto;
import com.webdrone.dto.RefreshTokenObject;
import com.webdrone.dto.UserDto;
import com.webdrone.dto.UserListDto;
import com.webdrone.model.User;
import com.webdrone.service.UserService;
import com.webdrone.util.RESTServiceUtil;

@Path("/user")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class UserRestService {

	private static String REQUEST_ACCESS_TOKEN_URL = "https://augdmUP68r57ldacwqjQYw:02535496a26c8df7a1b7dfd92e3c7f80@api.assembla.com/token?grant_type=refresh_token&refresh_token=";

	private static String REFRESH_TOKEN = "26fd9fa890f2130659a6948e10064f60";

	@Inject
	private UserService userService;

	@SuppressWarnings("unused")
	@POST
	@Path("/create")
	public Response createUser(@HeaderParam("Authorization") String authorization) {

		try {
			String response = RESTServiceUtil.sendPOST(REQUEST_ACCESS_TOKEN_URL + REFRESH_TOKEN, true, authorization);

			ObjectMapper objMap = new ObjectMapper();
			RefreshTokenObject rto = objMap.readValue(response, RefreshTokenObject.class);

			String userDetailsEndpoint = "https://api.assembla.com/v1/user.xml";

			String userDetails = RESTServiceUtil.sendGET(userDetailsEndpoint, true, "Bearer " + rto.getAccess_token());

			System.out.println("User Details : " + userDetails);
			JAXBContext jxb = JAXBContext.newInstance(UserAssemblaDto.class);
			Unmarshaller unmarshaller = jxb.createUnmarshaller();

			UserAssemblaDto userAssemblaDto = (UserAssemblaDto) unmarshaller.unmarshal(new StringReader(userDetails));

			User user = new User(userAssemblaDto.getLogin(), "password", userAssemblaDto.getId(), rto.getAccess_token(),
					REFRESH_TOKEN, userAssemblaDto.getName(), userAssemblaDto.getEmail(), userAssemblaDto.getPhone());

			UserDto userDto = new UserDto(user);
			userService.create(user);
			return Response.status(200).entity(userDto).build();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
