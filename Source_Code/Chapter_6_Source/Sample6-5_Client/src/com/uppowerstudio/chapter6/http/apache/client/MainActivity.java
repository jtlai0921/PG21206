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
 * HTTP�Τ���U�d�ҫȤ��
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {

	// �w�q���A���a�}
	private static final String SERVER_ADDRESS="http://192.168.2.207:8080/Sample6-5_Server/UserRegisterServer";

	// �ŧi����ܼ�
	private EditText txtUserName;
	private EditText txtEmail;
	private Button buttonHttpGet;
	private Button buttonHttpPost;
	private TextView responseMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ʊ��
		txtUserName=(EditText) findViewById(R.id.txt_user_name);
		txtEmail=(EditText) findViewById(R.id.txt_email);
		buttonHttpGet=(Button) findViewById(R.id.button_http_get);
		buttonHttpPost=(Button) findViewById(R.id.button_http_post);
		responseMessage=(TextView) findViewById(R.id.response_msg);

		// �]�m��T��J�ت��I���B�e����
		responseMessage.setBackgroundColor(Color.BLACK);
		responseMessage.setTextColor(Color.WHITE);
		
		// ���U"�HGET�覡���U"���s�ƥ��ť��
		buttonHttpGet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �HGET�覡�o�e��ơA����ܦ��A����^���G
				registerByGetRequest();
			}
		});

		// ���U"�HPOST�覡���U"���s�ƥ��ť��
		buttonHttpPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �HPOST�覡�o�e��ơA����ܦ��A����^���G
				registerByPostRequest();
			}
		});
	}

	/**
	 * �HGET�ШD�覡�P���A���i��q�H
	 */
	private void registerByGetRequest() {
		// �c�ؽШD��}�A�N�n�o�e����ƪ��[��URL���W
		String url=new StringBuffer(SERVER_ADDRESS).append("?un=")
				.append(txtUserName.getText().toString().trim())
				.append("&email=")
				.append(txtEmail.getText().toString().trim())
				.toString();
		
		try {
			// ��l��HttpClient��H
			HttpClient httpClient=new DefaultHttpClient();
			
			// �s�WHTTP GET�s��
			HttpGet httpGetRequest=new HttpGet(url);
			
			// �V���A���o�eHTTP GET�ШD
			HttpResponse response=httpClient.execute(httpGetRequest);
			
			// �P�_���A����^�����A�A�p�G��200�A�h��ܦ��\�B�z�ШD
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
				// Ū�����A����^�����
				String responseMsg=EntityUtils.toString(response.getEntity());
				
				// �N��ƶi�����
				responseMessage.setText(responseMsg);
			}else{
				// �p�G��^�����A�X����200�A�h�C�L�X�X����T
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
	 * �HPOST�ШD�覡�P���A���i��q�H
	 */
	private void registerByPostRequest() {
		try {
			// ��l��HttpClient��H
			HttpClient httpClient=new DefaultHttpClient();
			
			// �s�WHTTP POST�s��
			HttpPost httpPostRequest=new HttpPost(SERVER_ADDRESS);
			
			// �w�q�Ω�s�x�o�e��ƪ�List
			List<NameValuePair> paramList=new ArrayList<NameValuePair>();
			
			// �c�إΩ�o�e�����
			BasicNameValuePair userNameParam=new BasicNameValuePair("um", txtUserName.
getText().toString().trim());
			BasicNameValuePair emailParam=new BasicNameValuePair("email", txtEmail.
getText().toString().trim());
			paramList.add(userNameParam);
			paramList.add(emailParam);
			
			// ��o�e����ƶi��s�X
			HttpEntity httpEntity=new UrlEncodedFormEntity(paramList, HTTP.UTF_8);
			
			// �]�m�ǿ��ƪ�����
			httpPostRequest.setHeader("content-type", "text/html");

			// �V���A���o�eHTTP POST�ШD
			httpPostRequest.setEntity(httpEntity);	
			
			// Ū�����A����^�����
			HttpResponse response=httpClient.execute(httpPostRequest);
			
			// �P�_���A����^�����A�A�p�G��200�A�h��ܦ��\�B�z�ШD
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
