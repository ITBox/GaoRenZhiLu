package com.itbox.grzl.engine;

import java.util.List;

import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.EventAdd;
import com.itbox.grzl.bean.EventCommentGet;
import com.itbox.grzl.bean.EventDetailGet;
import com.itbox.grzl.bean.EventGet;
import com.itbox.grzl.bean.EventSearchGet;
import com.itbox.grzl.bean.EventUser;
import com.itbox.grzl.enumeration.EventState;
import com.itbox.grzl.enumeration.EventType;
import com.loopj.android.http.RequestParams;

/**
 * 活动业务
 * 
 * @author byz
 * @date 2014-5-10下午10:53:46
 */
public class EventEngine {

	public static final int PAGE_NUM = 20;

	/**
	 * 添加活动
	 * 
	 * @param bean
	 * @param handler
	 *            (BaseResult)
	 */
	public static void addEvent(EventAdd bean, ResponseHandler handler) {
		Net.request(bean, Api.getUrl(Api.Event.add), handler);
	}

	/**
	 * 搜索活动
	 * 
	 * @param userdistrict
	 *            区域code
	 * @param typeid
	 * @param title
	 * @param activitystate
	 *            2 已结束 1 报名中
	 * @param pageNum
	 * @param handler
	 */
	public static void searchEvent(String userdistrict, EventType type,
			EventState state, String title, int pageNum, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userdistrict", userdistrict);
		String typeid = "";
		if (type != null)
			typeid = Integer.toString(type.getIndex());
		params.put("typeid", typeid);
		String activitystate = "";
		if (state != null)
			activitystate = Integer.toString(state.getIndex());
		params.put("activitystate", activitystate);
		params.put("title", title);
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		Net.request(params, Api.getUrl(Api.Event.search), handler);
	}

	/**
	 * 加入活动
	 * 
	 * @param activityid
	 * @param handler
	 *            (BaseResult)
	 */
	public static void joinEvent(String activityid, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("activityid", activityid);
		Net.request(params, Api.getUrl(Api.Event.join), handler);
	}

	/**
	 * 添加感兴趣活动
	 * 
	 * @param activityid
	 * @param handler
	 *            (BaseResult)
	 */
	public static void addInterestEvent(String activityid,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("activityid", activityid);
		Net.request(params, Api.getUrl(Api.Event.interestAdd), handler);
	}

	/**
	 * 添加活动评论
	 * 
	 * @param activityid
	 * @param handler
	 *            (BaseResult)
	 */
	public static void addEventComment(String activityid, String commentcontent,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("activityid", activityid);
		params.put("commentcontent", commentcontent);
		Net.request(params, Api.getUrl(Api.Event.commentAdd), handler);
	}

	/**
	 * 获取活动评论
	 * 
	 * @param activityid
	 * @param handler
	 * @param pageNum
	 */
	public static void getEventComment(String activityid, int pageNum,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("activityid", activityid);
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		Net.request(params, Api.getUrl(Api.Event.commentList), handler);
	}

	/**
	 * 获取活动详情
	 * 
	 * @param activityid
	 * @param handler
	 */
	public static void getEventDetail(String activityid, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("activityid", activityid);
		Net.request(params, Api.getUrl(Api.Event.detail), handler);
	}

	/**
	 * 获取我的活动
	 * 
	 * @param userdistrict
	 * @param typeid
	 * @param isFinish
	 *            是不是结束的
	 * @param isMy
	 *            是不是我发起的
	 * @param pageNum
	 * @param handler
	 */
	public static void getMyEvent(String userdistrict, EventType type,
			EventState state, boolean isMy, int pageNum, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userdistrict", userdistrict);
		String typeid = "";
		if (type != null)
			typeid = Integer.toString(type.getIndex());
		params.put("typeid", typeid);
		String activitystate = "";
		if (state != null)
			activitystate = Integer.toString(state.getIndex());
		params.put("activitystate", activitystate);
		params.put("type", isMy ? "1" : "2");
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		Net.request(params, Api.getUrl(Api.Event.listForUser), handler);
	}

	public static class ActivityIdItem {

		private List<EventGet> ActivityIdItem;

		public List<EventGet> getActivityIdItem() {
			return ActivityIdItem;
		}

		public void setActivityIdItem(List<EventGet> activityIdItem) {
			ActivityIdItem = activityIdItem;
		}

	}

	public static class SearchItem {

		private List<EventSearchGet> ActivityIdItem;

		public List<EventSearchGet> getActivityIdItem() {
			return ActivityIdItem;
		}

		public void setActivityIdItem(List<EventSearchGet> activityIdItem) {
			ActivityIdItem = activityIdItem;
		}

	}
	
	public static class MyEventItem {
		
		private List<EventSearchGet> ActivityItem;
		
		public List<EventSearchGet> getActivityIdItem() {
			return ActivityItem;
		}
		
		public void setActivityIdItem(List<EventSearchGet> activityIdItem) {
			ActivityItem = activityIdItem;
		}
		
	}

	public static class ActivityUserCommentItem {

		private List<EventCommentGet> ActivityUserCommentItem;

		public List<EventCommentGet> getActivityUserCommentItem() {
			return ActivityUserCommentItem;
		}

		public void setActivityUserCommentItem(
				List<EventCommentGet> activityUserCommentItem) {
			ActivityUserCommentItem = activityUserCommentItem;
		}

	}

	public static class ActivityDetail {

		private EventDetailGet Activity;
		private List<EventUser> ActivityUserItem;
		private List<EventCommentGet> ActivityUserCommentItem;

		public List<EventCommentGet> getActivityUserCommentItem() {
			return ActivityUserCommentItem;
		}

		public void setActivityUserCommentItem(
				List<EventCommentGet> activityUserCommentItem) {
			ActivityUserCommentItem = activityUserCommentItem;
		}

		public EventDetailGet getActivity() {
			return Activity;
		}

		public void setActivity(EventDetailGet activity) {
			Activity = activity;
		}

		public List<EventUser> getActivityUserItem() {
			return ActivityUserItem;
		}

		public void setActivityUserItem(List<EventUser> activityUserItem) {
			ActivityUserItem = activityUserItem;
		}

	}
}
