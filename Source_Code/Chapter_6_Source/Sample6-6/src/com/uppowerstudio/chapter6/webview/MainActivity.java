package com.uppowerstudio.chapter6.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity {
	// 定義連結的網址
	private static final String ACCESS_URL="http://www.android.com";

	// 宣告控制項變數
	private WebView webView;
	private Button buttonLoadUrl;
	private Button buttonLoadData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化控制項
		webView=(WebView) findViewById(R.id.webview_page);
		buttonLoadUrl=(Button) findViewById(R.id.button_loadurl);
		buttonLoadData=(Button) findViewById(R.id.button_loaddata);

		buttonLoadUrl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 連結網址，載入網頁
				webView.loadUrl(ACCESS_URL);
			}
		});

		buttonLoadData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 定義HTML格式的字串資料
				String htmlData=new StringBuffer("<html><body><h2>")
						.append(getString(R.string.load_data))
						.append("</h2><h3><a href=http://www.uppowerstudio.com>UPPower Studio</a></h3></body></html>")
						.toString();
				
				// 定義傳輸資料的類型
				String contentType="text/html";
				
				// 載入網頁
				webView.loadData(htmlData, contentType, "UTF-8");
			}
		});
	}
}
