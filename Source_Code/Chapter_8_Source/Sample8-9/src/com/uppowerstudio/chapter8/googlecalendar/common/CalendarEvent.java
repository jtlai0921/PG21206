package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.http.HttpTransport;
import com.uppowerstudio.chapter8.googlecalendar.models.Event;

import java.io.IOException;

/**
 * 日曆活動事件數據模型
 * @author UPPower Studio
 *
 */
public class CalendarEvent extends Event {

	@Override
	public CalendarEvent clone() {
		return (CalendarEvent) super.clone();
	}

	/**
	 * 新創建日曆活動事件
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
	 * 更新日曆活動事件
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
