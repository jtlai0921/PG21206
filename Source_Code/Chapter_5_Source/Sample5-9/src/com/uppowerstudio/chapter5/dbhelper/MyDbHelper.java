package com.uppowerstudio.chapter5.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {	

	// �ŧi��Ʈw�W�r��`�q
	private static final String DB_NAME="db_2.db3";

	
	// �ŧi�Ω�O��Log���аO
	private static final String TAG="Sample5-9";
	
	/**
	 * �w�q�غc��k�A�Ω�s�W��Ʈw
	 * @param context
	 * @param version
	 */
	public MyDbHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}
	
	/**
	 * �Ĥ@���s�W��Ʈw��I�s�A�i�b����k��������Ʈw���s�W
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// ��X��x
		Log.d(TAG, "Begin execute onCreate, cretating database table...");
		try{
			// �ҥΰ���
			db.beginTransaction();
			
			// �غc�s�W��SQL�y�y
			String sql="CREATE TABLE TBL_USER(_id INTEGER PRIMARY KEY, name TEXT NOT NULL)";
			
			// ����SQL�y�y
			db.execSQL(sql);
			
			// �]�m���榨�\�аO
			db.setTransactionSuccessful();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			// ��������
			db.endTransaction();
		}
		
		Log.d(TAG, "Execute onCreate completed. Database created success.");
	}

	/**
	 * ������Ʈw���c����s�ާ@
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ��X��x
		Log.d(TAG, "Begin execute onUpgrade, alert the database schema... ");
		
		try{
			// �ҥΰ���
			db.beginTransaction();
			
			// �ק��SQL�y�y
			String upgradeSql="ALTER TABLE TBL_USER ADD COLUMN created_dt timestamp NOT NULL DEFAULT '2010-10-01' COLLATE NOCASE;";
			
			// ����SQL�y�y
			db.execSQL(upgradeSql);
			
			// �]�m���榨�\�аO
			db.setTransactionSuccessful();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			// ��������
			db.endTransaction();
		}
		Log.d(TAG, "Execute onUpgrade completed. Database schema updated success.");
	}

}
