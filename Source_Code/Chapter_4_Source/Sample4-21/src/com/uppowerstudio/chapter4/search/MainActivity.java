package com.uppowerstudio.chapter4.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 搜尋服務範例
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button button=(Button) findViewById(R.id.btn_search);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 呼叫搜尋框
				onSearchRequested();
			}
		});

		// 判斷當前執行的Intent是否為Search操作
		final String queryAction=getIntent().getAction();
		if (Intent.ACTION_SEARCH.equals(queryAction)) {
			// 獲取在搜尋框中輸入的查詢資料
			final String queryString=getIntent().getStringExtra(
					SearchManager.QUERY);
			
			// 顯示查詢資料
			Toast.makeText(this,
					getString(R.string.query_prefix)+queryString,
					Toast.LENGTH_LONG).show();
		}
	}
}
