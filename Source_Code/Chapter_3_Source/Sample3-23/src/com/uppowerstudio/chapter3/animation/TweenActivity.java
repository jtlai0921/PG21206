package com.uppowerstudio.chapter3.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * 动画示例
 * 
 * @author UPPower Studio
 * 
 */
public class TweenActivity extends Activity {
	private static final String[] ENTRIES = { "Alpha", "Scale", "Translate",
			"Rotate" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tween);

		Spinner s = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ENTRIES);
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 根据不同的选择，展示不同的动画效果
				switch (position) {
				case 0:
					// 获取动画
					Animation alpha = AnimationUtils.loadAnimation(
							TweenActivity.this, R.anim.alpha);
					// 对指定组件开始动画
					findViewById(R.id.txt).startAnimation(alpha);
					break;
				case 1:
					Animation scale = AnimationUtils.loadAnimation(
							TweenActivity.this, R.anim.scale);
					findViewById(R.id.txt).startAnimation(scale);
					break;
				case 2:
					Animation translate = AnimationUtils.loadAnimation(
							TweenActivity.this, R.anim.translate);
					findViewById(R.id.txt).startAnimation(translate);
					break;
				case 3:
					Animation rotate = AnimationUtils.loadAnimation(
							TweenActivity.this, R.anim.rotate);
					findViewById(R.id.txt).startAnimation(rotate);
					break;

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
}