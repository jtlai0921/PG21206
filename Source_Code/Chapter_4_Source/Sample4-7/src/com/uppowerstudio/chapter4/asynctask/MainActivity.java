package com.uppowerstudio.chapter4.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 非同步任務範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView text;
	private Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text=(TextView) findViewById(R.id.txt);
		Button btn=(Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 獲取LoadTask對象
				LoadTask task=new LoadTask();
				// 執行非同步task
				task.execute();
			}
		});
	}

	// 自訂繼承於AsyncTask的LoadTask
	private class LoadTask extends AsyncTask<Void, Integer, String> {

		private ProgressDialog progressDialog;

		@Override
		protected String doInBackground(Void... params) {
			// 類比一個載入的進度
			int sum=1;
			while (sum <= 10) {
				try {
					// 為了方便觀察，每一個增加進度前，都休眠了0.5秒
					Thread.sleep(500);
					// 更新任務進度
					publishProgress(sum);
					sum++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 返回最後的結果
			return "Loading finished.";
		}

		@Override
		protected void onPreExecute() {
			// 新增一個進度對話方塊並顯示
			progressDialog=ProgressDialog.show(MainActivity.this,
					"AsyncTask Demo", "Loading....", true);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// 將從publishProgress傳遞過來的值進行格式化後顯示到TextView元件
			text.setText(values[0] * 10+"%");
		}

		@Override
		protected void onPostExecute(String result) {
			// 隱藏進度對話方塊
			progressDialog.dismiss();
			// 將從doInBackground返回的result設置到TextView組件
			text.setText(result);
		}

	}
}
