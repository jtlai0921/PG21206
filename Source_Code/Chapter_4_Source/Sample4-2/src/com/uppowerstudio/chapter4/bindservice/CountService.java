package com.uppowerstudio.chapter4.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
/**
 * �A�Ƚd��
 * @author UPPower Studio
 * 
 */
public class CountService extends Service {
	private boolean threadDisable;
	private int count;
	private ServiceBinder serviceBinder;
	
	@Override
	public IBinder onBind(Intent intent) {
		return serviceBinder;
	}

	@Override
public void onCreate() {
super.onCreate();
        // �s�W�@������A�C���p�ƾ��[1�A�æb����x�i��log��X
new Thread(new Runnable() {
            @Override
public void run() {
while (!threadDisable) {
try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    count++;
                    Log.v("CountService", "Count is "+count);
                }
            }
        }).start();
    }
	
@Override
public void onDestroy() {
super.onDestroy();
        //�����A�ȮɡA�פ�p�ư����
this.threadDisable=true;
}

public int getCount() {
return count;
}

class ServiceBinder extends Binder {   
public CountService getService() {   
return CountService.this; 
     }   
}  
}
