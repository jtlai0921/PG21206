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
 * Google�j�M�A�ȵ{���X
 * 
 * @author UPPower Studio
 * 
 */
public class GoogleWebSearch {
	// �w�qGoogle�j�M������}
	private static final String GOOGLE_WEB_SEARCH_URL="http://ajax.googleapis.com/ajax/services/search/web?v=1.0";
	// �w�q�s�X�榡
	private static final String ENCODING="UTF-8";
	// �w�q�d�߰Ѽ�
	private static final String QUERIED_PARAM="&q=";
	// �w�q�d�߻y��
	private static final String RESULT_LANGUAGE_PARAM="&hl=zh-TW";
	// �w�q�d�ߪ�^������
	private static final String RESULT_COUNT="&rsz=large";

	// �ŧi�Ω�s��html�аO������ܼ�
	private static String ENTITY_STRINGS[]=null;
	private static String SPECIAL_ENTITIES[]=null;

	// �]�mhtml�аO
	static {
		SPECIAL_ENTITIES=new String[] { "&quot;", "&amp;", "&lt;", "&gt;",
				"&nbsp;", "&(#|\\w){2,8};" };
		ENTITY_STRINGS=new String[] { "\"", "&", "<", ">", " ", "" };
	}

	/**
	 * ����j�M�ާ@
	 * 
	 * @param text
	 * @param from
	 * @param to
	 * @return
	 */
	public List<SearchResultModel>search(String text) {
		List<SearchResultModel>resultList=new ArrayList<SearchResultModel>();

		try {
			// �غc�Ω�j�M���r��
			StringBuilder url=new StringBuilder();
			url.append(GOOGLE_WEB_SEARCH_URL).append(RESULT_LANGUAGE_PARAM)
					.append(RESULT_COUNT).append(QUERIED_PARAM)
					.append(URLEncoder.encode(text, ENCODING));

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

				// �q��^��JSON��Ƥ�����j�M���G
				JSONObject json=new JSONObject(result);

				// �����^���O��
				JSONArray array=((JSONObject) json.get("responseData"))
						.getJSONArray("results");

				// Ū���C�@����^�O���A�غcSearchResultModel����
				for (int i=0; i<array.length(); i++) {
					JSONObject obj=(JSONObject) array.get(i);

					SearchResultModel resultModel=new SearchResultModel();
					// ���title
					resultModel.setTitle(obj.getString("titleNoFormatting"));
					// ���content�A�éI�shtmlToText��k�����Ҧ�html�аO�H�KListView�i						// �����
					resultModel.setContent(htmlToText(obj.getString("content")));
					// ���URL
					resultModel.setUrl(htmlToText(obj.getString("url")));
					resultList.add(resultModel);
				}
			} finally {
				// �����y
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
	 * �q��^����J�y��Ū�����
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
	 * �N�a��HTML�аO������ഫ���¤�r�榡
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
