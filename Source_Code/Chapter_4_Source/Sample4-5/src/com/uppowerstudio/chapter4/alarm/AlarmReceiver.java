package com.uppowerstudio.chapter4.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * �x�a�d��
 * 
 * @author UPPower Studio
 * 
 */
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// �X�{����
		Toast.makeText(context, "This is Alarm Demo", Toast.LENGTH_LONG).show();
	}
}
