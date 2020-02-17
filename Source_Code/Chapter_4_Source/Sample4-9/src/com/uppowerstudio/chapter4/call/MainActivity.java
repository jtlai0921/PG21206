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
 * 通話範例
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
				// 將電話號碼解析為URI
				Uri uri=Uri.parse("tel:"+phoneNum);
				Intent intent=new Intent();
				// 設置intent的action為“ACTION_CALL”
				intent.setAction(Intent.ACTION_CALL);
				// 將URI傳入intent對象
				intent.setData(uri);
				// 開始通話
				startActivity(intent);
			}
		});
	}
}
