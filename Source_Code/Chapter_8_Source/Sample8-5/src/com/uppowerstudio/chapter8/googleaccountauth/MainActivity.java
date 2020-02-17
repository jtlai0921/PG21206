package com.uppowerstudio.chapter8.googleaccountauth;

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
import android.widget.Button;
import android.widget.EditText;

/**
 * Google授權服務範例主介面程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	// 定義用於顯示對話方塊的ID
	private static final int DIALOG_SHOW_AUTH_KEY=1;
	private static final int DIALOG_AUTH_FAILURE=2;

	// 宣告介面控制項變數
	private EditText loginAccount;
	private EditText loginPassword;
	private Button buttonLogin;
	private Button buttonClear;

	// 定義用於儲存Google授權碼變數
	private String googleAuth;

	// 顯示進度框物件
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		loginAccount=(EditText) findViewById(R.id.txt_google_account);
		loginPassword=(EditText) findViewById(R.id.txt_google_password);
		buttonLogin=(Button) findViewById(R.id.button_login);
		buttonClear=(Button) findViewById(R.id.button_clear);

		// 註冊按鈕點擊事件監聽器
		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 非同步執行Google授權驗證操作
				new GoogleAuthenticationTask().execute();
			}
		});

		buttonClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 清除輸入的帳號資料
				loginAccount.setText("");
				loginPassword.setText("");
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// 顯示授權成功返回的授權碼
		case DIALOG_SHOW_AUTH_KEY:
			return genDialog(MainActivity.this,
					getString(R.string.result_dialog_title),
					getString(R.string.msg_login_success)+"\n"
							+getString(R.string.label_auth)+"\n"
							+googleAuth, android.R.drawable.ic_dialog_info,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							loginAccount.setText("");
							loginPassword.setText("");
						}
					}, null);
		// 顯示授權失敗資訊
		case DIALOG_AUTH_FAILURE:
			return genDialog(MainActivity.this,
					getString(R.string.result_dialog_title),
					getString(R.string.msg_login_failure),
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
	 * 非同步執行Google帳號授權驗證類
	 * @author UPPower Studio
	 *
	 */
	private class GoogleAuthenticationTask extends
			AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			// 建構自訂Google授權登錄對象
			GoogleAuthentication ga=new GoogleAuthentication(loginAccount
					.getText().toString().trim(), loginPassword.getText()
					.toString().trim());

			// 獲取認證權杖
			String result=ga.getGoogleAuthToken();

			return result;
		}

		@Override
		protected void onPreExecute() {
			// 顯示進度框
			progressDialog=ProgressDialog.show(MainActivity.this, null,
					getString(R.string.msg_logining), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(String result) {
			// 取消進度框顯示
			progressDialog.dismiss();

			// 對授權返回結果進行處理
			if (null !=result && !"".equals(result)) {
				if ("-1".equals(result)) {
					showDialog(DIALOG_AUTH_FAILURE);
				} else {
					// 顯示授權碼
					googleAuth=result;
					showDialog(DIALOG_SHOW_AUTH_KEY);
				}
			} else {
				showDialog(DIALOG_AUTH_FAILURE);
			}
		}
	}
}
