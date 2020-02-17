package com.uppowerstudio.chapter3.image;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 圖片視圖範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button changeImgButton;
	private Button changeAlphaButton;
	private ImageView img;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		img=(ImageView) findViewById(R.id.display);

		changeImgButton=(Button) findViewById(R.id.btn_replace);
		changeImgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 替換現有圖片
				img.setImageResource(R.drawable.icon_replace);
			}
		});

		changeAlphaButton=(Button) findViewById(R.id.btn_alpha);
		changeAlphaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 設置透明度為80%
				img.setAlpha(80);
			}
		});
	}
}
