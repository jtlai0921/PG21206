package com.uppowerstudio.chapter4.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ¼s¼½½d¨Ò
 * 
 * @author UPPower Studio
 * 
 */
public class ValidateReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("ValidateReceiver", intent.getStringExtra("CONTENT"));
	}
}
