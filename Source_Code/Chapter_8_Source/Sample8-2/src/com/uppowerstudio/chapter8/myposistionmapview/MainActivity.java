package com.uppowerstudio.chapter8.myposistionmapview;

import java.util.List;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.uppowerstudio.chapter8.myposistionmapview.R;

import android.os.Bundle;

/**
 * ��ܷ�e��m���a���s���������{���X
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends MapActivity {

	// �ŧi�������
	private MapView mapView;

	// �ŧi��e��m�ܼ�
	private MyLocationOverlay myLocationOverlay;

	// �ŧi�a�ϱ��
	private MapController mapController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		mapView=(MapView) findViewById(R.id.mapview);

		// �]�m�ҥΦa�Ϥ��m�Y�񱱨
		mapView.setBuiltInZoomControls(true);

		// �غc��e��m
		myLocationOverlay=new MyLocationOverlay(MainActivity.this, mapView);
		// ��ܫ��n�w
		myLocationOverlay.enableCompass();
		// ��ܧڪ���m
		myLocationOverlay.enableMyLocation();
		
		// ����a�ϱ��
		mapController=mapView.getController();

		// �]�m�w�]���Y�񵥯Ŭ�12��
		mapController.setZoom(12);

		// �����e�w�g����Overlay�h
		List<Overlay> overlays=mapView.getOverlays();

		// ��������e��m��Ʈɰ���
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				// ���ʨ��e��m
				mapController.animateTo(myLocationOverlay.getMyLocation());
			}
		});
		
		// �bMapView�W��ܷ�e��m
		overlays.add(myLocationOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
