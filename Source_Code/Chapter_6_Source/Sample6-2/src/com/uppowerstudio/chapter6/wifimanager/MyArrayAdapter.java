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
 * Wi-Fi網路熱點資料清單配接器
 * @author UPPower Studio
 *
 */
public class MyArrayAdapter extends ArrayAdapter<ScanResult> {
	public List<ScanResult> items;
	public Context context;

	/**
	 * 構造函數
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
			// 載入ListView列表項佈局
			LayoutInflater vi=(LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=vi.inflate(R.layout.list_item, null);
		}

		// 獲取掃描到的結果
		final ScanResult sr=items.get(position);

		// 讀取並設置掃描到的Wi-Fi熱點名稱
		TextView ssidName=(TextView) v.findViewById(R.id.ssid_name);
		ssidName.setText(sr.SSID);

		// 讀取並設置掃描到的Wi-Fi熱點的頻率資料
		TextView frequency=(TextView) v.findViewById(R.id.frequency);
		frequency.setText(CommonUtils.convertFrequency(sr.frequency) + "G");

		// 讀取並設置掃描到的Wi-Fi熱點的信號強度
		TextView signalStrength=(TextView) v
				.findViewById(R.id.signal_strength);
		signalStrength.setText(sr.level + "dBm");

		// 讀取並設置掃描到的Wi-Fi熱點是否加密
		TextView encrypt=(TextView) v.findViewById(R.id.encrypt);
		encrypt.setText(CommonUtils.isEmptyString(sr.capabilities) ? context
				.getString(R.string.no) : context.getString(R.string.yes));

		return v;
	}
}
