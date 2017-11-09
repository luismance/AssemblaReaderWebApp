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

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webdrone.assembla.dto.UserAssemblaDto;
import com.webdrone.dto.RefreshTokenObject;
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

	private static String REQUEST_ACCESS_TOKEN_URL = "https://baX24QXs4r56RcacwqjQXA:040301aea16521d342ed8de1b9d12c9d@api.assembla.com/token?grant_type=refresh_token&refresh_token=";


	@Inject
	private UserService userService;

	@POST
	@Path("/create")
	public Response createUser(@HeaderParam("Authorization") String authorization) {

		try {
			String response = RESTServiceUtil.sendPOST(REQUEST_ACCESS_TOKEN_URL + "", true, authorization);

			ObjectMapper objMap = new ObjectMapper();
			RefreshTokenObject rto = objMap.readValue(response, RefreshTokenObject.class);

			String userDetailsEndpoint = "https://api.assembla.com/v1/user.xml";

			String userDetails = RESTServiceUtil.sendGET(userDetailsEndpoint, true, "Bearer " + rto.getAccess_token());

			System.out.println("User Details : " + userDetails);
			JAXBContext jxb = JAXBContext.newInstance(UserAssemblaDto.class);
			Unmarshaller unmarshaller = jxb.createUnmarshaller();

			UserAssemblaDto userAssemblaDto = (UserAssemblaDto) unmarshaller.unmarshal(new StringReader(userDetails));

			User user = new User(userAssemblaDto.getLogin(), "password", userAssemblaDto.getId(), rto.getAccess_token(),
					"", userAssemblaDto.getName(), userAssemblaDto.getEmail(), userAssemblaDto.getPhone());

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

	@POST
	@Path("/registerToken")
	public Response registerToken(String requestBody) {

		try {
			System.out.println("Req Body : " + requestBody);

			String authorizationToken = "Basic " + new String(
					Base64.encodeBase64("baX24QXs4r56RcacwqjQXA:040301aea16521d342ed8de1b9d12c9d".getBytes()));

			String response = RESTServiceUtil.sendPOST(REQUEST_REFRESH_TOKEN_URL + requestBody, true,
					authorizationToken);

			ObjectMapper objMap = new ObjectMapper();

			RequestTokenObject rto = objMap.readValue(response, RequestTokenObject.class);

			String userDetailsEndpoint = "https://api.assembla.com/v1/user.xml";

			String userDetails = RESTServiceUtil.sendGET(userDetailsEndpoint, true, "Bearer " + rto.getAccess_token());

			System.out.println("User Details : " + userDetails);
			JAXBContext jxb = JAXBContext.newInstance(UserAssemblaDto.class);
			Unmarshaller unmarshaller = jxb.createUnmarshaller();

			UserAssemblaDto userAssemblaDto = (UserAssemblaDto) unmarshaller.unmarshal(new StringReader(userDetails));

			User user = new User(userAssemblaDto.getLogin(), "password", userAssemblaDto.getId(), rto.getAccess_token(),
					rto.getRefresh_token(), userAssemblaDto.getName(), userAssemblaDto.getEmail(),
					userAssemblaDto.getPhone());

			UserDto userDto = new UserDto(user);

			userService.create(user);

			System.out.println("Response " + response);

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
		} catch (JAXBException e) {
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
