package com.uppowerstudio.chapter5.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Intent使用範例
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {
	
	// 宣告Button物件變數
	private Button btnStartActivity;
	private Button btnStartService;
	private Button btnStopService;
	private Button btnCallDial;
	private Button btnCallPhone;
	private Button btnSendSms;
	private Button btnSendEmail;
	private Button btnSearchMarket;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // 載入main.xml佈局文件
        setContentView(R.layout.main);
        
        // 呼叫初始化Button方法
        initButtonControllers();
        
        // 呼叫註冊Button按一下事件處理方法
        registerButtonHandler();
    }
    
    /**
     * 初始化Button
     */
    private void initButtonControllers(){
    	btnStartActivity=(Button)findViewById(R.id.start_activity_button);
    	btnStartService=(Button)findViewById(R.id.start_service_button);
    	btnStopService=(Button)findViewById(R.id.stop_service_button);
    	btnCallDial=(Button)findViewById(R.id.call_dial_button);
    	btnCallPhone=(Button)findViewById(R.id.call_phone_button);
    	btnSendSms=(Button)findViewById(R.id.send_sms_button);
    	btnSendEmail=(Button)findViewById(R.id.send_email_button);
    	btnSearchMarket=(Button)findViewById(R.id.market_search_button);    	
    }
    
    /**
     * 註冊Button按一下事件
     */
    private void registerButtonHandler(){
    	// 註冊“啟動新的Activity”按鈕按一下處理事件
    	btnStartActivity.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 使用自訂的Action建構Intent物件
				// Action字串需要與在AndroidManifest.xml中使用<action>定義的目標群組件名稱				//一致
				Intent intent=new Intent("action_secondary_activity");
				
				// 呼叫方法啟動新的Activity
				startActivity(intent); 
			}
    	});
    	
    	// 註冊“啟動新的Service”按鈕按一下處理事件
    	btnStartService.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 新增要啟動Service的Intent對象
				Intent service=new Intent(MainActivity.this, CountService.class);
				
				// 啟動Service
				startService(service);
				
			}
    	});
    	
    	// 註冊“停止已啟動的Service”按鈕按一下處理事件
    	btnStopService.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 新增要停止的Service的Intent對象
				Intent service=new Intent(MainActivity.this, CountService.class);
				
				// 停止Service
				stopService(service);
			}
    	});
    	
    	// 註冊“呼叫電話撥號介面”按鈕按一下處理事件
    	btnCallDial.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 建構電話號碼URI，電話號碼為5556
				Uri teleUri=Uri.parse("tel:5556");
				// 構建Intent物件，使用系統自帶的ACTION_DIAL動作
				Intent intent=new Intent(Intent.ACTION_DIAL, teleUri);
				// 啟動Activity呼叫撥號介面
				startActivity(intent);
			}
    	});
    	
    	// 註冊“撥打電話”按鈕按一下處理事件
    	btnCallPhone.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 建構電話號碼URI，電話號碼為5556
				Uri teleUri=Uri.parse("tel:5556");
				// 建構Intent物件，使用系統自帶的ACTION_CALL動作
				Intent intent=new Intent(Intent.ACTION_CALL, teleUri);
				// 啟動Activity撥打電話
				startActivity(intent);
			}
    	});
    	
    	// 註冊“呼叫發送簡訊介面”按鈕按一下處理事件
    	btnSendSms.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 建構發送簡訊的號碼URI，電話號碼為5556
				Uri uri=Uri.parse("smsto://5556");
				// 建構Intent物件，使用系統自帶的ACTION_SENDTO動作
				Intent intent=new Intent(Intent.ACTION_SENDTO, uri);
				// 設置發送簡訊的內容
				intent.putExtra("sms_body", "SMS簡訊發送測試");
				// 啟動Activity發送簡訊
				startActivity(intent);  
				
			}
    	});
    	
    	// 註冊“呼叫發送電子郵件介面”按鈕按一下處理事件
    	btnSendEmail.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				// 建構Intent物件，使用系統自帶的ACTION_SEND動作
				Intent intent=new Intent(Intent.ACTION_SEND);
				// 設置收件人位址
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"zhang@abc.com"});
				// 設置抄送人位址
				intent.putExtra(Intent.EXTRA_CC, new String[]{"li@abc.com"});
				// 設置發送郵件的主題
				intent.putExtra(Intent.EXTRA_SUBJECT, "測試呼叫 Intent方式發送郵件");
				// 設置發送郵件的內容
				intent.putExtra(Intent.EXTRA_TEXT, "測試使用隱式Intent呼叫方式打開郵件發送應用");
				// 設置郵件的編碼
				intent.setType("message/rfc822");
				
				// 啟動Activity發送郵件
				startActivity(intent);			
			}
    	});
    	
    	// 註冊“搜尋市場應用”按鈕按一下處理事件
    	btnSearchMarket.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 建構搜尋URI對象，搜尋封裝名為com.uppowerstudio.mapview的應用
				Uri uri=Uri.parse("market://search?q=pname:com.uppowerstudio.mapview");  
				// 建構Intent物件，使用系統自帶的ACTION_VIEW動作
				Intent intent=new Intent(Intent.ACTION_VIEW, uri);   
				// 啟動Activity開始搜尋
				startActivity(intent);				
			}
    	});
    	
    }
}
