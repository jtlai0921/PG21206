package com.uppowerstudio.chapter4.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
/**
 * splash範例
 * @author UPPower Studio
 * 
 */
public class SplashActivity extends Activity {
	@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
       setContentView(R.layout.splash); 
		ImageView spalsh=(ImageView)findViewById(R.id.splash);
		
		// 判斷螢幕方向，根據顯示方向的不同，顯示不同的LOGO
		Configuration newConfig=getResources().getConfiguration();
		if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
			spalsh.setImageDrawable(getResources().getDrawable(R.drawable.uppower_splash_landscape));
		}else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
			spalsh.setImageDrawable(getResources().getDrawable(R.drawable.uppower_splash_portrait));
		}
		
		// 持續3秒之後跳轉到MainActivity介面
		new Handler().postDelayed(new Runnable(){
			public void run() {
	             Intent mainIntent=new Intent(SplashActivity.this, MainActivity.class);
	             startActivity(mainIntent);
	             SplashActivity.this.finish();
			}
		}, 3000);
    }
}
