package com.uppowerstudio.chapter8.geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * �a�ϥ��B�ϦV�d�߽d�ҥD�����{���X
 * @author UPPower Studio
 *
 */
public class MainActivity extends MapActivity {

	// �w�q�̤j��^���G��
	private static final int MAX_RESULT=1;

	// �ŧi�������
	private MapView mapView;
	private Button buttonForward;
	private Button buttonReverse;
	private TextView displayMessage;

	// ����a�ϱ��
	private MapController mapController;

	// �ŧi��e��m�ܼ�
	private MyLocationOverlay myLocationOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		mapView=(MapView) findViewById(R.id.mapview);
		displayMessage=(TextView) findViewById(R.id.msg_display);
		buttonForward=(Button) findViewById(R.id.button_forward);
		buttonReverse=(Button) findViewById(R.id.button_reverse);

		// �غc��e��m
		myLocationOverlay=new MyLocationOverlay(MainActivity.this, mapView);
		// ��ܫ��n�w
		myLocationOverlay.enableCompass();
		// ��ܧڪ���m
		myLocationOverlay.enableMyLocation();

		// ��������e��m��Ʈɰ���
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				// ���ʨ��e��m
				mapController.animateTo(myLocationOverlay.getMyLocation());
			}
		});

		// �bMapView�W��ܷ�e��m
		mapView.getOverlays().add(myLocationOverlay);

		// �]�m�ҥΦa�Ϥ��m�Y�񱱨
		mapView.setBuiltInZoomControls(true);

		mapController=mapView.getController();
		// �]�m�w�]���Y�񵥯Ŭ�12��
		mapController.setZoom(12);

		// �]�m���G��ܰϰ�I����Τ�r�C��
		displayMessage.setBackgroundColor(Color.WHITE);
		displayMessage.setTextColor(Color.BLACK);

		// ���U���s�ƥ��ť��
		registerButtonHandler();
	}

	/**
	 * ���U���s�ƥ��ť��
	 */
	private void registerButtonHandler() {
		buttonForward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���}��}��J����
				Intent intent=new Intent("action_forward");
				startActivityForResult(intent, 1);
			}
		});

		buttonReverse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���}�g�n�׿�J����
				Intent intent=new Intent("action_reverse");
				startActivityForResult(intent, 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String returnType=data.getStringExtra("return_type");
			// �P�_�p�G�O�q�L��m�d�߸g�n�׾ާ@
			if ("forward".equals(returnType)) {
				String inputLocation=(String) data
						.getSerializableExtra("input_location");
				
				// ����d�ߵ��G
				String[] result=getLongitudeLatitude(inputLocation);

				// ø�s�Ъ`�I
				drawPointOnMapView(Double.parseDouble(result[1]),
						Double.parseDouble(result[0]));
				
				// �غc��^���G��ܸ�T�r��
				StringBuffer sb=new StringBuffer();
				sb.append(getString(R.string.prefix_location))
						.append(inputLocation).append("\n")
						.append(getString(R.string.prefix_longitude))
						.append(result[0]).append("\n")
						.append(getString(R.string.prefix_latitude))
						.append(result[1]);
				
				displayMessage.setVisibility(View.VISIBLE);
				displayMessage.setText(sb.toString());

				// �P�_�p�G�O�q�L�g�n�׬d�ߦ�m�ާ@
			} else if ("reverse".equals(returnType)) {
				// ����^�Ǫ��g�n��
				String longitude=(String) data
						.getSerializableExtra("longitude");
				String latitude=(String) data
						.getSerializableExtra("latitude");

				// ø�s�Ъ`�I
				drawPointOnMapView(Double.parseDouble(latitude),
						Double.parseDouble(longitude));

				// �غc��^���G��ܸ�T�r��
				StringBuffer sb=new StringBuffer();
				sb.append(getString(R.string.prefix_longitude))
						.append(longitude)
						.append("\n")
						.append(getString(R.string.prefix_latitude))
						.append(latitude)
						.append("\n")
						.append(getString(R.string.prefix_quired_location))
						.append(getLocation(Double.parseDouble(latitude),
								Double.parseDouble(longitude)));
				
				displayMessage.setVisibility(View.VISIBLE);
				displayMessage.setText(sb.toString());
			}
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * �b�a�ϤW�i��Ъ`�ާ@
	 * 
	 * @param latitudeValue
	 * @param longitudeValue
	 */
	private synchronized void drawPointOnMapView(double latitudeValue,
			double longitudeValue) {
		// �s�ؼЪ`�I
		MyOverlay myOverlay=new MyOverlay(longitudeValue, latitudeValue);
		// �K�[���e�h��
		mapView.getOverlays().add(myOverlay);

		// �غc�Ъ`�I�g�n�ת���
		GeoPoint pt=new GeoPoint((int) (latitudeValue * 1E6),
				(int) (longitudeValue * 1E6));
		// �N�Ъ`�I�]�m��ù����ߦ�m
		mapController.setCenter(pt);
	}

	/**
	 * �q�L�g�n�׬d�ߨ����m
	 * 
	 * @param latitudeValue
	 *            �n��
	 * @param longitudeValue
	 *            �g��
	 * @return
	 */
	private String getLocation(double latitudeValue, double longitudeValue) {
		StringBuffer sb=new StringBuffer();

		try {
			List<Address> addresses=null;

			// �غc�Ω�M�䪺Geocoder����A���w�M��ϰ쬰��e�]�m�ҳ]�m�ϰ�
			Geocoder g=new Geocoder(MainActivity.this, Locale.getDefault());

			// �M������m��T
			addresses=g.getFromLocation(latitudeValue, longitudeValue,
					MAX_RESULT);

			if (!addresses.isEmpty()) {
				// ���Address��H
				Address currentAddress=addresses.get(0);
				// �qAddress����������骺��m���
				if (currentAddress.getMaxAddressLineIndex() > 0) {
					for (int i=0; i<=currentAddress
							.getMaxAddressLineIndex(); i++) {
						sb.append(currentAddress.getAddressLine(i));
					}
				} else {
					if (currentAddress.getPostalCode() != null) {
						sb.append(currentAddress.getPostalCode());
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * �q�L�����m�d�߸g�n��
	 * 
	 * @param location
	 *            �����m
	 * @return
	 */
	private String[] getLongitudeLatitude(String location) {
		String[] returnData=new String[2];

		try {
			List<Address> addresses=null;

			// �غc�Ω�M�䪺Geocoder����A���w�M��ϰ쬰��e�]�m�ҳ]�m�ϰ�
			Geocoder g=new Geocoder(MainActivity.this, Locale.getDefault());

			// �M������m��T
			addresses=g.getFromLocationName(location, MAX_RESULT);

			if (!addresses.isEmpty()) {
				// ���Address��H
				Address currentAddress=addresses.get(0);
				
				// �]�m��^�ƾ�
				returnData[0]=String.valueOf(currentAddress.getLongitude());
				returnData[1]=String.valueOf(currentAddress.getLatitude());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnData;
	}
}
