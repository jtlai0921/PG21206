package com.uppowerstudio.chapter4.proximity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * �o�e²�T�d��
 * 
 * @author UPPower Studio
 * 
 */
public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// �P�_�O²�T�o�e�᪺�s����T�٬O²�T��F�᪺�s����T
		if ("SMS_SENT_ACTION".equals(intent.getAction())) {
			switch (getResultCode()) {
			// �p�G�o�e���\
			case Activity.RESULT_OK:
				Toast.makeText(context, R.string.send_success,
						Toast.LENGTH_LONG).show();
				break;
			// �p�G�o�e����
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				Toast.makeText(context, R.string.send_failure,
						Toast.LENGTH_LONG).show();
				break;
			}
		} else if ("SMS_DELIVERED_ACTION".equals(intent.getAction())) {
			switch (getResultCode()) {
			// �p�G²�T�w��F
			case Activity.RESULT_OK:
				Toast.makeText(context, R.string.send_reached,
						Toast.LENGTH_LONG).show();
				break;
			// �p�G²�T����F
			default:
				Toast.makeText(context, R.string.no_send_reached,
						Toast.LENGTH_LONG).show();
				break;
			}
		}

	}
}
