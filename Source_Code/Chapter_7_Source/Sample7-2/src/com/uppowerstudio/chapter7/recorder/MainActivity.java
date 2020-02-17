package com.uppowerstudio.chapter7.recorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.uppowerstudio.chapter7.recorder.utils.CommonUtils;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 簡易錄音器主介面程式碼
 * @author UPPower Studio
 *
 */
public class MainActivity extends ListActivity {
	
	// 定義錄音檔存放位置
	private static final String FOLDER_PATH="/sdcard/Sample7-2/";

	// 定義SD卡無效對話方塊ID
	private static final int DIALOG_SDCARD_UNAVAILABLE=1;

	// 宣告介面控制項變數
	private TextView txtStatus;
	private ImageButton buttonRecord;
	private ImageButton buttonStop;

	// 定義MediaRecorder變數
	private MediaRecorder mediaRecorder;

	// 定義臨時儲存檔
	private File audioRecFile;

	// 定義用於清單顯示的變數
	private List<File> recordList=new ArrayList<File>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		txtStatus=(TextView) findViewById(R.id.txt_status);
		buttonRecord=(ImageButton) findViewById(R.id.button_start_record);
		buttonStop=(ImageButton) findViewById(R.id.button_stop_record);

		// 註冊按鈕事件監聽器
		registerButtonHandler();
		
		// 填充列表
		renderListView();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// 如SD卡無效時顯示對話方塊
		case DIALOG_SDCARD_UNAVAILABLE:
			return CommonUtils.genDialog(MainActivity.this,
					getString(R.string.app_name),
					getString(R.string.error_msg_sdcard_unavailable),
					android.R.drawable.ic_dialog_alert, R.string.button_ok, 0,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}, null);
		default:
			return null;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// 呼叫系統Activity播放錄音檔
		Intent intent=new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(recordList.get(position)), "audio");
		startActivity(intent);
	}

	/**
	 * 註冊按鈕事件監聽器
	 */
	private void registerButtonHandler() {
		buttonRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 開始錄音操作
				startRecording();
			}
		});

		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 停止錄音
				stopRecording();
			}
		});
	}

	/**
	 * 開始錄音
	 */
	private void startRecording() {
		// 檢查SD卡是否有效並同時處於可寫狀態
		if (CommonUtils.isSdCardAvailable() && !CommonUtils.isSdCardReadOnly()) {
			// 判斷儲存音訊的資料夾是否存在，如果不存在則進行新增操作
			File audioRecPath=new File(FOLDER_PATH);
			if (!audioRecPath.exists()) {
				audioRecPath.mkdir();
			}

			try {
				// 如果存放檔路徑正確且SD卡空間有效，則進行錄音操作
				if (audioRecPath != null
						&& CommonUtils.isSdCardStorageAvailable()) {
					
					// 新增暫存檔案，以Record_開頭
					audioRecFile=File.createTempFile("Record_", ".amr",
							audioRecPath);

					// 建構MediaRecorder對象
					mediaRecorder=new MediaRecorder();

					// 設置採樣的音訊源為麥克風
					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					
					// 設置輸出檔案格式
					mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.
					DEFAULT);
					
					// 設置音訊的編碼方式
					mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.
					DEFAULT);
					
					// 設置輸出檔
					mediaRecorder.setOutputFile(audioRecFile.getAbsolutePath());
					
					// 準備錄音操作
					mediaRecorder.prepare();
					
					// 開始錄音
					mediaRecorder.start();

					// 設置狀態顯示
					txtStatus.setText(getString(R.string.prefix_status)
							+getString(R.string.msg_recording));
					
					// 設置錄音按鈕不可用
					buttonRecord.setEnabled(false);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 顯示錯誤對話方塊
			showDialog(DIALOG_SDCARD_UNAVAILABLE);
		}
	}

	/**
	 * 停止錄音
	 */
	private void stopRecording() {
		// 如果正在錄音
		if (audioRecFile !=null) {
			// 停止錄音操作
			mediaRecorder.stop();
			
			// 釋放MediaRecorder對象所佔用資源
			mediaRecorder.release();

			// 設置狀態顯示
			txtStatus.setText(getString(R.string.prefix_status)
					+getString(R.string.msg_stopped));
			
			// 設置錄音按鈕可用
			buttonRecord.setEnabled(true);

			// 將當前錄音文件添加到列表
			recordList.add(audioRecFile);

			// 建構錄音檔清單資料配接器
			RecordArrayAdapter adapter=new RecordArrayAdapter(
					MainActivity.this, R.layout.item, recordList);
			setListAdapter(adapter);
		}
	}

	/**
	 * 顯示錄音檔清單
	 */
	private void renderListView() {
		// 建構儲存檔位置
		File path=new File(FOLDER_PATH);
		
		// 讀取文件列表
		if (path != null&& path.listFiles() !=null
				&& path.listFiles().length> 0) {
			for (File file : path.listFiles()) {
				recordList.add(file);
			}
		}

		// 建構錄音檔清單資料配接器
		RecordArrayAdapter adapter=new RecordArrayAdapter(MainActivity.this,
				R.layout.item, recordList);
		setListAdapter(adapter);
	}
}
