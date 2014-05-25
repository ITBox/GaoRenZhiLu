package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 获取活动评论信息
 * 
 * @author byz
 * @date 2014-5-11下午6:14:34
 */
@Table(name = "event_comment_list", id = EventCommentGet.ID)
public class EventCommentGet extends BaseModel {

	public static final String ID = "_id";
	public static final String USERID = "ec_userid";
	public static final String ACTIVITYID = "ec_activityid";
	public static final String COMMENTCONTENT = "ec_commentcontent";
	public static final String COMMENTID = "ec_commentid";
	public static final String CREATETIME = "ec_createtime";

	@Column(name = EventCommentGet.USERID)
	private String userid;
	@Column(name = EventCommentGet.ACTIVITYID)
	private String activityid;
	@Column(name = EventCommentGet.COMMENTCONTENT)
	private String commentcontent;
	@Column(name = EventCommentGet.COMMENTID)
	private int CommentId;
	@Column(name = EventCommentGet.CREATETIME)
	private String CreateTime;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}

	public int getCommentId() {
		return CommentId;
	}

	public void setCommentId(int commentId) {
		CommentId = commentId;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

}
