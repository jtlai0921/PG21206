package com.uppowerstudio.chapter7.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;

/**
 * 圖像裁剪與渲染介面程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class ShaderActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 載入自訂視圖
		setContentView(new ShaderView(ShaderActivity.this));
	}

	/**
	 * 圖像裁剪與渲染自訂視圖
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private static class ShaderView extends View {

		private Context context;
		
		// 宣告用於裁剪的畫筆
		private Paint shaderPaint;
		
		// 宣告用於繪製說明文字的畫筆
		private Paint textPaint;

		// 宣告圖像物件變數
		private Bitmap bitmap;

		// 圖片裁剪渲染
		private Shader bitmapShader;

		// 混合渲染
		private Shader composeShader;

		// 線性漸變渲染
		private Shader linearGradient;

		// 放射狀漸變渲染
		private Shader radialGradient;

		// 梯度漸變渲染
		private Shader sweepGradient;

		public ShaderView(Context context) {
			super(context);

			this.context=context;
			
			// 設置用於繪製說明文字的畫筆
			textPaint=new Paint();
			
			// 設置用於裁剪的畫筆
			shaderPaint=new Paint();
			shaderPaint.setAntiAlias(true);

			// 從Drawable目錄獲取圖片
			bitmap=((BitmapDrawable) getResources().getDrawable(
					R.drawable.android)).getBitmap();

			// 建構圖像裁剪
			bitmapShader=new BitmapShader(bitmap, Shader.TileMode.REPEAT,
					Shader.TileMode.MIRROR);

			// 建構線性漸變渲染
			linearGradient=new LinearGradient(0, 0, 150, 150, new int[] {
					Color.RED, Color.WHITE, Color.BLUE }, null,
					Shader.TileMode.REPEAT);

			// 使用裁剪與線性漸變建構混合渲染
			composeShader=new ComposeShader(bitmapShader, linearGradient,
					PorterDuff.Mode.LIGHTEN);

			// 建構放射狀漸變渲染
			radialGradient=new RadialGradient(50, 200, 50, new int[] {
					Color.RED, Color.WHITE, Color.BLUE }, null,
					Shader.TileMode.REPEAT);

			// 建構梯度漸變渲染
			sweepGradient=new SweepGradient(50, 50, new int[] { Color.RED,
					Color.WHITE, Color.BLUE }, null);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// 建構裁剪區域為矩形
			ShapeDrawable shapedBitmap=new ShapeDrawable(new RectShape());
			// 將裁剪區域設置到畫筆物件中
			shapedBitmap.getPaint().setShader(bitmapShader);
			// 設置顯示區域
			shapedBitmap.setBounds(10, 10, 150, 150);
			// 進行裁剪
			shapedBitmap.draw(canvas);
			// 繪製說明性文字
			canvas.drawText(context.getString(R.string.msg_shader), 50, 170, textPaint);
			
			// 設置線性漸變渲染
			shaderPaint.setShader(linearGradient);
			// 繪製漸變圓形
			canvas.drawCircle(220, 60, 50, shaderPaint);
			// 繪製說明性文字
			canvas.drawText(context.getString(R.string.msg_linear), 220, 170, textPaint);
			
			// 設置放射狀漸變渲染
			shaderPaint.setShader(radialGradient);
			// 繪製漸變矩形
			canvas.drawRect(20, 180, 150, 320, shaderPaint);
			// 繪製說明性文字
			canvas.drawText(context.getString(R.string.msg_radial), 50, 340, textPaint);
			
			// 設置混合型漸變
			shaderPaint.setShader(composeShader);
			// 繪製漸變矩形
			canvas.drawRect(180, 180, 300, 320, shaderPaint);			
			// 繪製說明性文字
			canvas.drawText(context.getString(R.string.msg_compose), 220, 340, textPaint);
			
			// 設置梯度漸變
			shaderPaint.setShader(sweepGradient);
			// 繪製漸變矩形
			canvas.drawRect(20, 350, 300, 400, shaderPaint);			
			// 繪製說明性文字
			canvas.drawText(context.getString(R.string.msg_sweep), 120, 420, textPaint);
		}
	}
}
