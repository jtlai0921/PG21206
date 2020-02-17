package com.uppowerstudio.chapter8.googlecalendar.models;

import java.io.IOException;
import java.util.List;

import com.google.api.client.googleapis.xml.atom.AtomPatchRelativeToOriginalContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DataUtil;
import com.google.api.client.util.Key;
import com.google.api.client.xml.atom.AtomContent;
import com.uppowerstudio.chapter8.googlecalendar.common.CalendarUrl;
import com.uppowerstudio.chapter8.googlecalendar.utils.CalendarUtils;

/**
 * ��䬡�ʨƥ�Atom XML����Java�ƾڼҫ���
 * @author UPPower Studio
 *
 */
public class Event implements Cloneable {

	@Key
	public String updated;

	@Key
	public String published;

	@Key
	public String title;

	@Key
	public String content;

	@Key("link")
	public List<Link> links;

	@Key("gd:when")
	public When when;

	@Override
	protected Event clone() {
		return DataUtil.clone(this);
	}

	/**
	 * �����s��䬡�ʨƥ�link�챵�a�}
	 * @return
	 */
	private String getEditLink() {
		return CalendarUtils.find(links, "edit");
	}

	/**
	 * �R����䬡�ʨƥ�
	 * @param transport
	 * @throws IOException
	 */
	public void executeDelete(HttpTransport transport) throws IOException {
		HttpRequest request = transport.buildDeleteRequest();
		request.setUrl(getEditLink());
		RequestHandler.execute(request).ignore();
	}

	/**
	 * �Ыؤ�䬡�ʨƥ�
	 * @param transport
	 * @param url
	 * @return
	 * @throws IOException
	 */
	protected Event executeInsert(HttpTransport transport, CalendarUrl url)
			throws IOException {
		HttpRequest request = transport.buildPostRequest();
		request.url = url;
		AtomContent content = new AtomContent();
		content.namespaceDictionary = Namespace.DICTIONARY;
		content.entry = this;
		request.content = content;
		return RequestHandler.execute(request).parseAs(getClass());
	}

	/**
	 * �q�LPATCH�ШD�i���䬡�ʨƥ󪺧�s�ާ@
	 * @param transport
	 * @param original
	 * @return
	 * @throws IOException
	 */
	protected Event executePatchRelativeToOriginal(HttpTransport transport,
			Event original) throws IOException {
		HttpRequest request = transport.buildPatchRequest();
		request.setUrl(getEditLink());
		AtomPatchRelativeToOriginalContent content = new AtomPatchRelativeToOriginalContent();
		content.namespaceDictionary = Namespace.DICTIONARY;
		content.originalEntry = original;
		content.patchedEntry = this;
		request.content = content;
		return RequestHandler.execute(request).parseAs(getClass());
	}
}
