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
 * 地圖標注範例主介面程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends MapActivity {

	// 宣告介面控制項
	private MapView mapView;

	// 宣告地圖控制器
	private MapController mapController;

	// 標記點圖示
	private Drawable pin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化介面控制項
		mapView=(MapView) findViewById(R.id.mapview);
		// 設置啟用地圖內置縮放控制項
		mapView.setBuiltInZoomControls(true);

		// 獲取地圖控制器
		mapController=mapView.getController();

		// 獲取當前已經有的Overlay層
		List<Overlay> overlays=mapView.getOverlays();

		// 獲取用於標注用的圖示
		pin=getResources().getDrawable(R.drawable.red_pin);
		// 設置圖示的繪製邊界
		pin.setBounds(0, 0, pin.getMinimumWidth(), pin.getMinimumHeight());

		// 新建標注點
		MyItemOverlay myOverlay=new MyItemOverlay(pin);

		// 將標注點添加到Overlay層中
		overlays.add(myOverlay);

		// 獲取當前的中心位置
		GeoPoint pt=myOverlay.getCenter();
		// 將標注點設置到螢幕中心位置
		mapController.setCenter(pt);

		// 設置預設的縮放等級為12級
		mapController.setZoom(12);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/**
	 * 地圖標注點
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private class MyItemOverlay extends ItemizedOverlay<OverlayItem> {
		private static final int LAT=(int) (39.90960456049752 * 1E6);
		private static final int LNG=(int) (116.3972282409668 * 1E6);
		// 用於儲存標注點
		private List<OverlayItem>items=new ArrayList<OverlayItem>();

		public MyItemOverlay(Drawable defaultMarker) {
			super(defaultMarker);

			// 新建用於建構標注點的經緯度數據
			GeoPoint point=new GeoPoint(LAT, LNG);

			// 新建標注點並添加到Overlay層中
			items.add(new OverlayItem(point, "Point A", "Snippet Point A"));

			// 新建此層中包含的每一個標注點
			populate();
		}

		/**
		 * 新建Overlay
		 */
		@Override
		protected OverlayItem createItem(int i) {
			return items.get(i);
		}

		/**
		 * 返回Overlay的數量
		 */
		@Override
		public int size() {
			return items.size();
		}
	}
}
