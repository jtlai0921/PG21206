package com.uppowerstudio.chapter3.picker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 時間設置範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView txt;
	private DatePicker datePicker;
	private TimePicker timePicker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		datePicker=(DatePicker) findViewById(R.id.date_picker);
		timePicker=(TimePicker) findViewById(R.id.time_picker);
		// 判斷是否是24小時格式，如果不是則設置為24小時格式
		if (!timePicker.is24HourView()) {
			timePicker.setIs24HourView(true);
		}

		txt=(TextView) findViewById(R.id.txt_display);

		Button btn=(Button) findViewById(R.id.btn_submit);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 獲取日期
				int year=datePicker.getYear();
				int month=datePicker.getMonth() + 1;
				int day=datePicker.getDayOfMonth();
				// 獲取時間
				int hour=timePicker.getCurrentHour();
				int minute=timePicker.getCurrentMinute();
				// 顯示時間日期
				txt.setText(getString(R.string.txt_title) + year + "-" + month
						+ "-" + day + " " + hour + ":" + minute);
			}
		});
	}
}
