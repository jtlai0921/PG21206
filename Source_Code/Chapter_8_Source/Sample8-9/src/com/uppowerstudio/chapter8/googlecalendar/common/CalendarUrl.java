package com.uppowerstudio.chapter8.googlecalendar.common;

import com.google.api.client.googleapis.GoogleUrl;
import com.google.api.client.util.Key;

/**
 * 建構各種日曆存取URL
 * @author UPPower Studio
 *
 */
public class CalendarUrl extends GoogleUrl implements StaticConst {
	
	// 定義最大返回記錄數
	@Key("max-results")
	public Integer maxResults;

	public CalendarUrl(String url) {
		super(url);
	}
	
	/**
	 * 返回日曆API根地址
	 * @return
	 */
	private static CalendarUrl getRootUrl() {
		return new CalendarUrl(METAFEED_URL_BASE);
	}

	/**
	 * 返回Metafeed介面位址
	 * @return
	 */
	public static CalendarUrl getMetafeedCalendar() {
		CalendarUrl result=getRootUrl();
		result.pathParts.add(DEFAULT_FEED_URL_SUFFIX);
		return result;
	}

	/**
	 * 返回allCalendars介面位址
	 * @return
	 */
	public static CalendarUrl getAllCalendarsFeed() {
		CalendarUrl result=getMetafeedCalendar();
		result.pathParts.add(ALLCALENDARS_FEED_URL_SUFFIX);
		result.pathParts.add(PROJECT_FULL_SUFFIX);
		return result;
	}

	/**
	 * 返回ownCalendars介面位址
	 * @return
	 */
	public static CalendarUrl getOwnCalendarsFeed() {
		CalendarUrl result=getMetafeedCalendar();
		result.pathParts.add(OWNCALENDARS_FEED_URL_SUFFIX);
		result.pathParts.add(PROJECT_FULL_SUFFIX);
		return result;
	}

	/**
	 * 建構日曆活動事件介面位址
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
	 * 返回預設的日曆活動事件介面位址
	 * @return
	 */
	public static CalendarUrl getDefaultPrivateFullEventFeed() {
		return getEventFeed("default", "private", "full");
	}
}