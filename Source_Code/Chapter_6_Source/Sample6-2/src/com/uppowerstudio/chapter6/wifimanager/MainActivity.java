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
 * Wi-Fi������T�d�ݽd��
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {

	// �w�q�`�q
	private static final String EMPATY_SPAN=" ";
	
	private static final int DIALOG_WIFI_DISABLED=1;
	private static final int DIALOG_WIFI_NONE_CONNECTED=3;

	// �ŧiWi-Fi�޲z���O�ܼ�
	private WifiManager mWiFiManager;
	
	// �ŧiButton����ܼ�
	private Button buttonView;
	private Button buttonScan;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�ƫ��s
		buttonView=(Button) findViewById(R.id.button_view);
		buttonScan=(Button) findViewById(R.id.button_scan);

		// ���͹���WifiManager���O��H
		mWiFiManager=(WifiManager) getSystemService(WIFI_SERVICE);

		// �ˬdWi-Fi�O�_�w�g�}�ҡA�p���}����ܴ��ܹ�ܤ��
		if (!mWiFiManager.isWifiEnabled()) {
			showDialog(DIALOG_WIFI_DISABLED);
		}

		// ���U���d�ݤw�s����Wi-fi������T�����s���@�U�ƥ�
		buttonView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��ܷ�e�w�g�s����Wifi������T
				getConnectedWifiInfo();
			}
		});

		// ���U�����yWi-Fi�������I�����s���@�U�ƥ�
		buttonScan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// �������ܷ�e�j���쪺Wi-Fi�������I
				GotoScanActivity();
			}
		});
	}

	/**
	 * ��ܹ�ܤ��
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// �p�G��e�t��Wi-Fi�\�ॼ�}�ҡA��ܹ�ܤ���߰ݨϥΪ̬O�_�}��Wi-Fi�\��
		case DIALOG_WIFI_DISABLED:
			return CommonUtils.genDialog(MainActivity.this,
					getString(R.string.app_name),
					getString(R.string.disabled_wifi_message),
					android.R.drawable.ic_dialog_alert, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// ����}��Wi-Fi�\��A�åHToast�覡���ܬO�_�}�Ҧ��\
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
		// �p�G��e�t�Υ��s����Wi-Fi�����A��ܹ�ܤ���߰ݨϥΪ̬O�_�i��Wi-Fi�s���ާ@
		case DIALOG_WIFI_NONE_CONNECTED:
			return CommonUtils.genDialog(MainActivity.this,
					getString(R.string.app_name),
					getString(R.string.none_connected_message),
					android.R.drawable.ic_dialog_alert, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// �I�s�t�Φ۱a��Wi-Fi�s��Activity
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
	 * ��ܷ�e�w�g�s����Wi-fi������T
	 */
	private void getConnectedWifiInfo() {
		// �����e�w�g�s����Wi-Fi������T
		WifiInfo wi=mWiFiManager.getConnectionInfo();
		
		// �c�y�Ω���ܪ��r����
		if (wi != null) {
			StringBuffer sb=new StringBuffer();
			// �����eWi-Fi�������s��ID��
			sb.append(getString(R.string.wifi_info_netId)).append(EMPATY_SPAN)
					.append(wi.getNetworkId()).append("\n");
			// �����eWi-Fi���W��
			sb.append(getString(R.string.wifi_info_ssid)).append(EMPATY_SPAN)
					.append(wi.getSSID()).append("\n");
			// �����eWi-Fi�����J�I�a�}
			sb.append(getString(R.string.wifi_info_bssid)).append(EMPATY_SPAN)
					.append(wi.getBSSID()).append("\n");
			// �����eWi-Fi�����z��}
			sb.append(getString(R.string.wifi_info_mac)).append(EMPATY_SPAN)
					.append(wi.getMacAddress()).append("\n");
			// �����eWi-Fi���H���j��
			sb.append(getString(R.string.wifi_info_rssi)).append(EMPATY_SPAN)
					.append(wi.getRssi()).append(" dBm").append("\n");
			// �����eWi-Fi���s�u�t��
			sb.append(getString(R.string.wifi_info_linkspeed))
					.append(EMPATY_SPAN).append(wi.getLinkSpeed())
					.append(WifiInfo.LINK_SPEED_UNITS).append("\n");
			// �����eWi-Fi�O�_�����ñ��J�W��
			sb.append(getString(R.string.wifi_info_hidden_ssid))
					.append(EMPATY_SPAN)
					.append(wi.getHiddenSSID() ? getString(R.string.yes)
							: getString(R.string.no)).append("\n");
			// �����eWi-Fi��IP�a�}
			sb.append(getString(R.string.wifi_info_ipaddr)).append(EMPATY_SPAN)
					.append(getLongIpToString(wi.getIpAddress())).append("\n");
			// �����eWi-Fi���ШD���A
			sb.append(getString(R.string.wifi_info_supplicant))
					.append(EMPATY_SPAN)
					.append(wi.getSupplicantState().toString()).append("\n");

			// �H��ܤ���Φ���ܺ�����T
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
	 * ���Wi-Fi���y���G����Activity
	 */
	private void GotoScanActivity() {
		Intent intent=new Intent(MainActivity.this, WifiListActivity.class);
		startActivity(intent);
	}

	/**
	 * �N���㫬IP��}��Ƭ��r�ꫬ��IP��}
	 * @param ip ���㫬IP�a�}
	 * @return String �r�ꫬ��IP��}�A�p127.0.0.1
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
