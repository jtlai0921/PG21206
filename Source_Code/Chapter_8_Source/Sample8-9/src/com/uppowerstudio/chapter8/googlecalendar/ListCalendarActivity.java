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
 * Google���ާ@���O
 * 
 * @author UPPower Studio
 * 
 */
public class ListCalendarActivity extends ListActivity implements StaticConst {
	
	// �ŧi���s���
	private Button buttonCreateCalendar;

	// �ŧiHttpTransport��H
	private static HttpTransport transport;

	// �w�q�Ω�O�s��䪺�C��
	private final List<CalendarEntry>calendars=new ArrayList<CalendarEntry>();

	// �ŧi�i�׮��ܼ�
	private ProgressDialog progressDialog;

	// �O�s�Τ�W�αK�X
	private String userName;
	private String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_list);
		
		// ��l�ƨú�ť���s�ƥ��ť��
		initAndRegisterButtonsHandler();
		
		// �K�[�W�U����
		registerForContextMenu(getListView());

		// ����ǤJ��Google�b��W�αK�X
		Bundle bundle=getIntent().getExtras();
		userName=bundle.getString(BUNDLE_KEY_USERNAME);
		password=bundle.getString(BUNDLE_KEY_PASSWORD);

		try {
			// �غcHttpTransport����A�Ω󧹦�Http���U�ؽШD�ާ@
			transport=GoogleTransport.create();
			
			// ���Http�ШD�Y����T
			GoogleHeaders headers=(GoogleHeaders) transport.defaultHeaders;
			// �]�m���ε{���W��
			headers.setApplicationName("Sample8_9-CalendarAndroid/1.0");
			// ��e�A�Ȫ�����
			headers.gdataVersion="2";
			
			// �w�q�ϥ�Atom XML�@����ƶǿ�榡
			AtomParser parser=new AtomParser();
			parser.namespaceDictionary=Namespace.DICTIONARY;
			transport.addParser(parser);

			// �]�m�ϥ�ClientLogin�覡��Google�b���i������
			ClientLogin authenticator=new ClientLogin();
			authenticator.authTokenType=AUTH_SERVICE_TYPE;
			authenticator.username=userName;
			authenticator.password=password;
			
			// ���Ҩù��e�Τ�i����v�ާ@
			authenticator.authenticate().setAuthorizationHeader(transport);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ��������Ҧ���䪺�D�P�B����
		new RetrieveGoogleCalendarTask().execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// �I�����C����i�JGoogle��䬡�ʨƥ󤶭�
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
		
		// �K�[�W�U����
		menu.add(0, CONTEXT_EDIT_CALENDAR, 0,
				getString(R.string.ctx_menu_update_calendar));
		menu.add(0, CONTEXT_DELETE_CALENDAR, 0,
				getString(R.string.ctx_menu_delete_calendar));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// �����e�I�������C��
		AdapterContextMenuInfo info=(AdapterContextMenuInfo) item
				.getMenuInfo();
		CalendarEntry calendar=calendars.get((int) info.id);

		switch (item.getItemId()) {
		// �p�G�I�����O����s��䡨���s
		case CONTEXT_EDIT_CALENDAR:
			// �����s���D�P�B����
			new UpdateGoogleCalendarTask().execute(calendar);
			return true;
		// �p�G�I�����O���R����䡨���s
		case CONTEXT_DELETE_CALENDAR:
			// ����R�����D�P�B����
			new DeleteGoogleCalendarTask().execute(calendar);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	/**
	 * ��l�Ƥ�������õ��U�ƥ��ť��
	 */
	private void initAndRegisterButtonsHandler() {
		buttonCreateCalendar=(Button) findViewById(R.id.button_create_calendar);

		buttonCreateCalendar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ����s�ؤ��D�P�B����
				new NewGoogleCalendarTask().execute();
			}
		});
	}

	/**
	 * �s�ؤ��
	 */
	private void createNewCalender() {
		try {
			// ����s�ؤ�䪺URL�a�}
			CalendarUrl url=CalendarUrl.getOwnCalendarsFeed();
			
			// �]�m�n�s�ؤ�䪺���
			CalendarEntry calendar=new CalendarEntry();
			calendar.title=getString(R.string.new_calendar_title) + "_"
					+DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			calendar.summary=getString(R.string.new_calendar_summary);

			// ����s�ؾާ@
			calendar.executeInsert(transport, url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��s���
	 * 
	 * @param calendar
	 */
	private void updateCalender(CalendarEntry calendar) {
		try {
			// �]�m��s��䪺���
			CalendarEntry updateCalendar=calendar.clone();
			updateCalendar.title=getString(R.string.update_calendar_title)
					+ "_"+DateUtils.formatDate(new Date(), "MM-dd HH:mm");

			// �����s�ާ@
			updateCalendar.executePatchRelativeToOriginal(transport, calendar);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������
	 * 
	 * @return
	 */
	private List<CalendarModel> executeRetrieveCalendars() {
		List<CalendarModel> calendarList=new ArrayList<CalendarModel>();

		List<CalendarEntry> calendars=this.calendars;
		calendars.clear();
		try {
			// ���Ū���Ҧ���䪺Feed������}
			CalendarUrl url=CalendarUrl.getAllCalendarsFeed();
			
			while (true) {
				// �}�l��������Ҧ����ШD
				CalendarFeed feed=CalendarFeed.executeGet(transport, url);
				
				// �p�G���T��^�A�h�N��ƲK�[��M��
				if (feed.calendars !=null) {
					calendars.addAll(feed.calendars);
				}
				String nextLink=feed.getNextLink();
				if (nextLink==null) {
					break;
				}
			}
			int numCalendars=calendars.size();

			// �j��Ū����^����ơA�ëغc�Ω�ListView��ܪ���ƲM��
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
	 * ��������
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
			// ��ܶi�׮�
			progressDialog=ProgressDialog
					.show(ListCalendarActivity.this, null,
							getString(R.string.msg_searching_all_calendars),
							true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<CalendarModel> result) {
			// �����i�׮����
			progressDialog.dismiss();

			// �غc�Ω�����ܪ���ưt����
			CalendarListAdapter adapter=new CalendarListAdapter(
					ListCalendarActivity.this, R.layout.calendar_list_item,
					result);
			setListAdapter(adapter);

			// ���ܥΤ�����C���i�u�X�W�U��\���H������s�B�R�����ާ@
			Toast.makeText(ListCalendarActivity.this,
					getString(R.string.operate_cal_tips), Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * �s�ؤ��
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class NewGoogleCalendarTask extends
			AsyncTask<Void, Void, List<CalendarModel>>{

		@Override
		protected List<CalendarModel> doInBackground(Void... params) {
			// �s�طs���
			createNewCalender();

			// ���s����Ҧ������
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
	 * ��s���
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class UpdateGoogleCalendarTask extends
			AsyncTask<CalendarEntry, Void, List<CalendarModel>> {

		@Override
		protected List<CalendarModel> doInBackground(CalendarEntry... params) {
			// ��s���
			updateCalender(params[0]);

			// ���s����Ҧ������
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
	 * �R�����
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class DeleteGoogleCalendarTask extends
			AsyncTask<CalendarEntry, Void, List<CalendarModel>>{

		@Override
		protected List<CalendarModel> doInBackground(CalendarEntry... params) {
			try {
				// ����R����䪺�ާ@
				params[0].executeDelete(transport);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ���s����Ҧ������
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
