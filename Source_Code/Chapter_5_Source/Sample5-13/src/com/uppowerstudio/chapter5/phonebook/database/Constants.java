package com.uppowerstudio.chapter5.phonebook.database;

import android.net.Uri;

/**
 * �w�qContentProvider�ϥΪ��U�ر`�q
 * @author UPPower Studio
 *
 */
public interface Constants {
	// �w�q��Ʈw�W�١B��W�Ϊ�����
	public static final String TABLE_NAME="TBL_CONTACTS";
	public static final String DB_NAME="phonebook.db3";
	public static final int DB_VERSION=1;

	// �w�q��r�q�W
	public static final String COLUMN_ID="_id";
	public static final String COLUMN_NAME="contact_name";
	public static final String COLUMN_PHONE="phone_number";

	// �w�q����ContentProvider���r��`�q
	public static final String AUTHORITY="com.uppowerstudio.chapter5.phonebook.provider";
	// �w�q�Ω�Ϥ����P��URI�ШD���`�q
	public static final int CONTACT=1;
	public static final int CONTACT_ID=2;

	// �w�qContentProvider��URI�A��L���γq�L�o��URI�Ӷi��s��
	public static final Uri CONTENT_URI=Uri
			.parse("content://com.uppowerstudio.chapter5.phonebook.provider/phonebook");

	// �w�q��ƪ�MIME�����r��`�q
	public static final String CONTENT_TYPE_ALL="vnd.android.cursor.dir/vnd.uppowerstudio.phonebook";
	public static final String CONTENT_TYPE_ITEM="vnd.android.cursor.item/vnd.uppowerstudio.phonebook";
}
