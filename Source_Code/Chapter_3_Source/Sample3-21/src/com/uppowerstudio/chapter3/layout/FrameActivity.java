package com.uppowerstudio.chapter3.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 佈局範例
 * 
 * @author UPPower Studio
 * 
 */
public class FrameActivity extends Activity {
	private Button linearBtn;
	private Button frameBtn;
	private Button relativeBtn;
	private Button absBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame);
		linearBtn = (Button) findViewById(R.id.linear);
		linearBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FrameActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		frameBtn = (Button) findViewById(R.id.frame);
		frameBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		relativeBtn = (Button) findViewById(R.id.relative);
		relativeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FrameActivity.this,
						RelativeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		absBtn = (Button) findViewById(R.id.abs);
		absBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FrameActivity.this,
						AbsActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}