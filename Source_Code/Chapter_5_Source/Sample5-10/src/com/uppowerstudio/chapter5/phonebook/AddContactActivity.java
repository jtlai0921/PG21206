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
 * �K�[�p���H
 * @author UPPower Studio
 *
 */
public class AddContactActivity extends Activity {
    
	// �ŧi����ܼ�
	private Button addButton;
	private Button cancelButton;
	private EditText editContactName;
	private EditText editContactPhone;
	
	// �ŧi�A�ȼh�ܼ�
	private PhoneBookService service;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ���J�G����add_contact.xml
        setContentView(R.layout.add_contact);
        
        // ���͹�ҪA�ȼh����
        service=new PhoneBookService(getBaseContext());
        
        // ��l�Ʊ��
        addButton=(Button)findViewById(R.id.button_add);
        cancelButton=(Button)findViewById(R.id.button_cancel);
        editContactName=(EditText)findViewById(R.id.edit_contact_name);
        editContactPhone=(EditText)findViewById(R.id.edit_contact_phone);
        
        // ��������s�K�[�ƥ��ť��
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �����K�[�p���H�����A��^�p���H�M�椶��
				finish();
			}
        });
        
        // ��K�[���s�K�[�ƥ��ť��
        addButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			// ����K�[�p���H�ާ@
				doSaveContact();
			}
        });
    }
    
    /**
     * �K�[�p���H
     */
    private void doSaveContact(){
    		// ����n���J�����
    		String contactName=editContactName.getText().toString().trim();
    		String contactPhone=editContactPhone.getText().toString().trim();
    	
    		// �غcContentValues����A�ñN�n���J����ƥH��ȹ諸�Φ��i���x�s
    		ContentValues contact=new ContentValues();
    		contact.put("contact_name", contactName);
    		contact.put("phone_number", contactPhone);
    	
    		// �I�s�A�ȼh��ksaveContact�����K�[�ާ@
    		long result=service.saveContact(contact);
    		// �HToast�������覡��K�[���G�i�����
    		if (result != 0){
    			Toast.makeText(getBaseContext(), getString(R.string.msg_add_success), Toast.LENGTH_LONG).show();
    			finish();
   	 	}else{
   	 		Toast.makeText(getBaseContext(), getString(R.string.msg_add_failure), Toast.LENGTH_LONG).show();
    		}
    }    
}
