package com.uppowerstudio.chapter5.preftype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 新增不同類型Preference示例
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {
    // 宣告控件變量
	private Button xmlCreateButton;
	private Button codeCreateButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 加載佈局文件main.xml
        setContentView(R.layout.main);
        
        // 初始化按鈕控件
        xmlCreateButton = (Button)findViewById(R.id.button_xml_create);
        codeCreateButton = (Button)findViewById(R.id.button_code_create);
        
        // 註冊事件監聽器
        xmlCreateButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 建構並啓動以XML方式新增Preferences的Activity
				Intent xmlIntent = new Intent("action_xml_preference");
				startActivity(xmlIntent);				
			}
        });
        
        codeCreateButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 建構並啓動以程式碼方式新增Preferences的Activity
				Intent codeIntent = new Intent("action_code_preference");
				startActivity(codeIntent);
			}
        });
        
        
    }
}