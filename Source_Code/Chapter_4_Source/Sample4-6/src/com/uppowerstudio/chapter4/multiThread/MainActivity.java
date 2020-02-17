package com.uppowerstudio.chapter4.multiThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 多執行緒使用進度及消息範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private final static int PROGRESS_END=-1;

	private ProgressBar progressBar;
	private int prgress;

	// 建立Handler物件
	private Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 根據不同的Message執行不同的操作
			switch (msg.what) {
			case PROGRESS_END:
				Toast.makeText(MainActivity.this, "loading finished",
						Toast.LENGTH_LONG).show();
				break;
			default:
				progressBar.setProgress(msg.what);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		progressBar=(ProgressBar) findViewById(R.id.progress_bar);
		startProgressBar();
	}

	private void startProgressBar() {
		// 新建一個執行緒進行運算
		new Thread() {
			public void run() {
				while (prgress < 100) {
					try {
						// 每次休眠0.1秒
						Thread.sleep(100);
						countProgress();
						prgress++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				endProgress();
			}
		}.start();
	}

	private void countProgress() {
		// 獲取Message對象
		Message msg=Message.obtain();
		// 將使用者自訂的資訊code封裝入Message物件
		msg.what=prgress;
		// 發送Message對象
		handler.sendMessage(msg);
	}

	private void endProgress() {
		Message msg=Message.obtain();
		msg.what=PROGRESS_END;
		handler.sendMessage(msg);
	}
}
