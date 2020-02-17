package com.uppowerstudio.chapter8.geocoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 通過經緯度尋找位置程式碼
 * @author UPPower Studio
 *
 */
public class ReverseActivity extends Activity {
	
	// 檢查經緯度合法性的規則運算式
	private static final String LNG_LAT_REGEX="-?\\d+(\\.\\d+)?";

	// 宣告介面控制項變數
	private EditText longitude;
	private EditText latitude;
	private Button buttonReverseSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reverse);

		// 初始化介面控制項
		longitude=(EditText) findViewById(R.id.txt_longitude);
		latitude=(EditText) findViewById(R.id.txt_latitude);
		buttonReverseSearch=(Button) findViewById(R.id.button_reverse_search);

		// 註冊按鈕點擊事件
		buttonReverseSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				forwardSearch();
			}
		});
	}

	/**
	 * 回傳數據
	 */
	private void forwardSearch() {
		if (validate()) {
			Intent intent=new Intent();
			// 設置回傳資料類型
			intent.putExtra("return_type", "reverse");
			// 設置經度數據
			intent.putExtra("longitude", longitude.getText().toString().trim());
			// 設置緯度數據
			intent.putExtra("latitude", latitude.getText().toString().trim());
			// 設置返回值程式碼
			setResult(RESULT_OK, intent);
			finish();
		}
	}

	/**
	 * 驗證輸入經緯度的合法性
	 * @return
	 */
	private boolean validate() {
		boolean valid=true;
		
		// 獲取數值
		String strLng=longitude.getText().toString().trim();
		String strLat=latitude.getText().toString().trim();
		
		// 驗證不能為空
		if (strLng.length() == 0) {
			longitude.setError(getString(R.string.common_error_msg_required));
			valid=false;
		} else if (strLng.matches(LNG_LAT_REGEX)) { // 驗證資料的有效性
			if (Double.parseDouble(strLng) < -180
					|| Double.parseDouble(strLng) > 180) {
				longitude
						.setError(getString(R.string.common_error_msg_wrong_longtitude));
				valid=false;
			}
		} else {
			longitude
					.setError(getString(R.string.common_error_msg_wrong_longtitude));
			valid=false;
		}

		if (strLat.length() == 0) {
			latitude.setError(getString(R.string.common_error_msg_required));
			valid=false;
		} else if (strLat.matches(LNG_LAT_REGEX)) {
			if (Double.parseDouble(strLat) < -90
					|| Double.parseDouble(strLat) > 90) {
				latitude.setError(getString(R.string.common_error_msg_wrong_latitude));
				valid=false;
			}
		} else {
			latitude.setError(getString(R.string.common_error_msg_wrong_latitude));
			valid=false;
		}

		return valid;
	}
}
