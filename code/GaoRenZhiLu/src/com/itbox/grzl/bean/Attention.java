package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "attention_list", id = Attention.ID)
public class Attention extends BaseModel {

	public static final String ID = "_id";
	public static final String USERID = "al_userid";
	public static final String USERNAME = "al_username";
	public static final String USERAVATARVERSION = "al_useravatarversion";
	public static final String USERSEX = "al_usersex";
	public static final String USERBIRTHDAY = "al_userbirthday";

	@Column(name = Attention.USERID)
	private String userid;
	@Column(name = Attention.USERNAME)
	private String username;
	@Column(name = Attention.USERAVATARVERSION)
	private String useravatarversion;
	@Column(name = Attention.USERSEX)
	private String usersex;
	@Column(name = Attention.USERBIRTHDAY)
	private String userbirthday;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getUsersex() {
		return usersex == "1" ? "男" : "女";
	}

	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}

	public String getUserbirthday() {
		return userbirthday;
	}

	public void setUserbirthday(String userbirthday) {
		this.userbirthday = userbirthday;
	}

}
