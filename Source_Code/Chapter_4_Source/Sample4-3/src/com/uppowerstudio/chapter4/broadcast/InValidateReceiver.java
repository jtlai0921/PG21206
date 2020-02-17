package com.uppowerstudio.chapter4.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * �s���d��
 * 
 * @author UPPower Studio
 * 
 */
public class InValidateReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("InValidateReceiver", intent.getStringExtra("CONTENT"));
	}
}
