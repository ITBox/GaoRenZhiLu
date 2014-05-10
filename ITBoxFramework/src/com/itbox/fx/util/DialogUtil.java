package com.itbox.fx.util;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * 对话框工具类
 * 
 * @author baoyz
 * 
 *         2014-5-2 下午5:46:00
 * 
 */
public class DialogUtil {
	private static ProgressDialog progressDialog;

	/**
	 * 显示进度对话框
	 * 
	 * @param message
	 * @param cancelable
	 */
	public static void showProgressDialog(Activity act, String message,
			boolean cancelable) {
		dismissProgressDialog();
		progressDialog = new ProgressDialog(act);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(cancelable);
		progressDialog.show();
	}

	/**
	 * 显示进度对话框
	 * 
	 * @param message
	 */
	public static void showProgressDialog(Activity act, String message) {
		showProgressDialog(act, message, false);
	}

	/**
	 * 隐藏进度对话框
	 */
	public static void dismissProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing())
			progressDialog.dismiss();
		progressDialog = null;
	}
}
