package com.uppowerstudio.chapter5.adapter.simplecursor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��Ʈw�s���X
 * @author UPPower Studio
 *
 */
public class GoodsService {
	private static final String DATABASE_NAME="DB5_15.db";
	private static final String TABLE_NAME="TBL_GOODS";

	private SQLiteDatabase sqliteDatabase;

	public GoodsService(Context context) {
		context.deleteDatabase(DATABASE_NAME);
		
		// �s�W��Ʈw
		sqliteDatabase=context.openOrCreateDatabase(DATABASE_NAME,
				Context.MODE_PRIVATE, null);
		
		// �s�W��Ϊ�l���
		createTableAndInitData();
	}

	/**
	 * �s�W��Ʈw��Ϊ�l�Ƹ��
	 */
	private void createTableAndInitData() {
		// �s�W��
		String sql="CREATE TABLE "
				+ TABLE_NAME
				+ "(_id INTEGER PRIMARY KEY, goods_name TEXT NOT NULL, goods_status TEXT NOT NULL)";
		if (sqliteDatabase != null) {
			sqliteDatabase.execSQL(sql);
		}

		// �s�W��l�Ƹ��
		for (int i=1; i <= 6; i++) {
			ContentValues cv=new ContentValues();
			cv.put("goods_name", "name0"+i);
			if (i % 2 == 0) {
				cv.put("goods_status", "N");
			} else {
				cv.put("goods_status", "Y");
			}
			sqliteDatabase.insert(TABLE_NAME, "_id", cv);
		}
	}

	/**
	 * �d�߸��
	 * @return Cursor
	 */
	public Cursor getGoods() {
		Cursor c=sqliteDatabase.query(TABLE_NAME, null, null, null, null,
				null, "_id");
		return c;
	}

}
