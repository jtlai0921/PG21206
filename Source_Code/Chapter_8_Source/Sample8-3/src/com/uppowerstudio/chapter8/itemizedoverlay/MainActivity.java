package com.uppowerstudio.chapter8.itemizedoverlay;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

/**
 * �a�ϼЪ`�d�ҥD�����{���X
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends MapActivity {

	// �ŧi�������
	private MapView mapView;

	// �ŧi�a�ϱ��
	private MapController mapController;

	// �аO�I�ϥ�
	private Drawable pin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		mapView=(MapView) findViewById(R.id.mapview);
		// �]�m�ҥΦa�Ϥ��m�Y�񱱨
		mapView.setBuiltInZoomControls(true);

		// ����a�ϱ��
		mapController=mapView.getController();

		// �����e�w�g����Overlay�h
		List<Overlay> overlays=mapView.getOverlays();

		// ����Ω�Ъ`�Ϊ��ϥ�
		pin=getResources().getDrawable(R.drawable.red_pin);
		// �]�m�ϥܪ�ø�s���
		pin.setBounds(0, 0, pin.getMinimumWidth(), pin.getMinimumHeight());

		// �s�ؼЪ`�I
		MyItemOverlay myOverlay=new MyItemOverlay(pin);

		// �N�Ъ`�I�K�[��Overlay�h��
		overlays.add(myOverlay);

		// �����e�����ߦ�m
		GeoPoint pt=myOverlay.getCenter();
		// �N�Ъ`�I�]�m��ù����ߦ�m
		mapController.setCenter(pt);

		// �]�m�w�]���Y�񵥯Ŭ�12��
		mapController.setZoom(12);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/**
	 * �a�ϼЪ`�I
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class MyItemOverlay extends ItemizedOverlay<OverlayItem> {
		private static final int LAT=(int) (39.90960456049752 * 1E6);
		private static final int LNG=(int) (116.3972282409668 * 1E6);
		// �Ω��x�s�Ъ`�I
		private List<OverlayItem>items=new ArrayList<OverlayItem>();

		public MyItemOverlay(Drawable defaultMarker) {
			super(defaultMarker);

			// �s�إΩ�غc�Ъ`�I���g�n�׼ƾ�
			GeoPoint point=new GeoPoint(LAT, LNG);

			// �s�ؼЪ`�I�òK�[��Overlay�h��
			items.add(new OverlayItem(point, "Point A", "Snippet Point A"));

			// �s�ئ��h���]�t���C�@�ӼЪ`�I
			populate();
		}

		/**
		 * �s��Overlay
		 */
		@Override
		protected OverlayItem createItem(int i) {
			return items.get(i);
		}

		/**
		 * ��^Overlay���ƶq
		 */
		@Override
		public int size() {
			return items.size();
		}
	}
}
