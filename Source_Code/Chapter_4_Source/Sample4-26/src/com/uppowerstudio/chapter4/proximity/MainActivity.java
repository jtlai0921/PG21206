package com.uppowerstudio.chapter4.proximity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * 趨近警告範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	// 定義儲存經緯度數據變數
private double latitude;
	private double longitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// 產生實體Geocoder對象
		Geocoder gc=new Geocoder(MainActivity.this, Locale.getDefault());
		// 獲取LocationManager對象
		LocationManager manager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 讀取指定的查詢地址
		String address=getString(R.string.address);

		try {
			// 通過給定地址查詢其經緯度數據
			List<Address> list=gc.getFromLocationName(address, 10);
			if (list.size() > 0) {
				Address location=list.get(0);
				// 獲取經緯度
				latitude=location.getLatitude();
				longitude=location.getLongitude();
				// 設置趨近警告範圍半徑
				float radius=500;
				// 定義用於完成接收趨近警告的Receiver
				Intent intent=new Intent("com.uppowerstudio.proximity_action");
				PendingIntent pengding=PendingIntent.getBroadcast(
						MainActivity.this, -1, intent, 0);
				// 添加趨近警告
				manager.addProximityAlert(latitude, longitude, radius, -1,
						pengding);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
