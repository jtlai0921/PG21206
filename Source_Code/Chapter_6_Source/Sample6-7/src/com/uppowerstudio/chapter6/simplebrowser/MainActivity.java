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
 * ²���s����
 * @author UPPower Studio
 */
public class MainActivity extends Activity {

	// �ŧi����ܼ�
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

		// �������W�[�i����ܮ�
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		// ���J�G����
		setContentView(R.layout.main);

		// ��l�Ʊ��
		buttonGo=(ImageButton) findViewById(R.id.btn_go);
		buttonGoBack=(ImageButton) findViewById(R.id.btn_goback);
		buttonFoward=(ImageButton) findViewById(R.id.btn_forward);
		buttonRefresh=(ImageButton) findViewById(R.id.btn_refresh);
		buttonStop=(ImageButton) findViewById(R.id.btn_stop);
		txtUrlAddress=(EditText) findViewById(R.id.url_address);
		webView=(WebView) findViewById(R.id.webview);

		// ��WebView�i���l�Ƴ]�m�ާ@
		initWebViewSettings();

		// ���U�������s�ƥ��ť��
		handlerButtonEvent();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ����^��ɫ�h��W�@���s��������
		if (KeyEvent.KEYCODE_BACK==keyCode &&webView.canGoBack()) {
			// ��h
			webView.goBack();
			// �]�m��}����ܷ�e������}
			txtUrlAddress.setText(webView.getUrl());
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * ��WebView�i���l�Ƴ]�m�ާ@
	 */
	private void initWebViewSettings() {
		// �}��JavaScript���
		webView.getSettings().setJavaScriptEnabled(true);
		// �}���ɮ׳s���\�i
		webView.getSettings().setAllowFileAccess(true);
		// �}�Ҥ��m�Y�񱱨
		webView.getSettings().setBuiltInZoomControls(true);
		// �ϥΧ֨�
		webView.getSettings().setAppCacheEnabled(true);
		// �]�m�q�{���֨�����
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		// �}�Һ����Y��\��
		webView.getSettings().setSupportZoom(true);
		// �]�m�����r���j�p�j�p
		webView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
		// �䴩�ƹ����@�U�ĪG
		webView.getSettings().setLightTouchEnabled(true);

		// �]�m�ۭqWebViewClient���U��H
		webView.setWebViewClient(myWebViewClient);
		// �]�m�ۭqWebChromeClient���U��H
		webView.setWebChromeClient(myChromeClient);
	}

	/**
	 * ���U�������s�ƥ��ť��
	 */
	private void handlerButtonEvent() {
		/**
		 * �e���������s�ƥ��ť��
		 */
		buttonGo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �p�G����J���}�A���ܿ��~����
				if ("".equals(txtUrlAddress.getText())) {
					Toast.makeText(MainActivity.this,
							getString(R.string.url_address_hint),
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					// �����J�����}
					String tmpUrl=txtUrlAddress.getText().toString().trim();
					// ���J����
					webView.loadUrl(tmpUrl);
				}
			}
		});

		/**
		 * ��h���s�ƥ��ť��
		 */
		buttonGoBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �P�_�O�_������h�ާ@
				if (webView.canGoBack()) {
					// �����h�ާ@
					webView.goBack();
					// ��s��}��
					txtUrlAddress.setText(webView.getUrl());
				}
			}
		});

		/**
		 * �e�i���s�ƥ��ť��
		 */
		buttonFoward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �P�_�O�_�����e�i�ާ@
				if (webView.canGoForward()) {
					// ����e�i�ާ@
					webView.goForward();
					// ��s��}��
					txtUrlAddress.setText(webView.getUrl());
				}

			}
		});

		/**
		 * ��s���s�ƥ��ť��
		 */
		buttonRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��s��e����
				webView.reload();
			}
		});

		/**
		 * ������s�ƥ��ť��
		 */
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ������J��e����
				webView.stopLoading();
			}
		});
	}

	/**
	 * �ۭqWebViewClient��H
	 */
	private WebViewClient myWebViewClient=new WebViewClient() {
		/**
		 * ����s��URL�s���ɰ���
		 */
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// ��ܴ��ܸ�T
			Toast.makeText(
					MainActivity.this,
					getString(R.string.execute_method,
							"shouldOverrideUrlLoading"), Toast.LENGTH_SHORT)
					.show();
			
			// ��s��}��
			txtUrlAddress.setText(url);
			
			// ���J��eURL���w����
			view.loadUrl(url);
			return true;
		}

		/**
		 * �����}�l���J�ɰ���
		 */
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// ��ܴ��ܸ�T
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onPageStarted"),
					Toast.LENGTH_SHORT).show();

			// ��ܶi�׮�
			setProgressBarIndeterminateVisibility(true);

			super.onPageStarted(view, url, favicon);
		}

		/**
		 * �������J�����ɰ���
		 */
		@Override
		public void onPageFinished(WebView view, String url) {
			// ��ܴ��ܸ�T
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onPageFinished"),
					Toast.LENGTH_SHORT).show();

			// �����i�׮�
			setProgressBarIndeterminateVisibility(false);

			super.onPageFinished(view, url);
		}

		/**
		 * ����JURL���~�ɰ���
		 */
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// ��ܿ��~����
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_error_msg, description),
					Toast.LENGTH_SHORT).show();
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

	};

	/**
	 * �ۭqWebChromeClient��H
	 */
	private WebChromeClient myChromeClient=new WebChromeClient() {
		/**
		 * ��b�����W����javascript:alert��k�ɩI�s
		 */
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			// ��ܴ��ܸ�T
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onJsAlert"),
					Toast.LENGTH_SHORT).show();
			return super.onJsAlert(view, url, message, result);
		}

		/**
		 * ��b�����W����javascript:confirm��k�ɩI�s
		 */
		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				JsResult result) {
			// ��ܴ��ܸ�T
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onJsConfirm"),
					Toast.LENGTH_SHORT).show();

			return super.onJsConfirm(view, url, message, result);
		}

		/**
		 * ��b�����W����javascript:prompt��k�ɩI�s
		 */
		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {
			// ��ܴ��ܸ�T
			Toast.makeText(MainActivity.this,
					getString(R.string.execute_method, "onJsPrompt"),
					Toast.LENGTH_SHORT).show();
			
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}
	};
}
