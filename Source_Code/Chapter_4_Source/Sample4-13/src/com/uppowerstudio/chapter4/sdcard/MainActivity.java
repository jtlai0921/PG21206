package com.uppowerstudio.chapter4.sdcard;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * SD卡範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button btn;
	private TextView displayText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn=(Button) findViewById(R.id.btn);
		displayText=(TextView) findViewById(R.id.display);

		// 獲取SD卡狀態
		String sdState=Environment.getExternalStorageState();
		// 如果SD卡未裝載，則設置按鈕無效，否則為按鈕註冊按一下監聽器
		if (Environment.MEDIA_MOUNTED.equals(sdState)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(sdState)) {
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// 獲得SD卡的File對象
					File file=Environment.getExternalStorageDirectory();
					// 根據SD卡的路徑獲取StatFs物件
					StatFs sf=new StatFs(file.getPath());
					// 獲取資料區塊大小
					long blockSize=sf.getBlockSize();
					// 獲取總資料區塊數
					long totalBlocks=sf.getBlockCount();
					// 計算總容量，並以MB為單位進行顯示
					long totalSize=blockSize * totalBlocks/(1024 * 1024);
					displayText.setText(totalSize+"MB");
				}
			});
		} else {
			btn.setEnabled(false);
		}
	}
}
