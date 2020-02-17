package com.uppowerstudio.chapter8.mapview;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * �a���s���������{���X
 * @author UPPower Studio
 *
 */
public class MainActivity extends MapActivity {

	// �ŧi�������
	private MapView mapView;
	private Button buttonNormal;
	private Button buttonSatellite;
	private Button buttonTraffic;

	// �ŧi�a�ϱ��
	private MapController mapController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		mapView=(MapView) findViewById(R.id.mapview);
		buttonNormal=(Button) findViewById(R.id.button_normal);
		buttonSatellite=(Button) findViewById(R.id.button_satellite);
		buttonTraffic=(Button) findViewById(R.id.button_traffic);
		
		// �]�m�ҥΦa�Ϥ��m�Y�񱱨
		mapView.setBuiltInZoomControls(true);

		// ����a�ϱ��
		mapController=mapView.getController();
		// �]�m�w�]���Y�񵥯Ŭ�10��
		mapController.setZoom(10);

		// ���U���q�����I���ƥ��ť��
		buttonNormal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �M���ҳ]�m����L���ϼҦ��A�٭쬰���q����
				if (mapView.isSatellite()) {
					mapView.setSatellite(false);
				}

				if (mapView.isTraffic()) {
					mapView.setTraffic(false);
				}

				if (mapView.isStreetView()) {
					mapView.setStreetView(false);
				}
			}
		});

		// ���U�ìP�����I���ƥ��ť��
		buttonSatellite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �]�m���ìP���ϼҦ�
				mapView.setSatellite(true);
			}
		});

		// ���U��q�����I���ƥ��ť��
		buttonTraffic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �p�G��e���ìP�Ҧ��A�h����
				if (mapView.isSatellite()) {
					mapView.setSatellite(false);
				}
				
				// �]�m����q���ϼҦ�
				mapView.setTraffic(true);
			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
