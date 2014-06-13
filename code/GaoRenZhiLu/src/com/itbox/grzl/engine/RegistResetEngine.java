package com.itbox.grzl.engine;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.CheckAccount;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @author youzh
 *
 */
public class RegistResetEngine {
    /**
     * 修改密码
     * @param userId
     * @param password
     * @param handler
     */
	public static void resetPass(String userId, String password, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", userId);
		params.put("password", password);
		Net.request(params, Api.getUrl(Api.User.ChangePassword), handler);
	}
	
	/**
	 * 验证帐号是否注册过
	 * @param text
	 * @param handler
	 */
	public static void checkAccount(String text, ResponseHandler handler) {
		Net.request("useremail", text, Api.getUrl(Api.User.CheckAccount), handler);
	}
	
	/**
	 * 发送验证码
	 * @param mPhone
	 * @param type
	 * @param handler
	 */
	public static void sendAuthCode(String mPhone, int type, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userphone", mPhone);
		params.put("type", String.valueOf(type));
		Net.request(params, Api.getUrl(Api.User.SendVerifyCode), handler);
	};
	
	/**
	 * 验证验证码
	 * @param phone
	 * @param authCode
	 * @param handler
	 */
	public static void getAuthCode(String phone, String authCode, int type, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userphone", phone);
		params.put("verifycode", authCode);
		params.put("type", String.valueOf(type));
		Net.request(params, Api.getUrl(Api.User.CheckVerifyCode), handler);
	}
}
