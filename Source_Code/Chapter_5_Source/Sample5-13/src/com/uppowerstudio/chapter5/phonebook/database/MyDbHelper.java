package com.uppowerstudio.chapter5.phonebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库操作工具类
 * @author UPPower Studio
 *
 */
public class MyDbHelper extends SQLiteOpenHelper {
	

	// 声明数据库名字符串常量
	private static final String DB_NAME = "phonebook.db3";

	
	// 声明用于记录Log的标志
	private static final String TAG = "Sample5-10";
	
	/**
	 * 定义构造方法，用于创建数据库
	 * @param context
	 * @param version
	 */
	public MyDbHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}
	
	/**
	 * 第一次创建数据库后调用，可在此方法中完成数据库表的创建
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 输出日志
		Log.d(TAG, "Begin execute onCreate, cretating database table......");
		try{
			// 启用事务
			db.beginTransaction();
			
			// 构建创建联系人表的SQL语句
			String sql = "CREATE TABLE TBL_CONTACTS(_id INTEGER PRIMARY KEY, contact_name TEXT NOT NULL, phone_number TEXT NOT NULL)";
			
			// 执行SQL语句
			db.execSQL(sql);
			
			// 设置事务成功标志
			db.setTransactionSuccessful();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			// 结束事务
			db.endTransaction();
		}
		
		Log.d(TAG, "Execute onCreate completed. Database created success.");
	}

	/**
	 * 完成数据库结构的更新操作
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
