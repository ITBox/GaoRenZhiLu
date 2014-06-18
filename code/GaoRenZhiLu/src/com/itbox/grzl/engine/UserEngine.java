package com.itbox.grzl.engine;

import java.io.InputStream;

import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @author youzh
 *
 */
public class UserEngine {

	/**
	 * 上传图片
	 * @param userId
	 * @param is
	 * @param imagetype
	 * @param handler
	 */
	public static void uploadImg (String userId, InputStream is, int imagetype, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("图片流", is);
		params.put("id", userId);
		params.put("imagetype", imagetype+"");
		Net.request(params, Api.getUrl(Api.User.UPLOAD_IMAGE), handler);
	}
}
