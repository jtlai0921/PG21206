package com.uppowerstudio.chapter4.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * �A�Ƚd��
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
				//���@�U���s�ɡA�ҰʪA��
				Intent intent=new Intent(MainActivity.this, CountService.class);
				startService(intent);
			}
        });
    }

    @Override
protected void onDestroy() {
super.onDestroy();
        Intent intent=new Intent(MainActivity.this, CountService.class);
        //�h�XActivity�ɡA����A��
        stopService(intent);
        Log.v("MainActivity", "out");
    }
}
