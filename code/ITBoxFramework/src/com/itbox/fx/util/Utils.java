package com.itbox.fx.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Utils {

	/**
	 * 隐藏软键盘
	 * 
	 * @param activity
	 */
	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		View currentFocus = activity.getCurrentFocus();
		if (null != currentFocus) {
			imm.hideSoftInputFromWindow(currentFocus.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/**
	 * 比较两个List中的各值是否相等
	 * @param beforeList
	 * @param afterList
	 * @return
	 */
	public static <T> boolean compareContent(ArrayList<T> beforeList, ArrayList<T> afterList){
		boolean result = false ;
		if (beforeList != null && beforeList.size() > 0 ) {
			for (int i = 0; i < beforeList.size(); i++) {
				result = beforeList.get(i).equals(afterList.get(i));
				if (!result) {
					return result;
				}
			}
		}
		return result;
	}
}
