package com.uppowerstudio.chapter8.googlecalendar.models;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;

import java.io.IOException;

/**
 * �ШD�B�z��
 * @author UPPower Studio
 *
 */
public class RequestHandler {

	/**
	 * ����HTTP�ШD�ާ@
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static HttpResponse execute(HttpRequest request) throws IOException {
		try {
			return request.execute();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
