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
 * Google圖表服務範例主介面程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity implements StaticConst {

	// 宣告介面控制項變數
	private Spinner spinnerChartType;
	private Button buttonDraw;
	private ImageView resultChart;

	// 宣告輸入流變數
	private InputStream resultInputStream;

	// 顯示進度框物件
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化控制項變數
		spinnerChartType=(Spinner) findViewById(R.id.spinner_chart_type);
		buttonDraw=(Button) findViewById(R.id.button_draw);
		resultChart=(ImageView) findViewById(R.id.result_chart);

		// 初始化圖表類型下拉清單
		initSpinners();

		// 註冊按鈕單點事件監聽器
		buttonDraw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 非同步執行Google繪圖操作
				new GoogleDrawChartTask().execute();
			}
		});
	}

	/**
	 * 設置下拉清單數據
	 */
	private void initSpinners() {
		// 從資源檔arrays.xml獲取下拉清單資料
		String[] languageLabel=getResources().getStringArray(
				R.array.chart_type_label);
		String[] languageValue=getResources().getStringArray(
				R.array.chart_type_value);

		List<LabelValueBean> languages=new ArrayList<LabelValueBean>();

		// 轉換為LabelValueBean對象
		if (languageLabel != null) {
			for (int i=0; i<languageLabel.length; i++) {
				LabelValueBean lvb=new LabelValueBean(languageLabel[i],
						languageValue[i]);
				languages.add(lvb);
			}
		}

		// 建構用於Spinner控制項的資料配接器
		ArrayAdapter<LabelValueBean>adapter=new ArrayAdapter<LabelValueBean>(
				this, android.R.layout.simple_spinner_item, languages);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 綁定資料配接器到Spinner控制項
		spinnerChartType.setAdapter(adapter);
		spinnerChartType.setAdapter(adapter);
	}

	/**
	 * Google圖表服務類
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class GoogleDrawChartTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			GoogleChart chart=new GoogleChart(MainActivity.this);

			// 獲取選中的圖表類型
			LabelValueBean lvb=(LabelValueBean) spinnerChartType
					.getSelectedItem();
			String selectedChartType=lvb.getValue();

			// 對所選擇的圖表類型進行判斷
			if (CHART_TYPE_LINE.equals(selectedChartType)) {
				// 傳入圖表的value值完成繪製操作
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
			// 顯示進度框
			progressDialog=ProgressDialog.show(MainActivity.this, null,
					getString(R.string.msg_drawing), true);
			progressDialog.setCancelable(true);
		}

		@Override
		protected void onPostExecute(Void result) {
			// 取消進度框顯示
			progressDialog.dismiss();
			try {
				if (resultInputStream != null) {
					// 顯示繪製的圖片
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
