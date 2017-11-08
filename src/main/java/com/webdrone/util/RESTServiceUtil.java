package com.webdrone.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

public class RESTServiceUtil {

	public static String sendGET(String uri, boolean requiresAuthorization, String authorization) {
		return sendReq(uri, RESTServiceMethod.GET, requiresAuthorization, authorization);
	}

	public static String sendPOST(String uri, boolean requiresAuthorization, String authorization) {
		return sendReq(uri, RESTServiceMethod.POST, requiresAuthorization, authorization);
	}

	@SuppressWarnings("resource")
	public static String sendReq(String uri, RESTServiceMethod method, boolean requiresAuthorization,
			String authorization) {
		try {

			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.toString());

			if (requiresAuthorization) {
				connection.setRequestProperty("Authorization", authorization);
			}

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
		return "";
	}
}
