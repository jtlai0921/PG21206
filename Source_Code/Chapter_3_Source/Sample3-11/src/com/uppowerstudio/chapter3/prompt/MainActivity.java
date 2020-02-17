package com.uppowerstudio.chapter3.prompt;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 提示範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button dialogButton;
	private Button toastButton;
	private Dialog dialog;
	private Toast toast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 構成Dialog
		dialog=new Dialog(MainActivity.this);
		// 設置Dialog的title
		dialog.setTitle(R.string.dialog_title);
		// 設置以另一佈局資源檔為Dialog的內容
		dialog.setContentView(R.layout.content_dialog);
		// 當單次點擊Dialog以外的螢幕時，關閉Dialog
		dialog.setCanceledOnTouchOutside(true);

		// 初始化Toast，持續時間為Toast內建常量LENGTH_LONG
		toast=Toast.makeText(MainActivity.this, R.string.toast_content,
				Toast.LENGTH_LONG);

		// 初始化DialogButton
		dialogButton=(Button) findViewById(R.id.btn_dialog);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 顯示Dialog
				dialog.show();
			}
		});

		// 初始化toastButton
		toastButton=(Button) findViewById(R.id.btn_toast);
		toastButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 顯示Toast
				toast.show();
			}
		});
	}
}
