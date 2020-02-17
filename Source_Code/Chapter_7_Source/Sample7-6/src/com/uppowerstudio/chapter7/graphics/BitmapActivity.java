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
 * ø�s�Ϲ������{���X
 * @author UPPower Studio
 *
 */
public class BitmapActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		// ���J�ۭq����
		setContentView(new BitmapView(BitmapActivity.this));

	}

	/**
	 * ø�s�Ϲ��ۭq����
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private static class BitmapView extends View {
		
		// �ŧi�e��
		private Paint bitmapPaint;
		
		// �ŧi�Ϲ������ܼ�
		private Bitmap bitmap;

		public BitmapView(Context context) {
			super(context);
			
			// �غc�e��
			bitmapPaint=new Paint();

			// �qDrawable�ؿ�����Ϥ�
			bitmap=((BitmapDrawable) getResources().getDrawable(
					R.drawable.google)).getBitmap();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// �]�m�I����
			canvas.drawColor(Color.WHITE);
			
			// ø�s�Ϥ�
			canvas.drawBitmap(bitmap, 10, 20, bitmapPaint);
		}
	}
}
