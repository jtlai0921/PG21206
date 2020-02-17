package com.uppowerstudio.chapter4.orientation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * ��ܤ�V�d��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button porBtn;
	private Button landBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		porBtn=(Button) findViewById(R.id.portrait);
		porBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// �]�m��ܤ�V���ݦV���
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		});
		landBtn=(Button) findViewById(R.id.landscape);
		landBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// �]�m��ܤ�V����V���
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		});

		// �p�G��ܤ�V���w�]��ܤ�V�]��Android�t�Φ۰ʿ�ܾA���V�^�A�h�i�]�m����V�Ϊ̽ݦV���
		// �p�G��ܤ�V���ݦV��ܡA�h�u��]�m����V��ܡA�Ϥ��h�u��]�m���ݦV���
		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
			porBtn.setEnabled(true);
			landBtn.setEnabled(true);
		} else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			porBtn.setEnabled(false);
			landBtn.setEnabled(true);
		} else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			landBtn.setEnabled(false);
			porBtn.setEnabled(true);
		}

	}
}
