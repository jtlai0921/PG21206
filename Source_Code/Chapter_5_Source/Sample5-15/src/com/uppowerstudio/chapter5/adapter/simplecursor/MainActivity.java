package com.uppowerstudio.chapter5.adapter.simplecursor;

import com.uppowerstudio.chapter5.adapter.simplecursor.database.GoodsService;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;

/**
 * SimpleCursorAdapter�d��
 * @author UPPower Studio
 *
 */
public class MainActivity extends ListActivity {
	// �ŧiService�����ܼ�
	private GoodsService goodsService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    /*
     * ��l��ListView
     */
	private void initView() {
		// �s�WService��H
		goodsService=new GoodsService(getBaseContext());
		// ����ӫ~��T
		Cursor c=goodsService.getGoods();
		// �s�W�ۭq��Adapter
		GoodsAdapter adapter=new GoodsAdapter(this, R.layout.item, c, 
				new String[]{}, new int[]{});
		// �N��Adapter��H�]�m����ListView����ƨӷ�
		setListAdapter(adapter);
	}
}
