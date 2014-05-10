package com.itbox.fx.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;


/**
 * Created by huiyh on 14-2-24.
 */
public class AppContext extends Application {
    private static AppContext context;
	private static SharedPreferences appConfigFile;
	private static SharedPreferences userConfigFile;
	private static final String USER_XML_NAME = "User_Config";
	@Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 未捕获异常处理
        Thread.setDefaultUncaughtExceptionHandler(new AppException());
        // 服务器时间同步
        AppTime.getInstance();
//        if( AppState.isTampered()){
            // TODO
//          System.exit(0);
//          return;
//        }

        //初始化图画缓存 // TODO
//        initImageLoader();
//        initMap();
    }

    public static AppContext getInstance() {
        return context;
    }

	/**
	 * Context.getResources()
	 * @return
	 */
	public static Resources getRes() {
		return context.getResources();
	}
	
	public static SharedPreferences getAppPreferences(){
		if (null == appConfigFile) {
			appConfigFile = PreferenceManager.getDefaultSharedPreferences(context);
		}
		return appConfigFile;
	}
	public static SharedPreferences getUserPreferences(){
		if (null == userConfigFile) {
			userConfigFile = context.getSharedPreferences(USER_XML_NAME, Context.MODE_PRIVATE);
		}
		return userConfigFile;
	}
	
//    private void initMap() {
//        try {
//            BMapUtil.initMagManager(this);
//            MapManager.getInstance();
//        } catch (Throwable e) {}
//    }
}
