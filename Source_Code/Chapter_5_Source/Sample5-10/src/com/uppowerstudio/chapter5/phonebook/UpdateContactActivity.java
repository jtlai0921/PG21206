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
 * ��s�p���H
 * @author UPPower Studio
 *
 */
public class UpdateContactActivity extends Activity {
    
	// �ŧi����ܼ�
	private Button updateButton;
	private Button cancelButton;
	private EditText editUpdateContactName;
	private EditText editUpdateContactPhone;
	
	// �ŧi�Ω��x�s��e�p���HID���ܼ�
	private long uid;
	
	// �ŧi�A�ȼh�ܼ�
	private PhoneBookService service;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ���J�G����update_contact.xml
        setContentView(R.layout.update_contact);
        
        // ���͹�ҪA�ȼh����
        service=new PhoneBookService(getBaseContext());
        
        // ��l�Ʊ��
        updateButton=(Button)findViewById(R.id.button_update);
        cancelButton=(Button)findViewById(R.id.button_cancel);
        editUpdateContactName=(EditText)findViewById(R.id.update_contact_name);
        editUpdateContactPhone=(EditText)findViewById(R.id.update_contact_phone);
        
        // ��������s�K�[�ƥ��ť��
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// ������s�p���H�����A��^�p���H�M�椶��
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
        
        // ����p���H��T
        fillContactInfo();
    }
    
    /**
     * ����p���H��T
     */
    private void fillContactInfo(){
    	// ���o�q�p���H�C��ǤJ����e�p���HID��
    	uid=getIntent().getLongExtra("uid", 0L);
    	
    	// �I�s�A�ȼh��kgetContactById�A�q�L�p���HID�d�߸�T
    	ContactModel model=service.getContactById(uid);
    	
    	// ��R�p���H��T��������
    	if (model != null){
    		editUpdateContactName.setText(model.getContactName());
    		editUpdateContactPhone.setText(model.getContactPhone());
    	}
    }
    
    /**
     * ��s�p���H
     */
    private void doUpdateContact(){
    		// ����n��s�����
    		String contactName=editUpdateContactName.getText().toString().trim();
    		String contactPhone=editUpdateContactPhone.getText().toString().trim();
    	
    		// �غc��Ƽҫ�
    		ContactModel model=new ContactModel();
    		model.setId(uid);
    		model.setContactName(contactName);
    		model.setContactPhone(contactPhone);
    	
    		// �I�s�A�ȼh��kupdateContact���p���H�i���s
    		boolean result=service.updateContact(model);
    		// �HToast�������覡���s���G�i�����
    		if (result){
Toast.makeText(getBaseContext(), getString(R.string.msg_update_success), Toast.LENGTH_LONG).show();
    			finish();
   	 	}else{
Toast.makeText(getBaseContext(), getString(R.string.msg_update_failure), Toast.LENGTH_LONG).show();
   	 	}    	
    }    
}
