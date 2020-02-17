package com.uppowerstudio.chapter8.googletranslate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

/**
 * Google½Ķ�A�ȵ{���X
 * 
 * @author UPPower Studio
 * 
 */
public class GoogleTranslate {
	// �w�qGoogle½Ķ������}
	private static final String GOOGLE_TRANSLATE_URL="http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&langpair=";
	// �w�q�s�X�榡
	private static final String ENCODING="UTF-8";
	// �w�q�d�߰Ѽ�
	private static final String QUERIED_PARAM="&q=";

	/**
	 * ����½Ķ�ާ@
	 * 
	 * @param text
	 * @param from
	 * @param to
	 * @return
	 */
	public String translate(String text, String from, String to) {
		try {
			// �غc�Ω�½Ķ���r��
			StringBuilder url=new StringBuilder();
			url.append(GOOGLE_TRANSLATE_URL).append(from).append("%7C")
					.append(to);
			url.append(QUERIED_PARAM).append(URLEncoder.encode(text, ENCODING));
			
			// �i��s��
			HttpURLConnection uc=(HttpURLConnection) new URL(url.toString())
					.openConnection();
			
			// �]�mURLConnection���󤹳\�����J�B��X�ާ@
			uc.setDoInput(true);
			uc.setDoOutput(true);
			
			try {
				// �����J�y
				InputStream is=uc.getInputStream();
				// �q��J�y��Ū����^�����
				String result=toString(is);

				// �q��^��JSON��Ƥ����½Ķ���G
				JSONObject json=new JSONObject(result);
				return ((JSONObject) json.get("responseData"))
						.getString("translatedText");
			} finally {
				// �����y
				uc.getInputStream().close();
				if (uc.getErrorStream() != null)
					uc.getErrorStream().close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "";
	}

	/**
	 * �q��^����J�y��Ū�����
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	private String toString(InputStream inputStream) throws Exception {
		StringBuilder outputBuilder=new StringBuilder();
		try {
			String string;
			if (inputStream != null) {
				BufferedReader reader=new BufferedReader(
						new InputStreamReader(inputStream, ENCODING));
				while (null != (string=reader.readLine())) {
					outputBuilder.append(string).append('\n');
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return outputBuilder.toString();
	}

}
