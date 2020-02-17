package com.uppowerstudio.chapter5.phonebook.database;

import android.net.Uri;

/**
 * 定義ContentProvider使用的各種常量
 * @author UPPower Studio
 *
 */
public interface Constants {
	// 定義資料庫名稱、表名及版本號
	public static final String TABLE_NAME="TBL_CONTACTS";
	public static final String DB_NAME="phonebook.db3";
	public static final int DB_VERSION=1;

	// 定義表字段名
	public static final String COLUMN_ID="_id";
	public static final String COLUMN_NAME="contact_name";
	public static final String COLUMN_PHONE="phone_number";

	// 定義標識ContentProvider的字串常量
	public static final String AUTHORITY="com.uppowerstudio.chapter5.phonebook.provider";
	// 定義用於區分不同的URI請求的常量
	public static final int CONTACT=1;
	public static final int CONTACT_ID=2;

	// 定義ContentProvider的URI，其他應用通過這個URI來進行存取
	public static final Uri CONTENT_URI=Uri
			.parse("content://com.uppowerstudio.chapter5.phonebook.provider/phonebook");

	// 定義資料的MIME類型字串常量
	public static final String CONTENT_TYPE_ALL="vnd.android.cursor.dir/vnd.uppowerstudio.phonebook";
	public static final String CONTENT_TYPE_ITEM="vnd.android.cursor.item/vnd.uppowerstudio.phonebook";
}
