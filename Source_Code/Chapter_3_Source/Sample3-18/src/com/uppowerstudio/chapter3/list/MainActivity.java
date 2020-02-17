package com.uppowerstudio.chapter3.list;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 列表範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 獲取資料來源
		String[] items=getResources().getStringArray(R.array.items);

		// 獲取ListView元件
		ListView list=getListView();

		// 以兩個TextView元件作為ListView的頭部和底部，如不需要頭部和底部，可不新增這兩個			// TextView
		TextView header=new TextView(this);
		header.setText(R.string.header);
		header.setGravity(Gravity.CENTER);
		header.setTextSize(28);
		TextView footer=new TextView(this);
		footer.setText(R.string.footer);
		footer.setGravity(Gravity.CENTER);
		footer.setTextSize(28);
		list.addHeaderView(header);
		list.addFooterView(footer);

		// 設置資料配接器，將Android系統內建的佈局android.R.layout.simple_list_item_1和		// 資料來源進行綁定，並將此系統內建佈局設為ListView每一行記錄的佈局
		setListAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, items));

		// 註冊單次點擊事件，單次點擊時顯示被單次點擊記錄所在位置
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, String.valueOf(position), 1)
						.show();
			}
		});

	}
}
