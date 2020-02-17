package com.uppowerstudio.chapter8.googletranslate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

/**
 * Google翻譯服務程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class GoogleTranslate {
	// 定義Google翻譯介面位址
	private static final String GOOGLE_TRANSLATE_URL="http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&langpair=";
	// 定義編碼格式
	private static final String ENCODING="UTF-8";
	// 定義查詢參數
	private static final String QUERIED_PARAM="&q=";

	/**
	 * 執行翻譯操作
	 * 
	 * @param text
	 * @param from
	 * @param to
	 * @return
	 */
	public String translate(String text, String from, String to) {
		try {
			// 建構用於翻譯的字串
			StringBuilder url=new StringBuilder();
			url.append(GOOGLE_TRANSLATE_URL).append(from).append("%7C")
					.append(to);
			url.append(QUERIED_PARAM).append(URLEncoder.encode(text, ENCODING));
			
			// 進行連接
			HttpURLConnection uc=(HttpURLConnection) new URL(url.toString())
					.openConnection();
			
			// 設置URLConnection物件允許執行輸入、輸出操作
			uc.setDoInput(true);
			uc.setDoOutput(true);
			
			try {
				// 獲取輸入流
				InputStream is=uc.getInputStream();
				// 從輸入流中讀取返回的資料
				String result=toString(is);

				// 從返回的JSON資料中獲取翻譯結果
				JSONObject json=new JSONObject(result);
				return ((JSONObject) json.get("responseData"))
						.getString("translatedText");
			} finally {
				// 關閉流
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
	 * 從返回的輸入流中讀取資料
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
