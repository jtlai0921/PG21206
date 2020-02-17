package com.uppowerstudio.chapter5.phonebook;

import com.uppowerstudio.chapter5.phonebook.database.PhoneBookService;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 添加聯絡人
 * @author UPPower Studio
 *
 */
public class AddContactActivity extends Activity {
    
	// 宣告控制項變數
	private Button addButton;
	private Button cancelButton;
	private EditText editContactName;
	private EditText editContactPhone;
	
	// 宣告服務層變數
	private PhoneBookService service;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 載入佈局檔add_contact.xml
        setContentView(R.layout.add_contact);
        
        // 產生實例服務層物件
        service=new PhoneBookService(getBaseContext());
        
        // 初始化控制項
        addButton=(Button)findViewById(R.id.button_add);
        cancelButton=(Button)findViewById(R.id.button_cancel);
        editContactName=(EditText)findViewById(R.id.edit_contact_name);
        editContactPhone=(EditText)findViewById(R.id.edit_contact_phone);
        
        // 對取消按鈕添加事件監聽器
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 結束添加聯絡人介面，返回聯絡人清單介面
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
    		// 獲取要插入的資料
    		String contactName=editContactName.getText().toString().trim();
    		String contactPhone=editContactPhone.getText().toString().trim();
    	
    		// 建構ContentValues物件，並將要插入的資料以鍵值對的形式進行儲存
    		ContentValues contact=new ContentValues();
    		contact.put("contact_name", contactName);
    		contact.put("phone_number", contactPhone);
    	
    		// 呼叫服務層方法saveContact完成添加操作
    		long result=service.saveContact(contact);
    		// 以Toast消息的方式對添加結果進行顯示
    		if (result != 0){
    			Toast.makeText(getBaseContext(), getString(R.string.msg_add_success), Toast.LENGTH_LONG).show();
    			finish();
   	 	}else{
   	 		Toast.makeText(getBaseContext(), getString(R.string.msg_add_failure), Toast.LENGTH_LONG).show();
    		}
    }    
}
