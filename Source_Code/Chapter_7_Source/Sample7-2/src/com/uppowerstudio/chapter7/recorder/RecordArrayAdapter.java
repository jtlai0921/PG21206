package com.uppowerstudio.chapter7.recorder;

import java.io.File;
import java.util.List;

import com.uppowerstudio.chapter7.recorder.utils.CommonUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 錄音列表配接器
 * @author UPPower Studio
 *
 */
public class RecordArrayAdapter extends ArrayAdapter<File> {
	public List<File> items;
	public Context context;

	/**
	 * 建構函數
	 * @param context
	 * @param textViewResourceId
	 * @param items
	 */
	public RecordArrayAdapter(Context context, int textViewResourceId,
			List<File> items) {
		super(context,textViewResourceId, items);
		this.context=context;
		this.items=items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=convertView;
		if (v==null) {
			// 載入ListView列表項佈局
			LayoutInflater vi=(LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=vi.inflate(R.layout.item, null);
		}

		// 獲取音訊檔
		final File file=items.get(position);

		// 設置音訊檔案名稱
		TextView fileName=(TextView) v.findViewById(R.id.item_file_name);
		fileName.setText(file.getName());

		// 設置音訊檔大小
		TextView fileSize=(TextView) v.findViewById(R.id.item_file_size);
		fileSize.setText(CommonUtils.getFileSize(file.length()));

		// 設置音訊檔新增時間
		TextView fileDate=(TextView) v.findViewById(R.id.item_file_date);
		fileDate.setText(CommonUtils.getCurrentDate());

		return v;
	}
}
