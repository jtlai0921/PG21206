package com.uppowerstudio.chapter8.googlecalendar.models;

import com.google.api.client.util.Key;

/**
 * 日曆及日曆活動事件中Atom XML對應結點link的Java數據模型類
 * @author Yu
 *
 */
public class Link {

	@Key("@rel")
	public String rel;
	
	@Key("@type")
	public String type;
	
	@Key("@href")
	public String href;
}
