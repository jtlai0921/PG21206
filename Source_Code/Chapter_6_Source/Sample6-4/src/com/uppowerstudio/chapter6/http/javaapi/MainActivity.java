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

	// �w�q�Ϥ�������}
	private static final String GOOGLE_LOGO_URL="http://www.google.com.tw/images/srpr/logo3w.png";
	private static final String ANDROID_LOGO_URL="http://www.android.com/images/gingerdroid.png";
	
	// �ŧi����ܼ�
	private Button buttonURLConn;
	private Button buttonHttpURLConn;
	private ImageView imageGoogleLogo;
	private ImageView imageAndroidLogo;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

// ��l�Ʊ��
buttonURLConn=(Button)findViewById(R.id.button_url);
buttonHttpURLConn=(Button)findViewById(R.id.button_http_url);
imageGoogleLogo=(ImageView)findViewById(R.id.image_google_logo);
imageAndroidLogo=(ImageView)findViewById(R.id.image_android_logo);

// ���U��Ū��GoogleLogo�Ϥ��]URLConnection�覡�^�����s���@�U�ƥ�
buttonURLConn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Ū�������GoogleLogo�Ϥ�
				getGoogleLogoByURLConnection();
			}
        });

// ���U��Ū��Android�����H�Ϥ��]HttpURLConnection�覡�^�����s���@�U�ƥ�
buttonHttpURLConn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Ū�������Android�����H�Ϥ�
				getAndroidLogoByHttpURLConnection();
			}
        });
    }

/**
     * Ū�������GoogleLogo�Ϥ�
     */
private void getGoogleLogoByURLConnection(){
	try {
		// �q�L���}�c��URL��H
			URL url=new URL(GOOGLE_LOGO_URL);
			
			// �����URL�귽���s��
			URLConnection urlConn=url.openConnection();
			urlConn.connect();
			
			// �����^��InputStream�y
			InputStream is=urlConn.getInputStream();
			
			// �c��Bitemap��H
			Bitmap googleLogoBitmap=BitmapFactory.decodeStream(is);
			
			// ����InputStream�y
			is.close();
			
			// �]�m�Ϥ���ImageView
			imageGoogleLogo.setImageBitmap(googleLogoBitmap);
			imageGoogleLogo.setScaleType(ImageView.ScaleType.FIT_XY);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

/**
     * Ū�������Android�����H�Ϥ�
     */
private void getAndroidLogoByHttpURLConnection(){
	try {
		// �q�L���}�c��URL��H
			URL url=new URL(ANDROID_LOGO_URL);
			
			// �����URL�귽���s���A�N���ഫ��HttpURLConnection����
			HttpURLConnection httpUrlConn=(HttpURLConnection)url.openConnection();
			httpUrlConn.connect();
			
			// �����^��InputStream�y
			InputStream is=httpUrlConn.getInputStream();
			
			// �c��Bitemap��H
			Bitmap androidLogoBitmap=BitmapFactory.decodeStream(is);
			
			// ����InputStream�y
			is.close();
			
			// �]�m�Ϥ���ImageView
			imageAndroidLogo.setImageBitmap(androidLogoBitmap);
			imageAndroidLogo.setScaleType(ImageView.ScaleType.FIT_XY);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
