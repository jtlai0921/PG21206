package com.uppowerstudio.chapter6.bluetooth;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	
	// �ŧi��������ܼ�
	private TextView bluetoothStatus;
	private Button buttonEnableBluetooth;
	private Button buttonDisableBluetooth;
	private Button buttonSearchBluetooth;
	private Button buttonCancelSearch;
	
	// �ŧi�Ť��t�����ܼ�
	private BluetoothAdapter baAdapter;
	
	// �w�q�Ω��x�s�j�M�쪺�Ť��]��
	private List<BluetoothDevice> queriedBluetoothDevices = new ArrayList<BluetoothDevice>();

	// �w�q�����B�z�ܼ�
	private Handler handler=new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// �����W�[�i����ܮ�
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		bluetoothStatus=(TextView) findViewById(R.id.bluetooth_current_status);
		buttonEnableBluetooth=(Button) findViewById(R.id.button_enable);
		buttonDisableBluetooth=(Button) findViewById(R.id.button_disable);
		buttonSearchBluetooth=(Button) findViewById(R.id.button_search);
		buttonCancelSearch=(Button) findViewById(R.id.button_cancel);
		
		// ����Ť��t����
		baAdapter=BluetoothAdapter.getDefaultAdapter();

		// ��l�ƫ��s���A
		initControlStatus();

		// ���U�����j�M�Ť��]�Ƽs��������
		IntentFilter searchFilter=new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(searchDeviceFinishReceiver, searchFilter);

		// ���U�o�{�Ť��]�Ƽs��������
		IntentFilter foundFilter=new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		registerReceiver(foundBluetoothDeviceReceiver, foundFilter);

		// ���U���}�Ť����s���@�U�ƥ��ť��
		buttonEnableBluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���}�Ť��ާ@
				Intent intent=new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(intent, 1);
			}
		});

		// ���U�����Ť����s���@�U�ƥ��ť��
		buttonDisableBluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �����Ť�
				closeBluetooth();
			}
		});

		// ���U�j�M�Ť����s���@�U�ƥ��ť��
		buttonSearchBluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �}�l����j�M
				new Thread(searchDevicesJob).start();
				// ��ܶi�׮�
				setProgressBarIndeterminateVisibility(true);
			}
		});

		// ���U�����j�M�Ť����s���@�U�ƥ��ť��
		buttonCancelSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��������j�M�ާ@
				baAdapter.cancelDiscovery();
				
				// �����i�׮����
				setProgressBarIndeterminateVisibility(false);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ���}�Ť����s���s����ܪ��A
		initControlStatus();
	}

	private void initControlStatus() {
		// �P�_��e�Ť��O�_�}��
		if (baAdapter.isEnabled()) {
			// �]�m���A���
			bluetoothStatus.setText(getString(R.string.bluetooth_enabled));
			
			// �]�m���s�����A�A���Ť��}�ҮɡA���}���s���i��
			buttonEnableBluetooth.setEnabled(false);
			buttonDisableBluetooth.setEnabled(true);
			buttonSearchBluetooth.setEnabled(true);
			buttonCancelSearch.setEnabled(true);
		} else {
			bluetoothStatus.setText(getString(R.string.bluetooth_disabled));
			
			// �]�m���s�����A�A���Ť������ɡA�����B�j�M�Ψ������s���i��
			buttonEnableBluetooth.setEnabled(true);
			buttonDisableBluetooth.setEnabled(false);
			buttonSearchBluetooth.setEnabled(false);
			buttonCancelSearch.setEnabled(false);
		}
	}

	/**
	 * �����Ť�
	 */
	private void closeBluetooth() {
		// �����i�׮����
		setProgressBarIndeterminateVisibility(false);
		
		// �I�s�t������k�i�������ާ@
		baAdapter.disable();

		// ������s��ListView���
		initControlStatus();
		setListAdapter(null);
		
		// �Ѱ��w�g���U���s����ť��
		unregisterReceiver(foundBluetoothDeviceReceiver);
		unregisterReceiver(searchDeviceFinishReceiver);
	}

	/**
	 * �w�q����j�M�Ť��ާ@������
	 */
	private Runnable searchDevicesJob=new Runnable() {
		@Override
		public void run() {
			// ����j�M
			baAdapter.startDiscovery();
		}
	};

	/**
	 * �w�q�Ω�o�{�Ť��]�ƫ�i�����������������
	 */
	private BroadcastReceiver foundBluetoothDeviceReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// ����]�ƸԲӸ�T
			BluetoothDevice device=intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			// �K�[��C��
			queriedBluetoothDevices.add(device);
			
			// ��ܷj�M�쪺�]�Ƹ�T
			displayDiscoveryDevices();
		}
	};

	/**
	 * �w�q�Ω󧹦��Ť��]�Ʒj�M��������
	 */
	private BroadcastReceiver searchDeviceFinishReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// �R���w�g���U���s��������
			unregisterReceiver(foundBluetoothDeviceReceiver);
			unregisterReceiver(this);
			
			// ������ܶi�׮�
			setProgressBarIndeterminateVisibility(false);
		}
	};

	/**
	 * ��ܷj�M�쪺�]�Ƹ�T
	 */
	private void displayDiscoveryDevices() {
		List<String> itemList=new ArrayList<String>();
		
		// �c�إΩ���ܪ����
		for (BluetoothDevice device : queriedBluetoothDevices) {
			StringBuffer sb=new StringBuffer();
			// ����]�ƪ��W�٤Φ�}
			sb.append(getString(R.string.item_name_prefix))
					.append(isEmptyString(device.getName()) ? getString(R.string.no_device_name)
							: device.getName()).append("\n")
					.append(getString(R.string.item_address_prefix))
					.append(device.getAddress());
			// �K�[����ܲM�椤
			itemList.add(sb.toString());
		}
		
		// �c�y��ưt����
		final ArrayAdapter<String> listAdapter=new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_list_item_1,
				itemList);
		
		// �q�LUI�������ܵo�{�]�ƲM����
		handler.post(new Runnable() {
			@Override
			public void run() {
				setListAdapter(listAdapter);
			}
		});

	}

	/**
	 * �P�_�r��O�_����
	 * @param str
	 * @return
	 */
	private boolean isEmptyString(String str) {
		if (str != null) {
			if ("".equals(str)) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}
}
