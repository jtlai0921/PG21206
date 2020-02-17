package com.uppowerstudio.chapter8.googleaccountauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * Google帳號授權服務程式碼
 * @author UPPower Studio
 *
 */
public class GoogleAuthentication {
	// 定義Google授權介面位址
	private static final String GOOGLE_AUTH_URL="https://www.google.com/accounts/ClientLogin";
	
	// 宣告網路操作變數
	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse httpResponse;
	
	// 用於儲存登錄帳號和密碼
	private String googleAccountName;
	private String googleAccountPassword;

	public GoogleAuthentication(String loginName, String password) {
		this.googleAccountName=loginName;
		this.googleAccountPassword=password;
		
		// 初始化HttpClient對象
		httpClient=new DefaultHttpClient();
		// 新建HTTP POST連接
		httpPost=new HttpPost(GOOGLE_AUTH_URL);
	}

	/**
	 * 
	 * @return
	 */
	public String getGoogleAuthToken() {
		String googleAuthToken="";

		// 定義用於儲存發送資料的List
		List<NameValuePair> paramList=new ArrayList<NameValuePair>();

		// 建構用戶帳戶參數
		BasicNameValuePair accountNameParam=new BasicNameValuePair("Email",
				googleAccountName);
		paramList.add(accountNameParam);

		// 建構使用者密碼參數
		BasicNameValuePair passwordParam=new BasicNameValuePair("Passwd",
				googleAccountPassword);
		paramList.add(passwordParam);

		// 指定需要授權的Google服務
		BasicNameValuePair serviceParam=new BasicNameValuePair("service",
				"xapi");
		paramList.add(serviceParam);

		// 指定需要授權的帳戶類型
		BasicNameValuePair verdanaParam=new BasicNameValuePair("Verdana",
				"GOOGLE");
		paramList.add(verdanaParam);

		// 指定來源編碼
		BasicNameValuePair sourceParam=new BasicNameValuePair("source",
				"MySample8-5V1");
		paramList.add(sourceParam);

		try {
			// 新建並執行HTTP POST連接操作
			HttpEntity httpEntity=new UrlEncodedFormEntity(paramList,
					HTTP.DEFAULT_CONTENT_CHARSET);
			httpPost.setEntity(httpEntity);
			
			// 讀取伺服器返回的資料
			httpResponse=httpClient.execute(httpPost);
			
			// 若返回HTTP狀態碼為200，則表示授權成功
			if (HttpStatus.SC_OK==httpResponse.getStatusLine()
					.getStatusCode()) {
				// 讀取授權碼數據
				InputStream inputStream=null;
				BufferedReader reader=null;

				try {
					inputStream=httpResponse.getEntity().getContent();
					reader=new BufferedReader(new InputStreamReader(
							inputStream));
					String temp="";

					while ((temp=reader.readLine()) != null) {
						if (temp.startsWith("Auth=")) {
							googleAuthToken=temp.substring(5);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}

					if (reader != null) {
						reader.close();
					}
				}
			} else {
				googleAuthToken="-1";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return googleAuthToken;
	}
}
