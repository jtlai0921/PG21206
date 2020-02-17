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
 * Google���j�����G�ƾڦC��A�t��
 * 
 * @author UPPower Studio
 * 
 */
public class CalendarListAdapter extends ArrayAdapter<CalendarModel> {
	public List<CalendarModel> items;
	public Context context;

	/**
	 * �c�y���
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
			// �[��ListView�C���G��
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_list_item, null);
		}

		// ����j���쪺���G
		final CalendarModel resultModel = items.get(position);

		// Ū���ó]�m��䪺���D
		TextView title = (TextView) v.findViewById(R.id.item_title);
		title.setText(resultModel.getTitle());

		// Ū���ó]�m��䪺����
		TextView summary = (TextView) v.findViewById(R.id.item_summary);
		summary.setText(resultModel.getSummary());

		return v;
	}
}