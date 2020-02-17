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
 * 相機拍照範例主介面程式碼
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity implements Callback {
	// 定義照片儲存位置
	private static final String SAVE_PICTURE_PATH="/sdcard/Sample7-4";

	// 宣告介面控制項變數
	private Button buttonPreview;
	private Button buttonTakePhoto;
	
	// 宣告用於預覽使用的SurfaceView及SurfaceHolder物件
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;

	// 宣告相機變數
	private Camera mCamera=null;
	// 宣告點陣圖物件變數
	private Bitmap mBitmap=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// 初始化控制項
		buttonPreview=(Button) findViewById(R.id.button_preview);
		buttonTakePhoto=(Button) findViewById(R.id.button_take_photo);
		surfaceView=(SurfaceView) findViewById(R.id.surface_view);

		// 獲取SurfaceHolder對象
		surfaceHolder=surfaceView.getHolder();
		
		// 註冊回傳監聽器
		surfaceHolder.addCallback(this);
		
		// 設置SurfaceHolder的類型
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// 註冊預覽按鈕按一下事件
		buttonPreview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 預覽
				mCamera.startPreview();
			}
		});

		buttonTakePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 拍照
				mCamera.takePicture(null, null, pic);
			}
		});

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 啟動相機服務
		mCamera=Camera.open();
		try {
			// 設置預覽
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// 釋放所占資源
			mCamera.release();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// 獲取預設的相機屬性
		Camera.Parameters parameters=mCamera.getParameters();
		// 設置圖片格式
		parameters.setPictureFormat(PixelFormat.JPEG);
		// 設置尺寸
		parameters.setPreviewSize(320, 480);
		// 設置相機屬性
		mCamera.setParameters(parameters);
		// 開始預覽
		mCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// 停止預覽
		mCamera.stopPreview();
		// 釋放佔用資源
		mCamera.release();
	}

	// 拍照後輸出圖片
	public PictureCallback pic=new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			// 建構位置物件
			mBitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
			
			// 檢查照片儲存目錄是否存在
			File path=new File(SAVE_PICTURE_PATH);
			if (!path.exists()) {
				path.mkdir();
			}			
			
			// 建構照片檔
			File f=new File(SAVE_PICTURE_PATH+File.separator+"Camera_"
					+System.currentTimeMillis()+".jpg");
			
			try {
				// 輸出照片檔
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
