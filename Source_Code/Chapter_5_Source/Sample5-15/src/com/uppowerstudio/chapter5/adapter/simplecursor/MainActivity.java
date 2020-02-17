package com.uppowerstudio.chapter5.adapter.simplecursor;

import com.uppowerstudio.chapter5.adapter.simplecursor.database.GoodsService;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;

/**
 * SimpleCursorAdapter範例
 * @author UPPower Studio
 *
 */
public class MainActivity extends ListActivity {
	// 宣告Service物件變數
	private GoodsService goodsService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    /*
     * 初始化ListView
     */
	private void initView() {
		// 新增Service對象
		goodsService=new GoodsService(getBaseContext());
		// 獲取商品資訊
		Cursor c=goodsService.getGoods();
		// 新增自訂的Adapter
		GoodsAdapter adapter=new GoodsAdapter(this, R.layout.item, c, 
				new String[]{}, new int[]{});
		// 將此Adapter對象設置為此ListView的資料來源
		setListAdapter(adapter);
	}
}
