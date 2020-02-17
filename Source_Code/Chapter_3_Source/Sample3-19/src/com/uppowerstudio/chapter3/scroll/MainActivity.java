package com.uppowerstudio.chapter3.scroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 滾動範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 通過兩個按鈕到達不同的Activity
		Button vBtn=(Button) findViewById(R.id.vertical_scroll);
		vBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,
						VerticalActivity.class);
				startActivity(intent);
			}
		});
		Button hBtn=(Button) findViewById(R.id.horizontal_scroll);
		hBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,
						HorizontalActivity.class);
				startActivity(intent);
			}
		});
	}
}
