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
	
	// 宣告介面控制項變數
	private TextView bluetoothStatus;
	private Button buttonEnableBluetooth;
	private Button buttonDisableBluetooth;
	private Button buttonSearchBluetooth;
	private Button buttonCancelSearch;
	
	// 宣告藍牙配接器變數
	private BluetoothAdapter baAdapter;
	
	// 定義用於儲存搜尋到的藍牙設備
	private List<BluetoothDevice> queriedBluetoothDevices = new ArrayList<BluetoothDevice>();

	// 定義消息處理變數
	private Handler handler=new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 介面增加進度顯示框
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);

		// 初始化介面控制項
		bluetoothStatus=(TextView) findViewById(R.id.bluetooth_current_status);
		buttonEnableBluetooth=(Button) findViewById(R.id.button_enable);
		buttonDisableBluetooth=(Button) findViewById(R.id.button_disable);
		buttonSearchBluetooth=(Button) findViewById(R.id.button_search);
		buttonCancelSearch=(Button) findViewById(R.id.button_cancel);
		
		// 獲取藍牙配接器
		baAdapter=BluetoothAdapter.getDefaultAdapter();

		// 初始化按鈕狀態
		initControlStatus();

		// 註冊完成搜尋藍牙設備廣播接收器
		IntentFilter searchFilter=new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(searchDeviceFinishReceiver, searchFilter);

		// 註冊發現藍牙設備廣播接收器
		IntentFilter foundFilter=new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		registerReceiver(foundBluetoothDeviceReceiver, foundFilter);

		// 註冊打開藍牙按鈕按一下事件監聽器
		buttonEnableBluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 打開藍牙操作
				Intent intent=new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(intent, 1);
			}
		});

		// 註冊關閉藍牙按鈕按一下事件監聽器
		buttonDisableBluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 關閉藍牙
				closeBluetooth();
			}
		});

		// 註冊搜尋藍牙按鈕按一下事件監聽器
		buttonSearchBluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 開始執行搜尋
				new Thread(searchDevicesJob).start();
				// 顯示進度框
				setProgressBarIndeterminateVisibility(true);
			}
		});

		// 註冊取消搜尋藍牙按鈕按一下事件監聽器
		buttonCancelSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 執行取消搜尋操作
				baAdapter.cancelDiscovery();
				
				// 取消進度框顯示
				setProgressBarIndeterminateVisibility(false);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 打開藍牙後更新按鈕及顯示狀態
		initControlStatus();
	}

	private void initControlStatus() {
		// 判斷當前藍牙是否開啟
		if (baAdapter.isEnabled()) {
			// 設置狀態顯示
			bluetoothStatus.setText(getString(R.string.bluetooth_enabled));
			
			// 設置按鈕的狀態，當藍牙開啟時，打開按鈕不可用
			buttonEnableBluetooth.setEnabled(false);
			buttonDisableBluetooth.setEnabled(true);
			buttonSearchBluetooth.setEnabled(true);
			buttonCancelSearch.setEnabled(true);
		} else {
			bluetoothStatus.setText(getString(R.string.bluetooth_disabled));
			
			// 設置按鈕的狀態，當藍牙關閉時，關閉、搜尋及取消按鈕不可用
			buttonEnableBluetooth.setEnabled(true);
			buttonDisableBluetooth.setEnabled(false);
			buttonSearchBluetooth.setEnabled(false);
			buttonCancelSearch.setEnabled(false);
		}
	}

	/**
	 * 關閉藍牙
	 */
	private void closeBluetooth() {
		// 取消進度框顯示
		setProgressBarIndeterminateVisibility(false);
		
		// 呼叫配接器方法進行關閉操作
		baAdapter.disable();

		// 控制按鈕及ListView顯示
		initControlStatus();
		setListAdapter(null);
		
		// 解除已經註冊的廣播監聽器
		unregisterReceiver(foundBluetoothDeviceReceiver);
		unregisterReceiver(searchDeviceFinishReceiver);
	}

	/**
	 * 定義執行搜尋藍牙操作的任務
	 */
	private Runnable searchDevicesJob=new Runnable() {
		@Override
		public void run() {
			// 執行搜尋
			baAdapter.startDiscovery();
		}
	};

	/**
	 * 定義用於發現藍牙設備後進行消息接收的接收器
	 */
	private BroadcastReceiver foundBluetoothDeviceReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 獲取設備詳細資訊
			BluetoothDevice device=intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			// 添加到列表中
			queriedBluetoothDevices.add(device);
			
			// 顯示搜尋到的設備資訊
			displayDiscoveryDevices();
		}
	};

	/**
	 * 定義用於完成藍牙設備搜尋的接收器
	 */
	private BroadcastReceiver searchDeviceFinishReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 刪除已經註冊的廣播接收器
			unregisterReceiver(foundBluetoothDeviceReceiver);
			unregisterReceiver(this);
			
			// 取消顯示進度框
			setProgressBarIndeterminateVisibility(false);
		}
	};

	/**
	 * 顯示搜尋到的設備資訊
	 */
	private void displayDiscoveryDevices() {
		List<String> itemList=new ArrayList<String>();
		
		// 構建用於顯示的資料
		for (BluetoothDevice device : queriedBluetoothDevices) {
			StringBuffer sb=new StringBuffer();
			// 獲取設備的名稱及位址
			sb.append(getString(R.string.item_name_prefix))
					.append(isEmptyString(device.getName()) ? getString(R.string.no_device_name)
							: device.getName()).append("\n")
					.append(getString(R.string.item_address_prefix))
					.append(device.getAddress());
			// 添加到顯示清單中
			itemList.add(sb.toString());
		}
		
		// 構造資料配接器
		final ArrayAdapter<String> listAdapter=new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_list_item_1,
				itemList);
		
		// 通過UI執行緒顯示發現設備清單資料
		handler.post(new Runnable() {
			@Override
			public void run() {
				setListAdapter(listAdapter);
			}
		});

	}

	/**
	 * 判斷字串是否為空
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
