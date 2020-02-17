package com.uppowerstudio.chapter7.videoviewplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 簡易視訊播放機主介面程式碼
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {

	// 宣告介面按鈕變數
	private Button buttonVideoView;
	private Button buttonMediaPlayer;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

// 初始化按鈕
buttonVideoView=(Button)findViewById(R.id.button_video_view);
buttonMediaPlayer=(Button)findViewById(R.id.button_media_player);

// 註冊按鈕事件監聽器
        registerButtonHandler();
    }

/**
     * 註冊按鈕事件監聽器
     */
private void registerButtonHandler(){
	    // VideoView方式播放視訊按鈕監聽器
	    buttonVideoView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_videoview");
				startActivity(intent);
			}
	});
	
	    // MediaPlayer方式播放視訊按鈕監聽器
	    buttonMediaPlayer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_mediaplayer");
				startActivity(intent);
			}
	      });
    }
}
