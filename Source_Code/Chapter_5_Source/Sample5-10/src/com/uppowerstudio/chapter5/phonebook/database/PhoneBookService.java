package com.uppowerstudio.chapter5.phonebook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * �A�ȼh
 * @author UPPower Studio
 *
 */
public class PhoneBookService {
	// �w�q��Ʈw������
	private static final int DATABASE_VERSION=1;
	// �w�q��W
	private static final String TABLE_NAME="TBL_CONTACTS";

	// �ŧiSQLiteDatabase��H
	private SQLiteDatabase sqliteDatabase;
	// �ŧiMyDbHelper�u��������
	private MyDbHelper dbHelper;

	/**
	 * �غc�覡�A�Ω��l�Ƹ�Ʈw
	 * @param ctx
	 */
	public PhoneBookService(Context ctx) {
		dbHelper=new MyDbHelper(ctx, DATABASE_VERSION);
		sqliteDatabase=dbHelper.getWritableDatabase();
	}

	/**
	 * �x�s�p���H
	 * @param contacts
	 * @return
	 */
	public long saveContact(ContentValues contacts) {
		long result=sqliteDatabase.insert(TABLE_NAME, "_id", contacts);
		return result;
	}

	/**
	 * ����Ҧ��p���H
	 * @return
	 */
	public Cursor getContacts() {
		// �d��tbl_contacts��Ҧ���ơA�ë��C_id�@���ƦC
		Cursor c=sqliteDatabase.query(TABLE_NAME, null, null, null, null,
				null, "_id");
		return c;
	}

	/**
	 * �q�L�ǤJ���p���HID�d���p���H��T
	 * @param id
	 * @return
	 */
	public ContactModel getContactById(long id) {
		// �a����d�߸�ơA�ë��C_id�@���ƦC
		Cursor c=sqliteDatabase.query(TABLE_NAME, null, "_id=?",
				new String[] { String.valueOf(id) }, null, null, null);

		ContactModel model=new ContactModel();
		
		// �qCursor��Ū����ƨ��x�s���p���H��Ƽҫ�����
		if (c.moveToFirst()) {
			model.setId(c.getInt(c.getColumnIndex("_id")));
			model.setContactName(c.getString(c.getColumnIndex("contact_name")));
			model.setContactPhone(c.getString(c.getColumnIndex("phone_number")));
		}

		return model;
	}

	/**
	 * ��s�p���H
	 * @param model
	 * @return
	 */
	public boolean updateContact(ContactModel model) {
		boolean result=false;

		try {
			// �w�q�Ω��s�p���H��T��SQL�y�y
			String sql="update tbl_contacts set contact_name=?, phone_number=? where _id=?";
			
			// �����s�ާ@
			sqliteDatabase.execSQL(sql, new String[] { model.getContactName(),
					model.getContactPhone(), String.valueOf(model.getId()) });
			
			result=true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
	
	/**
	 * �R���p���H
	 * @param id
	 * @return
	 */
	public int deleteContact(long id){
		int result=sqliteDatabase.delete(TABLE_NAME, "_id=?", new String[]{String.
valueOf(id)});
		return result;
	}
}
