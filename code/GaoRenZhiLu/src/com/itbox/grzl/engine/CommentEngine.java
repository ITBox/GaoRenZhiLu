package com.itbox.grzl.engine;

import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.CommentAdd;
import com.itbox.grzl.bean.CommentMarkAdd;
import com.loopj.android.http.RequestParams;

/**
 * 行业论坛业务
 * 
 * @author byz
 * @date 2014-5-10下午10:53:46
 */
public class CommentEngine {

	public static final int PAGE_NUM = 20;

	/**
	 * 添加论坛信息
	 * 
	 * @param bean
	 * @param handler
	 */
	public static void addComment(CommentAdd bean, ResponseHandler handler) {
		Net.request(bean, Api.getUrl(Api.User.ADD_COMMENT), handler);
	}

	/**
	 * 添加论坛评论
	 * 
	 * @param bean
	 * @param handler
	 */
	public static void addCommentMark(CommentMarkAdd bean, ResponseHandler handler) {
		Net.request(bean, Api.getUrl(Api.User.ADD_COMMENTRE_MARK), handler);
	}

	/**
	 * 获取论坛信息列表
	 * 
	 * @param pageNum
	 * @param handler
	 */
	public static void getComment(int pageNum, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		Net.request(params, Api.getUrl(Api.User.GET_COMMENT), handler);
	}

	/**
	 * 获取论坛评论列表
	 * 
	 * @param pageNum
	 * @param id
	 * @param handler
	 */
	public static void getCommentMark(int pageNum, int id, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		params.put("id", Integer.toString(id));
		Net.request(params, Api.getUrl(Api.User.GET_COMMENTRE_MARK), handler);
	}

}
