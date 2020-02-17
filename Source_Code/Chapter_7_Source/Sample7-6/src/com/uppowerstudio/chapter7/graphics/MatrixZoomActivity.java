package com.uppowerstudio.chapter7.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

/**
 * 圖像旋轉與縮放介面程式碼
 * @author UPPower Studio
 *
 */
public class MatrixZoomActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 載入自訂視圖
		setContentView(new MatrixZoomView(MatrixZoomActivity.this));
	}

	/**
	 * 圖像旋轉與縮放自訂視圖
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private static class MatrixZoomView extends View {

		// 宣告畫筆
		private Paint bitmapPaint;

		// 宣告原始圖像變數
		private Bitmap bitmap;

		// 宣告旋轉圖像變數
		private Bitmap matrixBitmap;

		// 宣告放大圖像變數
		private Bitmap zoomBitmap;

		// 定義旋轉的角度
		private float degree=45.0F;

		// 定義縮放的比例值
		private float scale=2.0F;

		// 宣告Matrix變數
		private Matrix matrix;

		// 定義圖像的寬、高
		private int width=0;
		private int height=0;

		public MatrixZoomView(Context context) {
			super(context);

			// 建構畫筆
			bitmapPaint=new Paint();

			// 新增Matrix對象
			matrix=new Matrix();

			// 從Drawable目錄獲取圖片
			bitmap=((BitmapDrawable) getResources().getDrawable(
					R.drawable.google)).getBitmap();

			// 獲取圖像的寬度和高度
			width=bitmap.getWidth();
			height=bitmap.getHeight();

		}

		@Override
		protected void onDraw(Canvas canvas) {
			// 設置背景色
			canvas.drawColor(Color.WHITE);

			// 重置Matrix對象
			matrix.reset();
			// 設置旋轉的角度
			matrix.setRotate(degree);
			// 新增旋轉後的圖像
			matrixBitmap=Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, true);
			// 繪製旋轉後的圖像
			canvas.drawBitmap(matrixBitmap, 10, 20, bitmapPaint);

			// 重置Matrix對象
			matrix.reset();
			// 設置縮放值
			matrix.postScale(scale, scale);
			// 新增縮放後的圖像
			zoomBitmap=Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, true);
			// 繪製縮放後的圖像
			canvas.drawBitmap(zoomBitmap, 10, 200, bitmapPaint);
		}
	}
}
