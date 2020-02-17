package com.uppowerstudio.chapter4.email;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 發送E-mail範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button sendBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		sendBtn=(Button) findViewById(R.id.send);
		// 註冊發送按鈕按一下事件
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent();

				intent.setAction(Intent.ACTION_SEND);
				// 設置內容類別型為純文字
				intent.setType("plain/text");
				// 設置發送位址的字串陣列
				String[] toAddr={ "uppowerstudio@163.com",
						"uppowerstudio@163.com" };
				// 設置抄送位址的字串陣列
				String[] ccAddr={ "uppowerstudio@163.com" };
				// 設置E-mail主題
				intent.putExtra(Intent.EXTRA_SUBJECT, "Email Demo");
				// 設置E-mail發送位址
				intent.putExtra(Intent.EXTRA_EMAIL, toAddr);
				// 設置E-mail抄送位址
				intent.putExtra(Intent.EXTRA_CC, ccAddr);
				// 設置E-mail內容
				intent.putExtra(Intent.EXTRA_TEXT, "This is email send demo");
				// 發送E-mail
				startActivity(Intent.createChooser(intent, null));
			}
		});
	}
}
