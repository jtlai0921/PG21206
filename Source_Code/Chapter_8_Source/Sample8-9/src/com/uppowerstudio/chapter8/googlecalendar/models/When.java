package com.uppowerstudio.chapter8.googlecalendar.models;

import com.google.api.client.util.Key;

/**
 * ��䬡�ʨƥ�Atom XML�������Igd:when��Java�ƾڼҫ���
 * @author UPPower Studio
 *
 */
public class When {
	@Key("@startTime")
	public String startTime;
	
	@Key("@endTime")
	public String endTime;
}
