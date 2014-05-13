package com.itbox.grzl.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.itbox.grzl.constants.AccountTable;

/**
 * 
 * @author malinkang 2014年5月12日
 * 
 */
@Table(name = AccountTable.TABLE_NAME)
public class Account extends Model {
	@Column(name = AccountTable.COLUMN_USERID)
	private Integer userid;
	@Column(name = AccountTable.COLUMN_USERNAME)
	private String username;
	@Column(name = AccountTable.COLUMN_USERPHONE)
	private String userphone;
	@Column(name = AccountTable.COLUMN_USEREMAIL)
	private String useremail;
	@Column(name = AccountTable.COLUMN_USERREALNAME)
	private String userrealname;
	@Column(name = AccountTable.COLUMN_USERAVATARVERSION)
	private String useravatarversion;
	@Column(name = AccountTable.COLUMN_USERBALANCE)
	private String userbalance;
	@Column(name = AccountTable.COLUMN_USERTOTALAMOUNT)
	private String usertotalamount;
	@Column(name = AccountTable.COLUMN_USERSEX)
	private String usersex;
	@Column(name = AccountTable.COLUMN_USERPROVINCE)
	private String userprovince;
	@Column(name = AccountTable.COLUMN_USERCITY)
	private String usercity;
	@Column(name = AccountTable.COLUMN_USERDISTRICT)
	private String userdistrict;
	@Column(name = AccountTable.COLUMN_USERCODESTATE)
	private String usercodestate;
	@Column(name = AccountTable.COLUMN_USERTYPE)
	private Integer usertype;
	@Column(name = AccountTable.COLUMN_USERHEIGHT)
	private String userheight;
	@Column(name = AccountTable.COLUMN_USERBLOOD)
	private String userblood;
	@Column(name = AccountTable.COLUMN_USERWEIGHT)
	private String userweight;
	@Column(name = AccountTable.COLUMN_USERINTRODUCTION)
	private String userintroduction;
	@Column(name = AccountTable.COLUMN_BUYCOUNT)
	private String buycount;
	@Column(name = AccountTable.COLUMN_USERSTATE)
	private Integer userstate;
	@Column(name = AccountTable.COLUMN_USERBIRTHDAY)
	private String userbirthday;
	@Column(name = AccountTable.COLUMN_MEMBERID)
	private Integer memberid;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUserrealname() {
		return userrealname;
	}

	public void setUserrealname(String userrealname) {
		this.userrealname = userrealname;
	}

	public String getUseravatarversion() {
		return useravatarversion;
	}

	public void setUseravatarversion(String useravatarversion) {
		this.useravatarversion = useravatarversion;
	}

	public String getUserbalance() {
		return userbalance;
	}

	public void setUserbalance(String userbalance) {
		this.userbalance = userbalance;
	}

	public String getUsertotalamount() {
		return usertotalamount;
	}

	public void setUsertotalamount(String usertotalamount) {
		this.usertotalamount = usertotalamount;
	}

	public String getUsersex() {
		return usersex;
	}

	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}

	public String getUserprovince() {
		return userprovince;
	}

	public void setUserprovince(String userprovince) {
		this.userprovince = userprovince;
	}

	public String getUsercity() {
		return usercity;
	}

	public void setUsercity(String usercity) {
		this.usercity = usercity;
	}

	public String getUserdistrict() {
		return userdistrict;
	}

	public void setUserdistrict(String userdistrict) {
		this.userdistrict = userdistrict;
	}

	public String getUsercodestate() {
		return usercodestate;
	}

	public void setUsercodestate(String usercodestate) {
		this.usercodestate = usercodestate;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getUserheight() {
		return userheight;
	}

	public void setUserheight(String userheight) {
		this.userheight = userheight;
	}

	public String getUserweight() {
		return userweight;
	}

	public void setUserweight(String userweight) {
		this.userweight = userweight;
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

	public Integer getUserstate() {
		return userstate;
	}

	public void setUserstate(Integer userstate) {
		this.userstate = userstate;
	}

	public String getUserbirthday() {
		return userbirthday;
	}

	public void setUserbirthday(String userbirthday) {
		this.userbirthday = userbirthday;
	}

	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public String getUserblood() {
		return userblood;
	}

	public void setUserblood(String userblood) {
		this.userblood = userblood;
	}

}
