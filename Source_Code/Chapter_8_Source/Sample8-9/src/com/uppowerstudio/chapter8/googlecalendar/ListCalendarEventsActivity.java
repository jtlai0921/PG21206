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
 * Google��䬡�ʨƥ�ާ@���O
 * 
 * @author UPPower Studio
 * 
 */
public class ListCalendarEventsActivity extends ListActivity implements
		StaticConst {

	// �ŧi���s���
	private Button buttonCreateEvent;

	// �ŧiHttpTransport��H
	private static HttpTransport transport;

	// �w�q�Ω�O�s��䬡�ʨƥ󪺦C��
	private final List<CalendarEvent> calendarEvents=new ArrayList<CalendarEvent>();

	// �ŧi�i�׮��ܼ�
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_event_list);

		// ��l�ƨú�ť���s�ƥ��ť��
		initAndRegisterButtonsHandler();

		// ����ǤJ��Google�b��W�αK�X
		Bundle bundle=getIntent().getExtras();
		String userName=bundle.getString(BUNDLE_KEY_USERNAME);
		String password=bundle.getString(BUNDLE_KEY_PASSWORD);

		try {
			// ��l��GoogleTransport��H
			transport=GoogleTransport.create();
			GoogleHeaders headers=(GoogleHeaders) transport.defaultHeaders;
			headers.setApplicationName("Sample8_9-CalendarAndroid/1.0");
			headers.gdataVersion="2";
			AtomParser parser=new AtomParser();
			parser.namespaceDictionary=Namespace.DICTIONARY;
			transport.addParser(parser);

			// �]�m�ϥ�ClientLogin�覡��Google�b���i������
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

		// ��������Ҧ���䬡�ʨƥ󪺫D�P�B����
		new RetrieveGoogleCalendarEventsTask().execute();

	}

	/**
	 * ��l�Ƥ�������õ��U�ƥ��ť��
	 */
	private void initAndRegisterButtonsHandler() {
		buttonCreateEvent=(Button) findViewById(R.id.button_create_event);

		buttonCreateEvent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ����s�ؤ�䬡�ʨƥ󪺫D�P�B����
				new NewGoogleCalendarEventTask().execute();
			}
		});
	}

	/**
	 * �s�ؤ�䬡�ʨƥ�
	 */
	private void createNewCalenderEvent() {
		try {
			// �����䬡�ʨƥ�Feed������URL��}
			CalendarUrl url=CalendarUrl.getDefaultPrivateFullEventFeed();

			// �غc��䬡�ʨƥ����O
			CalendarEvent event=new CalendarEvent();
			event.title=getString(R.string.new_event_title)
					+"_"
					+DateUtils.formatDate(new Date(System.currentTimeMillis()),
									"MM-dd HH:mm");
			event.content=getString(R.string.new_event_content);

			// �]�m��䬡�ʨƥ󪺶}�l�B�����ɶ�
			When when=new When();
			when.startTime="2011-02-14T12:00:00.000+08:00";
			when.endTime="2011-02-14T14:00:00.000+08:00";
			event.when=when;

			// ����s�ؤ�䬡�ʨƥ�ާ@
			event.executeInsert(transport, url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����䬡�ʨƥ���
	 * 
	 * @return
	 */
	private List<CalendarEventModel> executeRetrieveCalendarEvents() {
		List<CalendarEventModel> eventsList=new ArrayList<CalendarEventModel>();

		List<CalendarEvent> events=this.calendarEvents;
		events.clear();
		try {
			// �����䬡�ʨƥ�Feed������URL��}
			CalendarUrl url=CalendarUrl.getDefaultPrivateFullEventFeed();

			while (true) {
				// ����d�߾ާ@
				CalendarEventFeed feed=CalendarEventFeed.executeEventGet(
						transport, url);

				// �K�[���䬡�ʨƥ󪺦C��
				if (feed.events !=null) {
					events.addAll(feed.events);
				}
				String nextLink=feed.getNextLink();
				if (nextLink==null) {
					break;
				}
			}
			int numCalendars=events.size();

			// �j��Ū����^����ơA�ëغc�Ω�ListView��ܪ���ƲM��A�ù�}�l�B�����ɶ��i��榡��			// �ާ@
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
	 * �N�r�����ഫ��������
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
	 * �N�����������ഫ���r��
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
	 * ��������
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class RetrieveGoogleCalendarEventsTask extends
			AsyncTask<Void, Void, List<CalendarEventModel>> {

		@Override
		protected List<CalendarEventModel> doInBackground(Void... params) {
			// �I�����������䬡�ʨƥ����
			List<CalendarEventModel> resultList=executeRetrieveCalendarEvents();
			return resultList;
		}

		@Override
		protected void onPreExecute() {
			// ��ܶi�׮�
			progressDialog=ProgressDialog.show(
					ListCalendarEventsActivity.this, null,
					getString(R.string.msg_searching_all_events),true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<CalendarEventModel> result) {
			// �����i�׮����
			progressDialog.dismiss();

			// �غc�Ω��䬡�ʨƥ���ܪ���ưt����
			CalendarEventListAdapter adapter=new CalendarEventListAdapter(
					ListCalendarEventsActivity.this,
					R.layout.calendar_event_list_item, result);
			setListAdapter(adapter);
		}
	}

	/**
	 * �s�ؤ�䬡�ʨƥ�
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class NewGoogleCalendarEventTask extends
			AsyncTask<Void, Void, List<CalendarEventModel>> {

		@Override
		protected List<CalendarEventModel> doInBackground(Void... params) {
			// ����s�ؤ�䬡�ʨƥ�
			createNewCalenderEvent();

			// ���s����Ҧ���䬡�ʨƥ���
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
