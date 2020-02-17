package com.uppowerstudio.chapter3.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.Toast;

/**
 * 畫廊範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Gallery gallery;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		gallery=(Gallery) findViewById(R.id.gallery);
		// ImageAdapter
		//是一個資料配接器，其具體程式碼可參考com.uppowerstudio.chapter3.gallery.ImageAdapter
		// 這裡可單純將ImageAdapter理解為一個資料來源，其原理可參考第5章
		ImageAdapter adapter=new ImageAdapter(MainActivity.this);
		gallery.setAdapter(adapter);

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			// 顯示被選取部分所處的位置，顯示資訊持續2秒
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, "第" + (position + 1) + "張", 2)
						.show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
}
