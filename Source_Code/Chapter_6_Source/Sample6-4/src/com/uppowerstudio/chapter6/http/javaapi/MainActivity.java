package com.uppowerstudio.chapter6.http.javaapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	// 定義圖片網路位址
	private static final String GOOGLE_LOGO_URL="http://www.google.com.tw/images/srpr/logo3w.png";
	private static final String ANDROID_LOGO_URL="http://www.android.com/images/gingerdroid.png";
	
	// 宣告控制項變數
	private Button buttonURLConn;
	private Button buttonHttpURLConn;
	private ImageView imageGoogleLogo;
	private ImageView imageAndroidLogo;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

// 初始化控制項
buttonURLConn=(Button)findViewById(R.id.button_url);
buttonHttpURLConn=(Button)findViewById(R.id.button_http_url);
imageGoogleLogo=(ImageView)findViewById(R.id.image_google_logo);
imageAndroidLogo=(ImageView)findViewById(R.id.image_android_logo);

// 註冊“讀取GoogleLogo圖片（URLConnection方式）”按鈕按一下事件
buttonURLConn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 讀取並顯示GoogleLogo圖片
				getGoogleLogoByURLConnection();
			}
        });

// 註冊“讀取Android機器人圖片（HttpURLConnection方式）”按鈕按一下事件
buttonHttpURLConn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 讀取並顯示Android機器人圖片
				getAndroidLogoByHttpURLConnection();
			}
        });
    }

/**
     * 讀取並顯示GoogleLogo圖片
     */
private void getGoogleLogoByURLConnection(){
	try {
		// 通過網址構建URL對象
			URL url=new URL(GOOGLE_LOGO_URL);
			
			// 獲取到URL資源的連結
			URLConnection urlConn=url.openConnection();
			urlConn.connect();
			
			// 獲取返回的InputStream流
			InputStream is=urlConn.getInputStream();
			
			// 構建Bitemap對象
			Bitmap googleLogoBitmap=BitmapFactory.decodeStream(is);
			
			// 關閉InputStream流
			is.close();
			
			// 設置圖片到ImageView
			imageGoogleLogo.setImageBitmap(googleLogoBitmap);
			imageGoogleLogo.setScaleType(ImageView.ScaleType.FIT_XY);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

/**
     * 讀取並顯示Android機器人圖片
     */
private void getAndroidLogoByHttpURLConnection(){
	try {
		// 通過網址構建URL對象
			URL url=new URL(ANDROID_LOGO_URL);
			
			// 獲取到URL資源的連結，將其轉換為HttpURLConnection物件
			HttpURLConnection httpUrlConn=(HttpURLConnection)url.openConnection();
			httpUrlConn.connect();
			
			// 獲取返回的InputStream流
			InputStream is=httpUrlConn.getInputStream();
			
			// 構建Bitemap對象
			Bitmap androidLogoBitmap=BitmapFactory.decodeStream(is);
			
			// 關閉InputStream流
			is.close();
			
			// 設置圖片到ImageView
			imageAndroidLogo.setImageBitmap(androidLogoBitmap);
			imageAndroidLogo.setScaleType(ImageView.ScaleType.FIT_XY);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
