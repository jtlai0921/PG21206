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
 * 日曆活動事件Atom XML對應Java數據模型類
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
	 * 獲取更新日曆活動事件的link鏈接地址
	 * @return
	 */
	private String getEditLink() {
		return CalendarUtils.find(links, "edit");
	}

	/**
	 * 刪除日曆活動事件
	 * @param transport
	 * @throws IOException
	 */
	public void executeDelete(HttpTransport transport) throws IOException {
		HttpRequest request = transport.buildDeleteRequest();
		request.setUrl(getEditLink());
		RequestHandler.execute(request).ignore();
	}

	/**
	 * 創建日曆活動事件
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
	 * 通過PATCH請求進行日曆活動事件的更新操作
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
