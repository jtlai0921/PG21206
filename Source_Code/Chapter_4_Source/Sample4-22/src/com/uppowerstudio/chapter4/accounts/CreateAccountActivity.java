package com.uppowerstudio.chapter4.accounts;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AccountAuthenticatorActivity {
	private static final String ACCOUNT_TYPE="com.uppowerstudio.account";
	private static final String SERVER="www.google.com";

	private Button buttonCreate;
	private Button buttonCancel;
	private EditText txtAccountName;
	private EditText txtAccountPassword;
	private AccountManager accountManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);

		// 獲取AccountManager實例物件
		accountManager=AccountManager.get(CreateAccountActivity.this);

		buttonCreate=(Button) findViewById(R.id.btn_create);
		buttonCancel=(Button) findViewById(R.id.btn_cancel);
		txtAccountName=(EditText) findViewById(R.id.txt_account_name);
		txtAccountPassword=(EditText) findViewById(R.id.txt_account_password);

		buttonCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createAccount();
			}
		});

		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 新增帳號
	 */
	private void createAccount() {
		// 獲取輸入的帳號名及密碼
		String accountName=txtAccountName.getText().toString();
		String password=txtAccountPassword.getText().toString();

		// 初始化帳號資料
		Account account=new Account(accountName, ACCOUNT_TYPE);
		
		// 設置伺服器資料
		Bundle bundle=new Bundle();
		bundle.putString("SERVER", SERVER);
		
		// 添加帳號
		if (accountManager.addAccountExplicitly(account, password, bundle)) {
			Bundle result=new Bundle();
			result.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
			result.putString(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
			setAccountAuthenticatorResult(result);
		}
		finish();
	}
}
