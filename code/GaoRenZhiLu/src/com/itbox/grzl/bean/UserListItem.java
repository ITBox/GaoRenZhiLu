package com.itbox.grzl.bean;

import java.io.Serializable;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.itbox.grzl.constants.UserListItemTable;

/**
 * 
 * @author malinkang 2014年5月19日
 * 
 * 
 * 
 */
@Table(name = UserListItemTable.TABLE_NAME, id = BaseColumns._ID)
public class UserListItem extends Model implements Serializable {

	@Column(name = UserListItemTable.COLUMN_USERID)
	private String userid;
	@Column(name = UserListItemTable.COLUMN_USERAVATARVERSION)
	private String useravatarversion;
	@Column(name = UserListItemTable.COLUMN_USERREALNAME)
	private String userrealname;
	@Column(name = UserListItemTable.COLUMN_JOBTYPE)
	private String jobtype;
	@Column(name = UserListItemTable.COLUMN_TEACHERLEVEL)
	private String teacherlevel;
	@Column(name = UserListItemTable.COLUMN_TEACHERTYPE)
	private String teachertype;
	@Column(name = UserListItemTable.COLUMN_USERINTRODUCTION)
	private String userintroduction;
	@Column(name = UserListItemTable.COLUMN_BUYCOUNT)
	private String buycount;
	@Column(name = UserListItemTable.COLUMN_ANSWERCOUNT)
	private String answercount;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUseravatarversion() {
		return useravatarversion;
	}

	public void setUseravatarversion(String useravatarversion) {
		this.useravatarversion = useravatarversion;
	}

	public String getUserrealname() {
		return userrealname;
	}

	public void setUserrealname(String userrealname) {
		this.userrealname = userrealname;
	}

	public String getTeacherlevel() {
		return teacherlevel;
	}

	public void setTeacherlevel(String teacherlevel) {
		this.teacherlevel = teacherlevel;
	}

	public String getUserintroduction() {
		return userintroduction;
	}

	public void setUserintroduction(String userintroduction) {
		this.userintroduction = userintroduction;
	}

	public String getBuycount() {
		return buycount;
	}

	public void setBuycount(String buycount) {
		this.buycount = buycount;
	}

	public String getAnswercount() {
		return answercount;
	}

	public void setAnswercount(String answercount) {
		this.answercount = answercount;
	}

	public String getJobtype() {
		return jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	public String getTeachertype() {
		return teachertype;
	}

	public void setTeachertype(String teachertype) {
		this.teachertype = teachertype;
	}
}
