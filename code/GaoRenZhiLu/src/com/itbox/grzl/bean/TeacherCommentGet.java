package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 获取老师评价
 * 
 * @author byz
 * @date 2014-5-11下午6:14:34
 */
 @Table(name = "teacher_comment_list", id = TeacherCommentGet.ID)
public class TeacherCommentGet extends BaseModel {

	public static final String ID = "_id";
	public static final String USERID = "tc_userid";
	public static final String TEACHERUSERID = "tc_teacheruserid";
	public static final String COMMENTCONTENT = "tc_commentcontent";
	public static final String COMMENTID = "tc_commentid";
	public static final String SCORE = "tc_score";
	public static final String CREATETIME = "tc_createtime";
	public static final String USERNAME = "tc_username";
	public static final String USERAVATARVERSION = "tc_useravatarversion";

	@Column(name = TeacherCommentGet.COMMENTID)
	private int commentid;
	@Column(name = TeacherCommentGet.TEACHERUSERID)
	private String teacheruserid;
	@Column(name = TeacherCommentGet.USERID)
	private String userid;
	@Column(name = TeacherCommentGet.COMMENTCONTENT)
	private String commentcontent;
	@Column(name = TeacherCommentGet.SCORE)
	private float score;
	@Column(name = TeacherCommentGet.CREATETIME)
	private String createtime;
	@Column(name = TeacherCommentGet.USERNAME)
	private String username;
	@Column(name = TeacherCommentGet.USERAVATARVERSION)
	private String useravatarversion;

	public int getCommentid() {
		return commentid;
	}

	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}

	public String getTeacheruserid() {
		return teacheruserid;
	}

	public void setTeacheruserid(String teacheruserid) {
		this.teacheruserid = teacheruserid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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

}
