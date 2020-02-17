package com.uppowerstudio.chapter4.battery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 電量範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView levelText;
	private int batteryLevel;
	private int batteryScale;
	// 自訂獲取電量資訊的接收器
	private BroadcastReceiver batteryReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 獲取當前電量，如未獲取到具體數值，則預設為0
			batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			// 獲取最大電量，如未獲取到具體數值，則預設為100
			batteryScale=intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
			// 顯示電量
			levelText.setText(getString(R.string.level)
					+ (batteryLevel * 100 / batteryScale)+"%");
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		levelText=(TextView) findViewById(R.id.battery_level);

		// 新增一個IntentFilter用於攔截指定的Intent
		IntentFilter intent=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		// 註冊接收器以獲取電量資訊
		registerReceiver(batteryReceiver, intent);
	}
}
