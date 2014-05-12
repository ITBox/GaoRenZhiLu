package com.itbox.grzl.api;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;
import com.itbox.fx.core.Application;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

public class BaseApi {
	protected Gson mGson = new Gson();
	public final static String TAG = "API";
	public static final int NetTimeout = 30000;
	public static final String USER_AGENT = "WhoyaoApp/";
	protected static AsyncHttpClient client;
	protected static CookieStore cookieStore;
	protected static Header[] headers = new Header[2];

	public BaseApi() {
		client = new AsyncHttpClient();
		cookieStore = new PersistentCookieStore(Application.getInstance());
		client.setCookieStore(cookieStore);
		client.setTimeout(NetTimeout);
		headers[0] = new BasicHeader("User-Agent", USER_AGENT);

	}

}
