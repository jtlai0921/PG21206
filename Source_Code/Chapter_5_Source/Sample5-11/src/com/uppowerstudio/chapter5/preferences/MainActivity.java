package com.uppowerstudio.chapter5.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// 定義儲存的Preferences名稱
	private static final String PREF_NAME="pref_sample";

	// 定義儲存在Preferences中資料的key
	private static final String PREF_KEY="pref_input_data";

	// 宣告控制項變數
	private Button saveButton;
	private Button loadButton;
	private EditText txtPrefInput;
	private TextView displayTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 載入佈局文件main.xml
		setContentView(R.layout.main);

		// 初始化控制項
		saveButton=(Button) findViewById(R.id.button_save_pref);
		loadButton=(Button) findViewById(R.id.button_load_pref);
		txtPrefInput=(EditText) findViewById(R.id.edit_pref_input_data);
		displayTextView=(TextView) findViewById(R.id.pref_content);

		// 註冊事件監聽器
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// 獲取使用者輸入的資料
					String inputData=txtPrefInput.getText().toString().trim();

					// 獲取SharedPreferences對象
					SharedPreferences sp=getSharedPreferences(PREF_NAME,
							Context.MODE_WORLD_WRITEABLE);
					// 獲取SharedPreferences.Editor物件，對Preferences進行修改操作
					SharedPreferences.Editor editor=sp.edit();
					// 設置數據
					editor.putString(PREF_KEY, inputData);
					// 呼叫commit方法儲存資料
					editor.commit();

					// 提示儲存成功
					Toast.makeText(MainActivity.this,
							getString(R.string.msg_save_success),
							Toast.LENGTH_LONG).show();
				} catch (Exception ex) {
					ex.printStackTrace();
					// 提示儲存失敗
					Toast.makeText(MainActivity.this,
							getString(R.string.msg_save_failure),
							Toast.LENGTH_LONG).show();
				}

			}
		});

		loadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 獲取SharedPreferences對象
				SharedPreferences sp=getSharedPreferences(PREF_NAME,
						Context.MODE_WORLD_WRITEABLE);
				// 讀取之前儲存的資料
				String content=sp.getString(PREF_KEY, "");

				// 顯示讀取的資料
				displayTextView.setText(content);
			}
		});

	}
}
