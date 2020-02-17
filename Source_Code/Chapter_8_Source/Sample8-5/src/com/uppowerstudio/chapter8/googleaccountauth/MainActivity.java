package com.uppowerstudio.chapter8.googleaccountauth;

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
import android.widget.Button;
import android.widget.EditText;

/**
 * Google���v�A�Ƚd�ҥD�����{���X
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	// �w�q�Ω���ܹ�ܤ����ID
	private static final int DIALOG_SHOW_AUTH_KEY=1;
	private static final int DIALOG_AUTH_FAILURE=2;

	// �ŧi��������ܼ�
	private EditText loginAccount;
	private EditText loginPassword;
	private Button buttonLogin;
	private Button buttonClear;

	// �w�q�Ω��x�sGoogle���v�X�ܼ�
	private String googleAuth;

	// ��ܶi�׮ت���
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		loginAccount=(EditText) findViewById(R.id.txt_google_account);
		loginPassword=(EditText) findViewById(R.id.txt_google_password);
		buttonLogin=(Button) findViewById(R.id.button_login);
		buttonClear=(Button) findViewById(R.id.button_clear);

		// ���U���s�I���ƥ��ť��
		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �D�P�B����Google���v���Ҿާ@
				new GoogleAuthenticationTask().execute();
			}
		});

		buttonClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �M����J���b�����
				loginAccount.setText("");
				loginPassword.setText("");
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// ��ܱ��v���\��^�����v�X
		case DIALOG_SHOW_AUTH_KEY:
			return genDialog(MainActivity.this,
					getString(R.string.result_dialog_title),
					getString(R.string.msg_login_success)+"\n"
							+getString(R.string.label_auth)+"\n"
							+googleAuth, android.R.drawable.ic_dialog_info,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							loginAccount.setText("");
							loginPassword.setText("");
						}
					}, null);
		// ��ܱ��v���Ѹ�T
		case DIALOG_AUTH_FAILURE:
			return genDialog(MainActivity.this,
					getString(R.string.result_dialog_title),
					getString(R.string.msg_login_failure),
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
	 * �D�P�B����Google�b�����v������
	 * @author UPPower Studio
	 *
	 */
	private class GoogleAuthenticationTask extends
			AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			// �غc�ۭqGoogle���v�n����H
			GoogleAuthentication ga=new GoogleAuthentication(loginAccount
					.getText().toString().trim(), loginPassword.getText()
					.toString().trim());

			// ����{���v��
			String result=ga.getGoogleAuthToken();

			return result;
		}

		@Override
		protected void onPreExecute() {
			// ��ܶi�׮�
			progressDialog=ProgressDialog.show(MainActivity.this, null,
					getString(R.string.msg_logining), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(String result) {
			// �����i�׮����
			progressDialog.dismiss();

			// ����v��^���G�i��B�z
			if (null !=result && !"".equals(result)) {
				if ("-1".equals(result)) {
					showDialog(DIALOG_AUTH_FAILURE);
				} else {
					// ��ܱ��v�X
					googleAuth=result;
					showDialog(DIALOG_SHOW_AUTH_KEY);
				}
			} else {
				showDialog(DIALOG_AUTH_FAILURE);
			}
		}
	}
}
