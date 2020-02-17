package com.uppowerstudio.chapter4.proximity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * 發送簡訊範例
 * 
 * @author UPPower Studio
 * 
 */
public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// 判斷是簡訊發送後的廣播資訊還是簡訊到達後的廣播資訊
		if ("SMS_SENT_ACTION".equals(intent.getAction())) {
			switch (getResultCode()) {
			// 如果發送成功
			case Activity.RESULT_OK:
				Toast.makeText(context, R.string.send_success,
						Toast.LENGTH_LONG).show();
				break;
			// 如果發送失敗
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				Toast.makeText(context, R.string.send_failure,
						Toast.LENGTH_LONG).show();
				break;
			}
		} else if ("SMS_DELIVERED_ACTION".equals(intent.getAction())) {
			switch (getResultCode()) {
			// 如果簡訊已到達
			case Activity.RESULT_OK:
				Toast.makeText(context, R.string.send_reached,
						Toast.LENGTH_LONG).show();
				break;
			// 如果簡訊未到達
			default:
				Toast.makeText(context, R.string.no_send_reached,
						Toast.LENGTH_LONG).show();
				break;
			}
		}

	}
}
