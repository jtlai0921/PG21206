package com.uppowerstudio.chapter3.seek;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 滑動條範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		SeekBar seek=(SeekBar) findViewById(R.id.seek_01);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			// 進度值改變時觸發
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Toast.makeText(MainActivity.this, progress + "", 1).show();
			}

			// 元件被單次點擊時觸發
			public void onStartTrackingTouch(SeekBar seekBar) {
				Toast.makeText(MainActivity.this, "start", 1).show();
			}

			// 元件被單次點擊之後觸發
			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(MainActivity.this, "stop", 1).show();
			}

		});
	}
}
