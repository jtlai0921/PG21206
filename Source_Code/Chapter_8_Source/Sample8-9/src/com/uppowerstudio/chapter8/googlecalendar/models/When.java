package com.uppowerstudio.chapter8.googlecalendar.models;

import com.google.api.client.util.Key;

/**
 * 日曆活動事件中Atom XML對應結點gd:when的Java數據模型類
 * @author UPPower Studio
 *
 */
public class When {
	@Key("@startTime")
	public String startTime;
	
	@Key("@endTime")
	public String endTime;
}
