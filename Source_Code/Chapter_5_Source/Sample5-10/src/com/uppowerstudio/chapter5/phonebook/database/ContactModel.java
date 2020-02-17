package com.uppowerstudio.chapter5.phonebook.database;

import java.io.Serializable;

/**
 * �p���H�ƾڼҫ�
 * @author UPPower Studio
 *
 */
public class ContactModel implements Serializable {

	/**
	 * Serial Version Unique ID
	 */
	private static final long serialVersionUID = 3951632261980474202L;

	// �p���HID
	private long id;
	// �p���H�m�W
	private String contactName;
	// �p���H�q��
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
