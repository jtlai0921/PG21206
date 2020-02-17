package com.uppowerstudio.chapter4.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * �q���d��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button txtBtn;
	private Button soundBtn;
	private Button vibrateBtn;
	private Button ledBtn;
	private Button closeBtn;
	private NotificationManager notificationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ���NotificationManager��H
		notificationManager=(NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);

		txtBtn=(Button) findViewById(R.id.txt);
		txtBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �s�WNotification��H
				Notification notification=new Notification();
				notification.icon=R.drawable.icon;
				// �]�m�q����T
				notification.tickerText="This is text notification";
				// ���@�U�q���᪺Intent�A���Ҥl���@�U���٬O�b��e����
				PendingIntent intent=PendingIntent.getActivity(
						MainActivity.this, 0, new Intent(MainActivity.this,
								MainActivity.class), 0);
				// �]�m�q����T
				notification.setLatestEventInfo(MainActivity.this,
						"Notification", "Content of Notification Demo", intent);
				// ����q��
				notificationManager.notify(0, notification);
			}
		});

		soundBtn=(Button) findViewById(R.id.sound);
		soundBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �s�WNotification��H
				Notification notification=new Notification();
				// ����t�η�e�a�n�W
				String ringName=RingtoneManager.getActualDefaultRingtoneUri(
						MainActivity.this, RingtoneManager.TYPE_RINGTONE)
						.toString();
				// �]�m�t�η�e�a�n�����q�����n��
				notification.sound=Uri.parse(ringName);
				// ����q��
				notificationManager.notify(0, notification);
			}
		});

		vibrateBtn=(Button) findViewById(R.id.vibrate);
		vibrateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �s�WNotification��H
				Notification notification=new Notification();
				// �]�m�q�����ʼҦ�
				notification.vibrate=new long[] { 0, 100, 200, 300 };
				// ����q��
				notificationManager.notify(0, notification);
			}
		});

		ledBtn=(Button) findViewById(R.id.led);
		ledBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �s�WNotification��H
				Notification notification=new Notification();
				// �]�mLED�}�l�ɪ��{���ɶ�
				notification.ledOnMS=300;
				// �]�mLED�����ɪ��{���ɶ�
				notification.ledOffMS=1000;
				// ����q��
				notificationManager.notify(0, notification);
			}
		});

		closeBtn=(Button) findViewById(R.id.close);
		closeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				notificationManager.cancel(0);
			}
		});
	}
}
