package com.uppowerstudio.chapter6.wifimanager;

import java.util.List;

import com.uppowerstudio.chapter6.wifimanager.utils.CommonUtils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Wi-Fi�������I��ƲM��t����
 * @author UPPower Studio
 *
 */
public class MyArrayAdapter extends ArrayAdapter<ScanResult> {
	public List<ScanResult> items;
	public Context context;

	/**
	 * �c�y���
	 * @param context
	 * @param textViewResourceId
	 * @param items
	 */
	public MyArrayAdapter(Context context, int textViewResourceId,
			List<ScanResult> items) {
		super(context, textViewResourceId, items);
		this.context=context;
		this.items=items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=convertView;
		if (v == null) {
			// ���JListView�C���G��
			LayoutInflater vi=(LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=vi.inflate(R.layout.list_item, null);
		}

		// ������y�쪺���G
		final ScanResult sr=items.get(position);

		// Ū���ó]�m���y�쪺Wi-Fi���I�W��
		TextView ssidName=(TextView) v.findViewById(R.id.ssid_name);
		ssidName.setText(sr.SSID);

		// Ū���ó]�m���y�쪺Wi-Fi���I���W�v���
		TextView frequency=(TextView) v.findViewById(R.id.frequency);
		frequency.setText(CommonUtils.convertFrequency(sr.frequency) + "G");

		// Ū���ó]�m���y�쪺Wi-Fi���I���H���j��
		TextView signalStrength=(TextView) v
				.findViewById(R.id.signal_strength);
		signalStrength.setText(sr.level + "dBm");

		// Ū���ó]�m���y�쪺Wi-Fi���I�O�_�[�K
		TextView encrypt=(TextView) v.findViewById(R.id.encrypt);
		encrypt.setText(CommonUtils.isEmptyString(sr.capabilities) ? context
				.getString(R.string.no) : context.getString(R.string.yes));

		return v;
	}
}
