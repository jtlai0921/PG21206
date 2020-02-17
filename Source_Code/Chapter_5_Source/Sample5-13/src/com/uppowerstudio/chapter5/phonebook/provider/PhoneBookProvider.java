package com.uppowerstudio.chapter5.phonebook.provider;

import com.uppowerstudio.chapter5.phonebook.database.Constants;
import com.uppowerstudio.chapter5.phonebook.database.MyDbHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 簡易電話簿內容提供器
 * @author UPPower Studio
 *
 */
public class PhoneBookProvider extends ContentProvider implements Constants {
	
	// 宣告變數
	private MyDbHelper dbHelper;
	private SQLiteDatabase sqliteDB;

	// 宣告URI對應器變數，用於處理傳入的URI
	private static final UriMatcher uriMatcher;
	// 產生實例UriMatcher
	static {
		uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
		// 添加當傳入以"phonebook"結尾的URI時將表示所有的聯絡人記錄
		uriMatcher.addURI(AUTHORITY, "phonebook", Constants.CONTACT);
		// 添加當傳入以"phonebook"加"/數位"格式結尾的URI，如"phonebook/1"時表示ID=1的聯絡人記錄
		uriMatcher.addURI(AUTHORITY, "phonebook/#", Constants.CONTACT_ID);
	}

	/**
	 * 刪除聯絡人
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int resultCode=-1;
		// 對應URI類型
		switch (uriMatcher.match(uri)) {
		case CONTACT:
			// 當類型為所有聯絡人時，刪除全部的聯絡人資料
			resultCode=sqliteDB.delete(TABLE_NAME, selection, selectionArgs);
			break;
		case CONTACT_ID:
			// 當類型為某個聯絡人，先獲取其ID值
			String id=uri.getPathSegments().get(1);
			// 執行刪除操作
			resultCode=sqliteDB.delete(TABLE_NAME, COLUMN_ID+"="+id,
					selectionArgs);
			break;
		default:
			// 如傳入的URI不是上述兩種類型時拋出異常
			throw new IllegalArgumentException("Unsupported URI: "+uri);
		}
		
		// 註冊ContentResolver，當資料發生改動時進行通知
		getContext().getContentResolver().notifyChange(uri, null);
		return resultCode;
	}

	/**
	 * 通過對應傳入的URI，返回被支持的String
	 */
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case CONTACT:
			return Constants.CONTENT_TYPE_ALL;
		case CONTACT_ID:
			return Constants.CONTENT_TYPE_ITEM;
		default:
			throw new IllegalArgumentException("Unsupported URI: "+uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// 插入操作
		long rid=sqliteDB.insert(TABLE_NAME, COLUMN_ID, values);
		// 插入成功後，將返回的ID值附加到URI之後，並註冊ContentResolver
		if (rid > 0) {
			Uri resultUri=ContentUris.withAppendedId(CONTENT_URI, rid);
			getContext().getContentResolver().notifyChange(resultUri, null);
			return resultUri;
		}
		// 插入失敗拋出異常
		throw new SQLException("Insert contact into database failed. "+uri);

	}

	@Override
	public boolean onCreate() {
		// 完成資料庫的初始化操作，返回true表示初始化成功
		dbHelper=new MyDbHelper(getContext(), DB_VERSION);
		sqliteDB=dbHelper.getWritableDatabase();
		return (sqliteDB == null) ? false : true;
	}

	/**
	 * 查詢操作
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor=null;
		// 對應傳入的URI執行不同的查詢
		switch (uriMatcher.match(uri)) {
		case CONTACT:
			// 查詢所有聯絡人
			cursor=sqliteDB.query(TABLE_NAME, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case CONTACT_ID:
			// 通過ID查詢特定的聯絡人
			String id=uri.getPathSegments().get(1);
			cursor=sqliteDB.query(TABLE_NAME, projection, COLUMN_ID+"="
					+ id, selectionArgs, null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: "+uri);
		}

		// 註冊ContentResolver
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	/**
	 * 更新操作
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int resultCode=-1;
		// 對應傳入的URI，如果是表示所有聯絡人的URI，則拋出異常
		if (CONTACT == uriMatcher.match(uri)) {
			throw new IllegalArgumentException("Unsupported URI: "+uri);
		} else if (CONTACT_ID == uriMatcher.match(uri)) {
			// 獲取ID，執行更新操作
			String id=uri.getPathSegments().get(1);
			resultCode=sqliteDB.update(TABLE_NAME, values, COLUMN_ID+"="
					+ id, selectionArgs);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return resultCode;
	}

}
