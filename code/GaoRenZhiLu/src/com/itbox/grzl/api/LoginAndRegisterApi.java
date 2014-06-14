package com.itbox.grzl.api;

import org.apache.http.Header;

import android.util.Log;

import com.itbox.fx.net.Net;
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
		client.post(Api.getUrl(Api.User.Login), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String content) {
				Account account = mGson.fromJson(content, Account.class);
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
		LoginInfo info = new LoginInfo();
		info.setAccount(username);
		info.setUserpassword(password);
		info.setLatitude(0);
		info.setLongitude(0);
		info.setMobilenum("111111");
		info.setNetWork("wifi");
		info.setPhoneModel("samsung");
		info.setSystemVersion("1.0");
		info.setVgaHeight(1280);
		info.setVgaWidth(720);
		Net.request(info, Api.getUrl(Api.User.Login), handler);
//		client.post(Api.getUrl(Api.User.Login), params, handler);
	}

	static class LoginInfo {
		private String account;
		private String userpassword;
		private String clientVersion;
		private float latitude;
		private float longitude;
		private String mobilenum;
		private String netWork;
		private String phoneModel;
		private String systemVersion;
		private String token;
		private int vgaHeight;
		private int vgaWidth;

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getUserpassword() {
			return userpassword;
		}

		public void setUserpassword(String userpassword) {
			this.userpassword = userpassword;
		}

		public String getClientVersion() {
			return clientVersion;
		}

		public void setClientVersion(String clientVersion) {
			this.clientVersion = clientVersion;
		}


		public float getLatitude() {
			return latitude;
		}

		public void setLatitude(float latitude) {
			this.latitude = latitude;
		}

		public float getLongitude() {
			return longitude;
		}

		public void setLongitude(float longitude) {
			this.longitude = longitude;
		}

		public String getMobilenum() {
			return mobilenum;
		}

		public void setMobilenum(String mobilenum) {
			this.mobilenum = mobilenum;
		}

		public String getNetWork() {
			return netWork;
		}

		public void setNetWork(String netWork) {
			this.netWork = netWork;
		}

		public String getPhoneModel() {
			return phoneModel;
		}

		public void setPhoneModel(String phoneModel) {
			this.phoneModel = phoneModel;
		}

		public String getSystemVersion() {
			return systemVersion;
		}

		public void setSystemVersion(String systemVersion) {
			this.systemVersion = systemVersion;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public int getVgaHeight() {
			return vgaHeight;
		}

		public void setVgaHeight(int vgaHeight) {
			this.vgaHeight = vgaHeight;
		}

		public int getVgaWidth() {
			return vgaWidth;
		}

		public void setVgaWidth(int vgaWidth) {
			this.vgaWidth = vgaWidth;
		}

	}
}
