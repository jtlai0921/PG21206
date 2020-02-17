package com.uppowerstudio.chapter5.phonebook;

import com.uppowerstudio.chapter5.phonebook.database.Constants;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 使用ContentResolver方式添加聯絡人
 * @author UPPower Studio
 *
 */
public class AddContactActivity extends Activity {
    
	// 宣告控件變量
	private Button addButton;
	private Button cancelButton;
	private EditText editContactName;
	private EditText editContactPhone;
	
	// 宣告ContentResolver變量
	private ContentResolver contentResolver;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 加載佈局文件add_contact.xml
        setContentView(R.layout.add_contact);
        
        // 獲取ContentResolver對象
        contentResolver = getContentResolver();
        
        // 初始化控件
        addButton = (Button)findViewById(R.id.button_add);
        cancelButton = (Button)findViewById(R.id.button_cancel);
        editContactName = (EditText)findViewById(R.id.edit_contact_name);
        editContactPhone = (EditText)findViewById(R.id.edit_contact_phone);
        
        // 對取消按鈕添加事件監聽器
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 結束添加聯絡人界面，返回聯絡人列表界面
				finish();
			}
        });
        
        // 對添加按鈕添加事件監聽器
        addButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 執行添加聯絡人操作
				doSaveContact();
			}
        });
    }
    
    /**
     * 添加聯絡人
     */
    private void doSaveContact(){
    	// 獲取要插入的數據
    	String contactName = editContactName.getText().toString().trim();
    	String contactPhone = editContactPhone.getText().toString().trim();
    	
    	// 構建ContentValues對象，並將要插入的數據以鍵值對的形式進行保存
    	ContentValues contact = new ContentValues();
    	contact.put("contact_name", contactName);
    	contact.put("phone_number", contactPhone);
    	
    	// 調用ContentProvider完成添加操作
    	Uri resultUri = contentResolver.insert(Constants.CONTENT_URI, contact); 
    	String resultCode = resultUri.getPathSegments().get(1);	
    	int result = Integer.parseInt(resultCode);
    	
    	// 以Toast消息的方式對添加結果進行顯示
    	if (result != 0){
    		Toast.makeText(getBaseContext(), getString(R.string.msg_add_success), Toast.LENGTH_LONG).show();
    		finish();
    	}else{
    		Toast.makeText(getBaseContext(), getString(R.string.msg_add_failure), Toast.LENGTH_LONG).show();
    	}
    	
    }
    
}