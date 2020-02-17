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
 * 地圖正、反向查詢範例主介面程式碼
 * @author UPPower Studio
 *
 */
public class MainActivity extends MapActivity {

	// 定義最大返回結果數
	private static final int MAX_RESULT=1;

	// 宣告介面控制項
	private MapView mapView;
	private Button buttonForward;
	private Button buttonReverse;
	private TextView displayMessage;

	// 獲取地圖控制器
	private MapController mapController;

	// 宣告當前位置變數
	private MyLocationOverlay myLocationOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		mapView=(MapView) findViewById(R.id.mapview);
		displayMessage=(TextView) findViewById(R.id.msg_display);
		buttonForward=(Button) findViewById(R.id.button_forward);
		buttonReverse=(Button) findViewById(R.id.button_reverse);

		// 建構當前位置
		myLocationOverlay=new MyLocationOverlay(MainActivity.this, mapView);
		// 顯示指南針
		myLocationOverlay.enableCompass();
		// 顯示我的位置
		myLocationOverlay.enableMyLocation();

		// 當獲取到當前位置資料時執行
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				// 移動到當前位置
				mapController.animateTo(myLocationOverlay.getMyLocation());
			}
		});

		// 在MapView上顯示當前位置
		mapView.getOverlays().add(myLocationOverlay);

		// 設置啟用地圖內置縮放控制項
		mapView.setBuiltInZoomControls(true);

		mapController=mapView.getController();
		// 設置預設的縮放等級為12級
		mapController.setZoom(12);

		// 設置結果顯示區域背景色及文字顏色
		displayMessage.setBackgroundColor(Color.WHITE);
		displayMessage.setTextColor(Color.BLACK);

		// 註冊按鈕事件監聽器
		registerButtonHandler();
	}

	/**
	 * 註冊按鈕事件監聽器
	 */
	private void registerButtonHandler() {
		buttonForward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 打開位址輸入介面
				Intent intent=new Intent("action_forward");
				startActivityForResult(intent, 1);
			}
		});

		buttonReverse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 打開經緯度輸入介面
				Intent intent=new Intent("action_reverse");
				startActivityForResult(intent, 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String returnType=data.getStringExtra("return_type");
			// 判斷如果是通過位置查詢經緯度操作
			if ("forward".equals(returnType)) {
				String inputLocation=(String) data
						.getSerializableExtra("input_location");
				
				// 獲取查詢結果
				String[] result=getLongitudeLatitude(inputLocation);

				// 繪製標注點
				drawPointOnMapView(Double.parseDouble(result[1]),
						Double.parseDouble(result[0]));
				
				// 建構返回結果顯示資訊字串
				StringBuffer sb=new StringBuffer();
				sb.append(getString(R.string.prefix_location))
						.append(inputLocation).append("\n")
						.append(getString(R.string.prefix_longitude))
						.append(result[0]).append("\n")
						.append(getString(R.string.prefix_latitude))
						.append(result[1]);
				
				displayMessage.setVisibility(View.VISIBLE);
				displayMessage.setText(sb.toString());

				// 判斷如果是通過經緯度查詢位置操作
			} else if ("reverse".equals(returnType)) {
				// 獲取回傳的經緯度
				String longitude=(String) data
						.getSerializableExtra("longitude");
				String latitude=(String) data
						.getSerializableExtra("latitude");

				// 繪製標注點
				drawPointOnMapView(Double.parseDouble(latitude),
						Double.parseDouble(longitude));

				// 建構返回結果顯示資訊字串
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
	 * 在地圖上進行標注操作
	 * 
	 * @param latitudeValue
	 * @param longitudeValue
	 */
	private synchronized void drawPointOnMapView(double latitudeValue,
			double longitudeValue) {
		// 新建標注點
		MyOverlay myOverlay=new MyOverlay(longitudeValue, latitudeValue);
		// 添加到當前層中
		mapView.getOverlays().add(myOverlay);

		// 建構標注點經緯度物件
		GeoPoint pt=new GeoPoint((int) (latitudeValue * 1E6),
				(int) (longitudeValue * 1E6));
		// 將標注點設置到螢幕中心位置
		mapController.setCenter(pt);
	}

	/**
	 * 通過經緯度查詢具體位置
	 * 
	 * @param latitudeValue
	 *            緯度
	 * @param longitudeValue
	 *            經度
	 * @return
	 */
	private String getLocation(double latitudeValue, double longitudeValue) {
		StringBuffer sb=new StringBuffer();

		try {
			List<Address> addresses=null;

			// 建構用於尋找的Geocoder物件，指定尋找區域為當前設置所設置區域
			Geocoder g=new Geocoder(MainActivity.this, Locale.getDefault());

			// 尋找具體位置資訊
			addresses=g.getFromLocation(latitudeValue, longitudeValue,
					MAX_RESULT);

			if (!addresses.isEmpty()) {
				// 獲取Address對象
				Address currentAddress=addresses.get(0);
				// 從Address物件中獲取具體的位置資料
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
	 * 通過具體位置查詢經緯度
	 * 
	 * @param location
	 *            具體位置
	 * @return
	 */
	private String[] getLongitudeLatitude(String location) {
		String[] returnData=new String[2];

		try {
			List<Address> addresses=null;

			// 建構用於尋找的Geocoder物件，指定尋找區域為當前設置所設置區域
			Geocoder g=new Geocoder(MainActivity.this, Locale.getDefault());

			// 尋找具體位置資訊
			addresses=g.getFromLocationName(location, MAX_RESULT);

			if (!addresses.isEmpty()) {
				// 獲取Address對象
				Address currentAddress=addresses.get(0);
				
				// 設置返回數據
				returnData[0]=String.valueOf(currentAddress.getLongitude());
				returnData[1]=String.valueOf(currentAddress.getLatitude());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnData;
	}
}
