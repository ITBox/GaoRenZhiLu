package com.itbox.fx.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
/**
 * 
 * @author youzh
 *
 */
public class EditTextUtils {

	/**
	 * 弹出软键盘
	 * 
	 * @param et
	 */
	public static void showKeyboard(final EditText et) {
		et.setFocusable(true);
		et.setFocusableInTouchMode(true);
		et.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(et, 0);
	}
	
	/**
	 * 光标显示在最后
	 * @param et
	 */
	public static void setSelection(EditText et) {
		String text = getText(et);
		if (!TextUtils.isEmpty(text)) {
			et.setSelection(text.length());
		}
	}
	
	/**
	 * 得到EditText的字符串
	 * @param et
	 * @return
	 */
	public static String getText(EditText et) {
		String text = et.getText().toString().trim();
		if (!TextUtils.isEmpty(text)) {
			return text;
		} else {
			return "";
		}
	}
	
}
