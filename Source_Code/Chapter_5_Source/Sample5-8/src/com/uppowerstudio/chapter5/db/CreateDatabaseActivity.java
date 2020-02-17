package com.uppowerstudio.chapter5.db;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CreateDatabaseActivity extends Activity {
    // 定義資料庫名
	public static final String DATABASE_NAME="db_1";
	
	// 宣告SQLiteDatabase對象
	private SQLiteDatabase sqliteDatabase;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 初始化控制項
        Button createButton=(Button)findViewById(R.id.button_create_db);
        Button closeButton=(Button)findViewById(R.id.button_close_db);
        Button deleteButton=(Button)findViewById(R.id.button_delete_db);
        
        // 註冊新增資料庫按鈕事件監聽器
        createButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 呼叫靜態方法新增資料庫
				sqliteDatabase=openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
				
				// 顯示提示消息，如果sqliteDatabase物件不為null，則表明新增或打開資料庫成功
				if (sqliteDatabase != null){
					Toast.makeText(getBaseContext(), getString(R.string.msg_create_success)+DATABASE_NAME, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getBaseContext(), getString(R.string.msg_create_failure)+DATABASE_NAME, Toast.LENGTH_LONG).show();
				}
			}
        });
        
        // 註冊關閉資料庫按鈕事件監聽器
        closeButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 呼叫SQLiteDatabase物件的close方法關閉已打開的資料庫
				sqliteDatabase.close();
				
				// 顯示提示消息
				Toast.makeText(getBaseContext(),getString(R.string.msg_close_success)+DATABASE_NAME, Toast.LENGTH_LONG).show();
			}
        });
        
        // 註冊刪除資料庫按鈕事件監聽器
        deleteButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 呼叫Context中的方法deleteDatabase刪除資料庫
				boolean isDeleted=deleteDatabase(DATABASE_NAME);
				
				// 顯示提示消息，刪除成功後返回true，否則為false
				if (isDeleted){
					Toast.makeText(getBaseContext(), 
							getString(R.string.msg_delete_success)+DATABASE_NAME, 
							Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getBaseContext(),getString(R.string. msg_delete_failure), Toast.LENGTH_LONG).show();
				}
			}
        });
    }
}
