package com.itbox.fx.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ToastUtils {
    /** 可以自定义Style样式（new Style.build）**/
	public static void makeAlert(Activity ctx, String message){
		Crouton.makeText(ctx, message, Style.ALERT).show();
	}
	public static void makeInfo(Activity ctx, String message){
		Crouton.makeText(ctx, message, Style.INFO).show();
	}
	public static void makeConfirm(Activity ctx, String message){
		Crouton.makeText(ctx, message, Style.CONFIRM).show();
	}
	
	/**
	 * 显示系统的Toast
	 * @param msg
	 */
	public static void showToast(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 显示系统的Toast
	 * @param msg
	 */
	public static void showToast(Context ctx, int msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 显示位置的Toast 根据自己所写XML的布局View的位置
	 * @param activity
	 * @param message
	 * @param viewGroupResId
	 */
	public static void makeCustomPosition(Activity activity, String message, int viewGroupResId) {
		Crouton.makeText(activity, message, Style.INFO, viewGroupResId).show();
	}
	/**
	 * 自定义View的Toast
	 * @param activity
	 * @param customView
	 */
	public static void makeCustomView(Activity activity, View customView){
		Crouton.make(activity, customView).show();
	}
	
	/**
	 * 自定义View显示位置的Toast
	 * @param activity
	 * @param customView
	 * @param viewGroupResId
	 */
	public static void makeCustomViewOrPosition(Activity activity, View customView, int viewGroupResId){
		Crouton.make(activity, customView, viewGroupResId).show();
	}
}
