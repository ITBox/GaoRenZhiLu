package com.itbox.grzl.bean;

/**
 * 添加论坛信息
 * @author byz
 * @date 2014-5-11下午6:14:34
 */
public class CommentAdd {

	private String title;
	private String userid;
	private String commentcontent;
	private String photo;

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

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
