package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Key;
import com.uppowerstudio.chapter8.googlecalendar.models.Feed;
import com.uppowerstudio.chapter8.googlecalendar.utils.CalendarUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ��䬡�ʨƥ�Feed���f
 * @author UPPower Studio
 *
 */
public class CalendarEventFeed extends Feed {

	// �Ω�M�g��䬡�ʨƥ�Atom XML����entry���I�ƾ�
	@Key("entry")
	public List<CalendarEvent> events = new ArrayList<CalendarEvent>();

	/**
	 * �����䬡�ʨƥ�d��
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
