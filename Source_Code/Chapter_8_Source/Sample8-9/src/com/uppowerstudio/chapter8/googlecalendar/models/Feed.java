package com.uppowerstudio.chapter8.googlecalendar.models;

import java.util.List;

import com.google.api.client.util.Key;
import com.uppowerstudio.chapter8.googlecalendar.utils.CalendarUtils;

/**
 * ���Feed���f����Java�ƾڼҫ���
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
	 * ����U�@��link�챵
	 * @return
	 */
	public String getNextLink() {
		return CalendarUtils.find(links, "next");
	}

	/**
	 * �������h�Ӿާ@��batch link�챵
	 * @return
	 */
	public String getBatchLink() {
		return CalendarUtils.find(links,
				"http://schemas.google.com/g/2005#batch");
	}
}
