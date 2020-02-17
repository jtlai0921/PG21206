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
 * �q�q�d��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView levelText;
	private int batteryLevel;
	private int batteryScale;
	// �ۭq����q�q��T��������
	private BroadcastReceiver batteryReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// �����e�q�q�A�p����������ƭȡA�h�w�]��0
			batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			// ����̤j�q�q�A�p����������ƭȡA�h�w�]��100
			batteryScale=intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
			// ��ܹq�q
			levelText.setText(getString(R.string.level)
					+ (batteryLevel * 100 / batteryScale)+"%");
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		levelText=(TextView) findViewById(R.id.battery_level);

		// �s�W�@��IntentFilter�Ω��d�I���w��Intent
		IntentFilter intent=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		// ���U�������H����q�q��T
		registerReceiver(batteryReceiver, intent);
	}
}
