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
 * �Ϲ����ŻP��V�����{���X
 * 
 * @author UPPower Studio
 * 
 */
public class ShaderActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ���J�ۭq����
		setContentView(new ShaderView(ShaderActivity.this));
	}

	/**
	 * �Ϲ����ŻP��V�ۭq����
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private static class ShaderView extends View {

		private Context context;
		
		// �ŧi�Ω���Ū��e��
		private Paint shaderPaint;
		
		// �ŧi�Ω�ø�s������r���e��
		private Paint textPaint;

		// �ŧi�Ϲ������ܼ�
		private Bitmap bitmap;

		// �Ϥ����Ŵ�V
		private Shader bitmapShader;

		// �V�X��V
		private Shader composeShader;

		// �u�ʺ��ܴ�V
		private Shader linearGradient;

		// ��g�����ܴ�V
		private Shader radialGradient;

		// ��׺��ܴ�V
		private Shader sweepGradient;

		public ShaderView(Context context) {
			super(context);

			this.context=context;
			
			// �]�m�Ω�ø�s������r���e��
			textPaint=new Paint();
			
			// �]�m�Ω���Ū��e��
			shaderPaint=new Paint();
			shaderPaint.setAntiAlias(true);

			// �qDrawable�ؿ�����Ϥ�
			bitmap=((BitmapDrawable) getResources().getDrawable(
					R.drawable.android)).getBitmap();

			// �غc�Ϲ�����
			bitmapShader=new BitmapShader(bitmap, Shader.TileMode.REPEAT,
					Shader.TileMode.MIRROR);

			// �غc�u�ʺ��ܴ�V
			linearGradient=new LinearGradient(0, 0, 150, 150, new int[] {
					Color.RED, Color.WHITE, Color.BLUE }, null,
					Shader.TileMode.REPEAT);

			// �ϥε��ŻP�u�ʺ��ܫغc�V�X��V
			composeShader=new ComposeShader(bitmapShader, linearGradient,
					PorterDuff.Mode.LIGHTEN);

			// �غc��g�����ܴ�V
			radialGradient=new RadialGradient(50, 200, 50, new int[] {
					Color.RED, Color.WHITE, Color.BLUE }, null,
					Shader.TileMode.REPEAT);

			// �غc��׺��ܴ�V
			sweepGradient=new SweepGradient(50, 50, new int[] { Color.RED,
					Color.WHITE, Color.BLUE }, null);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// �غc���Űϰ쬰�x��
			ShapeDrawable shapedBitmap=new ShapeDrawable(new RectShape());
			// �N���Űϰ�]�m��e������
			shapedBitmap.getPaint().setShader(bitmapShader);
			// �]�m��ܰϰ�
			shapedBitmap.setBounds(10, 10, 150, 150);
			// �i�����
			shapedBitmap.draw(canvas);
			// ø�s�����ʤ�r
			canvas.drawText(context.getString(R.string.msg_shader), 50, 170, textPaint);
			
			// �]�m�u�ʺ��ܴ�V
			shaderPaint.setShader(linearGradient);
			// ø�s���ܶ��
			canvas.drawCircle(220, 60, 50, shaderPaint);
			// ø�s�����ʤ�r
			canvas.drawText(context.getString(R.string.msg_linear), 220, 170, textPaint);
			
			// �]�m��g�����ܴ�V
			shaderPaint.setShader(radialGradient);
			// ø�s���ܯx��
			canvas.drawRect(20, 180, 150, 320, shaderPaint);
			// ø�s�����ʤ�r
			canvas.drawText(context.getString(R.string.msg_radial), 50, 340, textPaint);
			
			// �]�m�V�X������
			shaderPaint.setShader(composeShader);
			// ø�s���ܯx��
			canvas.drawRect(180, 180, 300, 320, shaderPaint);			
			// ø�s�����ʤ�r
			canvas.drawText(context.getString(R.string.msg_compose), 220, 340, textPaint);
			
			// �]�m��׺���
			shaderPaint.setShader(sweepGradient);
			// ø�s���ܯx��
			canvas.drawRect(20, 350, 300, 400, shaderPaint);			
			// ø�s�����ʤ�r
			canvas.drawText(context.getString(R.string.msg_sweep), 120, 420, textPaint);
		}
	}
}
