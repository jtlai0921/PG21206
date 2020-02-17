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
 * �ϥ�MediaPlayer�PSurfaceView������T
 * @author UPPower Studio
 *
 */
public class MediaPlayerActivity extends Activity implements
		OnCompletionListener, OnSeekBarChangeListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
	
	// �w�q���T�ɦ�m
	private static final String MP4_FILE="/sdcard/Sample7-3/Yesterday_Once_More.mp4";

	// �ŧi���������ܼ�
	private SurfaceView surfaceView;
	private ImageButton buttonPlay;
	private ImageButton buttonPause;
	private ImageButton buttonStop;

	// �ŧiMediaPlayer��SurfaceHolder�����ܼ�
	private MediaPlayer mediaPlayer;
	private SurfaceHolder surfaceHolder;
	
	// �ŧi�i�ױ��ܼ�
	private SeekBar videoSeekBar=null;
	
	// �ŧi�w�q���ܼ�
	private Timer mTimer;
	private TimerTask mTimerTask;

	// �Ω��x�s���T���e�B��
	private int width=0;
	private int height=0;
	
	// �i�ױ���ʼлx
	private boolean progressFlag=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_player);
		
		// ��l�Ʊ��
		surfaceView=(SurfaceView) findViewById(R.id.surface_view);
		buttonPlay=(ImageButton) findViewById(R.id.button_start);
		buttonPause=(ImageButton) findViewById(R.id.button_pause);
		buttonStop=(ImageButton) findViewById(R.id.button_stop);
		videoSeekBar=(SeekBar) findViewById(R.id.video_seekbar);

		// �غcMediaPlayer
		mediaPlayer=new MediaPlayer();
		
		// ���SurfaceHolder��H
		surfaceHolder=surfaceView.getHolder();
		
		// ���U�^�Ǻ�ť��
		surfaceHolder.addCallback(this);
		
		// �]�mSurfaceHolder������
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		// ���USeekBar�i�ק��ܨƥ��ť��
		videoSeekBar.setOnSeekBarChangeListener(this);

		// ���U���s�ƥ��ť��
		registerButtonHandler();
	}
	
	@Override
	protected void onDestroy() {
		// ����Ҧ����θ귽
		if (mediaPlayer !=null) {
			mediaPlayer.release();
			
			// �����p�ɾ�����
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
		// �]�m����лx
		progressFlag=true;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// �������w��m
		mediaPlayer.seekTo(seekBar.getProgress());
		progressFlag=false;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// ��SurfaceView�s�W�ɶ}�l���T����ާ@
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
		// ������T���e�P��
		width=mediaPlayer.getVideoWidth();
		height=mediaPlayer.getVideoHeight();
		if (width !=0 &&height !=0) {
			// �]�m���T���B�e
			surfaceHolder.setFixedSize(width, height);
			
			// �]�m�i�ױ��̤j��
			videoSeekBar.setMax(mediaPlayer.getDuration());

			// �έp�ɾ��O������i��
			mTimer=new Timer();
			mTimerTask=new TimerTask() {
				@Override
				public void run() {
					if (progressFlag==true)
						return;
					// �]�m�i�ױ�����e����i��
					videoSeekBar.setProgress(mediaPlayer.getCurrentPosition());
				}
			};
			
			// �}�l����w�ɥ���
			mTimer.schedule(mTimerTask, 0, 10);
			
			// �}�l������T
			mediaPlayer.start();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// ���񵲧���ܪ��A���ܸ�T
		Toast.makeText(MediaPlayerActivity.this,
				getString(R.string.play_completion), Toast.LENGTH_LONG).show();
	}

	/**
	 * ���U���s�ƥ��ť��
	 */
	private void registerButtonHandler() {
		// ������s
		buttonPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				playVideo();
			}
		});

		// �Ȱ����s
		buttonPause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pauseVideo();
			}
		});

		// ������s
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopVideo();
			}
		});
	}

	/**
	 * ������T
	 */
	private void playVideo() {
		try {
			// �]�m���T��
			mediaPlayer.setDataSource(MP4_FILE);
			
			// �]�m�q�L�ϥ�SurfaceView�覡��ܵ��T�e��
			mediaPlayer.setDisplay(surfaceHolder);
			
			// �]�m���T����
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			
			// ����e�ǳ�
			mediaPlayer.prepare();
			
			// ���U�ƥ��ť��
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
	 * �Ȱ�����
	 */
	private void pauseVideo() {
		// �P�_��e�O�_���b���񭵼�
		if (mediaPlayer !=null&&mediaPlayer.isPlaying()) {
			// �Ȱ�����
			mediaPlayer.pause();
		} else {
			// �~�򼽩�
			mediaPlayer.start();
		}
	}

	/**
	 * �����
	 */
	private void stopVideo() {
		if (mediaPlayer !=null&&mediaPlayer.isPlaying()) {
			// ���mMediaPlayer���l�ƪ��A
			mediaPlayer.reset();
			Toast.makeText(MediaPlayerActivity.this, getString(R.string.msg_stopped),
					Toast.LENGTH_LONG).show();
			
			// �����w�ɥ���
			mTimer.cancel();
			mTimerTask.cancel();
		}
	}
}
