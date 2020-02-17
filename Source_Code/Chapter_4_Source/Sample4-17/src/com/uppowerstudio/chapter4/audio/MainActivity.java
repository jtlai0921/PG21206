package com.uppowerstudio.chapter4.audio;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 音量範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button slientBtn;
	private Button upBtn;
	private Button downBtn;
	private Button maxBtn;
	private AudioManager audio;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 獲取AudioManager對象
		audio=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

		slientBtn=(Button) findViewById(R.id.slient);
		slientBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 將音量設置為0
				audio.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
			}
		});

		upBtn=(Button) findViewById(R.id.up);
		upBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 增加一個單位的音量
				audio.adjustVolume(AudioManager.ADJUST_RAISE, 0);
			}
		});

		downBtn=(Button) findViewById(R.id.down);
		downBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 減少一個單位的音量
				audio.adjustVolume(AudioManager.ADJUST_LOWER, 0);
			}
		});

		maxBtn=(Button) findViewById(R.id.max);
		maxBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 獲得當前系統最大音量
				int maxVolume=audio
						.getStreamMaxVolume(AudioManager.STREAM_RING);
				audio.setStreamVolume(AudioManager.STREAM_RING, maxVolume, 0);
			}
		});

	}
}
