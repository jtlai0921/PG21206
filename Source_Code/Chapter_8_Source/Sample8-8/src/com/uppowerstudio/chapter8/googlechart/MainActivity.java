package com.uppowerstudio.chapter8.googlechart;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Google�Ϫ�A�Ƚd�ҥD�����{���X
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity implements StaticConst {

	// �ŧi��������ܼ�
	private Spinner spinnerChartType;
	private Button buttonDraw;
	private ImageView resultChart;

	// �ŧi��J�y�ܼ�
	private InputStream resultInputStream;

	// ��ܶi�׮ت���
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ʊ���ܼ�
		spinnerChartType=(Spinner) findViewById(R.id.spinner_chart_type);
		buttonDraw=(Button) findViewById(R.id.button_draw);
		resultChart=(ImageView) findViewById(R.id.result_chart);

		// ��l�ƹϪ������U�ԲM��
		initSpinners();

		// ���U���s���I�ƥ��ť��
		buttonDraw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �D�P�B����Googleø�Ͼާ@
				new GoogleDrawChartTask().execute();
			}
		});
	}

	/**
	 * �]�m�U�ԲM��ƾ�
	 */
	private void initSpinners() {
		// �q�귽��arrays.xml����U�ԲM����
		String[] languageLabel=getResources().getStringArray(
				R.array.chart_type_label);
		String[] languageValue=getResources().getStringArray(
				R.array.chart_type_value);

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
		ArrayAdapter<LabelValueBean>adapter=new ArrayAdapter<LabelValueBean>(
				this, android.R.layout.simple_spinner_item, languages);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// �j�w��ưt������Spinner���
		spinnerChartType.setAdapter(adapter);
		spinnerChartType.setAdapter(adapter);
	}

	/**
	 * Google�Ϫ�A����
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class GoogleDrawChartTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			GoogleChart chart=new GoogleChart(MainActivity.this);

			// ����襤���Ϫ�����
			LabelValueBean lvb=(LabelValueBean) spinnerChartType
					.getSelectedItem();
			String selectedChartType=lvb.getValue();

			// ��ҿ�ܪ��Ϫ������i��P�_
			if (CHART_TYPE_LINE.equals(selectedChartType)) {
				// �ǤJ�Ϫ�value�ȧ���ø�s�ާ@
				resultInputStream=chart.drawChart(CHART_TYPE_LINE);
			} else if (CHART_TYPE_BAR.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_BAR);
			} else if (CHART_TYPE_PIE.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_PIE);
			} else if (CHART_TYPE_MAP.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_MAP);
			} else if (CHART_TYPE_SCATTER.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_SCATTER);
			} else if (CHART_TYPE_VENN.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_VENN);
			} else if (CHART_TYPE_RADAR.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_RADAR);
			} else if (CHART_TYPE_QR.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_QR);
			} else if (CHART_TYPE_FORMULA.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_FORMULA);
			} else if (CHART_TYPE_GOOGLE_O_METER.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_GOOGLE_O_METER);
			} else if (CHART_TYPE_COMPLEX.equals(selectedChartType)) {
				resultInputStream=chart.drawChart(CHART_TYPE_COMPLEX);
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			// ��ܶi�׮�
			progressDialog=ProgressDialog.show(MainActivity.this, null,
					getString(R.string.msg_drawing), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(Void result) {
			// �����i�׮����
			progressDialog.dismiss();
			try {
				if (resultInputStream != null) {
					// ���ø�s���Ϥ�
					resultChart.setImageBitmap(BitmapFactory
							.decodeStream(resultInputStream));

					resultInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
