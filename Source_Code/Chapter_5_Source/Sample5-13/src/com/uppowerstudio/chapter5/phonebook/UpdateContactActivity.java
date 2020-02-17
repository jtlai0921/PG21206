package com.uppowerstudio.chapter5.phonebook;


import com.uppowerstudio.chapter5.phonebook.database.Constants;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 更新聯絡人
 * @author UPPower Studio
 *
 */
public class UpdateContactActivity extends Activity implements Constants{
    
	// 宣告控件變量
	private Button updateButton;
	private Button cancelButton;
	private EditText editUpdateContactName;
	private EditText editUpdateContactPhone;
	
	// 宣告用於存儲當前聯絡人ID的變量
	private long uid;
	
	// 宣告ContentResolver變量
	private ContentResolver contentResolver;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 加載佈局文件update_contact.xml
        setContentView(R.layout.update_contact);
        
     // 獲取ContentResolver對象
		contentResolver = getContentResolver();
        
        // 初始化控件
        updateButton = (Button)findViewById(R.id.button_update);
        cancelButton = (Button)findViewById(R.id.button_cancel);
        editUpdateContactName = (EditText)findViewById(R.id.update_contact_name);
        editUpdateContactPhone = (EditText)findViewById(R.id.update_contact_phone);
        
        // 對取消按鈕添加事件監聽器
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 結束更新聯絡人界面，返回聯絡人列表界面
				finish();
			}
        });
        
        // 對更新按鈕添加事件監聽器
        updateButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 執行更新聯絡人操作
				doUpdateContact();
			}
        });
        
        // 顯示聯絡人信息
        fillContactInfo();
    }
    
    /**
     * 顯示聯絡人信息
     */
    private void fillContactInfo(){
    	// 取得從聯絡人列表傳入的當前聯絡人ID值
    	uid = getIntent().getLongExtra("uid", 0L);
    	
    	// 調用ContentProvider，通過聯絡人ID查詢信息
    	Uri uri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(uid));
    	Cursor c = contentResolver.query(uri, null, null, null, COLUMN_ID);
    	
    	// 填充聯絡人信息到相應控件
    	if (c.moveToFirst()){
    		editUpdateContactName.setText(c.getString(c.getColumnIndex(COLUMN_NAME)));
    		editUpdateContactPhone.setText(c.getString(c.getColumnIndex(COLUMN_PHONE)));
    	}
    }
    
    /**
     * 更新聯絡人
     */
    private void doUpdateContact(){
    	// 獲取要更新的數據
    	String contactName = editUpdateContactName.getText().toString().trim();
    	String contactPhone = editUpdateContactPhone.getText().toString().trim();
    	
    	// 構建數據模型
    	ContentValues contact = new ContentValues();
    	contact.put("contact_name", contactName);
    	contact.put("phone_number", contactPhone);
    	
    	// 調用ContentProvider方法對聯絡人進行更新
    	Uri uri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(uid));
    	int result = contentResolver.update(uri, contact, null, null);
    	
    	// 以Toast消息的方式對更新結果進行顯示
    	if (result > 0){
    		Toast.makeText(getBaseContext(), getString(R.string.msg_update_success), Toast.LENGTH_LONG).show();
    		finish();
    	}else{
    		Toast.makeText(getBaseContext(), getString(R.string.msg_update_failure), Toast.LENGTH_LONG).show();
    	}
    	
    }
    
}