package com.uppowerstudio.chapter8.googlecalendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.impl.cookie.DateUtils;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.GoogleTransport;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.xml.atom.AtomParser;

import com.uppowerstudio.chapter8.googlecalendar.common.CalendarEntry;
import com.uppowerstudio.chapter8.googlecalendar.common.CalendarFeed;
import com.uppowerstudio.chapter8.googlecalendar.common.CalendarListAdapter;
import com.uppowerstudio.chapter8.googlecalendar.common.CalendarUrl;
import com.uppowerstudio.chapter8.googlecalendar.common.StaticConst;
import com.uppowerstudio.chapter8.googlecalendar.models.CalendarModel;
import com.uppowerstudio.chapter8.googlecalendar.models.Namespace;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * Google日曆操作類別
 * 
 * @author UPPower Studio
 * 
 */
public class ListCalendarActivity extends ListActivity implements StaticConst {
	
	// 宣告按鈕控制項
	private Button buttonCreateCalendar;

	// 宣告HttpTransport對象
	private static HttpTransport transport;

	// 定義用於保存日曆的列表
	private final List<CalendarEntry>calendars=new ArrayList<CalendarEntry>();

	// 宣告進度框變數
	private ProgressDialog progressDialog;

	// 保存用戶名及密碼
	private String userName;
	private String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_list);
		
		// 初始化並監聽按鈕事件監聽器
		initAndRegisterButtonsHandler();
		
		// 添加上下文選單
		registerForContextMenu(getListView());

		// 獲取傳入的Google帳戶名及密碼
		Bundle bundle=getIntent().getExtras();
		userName=bundle.getString(BUNDLE_KEY_USERNAME);
		password=bundle.getString(BUNDLE_KEY_PASSWORD);

		try {
			// 建構HttpTransport物件，用於完成Http的各種請求操作
			transport=GoogleTransport.create();
			
			// 獲取Http請求頭部資訊
			GoogleHeaders headers=(GoogleHeaders) transport.defaultHeaders;
			// 設置應用程式名稱
			headers.setApplicationName("Sample8_9-CalendarAndroid/1.0");
			// 當前服務版本號
			headers.gdataVersion="2";
			
			// 定義使用Atom XML作為資料傳輸格式
			AtomParser parser=new AtomParser();
			parser.namespaceDictionary=Namespace.DICTIONARY;
			transport.addParser(parser);

			// 設置使用ClientLogin方式對Google帳號進行驗證
			ClientLogin authenticator=new ClientLogin();
			authenticator.authTokenType=AUTH_SERVICE_TYPE;
			authenticator.username=userName;
			authenticator.password=password;
			
			// 驗證並對當前用戶進行授權操作
			authenticator.authenticate().setAuthorizationHeader(transport);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 執行獲取所有日曆的非同步任務
		new RetrieveGoogleCalendarTask().execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// 點擊日曆列表項後進入Google日曆活動事件介面
		Intent intent=new Intent(ACTION_LIST_CALENDAR_EVENTS);
		Bundle bundle=new Bundle();
		bundle.putString(BUNDLE_KEY_USERNAME, userName);
		bundle.putString(BUNDLE_KEY_PASSWORD, password);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		// 添加上下文選單
		menu.add(0, CONTEXT_EDIT_CALENDAR, 0,
				getString(R.string.ctx_menu_update_calendar));
		menu.add(0, CONTEXT_DELETE_CALENDAR, 0,
				getString(R.string.ctx_menu_delete_calendar));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 獲取當前點擊的日曆列表項
		AdapterContextMenuInfo info=(AdapterContextMenuInfo) item
				.getMenuInfo();
		CalendarEntry calendar=calendars.get((int) info.id);

		switch (item.getItemId()) {
		// 如果點擊的是“更新日曆”按鈕
		case CONTEXT_EDIT_CALENDAR:
			// 執行更新日曆非同步任務
			new UpdateGoogleCalendarTask().execute(calendar);
			return true;
		// 如果點擊的是“刪除日曆”按鈕
		case CONTEXT_DELETE_CALENDAR:
			// 執行刪除日曆非同步任務
			new DeleteGoogleCalendarTask().execute(calendar);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	/**
	 * 初始化介面控制項並註冊事件監聽器
	 */
	private void initAndRegisterButtonsHandler() {
		buttonCreateCalendar=(Button) findViewById(R.id.button_create_calendar);

		buttonCreateCalendar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 執行新建日曆非同步任務
				new NewGoogleCalendarTask().execute();
			}
		});
	}

	/**
	 * 新建日曆
	 */
	private void createNewCalender() {
		try {
			// 獲取新建日曆的URL地址
			CalendarUrl url=CalendarUrl.getOwnCalendarsFeed();
			
			// 設置要新建日曆的資料
			CalendarEntry calendar=new CalendarEntry();
			calendar.title=getString(R.string.new_calendar_title) + "_"
					+DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			calendar.summary=getString(R.string.new_calendar_summary);

			// 執行新建操作
			calendar.executeInsert(transport, url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新日曆
	 * 
	 * @param calendar
	 */
	private void updateCalender(CalendarEntry calendar) {
		try {
			// 設置更新日曆的資料
			CalendarEntry updateCalendar=calendar.clone();
			updateCalendar.title=getString(R.string.update_calendar_title)
					+ "_"+DateUtils.formatDate(new Date(), "MM-dd HH:mm");

			// 執行更新操作
			updateCalendar.executePatchRelativeToOriginal(transport, calendar);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 獲取日曆資料
	 * 
	 * @return
	 */
	private List<CalendarModel> executeRetrieveCalendars() {
		List<CalendarModel> calendarList=new ArrayList<CalendarModel>();

		List<CalendarEntry> calendars=this.calendars;
		calendars.clear();
		try {
			// 獲取讀取所有日曆的Feed介面位址
			CalendarUrl url=CalendarUrl.getAllCalendarsFeed();
			
			while (true) {
				// 開始執行獲取所有日曆請求
				CalendarFeed feed=CalendarFeed.executeGet(transport, url);
				
				// 如果正確返回，則將資料添加到清單
				if (feed.calendars !=null) {
					calendars.addAll(feed.calendars);
				}
				String nextLink=feed.getNextLink();
				if (nextLink==null) {
					break;
				}
			}
			int numCalendars=calendars.size();

			// 迴圈讀取返回的資料，並建構用於ListView顯示的資料清單
			for (int i=0; i<numCalendars; i++) {
				CalendarEntry entry=calendars.get(i);

				CalendarModel model=new CalendarModel();
				model.setTitle(entry.title);
				model.setSummary(entry.summary);
				model.setPublishedDate(entry.published);

				calendarList.add(model);
			}
		} catch (IOException e) {
			calendarList.clear();
			e.printStackTrace();
		}

		return calendarList;
	}

	/**
	 * 獲取日曆資料
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class RetrieveGoogleCalendarTask extends
			AsyncTask<Void, Void, List<CalendarModel>>{

		@Override
		protected List<CalendarModel> doInBackground(Void... params) {
			List<CalendarModel> resultList=executeRetrieveCalendars();
			return resultList;
		}

		@Override
		protected void onPreExecute() {
			// 顯示進度框
			progressDialog=ProgressDialog
					.show(ListCalendarActivity.this, null,
							getString(R.string.msg_searching_all_calendars),
							true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<CalendarModel> result) {
			// 取消進度框顯示
			progressDialog.dismiss();

			// 建構用於日曆顯示的資料配接器
			CalendarListAdapter adapter=new CalendarListAdapter(
					ListCalendarActivity.this, R.layout.calendar_list_item,
					result);
			setListAdapter(adapter);

			// 提示用戶長按列表項可彈出上下文功能表以完成更新、刪除日曆操作
			Toast.makeText(ListCalendarActivity.this,
					getString(R.string.operate_cal_tips), Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * 新建日曆
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class NewGoogleCalendarTask extends
			AsyncTask<Void, Void, List<CalendarModel>>{

		@Override
		protected List<CalendarModel> doInBackground(Void... params) {
			// 新建新日曆
			createNewCalender();

			// 重新獲取所有日曆資料
			List<CalendarModel> resultList=executeRetrieveCalendars();
			return resultList;
		}

		@Override
		protected void onPreExecute() {
			progressDialog=ProgressDialog.show(ListCalendarActivity.this,
					null, getString(R.string.msg_creating_calendar), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<CalendarModel> result){
			progressDialog.dismiss();

			CalendarListAdapter adapter=new CalendarListAdapter(
					ListCalendarActivity.this, R.layout.calendar_list_item,
					result);
			setListAdapter(adapter);

			Toast.makeText(ListCalendarActivity.this,
					getString(R.string.msg_created_success), Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * 更新日曆
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class UpdateGoogleCalendarTask extends
			AsyncTask<CalendarEntry, Void, List<CalendarModel>> {

		@Override
		protected List<CalendarModel> doInBackground(CalendarEntry... params) {
			// 更新日曆
			updateCalender(params[0]);

			// 重新獲取所有日曆資料
			List<CalendarModel> resultList=executeRetrieveCalendars();
			return resultList;
		}

		@Override
		protected void onPreExecute() {
			progressDialog=ProgressDialog.show(ListCalendarActivity.this,
					null, getString(R.string.msg_updating_calendar), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<CalendarModel> result) {
			progressDialog.dismiss();

			CalendarListAdapter adapter=new CalendarListAdapter(
					ListCalendarActivity.this, R.layout.calendar_list_item,
					result);
			setListAdapter(adapter);

			Toast.makeText(ListCalendarActivity.this,
					getString(R.string.msg_updated_success), Toast.LENGTH_LONG)
					.show();
		}

	}

	/**
	 * 刪除日曆
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class DeleteGoogleCalendarTask extends
			AsyncTask<CalendarEntry, Void, List<CalendarModel>>{

		@Override
		protected List<CalendarModel> doInBackground(CalendarEntry... params) {
			try {
				// 執行刪除日曆的操作
				params[0].executeDelete(transport);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 重新獲取所有日曆資料
			List<CalendarModel> resultList=executeRetrieveCalendars();
			return resultList;
		}

		@Override
		protected void onPreExecute() {
			progressDialog=ProgressDialog.show(ListCalendarActivity.this,
					null, getString(R.string.msg_deleting_calendar), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<CalendarModel> result) {
			progressDialog.dismiss();

			CalendarListAdapter adapter=new CalendarListAdapter(
					ListCalendarActivity.this, R.layout.calendar_list_item,
					result);
			setListAdapter(adapter);

			Toast.makeText(ListCalendarActivity.this,
					getString(R.string.msg_deleted_success), Toast.LENGTH_LONG)
					.show();
		}
	}
}
