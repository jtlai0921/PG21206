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
 * Google��䬡�ʨƥ�j�����G�ƾڦC��A�t��
 * 
 * @author UPPower Studio
 * 
 */
public class CalendarEventListAdapter extends ArrayAdapter<CalendarEventModel> {
	public List<CalendarEventModel> items;
	public Context context;

	/**
	 * �c�y���
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
			// �[��ListView�C���G��
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.calendar_event_list_item, null);
		}

		// ����j���쪺���G
		final CalendarEventModel resultModel = items.get(position);

		// Ū���ó]�m��䬡�ʨƥ󪺼��D
		TextView title = (TextView) v.findViewById(R.id.item_title);
		title.setText(resultModel.getTitle());

		// Ū���ó]�m��䬡�ʨƥ󪺻���
		TextView content = (TextView) v.findViewById(R.id.item_content);
		content.setText(resultModel.getContent());

		// Ū���ó]�m��䬡�ʨƥ󪺶}�l�P�����ɶ�
		TextView period = (TextView) v.findViewById(R.id.item_when);
		period.setText(resultModel.getEventDate() + "  "
				+ resultModel.getStartTime() + " - " + resultModel.getEndTime());

		return v;
	}
}