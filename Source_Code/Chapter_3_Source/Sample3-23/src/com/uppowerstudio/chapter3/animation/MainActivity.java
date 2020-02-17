package com.uppowerstudio.chapter3.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 動畫範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button frameBtn;
	private Button tweenBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		frameBtn=(Button) findViewById(R.id.frame);
		frameBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,
						FrameActivity.class);
				startActivity(intent);
			}
		});
		tweenBtn=(Button) findViewById(R.id.tween);
		tweenBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,
						TweenActivity.class);
				startActivity(intent);
			}
		});
	}
}
