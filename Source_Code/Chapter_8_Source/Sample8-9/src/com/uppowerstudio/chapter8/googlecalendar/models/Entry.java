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
 * Java模型類別，對應Atom XML中的entry節點
 * @author UPPower Studio
 *
 */
public class Entry implements Cloneable {
	// 對應title節點，用於儲存日曆的標題
	@Key
	public String title;

	// 對應summary節點，用於儲存日曆的說明
	@Key
	public String summary;

	// 對應updated節點，用於儲存日曆的更新日期
	@Key
	public String updated;

	// 對應published節點，用於儲存日曆的發佈日期
	@Key
	public String published;
	
	// 對應link節點，用於儲存日曆包含的各種link連結，如使用者更新的link等
	@Key("link")
	public List<Link>links;

	@Override
	protected Entry clone() {
		return DataUtil.clone(this);
	}

	/**
	 * 執行日曆的刪除操作
	 * @param transport
	 * @throws IOException
	 */
	public void executeDelete(HttpTransport transport) throws IOException {
		// 新建HTTP DELETE請求
		HttpRequest request=transport.buildDeleteRequest();
		// 設置URL位址
		request.setUrl(getEditLink());
		// 執行請求操作
		RequestHandler.execute(request).ignore();
	}

	/**
	 * 執行日曆的新建操作
	 * @param transport
	 * @param url
	 * @return
	 * @throws IOException
	 */
	protected Entry executeInsert(HttpTransport transport, CalendarUrl url)
			throws IOException {
		// 新建HTTP POST請求
		HttpRequest request=transport.buildPostRequest();
		// 設置URL位址
		request.url=url;
		
		// 設置數據
		AtomContent content=new AtomContent();
		content.namespaceDictionary=Namespace.DICTIONARY;
		content.entry=this;
		request.content=content;
		
		// 執行請求
		return RequestHandler.execute(request).parseAs(getClass());
	}
	
	/**
	 * 通過PATCH請求進行日曆的更新操作
	 * @param transport
	 * @param original
	 * @return
	 * @throws IOException
	 */
	protected Entry executePatchRelativeToOriginal(HttpTransport transport,
			Entry original) throws IOException {
		// 新建HTTP PATCH請求
		HttpRequest request=transport.buildPatchRequest();
		// 設置URL位址
		request.setUrl(getEditLink());
		
		// 設置用於更新的資料
		AtomPatchRelativeToOriginalContent content=new AtomPatchRelativeToOriginalContent();
		content.namespaceDictionary=Namespace.DICTIONARY;
		content.originalEntry=original;
		content.patchedEntry=this;
		request.content=content;
		
		// 執行請求
		return RequestHandler.execute(request).parseAs(getClass());
	}

	/**
	 * 獲取更新日曆的link連結位址
	 * @return
	 */
	private String getEditLink() {
		return CalendarUtils.find(links, "edit");
	}
}

