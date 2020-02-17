package com.uppowerstudio.chapter8.googlecalendar.models;

import java.io.Serializable;

/**
 * 日曆活動事件數據Bean(用於ListView顯示及數據適配器使用)
 * @author UPPower Studio
 *
 */
public class CalendarEventModel implements Serializable{
	
	private static final long serialVersionUID = 5648883060126405305L;
	private String title;
	private String content;
	private String eventDate;
	private String startTime;
	private String endTime;
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
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
