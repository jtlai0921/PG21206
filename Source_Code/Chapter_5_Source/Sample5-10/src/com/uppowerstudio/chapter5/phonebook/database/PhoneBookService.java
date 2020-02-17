package com.uppowerstudio.chapter5.phonebook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 服務層
 * @author UPPower Studio
 *
 */
public class PhoneBookService {
	// 定義資料庫版本號
	private static final int DATABASE_VERSION=1;
	// 定義表名
	private static final String TABLE_NAME="TBL_CONTACTS";

	// 宣告SQLiteDatabase對象
	private SQLiteDatabase sqliteDatabase;
	// 宣告MyDbHelper工具類物件
	private MyDbHelper dbHelper;

	/**
	 * 建構方式，用於初始化資料庫
	 * @param ctx
	 */
	public PhoneBookService(Context ctx) {
		dbHelper=new MyDbHelper(ctx, DATABASE_VERSION);
		sqliteDatabase=dbHelper.getWritableDatabase();
	}

	/**
	 * 儲存聯絡人
	 * @param contacts
	 * @return
	 */
	public long saveContact(ContentValues contacts) {
		long result=sqliteDatabase.insert(TABLE_NAME, "_id", contacts);
		return result;
	}

	/**
	 * 獲取所有聯絡人
	 * @return
	 */
	public Cursor getContacts() {
		// 查詢tbl_contacts表所有資料，並按列_id昇冪排列
		Cursor c=sqliteDatabase.query(TABLE_NAME, null, null, null, null,
				null, "_id");
		return c;
	}

	/**
	 * 通過傳入的聯絡人ID查詢聯絡人資訊
	 * @param id
	 * @return
	 */
	public ContactModel getContactById(long id) {
		// 帶條件查詢資料，並按列_id昇冪排列
		Cursor c=sqliteDatabase.query(TABLE_NAME, null, "_id=?",
				new String[] { String.valueOf(id) }, null, null, null);

		ContactModel model=new ContactModel();
		
		// 從Cursor中讀取資料並儲存到聯絡人資料模型物件中
		if (c.moveToFirst()) {
			model.setId(c.getInt(c.getColumnIndex("_id")));
			model.setContactName(c.getString(c.getColumnIndex("contact_name")));
			model.setContactPhone(c.getString(c.getColumnIndex("phone_number")));
		}

		return model;
	}

	/**
	 * 更新聯絡人
	 * @param model
	 * @return
	 */
	public boolean updateContact(ContactModel model) {
		boolean result=false;

		try {
			// 定義用於更新聯絡人資訊的SQL語句
			String sql="update tbl_contacts set contact_name=?, phone_number=? where _id=?";
			
			// 執行更新操作
			sqliteDatabase.execSQL(sql, new String[] { model.getContactName(),
					model.getContactPhone(), String.valueOf(model.getId()) });
			
			result=true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 刪除聯絡人
	 * @param id
	 * @return
	 */
	public int deleteContact(long id){
		int result=sqliteDatabase.delete(TABLE_NAME, "_id=?", new String[]{String.
valueOf(id)});
		return result;
	}
}
