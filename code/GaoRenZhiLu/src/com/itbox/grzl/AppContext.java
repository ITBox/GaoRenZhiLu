package com.itbox.grzl;

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
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
