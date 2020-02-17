package com.uppowerstudio.chapter8.googlesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Google�j�����G�ƾڦC��A�t��
 * 
 * @author UPPower Studio
 * 
 */
public class SearchResultAdapter extends ArrayAdapter<SearchResultModel> {
	public List<SearchResultModel> items;
	public Context context;

	/**
	 * �c�y���
	 * 
	 * @param context
	 * @param textViewResourceId
	 * @param items
	 */
	public SearchResultAdapter(Context context, int textViewResourceId,
			List<SearchResultModel> items) {
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
			v = vi.inflate(R.layout.list_item, null);
		}

		// ����j���쪺���G
		final SearchResultModel resultModel = items.get(position);

		// Ū���ó]�m�j�����G�����D
		TextView title = (TextView) v.findViewById(R.id.item_title);
		title.setText(resultModel.getTitle());

		// Ū���ó]�m�j�����G�����e
		TextView content = (TextView) v.findViewById(R.id.item_content);
		content.setText(resultModel.getContent());

		// Ū���ó]�m�j�����G��URL�a�}
		TextView url = (TextView) v.findViewById(R.id.item_url);
		url.setText(resultModel.getUrl());

		return v;
	}
}