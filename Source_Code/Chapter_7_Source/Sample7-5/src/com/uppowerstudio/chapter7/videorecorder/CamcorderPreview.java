package com.uppowerstudio.chapter7.videorecorder;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ���T���s�w�������{���X
 * @author UPPower Studio
 *
 */
public class CamcorderPreview extends SurfaceView implements
		SurfaceHolder.Callback {

	// �w�q���T���x�s��m
	private static final String VIDEO_SAVE_FOLDER="/sdcard/Sample7-5";

	// �ŧiMediaRecorder�MSufaceHolder��H
	private MediaRecorder mediaRecorder;
	private SurfaceHolder surfaceHolder;

	// �غc��ơA�Ѩt�Ϊ�l�ƮɩI�s
	public CamcorderPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// ���SurfaceHolder��H
		surfaceHolder=getHolder();
		
		// ���U�^�Ǻ�ť��
		surfaceHolder.addCallback(this);
		
		// �]�mSurfaceHolder������
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		// �غcMediaRecorder��H
		mediaRecorder=new MediaRecorder();
		
		// �]�m�ļ˪����T�������J��
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		
		// �]�m�ļ˪����T�����w�]�]�ơX�X�۾�
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		
		// �]�m��X�ɮ׮榡
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		
		// �]�m���T���s�X�覡
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		// �]�m���T���s�X�覡
		mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		File path=new File(VIDEO_SAVE_FOLDER);
		if (!path.exists()){
			path.mkdir();
		}
		
		// �]�m��J��
		mediaRecorder.setOutputFile(VIDEO_SAVE_FOLDER+File.separator
				+"VideoRecord_"+System.currentTimeMillis()+".mp4");
		
		// �]�m�w������
		mediaRecorder.setPreviewDisplay(holder.getSurface());
		
		if (mediaRecorder != null) {
			try {
				// �ǳƶi����s
				mediaRecorder.prepare();
			} catch (IllegalStateException e) {
				Log.e("IllegalStateException", e.toString());
			} catch (IOException e) {
				Log.e("IOException", e.toString());
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	/**
	 * ��^MediaRecorder��H
	 * @return
	 */
	public MediaRecorder getRecorder() {
		return mediaRecorder;
	}

}
