package com.uppowerstudio.chapter4.alarm;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * ¾x¹a½d¨Ò
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button alarmButton;
	private Button closeButton;
	private AlarmManager alarm;
	private PendingIntent sender;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent intent=new Intent(MainActivity.this, AlarmReceiver.class);
		sender=PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

		// Àò¨úAlarmManager
		alarm=(AlarmManager) getSystemService(ALARM_SERVICE);

		alarmButton=(Button) findViewById(R.id.alarm);
		alarmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ³]¸m10¬í«áÄ²µo¾x¹a
				Calendar calender=Calendar.getInstance();
				calender.setTimeInMillis(System.currentTimeMillis());
				calender.add(Calendar.SECOND, 10);

				alarm.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(),
						sender);
			}
		});

		closeButton=(Button) findViewById(R.id.close);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Ãö³¬¾x¹a
				alarm.cancel(sender);
			}
		});

	}
}
