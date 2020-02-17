package com.uppowerstudio.chapter3.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 下拉清單範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button countButton;
	private Button txtButton;
	private Spinner spinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		spinner=(Spinner) findViewById(R.id.spinner);

		// 初始化元件
		countButton=(Button) findViewById(R.id.btn_count);
		countButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 單次點擊按鈕時顯示選項總數
				Toast.makeText(MainActivity.this,
						String.valueOf(spinner.getCount()), Toast.LENGTH_LONG)
						.show();
			}
		});

		txtButton=(Button) findViewById(R.id.btn_txt);
		txtButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 單次點擊按鈕時顯示選項
				Toast.makeText(MainActivity.this,
						String.valueOf(spinner.getSelectedItem()),
						Toast.LENGTH_LONG).show();
			}
		});
	}
}
