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
 * ø�s��r�����{���X
 * @author UPPower Studio
 *
 */
public class TextActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ���J�ۭq����
		setContentView(new DrawTextView(TextActivity.this));
	}

	/**
	 * ø�s�ɦۭq����
	 * @author UPPower Studio
	 *
	 */
	private static class DrawTextView extends View {
		private Context context;
		
		// �ŧi�Ω�ø�s��r���e��
		private Paint textPaint;
		
		// �ŧi�Ω�ø�s��r����覡���e��
		private Paint alignTextPaint;
		
		// �y��
		private float x;
		private float y;
		

		public DrawTextView(Context context) {
			super(context);
			setFocusable(true);
			this.context=context;
			
			// �]�m�Ω�ø�s��r���e��
			textPaint=new Paint();
			
			// �]�m�ܿ���
			textPaint.setAntiAlias(true);
			// �]�m�r�鬰�Ťߦr
			textPaint.setStyle(Paint.Style.STROKE);
			// �]�m��e��
			textPaint.setStrokeWidth(3);
			// �]�m�C��
			textPaint.setColor(Color.RED);
			// �]�m�r������
			textPaint.setTypeface(Typeface.DEFAULT_BOLD);
			// �]�m�r��j�p
			textPaint.setTextSize(48);
			// �]�m����覡
			textPaint.setTextAlign(Paint.Align.CENTER);
			
			// �]�m�Ω�ø�s��r����覡���e��
			alignTextPaint=new Paint();
			// �]�m�ܿ���
			alignTextPaint.setAntiAlias(true);
			// �]�m�r��j�p
			alignTextPaint.setTextSize(30);
			// �]�m�r������
			alignTextPaint.setTypeface(Typeface.SERIF);

		}

		@Override
		protected void onDraw(Canvas canvas) {
			// ø�s�I�����զ�
			canvas.drawColor(Color.WHITE);
			
			// ø�s�Ω���ܹ���覡�����u
			alignTextPaint.setColor(Color.BLUE);
			canvas.drawLine(x, y, x, y+30*3, alignTextPaint);
			alignTextPaint.setColor(Color.BLACK);
			
			// ø�s�������r
			canvas.translate(0, 30);
			alignTextPaint.setTextAlign(Paint.Align.LEFT);
			canvas.drawText(context.getString(R.string.draw_text_cn), x, y, alignTextPaint);

			// ø�s�~�������r
			canvas.translate(0, 30);
			alignTextPaint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(context.getString(R.string.draw_text_cn), x, y, alignTextPaint);

			// ø�s�k�����r
			canvas.translate(0, 30);
			alignTextPaint.setTextAlign(Paint.Align.RIGHT);
			canvas.drawText(context.getString(R.string.draw_text_cn), x, y, alignTextPaint);
			
			
			// ø�s�Ťߦr
			canvas.translate(100, 30 * 2);
			canvas.drawText(context.getString(R.string.draw_text_en), 10, y + 100, textPaint);
		}

		@Override
		protected void onSizeChanged(int w, int h, int ow, int oh) {
			super.onSizeChanged(w, h, ow, oh);
			// �p��ù����~����m
			x=w*0.5f; 
		}
	}
}
