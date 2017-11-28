package com.webdrone.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webdrone.dto.RefreshTokenObject;
import com.webdrone.model.User;

public class RESTServiceUtil {

	private static String REQUEST_ACCESS_TOKEN_URL = "https://api.assembla.com/token?grant_type=refresh_token&refresh_token=";

	public static String sendGET(String uri, boolean requiresAuthorization, String authorization) {
		return sendReq(uri, RESTServiceMethod.GET, requiresAuthorization, authorization);
	}

	public static String sendPOST(String uri, boolean requiresAuthorization, String authorization) {
		return sendReq(uri, RESTServiceMethod.POST, requiresAuthorization, authorization);
	}

	@SuppressWarnings("resource")
	public static String sendReq(String uri, RESTServiceMethod method, boolean requiresAuthorization,
			String authorization) {
		String responseCode = "";
		try {

			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.toString());

			if (requiresAuthorization) {
				connection.setRequestProperty("Authorization", authorization);
			}

			responseCode = connection.getResponseCode() + "";
			System.out.println("Response Code : " + connection.getResponseCode());
			InputStream xml = connection.getInputStream();

			java.util.Scanner s = new java.util.Scanner(xml).useDelimiter("\\A");

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
			String authorizationToken = "Basic " + new String(
					Base64.encodeBase64("baX24QXs4r56RcacwqjQXA:040301aea16521d342ed8de1b9d12c9d".getBytes()));

			String response = RESTServiceUtil.sendPOST(REQUEST_ACCESS_TOKEN_URL + user.getRefreshToken(), true,
					authorizationToken);

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
