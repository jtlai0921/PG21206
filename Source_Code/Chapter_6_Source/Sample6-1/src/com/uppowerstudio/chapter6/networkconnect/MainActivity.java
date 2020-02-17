package com.uppowerstudio.chapter6.networkconnect;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	// �ŧi����ܼ�
	private Button queryButton;
	private TextView txtAvailable;
	private TextView txtNetworkType;
	private TextView txtNetworkSubType;
	private TextView txtNetworkStatus;
	private TextView txtNetworkDetailStatus;
	private TextView txtNetworkExtra;
	private TextView txtNetworkRoaming;
	
	private ConnectivityManager connect;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ���J�G����main.xml
		setContentView(R.layout.main);

		// ��l�Ʊ��
		queryButton=(Button) findViewById(R.id.button_query);
		txtAvailable=(TextView) findViewById(R.id.network_available);
		txtNetworkType=(TextView) findViewById(R.id.network_type);
		txtNetworkSubType=(TextView) findViewById(R.id.network_subtype);
		txtNetworkStatus=(TextView) findViewById(R.id.network_status);
		txtNetworkDetailStatus=(TextView) findViewById(R.id.network_status_detail);
		txtNetworkExtra=(TextView) findViewById(R.id.network_extra);
		txtNetworkRoaming=(TextView) findViewById(R.id.network_roaming);

		// ���͹���ConnectivityManager��H
		connect=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// �]�m��ť����ť���s���@�U�ƥ�
		queryButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �����e�i�κ�����������T���
				NetworkInfo networkInfo=connect.getActiveNetworkInfo();
				
				if (networkInfo != null){
					// �q�LisAvilable��k��������O�_�i�Ϊ����A
					txtAvailable.setText(networkInfo.isAvailable() ? getString(R.
					string.yes) : getString(R.string.no));
					
					// �����e�����s��������
					txtNetworkType.setText(networkInfo.getTypeName());
					
					// �����e�����s�����l�����]�p�G�s�b�^
					txtNetworkSubType.setText(networkInfo.getSubtypeName());
					
					// ��������s�����ʲ����A
					txtNetworkStatus.setText(networkInfo.getState().toString());
					
					// ��������s�����ԲӪ��A
					txtNetworkDetailStatus.setText(networkInfo.getDetailedState().toString());
					
					// ��������s�����B�~���[��T
					txtNetworkExtra.setText(networkInfo.getExtraInfo());
					
					// �q�LisRoaming��k�P�_��e�O�_�B���ƺ��C���A
					txtNetworkRoaming.setText(networkInfo.isRoaming() ? getString(R.string.yes) : getString(R.string.no));
				}else{
					// �p�G�S���i�Ϊ������s���A��������
					txtAvailable.setText(getString(R.string.network_unavailable_msg));
				}
				
			}
			
		});
	}
}
