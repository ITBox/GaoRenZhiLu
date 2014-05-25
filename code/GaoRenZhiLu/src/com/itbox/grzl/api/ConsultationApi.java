package com.itbox.grzl.api;

import java.util.ArrayList;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.UserList;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.constants.UserListItemTable;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ConsultationApi extends BaseApi {
	/**
	 * 
	 * @param title
	 * @param jobtype
	 * @param photo
	 * @param contents
	 * @param userId
	 */
	public void freeAskQuestion(String title, String jobtype, String photo,
			String contents, String userId) {
		RequestParams params = new RequestParams();
		params.put("title", title);
		params.put("jobtype", jobtype);
		params.put("photo", photo);
		params.put("contents", contents);
		params.put("userId", userId);
		client.post(Api.getUrl(Api.Consultation.probleming), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.e(TAG, "");
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "马上提问失败" + error.toString());
					}
				});

	}

	/**
	 * 
	 * @param realname
	 * @param jobtype
	 * @param teachertype
	 */
	public void searchConsultation(String realname, final String jobtype,
			final String teachertype) {
		RequestParams params = new RequestParams();
		params.put("orderby", "1");
		params.put("realname", "");
		params.put("pagesize", "20");
		params.put("pageindex", "1");
		params.put("jobtype", jobtype);
		params.put("teachertype", teachertype);
		client.post(Api.getUrl(Api.Consultation.getteacher), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						new Delete()
								.from(UserListItem.class)
								.where(UserListItemTable.COLUMN_JOBTYPE + " = "
										+ jobtype + " and "
										+ UserListItemTable.COLUMN_TEACHERTYPE
										+ " = " + teachertype).execute();
						UserList userList = mGson.fromJson(content,
								UserList.class);
						if (userList != null) {
							ActiveAndroid.beginTransaction();
							ArrayList<UserListItem> userListItem = userList
									.getUserListItem();
							if (userListItem != null) {
								try {
									for (UserListItem item : userListItem) {
										item.save();
									}
									ActiveAndroid.setTransactionSuccessful();
								} finally {
									ActiveAndroid.endTransaction();
								}
							}
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "资讯搜索接口" + error.toString());
					}
				});

	}

	/**
	 * 获取电话咨询
	 */
	public void getPhoneConsultation(String userid) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("placedate", "");
		client.post(Api.getUrl(Api.Consultation.getteacherbooking), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.e(TAG, "获取电话咨询" + content);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "获取电话咨询" + error.toString());
					}
				});
	}

	/**
	 * 获取老师更多信息
	 * 
	 */
	public void getTeacherMoreInfo(String userid) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		client.post(Api.getUrl(Api.User.GET_USER_LIST), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.e(TAG, "获取用户更多信息" + content);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "获取用户更多信息" + error.toString());
					}
				});

	}

	/**
	 * 搜索免费咨询
	 * 
	 */
	public void searchFreeConsultation(String orderby, String pagesize,
			String pageindex, String jobtype) {
		RequestParams params = new RequestParams();
		params.put("orderby", orderby);
		params.put("pagesize", pagesize);
		params.put("pageindex", pageindex);
		params.put("jobtype", jobtype);
		client.post(Api.getUrl(Api.Consultation.searchprobleming), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						Log.e(TAG, "免费资讯搜索接口" + content);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "免费资讯搜索接口" + error.toString());
					}
				});

	}

}
