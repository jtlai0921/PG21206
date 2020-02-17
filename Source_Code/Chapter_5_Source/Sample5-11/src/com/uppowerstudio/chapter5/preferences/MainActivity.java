package com.uppowerstudio.chapter5.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// �w�q�x�s��Preferences�W��
	private static final String PREF_NAME="pref_sample";

	// �w�q�x�s�bPreferences����ƪ�key
	private static final String PREF_KEY="pref_input_data";

	// �ŧi����ܼ�
	private Button saveButton;
	private Button loadButton;
	private EditText txtPrefInput;
	private TextView displayTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ���J�G�����main.xml
		setContentView(R.layout.main);

		// ��l�Ʊ��
		saveButton=(Button) findViewById(R.id.button_save_pref);
		loadButton=(Button) findViewById(R.id.button_load_pref);
		txtPrefInput=(EditText) findViewById(R.id.edit_pref_input_data);
		displayTextView=(TextView) findViewById(R.id.pref_content);

		// ���U�ƥ��ť��
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// ����ϥΪ̿�J�����
					String inputData=txtPrefInput.getText().toString().trim();

					// ���SharedPreferences��H
					SharedPreferences sp=getSharedPreferences(PREF_NAME,
							Context.MODE_WORLD_WRITEABLE);
					// ���SharedPreferences.Editor����A��Preferences�i��ק�ާ@
					SharedPreferences.Editor editor=sp.edit();
					// �]�m�ƾ�
					editor.putString(PREF_KEY, inputData);
					// �I�scommit��k�x�s���
					editor.commit();

					// �����x�s���\
					Toast.makeText(MainActivity.this,
							getString(R.string.msg_save_success),
							Toast.LENGTH_LONG).show();
				} catch (Exception ex) {
					ex.printStackTrace();
					// �����x�s����
					Toast.makeText(MainActivity.this,
							getString(R.string.msg_save_failure),
							Toast.LENGTH_LONG).show();
				}

			}
		});

		loadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���SharedPreferences��H
				SharedPreferences sp=getSharedPreferences(PREF_NAME,
						Context.MODE_WORLD_WRITEABLE);
				// Ū�����e�x�s�����
				String content=sp.getString(PREF_KEY, "");

				// ���Ū�������
				displayTextView.setText(content);
			}
		});

	}
}
