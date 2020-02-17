package com.uppowerstudio.chapter4.call;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * �q�ܽd��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button callBtn;
	private EditText phoneNumEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		callBtn=(Button) findViewById(R.id.phone_call);
		phoneNumEdit=(EditText) findViewById(R.id.phone_num);

		callBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String phoneNum=phoneNumEdit.getText().toString();
				// �N�q�ܸ��X�ѪR��URI
				Uri uri=Uri.parse("tel:"+phoneNum);
				Intent intent=new Intent();
				// �]�mintent��action����ACTION_CALL��
				intent.setAction(Intent.ACTION_CALL);
				// �NURI�ǤJintent��H
				intent.setData(uri);
				// �}�l�q��
				startActivity(intent);
			}
		});
	}
}
