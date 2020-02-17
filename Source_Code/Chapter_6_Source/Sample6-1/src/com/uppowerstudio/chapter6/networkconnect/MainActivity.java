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

	// 宣告控制項變數
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

		// 載入佈局檔main.xml
		setContentView(R.layout.main);

		// 初始化控制項
		queryButton=(Button) findViewById(R.id.button_query);
		txtAvailable=(TextView) findViewById(R.id.network_available);
		txtNetworkType=(TextView) findViewById(R.id.network_type);
		txtNetworkSubType=(TextView) findViewById(R.id.network_subtype);
		txtNetworkStatus=(TextView) findViewById(R.id.network_status);
		txtNetworkDetailStatus=(TextView) findViewById(R.id.network_status_detail);
		txtNetworkExtra=(TextView) findViewById(R.id.network_extra);
		txtNetworkRoaming=(TextView) findViewById(R.id.network_roaming);

		// 產生實體ConnectivityManager對象
		connect=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// 設置監聽器監聽按鈕按一下事件
		queryButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 獲取當前可用網路的網路資訊資料
				NetworkInfo networkInfo=connect.getActiveNetworkInfo();
				
				if (networkInfo != null){
					// 通過isAvilable方法獲取網路是否可用的狀態
					txtAvailable.setText(networkInfo.isAvailable() ? getString(R.
					string.yes) : getString(R.string.no));
					
					// 獲取當前網路連結的類型
					txtNetworkType.setText(networkInfo.getTypeName());
					
					// 獲取當前網路連結的子類型（如果存在）
					txtNetworkSubType.setText(networkInfo.getSubtypeName());
					
					// 獲取網路連結的粗略狀態
					txtNetworkStatus.setText(networkInfo.getState().toString());
					
					// 獲取網路連結的詳細狀態
					txtNetworkDetailStatus.setText(networkInfo.getDetailedState().toString());
					
					// 獲取網路連結的額外附加資訊
					txtNetworkExtra.setText(networkInfo.getExtraInfo());
					
					// 通過isRoaming方法判斷當前是否處於資料漫遊狀態
					txtNetworkRoaming.setText(networkInfo.isRoaming() ? getString(R.string.yes) : getString(R.string.no));
				}else{
					// 如果沒有可用的網路連結，給予提示
					txtAvailable.setText(getString(R.string.network_unavailable_msg));
				}
				
			}
			
		});
	}
}
