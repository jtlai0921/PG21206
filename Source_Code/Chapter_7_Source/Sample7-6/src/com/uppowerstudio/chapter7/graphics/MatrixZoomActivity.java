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
 * �Ϲ�����P�Y�񤶭��{���X
 * @author UPPower Studio
 *
 */
public class MatrixZoomActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ���J�ۭq����
		setContentView(new MatrixZoomView(MatrixZoomActivity.this));
	}

	/**
	 * �Ϲ�����P�Y��ۭq����
	 * 
	 * @author UPPower Studio
	 * 
	 */
	private static class MatrixZoomView extends View {

		// �ŧi�e��
		private Paint bitmapPaint;

		// �ŧi��l�Ϲ��ܼ�
		private Bitmap bitmap;

		// �ŧi����Ϲ��ܼ�
		private Bitmap matrixBitmap;

		// �ŧi��j�Ϲ��ܼ�
		private Bitmap zoomBitmap;

		// �w�q���઺����
		private float degree=45.0F;

		// �w�q�Y�񪺤�ҭ�
		private float scale=2.0F;

		// �ŧiMatrix�ܼ�
		private Matrix matrix;

		// �w�q�Ϲ����e�B��
		private int width=0;
		private int height=0;

		public MatrixZoomView(Context context) {
			super(context);

			// �غc�e��
			bitmapPaint=new Paint();

			// �s�WMatrix��H
			matrix=new Matrix();

			// �qDrawable�ؿ�����Ϥ�
			bitmap=((BitmapDrawable) getResources().getDrawable(
					R.drawable.google)).getBitmap();

			// ����Ϲ����e�שM����
			width=bitmap.getWidth();
			height=bitmap.getHeight();

		}

		@Override
		protected void onDraw(Canvas canvas) {
			// �]�m�I����
			canvas.drawColor(Color.WHITE);

			// ���mMatrix��H
			matrix.reset();
			// �]�m���઺����
			matrix.setRotate(degree);
			// �s�W����᪺�Ϲ�
			matrixBitmap=Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, true);
			// ø�s����᪺�Ϲ�
			canvas.drawBitmap(matrixBitmap, 10, 20, bitmapPaint);

			// ���mMatrix��H
			matrix.reset();
			// �]�m�Y���
			matrix.postScale(scale, scale);
			// �s�W�Y��᪺�Ϲ�
			zoomBitmap=Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, true);
			// ø�s�Y��᪺�Ϲ�
			canvas.drawBitmap(zoomBitmap, 10, 200, bitmapPaint);
		}
	}
}
