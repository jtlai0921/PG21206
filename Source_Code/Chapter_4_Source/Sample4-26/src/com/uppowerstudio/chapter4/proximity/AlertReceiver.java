package com.uppowerstudio.chapter4.proximity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * �ͪ�ĵ�i������
 * 
 * @author UPPower Studio
 * 
 */
public class AlertReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// �P�_�O�_�i�Jĵ�i�ϰ�
		String key=LocationManager.KEY_PROXIMITY_ENTERING;	
		Boolean isEnter=intent.getBooleanExtra(key, false);
		// �i�J��i�洣�ܾާ@
		if (isEnter) {
			Toast.makeText(context, R.string.txt, 2).show();
		}
	}
}
