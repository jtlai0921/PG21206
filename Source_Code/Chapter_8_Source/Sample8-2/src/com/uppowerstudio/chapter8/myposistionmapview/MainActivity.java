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
 * 顯示當前位置的地圖瀏覽器介面程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends MapActivity {

	// 宣告介面控制項
	private MapView mapView;

	// 宣告當前位置變數
	private MyLocationOverlay myLocationOverlay;

	// 宣告地圖控制器
	private MapController mapController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		mapView=(MapView) findViewById(R.id.mapview);

		// 設置啟用地圖內置縮放控制項
		mapView.setBuiltInZoomControls(true);

		// 建構當前位置
		myLocationOverlay=new MyLocationOverlay(MainActivity.this, mapView);
		// 顯示指南針
		myLocationOverlay.enableCompass();
		// 顯示我的位置
		myLocationOverlay.enableMyLocation();
		
		// 獲取地圖控制器
		mapController=mapView.getController();

		// 設置預設的縮放等級為12級
		mapController.setZoom(12);

		// 獲取當前已經有的Overlay層
		List<Overlay> overlays=mapView.getOverlays();

		// 當獲取到當前位置資料時執行
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				// 移動到當前位置
				mapController.animateTo(myLocationOverlay.getMyLocation());
			}
		});
		
		// 在MapView上顯示當前位置
		overlays.add(myLocationOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
