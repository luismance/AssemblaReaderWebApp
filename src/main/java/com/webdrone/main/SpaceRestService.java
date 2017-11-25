package com.webdrone.main;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Base64;

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
import com.webdrone.model.User;
import com.webdrone.service.SpaceService;
import com.webdrone.service.UserService;
import com.webdrone.util.RESTServiceUtil;

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
			authorization = authorization.split("Basic ")[1];
			String credentials = new String(Base64.getDecoder().decode(authorization), Charset.forName("UTF-8"));
			// credentials = username:password
			final String[] values = credentials.split(":", 2);

			User user = userService.findUserByUsernameAndPassword(values[0], values[1]);
			if (user == null) {
				return Response.status(500).entity("Username not found!").build();
			}

			String spacesXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces.xml", true,
					"Bearer " + user.getBearerToken());

			System.out.println("Spaces XML : "+spacesXml);
			
			JAXBContext jxb = JAXBContext.newInstance(SpaceListAssemblaDto.class);

			Unmarshaller unmarshaller = jxb.createUnmarshaller();

			unmarshaller.setEventHandler(new ValidationEventHandler() {
				public boolean handleEvent(ValidationEvent event) {
					throw new RuntimeException(event.getMessage(), event.getLinkedException());
				}
			});

			SpaceListAssemblaDto spaceListAssemblaDto = (SpaceListAssemblaDto) unmarshaller
					.unmarshal(new StringReader(spacesXml));
			System.out.println("Space List Size : " + spaceListAssemblaDto.getSpaceDtos().size());
			for (SpaceAssemblaDto spaceAssemblaDto : spaceListAssemblaDto.getSpaceDtos()) {
				Object resultSpace = spaceService.findByExternalRefId(Space.class, spaceAssemblaDto.getId());
				Space currentSpace = resultSpace != null ? ((Space) resultSpace) : null;
				Object resultParentSpace = spaceService.findByExternalRefId(Space.class,spaceAssemblaDto.getParentId());
				Space parentSpace = resultParentSpace != null ? ((Space) resultParentSpace) : null;
				if (currentSpace == null) {
					Space newSpace = new Space(spaceAssemblaDto);
					if (parentSpace != null) {
						newSpace.setParentSpace(parentSpace);
					}
					spaceService.create(new Space(spaceAssemblaDto));
				} else {
					currentSpace.setApproved(spaceAssemblaDto.isApproved());
					currentSpace.setBannerHeight(spaceAssemblaDto.getBannerHeight().longValue());
					currentSpace.setBannerLink(spaceAssemblaDto.getBannerLink());
					currentSpace.setBannerPath(spaceAssemblaDto.getBanner());
					currentSpace.setBannerText(spaceAssemblaDto.getBannerText());
					currentSpace.setCanApply(spaceAssemblaDto.isCanApply());
					currentSpace.setCanJoin(spaceAssemblaDto.isCanJoin());
					currentSpace.setCommercial(spaceAssemblaDto.isCommercial());
					currentSpace.setCommercialFrom(spaceAssemblaDto.getCommercialFrom().toDate());
					currentSpace.setDefaultShowPage(spaceAssemblaDto.getDefaultShowpage());
					currentSpace.setDescription(spaceAssemblaDto.getDescription());
					currentSpace.setExternalRefId(spaceAssemblaDto.getId());
					currentSpace.setLastPayerChangedAt(spaceAssemblaDto.getLastPayerChangedAt().toDate());
					currentSpace.setManager(spaceAssemblaDto.isManager());
					if (parentSpace != null) {
						currentSpace.setParentSpace(parentSpace);
					}
					currentSpace.setPublicPermissions(spaceAssemblaDto.getPublicPermissions());
					currentSpace.setRemotelyCreated(spaceAssemblaDto.getCreatedAt().toDate());
					currentSpace.setRemotelyUpdated(spaceAssemblaDto.getUpdatedAt().toDate());
					currentSpace.setRestricted(spaceAssemblaDto.isRestricted());
					currentSpace.setRestrictedDate(spaceAssemblaDto.getRestrictedDate().toDate());
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

			return Response.status(200).entity(spacesXml).build();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(500).entity("Username not found!").build();
	}
}
