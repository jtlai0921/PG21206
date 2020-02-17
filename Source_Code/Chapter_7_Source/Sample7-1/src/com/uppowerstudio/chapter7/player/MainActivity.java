package com.uppowerstudio.chapter7.player;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 簡易MP3播放機主介面程式碼
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity implements OnSeekBarChangeListener {

	// 定義播放的MP3檔案名
	private static final String MP3_MUSIC_FILE_NAME="yesterday_once_more.mp3";

	// 定義SD卡中存放MP3檔的路徑
	private static final String MP3_SDCARD_MUSIC_PATH="/sdcard/sample7-1/";

	// 定義資源方式載入MP3的路徑
	private static final String MP3_RESOUCES_RAW_PATH="/res/raw/";

	// 定義網路MP3位址
	private static final String MP3_NETWORK_URL="http://rm.sina.com.cn/wm/VZ200805081542360400VK/music/1.mp3";

	// 宣告介面控制項變數
	private RadioGroup radioGroup;
	private ImageButton buttonPlay;
	private ImageButton buttonPause;
	private ImageButton buttonStop;
	private TextView currentPlayMusicPath;

	// 宣告進度條變數
	private SeekBar audioSeekBar=null;

	// 進度條拖動標誌
	private boolean progressFlag=false;

	// 宣告MediaPlayer變數
	private MediaPlayer mediaPlayer;

	// 宣告定義器變數
	private Timer mTimer;
	private TimerTask mTimerTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		initControls();

		// 初始化MediaPlayer對象
		mediaPlayer=new MediaPlayer();

		// 註冊各按鈕事件監聽器
		registerButtonHanlder();

		// 註冊SeekBar進度改變事件監聽器
		audioSeekBar.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		progressFlag=true;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mediaPlayer.seekTo(seekBar.getProgress());
		progressFlag=false;
	}

	@Override
	protected void onDestroy() {
		// 釋放所有佔用資源
		if (mediaPlayer != null) {
			mediaPlayer.release();
			
			// 取消計時器任務
			mTimer.cancel();
			mTimerTask.cancel();
		}

		super.onDestroy();
	}

	/**
	 * 初始化介面控制項
	 */
	private void initControls() {
		radioGroup=(RadioGroup) findViewById(R.id.radio_group);
		buttonPlay=(ImageButton) findViewById(R.id.button_start);
		buttonPause=(ImageButton) findViewById(R.id.button_pause);
		buttonStop=(ImageButton) findViewById(R.id.button_stop);
		currentPlayMusicPath=(TextView) findViewById(R.id.mp3_file_path);
		audioSeekBar=(SeekBar) findViewById(R.id.audio_seekbar);
	}

	/**
	 * 註冊事件監聽器
	 */
	private void registerButtonHanlder() {
		/**
		 * 註冊選項按鈕按一下事件監聽器
		 */
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 重置MediaPlayer對象
				if (mediaPlayer != null) {
					mediaPlayer.reset();
				}
			}

		});
		/**
		 * 註冊播放按鈕監聽器
		 */
		buttonPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 開始播放
				playMusic();
			}
		});

		/**
		 * 註冊暫停按鈕監聽器
		 */
		buttonPause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 暫停播放
				pauseMusic();
			}
		});

		/**
		 * 註冊停止按鈕監聽器
		 */
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 停止播放
				stopMusic();
			}
		});
	}

	/**
	 * 開始播放
	 */
	private void playMusic() {
		// 重置MediaPlayer
		mediaPlayer.reset();

		// 定義播放檔路徑
		String musicPath="";

		// 判斷以不同的方式載入MP3資源檔
		switch (radioGroup.getCheckedRadioButtonId()) {

		// 從資源檔res/raw載入MP3
		case R.id.button_load_from_res:
			// 建構MP3檔路徑
			musicPath=MP3_RESOUCES_RAW_PATH + MP3_MUSIC_FILE_NAME;
			currentPlayMusicPath.setText(musicPath);

			// 通過傳入位於res/raw下的MP3資源，建構MediaPlayer對象
			mediaPlayer=MediaPlayer.create(MainActivity.this,
					R.raw.yesterday_once_more);

			// 執行播放操作
			doPlayMusic(musicPath, true);
			break;

		// 從SD卡載入MP3
		case R.id.button_load_from_sdcard:
			// 建構MP3檔路徑
			musicPath=MP3_SDCARD_MUSIC_PATH + MP3_MUSIC_FILE_NAME;
			currentPlayMusicPath.setText(musicPath);

			// 執行播放操作
			doPlayMusic(musicPath, false);
			break;

		// 從網路載入MP3
		case R.id.button_load_from_url:
			// 建構MP3檔路徑
			musicPath=MP3_NETWORK_URL;
			currentPlayMusicPath.setText(musicPath);

			// 執行播放操作
			doPlayMusic(musicPath, false);
			break;
		}

		// 提示當前狀態
		Toast.makeText(MainActivity.this, getString(R.string.msg_playing),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * 暫停播放
	 */
	private void pauseMusic() {
		// 判斷當前是否正在播放音樂
		if (mediaPlayer != null&&mediaPlayer.isPlaying()) {
			// 暫停播放
			mediaPlayer.pause();

			// 狀態提示資訊
			Toast.makeText(MainActivity.this, getString(R.string.msg_pasued),
					Toast.LENGTH_LONG).show();
		} else {
			// 繼續播放
			mediaPlayer.start();
			Toast.makeText(MainActivity.this,
					getString(R.string.msg_continued), Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * 停止播放
	 */
	private void stopMusic() {
		// 判斷當前是否正在播放音樂
		if (mediaPlayer != null&&mediaPlayer.isPlaying()) {
			// 重置MediaPlayer到初始化狀態
			mediaPlayer.reset();
			Toast.makeText(MainActivity.this, getString(R.string.msg_stopped),
					Toast.LENGTH_LONG).show();
			
			mTimer.cancel();
			mTimerTask.cancel();
		}
	}

	/**
	 * 完成音樂的播放操作
	 * 
	 * @param musicPath
	 *            所播放MP3音樂的路徑
	 * @param isResWay
	 *            是否為內部資源載入方式
	 */
	private void doPlayMusic(String musicPath, boolean isResWay) {
		try {
			// 判斷當前是否以內部資源方式載入MP3檔
			if (!isResWay) {
				// 設置要播放的MP3檔路徑
				mediaPlayer.setDataSource(musicPath);

				// 進行播放前準備操作
				mediaPlayer.prepare();
			}

			// 設置進度條最大值
			audioSeekBar.setMax(mediaPlayer.getDuration());

			// 用計時器記錄播放進度
			mTimer=new Timer();
			mTimerTask=new TimerTask() {
				@Override
				public void run() {
					if (progressFlag==true)
						return;
					// 設置進度條為當前播放進度
					audioSeekBar.setProgress(mediaPlayer.getCurrentPosition());
				}
			};

			mTimer.schedule(mTimerTask, 0, 10);

			// 開始播放
			mediaPlayer.start();

			// 註冊播放完成事件監聽器
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					// 當播放完成時給予提示
					Toast.makeText(MainActivity.this,
							getString(R.string.play_completion),
							Toast.LENGTH_LONG).show();
					
					// 取消定時任務
					mTimer.cancel();
					mTimerTask.cancel();
					
					// 設置進度條為初始狀態
					audioSeekBar.setProgress(0);

					// 重置MediaPlayer為初始狀態
					mediaPlayer.reset();
				}
			});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
