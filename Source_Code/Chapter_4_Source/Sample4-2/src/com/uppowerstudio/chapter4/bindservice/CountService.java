package com.uppowerstudio.chapter4.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
/**
 * 服務範例
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
        // 新增一執行緒，每秒為計數器加1，並在控制台進行log輸出
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
        //結束服務時，終止計數執行緒
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
