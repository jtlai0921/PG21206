package com.uppowerstudio.chapter4.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 鬧鈴範例
 * 
 * @author UPPower Studio
 * 
 */
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 出現提示
		Toast.makeText(context, "This is Alarm Demo", Toast.LENGTH_LONG).show();
	}
}
