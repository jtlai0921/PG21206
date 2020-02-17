package com.uppowerstudio.chapter4.status;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 狀態範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private ContentResolver cr;
	private Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		cr=getContentResolver();

		btn=(Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 獲得TelephonyManager對象
				TelephonyManager telephonyManager=(TelephonyManager) getSystemService
				(Service.TELEPHONY_SERVICE);

				// 獲取電信網路國別
				String teleCode=telephonyManager.getNetworkCountryIso();
				// 獲取電信網路公司代碼
				String teleComCode=telephonyManager.getNetworkOperator();
				// 獲取電信網路公司名稱
				String teleComName=telephonyManager.getNetworkOperatorName();
				// 獲取行動通信類型
				int typeCode=telephonyManager.getPhoneType();
				String type="";
				switch (typeCode) {
				case TelephonyManager.PHONE_TYPE_NONE:
					type="PHONE_TYPE_NONE";
					break;
				case TelephonyManager.PHONE_TYPE_GSM:
					type="PHONE_TYPE_GSM";
					break;
				case TelephonyManager.PHONE_TYPE_CDMA:
					type="PHONE_TYPE_CDMA";
					break;
				}
				// 獲取網路類型
				int netTypeCode=telephonyManager.getNetworkType();
				String netType="NETWORK_TYPE_UNKNOWN";
				switch (netTypeCode) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
					netType="NETWORK_TYPE_GPRS";
					break;
				case TelephonyManager.NETWORK_TYPE_EDGE:
					netType="NETWORK_TYPE_EDGE";
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
					netType="NETWORK_TYPE_UMTS";
					break;
				case TelephonyManager.NETWORK_TYPE_HSDPA:
					netType="NETWORK_TYPE_HSDPA";
					break;
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					netType="NETWORK_TYPE_HSUPA";
					break;
				case TelephonyManager.NETWORK_TYPE_HSPA:
					netType="NETWORK_TYPE_HSPA";
					break;
				case TelephonyManager.NETWORK_TYPE_CDMA:
					netType="NETWORK_TYPE_CDMA";
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
					netType="NETWORK_TYPE_EVDO_0";
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
					netType="NETWORK_TYPE_EVDO_A";
					break;
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					netType="NETWORK_TYPE_1xRTT";
					break;
				}
				// 獲取漫遊狀態
				boolean roamStatusCode=telephonyManager.isNetworkRoaming();
				String roamStatus="NOT ROAMING";
				if (roamStatusCode) {
					roamStatus="ROAMING";
				}
				// 獲取手機唯一標識
				String imei=telephonyManager.getDeviceId();
				// 獲取手機IMEI軟體版本
				String imeiSV=telephonyManager.getDeviceSoftwareVersion();
				// 獲取手機IMSI
				String imsi=telephonyManager.getSubscriberId();

				// 藍芽服務
				String statusCode=android.provider.Settings.System.getString(
						cr, android.provider.Settings.System.BLUETOOTH_ON);
				String buletoothStatus="";
				if (statusCode.equals("1")) {
					// 開啟狀態
					buletoothStatus="ENABLED";
				} else {
					// 禁用狀態
					buletoothStatus="DISABLED";
				}

				// 飛行模式是否打開
				statusCode=android.provider.Settings.System.getString(cr,
						android.provider.Settings.System.AIRPLANE_MODE_ON);
				String AirplaneStatus="";
				if (statusCode.equals("1")) {
					// 開啟狀態
					AirplaneStatus="ENABLED";
				} else {
					// 禁用狀態
					AirplaneStatus="DISABLED";
				}

				// 資料漫遊模式是否打開
				statusCode=android.provider.Settings.System.getString(cr,
						android.provider.Settings.System.DATA_ROAMING);
				String dataRoamStatus="";
				if (statusCode.equals("1")) {
					// 開啟狀態
					dataRoamStatus="ENABLED";
				} else {
					// 禁用狀態
					dataRoamStatus="DISABLED";
				}

				// 在TextView中輸出格式化之後的資訊
				TextView txt=(TextView) findViewById(R.id.txt);
				StringBuilder sb=new StringBuilder();
				sb.append("teleCode: "+teleCode+"\n");
				sb.append("teleComCode: "+teleComCode+"\n");
				sb.append("teleComName: "+teleComName+"\n");
				sb.append("type: "+type+"\n");
				sb.append("netType: "+netType+"\n");
				sb.append("roamStatus: "+roamStatus+"\n");
				sb.append("teleComName: "+teleComName+"\n");
				sb.append("imei: "+imei+"\n");
				sb.append("imeiSV: "+imeiSV+"\n");
				sb.append("imsi: "+imsi+"\n");
				sb.append("buletoothStatus: "+buletoothStatus+"\n");
				sb.append("AirplaneStatus: "+AirplaneStatus+"\n");
				sb.append("dataRoamStatus: "+dataRoamStatus+"\n");
				txt.setText(sb.toString());
			}
		});
	}
}
