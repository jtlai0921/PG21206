package com.uppowerstudio.chapter8.googletranslate;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.uppowerstudio.chapter8.googletranslate.R;

/**
 * Google½Ķ�A�Ƚd�ҥD�����{���X
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private static final int DIALOG_TRANSLATE_FAILURE=1;

	// �ŧi��������ܼ�
	private EditText textSource;
	private EditText translatedResult;
	private Button buttonTranslate;
	private Button buttonClear;
	private Spinner spinnerSource;
	private Spinner spinnerTarget;

	// ��ܶi�׮ت���
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		translatedResult=(EditText) findViewById(R.id.translated_result);
		textSource=(EditText) findViewById(R.id.txt_source);
		buttonTranslate=(Button) findViewById(R.id.button_translate);
		buttonClear=(Button) findViewById(R.id.button_clear);
		spinnerSource=(Spinner) findViewById(R.id.spinner_source);
		spinnerTarget=(Spinner) findViewById(R.id.spinner_target);

		// �]�m�U�ԲM��ƾ�
		initSpinners();

		// ���U���s�I���ƥ��ť��
		buttonTranslate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �D�P�B����Google½Ķ�ާ@
				new GoogleTranslateTask().execute();
			}
		});

		buttonClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �M����J���b�����
				textSource.setText("");
				translatedResult.setText("");
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// ��ܱ��v���Ѹ�T
		case DIALOG_TRANSLATE_FAILURE:
			return genDialog(MainActivity.this, getString(R.string.app_name),
					getString(R.string.msg_translate_failure),
					android.R.drawable.ic_dialog_alert,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}, null);
		default:
			return null;
		}
	}

	/**
	 * �]�m�U�ԲM��ƾ�
	 */
	private void initSpinners() {
		// �q�귽��arrays.xml����U�ԲM����
		String[] languageLabel=getResources().getStringArray(
				R.array.language_label);
		String[] languageValue=getResources().getStringArray(
				R.array.language_value);

		List<LabelValueBean> languages=new ArrayList<LabelValueBean>();
		
		// �ഫ��LabelValueBean��H
		if (languageLabel != null) {
			for (int i=0; i<languageLabel.length; i++) {
				LabelValueBean lvb=new LabelValueBean(languageLabel[i],
						languageValue[i]);
				languages.add(lvb);
			}
		}

		// �غc�Ω�Spinner�������ưt����
		ArrayAdapter<LabelValueBean> adapter=new ArrayAdapter<LabelValueBean>(
				this, android.R.layout.simple_spinner_item, languages);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// �j�w��ưt������Spinner���
		spinnerSource.setAdapter(adapter);
		spinnerTarget.setAdapter(adapter);		
	}

	/**
	 * �ͦ���ܤ��
	 * 
	 * @param ctx
	 * @param title
	 * @param msg
	 * @param title_icon
	 * @param positiveEvent
	 * @param negativeEvent
	 * @return
	 */
	private Dialog genDialog(Context ctx, String title, String msg,
			int title_icon, DialogInterface.OnClickListener positiveEvent,
			DialogInterface.OnClickListener negativeEvent) {
		Builder dialog=new AlertDialog.Builder(ctx).setIcon(title_icon)
				.setTitle(title).setMessage(msg);
		if (positiveEvent !=null) {
			dialog.setPositiveButton(ctx.getString(R.string.button_submit),
					positiveEvent);
		}

		if (negativeEvent !=null) {
			dialog.setNegativeButton(ctx.getString(R.string.button_cancel),
					negativeEvent);
		}

		dialog.setCancelable(false);
		return dialog.create();
	}

	/**
	 * �D�P�B����Google�b�����v�������O
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class GoogleTranslateTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// �s��½Ķ�A�Ȫ���
			GoogleTranslate translate=new GoogleTranslate();
			
			// ����ӷ��y������
			LabelValueBean source=(LabelValueBean)spinnerSource.getSelectedItem();
			String sourceLng=source.getValue();
			
			// ����ت��y������
			LabelValueBean target=(LabelValueBean)spinnerTarget.getSelectedItem();
			String targetLng=target.getValue();
			
			// �i��½Ķ�ާ@
			String result=translate.translate(textSource.getText().toString()
					.trim(), sourceLng, targetLng);

			return result;
		}

		@Override
		protected void onPreExecute() {
			// ��ܶi�׮�
			progressDialog=ProgressDialog.show(MainActivity.this, null,
					getString(R.string.msg_translating), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(String result) {
			// �����i�׮����
			progressDialog.dismiss();
			
			// �P�_���A�A�p�G��-1�h��ܵo�Ϳ��~
			if ("-1".equals(result)){
				showDialog(DIALOG_TRANSLATE_FAILURE);
			}else{
				// ���½Ķ���G
				translatedResult.setText(result);
			}
		}
	}
}
