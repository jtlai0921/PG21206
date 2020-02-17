package com.uppowerstudio.chapter7.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

/**
 * 繪製文字介面程式碼
 * @author UPPower Studio
 *
 */
public class TextActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 載入自訂視圖
		setContentView(new DrawTextView(TextActivity.this));
	}

	/**
	 * 繪製檔自訂視圖
	 * @author UPPower Studio
	 *
	 */
	private static class DrawTextView extends View {
		private Context context;
		
		// 宣告用於繪製文字的畫筆
		private Paint textPaint;
		
		// 宣告用於繪製文字對齊方式的畫筆
		private Paint alignTextPaint;
		
		// 座標
		private float x;
		private float y;
		

		public DrawTextView(Context context) {
			super(context);
			setFocusable(true);
			this.context=context;
			
			// 設置用於繪製文字的畫筆
			textPaint=new Paint();
			
			// 設置抗鋸齒
			textPaint.setAntiAlias(true);
			// 設置字體為空心字
			textPaint.setStyle(Paint.Style.STROKE);
			// 設置邊寬度
			textPaint.setStrokeWidth(3);
			// 設置顏色
			textPaint.setColor(Color.RED);
			// 設置字體類型
			textPaint.setTypeface(Typeface.DEFAULT_BOLD);
			// 設置字體大小
			textPaint.setTextSize(48);
			// 設置對齊方式
			textPaint.setTextAlign(Paint.Align.CENTER);
			
			// 設置用於繪製文字對齊方式的畫筆
			alignTextPaint=new Paint();
			// 設置抗鋸齒
			alignTextPaint.setAntiAlias(true);
			// 設置字體大小
			alignTextPaint.setTextSize(30);
			// 設置字體類型
			alignTextPaint.setTypeface(Typeface.SERIF);

		}

		@Override
		protected void onDraw(Canvas canvas) {
			// 繪製背景為白色
			canvas.drawColor(Color.WHITE);
			
			// 繪製用於顯示對齊方式的中線
			alignTextPaint.setColor(Color.BLUE);
			canvas.drawLine(x, y, x, y+30*3, alignTextPaint);
			alignTextPaint.setColor(Color.BLACK);
			
			// 繪製左對齊文字
			canvas.translate(0, 30);
			alignTextPaint.setTextAlign(Paint.Align.LEFT);
			canvas.drawText(context.getString(R.string.draw_text_cn), x, y, alignTextPaint);

			// 繪製居中對齊文字
			canvas.translate(0, 30);
			alignTextPaint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(context.getString(R.string.draw_text_cn), x, y, alignTextPaint);

			// 繪製右對齊文字
			canvas.translate(0, 30);
			alignTextPaint.setTextAlign(Paint.Align.RIGHT);
			canvas.drawText(context.getString(R.string.draw_text_cn), x, y, alignTextPaint);
			
			
			// 繪製空心字
			canvas.translate(100, 30 * 2);
			canvas.drawText(context.getString(R.string.draw_text_en), 10, y + 100, textPaint);
		}

		@Override
		protected void onSizeChanged(int w, int h, int ow, int oh) {
			super.onSizeChanged(w, h, ow, oh);
			// 計算螢幕的居中位置
			x=w*0.5f; 
		}
	}
}
