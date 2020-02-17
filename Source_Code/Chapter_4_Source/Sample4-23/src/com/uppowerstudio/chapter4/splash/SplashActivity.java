package com.uppowerstudio.chapter4.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
/**
 * splash�d��
 * @author UPPower Studio
 * 
 */
public class SplashActivity extends Activity {
	@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
       setContentView(R.layout.splash); 
		ImageView spalsh=(ImageView)findViewById(R.id.splash);
		
		// �P�_�ù���V�A�ھ���ܤ�V�����P�A��ܤ��P��LOGO
		Configuration newConfig=getResources().getConfiguration();
		if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
			spalsh.setImageDrawable(getResources().getDrawable(R.drawable.uppower_splash_landscape));
		}else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
			spalsh.setImageDrawable(getResources().getDrawable(R.drawable.uppower_splash_portrait));
		}
		
		// ����3��������MainActivity����
		new Handler().postDelayed(new Runnable(){
			public void run() {
	             Intent mainIntent=new Intent(SplashActivity.this, MainActivity.class);
	             startActivity(mainIntent);
	             SplashActivity.this.finish();
			}
		}, 3000);
    }
}
