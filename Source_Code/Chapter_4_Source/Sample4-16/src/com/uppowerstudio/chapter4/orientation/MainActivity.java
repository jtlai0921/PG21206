package com.uppowerstudio.chapter4.orientation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 顯示方向範例
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
				// 設置顯示方向為豎向顯示
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		});
		landBtn=(Button) findViewById(R.id.landscape);
		landBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 設置顯示方向為橫向顯示
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		});

		// 如果顯示方向為預設顯示方向（由Android系統自動選擇適當方向），則可設置為橫向或者豎向顯示
		// 如果顯示方向為豎向顯示，則只能設置為橫向顯示，反之則只能設置為豎向顯示
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
