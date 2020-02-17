package com.uppowerstudio.chapter8.googlecalendar.common;

/**
 * 靜態介面
 * @author UPPower Studio
 *
 */
public interface StaticConst {
	static final String TAG="Sample8-8";

	// 定義應用中的Action
	static final String ACTION_LIST_CALENDAR="action_list_calendar";
	static final String ACTION_LIST_CALENDAR_EVENTS="action_list_calendar_events";

	// Google日曆服務根位址
	static final String METAFEED_URL_BASE="https://www.google.com/calendar/feeds";

	// 作用為獲取用戶默認日曆
	static final String DEFAULT_FEED_URL_SUFFIX="default";

	// 此尾碼用於添加到用戶Feed URL地址之後，作用為獲取用戶所有的日曆
	static final String ALLCALENDARS_FEED_URL_SUFFIX="allcalendars";

	// 此尾碼用於添加到用戶Feed URL地址之後，作用為獲取用戶所擁有的日曆
	static final String OWNCALENDARS_FEED_URL_SUFFIX="owncalendars";
	
	// 定義日曆活動事件Feed介面的Project屬性值
	static final String PROJECT_FULL_SUFFIX="full";

	// 成都市的日出和日落日曆ID
	static final String CHENGDU_SUNRISE_CALENDAR_ID="i_125.69.29.149#sunrise@group.v.calendar.google.com";

	// 定義網路編碼格式
	static final String ENCODING="UTF-8";

	// 定義Bundle Keys
	static final String BUNDLE_KEY_USERNAME="USER_NAME";
	static final String BUNDLE_KEY_PASSWORD="PASSWORD";

	// 定義用於授權時使用的日曆服務程式碼
	static final String AUTH_SERVICE_TYPE="cl";

	// 定義功能表使用值
	static final int CONTEXT_EDIT_CALENDAR=0;
	static final int CONTEXT_DELETE_CALENDAR=1;
}
