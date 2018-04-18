package com.webdrone.main;

import java.io.StringReader;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import com.webdrone.assembla.dto.SpaceAssemblaDto;
import com.webdrone.assembla.dto.SpaceListAssemblaDto;
import com.webdrone.model.Space;
import com.webdrone.service.SpaceService;
import com.webdrone.service.UserService;
import com.webdrone.util.RESTServiceUtil;
import com.webdrone.util.UserAuthResult;

@Path("/space")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class SpaceRestService {

	@Inject
	private SpaceService spaceService;

	@Inject
	private UserService userService;

	@GET
	@Path("/list")
	public Response getSpaces(@HeaderParam("Authorization") String authorization) {

		try {
			UserAuthResult valResult = userService.validateUserAuthorization(authorization);

			if (valResult.getResponseCode() != 200) {
				return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
			}

			String spacesXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces.xml", true,
					"Bearer " + valResult.getUser().getBearerToken());

			if (spacesXml.equals("401")) {

				RESTServiceUtil.refreshBearerToken(valResult.getUser());

				spacesXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces.xml", true,
						"Bearer " + valResult.getUser().getBearerToken());
				userService.update(valResult.getUser());

			}
			JAXBContext jxb = JAXBContext.newInstance(SpaceListAssemblaDto.class);

			Unmarshaller unmarshaller = jxb.createUnmarshaller();

			unmarshaller.setEventHandler(new ValidationEventHandler() {
				public boolean handleEvent(ValidationEvent event) {
					if (event.getMessage().contains("unexpected element"))
						return true;
					return false;
				}
			});

			SpaceListAssemblaDto spaceListAssemblaDto = (SpaceListAssemblaDto) unmarshaller
					.unmarshal(new StringReader(spacesXml));

			for (SpaceAssemblaDto spaceAssemblaDto : spaceListAssemblaDto.getSpaceDtos()) {
				Object resultSpace = spaceService.findByExternalRefId(Space.class, spaceAssemblaDto.getId());
				Space currentSpace = resultSpace != null ? ((Space) resultSpace) : null;
				Object resultParentSpace = spaceService.findByExternalRefId(Space.class,
						spaceAssemblaDto.getParentId());
				Space parentSpace = resultParentSpace != null ? ((Space) resultParentSpace) : null;
				if (currentSpace == null) {
					Space newSpace = new Space(spaceAssemblaDto);
					if (parentSpace != null) {
						newSpace.setParentSpace(parentSpace);
					}
					spaceService.create(new Space(spaceAssemblaDto));
				} else {
					currentSpace.setApproved(spaceAssemblaDto.isApproved());
					currentSpace.setBannerHeight(spaceAssemblaDto.getBannerHeight() != null
							? spaceAssemblaDto.getBannerHeight().longValue() : 0);
					currentSpace.setBannerLink(spaceAssemblaDto.getBannerLink());
					currentSpace.setBannerPath(spaceAssemblaDto.getBanner());
					currentSpace.setBannerText(spaceAssemblaDto.getBannerText());
					currentSpace.setCanApply(spaceAssemblaDto.isCanApply());
					currentSpace.setCanJoin(spaceAssemblaDto.isCanJoin());
					currentSpace.setCommercial(spaceAssemblaDto.isCommercial());
					currentSpace.setCommercialFrom(spaceAssemblaDto.getCommercialFrom() != null
							? spaceAssemblaDto.getCommercialFrom().toDate() : null);
					currentSpace.setDefaultShowPage(spaceAssemblaDto.getDefaultShowpage());
					currentSpace.setDescription(spaceAssemblaDto.getDescription());
					currentSpace.setExternalRefId(spaceAssemblaDto.getId());
					currentSpace.setLastPayerChangedAt(spaceAssemblaDto.getLastPayerChangedAt() != null
							? spaceAssemblaDto.getLastPayerChangedAt().toDate() : null);
					currentSpace.setManager(spaceAssemblaDto.isManager());
					if (parentSpace != null) {
						currentSpace.setParentSpace(parentSpace);
					}
					currentSpace.setPublicPermissions(spaceAssemblaDto.getPublicPermissions());
					currentSpace.setRemotelyCreated(
							spaceAssemblaDto.getCreatedAt() != null ? spaceAssemblaDto.getCreatedAt().toDate() : null);
					currentSpace.setRemotelyUpdated(
							spaceAssemblaDto.getUpdatedAt() != null ? spaceAssemblaDto.getUpdatedAt().toDate() : null);
					currentSpace.setRestricted(spaceAssemblaDto.isRestricted());
					currentSpace.setRestrictedDate(spaceAssemblaDto.getRestrictedDate() != null
							? spaceAssemblaDto.getRestrictedDate().toDate() : null);
					currentSpace.setStatus(spaceAssemblaDto.getStatus());
					currentSpace.setStyle(spaceAssemblaDto.getStyle());
					currentSpace.setTabsOrder(spaceAssemblaDto.getTabsOrder());
					currentSpace.setTeamPermissions(spaceAssemblaDto.getTeamPermissions());
					currentSpace.setTeamTabRole(spaceAssemblaDto.getTeamTabRole());
					currentSpace.setVolunteer(spaceAssemblaDto.isVolunteer());
					currentSpace.setWatcherPermissions(spaceAssemblaDto.getWatcherPermissions());
					currentSpace.setWikiname(spaceAssemblaDto.getWikiName());
					spaceService.update(currentSpace);
				}
			}

			return Response.status(200).entity(spaceListAssemblaDto).build();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(500).entity("Username not found!").build();
	}
}
