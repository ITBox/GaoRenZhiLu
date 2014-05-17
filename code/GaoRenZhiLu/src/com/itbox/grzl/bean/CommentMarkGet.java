package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 获取论坛评论
 * 
 * @author byz
 * @date 2014-5-11下午6:14:34
 */
@Table(name = "comment_mark", id = CommentMarkGet.ID)
public class CommentMarkGet extends BaseModel {

	public static final String ID = "_id";
	public static final String COMMENTID = "cm_commentid";
	public static final String USERAVATARVERSION = "cm_useravatarversion";
	public static final String USERNAME = "cm_username";
	public static final String COMMENTCONTENT = "cm_commentcontent";
	public static final String CREATETIME = "cm_createtime";

	@Column(name = CommentMarkGet.COMMENTID)
	private String commentid;
	@Column(name = CommentMarkGet.USERAVATARVERSION)
	private String useravatarversion;
	@Column(name = CommentMarkGet.USERNAME)
	private String username;
	@Column(name = CommentMarkGet.COMMENTCONTENT)
	private String commentcontent;
	@Column(name = CommentMarkGet.CREATETIME)
	private String createtime;

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
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

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "CommentMarkGet [commentid=" + commentid
				+ ", useravatarversion=" + useravatarversion + ", username="
				+ username + ", commentcontent=" + commentcontent
				+ ", createtime=" + createtime + "]";
	}

}
