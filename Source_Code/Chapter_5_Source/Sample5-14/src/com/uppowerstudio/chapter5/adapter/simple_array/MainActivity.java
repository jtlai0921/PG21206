package com.uppowerstudio.chapter5.adapter.simple_array;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 清單主介面
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {
	// 宣告Button物件變數
	private Button arrayAdapterButton;
	private Button simpleAdapterButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initButtonControllers();
        registerButtonHandler();
    }
    
    /**
     * 初始化Button
     */
    private void initButtonControllers(){
    		arrayAdapterButton=(Button)findViewById(R.id.array_adapter_button);
    		simpleAdapterButton=(Button)findViewById(R.id.simple_adapter_button);
    }

    /**
     * 註冊Button按一下事件
     */
    private void registerButtonHandler(){
    		// 註冊“使用ArrayAdapter建構清單”按鈕按一下處理事件
    		arrayAdapterButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// 新增要啟動ArrayAdapterActivity的Intent對象
				Intent intent=new Intent(MainActivity.this, ArrayAdapterActivity.class);
				// 啟動Activity
				startActivity(intent);
			}
    		});
    	
    		// 註冊“使用SimpleAdapter建構清單”按鈕按一下處理事件
    		simpleAdapterButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// 新增要啟動SimpleAdapterActivity的Intent對象
				Intent intent=new Intent(MainActivity.this, SimpleAdapterActivity.class);
				// 啟動Activity
				startActivity(intent);
			}
    		});
    
    }
}
