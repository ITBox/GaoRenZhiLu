package com.itbox.fx.net;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicHeader;

import android.os.Build;
import android.widget.Toast;

import com.itbox.fx.core.AppContext;
import com.itbox.fx.core.AppException;
import com.itbox.fx.core.L;
import com.itbox.fx.net.NetCache.CacheModel;
import com.itbox.fx.util.NetUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;



public class Net {
	public static final String TAG = "★Net★";
	
	public static final int NetTimeout = 30000;

	public static final String USER_AGENT = "WhoyaoApp/";
//	public static final String USER_AGENT = "WhoyaoApp/"+AppContext..getString(R.string.version_name)+"/"+Utils.getApkVersionCode(AppContext.context)+";Android/"+Build.VERSION.RELEASE+";"+Build.MANUFACTURER +"/"+Build.MODEL;
	
	//WhoyaoApp/3.0.1/5;Android/4.3;sumsong/GT-I9300; 
	protected static AsyncHttpClient client;
	protected static CookieStore cookieStore;
	protected static Header[] headers = new Header[2];

	static {
		client = new AsyncHttpClient();
		cookieStore = new PersistentCookieStore(AppContext.getInstance());
		client.setCookieStore(cookieStore);
		client.setTimeout(NetTimeout);
		headers[0] = new BasicHeader("User-Agent", USER_AGENT);
		if (NetCache.VERSION != NetCache.getCacheVersion()){
			NetCache.clearCaches();
		}
	}

	/**
	 * 带缓存的异步网络请求
	 * 
	 * @param Bean
	 * @param WebApiName
	 * @param ResponseHandler
	 */
	public static void cacheRequest(Object bean, String webApiName, ResponseHandler responseHandler) {
		RequestParams params = getRequestParamsWithNull(bean);
		cacheRequest(params, webApiName, responseHandler);
	}

	/**
	 * 带缓存的异步网络请求
	 * 
	 * @param Key
	 * @param Value
	 * @param WebApiName
	 * @param ResponseHandler
	 */
	public static void cacheRequest(String key, String value, String webApiName,
			ResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.put(key, value);
		cacheRequest(params, webApiName, responseHandler);
	}

	/**
	 * 带缓存的异步网络请求
	 * 
	 * @param Params
	 * @param WebApiName
	 * @param ResponseHandler
	 */
	public static void cacheRequest(RequestParams params, String webApiName, ResponseHandler responseHandler) {
		String requestStr = params.toString();
		CacheModel cache = NetCache.getCache(webApiName, requestStr);
		if (null == cache) {
			responseHandler.setRequestInfo(webApiName, requestStr);
			request(params, webApiName, responseHandler);
		} else {
			responseHandler.onFinish();
			responseHandler.onSuccess(cache.statusCode,cache.responseBody);
		}
	}

	/**
	 * 异步网络请求
	 * 
	 * @param Bean
	 * @param WebApiName
	 * @param ResponseHandler
	 */
	public static void request(Object bean, String webApiName, ResponseHandler responseHandler) {
		RequestParams params = getRequestParamsWithNull(bean);
		try {
			params.remove("serialVersionUID");
		} catch (Exception e) {
		}
		request(params, webApiName, responseHandler);
	}

	/**
	 * 异步网络请求
	 * 
	 * @param Key
	 * @param Value
	 * @param WebApiName
	 * @param ResponseHandler
	 */
	public static void request(String key, String value, String webApiName, ResponseHandler responseHandler) {

		RequestParams params = new RequestParams();
		params.put(key, value);
		request(params, webApiName, responseHandler);
	}

