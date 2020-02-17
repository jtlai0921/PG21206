package com.uppowerstudio.chapter3.dialogs;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * 特殊對話方塊範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button alertButton01;
	private Button alertButton02;
	private Button progressButton;
	private Button datePickerButton;
	private Button timePickerButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 新增一個對話方塊按鈕單次點擊事件監聽器
		final DialogInterface.OnClickListener closeDialog=new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		};

		// 新增一個日期設置監聽器
		final DatePickerDialog.OnDateSetListener dateSetLisener=new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				view.updateDate(year, monthOfYear + 1, dayOfMonth);
			}
		};

		// 新增一個日期設置監聽器
		final TimePickerDialog.OnTimeSetListener timeSetLisener=new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				view.setCurrentHour(hourOfDay);
				view.setCurrentMinute(minute);
			}
		};

		alertButton01=(Button) findViewById(R.id.dialog_alert1);
		alertButton01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 新增AlertDialog.Builder
				AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(
						MainActivity.this);
				// 通過AlertDialog.Builder初始化AlertDialog
				alertDialogBuilder.setIcon(R.drawable.icon);
				alertDialogBuilder.setTitle(R.string.dialog_title);
				alertDialogBuilder.setMessage(R.string.dialog_content);
				// 初始化AlertDialog的按鈕
				alertDialogBuilder.setNegativeButton(
						R.string.dialog_btn_Negative, closeDialog);
				alertDialogBuilder.setNeutralButton(
						R.string.dialog_btn_Neutral, closeDialog);
				alertDialogBuilder.setPositiveButton(
						R.string.dialog_btn_Positive, closeDialog);
				alertDialogBuilder.create();
				// 顯示AlertDialog
				alertDialogBuilder.show();
			}
		});

		alertButton02=(Button) findViewById(R.id.dialog_alert2);
		alertButton02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 新增AlertDialog.Builder
				AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(
						MainActivity.this);
				// 通過AlertDialog.Builder獲取AlertDialog
				AlertDialog alertDialog=alertDialogBuilder.create();
				alertDialog.setIcon(R.drawable.icon);
				alertDialog.setTitle(R.string.dialog_title);
				alertDialog.setMessage(getString(R.string.dialog_content));
				alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
						getString(R.string.dialog_btn_Positive), closeDialog);
				alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
						getString(R.string.dialog_btn_Neutral), closeDialog);
				alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
						getString(R.string.dialog_btn_Negative), closeDialog);
				// 顯示AlertDialog
				alertDialog.show();
			}
		});

		progressButton=(Button) findViewById(R.id.dialog_progress);
		progressButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 新增ProgressDialog
				ProgressDialog progressDialog=new ProgressDialog(
						MainActivity.this);
				// 初始化ProgressDialog
				progressDialog
						.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setMax(100);
				progressDialog.setTitle(R.string.dialog_title);
				progressDialog.setMessage(getString(R.string.dialog_content));
				progressDialog.setButton(AlertDialog.BUTTON_POSITIVE,
						getString(R.string.dialog_btn_Positive), closeDialog);
				progressDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
						getString(R.string.dialog_btn_Neutral), closeDialog);
				progressDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
						getString(R.string.dialog_btn_Negative), closeDialog);
				// 顯示ProgressDialog
				progressDialog.show();
				// 設置ProgressDialog進度，設置進度得在元件被新增之後才能生效
				progressDialog.setProgress(50);
			}
		});

		datePickerButton=(Button) findViewById(R.id.dialog_date_picker);
		datePickerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar c=Calendar.getInstance();
				// 新增DatePickerDialog
				DatePickerDialog datePickerDialog=new DatePickerDialog(
						MainActivity.this, dateSetLisener,
						c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DATE));
				// 初始化DatePickerDialog
				datePickerDialog.setTitle(R.string.dialog_title);
				datePickerDialog.setMessage(getString(R.string.dialog_content));
				datePickerDialog.setButton(AlertDialog.BUTTON_POSITIVE,
						getString(R.string.dialog_btn_Positive), closeDialog);
				datePickerDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
						getString(R.string.dialog_btn_Neutral), closeDialog);
				datePickerDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
						getString(R.string.dialog_btn_Negative), closeDialog);
				// 顯示DatePickerDialog
				datePickerDialog.show();
			}
		});

		timePickerButton=(Button) findViewById(R.id.dialog_time_picker);
		timePickerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar c=Calendar.getInstance();
				// 新增TimePickerDialog
				TimePickerDialog timePickerDialog=new TimePickerDialog(
						MainActivity.this, timeSetLisener, c
								.get(Calendar.HOUR_OF_DAY), c
								.get(Calendar.MINUTE), true);
				// 初始化TimePickerDialog
				timePickerDialog.setTitle(R.string.dialog_title);
				timePickerDialog.setMessage(getString(R.string.dialog_content));
				timePickerDialog.setButton(AlertDialog.BUTTON_POSITIVE,
						getString(R.string.dialog_btn_Positive), closeDialog);
				timePickerDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
						getString(R.string.dialog_btn_Neutral), closeDialog);
				timePickerDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
						getString(R.string.dialog_btn_Negative), closeDialog);
				// 顯示TimePickerDialog
				timePickerDialog.show();
			}
		});
	}
}
