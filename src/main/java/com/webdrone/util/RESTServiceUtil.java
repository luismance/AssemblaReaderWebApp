package com.webdrone.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class RESTServiceUtil {

	public static String sendGET(String url, boolean requiresAuthorization, String authorization) {
		return sendReq(url, RESTServiceMethod.GET, requiresAuthorization, authorization);
	}

	@SuppressWarnings("deprecation")
	public static String sendReq(String url, RESTServiceMethod method, boolean requiresAuthorization,
			String authorization) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			if (requiresAuthorization) {
				httpGet.setHeader("Authorization", authorization);
			}

			System.out.println("executing request " + httpGet.getRequestLine());

			for (Header hd : httpGet.getAllHeaders()) {
				System.out.println("executing request " + hd.getName() + "," + hd.getValue());
			}

			HttpResponse response;

			response = httpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();

			return response.toString();
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
