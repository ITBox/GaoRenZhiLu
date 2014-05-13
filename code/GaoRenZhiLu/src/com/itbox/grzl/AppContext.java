package com.itbox.grzl;

import com.activeandroid.ActiveAndroid;
import com.itbox.fx.core.Application;
import com.itbox.grzl.bean.Account;

/**
 * Created by huiyh on 14-2-24.
 */
public class AppContext extends Application {

	private static AppContext instance;

	private Account account;

	public static AppContext getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		instance = this;
		super.onCreate();
		
		ActiveAndroid.initialize(this);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
