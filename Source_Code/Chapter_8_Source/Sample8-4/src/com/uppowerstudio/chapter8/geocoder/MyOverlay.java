package com.uppowerstudio.chapter8.geocoder;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/**
 * 繪製標注點程式碼
 * @author UPPower Studio
 *
 */
public class MyOverlay extends Overlay{

	double longitude=0.0;
	double latitude=0.0;

	public MyOverlay(double lng, double lat) {
		this.longitude=lng;
		this.latitude=lat;
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		
		// 新建畫筆
		Paint accuracyPaint=new Paint();

		// 宣告座標點對象
		Point center;
		Point left;
		
		// 宣告圖示資料物件
		Drawable drawable;
		
		// 定義圖示的寬度、高度
		int width=0;
		int height=0;

		// 設置畫筆
		accuracyPaint.setAntiAlias(true);
		accuracyPaint.setStrokeWidth(2.0f);
		
		// 讀取標注的圖示檔
		drawable=mapView.getContext().getResources()
				.getDrawable(R.drawable.icon_point);
		
		// 獲取圖示的寬度、高度
		width=drawable.getIntrinsicWidth();
		height=drawable.getIntrinsicHeight();
		
		// 新建座標點對象
		center=new Point();
		left=new Point();

		// 獲取投影對象
		Projection projection=mapView.getProjection();

		float[] result=new float[1];
		
		// 計算距離
		Location.distanceBetween(latitude, longitude, latitude, longitude + 1,
				result);
		float longitudeLineDistance=result[0];

		// 新建經緯度資訊物件
		GeoPoint leftGeo=new GeoPoint((int) (latitude*1e6),
				(int) ((longitude/longitudeLineDistance)*1e6));
		GeoPoint geoPoint=new GeoPoint((int) (latitude*1E6),
				(int) (longitude*1E6));
		projection.toPixels(geoPoint, center);
		
		// 進行座標轉換
		projection.toPixels(leftGeo, left);		

		// 繪製標注點
		drawable.setBounds(center.x-width/2, center.y-height/2,
				center.x+width/2, center.y+height/2);
		drawable.draw(canvas);

		return true;
	}
}
