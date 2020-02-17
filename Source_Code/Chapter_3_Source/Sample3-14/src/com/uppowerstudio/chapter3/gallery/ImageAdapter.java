package com.uppowerstudio.chapter3.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 畫廊範例
 * 
 * @author UPPower Studio
 * 
 */
public class ImageAdapter extends BaseAdapter {
	private Context ctx;
	private int[] imgResId=new int[] { R.drawable.icon_01,
			R.drawable.icon_02, R.drawable.icon_03, R.drawable.icon_04,
			R.drawable.icon_05, };

	public ImageAdapter(Context ctx) {
		super();
		this.ctx=ctx;
	}

	@Override
	public int getCount() {
		return imgResId.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return imgResId[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView=new ImageView(ctx);
		imageView.setImageResource(imgResId[position]);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		return imageView;
	}
}
