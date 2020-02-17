package com.uppowerstudio.chapter8.googlecalendar.models;

import java.util.List;

import com.google.api.client.util.Key;
import com.uppowerstudio.chapter8.googlecalendar.utils.CalendarUtils;

/**
 * 日曆Feed接口對應Java數據模型類
 * @author UPPower Studio
 *
 */
public class Feed {

	@Key
	public String id;

	@Key
	public String updated;

	@Key
	public String title;

	@Key("link")
	public List<Link> links;

	/**
	 * 獲取下一個link鏈接
	 * @return
	 */
	public String getNextLink() {
		return CalendarUtils.find(links, "next");
	}

	/**
	 * 獲取執行多個操作的batch link鏈接
	 * @return
	 */
	public String getBatchLink() {
		return CalendarUtils.find(links,
				"http://schemas.google.com/g/2005#batch");
	}
}
