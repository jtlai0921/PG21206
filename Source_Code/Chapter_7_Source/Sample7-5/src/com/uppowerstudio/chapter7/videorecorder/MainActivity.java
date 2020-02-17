package com.uppowerstudio.chapter7.videorecorder;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

/**
 * ���T���s�D�����{���X
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {
	
	// �ŧi���T�w�������ܼ�
	private CamcorderPreview camcorderPreview;
	
	// �лx�ܼơA�Ω�P�_�O�_�B����s��
	private boolean recording=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// �]�m�D���������ù�
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		// ���J�G����
		setContentView(R.layout.main);

		// ���͹���w���������
		camcorderPreview=(CamcorderPreview) findViewById(R.id.camcorder_preview);

		// ���U��պ�ť��
		camcorderPreview.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// ���լ��q�W��U�ư�
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					if (recording) {
						// �p��e���b���s�A�h������s
						camcorderPreview.getRecorder().stop();
						// ������Ϊ��귽
						camcorderPreview.getRecorder().release();
						finish();
					} else {
						recording=true;
						// �}�l���s
						camcorderPreview.getRecorder().start();
					}
					return true;
				}
				return false;
			}
		});
	}
}
