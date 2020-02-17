package com.uppowerstudio.chapter7.camera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * �۾���ӽd�ҥD�����{���X
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity implements Callback {
	// �w�q�Ӥ��x�s��m
	private static final String SAVE_PICTURE_PATH="/sdcard/Sample7-4";

	// �ŧi��������ܼ�
	private Button buttonPreview;
	private Button buttonTakePhoto;
	
	// �ŧi�Ω�w���ϥΪ�SurfaceView��SurfaceHolder����
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;

	// �ŧi�۾��ܼ�
	private Camera mCamera=null;
	// �ŧi�I�}�Ϫ����ܼ�
	private Bitmap mBitmap=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// ��l�Ʊ��
		buttonPreview=(Button) findViewById(R.id.button_preview);
		buttonTakePhoto=(Button) findViewById(R.id.button_take_photo);
		surfaceView=(SurfaceView) findViewById(R.id.surface_view);

		// ���SurfaceHolder��H
		surfaceHolder=surfaceView.getHolder();
		
		// ���U�^�Ǻ�ť��
		surfaceHolder.addCallback(this);
		
		// �]�mSurfaceHolder������
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// ���U�w�����s���@�U�ƥ�
		buttonPreview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �w��
				mCamera.startPreview();
			}
		});

		buttonTakePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���
				mCamera.takePicture(null, null, pic);
			}
		});

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// �Ұʬ۾��A��
		mCamera=Camera.open();
		try {
			// �]�m�w��
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// ����ҥe�귽
			mCamera.release();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// ����w�]���۾��ݩ�
		Camera.Parameters parameters=mCamera.getParameters();
		// �]�m�Ϥ��榡
		parameters.setPictureFormat(PixelFormat.JPEG);
		// �]�m�ؤo
		parameters.setPreviewSize(320, 480);
		// �]�m�۾��ݩ�
		mCamera.setParameters(parameters);
		// �}�l�w��
		mCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// ����w��
		mCamera.stopPreview();
		// ������θ귽
		mCamera.release();
	}

	// ��ӫ��X�Ϥ�
	public PictureCallback pic=new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// �غc��m����
			mBitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
			
			// �ˬd�Ӥ��x�s�ؿ��O�_�s�b
			File path=new File(SAVE_PICTURE_PATH);
			if (!path.exists()) {
				path.mkdir();
			}			
			
			// �غc�Ӥ���
			File f=new File(SAVE_PICTURE_PATH+File.separator+"Camera_"
					+System.currentTimeMillis()+".jpg");
			
			try {
				// ��X�Ӥ���
				BufferedOutputStream os=new BufferedOutputStream(
						new FileOutputStream(f));
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
				os.flush();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
}
