package com.uppowerstudio.chapter8.geocoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 通過位置尋找經緯度介面程式碼
 * 
 * @author UPPower Studio
 * 
 */
public class ForwardActivity extends Activity {

	// 宣告介面控制項
	private EditText inputLocation;
	private Button buttonForwardSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forward);

		// 初始化介面控制項
		inputLocation=(EditText) findViewById(R.id.txt_location);
		buttonForwardSearch=(Button) findViewById(R.id.button_forward_search);

		// 註冊按鈕點擊事件
		buttonForwardSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validate()) {
					Intent intent=new Intent();
					// 設置回傳資料類型
					intent.putExtra("return_type", "forward");
					// 設置具體位置資料
					intent.putExtra("input_location", inputLocation.getText()
							.toString().trim());
					// 設置返回值程式碼
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
	}

	/**
	 * 驗證位址輸入不能為空
	 * 
	 * @return
	 */
	private boolean validate() {
		boolean valid=true;

		String location=inputLocation.getText().toString().trim();

		if (location.length()==0) {
			inputLocation
					.setError(getString(R.string.common_error_msg_required));
			valid=false;
		}

		return valid;
	}
}
