package com.uppowerstudio.chapter5.phonebook;

import com.uppowerstudio.chapter5.phonebook.database.ContactModel;
import com.uppowerstudio.chapter5.phonebook.database.PhoneBookService;

import android.app.Activity;
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
public class UpdateContactActivity extends Activity {
    
	// 宣告控制項變數
	private Button updateButton;
	private Button cancelButton;
	private EditText editUpdateContactName;
	private EditText editUpdateContactPhone;
	
	// 宣告用於儲存當前聯絡人ID的變數
	private long uid;
	
	// 宣告服務層變數
	private PhoneBookService service;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 載入佈局檔update_contact.xml
        setContentView(R.layout.update_contact);
        
        // 產生實例服務層物件
        service=new PhoneBookService(getBaseContext());
        
        // 初始化控制項
        updateButton=(Button)findViewById(R.id.button_update);
        cancelButton=(Button)findViewById(R.id.button_cancel);
        editUpdateContactName=(EditText)findViewById(R.id.update_contact_name);
        editUpdateContactPhone=(EditText)findViewById(R.id.update_contact_phone);
        
        // 對取消按鈕添加事件監聽器
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 結束更新聯絡人介面，返回聯絡人清單介面
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
        
        // 顯示聯絡人資訊
        fillContactInfo();
    }
    
    /**
     * 顯示聯絡人資訊
     */
    private void fillContactInfo(){
    	// 取得從聯絡人列表傳入的當前聯絡人ID值
    	uid=getIntent().getLongExtra("uid", 0L);
    	
    	// 呼叫服務層方法getContactById，通過聯絡人ID查詢資訊
    	ContactModel model=service.getContactById(uid);
    	
    	// 填充聯絡人資訊到對應控制項
    	if (model != null){
    		editUpdateContactName.setText(model.getContactName());
    		editUpdateContactPhone.setText(model.getContactPhone());
    	}
    }
    
    /**
     * 更新聯絡人
     */
    private void doUpdateContact(){
    		// 獲取要更新的資料
    		String contactName=editUpdateContactName.getText().toString().trim();
    		String contactPhone=editUpdateContactPhone.getText().toString().trim();
    	
    		// 建構資料模型
    		ContactModel model=new ContactModel();
    		model.setId(uid);
    		model.setContactName(contactName);
    		model.setContactPhone(contactPhone);
    	
    		// 呼叫服務層方法updateContact對聯絡人進行更新
    		boolean result=service.updateContact(model);
    		// 以Toast消息的方式對更新結果進行顯示
    		if (result){
Toast.makeText(getBaseContext(), getString(R.string.msg_update_success), Toast.LENGTH_LONG).show();
    			finish();
   	 	}else{
Toast.makeText(getBaseContext(), getString(R.string.msg_update_failure), Toast.LENGTH_LONG).show();
   	 	}    	
    }    
}
