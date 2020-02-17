package com.uppowerstudio.chapter5.intent;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

/**
 * 計數服務
 * @author UPPower Studio
 *
 */
public class CountService extends Service {
	
	// 定義Handler用於定時發送消息
	private Handler handler=new Handler();
	
	// 定義計數的時間間隔，單位為毫秒
	private int intUpdateSecond=5 * 1000;
	
	// 定義計數器
	private int count=0;
	
	// 定義計數任務
	private Runnable countTask=new Runnable(){
		@Override
		public void run() {			
			try{
				// 每次執行後計數器遞增
				count++;
				
				// 構建自訂用於發送廣播消息的Intent物件
				Intent i=new Intent("COUNT_BROADCAST");
				// 傳值(詳細講解請參見Q5)
				i.putExtra("currentCount", count);
				// 發送廣播消息
				sendBroadcast(i);
				
				// postDealyed方法用於按設置的週期定時執行countTask任務
				handler.postDelayed(countTask, intUpdateSecond);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	};
	
	@Override
	public void onCreate() {
		super.onCreate();
	}


	@Override
	public void onStart(Intent intent, int startId) {		
		super.onStart(intent, startId);
		Toast.makeText(getBaseContext(), "計數服務已經啟動", Toast.LENGTH_SHORT).show();
		
		// 開始執行計數任務
		handler.postDelayed(countTask, intUpdateSecond);
	}


	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void onDestroy() {
		// 將計數任務從訊息佇列中移除
		handler.removeCallbacks(countTask);
		Toast.makeText(getBaseContext(), "計數服務已被停止", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
}