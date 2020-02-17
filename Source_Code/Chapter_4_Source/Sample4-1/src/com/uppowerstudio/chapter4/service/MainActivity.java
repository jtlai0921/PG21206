package com.uppowerstudio.chapter4.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 服務範例
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button startBtn;
	
	
    @Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        Log.v("MainActivity", "into");
        setContentView(R.layout.main);
        startBtn=(Button)findViewById(R.id.start);
        startBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//按一下按鈕時，啟動服務
				Intent intent=new Intent(MainActivity.this, CountService.class);
				startService(intent);
			}
        });
    }

    @Override
protected void onDestroy() {
super.onDestroy();
        Intent intent=new Intent(MainActivity.this, CountService.class);
        //退出Activity時，停止服務
        stopService(intent);
        Log.v("MainActivity", "out");
    }
}
