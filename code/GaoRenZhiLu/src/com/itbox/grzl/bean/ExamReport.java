package com.itbox.grzl.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 测评记录
 * 
 * @author byz
 * 
 */
@Table(name = "exam_report", id = "_id")
public class ExamReport extends Model {

	@Column(name="er_type")
	private String type;
	@Column(name="er_userid")
	private String userid;
	@Column(name="er_contents")
	private String contents;
	@Column(name="er_username")
	private String username;
	@Column(name="er_useravatarversion")
	private String useravatarversion;
	@Column(name="er_createtime")
	private String createtime;

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

}
