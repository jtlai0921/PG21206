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
 * �A�Ƚd��
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
        // �i�JActivity�ɡA�}�l�A��
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
 }

 @Override
protected void onDestroy() {
super.onDestroy();
        // �h�XActivity�ɡA����A��
this.unbindService(conn);
        Log.v("MainActivity", "out");
}

private ServiceConnection conn=new ServiceConnection() {   
	// ����A�Ȫ���ɪ��ާ@
public void onServiceConnected(ComponentName name, IBinder service) {   
	      countService=( (CountService.ServiceBinder) service ).getService();   
 }   

        // �L�k�����A�Ȫ���ɪ��ާ@
public void onServiceDisconnected(ComponentName name) {   
	      countService=null;   
}   
}; 
}
