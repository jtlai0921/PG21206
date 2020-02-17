package com.uppowerstudio.chapter4.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 位置範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView txt;
	private LocationProvider locationProvider;
	private Location location;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		txt=(TextView) findViewById(R.id.txt);

		// 獲取LocationManager對象
		LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 如果沒有開啟GPS則獲取位置資訊，否則跳到設置介面
		if (locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			// 獲取通過網路定位的Provider物件
			locationProvider=locationManager
					.getProvider(LocationManager.GPS_PROVIDER);
			// 獲取位置物件
			location=locationManager.getLastKnownLocation(locationProvider
					.getName());
			if (location != null) {
				txt.setText("latitude: "+location.getLatitude()+" \n "
						+ "longtude:"+location.getLongitude());
			}

			// 註冊監聽器，間隔2秒時間或者10米距離更新一次位置資訊
			locationManager.requestLocationUpdates(locationProvider.getName(),
					2000, 10, listener);
		} else {
			Toast.makeText(this, "please turen on GPS", Toast.LENGTH_SHORT)
					.show();
			Intent intent=new Intent(Settings.ACTION_SECURITY_SETTINGS);
			// 此為設置完成後返回到獲取介面
			startActivityForResult(intent, 0);
		}
	}

	// 新增一個監聽器，當位置發生改變時，重新顯示位置資訊
	private LocationListener listener=new LocationListener() {
		public void onLocationChanged(Location location) {
			txt.setText("latitude: "+location.getLatitude()+" \n "
					+ "longtude:"+location.getLongitude());
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};
}
