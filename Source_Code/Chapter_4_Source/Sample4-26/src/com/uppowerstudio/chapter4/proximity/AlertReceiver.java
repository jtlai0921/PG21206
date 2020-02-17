package com.uppowerstudio.chapter4.proximity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * 趨近警告接收器
 * 
 * @author UPPower Studio
 * 
 */
public class AlertReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 判斷是否進入警告區域
		String key=LocationManager.KEY_PROXIMITY_ENTERING;	
		Boolean isEnter=intent.getBooleanExtra(key, false);
		// 進入後進行提示操作
		if (isEnter) {
			Toast.makeText(context, R.string.txt, 2).show();
		}
	}
}
