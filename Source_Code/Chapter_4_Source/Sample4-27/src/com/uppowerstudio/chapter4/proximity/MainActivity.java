package com.uppowerstudio.chapter4.proximity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 發送簡訊範例
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

		// 按一下按鈕時發送簡訊
		sendBtn=(Button) findViewById(R.id.send);
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 獲取SmsManager對象
				SmsManager manager=SmsManager.getDefault();
				// 定義簡訊發送後的廣播資訊
				PendingIntent sentIntent=PendingIntent.getBroadcast(
						MainActivity.this, 0, new Intent("SMS_SENT_ACTION"), 0);
				// 定義簡訊到達後的廣播資訊
				PendingIntent deliveredIntent=PendingIntent.getBroadcast(
						MainActivity.this, 0,
						new Intent("SMS_DELIVERED_ACTION"), 0);
				// 攜帶廣播資訊發送自訂簡訊到指定號碼
				manager.sendTextMessage("137*****530", null, "Test Message",
						sentIntent, deliveredIntent);
			}
		});
	}
}
