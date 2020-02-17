package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.googleapis.GoogleUrl;
import com.google.api.client.util.Key;

/**
 * �غc�U�ؤ��s��URL
 * @author UPPower Studio
 *
 */
public class CalendarUrl extends GoogleUrl implements StaticConst {
	
	// �w�q�̤j��^�O����
	@Key("max-results")
	public Integer maxResults;

	public CalendarUrl(String url) {
		super(url);
	}
	
	/**
	 * ��^���API�ڦa�}
	 * @return
	 */
	private static CalendarUrl getRootUrl() {
		return new CalendarUrl(METAFEED_URL_BASE);
	}

	/**
	 * ��^Metafeed������}
	 * @return
	 */
	public static CalendarUrl getMetafeedCalendar() {
		CalendarUrl result=getRootUrl();
		result.pathParts.add(DEFAULT_FEED_URL_SUFFIX);
		return result;
	}

	/**
	 * ��^allCalendars������}
	 * @return
	 */
	public static CalendarUrl getAllCalendarsFeed() {
		CalendarUrl result=getMetafeedCalendar();
		result.pathParts.add(ALLCALENDARS_FEED_URL_SUFFIX);
		result.pathParts.add(PROJECT_FULL_SUFFIX);
		return result;
	}

	/**
	 * ��^ownCalendars������}
	 * @return
	 */
	public static CalendarUrl getOwnCalendarsFeed() {
		CalendarUrl result=getMetafeedCalendar();
		result.pathParts.add(OWNCALENDARS_FEED_URL_SUFFIX);
		result.pathParts.add(PROJECT_FULL_SUFFIX);
		return result;
	}

	/**
	 * �غc��䬡�ʨƥ󤶭���}
	 * @param userId
	 * @param visibility
	 * @param projection
	 * @return
	 */
	public static CalendarUrl getEventFeed(String userId, String visibility,
			String projection) {
		CalendarUrl result=getRootUrl();
		result.pathParts.add(userId);
		result.pathParts.add(visibility);
		result.pathParts.add(projection);
		return result;
	}

	/**
	 * ��^�w�]����䬡�ʨƥ󤶭���}
	 * @return
	 */
	public static CalendarUrl getDefaultPrivateFullEventFeed() {
		return getEventFeed("default", "private", "full");
	}
}