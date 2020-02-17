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
 * ø�s�X��ϧΤ����{���X
 * @author UPPower Studio
 *
 */
public class GeometryActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ���J�ۭq����
		setContentView(new GeometryView(GeometryActivity.this));
	}

	/**
	 * ø�s�X��ϧΦۭq����
	 * @author UPPower Studio
	 *
	 */
	private static class GeometryView extends View {
		private Context context;
		
		// �ŧi�Ω�ø�s������r���e��
		private Paint textPaint;
		
		// �ŧi�Ω�ø�s���u���e��
		private Paint arcPaint;
		
		// �ŧi�Ω�ø�s�x�Ϊ��e��
		private Paint rectPaint;
		
		// �ŧi�Ω�ø�s��Ϊ��e��
		private Paint circlePaint;
		
		// �ŧi�Ω�ø�s��ꪺ�e��
		private Paint ovalPaint;

		// �ŧi�Ω�ø�s���u���e��
		private Paint linePaint;
		
		// �ŧi�Ω�ø�s�h��Ϊ��e��
		private Paint pathPaint;
		
		public GeometryView(Context context) {
			super(context);
			this.context=context;
			
			// �]�m�Ω�ø�s���u���e��
			arcPaint=new Paint();
			// �]�m�ܿ���
			arcPaint.setAntiAlias(true);
			// �]�m���Ť߼Ҧ�
			arcPaint.setStyle(Paint.Style.STROKE);
			// �]�m�C��
			arcPaint.setColor(Color.BLACK);
			// �]�m�䪺�e��
			arcPaint.setStrokeWidth(4);
			
			// �]�m�Ω�ø�s������r���e��
			textPaint=new Paint();
			arcPaint.setColor(Color.RED);
			
			// �]�m�Ω�ø�s�x�Ϊ��e��
			rectPaint=new Paint();
			rectPaint.setAntiAlias(true);
			rectPaint.setStyle(Paint.Style.FILL);
			// �q�LARGB�Ҧ��]�m�C��
			rectPaint.setARGB(60, 120, 240, 99);
			
			// �]�m�Ω�ø�s��Ϊ��e��
			circlePaint=new Paint();
			circlePaint.setAntiAlias(true);
			// �]�m����R�Ҧ�
			circlePaint.setStyle(Paint.Style.FILL);
			circlePaint.setColor(Color.BLUE);
			
			// �]�m�Ω�ø�s��ꪺ�e��
			ovalPaint=new Paint();
			ovalPaint.setAntiAlias(true);
			ovalPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			ovalPaint.setColor(Color.LTGRAY);
			
			// �]�m�Ω�ø�s���u���e��
			linePaint=new Paint();
			linePaint.setColor(Color.YELLOW);
			
			// �]�m�Ω�ø�s�h��Ϊ��e��
			pathPaint=new Paint();
			pathPaint.setAntiAlias(true);
			pathPaint.setStyle(Paint.Style.STROKE);
			pathPaint.setColor(Color.BLACK);
			pathPaint.setStrokeWidth(4);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			
			// ø�s���u
			RectF arcRect=new RectF(10, 10, 100, 100);
			canvas.drawArc(arcRect, 0, 270, false, arcPaint);
			// ø�s���u������r
			canvas.drawText(context.getString(R.string.msg_arc), 50, 120, textPaint);
			
			// ø�s�x��
			canvas.drawRect(150, 10, 250, 100, rectPaint);
			// ø�s�x�λ�����r
			canvas.drawText(context.getString(R.string.msg_rect), 190, 120, textPaint);
			
			// ø�s���
			canvas.drawCircle(60, 190, 50, circlePaint);
			// ø�s��λ�����r
			canvas.drawText(context.getString(R.string.msg_circle), 50, 260, textPaint);
			
			// ø�s����
			RectF ovalRect=new RectF(150, 140, 280, 200);
			canvas.drawOval(ovalRect, ovalPaint);
			// ø�s���λ�����r
			canvas.drawText(context.getString(R.string.msg_oval), 190, 260, textPaint);
			
			// ø�s���u
			canvas.drawLine(0, 280, 320, 280, linePaint);
			
			
			// �غc�h���
			Path path=new Path();
			path.moveTo(50, 350);
			path.lineTo(150, 350);
			path.lineTo(130, 300);
			path.lineTo(100, 300);
			// �ϤW��w�q���U��c���ʳ��h���
			path.close();
			
			// ø�s�h���
			canvas.drawPath(path, pathPaint);
			// ø�s�h��λ�����r
			canvas.drawText(context.getString(R.string.msg_path), 190, 380, textPaint);
		}
	}
}
