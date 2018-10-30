package com.webdrone.main;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.webdrone.assembla.dto.SpaceAssemblaDto;
import com.webdrone.assembla.dto.SpaceListAssemblaDto;
import com.webdrone.model.Space;
import com.webdrone.service.UserService;
import com.webdrone.util.UserAuthResult;

@Path("/space")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class SpaceRestService {

	@Inject
	private UserService userService;

	@GET
	@Path("/list")
	public Response getSpaces(@HeaderParam("Authorization") String authorization) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		SpaceListAssemblaDto spaceListResult = new SpaceListAssemblaDto();
		List<SpaceAssemblaDto> sadList = new ArrayList<SpaceAssemblaDto>();
		for (Space space : valResult.getUser().getSpaces()) {
			sadList.add(space.toDto());
		}
		spaceListResult.setSpaceDtos(sadList);

		return Response.status(200).entity(spaceListResult).build();
	}
}
