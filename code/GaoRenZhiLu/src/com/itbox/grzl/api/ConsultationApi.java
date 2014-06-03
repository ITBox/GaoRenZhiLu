package com.itbox.grzl.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.TeacherComment;
import com.itbox.grzl.bean.TeacherCommentList;
import com.itbox.grzl.bean.TeacherExtension;
import com.itbox.grzl.bean.UserLevel;
import com.itbox.grzl.bean.UserLevelList;
import com.itbox.grzl.bean.UserList;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.constants.TeacherCommentTable;
import com.itbox.grzl.constants.UserLevelTable;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ConsultationApi extends BaseApi {

	public interface AskQuestionListener {
		public void onStartAsk();

		public void onSuccess();

		public void onFail();

	}

	private AskQuestionListener mAskQuestionListener;

	public void setmAskQuestionListener(AskQuestionListener mAskQuestionListener) {
		this.mAskQuestionListener = mAskQuestionListener;
	}

	/**
	 * 
	 * @param title
	 * @param jobtype
	 * @param photo
	 * @param contents
	 * @param userId
	 */
	public void freeAskQuestion(String title, String jobtype, File photo,
			String contents, String userId) {
		mAskQuestionListener.onStartAsk();
		RequestParams params = new RequestParams();
		params.put("title", title);
		params.put("jobtype", jobtype);
		try {
			params.put("photo", photo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		params.put("contents", contents);
		params.put("userId", userId);
		client.post(Api.getUrl(Api.Consultation.probleming), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						try {
							JSONObject object = new JSONObject(content);
							int result = object.optInt("result");
							if (result == 1) {
								mAskQuestionListener.onSuccess();
							} else {
								mAskQuestionListener.onFail();
							}

						} catch (JSONException e) {
							e.printStackTrace();
							mAskQuestionListener.onFail();

						}

					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						mAskQuestionListener.onFail();
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
						Log.e(TAG, "资讯搜索接口返回值" + content);
						new Delete().from(UserListItem.class).execute();
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
		params.put("placedate", "2014-5-25");
		Net.request(params, Api.getUrl(Api.Consultation.getteacherbooking),
				new ResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);
						Log.e(TAG, "获取电话咨询" + content);
					}

					@Override
					public void onFailure(Throwable e, int statusCode,
							String content) {
						super.onFailure(e, statusCode, content);
						Log.e(TAG, "获取电话咨询" + content);
					}
				});
	}

	/**
	 * 获取
	 */
	public void getUserLevel(final String userid) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("placedate", "2014-5-25");
		Net.request(params, Api.getUrl(Api.Consultation.GETUSERMEMBER),
				new ResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);

						new Delete()
								.from(UserLevel.class)
								.where(UserLevelTable.COLUMN_USER_ID + "=?",
										userid).execute();
						UserLevelList userLevelList = mGson.fromJson(content,
								UserLevelList.class);
						if (userLevelList != null
								&& userLevelList.getUserMemberItem() != null) {

							ActiveAndroid.beginTransaction();
							ArrayList<UserLevel> userLevels = userLevelList
									.getUserMemberItem();
							if (userLevels != null) {
								try {
									for (UserLevel item : userLevels) {
										item.setUserid(userid);
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
					public void onFailure(Throwable e, int statusCode,
							String content) {
						super.onFailure(e, statusCode, content);

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
		client.post(Api.getUrl(Api.User.GETUSEREXTENSION), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						TeacherExtension mTeacherExtension = mGson.fromJson(
								content, TeacherExtension.class);
						mTeacherExtension.save();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "获取用户更多信息" + error.toString());
					}
				});

	}

	/**
	 * 搜索免费咨询 ...
	 * 
	 */
	public void searchFreeConsultation(String orderby, String pagesize,
			String pageindex, String jobtype) {
		RequestParams params = new RequestParams();
		params.put("orderby", orderby);
		params.put("pagesize", pagesize);
		params.put("pageindex", pageindex);
		params.put("jobtype", jobtype);
		client.post(Api.getUrl(Api.User.GET_TEACHER_COMMENT), params,
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

	/**
	 * 获取导师评论接口
	 * 
	 */
	public void getTeacherComment(String teacherid) {
		RequestParams params = new RequestParams();
		params.put("teacherid", teacherid);
		client.post(Api.getUrl(Api.User.GET_TEACHER_COMMENT), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						TeacherCommentList mTeacherCommentList = mGson
								.fromJson(content, TeacherCommentList.class);
						new Delete()
								.from(TeacherComment.class)
								.where(TeacherCommentTable.COLUMN_TEACHERUSERID
										+ "=?", "14").execute();
						if (mTeacherCommentList != null
								&& mTeacherCommentList.getTeacherCommentItem() != null) {
							ActiveAndroid.beginTransaction();
							try {
								for (TeacherComment comment : mTeacherCommentList
										.getTeacherCommentItem()) {
									comment.save();
								}
								ActiveAndroid.setTransactionSuccessful();
							} finally {
								ActiveAndroid.endTransaction();
							}
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "获取老师评论接口" + error.toString());
					}
				});

	}

	/**
	 * 获取导师评论
	 * 
	 */
	public void addTeacherComment(String teacheruserid, String userid,
			String commentcontent, String score, String id) {
		RequestParams params = new RequestParams();
		params.put("teacheruserid", teacheruserid);
		params.put("userid", userid);
		params.put("commentcontent", commentcontent);
		params.put("score", score);
		params.put("id", id);
		client.post(Api.getUrl(Api.User.ADD_TEACHER_COMMENT), params,
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
