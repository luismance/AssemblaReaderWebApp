package com.webdrone.servlets;

import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/page")
@Produces(MediaType.TEXT_HTML)
public class LoginBean {

	private static final long serialVersionUID = 1L;

	public static final String HTML_START = "<html><body>";
	public static final String HTML_END = "</body></html>";

	@GET
	@Path("/param/{param}")
	public Response printMessage(@PathParam("param") String msg) {
		String result = "Hello " + msg + "!";
		return Response.status(200).entity(result).build();
	}

	@GET
	@Path("/index")
	public Response getIndex() {

		System.out.println("User Dir : " + System.getProperty("user.dir"));
		URL url = LoginBean.class.getResource("index.html");

		if (url == null) {
			String result = "Page NOT FUCKING FOUND!";
			return Response.status(200).entity(result).build();
		}
		return Response.ok(url).build();

	}
	
	@GET
	@Path("/notifications")
	public Response getNotifications() {

		System.out.println("User Dir : " + System.getProperty("user.dir"));
		URL url = LoginBean.class.getResource("index.html");

		if (url == null) {
			String result = "Page NOT FUCKING FOUND!";
			return Response.status(200).entity(result).build();
		}
		return Response.ok(url).build();

	}

}
