package com.itbox.grzl.bean;

/**
 * 添加论坛评论
 * 
 * @author byz
 * @date 2014-5-11下午6:14:34
 */
public class CommentMarkAdd {

	private String id;
	private String userid;
	private String commentcontent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
