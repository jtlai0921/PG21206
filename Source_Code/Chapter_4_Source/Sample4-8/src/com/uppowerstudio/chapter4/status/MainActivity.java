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
 * ���A�d��
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
				// ��oTelephonyManager��H
				TelephonyManager telephonyManager=(TelephonyManager) getSystemService
				(Service.TELEPHONY_SERVICE);

				// ����q�H������O
				String teleCode=telephonyManager.getNetworkCountryIso();
				// ����q�H�������q�N�X
				String teleComCode=telephonyManager.getNetworkOperator();
				// ����q�H�������q�W��
				String teleComName=telephonyManager.getNetworkOperatorName();
				// �����ʳq�H����
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
				// �����������
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
				// ������C���A
				boolean roamStatusCode=telephonyManager.isNetworkRoaming();
				String roamStatus="NOT ROAMING";
				if (roamStatusCode) {
					roamStatus="ROAMING";
				}
				// �������ߤ@����
				String imei=telephonyManager.getDeviceId();
				// ������IMEI�n�骩��
				String imeiSV=telephonyManager.getDeviceSoftwareVersion();
				// ������IMSI
				String imsi=telephonyManager.getSubscriberId();

				// �ŪުA��
				String statusCode=android.provider.Settings.System.getString(
						cr, android.provider.Settings.System.BLUETOOTH_ON);
				String buletoothStatus="";
				if (statusCode.equals("1")) {
					// �}�Ҫ��A
					buletoothStatus="ENABLED";
				} else {
					// �T�Ϊ��A
					buletoothStatus="DISABLED";
				}

				// ����Ҧ��O�_���}
				statusCode=android.provider.Settings.System.getString(cr,
						android.provider.Settings.System.AIRPLANE_MODE_ON);
				String AirplaneStatus="";
				if (statusCode.equals("1")) {
					// �}�Ҫ��A
					AirplaneStatus="ENABLED";
				} else {
					// �T�Ϊ��A
					AirplaneStatus="DISABLED";
				}

				// ��ƺ��C�Ҧ��O�_���}
				statusCode=android.provider.Settings.System.getString(cr,
						android.provider.Settings.System.DATA_ROAMING);
				String dataRoamStatus="";
				if (statusCode.equals("1")) {
					// �}�Ҫ��A
					dataRoamStatus="ENABLED";
				} else {
					// �T�Ϊ��A
					dataRoamStatus="DISABLED";
				}

				// �bTextView����X�榡�Ƥ��᪺��T
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
