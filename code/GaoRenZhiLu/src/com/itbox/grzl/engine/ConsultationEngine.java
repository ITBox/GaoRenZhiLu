package com.itbox.grzl.engine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.UserProblem;
import com.loopj.android.http.RequestParams;

/**
 * 咨询业务逻辑
 * 
 * @author baoyz
 * 
 *         2014-5-3 下午6:02:20
 * 
 */
public class ConsultationEngine {

	// IT/通讯、电子/互联网、金融、建筑房地产、制造业、物流/仓储、文化/传媒、影视/娱乐、教育、矿产/能源、农林牧渔、医药、商业服务
	public static final String[] JOB_TYPES = { "IT/互联网", "电子/互联网", "金融",
			"建筑房地产", "制造业", "物流/仓储", "文化/传媒", "影视/娱乐", "教育", "矿产/能源", "农林牧渔",
			"医药", "商业服务" };
	public static final String[] TEACHER_TYPES = { "专业导师", "人力导师" };

	/**
	 * 咨询搜索
	 * 
	 * @param handler
	 * @param info
	 */
	public static void getTeacher(GetTeacher info, ResponseHandler handler) {
		Net.request(info, Api.getUrl(Api.Consultation.getteacher), handler);
	}

	/**
	 * 马上提问
	 * 
	 * @param handler
	 */
	public static void probleming(ConsultationInfo info, ResponseHandler handler) {
		Net.request(info, Api.getUrl(Api.Consultation.probleming), handler);
	}

	public static void getTeacherBooking(String teacherid,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", teacherid);
		params.put("placedate", getToday());
		Net.request(params, Api.getUrl(Api.Consultation.getteacherbooking),
				handler);
	}

	public static void buyPhone(String teacherid, String discountprice,
			String price, String paydate, String hour, String min,
			String phone, boolean isClient, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("teacheruserid", teacherid);
		params.put("paydate", paydate);
		params.put("placetime", hour);
		params.put("placetimedetail", min);
		params.put("userphone", phone);
		params.put("price", price);
		params.put("discountprice", discountprice);
		Net.request(params, Api.getUrl(isClient ? Api.Alipay.Buy_Phone_Client
				: Api.Alipay.Buy_Phone_Web), handler);
	}

	public static void buyPicture(String teacherid, String discountprice,
			String price, boolean isClient, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("teacheruserid", teacherid);
		params.put("price", price);
		params.put("discountprice", discountprice);
		Net.request(params, Api.getUrl(isClient ? Api.Alipay.Buy_Picture_Client
				: Api.Alipay.Buy_Picture_Web), handler);
	}

	public static void buyMember(String memberid, boolean isClient,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()
				.toString());
		params.put("memberid", memberid);
		Net.request(params, Api.getUrl(isClient ? Api.Alipay.Buy_Member_Clinet
				: Api.Alipay.Buy_Member_Web), handler);
	}

	public static void issolve(String teacherid, String problemId,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("teacheruserid", teacherid);
		params.put("id", problemId);
		Net.request(params, Api.getUrl(Api.Consultation.ISSOLVE), handler);
	}

	public static void getProblemDetail(String problemId,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("id", problemId);
		params.put("pagesize", "20");
		params.put("pageindex", "1");
		Net.request(params, Api.getUrl(Api.Consultation.GETUSERPROBLEMDETAIL),
				handler);
	}

	public static String getToday() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 我的咨询
	 * 
	 * @param handler
	 * @param info
	 */
	public static void getMyConsultation(String userid, String teacheruserid,
			String consultationtype, String orderby, int page,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("orderby", orderby);
		params.put("pagesize", "20");
		params.put("pageindex", page + "");
		params.put("consultationtype", consultationtype);
		params.put("userid", userid);
		params.put("teacheruserid", teacheruserid);
		Net.request(params, Api.getUrl(Api.Consultation.GETMYPROBLEM), handler);
	}

	/**
	 * 搜索免费咨询
	 * 
	 * @param handler
	 * @param info
	 */
	public static void searchFree(String orderby, String jobtype, int page,
			ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("orderby", orderby);
		params.put("pagesize", "20");
		params.put("pageindex", page + "");
		params.put("jobtype", jobtype);
		Net.request(params, Api.getUrl(Api.Consultation.searchprobleming),
				handler);
	}

	public static class GetTeacher {
		private int orderby;
		private String realname;
		private String pagesize = "20";
		private String pageindex;
		private String jobtype;
		private String teachertype;

		public int getOrderby() {
			return orderby;
		}

		public void setOrderby(int orderby) {
			this.orderby = orderby;
		}

		public String getRealname() {
			return realname;
		}

		public void setRealname(String realname) {
			this.realname = realname;
		}

		public String getPagesize() {
			return pagesize;
		}

		public void setPagesize(String pagesize) {
			this.pagesize = pagesize;
		}

		public String getPageindex() {
			return pageindex;
		}

		public void setPageindex(String pageindex) {
			this.pageindex = pageindex;
		}

		public String getJobtype() {
			return jobtype;
		}

		public void setJobtype(String jobtype) {
			this.jobtype = jobtype;
		}

		public String getTeachertype() {
			return teachertype;
		}

		public void setTeachertype(String teachertype) {
			this.teachertype = teachertype;
		}

	}

	/**
	 * 提问信息
	 * 
	 * @author baoyz
	 * 
	 *         2014-5-3 下午6:10:06
	 * 
	 */
	public static class ConsultationInfo {
		private String title;
		private int jobtype;
		private String photo;
		private String contents;
		private int userId;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getJobtype() {
			return jobtype;
		}

		public void setJobtype(int jobtype) {
			this.jobtype = jobtype;
		}

		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getContents() {
			return contents;
		}

		public void setContents(String contents) {
			this.contents = contents;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

	}

	public static class Result {
		private int result;

		public int getResult() {
			return result;
		}

		public void setResult(int result) {
			this.result = result;
		}

		public boolean isSuccess() {
			return result == 1;
		}

	}

	/**
	 * 获取会员级别
	 * 
	 * @param hanlder
	 */
	public static void getUserMember(ResponseHandler hanlder) {
		Net.request(null, Api.getUrl(Api.Consultation.GETUSERMEMBER), hanlder);
	}

	public static CharSequence getTeacherType(String teachertype) {
		try {
			return TEACHER_TYPES[Integer.parseInt(teachertype) - 1];
		} catch (Exception e) {
		}
		return "";
	}

	public static CharSequence getJobType(String jobType) {
		try {
			return JOB_TYPES[Integer.parseInt(jobType) - 1];
		} catch (Exception e) {
		}
		return "";
	}

	public static class UserProblemItem {
		private List<UserProblem> UserProblemItem;

		public List<UserProblem> getUserProblemItem() {
			return UserProblemItem;
		}

		public void setUserProblemItem(List<UserProblem> userProblemItem) {
			UserProblemItem = userProblemItem;
		}

	}

}
