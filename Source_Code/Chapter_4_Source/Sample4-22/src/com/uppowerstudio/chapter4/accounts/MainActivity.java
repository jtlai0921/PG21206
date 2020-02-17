package com.uppowerstudio.chapter4.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button buttonCreateAccount;
	private Button buttonListAccount;
	private TextView accountsInfo;
	private AccountManager accountManager;
	
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

accountManager=AccountManager.get(MainActivity.this);

buttonCreateAccount=(Button)findViewById(R.id.btn_create_account);
buttonListAccount=(Button)findViewById(R.id.btn_list_account);
accountsInfo=(TextView)findViewById(R.id.txt_account_info);

buttonCreateAccount.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 呼叫新增Google帳號介面
				Intent intent=new Intent(MainActivity.this, CreateAccountActivity.class);
				startActivity(intent);
			}    	
        });

buttonListAccount.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				listAccounts();
			}    	
        });
    }

/**
     * 清單顯示當前存在的帳號資訊
     */
private void listAccounts(){
	StringBuffer sb=new StringBuffer();
	
	// 獲取所有帳號資訊
	Account[] accounts=accountManager.getAccounts();
	
	for (Account account : accounts){
		sb.append("\n");
		// 獲取帳號名及其類型資訊
		sb.append(getString(R.string.account_name)).append(account.name).append("\n");
		sb.append(getString(R.string.account_type)).append(account.type).append("\n");
	}
	
	accountsInfo.setText(sb.toString());
    }
}
