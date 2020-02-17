package com.uppowerstudio.chapter8.googlecalendar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.impl.cookie.DateUtils;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.GoogleTransport;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.xml.atom.AtomParser;

import com.uppowerstudio.chapter8.googlecalendar.common.CalendarEvent;
import com.uppowerstudio.chapter8.googlecalendar.common.CalendarEventFeed;
import com.uppowerstudio.chapter8.googlecalendar.common.CalendarEventListAdapter;
import com.uppowerstudio.chapter8.googlecalendar.common.CalendarUrl;
import com.uppowerstudio.chapter8.googlecalendar.common.StaticConst;
import com.uppowerstudio.chapter8.googlecalendar.models.CalendarEventModel;
import com.uppowerstudio.chapter8.googlecalendar.models.Namespace;
import com.uppowerstudio.chapter8.googlecalendar.models.When;

/**
 * Google日曆活動事件操作類別
 * 
 * @author UPPower Studio
 * 
 */
public class ListCalendarEventsActivity extends ListActivity implements
		StaticConst {

	// 宣告按鈕控制項
	private Button buttonCreateEvent;

	// 宣告HttpTransport對象
	private static HttpTransport transport;

	// 定義用於保存日曆活動事件的列表
	private final List<CalendarEvent> calendarEvents=new ArrayList<CalendarEvent>();

	// 宣告進度框變數
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_event_list);

		// 初始化並監聽按鈕事件監聽器
		initAndRegisterButtonsHandler();

		// 獲取傳入的Google帳戶名及密碼
		Bundle bundle=getIntent().getExtras();
		String userName=bundle.getString(BUNDLE_KEY_USERNAME);
		String password=bundle.getString(BUNDLE_KEY_PASSWORD);

		try {
			// 初始化GoogleTransport對象
			transport=GoogleTransport.create();
			GoogleHeaders headers=(GoogleHeaders) transport.defaultHeaders;
			headers.setApplicationName("Sample8_9-CalendarAndroid/1.0");
			headers.gdataVersion="2";
			AtomParser parser=new AtomParser();
			parser.namespaceDictionary=Namespace.DICTIONARY;
			transport.addParser(parser);

			// 設置使用ClientLogin方式對Google帳號進行驗證
			ClientLogin authenticator=new ClientLogin();
			authenticator.authTokenType=AUTH_SERVICE_TYPE;
			authenticator.username=userName;
			authenticator.password=password;
			authenticator.authenticate().setAuthorizationHeader(transport);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 執行獲取所有日曆活動事件的非同步任務
		new RetrieveGoogleCalendarEventsTask().execute();

	}

	/**
	 * 初始化介面控制項並註冊事件監聽器
	 */
	private void initAndRegisterButtonsHandler() {
		buttonCreateEvent=(Button) findViewById(R.id.button_create_event);

		buttonCreateEvent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 執行新建日曆活動事件的非同步任務
				new NewGoogleCalendarEventTask().execute();
			}
		});
	}

	/**
	 * 新建日曆活動事件
	 */
	private void createNewCalenderEvent() {
		try {
			// 獲取日曆活動事件Feed介面的URL位址
			CalendarUrl url=CalendarUrl.getDefaultPrivateFullEventFeed();

			// 建構日曆活動事件類別
			CalendarEvent event=new CalendarEvent();
			event.title=getString(R.string.new_event_title)
					+"_"
					+DateUtils.formatDate(new Date(System.currentTimeMillis()),
									"MM-dd HH:mm");
			event.content=getString(R.string.new_event_content);

			// 設置日曆活動事件的開始、結束時間
			When when=new When();
			when.startTime="2011-02-14T12:00:00.000+08:00";
			when.endTime="2011-02-14T14:00:00.000+08:00";
			event.when=when;

			// 執行新建日曆活動事件操作
			event.executeInsert(transport, url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 獲取日曆活動事件資料
	 * 
	 * @return
	 */
	private List<CalendarEventModel> executeRetrieveCalendarEvents() {
		List<CalendarEventModel> eventsList=new ArrayList<CalendarEventModel>();

		List<CalendarEvent> events=this.calendarEvents;
		events.clear();
		try {
			// 獲取日曆活動事件Feed介面的URL位址
			CalendarUrl url=CalendarUrl.getDefaultPrivateFullEventFeed();

			while (true) {
				// 執行查詢操作
				CalendarEventFeed feed=CalendarEventFeed.executeEventGet(
						transport, url);

				// 添加到日曆活動事件的列表
				if (feed.events !=null) {
					events.addAll(feed.events);
				}
				String nextLink=feed.getNextLink();
				if (nextLink==null) {
					break;
				}
			}
			int numCalendars=events.size();

			// 迴圈讀取返回的資料，並建構用於ListView顯示的資料清單，並對開始、結束時間進行格式化			// 操作
			for (int i=0; i<numCalendars; i++) {
				CalendarEvent event=events.get(i);

				CalendarEventModel model=new CalendarEventModel();
				model.setTitle(event.title);
				model.setContent(event.content);
				if (event.when !=null&& event.when.startTime != null) {
					model.setEventDate(dateToString(
							parseUtilDate(event.when.startTime,
									"yyyy-MM-dd'T'HH:mm:ss.SSSZ"), "yyyy-MM-dd"));

					model.setStartTime(dateToString(
							parseUtilDate(event.when.startTime,
									"yyyy-MM-dd'T'HH:mm:ss.SSSZ"), "HH:mm"));

				}

				if (event.when !=null&& event.when.endTime !=null) {
					model.setEventDate(dateToString(
							parseUtilDate(event.when.endTime,
									"yyyy-MM-dd'T'HH:mm:ss.SSSZ"), "yyyy-MM-dd"));

					model.setEndTime(dateToString(
							parseUtilDate(event.when.endTime,
									"yyyy-MM-dd'T'HH:mm:ss.SSSZ"), "HH:mm"));
				}
				eventsList.add(model);
			}
		} catch (IOException e) {
			eventsList.clear();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventsList;
	}

	/**
	 * 將字串資料轉換為日期資料
	 * @param str
	 * @param pattern
	 * @return
	 */
	private Date parseUtilDate(String str, String pattern) {
		if (str==null||str.trim().equals(""))
			return null;

		java.util.Date dt=null;
		DateFormat dtFmt=new SimpleDateFormat(pattern, Locale.US);
		try {
			dt=new java.util.Date(dtFmt.parse(str).getTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dt;
	}

	/**
	 * 將日期類型資料轉換為字串
	 * @param date
	 * @param pattern
	 * @return
	 */
	private String dateToString(Date date, String pattern) {
		if (date==null)
			return"";
		DateFormat dtFmt=new SimpleDateFormat(pattern, Locale.US);
		return dtFmt.format(date);
	}

	/**
	 * 獲取日曆資料
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class RetrieveGoogleCalendarEventsTask extends
			AsyncTask<Void, Void, List<CalendarEventModel>> {

		@Override
		protected List<CalendarEventModel> doInBackground(Void... params) {
			// 背景執行獲取日曆活動事件任務
			List<CalendarEventModel> resultList=executeRetrieveCalendarEvents();
			return resultList;
		}

		@Override
		protected void onPreExecute() {
			// 顯示進度框
			progressDialog=ProgressDialog.show(
					ListCalendarEventsActivity.this, null,
					getString(R.string.msg_searching_all_events),true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<CalendarEventModel> result) {
			// 取消進度框顯示
			progressDialog.dismiss();

			// 建構用於日曆活動事件顯示的資料配接器
			CalendarEventListAdapter adapter=new CalendarEventListAdapter(
					ListCalendarEventsActivity.this,
					R.layout.calendar_event_list_item, result);
			setListAdapter(adapter);
		}
	}

	/**
	 * 新建日曆活動事件
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class NewGoogleCalendarEventTask extends
			AsyncTask<Void, Void, List<CalendarEventModel>> {

		@Override
		protected List<CalendarEventModel> doInBackground(Void... params) {
			// 執行新建日曆活動事件
			createNewCalenderEvent();

			// 重新獲取所有日曆活動事件資料
			List<CalendarEventModel> resultList=executeRetrieveCalendarEvents();
			return resultList;
		}

		@Override
		protected void onPreExecute() {			
			progressDialog=ProgressDialog.show(
					ListCalendarEventsActivity.this, null,
					getString(R.string.msg_creating_event), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<CalendarEventModel> result) {
			progressDialog.dismiss();

			CalendarEventListAdapter adapter=new CalendarEventListAdapter(
					ListCalendarEventsActivity.this,
					R.layout.calendar_event_list_item, result);
			setListAdapter(adapter);

			Toast.makeText(ListCalendarEventsActivity.this,
					getString(R.string.msg_created_success), Toast.LENGTH_LONG)
					.show();
		}
	}
}
