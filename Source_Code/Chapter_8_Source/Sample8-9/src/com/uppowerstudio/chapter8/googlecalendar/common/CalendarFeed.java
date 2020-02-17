package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Key;
import com.uppowerstudio.chapter8.googlecalendar.models.Feed;
import com.uppowerstudio.chapter8.googlecalendar.utils.CalendarUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 日曆Feed接口
 * @author UPPower Studio
 *
 */
public class CalendarFeed extends Feed {
	
	// 用於映射日曆Atom XML中的entry結點數據
	@Key("entry")
	public List<CalendarEntry> calendars = new ArrayList<CalendarEntry>();

	/**
	 * 執行日曆查詢
	 * @param transport
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static CalendarFeed executeGet(HttpTransport transport,
			CalendarUrl url) throws IOException {
		return (CalendarFeed) CalendarUtils.executeGet(transport, url,
				CalendarFeed.class);
	}
}
