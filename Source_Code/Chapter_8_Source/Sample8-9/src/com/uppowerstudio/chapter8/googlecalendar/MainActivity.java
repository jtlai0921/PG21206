package com.uppowerstudio.chapter8.googlecalendar;

import com.uppowerstudio.chapter8.googlecalendar.common.StaticConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Google���A�ȥD�������O
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity implements StaticConst{

	// �ŧi��������ܼ�
	private EditText loginAccount;
	private EditText loginPassword;
	private Button buttonLogin;
	private Button buttonClear;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		loginAccount=(EditText) findViewById(R.id.txt_google_account);
		loginPassword=(EditText) findViewById(R.id.txt_google_password);
		buttonLogin=(Button) findViewById(R.id.button_login);
		buttonClear=(Button) findViewById(R.id.button_clear);

		// ���U���s�I���ƥ��ť��
		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �I�sListCalendarActivity
				Intent intent=new Intent(ACTION_LIST_CALENDAR);
				// �N��J���Τ�W�αK�X�ǻ���ListCalendarActivity�������{�һP���v�ާ@
				Bundle bundle=new Bundle();
				bundle.putString(BUNDLE_KEY_USERNAME, loginAccount.getText().
toString().trim());
				bundle.putString(BUNDLE_KEY_PASSWORD, loginPassword.getText().
toString().trim());
				intent.putExtras(bundle);
				
				startActivity(intent);
			}
		});

		buttonClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �M����J���b�����
				loginAccount.setText("");
				loginPassword.setText("");
			}
		});
	}
}
