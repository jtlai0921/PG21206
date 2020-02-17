package com.uppowerstudio.chapter3.tabs;

import android.app.TabActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

/**
 * 選項卡範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res=getResources();
		// 獲取TabHost元件
		TabHost host=getTabHost();
		TabSpec spec;

		// 新增一個選項卡並設置內容
		spec=host.newTabSpec(res.getString(R.string.tab_01))
				.setIndicator(res.getString(R.string.tab_01),
				// android.R表示引用的Android系統內建的資源
						res.getDrawable(android.R.drawable.star_big_on))
				.setContent(R.id.txt1);
		// 將選項卡加入到TabHost這個容器內
		host.addTab(spec);

		spec=host
				.newTabSpec(res.getString(R.string.tab_02))
				.setIndicator(res.getString(R.string.tab_02),
						res.getDrawable(android.R.drawable.star_big_on))
				.setContent(R.id.txt2);
		host.addTab(spec);

		spec=host
				.newTabSpec(res.getString(R.string.tab_03))
				.setIndicator(res.getString(R.string.tab_03),
						res.getDrawable(android.R.drawable.star_big_on))
				.setContent(R.id.txt3);
		host.addTab(spec);

		// 設置當前活動選項卡
		host.setCurrentTab(1);

		// 註冊選項卡改變監聽器
		host.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// 顯示當前選項卡標識
				Toast.makeText(MainActivity.this, tabId, 2).show();
			}
		});
	}
}
