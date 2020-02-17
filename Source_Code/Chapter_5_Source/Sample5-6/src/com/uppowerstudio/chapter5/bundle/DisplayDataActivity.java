package com.uppowerstudio.chapter5.bundle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Bundle資料共用範例
 * @author UPPower Studio
 *
 */
public class DisplayDataActivity extends Activity {
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 載入佈局檔display.xml
        setContentView(R.layout.display);
        
        // 初始化TextView控制用於顯示共用資料
        TextView textView=(TextView)findViewById(R.id.display_shared_data);
        
        // 從Intent附件資訊中取得Bundle物件
        Bundle sharedBundle=getIntent().getExtras();
        if (sharedBundle != null){
        	// 從Bundle中獲取資料
        	CharSequence sharedInputData=sharedBundle.getCharSequence("shared_data");

        	// 顯示共用資料
        	textView.setText(sharedInputData);
        }
        
    }
}
