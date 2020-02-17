package com.uppowerstudio.chapter4.audio;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * ���q�d��
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

		// ���AudioManager��H
		audio=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

		slientBtn=(Button) findViewById(R.id.slient);
		slientBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// �N���q�]�m��0
				audio.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
			}
		});

		upBtn=(Button) findViewById(R.id.up);
		upBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// �W�[�@�ӳ�쪺���q
				audio.adjustVolume(AudioManager.ADJUST_RAISE, 0);
			}
		});

		downBtn=(Button) findViewById(R.id.down);
		downBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ��֤@�ӳ�쪺���q
				audio.adjustVolume(AudioManager.ADJUST_LOWER, 0);
			}
		});

		maxBtn=(Button) findViewById(R.id.max);
		maxBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ��o��e�t�γ̤j���q
				int maxVolume=audio
						.getStreamMaxVolume(AudioManager.STREAM_RING);
				audio.setStreamVolume(AudioManager.STREAM_RING, maxVolume, 0);
			}
		});

	}
}
