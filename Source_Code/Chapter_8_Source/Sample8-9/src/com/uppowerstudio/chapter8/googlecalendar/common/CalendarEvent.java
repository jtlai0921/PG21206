package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.http.HttpTransport;
import com.uppowerstudio.chapter8.googlecalendar.models.Event;

import java.io.IOException;

/**
 * ��䬡�ʨƥ�ƾڼҫ�
 * @author UPPower Studio
 *
 */
public class CalendarEvent extends Event {

	@Override
	public CalendarEvent clone() {
		return (CalendarEvent) super.clone();
	}

	/**
	 * �s�Ыؤ�䬡�ʨƥ�
	 * @param transport
	 * @param url
	 * @return
	 * @throws IOException
	 */
	@Override
	public CalendarEvent executeInsert(HttpTransport transport, CalendarUrl url)
			throws IOException {
		return (CalendarEvent) super.executeInsert(transport, url);
	}

	/**
	 * ��s��䬡�ʨƥ�
	 * @param transport
	 * @param original
	 * @return
	 * @throws IOException
	 */
	public CalendarEvent executePatchRelativeToOriginal(
			HttpTransport transport, CalendarEvent original) throws IOException {
		return (CalendarEvent) super.executePatchRelativeToOriginal(transport,
				original);
	}
}
