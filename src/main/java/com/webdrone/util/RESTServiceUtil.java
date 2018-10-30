package com.webdrone.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webdrone.assembla.dto.MilestoneAssemblaDto;
import com.webdrone.assembla.dto.TicketListAssemblaDto;
import com.webdrone.assembla.dto.UserAssemblaDto;
import com.webdrone.dto.RefreshTokenObject;
import com.webdrone.model.User;

public class RESTServiceUtil {

	private static String REQUEST_ACCESS_TOKEN_URL = "https://api.assembla.com/token?grant_type=refresh_token&refresh_token=";

	public static TicketListAssemblaDto convertTicketListXml(String spaceId, int ticketsPerPage, int page, String bearerToken) {
		String ticketsXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces/" + spaceId + "/tickets.xml?per_page=" + ticketsPerPage + "&page=" + page, true, "Bearer " + bearerToken);

		TicketListAssemblaDto ticketListAssemblaDto = (TicketListAssemblaDto) unmarshaller(TicketListAssemblaDto.class, ticketsXml);

		return ticketListAssemblaDto;

	}

	public static MilestoneAssemblaDto convertMilestonXml(String spaceId, String milestoneExternalRefId, String bearerToken) {
		String milestoneXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/spaces/" + spaceId + "/milestones/" + milestoneExternalRefId + ".xml", true, "Bearer " + bearerToken);

		MilestoneAssemblaDto milestoneAssemblaDto = (MilestoneAssemblaDto) unmarshaller(MilestoneAssemblaDto.class, milestoneXml);

		return milestoneAssemblaDto;
	}

	public static User convertUserXml(String userExternalRefId, String bearerToken) {
		String userXml = RESTServiceUtil.sendGET("https://api.assembla.com/v1/users/" + userExternalRefId + ".xml", true, "Bearer " + bearerToken);

		UserAssemblaDto userAssemblaDto = (UserAssemblaDto) unmarshaller(UserAssemblaDto.class, userXml);

		User user = new User(userAssemblaDto.getLogin(), userAssemblaDto.getLogin(), userAssemblaDto.getId(), "bearer_token", "refresh_token", userAssemblaDto.getName(), userAssemblaDto.getEmail(), "");
		return user;

	}

	public static String sendGET(String uri, boolean requiresAuthorization, String authorization) {
		return sendReq(uri, RESTServiceMethod.GET, requiresAuthorization, authorization);
	}

	@SuppressWarnings("rawtypes")
	public static Object unmarshaller(Class clazz, String xml) {
		return unmarshaller(clazz, xml, false);
	}

	@SuppressWarnings("rawtypes")
	public static Object unmarshaller(Class clazz, String xml, final boolean throwExceptionUnrecognizedElement) {
		JAXBContext jxb;
		try {
			jxb = JAXBContext.newInstance(clazz);

			Unmarshaller unmarshaller = jxb.createUnmarshaller();

			unmarshaller.setEventHandler(new ValidationEventHandler() {
				public boolean handleEvent(ValidationEvent event) {
					if (throwExceptionUnrecognizedElement) {
						throw new RuntimeException(event.getMessage(), event.getLinkedException());
					} else {
						if (event.getMessage().contains("unexpected element"))
							return true;
						return false;
					}
				}
			});

			return unmarshaller.unmarshal(new StringReader(xml));

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String sendPOST(String uri, boolean requiresAuthorization, String authorization) {
		return sendReq(uri, RESTServiceMethod.POST, requiresAuthorization, authorization);
	}

	@SuppressWarnings("resource")
	public static String sendReq(String uri, RESTServiceMethod method, boolean requiresAuthorization, String authorization) {
		String responseCode = "";
		try {

			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.toString());

			if (requiresAuthorization) {
				connection.setRequestProperty("Authorization", authorization);
			}

			responseCode = connection.getResponseCode() + "";

			System.out.println("Response Code : " + connection.getResponseCode() + ", URI : " + uri + ", Method : " + method.name());
			InputStreamReader r = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);

			java.util.Scanner s = new java.util.Scanner(r).useDelimiter("\\A");

			String response = s.hasNext() ? s.next() : "";

			return response;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseCode;
	}

	public static String encryptToSHA256(String initial) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(initial.getBytes(StandardCharsets.UTF_8));
			String converted = DatatypeConverter.printHexBinary(hash);

			return converted;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static void refreshBearerToken(User user) {
		try {
			String authorizationToken = "Basic " + new String(Base64.encodeBase64("baX24QXs4r56RcacwqjQXA:040301aea16521d342ed8de1b9d12c9d".getBytes()));

			String response = RESTServiceUtil.sendPOST(REQUEST_ACCESS_TOKEN_URL + user.getRefreshToken(), true, authorizationToken);

			ObjectMapper objMap = new ObjectMapper();

			RefreshTokenObject rto;

			rto = objMap.readValue(response, RefreshTokenObject.class);

			user.setBearerToken(rto.getAccess_token());

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
	}
}
