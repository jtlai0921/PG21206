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
 * ��s�p���H
 * @author UPPower Studio
 *
 */
public class UpdateContactActivity extends Activity implements Constants{
    
	// �ŧi�����ܶq
	private Button updateButton;
	private Button cancelButton;
	private EditText editUpdateContactName;
	private EditText editUpdateContactPhone;
	
	// �ŧi�Ω�s�x��e�p���HID���ܶq
	private long uid;
	
	// �ŧiContentResolver�ܶq
	private ContentResolver contentResolver;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // �[���G�����update_contact.xml
        setContentView(R.layout.update_contact);
        
     // ���ContentResolver��H
		contentResolver = getContentResolver();
        
        // ��l�Ʊ���
        updateButton = (Button)findViewById(R.id.button_update);
        cancelButton = (Button)findViewById(R.id.button_cancel);
        editUpdateContactName = (EditText)findViewById(R.id.update_contact_name);
        editUpdateContactPhone = (EditText)findViewById(R.id.update_contact_phone);
        
        // ��������s�K�[�ƥ��ť��
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// ������s�p���H�ɭ��A��^�p���H�C��ɭ�
				finish();
			}
        });
        
        // ���s���s�K�[�ƥ��ť��
        updateButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �����s�p���H�ާ@
				doUpdateContact();
			}
        });
        
        // ����p���H�H��
        fillContactInfo();
    }
    
    /**
     * ����p���H�H��
     */
    private void fillContactInfo(){
    	// ���o�q�p���H�C��ǤJ����e�p���HID��
    	uid = getIntent().getLongExtra("uid", 0L);
    	
    	// �ե�ContentProvider�A�q�L�p���HID�d�߫H��
    	Uri uri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(uid));
    	Cursor c = contentResolver.query(uri, null, null, null, COLUMN_ID);
    	
    	// ��R�p���H�H�����������
    	if (c.moveToFirst()){
    		editUpdateContactName.setText(c.getString(c.getColumnIndex(COLUMN_NAME)));
    		editUpdateContactPhone.setText(c.getString(c.getColumnIndex(COLUMN_PHONE)));
    	}
    }
    
    /**
     * ��s�p���H
     */
    private void doUpdateContact(){
    	// ����n��s���ƾ�
    	String contactName = editUpdateContactName.getText().toString().trim();
    	String contactPhone = editUpdateContactPhone.getText().toString().trim();
    	
    	// �c�ؼƾڼҫ�
    	ContentValues contact = new ContentValues();
    	contact.put("contact_name", contactName);
    	contact.put("phone_number", contactPhone);
    	
    	// �ե�ContentProvider��k���p���H�i���s
    	Uri uri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(uid));
    	int result = contentResolver.update(uri, contact, null, null);
    	
    	// �HToast�������覡���s���G�i�����
    	if (result > 0){
    		Toast.makeText(getBaseContext(), getString(R.string.msg_update_success), Toast.LENGTH_LONG).show();
    		finish();
    	}else{
    		Toast.makeText(getBaseContext(), getString(R.string.msg_update_failure), Toast.LENGTH_LONG).show();
    	}
    	
    }
    
}