package com.uppowerstudio.chapter6.http.apache.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uppowerstudio.chapter6.http.apache.client.R;

/**
 * HTTP用戶註冊範例客戶端
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {

	// 定義伺服器地址
	private static final String SERVER_ADDRESS="http://192.168.2.207:8080/Sample6-5_Server/UserRegisterServer";

	// 宣告控制項變數
	private EditText txtUserName;
	private EditText txtEmail;
	private Button buttonHttpGet;
	private Button buttonHttpPost;
	private TextView responseMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化控制項
		txtUserName=(EditText) findViewById(R.id.txt_user_name);
		txtEmail=(EditText) findViewById(R.id.txt_email);
		buttonHttpGet=(Button) findViewById(R.id.button_http_get);
		buttonHttpPost=(Button) findViewById(R.id.button_http_post);
		responseMessage=(TextView) findViewById(R.id.response_msg);

		// 設置資訊輸入框的背景、前景色
		responseMessage.setBackgroundColor(Color.BLACK);
		responseMessage.setTextColor(Color.WHITE);
		
		// 註冊"以GET方式註冊"按鈕事件監聽器
		buttonHttpGet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 以GET方式發送資料，並顯示伺服器返回結果
				registerByGetRequest();
			}
		});

		// 註冊"以POST方式註冊"按鈕事件監聽器
		buttonHttpPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 以POST方式發送資料，並顯示伺服器返回結果
				registerByPostRequest();
			}
		});
	}

	/**
	 * 以GET請求方式與伺服器進行通信
	 */
	private void registerByGetRequest() {
		// 構建請求位址，將要發送的資料附加到URL之上
		String url=new StringBuffer(SERVER_ADDRESS).append("?un=")
				.append(txtUserName.getText().toString().trim())
				.append("&email=")
				.append(txtEmail.getText().toString().trim())
				.toString();
		
		try {
			// 初始化HttpClient對象
			HttpClient httpClient=new DefaultHttpClient();
			
			// 新增HTTP GET連結
			HttpGet httpGetRequest=new HttpGet(url);
			
			// 向伺服器發送HTTP GET請求
			HttpResponse response=httpClient.execute(httpGetRequest);
			
			// 判斷伺服器返回的狀態，如果為200，則表示成功處理請求
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				// 讀取伺服器返回的資料
				String responseMsg=EntityUtils.toString(response.getEntity());
				
				// 將資料進行顯示
				responseMessage.setText(responseMsg);
			}else{
				// 如果返回的狀態碼不為200，則列印出出錯資訊
				String errorMsg=getString(R.string.error_msg) + response.getStatusLine().toString();
				responseMessage.setText(errorMsg);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以POST請求方式與伺服器進行通信
	 */
	private void registerByPostRequest() {
		try {
			// 初始化HttpClient對象
			HttpClient httpClient=new DefaultHttpClient();
			
			// 新增HTTP POST連結
			HttpPost httpPostRequest=new HttpPost(SERVER_ADDRESS);
			
			// 定義用於存儲發送資料的List
			List<NameValuePair> paramList=new ArrayList<NameValuePair>();
			
			// 構建用於發送的資料
			BasicNameValuePair userNameParam=new BasicNameValuePair("um", txtUserName.
getText().toString().trim());
			BasicNameValuePair emailParam=new BasicNameValuePair("email", txtEmail.
getText().toString().trim());
			paramList.add(userNameParam);
			paramList.add(emailParam);
			
			// 對發送的資料進行編碼
			HttpEntity httpEntity=new UrlEncodedFormEntity(paramList, HTTP.UTF_8);
			
			// 設置傳輸資料的類型
			httpPostRequest.setHeader("content-type", "text/html");

			// 向伺服器發送HTTP POST請求
			httpPostRequest.setEntity(httpEntity);	
			
			// 讀取伺服器返回的資料
			HttpResponse response=httpClient.execute(httpPostRequest);
			
			// 判斷伺服器返回的狀態，如果為200，則表示成功處理請求
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				String responseMsg=EntityUtils.toString(response.getEntity());
				responseMessage.setText(responseMsg);
			}else{
				String errorMsg=getString(R.string.error_msg) + response.getStatusLine().toString();
				responseMessage.setText(errorMsg);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
