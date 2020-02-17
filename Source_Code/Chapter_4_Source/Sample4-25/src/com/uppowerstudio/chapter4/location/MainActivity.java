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
 * ��m�d��
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

		// ���LocationManager��H
		LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// �p�G�S���}��GPS�h�����m��T�A�_�h����]�m����
		if (locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			// ����q�L�����w�쪺Provider����
			locationProvider=locationManager
					.getProvider(LocationManager.GPS_PROVIDER);
			// �����m����
			location=locationManager.getLastKnownLocation(locationProvider
					.getName());
			if (location != null) {
				txt.setText("latitude: "+location.getLatitude()+" \n "
						+ "longtude:"+location.getLongitude());
			}

			// ���U��ť���A���j2��ɶ��Ϊ�10�̶Z����s�@����m��T
			locationManager.requestLocationUpdates(locationProvider.getName(),
					2000, 10, listener);
		} else {
			Toast.makeText(this, "please turen on GPS", Toast.LENGTH_SHORT)
					.show();
			Intent intent=new Intent(Settings.ACTION_SECURITY_SETTINGS);
			// �����]�m�������^���������
			startActivityForResult(intent, 0);
		}
	}

	// �s�W�@�Ӻ�ť���A���m�o�ͧ��ܮɡA���s��ܦ�m��T
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
