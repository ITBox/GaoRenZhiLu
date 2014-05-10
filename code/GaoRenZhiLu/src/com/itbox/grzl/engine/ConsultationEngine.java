package com.itbox.grzl.engine;

import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
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

	public static class GetTeacher {
		private int orderby;
		private String realname;
		private int pagesize;
		private int pageindex;
		private int jobtype;
		private int teachertype;

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

		public int getPagesize() {
			return pagesize;
		}

		public void setPagesize(int pagesize) {
			this.pagesize = pagesize;
		}

		public int getPageindex() {
			return pageindex;
		}

		public void setPageindex(int pageindex) {
			this.pageindex = pageindex;
		}

		public int getJobtype() {
			return jobtype;
		}

		public void setJobtype(int jobtype) {
			this.jobtype = jobtype;
		}

		public int getTeachertype() {
			return teachertype;
		}

		public void setTeachertype(int teachertype) {
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

}
