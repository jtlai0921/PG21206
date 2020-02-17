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
 * ø�s�Ъ`�I�{���X
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
		
		// �s�صe��
		Paint accuracyPaint=new Paint();

		// �ŧi�y���I��H
		Point center;
		Point left;
		
		// �ŧi�ϥܸ�ƪ���
		Drawable drawable;
		
		// �w�q�ϥܪ��e�סB����
		int width=0;
		int height=0;

		// �]�m�e��
		accuracyPaint.setAntiAlias(true);
		accuracyPaint.setStrokeWidth(2.0f);
		
		// Ū���Ъ`���ϥ���
		drawable=mapView.getContext().getResources()
				.getDrawable(R.drawable.icon_point);
		
		// ����ϥܪ��e�סB����
		width=drawable.getIntrinsicWidth();
		height=drawable.getIntrinsicHeight();
		
		// �s�خy���I��H
		center=new Point();
		left=new Point();

		// �����v��H
		Projection projection=mapView.getProjection();

		float[] result=new float[1];
		
		// �p��Z��
		Location.distanceBetween(latitude, longitude, latitude, longitude + 1,
				result);
		float longitudeLineDistance=result[0];

		// �s�ظg�n�׸�T����
		GeoPoint leftGeo=new GeoPoint((int) (latitude*1e6),
				(int) ((longitude/longitudeLineDistance)*1e6));
		GeoPoint geoPoint=new GeoPoint((int) (latitude*1E6),
				(int) (longitude*1E6));
		projection.toPixels(geoPoint, center);
		
		// �i��y���ഫ
		projection.toPixels(leftGeo, left);		

		// ø�s�Ъ`�I
		drawable.setBounds(center.x-width/2, center.y-height/2,
				center.x+width/2, center.y+height/2);
		drawable.draw(canvas);

		return true;
	}
}
