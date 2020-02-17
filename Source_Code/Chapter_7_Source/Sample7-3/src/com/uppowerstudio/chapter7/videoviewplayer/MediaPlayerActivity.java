package com.uppowerstudio.chapter7.videoviewplayer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

/**
 * 使用MediaPlayer與SurfaceView播放視訊
 * @author UPPower Studio
 *
 */
public class MediaPlayerActivity extends Activity implements
		OnCompletionListener, OnSeekBarChangeListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
	
	// 定義視訊檔位置
	private static final String MP4_FILE="/sdcard/Sample7-3/Yesterday_Once_More.mp4";

	// 宣告介面控制變數
	private SurfaceView surfaceView;
	private ImageButton buttonPlay;
	private ImageButton buttonPause;
	private ImageButton buttonStop;

	// 宣告MediaPlayer及SurfaceHolder物件變數
	private MediaPlayer mediaPlayer;
	private SurfaceHolder surfaceHolder;
	
	// 宣告進度條變數
	private SeekBar videoSeekBar=null;
	
	// 宣告定義器變數
	private Timer mTimer;
	private TimerTask mTimerTask;

	// 用於儲存視訊的寬、高
	private int width=0;
	private int height=0;
	
	// 進度條拖動標誌
	private boolean progressFlag=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_player);
		
		// 初始化控制項
		surfaceView=(SurfaceView) findViewById(R.id.surface_view);
		buttonPlay=(ImageButton) findViewById(R.id.button_start);
		buttonPause=(ImageButton) findViewById(R.id.button_pause);
		buttonStop=(ImageButton) findViewById(R.id.button_stop);
		videoSeekBar=(SeekBar) findViewById(R.id.video_seekbar);

		// 建構MediaPlayer
		mediaPlayer=new MediaPlayer();
		
		// 獲取SurfaceHolder對象
		surfaceHolder=surfaceView.getHolder();
		
		// 註冊回傳監聽器
		surfaceHolder.addCallback(this);
		
		// 設置SurfaceHolder的類型
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		// 註冊SeekBar進度改變事件監聽器
		videoSeekBar.setOnSeekBarChangeListener(this);

		// 註冊按鈕事件監聽器
		registerButtonHandler();
	}
	
	@Override
	protected void onDestroy() {
		// 釋放所有佔用資源
		if (mediaPlayer !=null) {
			mediaPlayer.release();
			
			// 取消計時器任務
			mTimer.cancel();
			mTimerTask.cancel();
		}

		super.onDestroy();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// 設置播放標誌
		progressFlag=true;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// 跳轉到指定位置
		mediaPlayer.seekTo(seekBar.getProgress());
		progressFlag=false;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 當SurfaceView新增時開始視訊播放操作
		playVideo();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// 獲取視訊的寬與高
		width=mediaPlayer.getVideoWidth();
		height=mediaPlayer.getVideoHeight();
		if (width !=0 &&height !=0) {
			// 設置視訊高、寬
			surfaceHolder.setFixedSize(width, height);
			
			// 設置進度條最大值
			videoSeekBar.setMax(mediaPlayer.getDuration());

			// 用計時器記錄播放進度
			mTimer=new Timer();
			mTimerTask=new TimerTask() {
				@Override
				public void run() {
					if (progressFlag==true)
						return;
					// 設置進度條為當前播放進度
					videoSeekBar.setProgress(mediaPlayer.getCurrentPosition());
				}
			};
			
			// 開始執行定時任務
			mTimer.schedule(mTimerTask, 0, 10);
			
			// 開始播放視訊
			mediaPlayer.start();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// 播放結束顯示狀態提示資訊
		Toast.makeText(MediaPlayerActivity.this,
				getString(R.string.play_completion), Toast.LENGTH_LONG).show();
	}

	/**
	 * 註冊按鈕事件監聽器
	 */
	private void registerButtonHandler() {
		// 播放按鈕
		buttonPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				playVideo();
			}
		});

		// 暫停按鈕
		buttonPause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pauseVideo();
			}
		});

		// 停止按鈕
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopVideo();
			}
		});
	}

	/**
	 * 播放視訊
	 */
	private void playVideo() {
		try {
			// 設置視訊源
			mediaPlayer.setDataSource(MP4_FILE);
			
			// 設置通過使用SurfaceView方式顯示視訊畫面
			mediaPlayer.setDisplay(surfaceHolder);
			
			// 設置音訊類型
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			
			// 播放前準備
			mediaPlayer.prepare();
			
			// 註冊事件監聽器
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnPreparedListener(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 暫停播放
	 */
	private void pauseVideo() {
		// 判斷當前是否正在播放音樂
		if (mediaPlayer !=null&&mediaPlayer.isPlaying()) {
			// 暫停播放
			mediaPlayer.pause();
		} else {
			// 繼續播放
			mediaPlayer.start();
		}
	}

	/**
	 * 停止播放
	 */
	private void stopVideo() {
		if (mediaPlayer !=null&&mediaPlayer.isPlaying()) {
			// 重置MediaPlayer到初始化狀態
			mediaPlayer.reset();
			Toast.makeText(MediaPlayerActivity.this, getString(R.string.msg_stopped),
					Toast.LENGTH_LONG).show();
			
			// 取消定時任務
			mTimer.cancel();
			mTimerTask.cancel();
		}
	}
}
