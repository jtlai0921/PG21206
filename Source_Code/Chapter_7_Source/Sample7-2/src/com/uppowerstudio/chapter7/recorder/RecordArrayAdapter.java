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
 * �����C��t����
 * @author UPPower Studio
 *
 */
public class RecordArrayAdapter extends ArrayAdapter<File> {
	public List<File> items;
	public Context context;

	/**
	 * �غc���
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
			// ���JListView�C���G��
			LayoutInflater vi=(LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=vi.inflate(R.layout.item, null);
		}

		// ������T��
		final File file=items.get(position);

		// �]�m���T�ɮצW��
		TextView fileName=(TextView) v.findViewById(R.id.item_file_name);
		fileName.setText(file.getName());

		// �]�m���T�ɤj�p
		TextView fileSize=(TextView) v.findViewById(R.id.item_file_size);
		fileSize.setText(CommonUtils.getFileSize(file.length()));

		// �]�m���T�ɷs�W�ɶ�
		TextView fileDate=(TextView) v.findViewById(R.id.item_file_date);
		fileDate.setText(CommonUtils.getCurrentDate());

		return v;
	}
}
