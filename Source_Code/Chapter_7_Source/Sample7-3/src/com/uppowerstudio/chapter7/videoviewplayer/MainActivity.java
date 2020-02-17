package com.uppowerstudio.chapter7.videoviewplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * ²�����T������D�����{���X
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {

	// �ŧi�������s�ܼ�
	private Button buttonVideoView;
	private Button buttonMediaPlayer;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

// ��l�ƫ��s
buttonVideoView=(Button)findViewById(R.id.button_video_view);
buttonMediaPlayer=(Button)findViewById(R.id.button_media_player);

// ���U���s�ƥ��ť��
        registerButtonHandler();
    }

/**
     * ���U���s�ƥ��ť��
     */
private void registerButtonHandler(){
	    // VideoView�覡������T���s��ť��
	    buttonVideoView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_videoview");
				startActivity(intent);
			}
	});
	
	    // MediaPlayer�覡������T���s��ť��
	    buttonMediaPlayer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_mediaplayer");
				startActivity(intent);
			}
	      });
    }
}
