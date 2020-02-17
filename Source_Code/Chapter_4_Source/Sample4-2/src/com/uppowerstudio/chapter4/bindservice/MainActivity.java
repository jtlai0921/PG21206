package com.uppowerstudio.chapter4.bindservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * 服務範例
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private CountService countService;
	
	
 @Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        Log.v("MainActivity", "into");
        Intent intent=new Intent(MainActivity.this, CountService.class);
        // 進入Activity時，開始服務
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
 }

 @Override
protected void onDestroy() {
super.onDestroy();
        // 退出Activity時，停止服務
this.unbindService(conn);
        Log.v("MainActivity", "out");
}

private ServiceConnection conn=new ServiceConnection() {   
	// 獲取服務物件時的操作
public void onServiceConnected(ComponentName name, IBinder service) {   
	      countService=( (CountService.ServiceBinder) service ).getService();   
 }   

        // 無法獲取到服務物件時的操作
public void onServiceDisconnected(ComponentName name) {   
	      countService=null;   
}   
}; 
}
