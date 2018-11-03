package com.webdrone.main;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.webdrone.assembla.dto.SpaceMilestoneCountDto;
import com.webdrone.model.Space;
import com.webdrone.service.MilestoneService;
import com.webdrone.service.UserService;
import com.webdrone.util.UserAuthResult;

@Path("/milestone")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class MilestoneRestService {

	@Inject
	UserService userService;

	@Inject
	MilestoneService milestonService;

	@GET
	@Path("/userMilestoneCount")
	public Response totalTicketCount(@HeaderParam("Authorization") String authorization) {

		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		long milestoneCount = 0;
		for (Space space : valResult.getUser().getSpaces()) {
			milestoneCount += milestonService.getMilestoneCountBySpace(space.getExternalRefId());
		}

		SpaceMilestoneCountDto milestoneCountDto = new SpaceMilestoneCountDto();
		milestoneCountDto.setMilestoneCount(milestoneCount);
		milestoneCountDto.setSyncStatus(valResult.getUser().getSyncStatus());
		return Response.status(200).entity(milestoneCountDto).build();

	}
}
