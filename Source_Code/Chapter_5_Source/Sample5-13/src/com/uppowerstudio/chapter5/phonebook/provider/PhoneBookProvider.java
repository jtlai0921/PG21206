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
 * ²���q��ï���e���Ѿ�
 * @author UPPower Studio
 *
 */
public class PhoneBookProvider extends ContentProvider implements Constants {
	
	// �ŧi�ܼ�
	private MyDbHelper dbHelper;
	private SQLiteDatabase sqliteDB;

	// �ŧiURI�������ܼơA�Ω�B�z�ǤJ��URI
	private static final UriMatcher uriMatcher;
	// ���͹��UriMatcher
	static {
		uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
		// �K�[��ǤJ�H"phonebook"������URI�ɱN��ܩҦ����p���H�O��
		uriMatcher.addURI(AUTHORITY, "phonebook", Constants.CONTACT);
		// �K�[��ǤJ�H"phonebook"�["/�Ʀ�"�榡������URI�A�p"phonebook/1"�ɪ��ID=1���p���H�O��
		uriMatcher.addURI(AUTHORITY, "phonebook/#", Constants.CONTACT_ID);
	}

	/**
	 * �R���p���H
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int resultCode=-1;
		// ����URI����
		switch (uriMatcher.match(uri)) {
		case CONTACT:
			// ���������Ҧ��p���H�ɡA�R���������p���H���
			resultCode=sqliteDB.delete(TABLE_NAME, selection, selectionArgs);
			break;
		case CONTACT_ID:
			// ���������Y���p���H�A�������ID��
			String id=uri.getPathSegments().get(1);
			// ����R���ާ@
			resultCode=sqliteDB.delete(TABLE_NAME, COLUMN_ID+"="+id,
					selectionArgs);
			break;
		default:
			// �p�ǤJ��URI���O�W�z��������ɩߥX���`
			throw new IllegalArgumentException("Unsupported URI: "+uri);
		}
		
		// ���UContentResolver�A���Ƶo�ͧ�ʮɶi��q��
		getContext().getContentResolver().notifyChange(uri, null);
		return resultCode;
	}

	/**
	 * �q�L�����ǤJ��URI�A��^�Q�����String
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
		// ���J�ާ@
		long rid=sqliteDB.insert(TABLE_NAME, COLUMN_ID, values);
		// ���J���\��A�N��^��ID�Ȫ��[��URI����A�õ��UContentResolver
		if (rid > 0) {
			Uri resultUri=ContentUris.withAppendedId(CONTENT_URI, rid);
			getContext().getContentResolver().notifyChange(resultUri, null);
			return resultUri;
		}
		// ���J���ѩߥX���`
		throw new SQLException("Insert contact into database failed. "+uri);

	}

	@Override
	public boolean onCreate() {
		// ������Ʈw����l�ƾާ@�A��^true��ܪ�l�Ʀ��\
		dbHelper=new MyDbHelper(getContext(), DB_VERSION);
		sqliteDB=dbHelper.getWritableDatabase();
		return (sqliteDB == null) ? false : true;
	}

	/**
	 * �d�߾ާ@
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor=null;
		// �����ǤJ��URI���椣�P���d��
		switch (uriMatcher.match(uri)) {
		case CONTACT:
			// �d�ߩҦ��p���H
			cursor=sqliteDB.query(TABLE_NAME, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case CONTACT_ID:
			// �q�LID�d�߯S�w���p���H
			String id=uri.getPathSegments().get(1);
			cursor=sqliteDB.query(TABLE_NAME, projection, COLUMN_ID+"="
					+ id, selectionArgs, null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI: "+uri);
		}

		// ���UContentResolver
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	/**
	 * ��s�ާ@
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int resultCode=-1;
		// �����ǤJ��URI�A�p�G�O��ܩҦ��p���H��URI�A�h�ߥX���`
		if (CONTACT == uriMatcher.match(uri)) {
			throw new IllegalArgumentException("Unsupported URI: "+uri);
		} else if (CONTACT_ID == uriMatcher.match(uri)) {
			// ���ID�A�����s�ާ@
			String id=uri.getPathSegments().get(1);
			resultCode=sqliteDB.update(TABLE_NAME, values, COLUMN_ID+"="
					+ id, selectionArgs);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return resultCode;
	}

}
