package com.uppowerstudio.chapter4.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * �j�M�A�Ƚd��
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button button=(Button) findViewById(R.id.btn_search);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �I�s�j�M��
				onSearchRequested();
			}
		});

		// �P�_��e���檺Intent�O�_��Search�ާ@
		final String queryAction=getIntent().getAction();
		if (Intent.ACTION_SEARCH.equals(queryAction)) {
			// ����b�j�M�ؤ���J���d�߸��
			final String queryString=getIntent().getStringExtra(
					SearchManager.QUERY);
			
			// ��ܬd�߸��
			Toast.makeText(this,
					getString(R.string.query_prefix)+queryString,
					Toast.LENGTH_LONG).show();
		}
	}
}
