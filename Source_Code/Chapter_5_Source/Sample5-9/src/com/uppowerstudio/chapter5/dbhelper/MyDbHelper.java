package com.uppowerstudio.chapter5.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {	

	// 宣告資料庫名字串常量
	private static final String DB_NAME="db_2.db3";

	
	// 宣告用於記錄Log的標記
	private static final String TAG="Sample5-9";
	
	/**
	 * 定義建構方法，用於新增資料庫
	 * @param context
	 * @param version
	 */
	public MyDbHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}
	
	/**
	 * 第一次新增資料庫後呼叫，可在此方法中完成資料庫表的新增
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 輸出日誌
		Log.d(TAG, "Begin execute onCreate, cretating database table...");
		try{
			// 啟用執行
			db.beginTransaction();
			
			// 建構新增表的SQL語句
			String sql="CREATE TABLE TBL_USER(_id INTEGER PRIMARY KEY, name TEXT NOT NULL)";
			
			// 執行SQL語句
			db.execSQL(sql);
			
			// 設置執行成功標記
			db.setTransactionSuccessful();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			// 結束執行
			db.endTransaction();
		}
		
		Log.d(TAG, "Execute onCreate completed. Database created success.");
	}

	/**
	 * 完成資料庫結構的更新操作
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 輸出日誌
		Log.d(TAG, "Begin execute onUpgrade, alert the database schema... ");
		
		try{
			// 啟用執行
			db.beginTransaction();
			
			// 修改表的SQL語句
			String upgradeSql="ALTER TABLE TBL_USER ADD COLUMN created_dt timestamp NOT NULL DEFAULT '2010-10-01' COLLATE NOCASE;";
			
			// 執行SQL語句
			db.execSQL(upgradeSql);
			
			// 設置執行成功標記
			db.setTransactionSuccessful();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			// 結束執行
			db.endTransaction();
		}
		Log.d(TAG, "Execute onUpgrade completed. Database schema updated success.");
	}

}
