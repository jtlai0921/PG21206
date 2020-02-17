package com.uppowerstudio.chapter5.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * �p�Ʈ���������
 * @author UPPower Studio
 *
 */
public class CountReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent intent) {
		try {
			// ����p�ƪA�Ȥ��s�J����e�p�ƭ�
			int count=intent.getIntExtra("currentCount", 0);
			
			// ��ܷ�e�p�ƭ�
			Toast.makeText(ctx, "�p�ƾ�="+count, Toast.LENGTH_LONG).show();
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
	}
}
