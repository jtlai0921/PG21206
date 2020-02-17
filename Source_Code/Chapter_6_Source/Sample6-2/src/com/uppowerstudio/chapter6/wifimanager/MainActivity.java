package com.uppowerstudio.chapter6.wifimanager;

import com.uppowerstudio.chapter6.wifimanager.utils.CommonUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Wi-Fi網路資訊查看範例
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {

	// 定義常量
	private static final String EMPATY_SPAN=" ";
	
	private static final int DIALOG_WIFI_DISABLED=1;
	private static final int DIALOG_WIFI_NONE_CONNECTED=3;

	// 宣告Wi-Fi管理類別變數
	private WifiManager mWiFiManager;
	
	// 宣告Button控制項變數
	private Button buttonView;
	private Button buttonScan;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化按鈕
		buttonView=(Button) findViewById(R.id.button_view);
		buttonScan=(Button) findViewById(R.id.button_scan);

		// 產生實體WifiManager類別對象
		mWiFiManager=(WifiManager) getSystemService(WIFI_SERVICE);

		// 檢查Wi-Fi是否已經開啟，如未開啟顯示提示對話方塊
		if (!mWiFiManager.isWifiEnabled()) {
			showDialog(DIALOG_WIFI_DISABLED);
		}

		// 註冊“查看已連結的Wi-fi網路資訊”按鈕按一下事件
		buttonView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 顯示當前已經連結的Wifi網路資訊
				getConnectedWifiInfo();
			}
		});

		// 註冊“掃描Wi-Fi網路熱點”按鈕按一下事件
		buttonScan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 跳轉並顯示當前搜索到的Wi-Fi網路熱點
				GotoScanActivity();
			}
		});
	}

	/**
	 * 顯示對話方塊
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// 如果當前系統Wi-Fi功能未開啟，顯示對話方塊詢問使用者是否開啟Wi-Fi功能
		case DIALOG_WIFI_DISABLED:
			return CommonUtils.genDialog(MainActivity.this,
					getString(R.string.app_name),
					getString(R.string.disabled_wifi_message),
					android.R.drawable.ic_dialog_alert, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 執行開啟Wi-Fi功能，並以Toast方式提示是否開啟成功
							if (mWiFiManager.setWifiEnabled(true)) {
								Toast.makeText(
										MainActivity.this,
										getString(R.string.enable_wifi_success),
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(
										MainActivity.this,
										getString(R.string.enable_wifi_failure),
										Toast.LENGTH_LONG).show();
							}

						}

					}, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}

					});
		// 如果當前系統未連結到Wi-Fi網路，顯示對話方塊詢問使用者是否進行Wi-Fi連結操作
		case DIALOG_WIFI_NONE_CONNECTED:
			return CommonUtils.genDialog(MainActivity.this,
					getString(R.string.app_name),
					getString(R.string.none_connected_message),
					android.R.drawable.ic_dialog_alert, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 呼叫系統自帶的Wi-Fi連結Activity
							Intent intent=new Intent(
									WifiManager.ACTION_PICK_WIFI_NETWORK);
							startActivity(intent);
						}

					}, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		default:
			return null;
		}
	}

	/**
	 * 顯示當前已經連結的Wi-fi網路資訊
	 */
	private void getConnectedWifiInfo() {
		// 獲取當前已經連結的Wi-Fi網路資訊
		WifiInfo wi=mWiFiManager.getConnectionInfo();
		
		// 構造用於顯示的字串資料
		if (wi != null) {
			StringBuffer sb=new StringBuffer();
			// 獲取當前Wi-Fi的網路連結ID值
			sb.append(getString(R.string.wifi_info_netId)).append(EMPATY_SPAN)
					.append(wi.getNetworkId()).append("\n");
			// 獲取當前Wi-Fi的名稱
			sb.append(getString(R.string.wifi_info_ssid)).append(EMPATY_SPAN)
					.append(wi.getSSID()).append("\n");
			// 獲取當前Wi-Fi的接入點地址
			sb.append(getString(R.string.wifi_info_bssid)).append(EMPATY_SPAN)
					.append(wi.getBSSID()).append("\n");
			// 獲取當前Wi-Fi的物理位址
			sb.append(getString(R.string.wifi_info_mac)).append(EMPATY_SPAN)
					.append(wi.getMacAddress()).append("\n");
			// 獲取當前Wi-Fi的信號強度
			sb.append(getString(R.string.wifi_info_rssi)).append(EMPATY_SPAN)
					.append(wi.getRssi()).append(" dBm").append("\n");
			// 獲取當前Wi-Fi的連線速度
			sb.append(getString(R.string.wifi_info_linkspeed))
					.append(EMPATY_SPAN).append(wi.getLinkSpeed())
					.append(WifiInfo.LINK_SPEED_UNITS).append("\n");
			// 獲取當前Wi-Fi是否有隱藏接入名稱
			sb.append(getString(R.string.wifi_info_hidden_ssid))
					.append(EMPATY_SPAN)
					.append(wi.getHiddenSSID() ? getString(R.string.yes)
							: getString(R.string.no)).append("\n");
			// 獲取當前Wi-Fi的IP地址
			sb.append(getString(R.string.wifi_info_ipaddr)).append(EMPATY_SPAN)
					.append(getLongIpToString(wi.getIpAddress())).append("\n");
			// 獲取當前Wi-Fi的請求狀態
			sb.append(getString(R.string.wifi_info_supplicant))
					.append(EMPATY_SPAN)
					.append(wi.getSupplicantState().toString()).append("\n");

			// 以對話方塊形式顯示網路資訊
			CommonUtils.genDialog(MainActivity.this,
					getString(R.string.app_name), sb.toString(),
					android.R.drawable.ic_dialog_info, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}

					}, null).show();
			
		} else {
			showDialog(DIALOG_WIFI_NONE_CONNECTED);
		}
	}

	/**
	 * 轉到Wi-Fi掃描結果介面Activity
	 */
	private void GotoScanActivity() {
		Intent intent=new Intent(MainActivity.this, WifiListActivity.class);
		startActivity(intent);
	}

	/**
	 * 將長整型IP位址轉化為字串型的IP位址
	 * @param ip 長整型IP地址
	 * @return String 字串型的IP位址，如127.0.0.1
	 */
	private String getLongIpToString(long ip) {
		int[] b=new int[4];
		b[0]=(int) ((ip>>24) & 0xff);
		b[1]=(int) ((ip>>16) & 0xff);
		b[2]=(int) ((ip>>8) & 0xff);
		b[3]=(int) (ip & 0xff);
		String x=new StringBuffer().append(Integer.toString(b[3])).append(".")
				.append(Integer.toString(b[2])).append(".")
				.append(Integer.toString(b[1])).append(".")
				.append(Integer.toString(b[0])).toString();

		return x;
	}
}
