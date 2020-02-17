package com.uppowerstudio.chapter4.keyevent;

import com.uppowerstudio.chapter4.keyevent.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * ����d��
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
		// ���U�ɰ��檺�ާ@
		// ����UBack��ɦb�����W��ܡ����UBack�䡨
		// ����UMenu��ɦb�����W��ܡ����UMenu�䡨
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
