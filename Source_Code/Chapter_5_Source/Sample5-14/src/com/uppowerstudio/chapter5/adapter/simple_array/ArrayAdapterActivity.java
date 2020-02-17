package com.uppowerstudio.chapter5.adapter.simple_array;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * ArrayAdapter
 * @author UPPower Studio
 *
 */
public class ArrayAdapterActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.array_adapter_list);
		initListView();
	}

	/**
	 * 初始化ListView
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initListView() {
		// 新增10條資料
		String[] data=new String[10];
		for (int i=0; i < 10; i++) {
			data[i]="text"+i;
		}
		// 建構ArrayAdapter物件
		ArrayAdapter arrayAdapter=new ArrayAdapter(this,
				R.layout.array_adapter_item, R.id.row_text, data);
	
		// 將此Adapter對象設置為ListView的資料來源
		setListAdapter(arrayAdapter);
	}
}
