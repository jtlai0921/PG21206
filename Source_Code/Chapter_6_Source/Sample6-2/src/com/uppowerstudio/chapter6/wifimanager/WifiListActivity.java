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
 * 清單顯示掃描到的Wi-Fi網路熱點
 * @author UPPower Studio
 *
 */
public class WifiListActivity extends ListActivity {

	private static final String EMPATY_SPAN=" ";

	// 宣告Wi-Fi管理類別變數
	private WifiManager mWiFiManager;
	
	// 宣告用於存儲熱點的List
	private List<ScanResult>availableHotSpot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		// 產生實體WifiManager類別對象
		mWiFiManager=(WifiManager) getSystemService(WIFI_SERVICE);

		// 設置顯示清單的表頭說明文字
		getListView().addHeaderView(
				LayoutInflater.from(this).inflate(R.layout.list_item_title,
						null));
		// 掃描並顯示熱點資訊
		scanWifiNetwork();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// 獲取當前按一下的Item物件
		Object obj=l.getItemAtPosition(position);
		
		// 驗證是否為ScanResult物件
		ScanResult sr=null;
		if (obj instanceof ScanResult) {
			sr=(ScanResult) obj;
		}

		// 從ScanResult物件中獲取並顯示相關熱點資訊
		if (sr != null) {
			getScanResultInfo(sr);
		}

	}

	/**
	 * 獲取並顯示相關熱點資訊
	 * @param sr
	 */
	private void getScanResultInfo(ScanResult sr) {
		StringBuffer scanResultInfo=new StringBuffer();

		// 獲取當前Wi-Fi的名稱
		scanResultInfo.append(getString(R.string.scan_result_ssid)).append(EMPATY_SPAN)
				.append(sr.SSID).append("\n");
		// 獲取當前Wi-Fi的接入點地址
		scanResultInfo.append(getString(R.string.scan_result_bssid)).append(EMPATY_SPAN)
				.append(sr.BSSID).append("\n");
		// 獲取當前Wi-Fi的信號強度
		scanResultInfo.append(getString(R.string.scan_result_signal_strength))
				.append(EMPATY_SPAN).append(sr.level).append(" dBm")
				.append("\n");
		// 獲取當前Wi-Fi的信號頻率
		scanResultInfo.append(getString(R.string.scan_result_frequency))
				.append(EMPATY_SPAN).append(sr.frequency).append(" MHz")
				.append("\n");
		// 獲取當前Wi-Fi加密類型
		scanResultInfo.append(getString(R.string.scan_result_encypt)).append(EMPATY_SPAN)
				.append(sr.capabilities).append("\n");
		
		// 顯示相關資訊
		CommonUtils.genDialog(WifiListActivity.this,
				getString(R.string.app_name), scanResultInfo.toString(),
				android.R.drawable.ic_dialog_info, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}

				}, null).show();

	}

	/**
	 * 掃描並顯示熱點資訊
	 */
	private void scanWifiNetwork() {
		// 獲取掃描到的Wi-Fi熱點清單
		availableHotSpot=mWiFiManager.getScanResults();

		// 呼叫自訂Adapter進行資料設置操作
		MyArrayAdapter adapter=new MyArrayAdapter(WifiListActivity.this,
				R.layout.list_item, availableHotSpot);

		// 對ListView進行資料綁定
		setListAdapter(adapter);

		// 向使用者提示按一下清單項可顯示Wi-Fi熱點詳細資訊
		Toast.makeText(WifiListActivity.this, getString(R.string.scan_tip),
				Toast.LENGTH_LONG).show();
	}
}
