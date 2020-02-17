package com.uppowerstudio.chapter8.googlecalendar.models;

import java.io.Serializable;

/**
 * ���ƾ�Bean(�Ω�ListView��ܤμƾھA�t���ϥ�)
 * @author UPPower Studio
 *
 */
public class CalendarModel implements Serializable {

	private static final long serialVersionUID = -8394186468485465303L;
	
	private String id;
	private String title;
	private String summary;
	private String timeZone;
	private String location;
	private String color;
	private String publishedDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
}
