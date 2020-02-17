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
 * �ͪ�ĵ�i�d��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	// �w�q�x�s�g�n�׼ƾ��ܼ�
private double latitude;
	private double longitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// ���͹���Geocoder��H
		Geocoder gc=new Geocoder(MainActivity.this, Locale.getDefault());
		// ���LocationManager��H
		LocationManager manager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Ū�����w���d�ߦa�}
		String address=getString(R.string.address);

		try {
			// �q�L���w�a�}�d�ߨ�g�n�׼ƾ�
			List<Address> list=gc.getFromLocationName(address, 10);
			if (list.size() > 0) {
				Address location=list.get(0);
				// ����g�n��
				latitude=location.getLatitude();
				longitude=location.getLongitude();
				// �]�m�ͪ�ĵ�i�d��b�|
				float radius=500;
				// �w�q�Ω󧹦������ͪ�ĵ�i��Receiver
				Intent intent=new Intent("com.uppowerstudio.proximity_action");
				PendingIntent pengding=PendingIntent.getBroadcast(
						MainActivity.this, -1, intent, 0);
				// �K�[�ͪ�ĵ�i
				manager.addProximityAlert(latitude, longitude, radius, -1,
						pengding);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
