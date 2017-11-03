package com.webdrone.main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_XML)
public class RestServices {

	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {
		String result = "Hello " + msg + "!";
		return Response.status(200).entity(result).build();
	}
	
	@GET
	@Path("/notifications")
	public String getNotifications(){
		
		return "something response";
	}
}
