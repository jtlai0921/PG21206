package com.uppowerstudio.chapter7.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

/**
 * 繪製圖像介面程式碼
 * @author UPPower Studio
 *
 */
public class BitmapActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		// 載入自訂視圖
		setContentView(new BitmapView(BitmapActivity.this));

	}

	/**
	 * 繪製圖像自訂視圖
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private static class BitmapView extends View {
		
		// 宣告畫筆
		private Paint bitmapPaint;
		
		// 宣告圖像物件變數
		private Bitmap bitmap;

		public BitmapView(Context context) {
			super(context);
			
			// 建構畫筆
			bitmapPaint=new Paint();

			// 從Drawable目錄獲取圖片
			bitmap=((BitmapDrawable) getResources().getDrawable(
					R.drawable.google)).getBitmap();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// 設置背景色
			canvas.drawColor(Color.WHITE);
			
			// 繪製圖片
			canvas.drawBitmap(bitmap, 10, 20, bitmapPaint);
		}
	}
}
