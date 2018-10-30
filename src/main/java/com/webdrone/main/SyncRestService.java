package com.webdrone.main;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.webdrone.service.UserService;
import com.webdrone.thread.SyncUserDataThread;
import com.webdrone.util.UserAuthResult;

@Path("/sync")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class SyncRestService {

	@Inject
	private UserService userService;

	@Resource
	private ManagedThreadFactory threadFactory;

	@PersistenceUnit(unitName = "webdroneAssembla")
	private EntityManagerFactory emf;

	@Resource
	private UserTransaction utx;

	@POST
	@Path("/syncdata")
	public Response syncData(@HeaderParam("Authorization") String authorization, String requestBody) {
		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}

		SyncUserDataThread sudt = new SyncUserDataThread(utx, emf.createEntityManager(), valResult.getUser().getUsername());
		Thread thread = threadFactory.newThread(sudt);
		thread.start();

		return Response.status(200).entity("SUCCESS").build();

	}

	@GET
	@Path("/status")
	public Response syncDataStatus(@HeaderParam("Authorization") String authorization, String requestBody) {
		UserAuthResult valResult = userService.validateUserAuthorization(authorization);

		if (valResult.getResponseCode() != 200) {
			return Response.status(valResult.getResponseCode()).entity(valResult.getResponseMessage()).build();
		}
		return Response.status(200).entity("SUCCESS").build();

	}
}
