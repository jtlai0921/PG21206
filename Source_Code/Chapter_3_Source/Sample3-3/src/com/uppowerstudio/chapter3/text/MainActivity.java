package com.uppowerstudio.chapter3.text;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 文字框和編輯框
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 預設呼叫父類別onCreate方法，並傳遞savedInstanceState參數，
		// 以使在頁面初始化時，保持之前狀態
		super.onCreate(savedInstanceState);
		// 指定main.xml佈局文件為當前Activity的佈局文件
		setContentView(R.layout.main);
		// 在當前佈局中，通過查尋id為test的組件並做類型轉換
		TextView tv = (TextView) findViewById(R.id.text);
		// 因為文字跑馬燈效果需要在組件獲得焦點後才能有效
		// 所以這裡讓TextView獲取焦點
		tv.setSelected(true);
	}
}