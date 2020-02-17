package com.uppowerstudio.chapter5.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Bundle資料共用範例
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 載入佈局檔main.xml
        setContentView(R.layout.main);
        
        // 初始化EditText
        final EditText editText=(EditText)findViewById(R.id.edit_share_data);
        
        // 初始化Button
        Button button=(Button)findViewById(R.id.button_display_share_data);
        
        // 註冊Button事件監聽器
        button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 獲取使用者輸入的資料
				CharSequence inputData=editText.getText();
				
				// 新增Bundle對象
				Bundle bundle=new Bundle();
				// 保存資料到Bundle物件
				bundle.putCharSequence("shared_data", inputData);
				
				// 建構新的Intent物件
				Intent intent=new Intent(MainActivity.this, DisplayDataActivity.class);
				// 保存Bundle物件到Intent附件資訊中
				intent.putExtras(bundle);
				// 啟動新的Activity
				startActivity(intent);
			}
        });
        
    }
}
