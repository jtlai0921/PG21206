package com.uppowerstudio.chapter7.videorecorder;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

/**
 * 視訊錄製主介面程式碼
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {
	
	// 宣告視訊預覽視窗變數
	private CamcorderPreview camcorderPreview;
	
	// 標誌變數，用於判斷是否處於錄製中
	private boolean recording=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 設置主介面為全螢幕
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		// 載入佈局檔
		setContentView(R.layout.main);

		// 產生實體預覽視窗控制項
		camcorderPreview=(CamcorderPreview) findViewById(R.id.camcorder_preview);

		// 註冊手勢監聽器
		camcorderPreview.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// 當手勢為從上到下滑動
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					if (recording) {
						// 如當前正在錄製，則停止錄製
						camcorderPreview.getRecorder().stop();
						// 釋放佔用的資源
						camcorderPreview.getRecorder().release();
						finish();
					} else {
						recording=true;
						// 開始錄製
						camcorderPreview.getRecorder().start();
					}
					return true;
				}
				return false;
			}
		});
	}
}
