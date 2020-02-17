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
 * 通知範例
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
		// 獲取NotificationManager對象
		notificationManager=(NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);

		txtBtn=(Button) findViewById(R.id.txt);
		txtBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 新增Notification對象
				Notification notification=new Notification();
				notification.icon=R.drawable.icon;
				// 設置通知資訊
				notification.tickerText="This is text notification";
				// 按一下通知後的Intent，此例子按一下後還是在當前介面
				PendingIntent intent=PendingIntent.getActivity(
						MainActivity.this, 0, new Intent(MainActivity.this,
								MainActivity.class), 0);
				// 設置通知資訊
				notification.setLatestEventInfo(MainActivity.this,
						"Notification", "Content of Notification Demo", intent);
				// 執行通知
				notificationManager.notify(0, notification);
			}
		});

		soundBtn=(Button) findViewById(R.id.sound);
		soundBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 新增Notification對象
				Notification notification=new Notification();
				// 獲取系統當前鈴聲名
				String ringName=RingtoneManager.getActualDefaultRingtoneUri(
						MainActivity.this, RingtoneManager.TYPE_RINGTONE)
						.toString();
				// 設置系統當前鈴聲為此通知的聲音
				notification.sound=Uri.parse(ringName);
				// 執行通知
				notificationManager.notify(0, notification);
			}
		});

		vibrateBtn=(Button) findViewById(R.id.vibrate);
		vibrateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 新增Notification對象
				Notification notification=new Notification();
				// 設置通知振動模式
				notification.vibrate=new long[] { 0, 100, 200, 300 };
				// 執行通知
				notificationManager.notify(0, notification);
			}
		});

		ledBtn=(Button) findViewById(R.id.led);
		ledBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 新增Notification對象
				Notification notification=new Notification();
				// 設置LED開始時的閃光時間
				notification.ledOnMS=300;
				// 設置LED關閉時的閃光時間
				notification.ledOffMS=1000;
				// 執行通知
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
