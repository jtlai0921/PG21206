package com.uppowerstudio.chapter4.accounts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 帳號服務類別
 * 
 * @author UPPower Studio
 * 
 */
public class AccountsService extends Service {
	private AccountAuthenticator accountAuth;

	@Override
	public IBinder onBind(Intent intent) {
		IBinder binder=null;

		// 判斷如果當前執行的Intent為android.accounts.AccountAuthenticator，則返回IBinder
		if (intent.getAction().equals(
				android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))

			if (accountAuth==null) {
				accountAuth=new AccountAuthenticator(AccountsService.this);
			} else {
				binder=accountAuth.getIBinder();
			}

		return binder;
	}
}
