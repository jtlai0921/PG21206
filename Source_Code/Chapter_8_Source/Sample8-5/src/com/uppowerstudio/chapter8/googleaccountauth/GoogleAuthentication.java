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
 * Google�b�����v�A�ȵ{���X
 * @author UPPower Studio
 *
 */
public class GoogleAuthentication {
	// �w�qGoogle���v������}
	private static final String GOOGLE_AUTH_URL="https://www.google.com/accounts/ClientLogin";
	
	// �ŧi�����ާ@�ܼ�
	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse httpResponse;
	
	// �Ω��x�s�n���b���M�K�X
	private String googleAccountName;
	private String googleAccountPassword;

	public GoogleAuthentication(String loginName, String password) {
		this.googleAccountName=loginName;
		this.googleAccountPassword=password;
		
		// ��l��HttpClient��H
		httpClient=new DefaultHttpClient();
		// �s��HTTP POST�s��
		httpPost=new HttpPost(GOOGLE_AUTH_URL);
	}

	/**
	 * 
	 * @return
	 */
	public String getGoogleAuthToken() {
		String googleAuthToken="";

		// �w�q�Ω��x�s�o�e��ƪ�List
		List<NameValuePair> paramList=new ArrayList<NameValuePair>();

		// �غc�Τ�b��Ѽ�
		BasicNameValuePair accountNameParam=new BasicNameValuePair("Email",
				googleAccountName);
		paramList.add(accountNameParam);

		// �غc�ϥΪ̱K�X�Ѽ�
		BasicNameValuePair passwordParam=new BasicNameValuePair("Passwd",
				googleAccountPassword);
		paramList.add(passwordParam);

		// ���w�ݭn���v��Google�A��
		BasicNameValuePair serviceParam=new BasicNameValuePair("service",
				"xapi");
		paramList.add(serviceParam);

		// ���w�ݭn���v���b������
		BasicNameValuePair verdanaParam=new BasicNameValuePair("Verdana",
				"GOOGLE");
		paramList.add(verdanaParam);

		// ���w�ӷ��s�X
		BasicNameValuePair sourceParam=new BasicNameValuePair("source",
				"MySample8-5V1");
		paramList.add(sourceParam);

		try {
			// �s�بð���HTTP POST�s���ާ@
			HttpEntity httpEntity=new UrlEncodedFormEntity(paramList,
					HTTP.DEFAULT_CONTENT_CHARSET);
			httpPost.setEntity(httpEntity);
			
			// Ū�����A����^�����
			httpResponse=httpClient.execute(httpPost);
			
			// �Y��^HTTP���A�X��200�A�h��ܱ��v���\
			if (HttpStatus.SC_OK==httpResponse.getStatusLine()
					.getStatusCode()) {
				// Ū�����v�X�ƾ�
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
