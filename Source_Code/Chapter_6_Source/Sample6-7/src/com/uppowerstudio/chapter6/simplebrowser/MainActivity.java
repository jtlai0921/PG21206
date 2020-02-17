package com.uppowerstudio.chapter6.simplebrowser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * 簡易瀏覽器
 * @author UPPower Studio
 */
public class MainActivity extends Activity {

	// 宣告控制項變數
	private ImageButton buttonGo;
	private ImageButton buttonGoBack;
	private ImageButton buttonFoward;
	private ImageButton buttonRefresh;
	private ImageButton buttonStop;
	private EditText txtUrlAddress;
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 為介面增加進度顯示框
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		// 載入佈局檔
		setContentView(R.layout.main);

		// 初始化控制項
		buttonGo=(ImageButton) findViewById(R.id.btn_go);
		buttonGoBack=(ImageButton) findViewById(R.id.btn_goback);
		buttonFoward=(ImageButton) findViewById(R.id.btn_forward);
		buttonRefresh=(ImageButton) findViewById(R.id.btn_refresh);
		buttonStop=(ImageButton) findViewById(R.id.btn_stop);
		txtUrlAddress=(EditText) findViewById(R.id.url_address);
		webView=(WebView) findViewById(R.id.webview);

		// 對WebView進行初始化設置操作
		initWebViewSettings();

		// 註冊介面按鈕事件監聽器
		handlerButtonEvent();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按返回鍵時後退到上一次瀏覽的網頁
		if (KeyEvent.KEYCODE_BACK==keyCode &&webView.canGoBack()) {
			// 後退
			webView.goBack();
			// 設置位址欄顯示當前網頁位址
			txtUrlAddress.setText(webView.getUrl());
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * 對WebView進行初始化設置操作
	 */
	private void initWebViewSettings() {
		// 開啟JavaScript支持
		webView.getSettings().setJavaScriptEnabled(true);
		// 開啟檔案連結許可
		webView.getSettings().setAllowFileAccess(true);
		// 開啟內置縮放控制項
		webView.getSettings().setBuiltInZoomControls(true);
		// 使用快取
		webView.getSettings().setAppCacheEnabled(true);
		// 設置默認的快取策略
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		// 開啟網頁縮放功能
		webView.getSettings().setSupportZoom(true);
		// 設置網頁字型大小大小
		webView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
		// 支援滑鼠按一下效果
		webView.getSettings().setLightTouchEnabled(true);

		// 設置自訂WebViewClient輔助對象
		webView.setWebViewClient(myWebViewClient);
		// 設置自訂WebChromeClient輔助對象
		webView.setWebChromeClient(myChromeClient);
	}

	/**
	 * 註冊介面按鈕事件監聽器
	 */
	private void handlerButtonEvent() {
		/**
		 * 前往網頁按鈕事件監聽器
		 */
		buttonGo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 如果未輸入網址，提示錯誤消息
				if ("".equals(txtUrlAddress.getText())) {
					Toast.makeText(MainActivity.this,
							getString(R.string.url_address_hint),
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					// 獲取輸入的網址
					String tmpUrl=txtUrlAddress.getText().toString().trim();
					// 載入網頁
					webView.loadUrl(tmpUrl);
				}
			}
		});

		/**
		 * 後退按鈕事件監聽器
		 */
		buttonGoBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 判斷是否能執行後退操作
				if (webView.canGoBack()) {
					// 執行後退操作
					webView.goBack();
					// 更新位址欄
					txtUrlAddress.setText(webView.getUrl());
				}
			}
		});

		/**
		 * 前進按鈕事件監聽器
		 */
		buttonFoward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 判斷是否能執行前進操作
				if (webView.canGoForward()) {
					// 執行前進操作
					webView.goForward();
					// 更新位址欄
					txtUrlAddress.setText(webView.getUrl());
				}

			}
		});

		/**
		 * 刷新按鈕事件監聽器
		 */
		buttonRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 刷新當前頁面
				webView.reload();
			}
		});

		/**
		 * 停止按鈕事件監聽器
		 */
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 停止載入當前頁面
				webView.stopLoading();
			}
		});
	}

	/**
	 * 自訂WebViewClient對象
	 */
	private WebViewClient myWebViewClient=new WebViewClient() {
		/**
		 * 執行新的URL連結時執行
		 */
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// 顯示提示資訊
			Toast.makeText(
					MainActivity.this,
					getString(R.string.execute_method,
							"shouldOverrideUrlLoading"), Toast.LENGTH_SHORT)
					.show();
			
			// 更新位址欄
			txtUrlAddress.setText(url);
			
			// 載入當前URL指定網頁
			view.loadUrl(url);
			return true;
		}

		/**
		 * 當頁面開始載入時執行
		 */
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// 顯示提示資訊
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onPageStarted"),
					Toast.LENGTH_SHORT).show();

			// 顯示進度框
			setProgressBarIndeterminateVisibility(true);

			super.onPageStarted(view, url, favicon);
		}

		/**
		 * 當頁面載入完成時執行
		 */
		@Override
		public void onPageFinished(WebView view, String url) {
			// 顯示提示資訊
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onPageFinished"),
					Toast.LENGTH_SHORT).show();

			// 關閉進度框
			setProgressBarIndeterminateVisibility(false);

			super.onPageFinished(view, url);
		}

		/**
		 * 當載入URL錯誤時執行
		 */
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// 顯示錯誤消息
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_error_msg, description),
					Toast.LENGTH_SHORT).show();
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

	};

	/**
	 * 自訂WebChromeClient對象
	 */
	private WebChromeClient myChromeClient=new WebChromeClient() {
		/**
		 * 當在頁面上執行javascript:alert方法時呼叫
		 */
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			// 顯示提示資訊
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onJsAlert"),
					Toast.LENGTH_SHORT).show();
			return super.onJsAlert(view, url, message, result);
		}

		/**
		 * 當在頁面上執行javascript:confirm方法時呼叫
		 */
		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				JsResult result) {
			// 顯示提示資訊
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onJsConfirm"),
					Toast.LENGTH_SHORT).show();

			return super.onJsConfirm(view, url, message, result);
		}

		/**
		 * 當在頁面上執行javascript:prompt方法時呼叫
		 */
		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {
			// 顯示提示資訊
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onJsPrompt"),
					Toast.LENGTH_SHORT).show();
			
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}
	};
}
