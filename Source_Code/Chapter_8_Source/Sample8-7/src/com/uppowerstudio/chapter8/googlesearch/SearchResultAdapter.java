package com.uppowerstudio.chapter8.googlesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Google搜索結果數據列表適配器
 * 
 * @author UPPower Studio
 * 
 */
public class SearchResultAdapter extends ArrayAdapter<SearchResultModel> {
	public List<SearchResultModel> items;
	public Context context;

	/**
	 * 構造函數
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
			// 加載ListView列表項佈局
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_item, null);
		}

		// 獲取搜索到的結果
		final SearchResultModel resultModel = items.get(position);

		// 讀取並設置搜索結果的標題
		TextView title = (TextView) v.findViewById(R.id.item_title);
		title.setText(resultModel.getTitle());

		// 讀取並設置搜索結果的內容
		TextView content = (TextView) v.findViewById(R.id.item_content);
		content.setText(resultModel.getContent());

		// 讀取並設置搜索結果的URL地址
		TextView url = (TextView) v.findViewById(R.id.item_url);
		url.setText(resultModel.getUrl());

		return v;
	}
}