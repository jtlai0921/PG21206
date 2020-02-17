package com.uppowerstudio.chapter8.mapview;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 地圖瀏覽器介面程式碼
 * @author UPPower Studio
 *
 */
public class MainActivity extends MapActivity {

	// 宣告介面控制項
	private MapView mapView;
	private Button buttonNormal;
	private Button buttonSatellite;
	private Button buttonTraffic;

	// 宣告地圖控制器
	private MapController mapController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		mapView=(MapView) findViewById(R.id.mapview);
		buttonNormal=(Button) findViewById(R.id.button_normal);
		buttonSatellite=(Button) findViewById(R.id.button_satellite);
		buttonTraffic=(Button) findViewById(R.id.button_traffic);
		
		// 設置啟用地圖內置縮放控制項
		mapView.setBuiltInZoomControls(true);

		// 獲取地圖控制器
		mapController=mapView.getController();
		// 設置預設的縮放等級為10級
		mapController.setZoom(10);

		// 註冊普通視圖點擊事件監聽器
		buttonNormal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 清除所設置的其他視圖模式，還原為普通視圖
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

		// 註冊衛星視圖點擊事件監聽器
		buttonSatellite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 設置為衛星視圖模式
				mapView.setSatellite(true);
			}
		});

		// 註冊交通視圖點擊事件監聽器
		buttonTraffic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 如果當前為衛星模式，則關閉
				if (mapView.isSatellite()) {
					mapView.setSatellite(false);
				}
				
				// 設置為交通視圖模式
				mapView.setTraffic(true);
			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
