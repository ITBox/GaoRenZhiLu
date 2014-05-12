package com.itbox.fx.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

/**
 * @author huiyh
 *
 * 根据签名判断APK状态
 * <ul>
 * <li>正在开发:1</li>
 * <li>正式发布:2</li>
 * <li>被篡改:-1</li>
 * </ul>
 */
public class AppState {
	/**被篡改*/
	public static final int STATUS_TAMPER = -1;
	/**正在开发*/
	public static final int STATUS_DEVELOP = 1;
	/**正式发布*/
	public static final int STATUS_RELEASE = 2;

    // TODO 需要使用同一的keystory
    /**
     * <font color=red>需要使用同一的keystory<font/>
     */
	private static final int SHA1_DEBUG = 197583638, SHA1_RELEASE = -624722122;
	
	private static int STATE = 0;
	
	static{
		STATE = STATUS_DEVELOP;
//		initAppState();
	}
	
	public static int getState(){
		if(STATE == 0){
			initAppState();
		}
		return STATE;
	}

	/**
	 * 正在开发
	 * @return
	 */
	public static boolean isDeveloping(){
		return STATUS_DEVELOP == STATE;
	}
	/**
	 * 正式发布
	 * @return
	 */
	public static boolean isReleased(){
		return STATUS_RELEASE == STATE;
	}
    /**
	 * 正式发布
	 * @return
	 */
	public static boolean isTampered(){
		return STATUS_TAMPER == STATE;
	}
	
	/**
	 * 初始化App状态:开发or发布or被篡改
	 * */
	private static void initAppState() {
		try {
			Context context = getAppContext();
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			Signature sigs = packageInfo.signatures[0];
            L.e(sigs.hashCode() + "  hashCode");
			switch (sigs.hashCode()) {
			case SHA1_DEBUG:
				STATE = STATUS_DEVELOP;
				break;
			case SHA1_RELEASE:
				STATE = STATUS_RELEASE;
				break;
			default:
				STATE = STATUS_TAMPER;
//				System.exit(0);
				return;
			}
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	/**
     *  获取AppConteaxt
     * */
	private static Context getAppContext() {
		return Application.getInstance();
	}
	/**
	 * 捕获异常的处理
	 */
	private static void handleException(Exception e) {
		// 内容可以为空 , 也可替换为相应的异常处理方法
		AppException.handle(e);
	}
	
}
