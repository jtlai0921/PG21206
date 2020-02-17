package com.uppowerstudio.chapter4.multiThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * �h������ϥζi�פή����d��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private final static int PROGRESS_END=-1;

	private ProgressBar progressBar;
	private int prgress;

	// �إ�Handler����
	private Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// �ھڤ��P��Message���椣�P���ާ@
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
		// �s�ؤ@�Ӱ�����i��B��
		new Thread() {
			public void run() {
				while (prgress < 100) {
					try {
						// �C����v0.1��
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
		// ���Message��H
		Message msg=Message.obtain();
		// �N�ϥΪ̦ۭq����Tcode�ʸˤJMessage����
		msg.what=prgress;
		// �o�eMessage��H
		handler.sendMessage(msg);
	}

	private void endProgress() {
		Message msg=Message.obtain();
		msg.what=PROGRESS_END;
		handler.sendMessage(msg);
	}
}
