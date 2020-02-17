package com.uppowerstudio.chapter8.googlesearch;

import java.io.Serializable;

public class SearchResultModel implements Serializable{
	
	private static final long serialVersionUID = 5280509884141292938L;
	
	private String title;
	private String content;
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
