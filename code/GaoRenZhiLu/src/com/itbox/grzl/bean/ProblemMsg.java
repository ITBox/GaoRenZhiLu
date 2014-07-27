package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.itbox.grzl.AppContext;

/**
 * 咨询消息
 * 
 * @author byz
 * @date 2014-5-11下午6:14:34
 */
@Table(name = "problem_msg", id = ProblemMsg.ID)
public class ProblemMsg extends BaseModel {

	public static final String ID = "_id";
	public static final String PM_ID = "pm_id";
	public static final String COMMENTID = "pm_commentid";
	public static final String USERID = "pm_userid";
	public static final String USERAVATARVERSION = "pm_useravatarversion";
	public static final String USERNAME = "pm_username";
	public static final String COMMENTCONTENT = "pm_commentcontent";
	public static final String CREATETIME = "pm_createtime";

	@Column(name = ProblemMsg.PM_ID)
	private int id;
	@Column(name = ProblemMsg.COMMENTID)
	private String commentid;
	@Column(name = ProblemMsg.USERID)
	private int userid;
	@Column(name = ProblemMsg.USERAVATARVERSION)
	private String useravatarversion;
	@Column(name = ProblemMsg.USERNAME)
	private String username;
	@Column(name = ProblemMsg.COMMENTCONTENT)
	private String commentcontent;
	@Column(name = ProblemMsg.CREATETIME)
	private String createtime;

	public int getViewType() {
		return AppContext.getInstance().getAccount().getUserid() == userid ? 0
				: 1;
	}

	public int getMsgId() {
		return id;
	}

	public void setMsgId(int id) {
		this.id = id;
	}

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

}
