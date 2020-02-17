package com.uppowerstudio.chapter5.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 計數消息接收器
 * @author UPPower Studio
 *
 */
public class CountReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent intent) {
		try {
			// 獲取計數服務中存入的當前計數值
			int count=intent.getIntExtra("currentCount", 0);
			
			// 顯示當前計數值
			Toast.makeText(ctx, "計數器="+count, Toast.LENGTH_LONG).show();
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
	}
}
