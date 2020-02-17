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
 * Google日曆服務主介面類別
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity implements StaticConst{

	// 宣告介面控制項變數
	private EditText loginAccount;
	private EditText loginPassword;
	private Button buttonLogin;
	private Button buttonClear;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		loginAccount=(EditText) findViewById(R.id.txt_google_account);
		loginPassword=(EditText) findViewById(R.id.txt_google_password);
		buttonLogin=(Button) findViewById(R.id.button_login);
		buttonClear=(Button) findViewById(R.id.button_clear);

		// 註冊按鈕點擊事件監聽器
		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 呼叫ListCalendarActivity
				Intent intent=new Intent(ACTION_LIST_CALENDAR);
				// 將輸入的用戶名及密碼傳遞到ListCalendarActivity中完成認證與授權操作
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
				// 清除輸入的帳號資料
				loginAccount.setText("");
				loginPassword.setText("");
			}
		});
	}
}
