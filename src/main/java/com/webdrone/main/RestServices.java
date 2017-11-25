package com.webdrone.main;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.webdrone.service.SpaceService;
import com.webdrone.service.UserService;
import com.webdrone.util.RESTServiceUtil;

@Path("/")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class RestServices {

	private static String REQUEST_ACCESS_TOKEN_URL = "https://augdmUP68r57ldacwqjQYw:02535496a26c8df7a1b7dfd92e3c7f80@api.assembla.com/token?grant_type=refresh_token&refresh_token=";

	private static String REFRESH_TOKEN = "26fd9fa890f2130659a6948e10064f60";

	private String accessToken;

	@Inject
	private SpaceService spaceService;

	@Inject
	private UserService userService;

	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {
		String result = "Hello " + msg + "!";
		return Response.status(200).entity(result).build();
	}

	@GET
	@Path("/notifications")
	public Response getNotifications(@HeaderParam("Authorization") String authorization) {

		String response = RESTServiceUtil.sendGET(REQUEST_ACCESS_TOKEN_URL + REFRESH_TOKEN, true, authorization);

		return Response.status(200).entity(response).build();
	}

}
