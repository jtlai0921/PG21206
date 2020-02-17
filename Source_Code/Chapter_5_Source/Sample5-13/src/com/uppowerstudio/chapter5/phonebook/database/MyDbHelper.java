package com.uppowerstudio.chapter5.phonebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * ���ݿ����������
 * @author UPPower Studio
 *
 */
public class MyDbHelper extends SQLiteOpenHelper {
	

	// �������ݿ����ַ�������
	private static final String DB_NAME = "phonebook.db3";

	
	// �������ڼ�¼Log�ı�־
	private static final String TAG = "Sample5-10";
	
	/**
	 * ���幹�췽�������ڴ������ݿ�
	 * @param context
	 * @param version
	 */
	public MyDbHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}
	
	/**
	 * ��һ�δ������ݿ����ã����ڴ˷�����������ݿ��Ĵ���
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// �����־
		Log.d(TAG, "Begin execute onCreate, cretating database table......");
		try{
			// ��������
			db.beginTransaction();
			
			// ����������ϵ�˱��SQL���
			String sql = "CREATE TABLE TBL_CONTACTS(_id INTEGER PRIMARY KEY, contact_name TEXT NOT NULL, phone_number TEXT NOT NULL)";
			
			// ִ��SQL���
			db.execSQL(sql);
			
			// ��������ɹ���־
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
	 * ������ݿ�ṹ�ĸ��²���
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
