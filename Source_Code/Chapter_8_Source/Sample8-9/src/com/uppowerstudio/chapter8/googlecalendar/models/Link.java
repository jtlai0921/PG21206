package com.uppowerstudio.chapter8.googlecalendar.models;

import com.google.api.client.util.Key;

/**
 * ���Τ�䬡�ʨƥ�Atom XML�������Ilink��Java�ƾڼҫ���
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