	/**
	 * ★★异步网络请求,其他请求到调用这个★★
	 * 
	 * @param Params
	 * @param WebApiName
	 * @param ResponseHandler
	 */
	public static void request(RequestParams params, String url, ResponseHandler responseHandler) {
		if(responseHandler == null){
			responseHandler = new ResponseHandler();
		}
		if(!NetUtil.isNetAvailable()){
			responseHandler.onFinish();
			responseHandler.handleFailureMessage(new HttpResponseException(-1,"no active network"), "");
			return;
		}


		Header[] headers = getHeaders();
		L.i(TAG,"url: "+ url);
		if(null != params){
			L.i(TAG,"params: "+ params.toString());
		}
		L.i(TAG,"header: "+ headers[0].toString() + " & "+ headers[1].toString());
		
		//cookieStore.addCookie(new BasicClientCookie("t", AppContext.serviceTimeMillis()+""));
		client.post(AppContext.getInstance(), url, headers, params, null, responseHandler);
	}
	/***
	 * <font color='#ff0000' size='4'><strong>不能用了下载大文件</strong></font>
	 * @param url
	 */
	public static void request(String url,BinaryHttpResponseHandler handler){
		L.i(TAG,"CodeUrl: "+url);
		client.get(url, handler);
	}
	
	/**获取Cookies*/
	public static List<Cookie> getCookies(){
		return cookieStore.getCookies();
	}
	

	/**
	 * 获取一个新的RequestParams
	 * @return
	 */
	public static RequestParams getRequestParams() {
		return new RequestParams();
	}
	/**
	 * 将传输的bean对象封装成RequestParams
	 * 
	 * @param bean
	 * @return
	 */
	public static RequestParams getRequestParamsWithNull(Object bean) {
		if(bean == null){
			return null;
		}
		RequestParams params = null;
		Field[] fields = bean.getClass().getDeclaredFields();

		if (0 < fields.length) {
			params = new RequestParams();
			String fieldName = null;
			Object fieldValue = null;
			try {
				for (Field f : fields) {
					f.setAccessible(true);
					fieldName = f.getName();
					fieldValue = f.get(bean);
					if (null == fieldValue) {
						params.put(fieldName, "");
					}else{
						params.put(fieldName, fieldValue.toString());
					}
				}
			} catch (IllegalArgumentException e) {
				AppException.handle(e);
			} catch (IllegalAccessException e) {
				AppException.handle(e);
			}
		}

		return params;
	}
	/**
	 * 将传输的bean对象封装成RequestParams
	 * 
	 * @param bean
	 * @return
	 */
	public static RequestParams getRequestParamsShowNull(Object bean) {
		RequestParams params = null;
		Field[] fields = bean.getClass().getDeclaredFields();
		
		if (0 < fields.length) {
			params = new RequestParams();
			String fieldName = null;
			Object fieldValue = null;
			try {
				for (Field f : fields) {
					f.setAccessible(true);
					fieldName = f.getName();
					fieldValue = f.get(bean);
					if (null == fieldValue) {
						params.put(fieldName, "NULL");
					}else{
						params.put(fieldName, fieldValue.toString());
					}
				}
			} catch (IllegalArgumentException e) {
				AppException.handle(e);
			} catch (IllegalAccessException e) {
				AppException.handle(e);
			}
		}
		
		return params;
	}
	/**
	 * 将传输的bean对象封装成RequestParams
	 * 
	 * @param bean
	 * @return
	 */
	public static RequestParams getRequestParamsWithoutNull(Object bean) {
		RequestParams params = null;
		Field[] fields = bean.getClass().getDeclaredFields();
		
		if (0 < fields.length) {
			params = new RequestParams();
			String fieldName = null;
			Object fieldValue = null;
			try {
				for (Field f : fields) {
					f.setAccessible(true);
					fieldName = f.getName();
					fieldValue = f.get(bean);
					if (null != fieldValue) {
						params.put(fieldName, fieldValue.toString());
					}
				}
			} catch (IllegalArgumentException e) {
				AppException.handle(e);
			} catch (IllegalAccessException e) {
				AppException.handle(e);
			}
		}
		
		return params;
	}

	/**
	 * 更新请求头,其中Postion用于标识当前操作所在的位置
	 * 
	 * @return
	 */
	protected static Header[] getHeaders() {
		String pos = "NotHaveActivity";// TODO
//		if (null != AppContext.curActivity) {
////			pos = AppContext.curActivity.toString();
//			pos = AppContext.curActivity.getClass().getSimpleName();
//		}
		if (null == headers[1] || !headers[1].getValue().equals(pos)) {
			headers[1] = new BasicHeader("X-Position", pos);
		}
		return headers;
	}
	
	
}
