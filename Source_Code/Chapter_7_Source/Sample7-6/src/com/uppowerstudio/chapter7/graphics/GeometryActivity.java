package com.uppowerstudio.chapter7.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

/**
 * 繪製幾何圖形介面程式碼
 * @author UPPower Studio
 *
 */
public class GeometryActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 載入自訂視圖
		setContentView(new GeometryView(GeometryActivity.this));
	}

	/**
	 * 繪製幾何圖形自訂視圖
	 * @author UPPower Studio
	 *
	 */
	private static class GeometryView extends View {
		private Context context;
		
		// 宣告用於繪製說明文字的畫筆
		private Paint textPaint;
		
		// 宣告用於繪製弧線的畫筆
		private Paint arcPaint;
		
		// 宣告用於繪製矩形的畫筆
		private Paint rectPaint;
		
		// 宣告用於繪製圓形的畫筆
		private Paint circlePaint;
		
		// 宣告用於繪製橢圓的畫筆
		private Paint ovalPaint;

		// 宣告用於繪製直線的畫筆
		private Paint linePaint;
		
		// 宣告用於繪製多邊形的畫筆
		private Paint pathPaint;
		
		public GeometryView(Context context) {
			super(context);
			this.context=context;
			
			// 設置用於繪製弧線的畫筆
			arcPaint=new Paint();
			// 設置抗鋸齒
			arcPaint.setAntiAlias(true);
			// 設置為空心模式
			arcPaint.setStyle(Paint.Style.STROKE);
			// 設置顏色
			arcPaint.setColor(Color.BLACK);
			// 設置邊的寬度
			arcPaint.setStrokeWidth(4);
			
			// 設置用於繪製說明文字的畫筆
			textPaint=new Paint();
			arcPaint.setColor(Color.RED);
			
			// 設置用於繪製矩形的畫筆
			rectPaint=new Paint();
			rectPaint.setAntiAlias(true);
			rectPaint.setStyle(Paint.Style.FILL);
			// 通過ARGB模式設置顏色
			rectPaint.setARGB(60, 120, 240, 99);
			
			// 設置用於繪製圓形的畫筆
			circlePaint=new Paint();
			circlePaint.setAntiAlias(true);
			// 設置為填充模式
			circlePaint.setStyle(Paint.Style.FILL);
			circlePaint.setColor(Color.BLUE);
			
			// 設置用於繪製橢圓的畫筆
			ovalPaint=new Paint();
			ovalPaint.setAntiAlias(true);
			ovalPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			ovalPaint.setColor(Color.LTGRAY);
			
			// 設置用於繪製直線的畫筆
			linePaint=new Paint();
			linePaint.setColor(Color.YELLOW);
			
			// 設置用於繪製多邊形的畫筆
			pathPaint=new Paint();
			pathPaint.setAntiAlias(true);
			pathPaint.setStyle(Paint.Style.STROKE);
			pathPaint.setColor(Color.BLACK);
			pathPaint.setStrokeWidth(4);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			
			// 繪製弧線
			RectF arcRect=new RectF(10, 10, 100, 100);
			canvas.drawArc(arcRect, 0, 270, false, arcPaint);
			// 繪製弧線說明文字
			canvas.drawText(context.getString(R.string.msg_arc), 50, 120, textPaint);
			
			// 繪製矩形
			canvas.drawRect(150, 10, 250, 100, rectPaint);
			// 繪製矩形說明文字
			canvas.drawText(context.getString(R.string.msg_rect), 190, 120, textPaint);
			
			// 繪製圓形
			canvas.drawCircle(60, 190, 50, circlePaint);
			// 繪製圓形說明文字
			canvas.drawText(context.getString(R.string.msg_circle), 50, 260, textPaint);
			
			// 繪製橢圓形
			RectF ovalRect=new RectF(150, 140, 280, 200);
			canvas.drawOval(ovalRect, ovalPaint);
			// 繪製橢圓形說明文字
			canvas.drawText(context.getString(R.string.msg_oval), 190, 260, textPaint);
			
			// 繪製直線
			canvas.drawLine(0, 280, 320, 280, linePaint);
			
			
			// 建構多邊形
			Path path=new Path();
			path.moveTo(50, 350);
			path.lineTo(150, 350);
			path.lineTo(130, 300);
			path.lineTo(100, 300);
			// 使上邊定義的各邊構成封閉多邊形
			path.close();
			
			// 繪製多邊形
			canvas.drawPath(path, pathPaint);
			// 繪製多邊形說明文字
			canvas.drawText(context.getString(R.string.msg_path), 190, 380, textPaint);
		}
	}
}
