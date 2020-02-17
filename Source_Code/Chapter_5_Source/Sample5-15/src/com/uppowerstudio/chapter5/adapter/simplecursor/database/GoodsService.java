package com.uppowerstudio.chapter5.adapter.simplecursor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 資料庫存取碼
 * @author UPPower Studio
 *
 */
public class GoodsService {
	private static final String DATABASE_NAME="DB5_15.db";
	private static final String TABLE_NAME="TBL_GOODS";

	private SQLiteDatabase sqliteDatabase;

	public GoodsService(Context context) {
		context.deleteDatabase(DATABASE_NAME);
		
		// 新增資料庫
		sqliteDatabase=context.openOrCreateDatabase(DATABASE_NAME,
				Context.MODE_PRIVATE, null);
		
		// 新增表及初始資料
		createTableAndInitData();
	}

	/**
	 * 新增資料庫表及初始化資料
	 */
	private void createTableAndInitData() {
		// 新增表
		String sql="CREATE TABLE "
				+ TABLE_NAME
				+ "(_id INTEGER PRIMARY KEY, goods_name TEXT NOT NULL, goods_status TEXT NOT NULL)";
		if (sqliteDatabase != null) {
			sqliteDatabase.execSQL(sql);
		}

		// 新增初始化資料
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
	 * 查詢資料
	 * @return Cursor
	 */
	public Cursor getGoods() {
		Cursor c=sqliteDatabase.query(TABLE_NAME, null, null, null, null,
				null, "_id");
		return c;
	}

}
