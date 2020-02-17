package com.uppowerstudio.chapter5.adapter.simplecursor;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * SimpleCursorAdapter資料配接器
 * @author UPPower Studio
 *
 */
public class GoodsAdapter extends SimpleCursorAdapter {
	// 宣告Cursor物件變數
	private Cursor c;

	/*
	 * 提供建構方法
	 */
	public GoodsAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to) {
		super(context, layout, c, from, to);
		this.c=c;
	}

	/*
	 * 重寫bindView方法
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// 指定Cursor物件中的值與View中ID的對應關係
		TextView name=(TextView) view.findViewById(R.id.name);
		int i=c.getColumnIndexOrThrow("goods_name");
		name.setText(c.getString(i));

		TextView status=(TextView) view.findViewById(R.id.status);
		i=c.getColumnIndexOrThrow("goods_status");
		// 根據資料庫中的值，設置View中顯示的值
		if ("Y".equals(c.getString(i))) {
			status.setText(context.getString(R.string.available));
		} else {
			status.setText(context.getString(R.string.inavailable));
		}
	}
}
