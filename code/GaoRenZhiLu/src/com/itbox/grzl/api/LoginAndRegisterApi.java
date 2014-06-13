package com.itbox.grzl.api;

import org.apache.http.Header;

import android.util.Log;

import com.itbox.fx.net.ResponseHandler;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.Account;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LoginAndRegisterApi extends BaseApi {

	public void login(String username, String password) {
		RequestParams params = new RequestParams();
		params.put("account", username);
		params.put("userpassword", password);
		client.post(Api.getUrl(Api.User.Login), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						Account account = mGson
								.fromJson(content, Account.class);
						account.save();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "login" + error.toString());
						ToastUtils.showToast(AppContext.getInstance(), "用户名或密码错误");
					}
				});
	}
	
	public void login(String username, String password, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("account", username);
		params.put("userpassword", password);
		client.post(Api.getUrl(Api.User.Login), params, handler);
	}

}
