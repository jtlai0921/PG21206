package com.uppowerstudio.chapter8.googlecalendar.common;

import java.util.List;

import com.uppowerstudio.chapter8.googlecalendar.R;
import com.uppowerstudio.chapter8.googlecalendar.models.CalendarModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Google日曆搜索結果數據列表適配器
 * 
 * @author UPPower Studio
 * 
 */
public class CalendarListAdapter extends ArrayAdapter<CalendarModel> {
	public List<CalendarModel> items;
	public Context context;

	/**
	 * 構造函數
	 * 
	 * @param context
	 * @param textViewResourceId
	 * @param items
	 */
	public CalendarListAdapter(Context context, int textViewResourceId,
			List<CalendarModel> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			// 加載ListView列表項佈局
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_list_item, null);
		}

		// 獲取搜索到的結果
		final CalendarModel resultModel = items.get(position);

		// 讀取並設置日曆的標題
		TextView title = (TextView) v.findViewById(R.id.item_title);
		title.setText(resultModel.getTitle());

		// 讀取並設置日曆的說明
		TextView summary = (TextView) v.findViewById(R.id.item_summary);
		summary.setText(resultModel.getSummary());

		return v;
	}
}