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
 * �o�e²�T�d��
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

		// ���@�U���s�ɵo�e²�T
		sendBtn=(Button) findViewById(R.id.send);
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ���SmsManager��H
				SmsManager manager=SmsManager.getDefault();
				// �w�q²�T�o�e�᪺�s����T
				PendingIntent sentIntent=PendingIntent.getBroadcast(
						MainActivity.this, 0, new Intent("SMS_SENT_ACTION"), 0);
				// �w�q²�T��F�᪺�s����T
				PendingIntent deliveredIntent=PendingIntent.getBroadcast(
						MainActivity.this, 0,
						new Intent("SMS_DELIVERED_ACTION"), 0);
				// ��a�s����T�o�e�ۭq²�T����w���X
				manager.sendTextMessage("137*****530", null, "Test Message",
						sentIntent, deliveredIntent);
			}
		});
	}
}
