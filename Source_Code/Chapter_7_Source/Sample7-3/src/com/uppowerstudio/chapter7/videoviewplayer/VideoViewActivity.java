package com.uppowerstudio.chapter7.videoviewplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * �ϥ�VideoView������T
 * @author UPPower Studio
 *
 */
public class VideoViewActivity extends Activity {

	// �w�q���T�ɦ�m
	private static final String MP4_FILE="/sdcard/Sample7-3/Yesterday_Once_More.mp4";
	
	// �ŧi���������ܼ�
	private VideoView videoView;
	private ImageButton buttonPlay;
	private ImageButton buttonPause;
	private ImageButton buttonStop;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);

// ��l�Ʊ��
videoView=(VideoView)findViewById(R.id.video_view);
buttonPlay=(ImageButton)findViewById(R.id.button_start);
buttonPause=(ImageButton)findViewById(R.id.button_pause);
buttonStop=(ImageButton)findViewById(R.id.button_stop);

// �]�m���T�����|
videoView.setVideoPath(MP4_FILE);

// �]�m����i�ױ�
videoView.setMediaController(new MediaController(VideoViewActivity.this));
videoView.requestFocus();

// ���U���s�ƥ��ť��
        registerButtonHandler();
    }

/**
     * ���U���s�ƥ��ť��
     */
private void registerButtonHandler(){
	// ������s
	buttonPlay.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �}�l���漽��ާ@
				videoView.start();
			}
	});
	
	// �Ȱ����s
	buttonPause.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �P�_�O�_���b����A�p���b����h����Ȱ��ާ@
				if (videoView.isPlaying()){
					videoView.pause();
				}else{
					videoView.start();
				}
			}
	});
	
	// ������s
	buttonStop.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (videoView.isPlaying()){
					// �����
					videoView.stopPlayback();
				}
			}
	});
    }
}
