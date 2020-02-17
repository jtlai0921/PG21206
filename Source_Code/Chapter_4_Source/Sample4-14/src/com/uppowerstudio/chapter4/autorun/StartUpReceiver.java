package com.uppowerstudio.chapter4.autorun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 自動執行範例
 * @author UPPower Studio
 * 
 */
public class StartUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 跳轉到MainActivity
		Intent i=new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}

}
