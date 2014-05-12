package com.itbox.grzl.api;

import android.util.Log;

import com.itbox.grzl.Api;
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
					}

					@Override
					public void onFailure(Throwable error, String content) {
						// TODO Auto-generated method stub
						super.onFailure(error, content);
						Log.e(TAG, "马上提问失败" + error.toString());
					}
				});

	}

	/**
	 * 搜索咨询接口
	 * 
	 */
	public void searchConsultation(String orderby, String realname,
			String pagesize, String pageindex, String jobtype,
			String teachertype) {
		RequestParams params = new RequestParams();
		params.put("orderby", orderby);
		params.put("realname", realname);
		params.put("pagesize", pagesize);
		params.put("pageindex", pageindex);
		params.put("jobtype", jobtype);
		params.put("teachertype", teachertype);
		client.post(Api.getUrl(Api.Consultation.getteacher), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						Log.e(TAG, "资讯搜索接口" + error.toString());
					}
				});

	}
}
