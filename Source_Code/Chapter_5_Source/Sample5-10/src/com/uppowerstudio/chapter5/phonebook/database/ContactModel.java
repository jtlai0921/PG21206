package com.uppowerstudio.chapter5.phonebook.database;

import java.io.Serializable;

/**
 * 聯絡人數據模型
 * @author UPPower Studio
 *
 */
public class ContactModel implements Serializable {

	/**
	 * Serial Version Unique ID
	 */
	private static final long serialVersionUID = 3951632261980474202L;

	// 聯絡人ID
	private long id;
	// 聯絡人姓名
	private String contactName;
	// 聯絡人電話
	private String contactPhone;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
}
