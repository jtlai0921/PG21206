package com.uppowerstudio.chapter6.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity {
	// �w�q�s�������}
	private static final String ACCESS_URL="http://www.android.com";

	// �ŧi����ܼ�
	private WebView webView;
	private Button buttonLoadUrl;
	private Button buttonLoadData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ʊ��
		webView=(WebView) findViewById(R.id.webview_page);
		buttonLoadUrl=(Button) findViewById(R.id.button_loadurl);
		buttonLoadData=(Button) findViewById(R.id.button_loaddata);

		buttonLoadUrl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �s�����}�A���J����
				webView.loadUrl(ACCESS_URL);
			}
		});

		buttonLoadData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �w�qHTML�榡���r����
				String htmlData=new StringBuffer("<html><body><h2>")
						.append(getString(R.string.load_data))
						.append("</h2><h3><a href=http://www.uppowerstudio.com>UPPower Studio</a></h3></body></html>")
						.toString();
				
				// �w�q�ǿ��ƪ�����
				String contentType="text/html";
				
				// ���J����
				webView.loadData(htmlData, contentType, "UTF-8");
			}
		});
	}
}
