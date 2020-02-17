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
 * 視訊錄製預覽介面程式碼
 * @author UPPower Studio
 *
 */
public class CamcorderPreview extends SurfaceView implements
		SurfaceHolder.Callback {

	// 定義視訊檔儲存位置
	private static final String VIDEO_SAVE_FOLDER="/sdcard/Sample7-5";

	// 宣告MediaRecorder和SufaceHolder對象
	private MediaRecorder mediaRecorder;
	private SurfaceHolder surfaceHolder;

	// 建構函數，由系統初始化時呼叫
	public CamcorderPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// 獲取SurfaceHolder對象
		surfaceHolder=getHolder();
		
		// 註冊回傳監聽器
		surfaceHolder.addCallback(this);
		
		// 設置SurfaceHolder的類型
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		// 建構MediaRecorder對象
		mediaRecorder=new MediaRecorder();
		
		// 設置採樣的音訊源為麥克風
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		
		// 設置採樣的視訊源為預設設備——相機
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		
		// 設置輸出檔案格式
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		
		// 設置音訊的編碼方式
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		// 設置視訊的編碼方式
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
		
		// 設置輸入檔
		mediaRecorder.setOutputFile(VIDEO_SAVE_FOLDER+File.separator
				+"VideoRecord_"+System.currentTimeMillis()+".mp4");
		
		// 設置預覽視窗
		mediaRecorder.setPreviewDisplay(holder.getSurface());
		
		if (mediaRecorder != null) {
			try {
				// 準備進行錄製
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
	 * 返回MediaRecorder對象
	 * @return
	 */
	public MediaRecorder getRecorder() {
		return mediaRecorder;
	}

}
