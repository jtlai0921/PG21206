package com.uppowerstudio.chapter8.googlecalendar.common;

import java.util.List;

import com.uppowerstudio.chapter8.googlecalendar.R;
import com.uppowerstudio.chapter8.googlecalendar.models.CalendarEventModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Google日曆活動事件搜索結果數據列表適配器
 * 
 * @author UPPower Studio
 * 
 */
public class CalendarEventListAdapter extends ArrayAdapter<CalendarEventModel> {
	public List<CalendarEventModel> items;
	public Context context;

	/**
	 * 構造函數
	 * 
	 * @param context
	 * @param textViewResourceId
	 * @param items
	 */
	public CalendarEventListAdapter(Context context, int textViewResourceId,
			List<CalendarEventModel> items) {
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
			v = vi.inflate(R.layout.calendar_event_list_item, null);
		}

		// 獲取搜索到的結果
		final CalendarEventModel resultModel = items.get(position);

		// 讀取並設置日曆活動事件的標題
		TextView title = (TextView) v.findViewById(R.id.item_title);
		title.setText(resultModel.getTitle());

		// 讀取並設置日曆活動事件的說明
		TextView content = (TextView) v.findViewById(R.id.item_content);
		content.setText(resultModel.getContent());

		// 讀取並設置日曆活動事件的開始與結束時間
		TextView period = (TextView) v.findViewById(R.id.item_when);
		period.setText(resultModel.getEventDate() + "  "
				+ resultModel.getStartTime() + " - " + resultModel.getEndTime());

		return v;
	}
}