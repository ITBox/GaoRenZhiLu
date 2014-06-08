package com.itbox.grzl.bean;

import android.database.Cursor;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 测评记录
 * 
 * @author byz
 * 
 */
@Table(name = "exam_report", id = ExamReport.ID)
public class ExamReport extends Model {

	public static final String ID = "_id";
	public static final String TYPE = "er_type";
	public static final String USERID = "er_userid";
	public static final String CONTENTS = "er_contents";
	public static final String USERNAME = "er_username";
	public static final String USERAVATARVERSION = "er_useravatarversion";
	public static final String CREATETIME = "er_createtime";
	public static final String JOB = "er_job";

	@Column(name = ExamReport.TYPE)
	private String type;
	@Column(name = ExamReport.USERID)
	private String userid;
	@Column(name = ExamReport.CONTENTS)
	private String contents;
	@Column(name = ExamReport.USERNAME)
	private String username;
	@Column(name = ExamReport.USERAVATARVERSION)
	private String useravatarversion;
	@Column(name = ExamReport.CREATETIME)
	private String createtime;
	@Column(name = ExamReport.JOB)
	private String job;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUseravatarversion() {
		return useravatarversion;
	}

	public void setUseravatarversion(String useravatarversion) {
		this.useravatarversion = useravatarversion;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getJob() {
		if (job == null) {
			job = "无";
		}
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "ExamReport [type=" + type + ", userid=" + userid
				+ ", contents=" + contents + ", username=" + username
				+ ", useravatarversion=" + useravatarversion + ", createtime="
				+ createtime + "]";
	}
}
