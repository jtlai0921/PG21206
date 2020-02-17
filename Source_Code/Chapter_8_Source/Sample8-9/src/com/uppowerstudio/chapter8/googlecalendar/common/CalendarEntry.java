package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.http.HttpTransport;
import com.uppowerstudio.chapter8.googlecalendar.models.Entry;
import com.uppowerstudio.chapter8.googlecalendar.utils.CalendarUtils;

import java.io.IOException;

/**
 * 日曆數據模型
 * @author UPPower Studio
 *
 */
public class CalendarEntry extends Entry {

	public String getEventFeedLink() {
		return CalendarUtils.find(links,
				"http://schemas.google.com/gCal/2005#eventFeed");
	}

	@Override
	public CalendarEntry clone() {
		return (CalendarEntry) super.clone();
	}

	/**
	 * 新創建日曆
	 * @param transport
	 * @param url
	 * @return
	 * @throws IOException
	 */
	@Override
	public CalendarEntry executeInsert(HttpTransport transport, CalendarUrl url)
			throws IOException {
		return (CalendarEntry) super.executeInsert(transport, url);
	}

	/**
	 * 更新日曆
	 * @param transport
	 * @param original
	 * @return
	 * @throws IOException
	 */
	public CalendarEntry executePatchRelativeToOriginal(
			HttpTransport transport, CalendarEntry original) throws IOException {
		return (CalendarEntry) super.executePatchRelativeToOriginal(transport,
				original);
	}
}
