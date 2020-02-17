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
 * ²��MP3������D�����{���X
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity implements OnSeekBarChangeListener {

	// �w�q����MP3�ɮצW
	private static final String MP3_MUSIC_FILE_NAME="yesterday_once_more.mp3";

	// �w�qSD�d���s��MP3�ɪ����|
	private static final String MP3_SDCARD_MUSIC_PATH="/sdcard/sample7-1/";

	// �w�q�귽�覡���JMP3�����|
	private static final String MP3_RESOUCES_RAW_PATH="/res/raw/";

	// �w�q����MP3��}
	private static final String MP3_NETWORK_URL="http://rm.sina.com.cn/wm/VZ200805081542360400VK/music/1.mp3";

	// �ŧi��������ܼ�
	private RadioGroup radioGroup;
	private ImageButton buttonPlay;
	private ImageButton buttonPause;
	private ImageButton buttonStop;
	private TextView currentPlayMusicPath;

	// �ŧi�i�ױ��ܼ�
	private SeekBar audioSeekBar=null;

	// �i�ױ���ʼлx
	private boolean progressFlag=false;

	// �ŧiMediaPlayer�ܼ�
	private MediaPlayer mediaPlayer;

	// �ŧi�w�q���ܼ�
	private Timer mTimer;
	private TimerTask mTimerTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		initControls();

		// ��l��MediaPlayer��H
		mediaPlayer=new MediaPlayer();

		// ���U�U���s�ƥ��ť��
		registerButtonHanlder();

		// ���USeekBar�i�ק��ܨƥ��ť��
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
		// ����Ҧ����θ귽
		if (mediaPlayer != null) {
			mediaPlayer.release();
			
			// �����p�ɾ�����
			mTimer.cancel();
			mTimerTask.cancel();
		}

		super.onDestroy();
	}

	/**
	 * ��l�Ƥ������
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
	 * ���U�ƥ��ť��
	 */
	private void registerButtonHanlder() {
		/**
		 * ���U�ﶵ���s���@�U�ƥ��ť��
		 */
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// ���mMediaPlayer��H
				if (mediaPlayer != null) {
					mediaPlayer.reset();
				}
			}

		});
		/**
		 * ���U������s��ť��
		 */
		buttonPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �}�l����
				playMusic();
			}
		});

		/**
		 * ���U�Ȱ����s��ť��
		 */
		buttonPause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �Ȱ�����
				pauseMusic();
			}
		});

		/**
		 * ���U������s��ť��
		 */
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �����
				stopMusic();
			}
		});
	}

	/**
	 * �}�l����
	 */
	private void playMusic() {
		// ���mMediaPlayer
		mediaPlayer.reset();

		// �w�q�����ɸ��|
		String musicPath="";

		// �P�_�H���P���覡���JMP3�귽��
		switch (radioGroup.getCheckedRadioButtonId()) {

		// �q�귽��res/raw���JMP3
		case R.id.button_load_from_res:
			// �غcMP3�ɸ��|
			musicPath=MP3_RESOUCES_RAW_PATH + MP3_MUSIC_FILE_NAME;
			currentPlayMusicPath.setText(musicPath);

			// �q�L�ǤJ���res/raw�U��MP3�귽�A�غcMediaPlayer��H
			mediaPlayer=MediaPlayer.create(MainActivity.this,
					R.raw.yesterday_once_more);

			// ���漽��ާ@
			doPlayMusic(musicPath, true);
			break;

		// �qSD�d���JMP3
		case R.id.button_load_from_sdcard:
			// �غcMP3�ɸ��|
			musicPath=MP3_SDCARD_MUSIC_PATH + MP3_MUSIC_FILE_NAME;
			currentPlayMusicPath.setText(musicPath);

			// ���漽��ާ@
			doPlayMusic(musicPath, false);
			break;

		// �q�������JMP3
		case R.id.button_load_from_url:
			// �غcMP3�ɸ��|
			musicPath=MP3_NETWORK_URL;
			currentPlayMusicPath.setText(musicPath);

			// ���漽��ާ@
			doPlayMusic(musicPath, false);
			break;
		}

		// ���ܷ�e���A
		Toast.makeText(MainActivity.this, getString(R.string.msg_playing),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * �Ȱ�����
	 */
	private void pauseMusic() {
		// �P�_��e�O�_���b���񭵼�
		if (mediaPlayer != null&&mediaPlayer.isPlaying()) {
			// �Ȱ�����
			mediaPlayer.pause();

			// ���A���ܸ�T
			Toast.makeText(MainActivity.this, getString(R.string.msg_pasued),
					Toast.LENGTH_LONG).show();
		} else {
			// �~�򼽩�
			mediaPlayer.start();
			Toast.makeText(MainActivity.this,
					getString(R.string.msg_continued), Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * �����
	 */
	private void stopMusic() {
		// �P�_��e�O�_���b���񭵼�
		if (mediaPlayer != null&&mediaPlayer.isPlaying()) {
			// ���mMediaPlayer���l�ƪ��A
			mediaPlayer.reset();
			Toast.makeText(MainActivity.this, getString(R.string.msg_stopped),
					Toast.LENGTH_LONG).show();
			
			mTimer.cancel();
			mTimerTask.cancel();
		}
	}

	/**
	 * �������֪�����ާ@
	 * 
	 * @param musicPath
	 *            �Ҽ���MP3���֪����|
	 * @param isResWay
	 *            �O�_�������귽���J�覡
	 */
	private void doPlayMusic(String musicPath, boolean isResWay) {
		try {
			// �P�_��e�O�_�H�����귽�覡���JMP3��
			if (!isResWay) {
				// �]�m�n����MP3�ɸ��|
				mediaPlayer.setDataSource(musicPath);

				// �i�漽��e�ǳƾާ@
				mediaPlayer.prepare();
			}

			// �]�m�i�ױ��̤j��
			audioSeekBar.setMax(mediaPlayer.getDuration());

			// �έp�ɾ��O������i��
			mTimer=new Timer();
			mTimerTask=new TimerTask() {
				@Override
				public void run() {
					if (progressFlag==true)
						return;
					// �]�m�i�ױ�����e����i��
					audioSeekBar.setProgress(mediaPlayer.getCurrentPosition());
				}
			};

			mTimer.schedule(mTimerTask, 0, 10);

			// �}�l����
			mediaPlayer.start();

			// ���U���񧹦��ƥ��ť��
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					// ���񧹦��ɵ�������
					Toast.makeText(MainActivity.this,
							getString(R.string.play_completion),
							Toast.LENGTH_LONG).show();
					
					// �����w�ɥ���
					mTimer.cancel();
					mTimerTask.cancel();
					
					// �]�m�i�ױ�����l���A
					audioSeekBar.setProgress(0);

					// ���mMediaPlayer����l���A
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
