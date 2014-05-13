package com.itbox.fx.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;

public class Application extends android.app.Application {
	protected static Application context;
	private static SharedPreferences appConfigFile;
	private static SharedPreferences userConfigFile;
	private static final String USER_XML_NAME = "User_Config";

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		Thread.setDefaultUncaughtExceptionHandler(new AppException());
		AppTime.getInstance();
		// 初始化ActiveAndroid
		ActiveAndroid.initialize(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}

	public static Application getInstance() {
		return context;
	}

	public static Resources getRes() {
		return context.getResources();
	}

	public static SharedPreferences getAppPreferences() {
		if (null == appConfigFile) {
			appConfigFile = PreferenceManager
					.getDefaultSharedPreferences(context);
		}
		return appConfigFile;
	}

	public static SharedPreferences getUserPreferences() {
		if (null == userConfigFile) {
			userConfigFile = context.getSharedPreferences(USER_XML_NAME,
					Context.MODE_PRIVATE);
		}
		return userConfigFile;
	}

}
