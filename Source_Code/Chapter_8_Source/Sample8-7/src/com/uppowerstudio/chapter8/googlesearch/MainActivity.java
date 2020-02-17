package com.uppowerstudio.chapter8.googlesearch;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Google�����j�M�A�Ƚd�ҥD�����{���X
 * @author UPPower Studio
 *
 */
public class MainActivity extends ListActivity {

	// �ŧi��������ܼ�
	private EditText searchKeyword;
	private ImageButton buttonSearch;

	// �ŧi�d�߶i�׮�
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		searchKeyword=(EditText) findViewById(R.id.txt_search_keyword);
		buttonSearch=(ImageButton) findViewById(R.id.button_search);

		// ���U���s�I���ƥ��ť�ۥs
		buttonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ����Google�j�M�A�ȫD�P�B����
				new GoogleSearchTask().execute();
			}
		});
	}

	/**
	 * �D�P�B����Google�j�M�A�����O
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class GoogleSearchTask extends
			AsyncTask<Void, Void, List<SearchResultModel>> {

		@Override
		protected List<SearchResultModel>doInBackground(Void... params) {
			// �I�sGoogle�j�M�A�ȶi��j�M�ާ@
			GoogleWebSearch webSearch=new GoogleWebSearch();
			List<SearchResultModel> resultList=webSearch.search(searchKeyword
					.getText().toString());
			return resultList;
		}

		@Override
		protected void onPreExecute() {
			// ��ܶi�׮�
			progressDialog=ProgressDialog.show(MainActivity.this, null,
					getString(R.string.msg_searching), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(List<SearchResultModel> result) {
			// �����i�׮����
			progressDialog.dismiss();

			// �غc�Ω����Google�j�M���G����ưt����
			SearchResultAdapter adapter=new SearchResultAdapter(
					MainActivity.this, R.layout.list_item, result);
			// �j�w��ưt������ListView���
			getListView().setAdapter(adapter);
		}
	}
}
