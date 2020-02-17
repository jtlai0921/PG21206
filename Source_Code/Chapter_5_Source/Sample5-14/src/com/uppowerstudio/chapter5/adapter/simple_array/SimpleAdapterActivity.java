package com.uppowerstudio.chapter5.adapter.simple_array;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

/**
 * SimpleAdapter
 * @author UPPower Studio
 *
 */
public class SimpleAdapterActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_adapter_list);
		initListView();
	}

	/**
	 * 初始化ArrayAdapter
	 */
	private void initListView() {
		// 新增10條資料
		String[] data1=new String[10];
		for (int i=0; i < 10; i++) {
			data1[i]="data1_text"+i;
		}

		String[] data2=new String[10];
		for (int i=0; i < 10; i++) {
			data2[i]="data2_text"+i;
		}

		ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
		for (int i=0; i < 10; i++) {
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("text01", data1[i]);
			map.put("text02", data2[i]);
			list.add(map);
		}

		// 建構ArrayAdapter物件
		SimpleAdapter simpleAdapter=new SimpleAdapter(this, list,
				R.layout.simple_adapter_item,
				new String[] { "text01", "text02" }, new int[] {
						R.id.row_text02, R.id.row_text01 });

		// 將此Adapter對象設置為此ListView的資料來源
		setListAdapter(simpleAdapter);
	}
}
