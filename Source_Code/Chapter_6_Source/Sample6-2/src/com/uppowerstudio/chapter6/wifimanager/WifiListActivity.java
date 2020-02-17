package com.uppowerstudio.chapter6.wifimanager;

import java.util.List;

import com.uppowerstudio.chapter6.wifimanager.utils.CommonUtils;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * �M����ܱ��y�쪺Wi-Fi�������I
 * @author UPPower Studio
 *
 */
public class WifiListActivity extends ListActivity {

	private static final String EMPATY_SPAN=" ";

	// �ŧiWi-Fi�޲z���O�ܼ�
	private WifiManager mWiFiManager;
	
	// �ŧi�Ω�s�x���I��List
	private List<ScanResult>availableHotSpot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		// ���͹���WifiManager���O��H
		mWiFiManager=(WifiManager) getSystemService(WIFI_SERVICE);

		// �]�m��ܲM�檺���Y������r
		getListView().addHeaderView(
				LayoutInflater.from(this).inflate(R.layout.list_item_title,
						null));
		// ���y����ܼ��I��T
		scanWifiNetwork();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// �����e���@�U��Item����
		Object obj=l.getItemAtPosition(position);
		
		// ���ҬO�_��ScanResult����
		ScanResult sr=null;
		if (obj instanceof ScanResult) {
			sr=(ScanResult) obj;
		}

		// �qScanResult�����������ܬ������I��T
		if (sr != null) {
			getScanResultInfo(sr);
		}

	}

	/**
	 * �������ܬ������I��T
	 * @param sr
	 */
	private void getScanResultInfo(ScanResult sr) {
		StringBuffer scanResultInfo=new StringBuffer();

		// �����eWi-Fi���W��
		scanResultInfo.append(getString(R.string.scan_result_ssid)).append(EMPATY_SPAN)
				.append(sr.SSID).append("\n");
		// �����eWi-Fi�����J�I�a�}
		scanResultInfo.append(getString(R.string.scan_result_bssid)).append(EMPATY_SPAN)
				.append(sr.BSSID).append("\n");
		// �����eWi-Fi���H���j��
		scanResultInfo.append(getString(R.string.scan_result_signal_strength))
				.append(EMPATY_SPAN).append(sr.level).append(" dBm")
				.append("\n");
		// �����eWi-Fi���H���W�v
		scanResultInfo.append(getString(R.string.scan_result_frequency))
				.append(EMPATY_SPAN).append(sr.frequency).append(" MHz")
				.append("\n");
		// �����eWi-Fi�[�K����
		scanResultInfo.append(getString(R.string.scan_result_encypt)).append(EMPATY_SPAN)
				.append(sr.capabilities).append("\n");
		
		// ��ܬ�����T
		CommonUtils.genDialog(WifiListActivity.this,
				getString(R.string.app_name), scanResultInfo.toString(),
				android.R.drawable.ic_dialog_info, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}

				}, null).show();

	}

	/**
	 * ���y����ܼ��I��T
	 */
	private void scanWifiNetwork() {
		// ������y�쪺Wi-Fi���I�M��
		availableHotSpot=mWiFiManager.getScanResults();

		// �I�s�ۭqAdapter�i���Ƴ]�m�ާ@
		MyArrayAdapter adapter=new MyArrayAdapter(WifiListActivity.this,
				R.layout.list_item, availableHotSpot);

		// ��ListView�i���Ƹj�w
		setListAdapter(adapter);

		// �V�ϥΪ̴��ܫ��@�U�M�涵�i���Wi-Fi���I�ԲӸ�T
		Toast.makeText(WifiListActivity.this, getString(R.string.scan_tip),
				Toast.LENGTH_LONG).show();
	}
}
