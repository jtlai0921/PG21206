package com.uppowerstudio.chapter7.videoviewplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * 使用VideoView播放視訊
 * @author UPPower Studio
 *
 */
public class VideoViewActivity extends Activity {

	// 定義視訊檔位置
	private static final String MP4_FILE="/sdcard/Sample7-3/Yesterday_Once_More.mp4";
	
	// 宣告介面控制變數
	private VideoView videoView;
	private ImageButton buttonPlay;
	private ImageButton buttonPause;
	private ImageButton buttonStop;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);

// 初始化控制項
videoView=(VideoView)findViewById(R.id.video_view);
buttonPlay=(ImageButton)findViewById(R.id.button_start);
buttonPause=(ImageButton)findViewById(R.id.button_pause);
buttonStop=(ImageButton)findViewById(R.id.button_stop);

// 設置視訊源路徑
videoView.setVideoPath(MP4_FILE);

// 設置播放進度條
videoView.setMediaController(new MediaController(VideoViewActivity.this));
videoView.requestFocus();

// 註冊按鈕事件監聽器
        registerButtonHandler();
    }

/**
     * 註冊按鈕事件監聽器
     */
private void registerButtonHandler(){
	// 播放按鈕
	buttonPlay.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 開始執行播放操作
				videoView.start();
			}
	});
	
	// 暫停按鈕
	buttonPause.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 判斷是否正在播放，如正在播放則執行暫停操作
				if (videoView.isPlaying()){
					videoView.pause();
				}else{
					videoView.start();
				}
			}
	});
	
	// 停止按鈕
	buttonStop.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (videoView.isPlaying()){
					// 停止播放
					videoView.stopPlayback();
				}
			}
	});
    }
}
