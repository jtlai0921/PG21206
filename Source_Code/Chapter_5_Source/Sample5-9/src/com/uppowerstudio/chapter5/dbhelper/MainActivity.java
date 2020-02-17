package com.uppowerstudio.chapter5.dbhelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    // 定義初始資料庫版本號常量
	private static final int INIT_DB_VERSION=1;
	// 定義更新資料庫版本號常量
	private static final int UPGRADE_DB_VERSION=2;
	
	// 宣告MyDbHelper物件變數
	private MyDbHelper dbHelper;
	
	// 宣告資料庫物件SQLiteDatabase變數
	private SQLiteDatabase sqliteDatabase;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 初始化按鈕
        Button createButton=(Button)findViewById(R.id.button_create_db);
        Button upgradeButton=(Button)findViewById(R.id.button_upgrade_db);
        
        // 註冊新增資料庫按鈕事件監聽器
        createButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 新增資料庫
				dbHelper=new MyDbHelper(getBaseContext(), INIT_DB_VERSION);
				// 返回可讀取的資料庫物件
				sqliteDatabase=dbHelper.getWritableDatabase();
				// 顯示提示消息
				Toast.makeText(getBaseContext(), getString(R.string.msg_create_success), Toast.LENGTH_LONG).show();
			}
        });
        
        // 註冊更新資料庫結構按鈕事件監聽器
        upgradeButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 更新資料庫結構，傳入的版本號與新增時的不同即可觸發對onUpgrade的呼叫
				dbHelper=new MyDbHelper(getBaseContext(), UPGRADE_DB_VERSION);
				// 返回可讀取的資料庫物件
				sqliteDatabase=dbHelper.getWritableDatabase();
				// 顯示提示消息
				Toast.makeText(getBaseContext(), getString(R.string.msg_upgrade_success), Toast.LENGTH_LONG).show();
			}
        });
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 關閉資料庫
		dbHelper.close();
	}
    
    
}
