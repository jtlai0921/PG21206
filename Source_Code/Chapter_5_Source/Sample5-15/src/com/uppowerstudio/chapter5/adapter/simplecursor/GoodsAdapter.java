package com.uppowerstudio.chapter5.adapter.simplecursor;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * SimpleCursorAdapter��ưt����
 * @author UPPower Studio
 *
 */
public class GoodsAdapter extends SimpleCursorAdapter {
	// �ŧiCursor�����ܼ�
	private Cursor c;

	/*
	 * ���ѫغc��k
	 */
	public GoodsAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to) {
		super(context, layout, c, from, to);
		this.c=c;
	}

	/*
	 * ���gbindView��k
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// ���wCursor���󤤪��ȻPView��ID���������Y
		TextView name=(TextView) view.findViewById(R.id.name);
		int i=c.getColumnIndexOrThrow("goods_name");
		name.setText(c.getString(i));

		TextView status=(TextView) view.findViewById(R.id.status);
		i=c.getColumnIndexOrThrow("goods_status");
		// �ھڸ�Ʈw�����ȡA�]�mView����ܪ���
		if ("Y".equals(c.getString(i))) {
			status.setText(context.getString(R.string.available));
		} else {
			status.setText(context.getString(R.string.inavailable));
		}
	}
}
