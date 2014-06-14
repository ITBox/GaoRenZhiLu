package com.itbox.fx.core;

/**
 * @author hyh 
 * creat_at：2013-7-2-上午9:41:56 <p>
 * Log统一处理工具类
 */
public class L {
	
	public static void v(String TAG, String msg) {
		if(null == msg){
			msg = "none";
		}
		switch (AppState.getState()) {
		case AppState.STATUS_DEVELOP:{
			android.util.Log.v(TAG, msg);
			break;
		}
		case AppState.STATUS_RELEASE:
			break;
		case AppState.STATUS_TAMPER:
			break;
		default:
			break;
		}
	}

	public static void d(String TAG, String msg) {
		if(null == msg){
			msg = "none";
		}
		switch (AppState.getState()) {
		case AppState.STATUS_DEVELOP:{
			android.util.Log.d(TAG, msg);
			break;
		}
		case AppState.STATUS_RELEASE:
			break;
		case AppState.STATUS_TAMPER:
			break;
		default:
			break;
		}
	}
	
	public static void i(String TAG, String msg) {
		if(null == msg){
			msg = "none";
		}
		switch (AppState.getState()) {
		case AppState.STATUS_DEVELOP:{
			android.util.Log.i(TAG, msg);
			break;
		}
		case AppState.STATUS_RELEASE:
			break;
		case AppState.STATUS_TAMPER:
			break;
		default:
			break;
		}
	}

	public static void w(String TAG, String msg) {
		if(null == msg){
			msg = "none";
		}
		switch (AppState.getState()) {
		case AppState.STATUS_DEVELOP:{
			android.util.Log.w(TAG, msg);
			break;
		}
		case AppState.STATUS_RELEASE:
			break;
		case AppState.STATUS_TAMPER:
			break;
		default:
			break;
		}
	}

	public static void e(String TAG, String msg) {
		if(null == msg){
			msg = "none";
		}
		switch (AppState.getState()) {
		case AppState.STATUS_DEVELOP:{
			android.util.Log.e(TAG, msg);
			break;
		}
		case AppState.STATUS_RELEASE:
			break;
		case AppState.STATUS_TAMPER:
			break;
		default:
			break;
		}
	}
	public static void i(String msg) {
		i(getAppName(),msg);
	}
	public static void w(String msg) {
		w(getAppName(),msg);
	}
	public static void e(String msg) {
		e(getAppName(),msg);
	}

	/** You'd butter to override this method*/
	protected static String getAppName(){
		return "itbox_framework";
	}
}
