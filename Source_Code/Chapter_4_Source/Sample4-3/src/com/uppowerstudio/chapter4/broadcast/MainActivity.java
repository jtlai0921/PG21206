package com.uppowerstudio.chapter4.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 廣播範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn=(Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				// 設置廣播的內容
				intent.putExtra("CONTENT", "This is broadcast demo");
				// 設置廣播的action
				intent.setAction("com.uppowerstudio.chapter4.broadcast");
				// 發送廣播
				sendBroadcast(intent);
			}
		});
	}
}
