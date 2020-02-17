package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Key;
import com.uppowerstudio.chapter8.googlecalendar.models.Feed;
import com.uppowerstudio.chapter8.googlecalendar.utils.CalendarUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ���Feed���f
 * @author UPPower Studio
 *
 */
public class CalendarFeed extends Feed {
	
	// �Ω�M�g���Atom XML����entry���I�ƾ�
	@Key("entry")
	public List<CalendarEntry> calendars = new ArrayList<CalendarEntry>();

	/**
	 * ������d��
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
