package com.uppowerstudio.chapter8.googlesearch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Google搜尋服務程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class GoogleWebSearch {
	// 定義Google搜尋介面位址
	private static final String GOOGLE_WEB_SEARCH_URL="http://ajax.googleapis.com/ajax/services/search/web?v=1.0";
	// 定義編碼格式
	private static final String ENCODING="UTF-8";
	// 定義查詢參數
	private static final String QUERIED_PARAM="&q=";
	// 定義查詢語言
	private static final String RESULT_LANGUAGE_PARAM="&hl=zh-TW";
	// 定義查詢返回的條數
	private static final String RESULT_COUNT="&rsz=large";

	// 宣告用於存放html標記的資料變數
	private static String ENTITY_STRINGS[]=null;
	private static String SPECIAL_ENTITIES[]=null;

	// 設置html標記
	static {
		SPECIAL_ENTITIES=new String[] { "&quot;", "&amp;", "&lt;", "&gt;",
				"&nbsp;", "&(#|\\w){2,8};" };
		ENTITY_STRINGS=new String[] { "\"", "&", "<", ">", " ", "" };
	}

	/**
	 * 執行搜尋操作
	 * 
	 * @param text
	 * @param from
	 * @param to
	 * @return
	 */
	public List<SearchResultModel>search(String text) {
		List<SearchResultModel>resultList=new ArrayList<SearchResultModel>();

		try {
			// 建構用於搜尋的字串
			StringBuilder url=new StringBuilder();
			url.append(GOOGLE_WEB_SEARCH_URL).append(RESULT_LANGUAGE_PARAM)
					.append(RESULT_COUNT).append(QUERIED_PARAM)
					.append(URLEncoder.encode(text, ENCODING));

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

				// 從返回的JSON資料中獲取搜尋結果
				JSONObject json=new JSONObject(result);

				// 獲取返回的記錄
				JSONArray array=((JSONObject) json.get("responseData"))
						.getJSONArray("results");

				// 讀取每一條返回記錄，建構SearchResultModel物件
				for (int i=0; i<array.length(); i++) {
					JSONObject obj=(JSONObject) array.get(i);

					SearchResultModel resultModel=new SearchResultModel();
					// 獲取title
					resultModel.setTitle(obj.getString("titleNoFormatting"));
					// 獲取content，並呼叫htmlToText方法移除所有html標記以便ListView進						// 行顯示
					resultModel.setContent(htmlToText(obj.getString("content")));
					// 獲取URL
					resultModel.setUrl(htmlToText(obj.getString("url")));
					resultList.add(resultModel);
				}
			} finally {
				// 關閉流
				uc.getInputStream().close();
				if (uc.getErrorStream() !=null){
					uc.getErrorStream().close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resultList;
	}

	/**
	 * 從返回的輸入流中讀取資料
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	private String toString(InputStream inputStream) throws Exception {
		StringBuilder outputBuilder=new StringBuilder();
		try {
			String string;
			if (inputStream !=null) {
				BufferedReader reader=new BufferedReader(
						new InputStreamReader(inputStream, ENCODING));
				while (null !=(string=reader.readLine())) {
					outputBuilder.append(string).append('\n');
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return outputBuilder.toString();
	}

	/**
	 * 將帶有HTML標記的資料轉換為純文字格式
	 * @param sourceStr
	 * @return
	 */
	private String htmlToText(String sourceStr) {
		String lineSymbol="\n";
		String emptyStr="";
		Object obj;
		if (sourceStr ==null) {
			obj=0;
		} else {
			sourceStr=sourceStr
					.replaceAll("<(?i)head[^>]*?>[\\s\\S]*?</(?i)head>",
							emptyStr)
					.replaceAll("<(?i)style[^>]*?>[\\s\\S]*?</(?i)style>",
							emptyStr)
					.replaceAll("<(?i)script[^>]*?>[\\s\\S]*?</(?i)script>",
							emptyStr).replaceAll("</(?i)div>", lineSymbol)
					.replaceAll("</(?i)p>", lineSymbol)
					.replaceAll("<(?i)br\\s?/?>", "\n")
					.replaceAll("</(?i)h\\d>", lineSymbol)
					.replaceAll("</(?i)tr>", lineSymbol)
					.replaceAll("<!--.*?-->", emptyStr)
					.replaceAll("<[^>]+>", emptyStr);
			obj="\r\n\\s*";
			sourceStr=sourceStr.replaceAll(((String) (obj)), "\n\n");
			int i=0;
			do {
				int temp=SPECIAL_ENTITIES.length;
				if (i>=temp){
					break;
				}
				obj=SPECIAL_ENTITIES[i];
				String s3=ENTITY_STRINGS[i];
				sourceStr=sourceStr.replaceAll(((String) (obj)), s3);
				i++;
			} while (true);
			obj=sourceStr;
		}
		return obj.toString();
	}
}
