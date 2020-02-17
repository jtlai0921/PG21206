package com.uppowerstudio.chapter4.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * �D�P�B���Ƚd��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView text;
	private Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text=(TextView) findViewById(R.id.txt);
		Button btn=(Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���LoadTask��H
				LoadTask task=new LoadTask();
				// ����D�P�Btask
				task.execute();
			}
		});
	}

	// �ۭq�~�ө�AsyncTask��LoadTask
	private class LoadTask extends AsyncTask<Void, Integer, String> {

		private ProgressDialog progressDialog;

		@Override
		protected String doInBackground(Void... params) {
			// ����@�Ӹ��J���i��
			int sum=1;
			while (sum <= 10) {
				try {
					// ���F��K�[��A�C�@�ӼW�[�i�׫e�A����v�F0.5��
					Thread.sleep(500);
					// ��s���ȶi��
					publishProgress(sum);
					sum++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// ��^�̫᪺���G
			return "Loading finished.";
		}

		@Override
		protected void onPreExecute() {
			// �s�W�@�Ӷi�׹�ܤ�������
			progressDialog=ProgressDialog.show(MainActivity.this,
					"AsyncTask Demo", "Loading....", true);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// �N�qpublishProgress�ǻ��L�Ӫ��ȶi��榡�ƫ���ܨ�TextView����
			text.setText(values[0] * 10+"%");
		}

		@Override
		protected void onPostExecute(String result) {
			// ���öi�׹�ܤ��
			progressDialog.dismiss();
			// �N�qdoInBackground��^��result�]�m��TextView�ե�
			text.setText(result);
		}

	}
}
