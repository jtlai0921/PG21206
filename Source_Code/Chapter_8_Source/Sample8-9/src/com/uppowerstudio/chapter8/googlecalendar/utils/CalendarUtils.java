package com.uppowerstudio.chapter8.googlecalendar.utils;

import java.io.IOException;
import java.util.List;

import com.google.api.client.googleapis.xml.atom.GData;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.uppowerstudio.chapter8.googlecalendar.common.CalendarUrl;
import com.uppowerstudio.chapter8.googlecalendar.common.StaticConst;
import com.uppowerstudio.chapter8.googlecalendar.models.Feed;
import com.uppowerstudio.chapter8.googlecalendar.models.Link;
import com.uppowerstudio.chapter8.googlecalendar.models.RequestHandler;

/**
 * ��������������
 * @author UPPower Studio
 *
 */
public class CalendarUtils implements StaticConst {

	/**
	 * ִ��GET�������
	 * @param transport
	 * @param url
	 * @param feedClass
	 * @return
	 * @throws IOException
	 */
	public static Feed executeGet(HttpTransport transport, CalendarUrl url,
			Class<? extends Feed> feedClass) throws IOException {
		url.fields = GData.getFieldsFor(feedClass);
		HttpRequest request = transport.buildGetRequest();
		request.url = url;
		return RequestHandler.execute(request).parseAs(feedClass);
	}

	/**
	 * �����ض���link
	 * @param links
	 * @param rel
	 * @return
	 */
	public static String find(List<Link> links, String rel) {
		if (links != null) {
			for (Link link : links) {
				if (rel.equals(link.rel)) {
					return link.href;
				}
			}
		}
		return null;
	}
}
