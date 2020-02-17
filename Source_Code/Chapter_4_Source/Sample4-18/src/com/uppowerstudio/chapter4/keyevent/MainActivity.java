package com.uppowerstudio.chapter4.keyevent;

import com.uppowerstudio.chapter4.keyevent.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 按鍵範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下時執行的操作
		// 當按下Back鍵時在介面上顯示“按下Back鍵”
		// 當按下Menu鍵時在介面上顯示“按下Menu鍵”
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Toast.makeText(MainActivity.this,
					getString(R.string.key_down_back), Toast.LENGTH_LONG)
					.show();
			return true;
		case KeyEvent.KEYCODE_MENU:
			Toast.makeText(MainActivity.this,
					getString(R.string.key_down_menu), Toast.LENGTH_LONG)
					.show();
			return true;
		default:
			return super.onKeyDown(keyCode, event);
		}
	}
}
