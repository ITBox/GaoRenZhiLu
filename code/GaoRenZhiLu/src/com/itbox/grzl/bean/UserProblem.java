package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.baoyz.pg.Parcelable;

/**
 * 获取免费咨询信息
 * 
 * @author byz
 * @date 2014-5-11下午6:14:34
 */
@Parcelable
@Table(name = "user_problem", id = UserProblem.ID)
public class UserProblem extends BaseModel {

	public static final String TYPE_FREE = "3";
	public static final String TYPE_PHOTO = "2";
	public static final String TYPE_PHONE = "1";

	public static final String ID = "_id";
	public static final String UP_ID = "up_id";
	public static final String TITLE = "up_title";
	public static final String USERID = "up_userid";
	public static final String USERNAME = "up_username";
	public static final String PHOTO = "up_photo";
	public static final String CONTENTS = "up_contents";
	public static final String TEACHERUSERID = "up_replacecount";
	public static final String JOBTYPE = "up_jobtype";
	public static final String CONSULTATIONTYPE = "up_consultationtype";
	public static final String CREATETIME = "up_createtime";

	@Column(name = UserProblem.UP_ID)
	private int id;
	@Column(name = UserProblem.TITLE)
	private String title;
	@Column(name = UserProblem.USERID)
	private String userid;
	@Column(name = UserProblem.TEACHERUSERID)
	private String teacherid;
	@Column(name = UserProblem.CONTENTS)
	private String contents;
	@Column(name = UserProblem.PHOTO)
	private String photo;
	@Column(name = UserProblem.JOBTYPE)
	private int jobType;
	@Column(name = UserProblem.CONSULTATIONTYPE)
	private int consultationType;
	@Column(name = UserProblem.CREATETIME)
	private String createtime;
	@Column(name = UserProblem.USERNAME)
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getProblemId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public int getConsultationType() {
		return consultationType;
	}

	public void setConsultationType(int consultationType) {
		this.consultationType = consultationType;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}
