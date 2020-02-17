package com.uppowerstudio.chapter5.phonebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 資料庫操作工具類
 * @author UPPower Studio
 *
 */
public class MyDbHelper extends SQLiteOpenHelper {
	

	// 聲明資料庫名字符串常量
	private static final String DB_NAME = "phonebook.db3";

	
	// 聲明用於記錄Log的標誌
	private static final String TAG = "Sample5-10";
	
	/**
	 * 定義構造方法，用於創建資料庫
	 * @param context
	 * @param version
	 */
	public MyDbHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}
	
	/**
	 * 第一次創建資料庫後調用，可在此方法中完成資料庫表的創建
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 輸出日誌
		Log.d(TAG, "Begin execute onCreate, cretating database table......");
		try{
			// 啓用事務
			db.beginTransaction();
			
			// 構建創建聯繫人表的SQL語句
			String sql = "CREATE TABLE TBL_CONTACTS(_id INTEGER PRIMARY KEY, contact_name TEXT NOT NULL, phone_number TEXT NOT NULL)";
			
			// 執行SQL語句
			db.execSQL(sql);
			
			// 設置事務成功標誌
			db.setTransactionSuccessful();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			// 結束事務
			db.endTransaction();
		}
		
		Log.d(TAG, "Execute onCreate completed. Database created success.");
	}

	/**
	 * 完成資料庫結構的更新操作
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
