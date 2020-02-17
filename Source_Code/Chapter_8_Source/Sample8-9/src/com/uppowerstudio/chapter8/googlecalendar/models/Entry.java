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
 * Java�ҫ����O�A����Atom XML����entry�`�I
 * @author UPPower Studio
 *
 */
public class Entry implements Cloneable {
	// ����title�`�I�A�Ω��x�s��䪺���D
	@Key
	public String title;

	// ����summary�`�I�A�Ω��x�s��䪺����
	@Key
	public String summary;

	// ����updated�`�I�A�Ω��x�s��䪺��s���
	@Key
	public String updated;

	// ����published�`�I�A�Ω��x�s��䪺�o�G���
	@Key
	public String published;
	
	// ����link�`�I�A�Ω��x�s���]�t���U��link�s���A�p�ϥΪ̧�s��link��
	@Key("link")
	public List<Link>links;

	@Override
	protected Entry clone() {
		return DataUtil.clone(this);
	}

	/**
	 * �����䪺�R���ާ@
	 * @param transport
	 * @throws IOException
	 */
	public void executeDelete(HttpTransport transport) throws IOException {
		// �s��HTTP DELETE�ШD
		HttpRequest request=transport.buildDeleteRequest();
		// �]�mURL��}
		request.setUrl(getEditLink());
		// ����ШD�ާ@
		RequestHandler.execute(request).ignore();
	}

	/**
	 * �����䪺�s�ؾާ@
	 * @param transport
	 * @param url
	 * @return
	 * @throws IOException
	 */
	protected Entry executeInsert(HttpTransport transport, CalendarUrl url)
			throws IOException {
		// �s��HTTP POST�ШD
		HttpRequest request=transport.buildPostRequest();
		// �]�mURL��}
		request.url=url;
		
		// �]�m�ƾ�
		AtomContent content=new AtomContent();
		content.namespaceDictionary=Namespace.DICTIONARY;
		content.entry=this;
		request.content=content;
		
		// ����ШD
		return RequestHandler.execute(request).parseAs(getClass());
	}
	
	/**
	 * �q�LPATCH�ШD�i���䪺��s�ާ@
	 * @param transport
	 * @param original
	 * @return
	 * @throws IOException
	 */
	protected Entry executePatchRelativeToOriginal(HttpTransport transport,
			Entry original) throws IOException {
		// �s��HTTP PATCH�ШD
		HttpRequest request=transport.buildPatchRequest();
		// �]�mURL��}
		request.setUrl(getEditLink());
		
		// �]�m�Ω��s�����
		AtomPatchRelativeToOriginalContent content=new AtomPatchRelativeToOriginalContent();
		content.namespaceDictionary=Namespace.DICTIONARY;
		content.originalEntry=original;
		content.patchedEntry=this;
		request.content=content;
		
		// ����ШD
		return RequestHandler.execute(request).parseAs(getClass());
	}

	/**
	 * �����s��䪺link�s����}
	 * @return
	 */
	private String getEditLink() {
		return CalendarUtils.find(links, "edit");
	}
}

