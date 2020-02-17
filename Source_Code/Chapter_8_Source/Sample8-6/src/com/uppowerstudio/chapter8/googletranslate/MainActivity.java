package com.uppowerstudio.chapter8.googletranslate;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.uppowerstudio.chapter8.googletranslate.R;

/**
 * Google翻譯服務範例主介面程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private static final int DIALOG_TRANSLATE_FAILURE=1;

	// 宣告介面控制項變數
	private EditText textSource;
	private EditText translatedResult;
	private Button buttonTranslate;
	private Button buttonClear;
	private Spinner spinnerSource;
	private Spinner spinnerTarget;

	// 顯示進度框物件
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		translatedResult=(EditText) findViewById(R.id.translated_result);
		textSource=(EditText) findViewById(R.id.txt_source);
		buttonTranslate=(Button) findViewById(R.id.button_translate);
		buttonClear=(Button) findViewById(R.id.button_clear);
		spinnerSource=(Spinner) findViewById(R.id.spinner_source);
		spinnerTarget=(Spinner) findViewById(R.id.spinner_target);

		// 設置下拉清單數據
		initSpinners();

		// 註冊按鈕點擊事件監聽器
		buttonTranslate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 非同步執行Google翻譯操作
				new GoogleTranslateTask().execute();
			}
		});

		buttonClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 清除輸入的帳號資料
				textSource.setText("");
				translatedResult.setText("");
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// 顯示授權失敗資訊
		case DIALOG_TRANSLATE_FAILURE:
			return genDialog(MainActivity.this, getString(R.string.app_name),
					getString(R.string.msg_translate_failure),
					android.R.drawable.ic_dialog_alert,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}, null);
		default:
			return null;
		}
	}

	/**
	 * 設置下拉清單數據
	 */
	private void initSpinners() {
		// 從資源檔arrays.xml獲取下拉清單資料
		String[] languageLabel=getResources().getStringArray(
				R.array.language_label);
		String[] languageValue=getResources().getStringArray(
				R.array.language_value);

		List<LabelValueBean> languages=new ArrayList<LabelValueBean>();
		
		// 轉換為LabelValueBean對象
		if (languageLabel != null) {
			for (int i=0; i<languageLabel.length; i++) {
				LabelValueBean lvb=new LabelValueBean(languageLabel[i],
						languageValue[i]);
				languages.add(lvb);
			}
		}

		// 建構用於Spinner控制項的資料配接器
		ArrayAdapter<LabelValueBean> adapter=new ArrayAdapter<LabelValueBean>(
				this, android.R.layout.simple_spinner_item, languages);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// 綁定資料配接器到Spinner控制項
		spinnerSource.setAdapter(adapter);
		spinnerTarget.setAdapter(adapter);		
	}

	/**
	 * 生成對話方塊
	 * 
	 * @param ctx
	 * @param title
	 * @param msg
	 * @param title_icon
	 * @param positiveEvent
	 * @param negativeEvent
	 * @return
	 */
	private Dialog genDialog(Context ctx, String title, String msg,
			int title_icon, DialogInterface.OnClickListener positiveEvent,
			DialogInterface.OnClickListener negativeEvent) {
		Builder dialog=new AlertDialog.Builder(ctx).setIcon(title_icon)
				.setTitle(title).setMessage(msg);
		if (positiveEvent !=null) {
			dialog.setPositiveButton(ctx.getString(R.string.button_submit),
					positiveEvent);
		}

		if (negativeEvent !=null) {
			dialog.setNegativeButton(ctx.getString(R.string.button_cancel),
					negativeEvent);
		}

		dialog.setCancelable(false);
		return dialog.create();
	}

	/**
	 * 非同步執行Google帳號授權驗證類別
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class GoogleTranslateTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// 新建翻譯服務物件
			GoogleTranslate translate=new GoogleTranslate();
			
			// 獲取來源語言類型
			LabelValueBean source=(LabelValueBean)spinnerSource.getSelectedItem();
			String sourceLng=source.getValue();
			
			// 獲取目的語言類型
			LabelValueBean target=(LabelValueBean)spinnerTarget.getSelectedItem();
			String targetLng=target.getValue();
			
			// 進行翻譯操作
			String result=translate.translate(textSource.getText().toString()
					.trim(), sourceLng, targetLng);

			return result;
		}

		@Override
		protected void onPreExecute() {
			// 顯示進度框
			progressDialog=ProgressDialog.show(MainActivity.this, null,
					getString(R.string.msg_translating), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(String result) {
			// 取消進度框顯示
			progressDialog.dismiss();
			
			// 判斷狀態，如果為-1則表示發生錯誤
			if ("-1".equals(result)){
				showDialog(DIALOG_TRANSLATE_FAILURE);
			}else{
				// 顯示翻譯結果
				translatedResult.setText(result);
			}
		}
	}
}
