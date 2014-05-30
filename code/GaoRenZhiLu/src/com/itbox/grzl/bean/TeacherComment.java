package com.itbox.grzl.bean;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.itbox.grzl.constants.TeacherCommentTable;

@Table(name = TeacherCommentTable.TABLE_NAME, id = BaseColumns._ID)
public class TeacherComment extends Model {

	@Column(name = TeacherCommentTable.COLUMN_COMMENTID)
	private String commentid;
	@Column(name = TeacherCommentTable.COLUMN_TEACHERUSERID)
	private String teacheruserid;
	@Column(name = TeacherCommentTable.COLUMN_USERID)
	private String userid;
	@Column(name = TeacherCommentTable.COLUMN_COMMENTCONTENT)
	private String commentcontent;
	@Column(name = TeacherCommentTable.COLUMN_SCORE)
	private String score;
	@Column(name = TeacherCommentTable.COLUMN_CREATETIME)
	private String createtime;
	@Column(name = TeacherCommentTable.COLUMN_USERAVATARVERSION)
	private String useravatarversion;
	@Column(name = TeacherCommentTable.COLUMN_USERNAME)
	private String username;

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUseravatarversion() {
		return useravatarversion;
	}

	public void setUseravatarversion(String useravatarversion) {
		this.useravatarversion = useravatarversion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
