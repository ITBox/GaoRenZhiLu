package com.itbox.grzl.engine;

import java.io.InputStream;
import java.util.List;

import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.Attention;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @author youzh
 * 
 */
public class UserEngine {

	public static final int PAGE_NUM = 20;

	/**
	 * 上传图片
	 * 
	 * @param userId
	 * @param is
	 * @param imagetype
	 * @param handler
	 */
	public static void uploadImg(String userId, InputStream is, int imagetype,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("图片流", is);
		params.put("id", userId);
		params.put("imagetype", imagetype + "");
		Net.request(params, Api.getUrl(Api.User.UPLOAD_IMAGE), handler);
	}

	/**
	 * 检查关注
	 * 
	 * @param teacherid
	 * @param handler
	 */
	public static void checkAttention(String teacherid, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("teacherid", teacherid);
		Net.request(params, Api.getUrl(Api.User.checkattention), handler);
	}

	/**
	 * 添加关注
	 * 
	 * @param teacherid
	 * @param handler
	 */
	public static void addAttention(String teacherid, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("teacherid", teacherid);
		Net.request(params, Api.getUrl(Api.User.addattention), handler);
	}

	/**
	 * 删除关注
	 * 
	 * @param teacherid
	 * @param handler
	 */
	public static void deleteAttention(String teacherid, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("teacherid", teacherid);
		Net.request(params, Api.getUrl(Api.User.deleteattention), handler);
	}

	/**
	 * 删除关注
	 * 
	 * @param teacherid
	 * @param handler
	 * @param pageNum
	 */
	public static void getAttention(int pageNum, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		Net.request(params, Api.getUrl(Api.User.getattentionbyuserid), handler);
	}

	public static class UserAttention {
		private List<Attention> UserAttention;

		public List<Attention> getUserAttention() {
			return UserAttention;
		}

		public void setUserAttention(List<Attention> userAttention) {
			UserAttention = userAttention;
		}

	}
}
