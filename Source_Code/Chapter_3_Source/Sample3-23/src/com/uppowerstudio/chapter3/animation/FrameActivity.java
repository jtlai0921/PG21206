package com.uppowerstudio.chapter3.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 动画示例
 * 
 * @author UPPower Studio
 * 
 */
public class FrameActivity extends Activity {
	private Button playBtn;
	private AnimationDrawable animation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame);

		ImageView img = (ImageView) findViewById(R.id.img);
		// 设置动画
		img.setBackgroundResource(R.anim.frame);
		// 获取动画
		animation = (AnimationDrawable) img.getBackground();

		playBtn = (Button) findViewById(R.id.play);
		playBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 播放动画
				animation.start();
			}
		});
	}
}