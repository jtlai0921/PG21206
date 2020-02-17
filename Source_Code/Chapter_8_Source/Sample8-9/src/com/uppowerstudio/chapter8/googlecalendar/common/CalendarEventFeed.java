package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Key;
import com.uppowerstudio.chapter8.googlecalendar.models.Feed;
import com.uppowerstudio.chapter8.googlecalendar.utils.CalendarUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 日曆活動事件Feed接口
 * @author UPPower Studio
 *
 */
public class CalendarEventFeed extends Feed {

	// 用於映射日曆活動事件Atom XML中的entry結點數據
	@Key("entry")
	public List<CalendarEvent> events = new ArrayList<CalendarEvent>();

	/**
	 * 執行日曆活動事件查詢
	 * @param transport
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static CalendarEventFeed executeEventGet(HttpTransport transport,
			CalendarUrl url) throws IOException {
		return (CalendarEventFeed) CalendarUtils.executeGet(transport, url,
				CalendarEventFeed.class);
	}
}
