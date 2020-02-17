package com.uppowerstudio.chapter8.googlesearch;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Google網頁搜尋服務範例主介面程式碼
 * @author UPPower Studio
 *
 */
public class MainActivity extends ListActivity {

	// 宣告介面控制項變數
	private EditText searchKeyword;
	private ImageButton buttonSearch;

	// 宣告查詢進度框
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		searchKeyword=(EditText) findViewById(R.id.txt_search_keyword);
		buttonSearch=(ImageButton) findViewById(R.id.button_search);

		// 註冊按鈕點擊事件監聽喊叫
		buttonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 執行Google搜尋服務非同步任務
				new GoogleSearchTask().execute();
			}
		});
	}

	/**
	 * 非同步執行Google搜尋服務類別
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class GoogleSearchTask extends
			AsyncTask<Void, Void, List<SearchResultModel>> {

		@Override
		protected List<SearchResultModel>doInBackground(Void... params) {
			// 呼叫Google搜尋服務進行搜尋操作
			GoogleWebSearch webSearch=new GoogleWebSearch();
			List<SearchResultModel> resultList=webSearch.search(searchKeyword
					.getText().toString());
			return resultList;
		}

		@Override
		protected void onPreExecute() {
			// 顯示進度框
			progressDialog=ProgressDialog.show(MainActivity.this, null,
					getString(R.string.msg_searching), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<SearchResultModel> result) {
			// 取消進度框顯示
			progressDialog.dismiss();

			// 建構用於顯示Google搜尋結果的資料配接器
			SearchResultAdapter adapter=new SearchResultAdapter(
					MainActivity.this, R.layout.list_item, result);
			// 綁定資料配接器到ListView控制項
			getListView().setAdapter(adapter);
		}
	}
}
