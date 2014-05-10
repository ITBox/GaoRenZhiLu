package com.itbox.fx.util;


import com.itbox.fx.core.AppContext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	private static ConnectivityManager connectivityManager;
	private static NetworkInfo netState;
	
	public static boolean isNetAvailable(){
		NetworkInfo info = getNetworkInfo();
		return (null != info && info.isAvailable());
	}
	
	public static NetworkInfo getNetworkInfo() {
		if (null == connectivityManager) {
			connectivityManager = (ConnectivityManager) AppContext.getInstance()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		netState = connectivityManager.getActiveNetworkInfo();
		return netState;
	}
}
