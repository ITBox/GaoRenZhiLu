package com.itbox.fx.util;

import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
	}
	/**
	 * 检测手机号
	 * @param mPhoneNum
	 * @return
	 */
	public static boolean checkPhone(String mPhoneNum) {
		boolean result = false;
		String checkMobile = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
		if(Pattern.compile(checkMobile).matcher(mPhoneNum).matches()){
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	/**
	 * 检测邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email){
		boolean result = false;
		String checkEmail = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		if (Pattern.compile(checkEmail).matcher(email).matches()) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
}
