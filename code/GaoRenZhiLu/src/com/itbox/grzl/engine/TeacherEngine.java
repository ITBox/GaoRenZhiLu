package com.itbox.grzl.engine;

import java.util.List;

import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.TeacherCommentGet;
import com.itbox.grzl.bean.TeacherIncoming;
import com.itbox.grzl.bean.TeacherWithdrawals;
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
	public static void addWithdrawals(double price, String description,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("price", Double.toString(price));
		params.put("description", description);
		Net.request(params, Api.getUrl(Api.User.ADD_USER_WITHDRAWALS), handler);
	}

	/**
	 * 获取收入明细
	 * 
	 * @param pageNum
	 * @param teacheruserid
	 * @param handler
	 */
	public static void getIncoming(int pageNum, int teacheruserid,
			ResponseHandler handler) {
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
	public static void getWithdrawals(int pageNum, int userid,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		params.put("userid", Integer.toString(userid));
		Net.request(params, Api.getUrl(Api.User.GET_USER_WITHDRAWALS), handler);
	}

	/**
	 * 取消提现记录
	 * 
	 * @param pageNum
	 * @param userid
	 * @param handler
	 */
	public static void cancelWithdrawals(String id, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("id", id);
		Net.request(params, Api.getUrl(Api.User.CANCEL_USER_WITHDRAWALS),
				handler);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param handler
	 */
	public static void getUserList(ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		Net.request(params, Api.getUrl(Api.User.GET_USER_LIST), handler);
	}

	/**
	 * 获取老师评价
	 * 
	 * @param handler
	 */
	public static void getCommentList(ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("teacherid", AppContext.getInstance().getAccount()
				.getUserid().toString());
		// params.put("teacherid", "14");
		Net.request(params, Api.getUrl(Api.User.GET_TEACHER_COMMENT), handler);
	}

	public static class UserPayDetailItem {
		public List<TeacherIncoming> UserPayDetailItem;

		public List<TeacherIncoming> getUserPayDetailItem() {
			return UserPayDetailItem;
		}

		public void setUserPayDetailItem(List<TeacherIncoming> userPayDetailItem) {
			UserPayDetailItem = userPayDetailItem;
		}

	}

	public static class UserWithdrawalsItem {
		public List<TeacherWithdrawals> UserWithdrawalsItem;

		public List<TeacherWithdrawals> getUserWithdrawalsItem() {
			return UserWithdrawalsItem;
		}

		public void setUserWithdrawalsItem(
				List<TeacherWithdrawals> userWithdrawalsItem) {
			UserWithdrawalsItem = userWithdrawalsItem;
		}

	}

	public static class TeacherCommentItem {
		public List<TeacherCommentGet> TeacherCommentItem;

		public List<TeacherCommentGet> getTeacherCommentItem() {
			return TeacherCommentItem;
		}

		public void setTeacherCommentItem(
				List<TeacherCommentGet> teacherCommentItem) {
			TeacherCommentItem = teacherCommentItem;
		}

	}

}
