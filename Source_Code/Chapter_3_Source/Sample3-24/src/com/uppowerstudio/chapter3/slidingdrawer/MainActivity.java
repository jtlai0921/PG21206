package com.uppowerstudio.chapter3.slidingdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;
import android.widget.TextView;

/**
 * 抽屜範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private ImageView img;
	private SlidingDrawer sliding;
	private TextView txt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		img=(ImageView) findViewById(R.id.handle);
		sliding=(SlidingDrawer) findViewById(R.id.slidingdrawer);
		txt=(TextView) findViewById(R.id.txt);

		// SlidingDrawer的內容部分被完全打開時所做的操作
		sliding.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				img.setImageResource(R.drawable.down);
			}
		});

		// SlidingDrawer的內容部分被完全關閉時所做的操作
		sliding.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				img.setImageResource(R.drawable.up);
			}
		});

		sliding.setOnDrawerScrollListener(new OnDrawerScrollListener() {
			// SlidingDrawer開始滾動時所做的操作
			@Override
			public void onScrollStarted() {
				txt.setText(R.string.app_name);
			}

			// SlidingDrawer結束滾動時所做的操作
			@Override
			public void onScrollEnded() {
				txt.setText(R.string.txt);
			}
		});
	}
}
