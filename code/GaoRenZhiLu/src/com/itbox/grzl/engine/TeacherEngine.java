package com.itbox.grzl.engine;

import com.itbox.fx.core.AppContext;
import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.CommentAdd;
import com.itbox.grzl.bean.CommentMarkAdd;
import com.itbox.grzl.common.Contasts;
import com.loopj.android.http.RequestParams;

/**
 * 老师相关业务
 * 
 * @author byz
 * @date 2014-5-10下午10:53:46
 */
public class TeacherEngine {

	public static final int PAGE_NUM = 20;

	/**
	 * 申请提现
	 * 
	 * @param bean
	 * @param handler
	 */
	public static void addWithdrawals(double price, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid",
				AppContext.getUserPreferences().getString(Contasts.USERID, ""));
		params.put("price", Double.toString(price));
		Net.request(params , Api.getUrl(Api.User.ADD_USER_WITHDRAWALS), handler);
	}

	/**
	 * 获取收入明细
	 * 
	 * @param pageNum
	 * @param teacheruserid
	 * @param handler
	 */
	public static void getIncoming(int pageNum, int teacheruserid, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		params.put("teacheruserid", Integer.toString(teacheruserid));
		Net.request(params, Api.getUrl(Api.User.GET_TEACHER_INCOME), handler);
	}
	
	/**
	 * 获取提现记录
	 * 
	 * @param pageNum
	 * @param userid
	 * @param handler
	 */
	public static void getWithdrawals(int pageNum, int userid, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		params.put("userid", Integer.toString(userid));
		Net.request(params, Api.getUrl(Api.User.GET_USER_WITHDRAWALS), handler);
	}

}
