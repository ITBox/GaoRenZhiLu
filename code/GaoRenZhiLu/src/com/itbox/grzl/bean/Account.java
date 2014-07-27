package com.itbox.grzl.bean;

import android.provider.BaseColumns;
import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.itbox.grzl.Api;
import com.itbox.grzl.constants.AccountTable;

/**
 * 
 * @author malinkang 2014骞�5鏈�12鏃�
 * 
 */
@Table(name = AccountTable.TABLE_NAME, id = BaseColumns._ID)
public class Account extends BaseModel {
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
	@Column(name = AccountTable.COLUMN_ISMESSAGE)
	private Integer ismessage;
	@Column(name = AccountTable.COLUMN_ANSWERCOUNT)
	private String answercount;
	@Column(name = AccountTable.COLUMN_TEACHERTYPE)
	private String teachertype;
	@Column(name = AccountTable.COLUMN_JOBTYPE)
	private String jobtype;
	@Column(name = AccountTable.COLUMN_TEACHERLEVEL)
	private String teacherlevel;
	@Column(name = AccountTable.COLUMN_REMARKCOUNT)
	private String remarkcount;
	@Column(name = AccountTable.COLUMN_CONNECTKEY)
	private String connectkey;
	private String attentioncount;

	public String getAttentioncount() {
		return attentioncount;
	}

	public void setAttentioncount(String attentioncount) {
		this.attentioncount = attentioncount;
	}

	public String getConnectkey() {
		return connectkey;
	}

	public void setConnectkey(String connectkey) {
		this.connectkey = connectkey;
	}

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
		if (TextUtils.isEmpty(useravatarversion)) {
			return null;
		}
		return Api.User.getAvatarUrl(useravatarversion);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((answercount == null) ? 0 : answercount.hashCode());
		result = prime * result
				+ ((buycount == null) ? 0 : buycount.hashCode());
		result = prime * result
				+ ((ismessage == null) ? 0 : ismessage.hashCode());
		result = prime * result + ((jobtype == null) ? 0 : jobtype.hashCode());
		result = prime * result
				+ ((memberid == null) ? 0 : memberid.hashCode());
		result = prime * result
				+ ((remarkcount == null) ? 0 : remarkcount.hashCode());
		result = prime * result
				+ ((teacherlevel == null) ? 0 : teacherlevel.hashCode());
		result = prime * result
				+ ((teachertype == null) ? 0 : teachertype.hashCode());
		result = prime
				* result
				+ ((useravatarversion == null) ? 0 : useravatarversion
						.hashCode());
		result = prime * result
				+ ((userbalance == null) ? 0 : userbalance.hashCode());
		result = prime * result
				+ ((userbirthday == null) ? 0 : userbirthday.hashCode());
		result = prime * result
				+ ((userblood == null) ? 0 : userblood.hashCode());
		result = prime * result
				+ ((usercity == null) ? 0 : usercity.hashCode());
		result = prime * result
				+ ((usercodestate == null) ? 0 : usercodestate.hashCode());
		result = prime * result
				+ ((userdistrict == null) ? 0 : userdistrict.hashCode());
		result = prime * result
				+ ((useremail == null) ? 0 : useremail.hashCode());
		result = prime * result
				+ ((userheight == null) ? 0 : userheight.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		result = prime
				* result
				+ ((userintroduction == null) ? 0 : userintroduction.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result
				+ ((userphone == null) ? 0 : userphone.hashCode());
		result = prime * result
				+ ((userprovince == null) ? 0 : userprovince.hashCode());
		result = prime * result
				+ ((userrealname == null) ? 0 : userrealname.hashCode());
		result = prime * result + ((usersex == null) ? 0 : usersex.hashCode());
		result = prime * result
				+ ((userstate == null) ? 0 : userstate.hashCode());
		result = prime * result
				+ ((usertotalamount == null) ? 0 : usertotalamount.hashCode());
		result = prime * result
				+ ((usertype == null) ? 0 : usertype.hashCode());
		result = prime * result
				+ ((userweight == null) ? 0 : userweight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (answercount == null) {
			if (other.answercount != null)
				return false;
		} else if (!answercount.equals(other.answercount))
			return false;
		if (buycount == null) {
			if (other.buycount != null)
				return false;
		} else if (!buycount.equals(other.buycount))
			return false;
		if (ismessage == null) {
			if (other.ismessage != null)
				return false;
		} else if (!ismessage.equals(other.ismessage))
			return false;
		if (jobtype == null) {
			if (other.jobtype != null)
				return false;
		} else if (!jobtype.equals(other.jobtype))
			return false;
		if (memberid == null) {
			if (other.memberid != null)
				return false;
		} else if (!memberid.equals(other.memberid))
			return false;
		if (remarkcount == null) {
			if (other.remarkcount != null)
				return false;
		} else if (!remarkcount.equals(other.remarkcount))
			return false;
		if (teacherlevel == null) {
			if (other.teacherlevel != null)
				return false;
		} else if (!teacherlevel.equals(other.teacherlevel))
			return false;
		if (teachertype == null) {
			if (other.teachertype != null)
				return false;
		} else if (!teachertype.equals(other.teachertype))
			return false;
		if (useravatarversion == null) {
			if (other.useravatarversion != null)
				return false;
		} else if (!useravatarversion.equals(other.useravatarversion))
			return false;
		if (userbalance == null) {
			if (other.userbalance != null)
				return false;
		} else if (!userbalance.equals(other.userbalance))
			return false;
		if (userbirthday == null) {
			if (other.userbirthday != null)
				return false;
		} else if (!userbirthday.equals(other.userbirthday))
			return false;
		if (userblood == null) {
			if (other.userblood != null)
				return false;
		} else if (!userblood.equals(other.userblood))
			return false;
		if (usercity == null) {
			if (other.usercity != null)
				return false;
		} else if (!usercity.equals(other.usercity))
			return false;
		if (usercodestate == null) {
			if (other.usercodestate != null)
				return false;
		} else if (!usercodestate.equals(other.usercodestate))
			return false;
		if (userdistrict == null) {
			if (other.userdistrict != null)
				return false;
		} else if (!userdistrict.equals(other.userdistrict))
			return false;
		if (useremail == null) {
			if (other.useremail != null)
				return false;
		} else if (!useremail.equals(other.useremail))
			return false;
		if (userheight == null) {
			if (other.userheight != null)
				return false;
		} else if (!userheight.equals(other.userheight))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		if (userintroduction == null) {
			if (other.userintroduction != null)
				return false;
		} else if (!userintroduction.equals(other.userintroduction))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (userphone == null) {
			if (other.userphone != null)
				return false;
		} else if (!userphone.equals(other.userphone))
			return false;
		if (userprovince == null) {
			if (other.userprovince != null)
				return false;
		} else if (!userprovince.equals(other.userprovince))
			return false;
		if (userrealname == null) {
			if (other.userrealname != null)
				return false;
		} else if (!userrealname.equals(other.userrealname))
			return false;
		if (usersex == null) {
			if (other.usersex != null)
				return false;
		} else if (!usersex.equals(other.usersex))
			return false;
		if (userstate == null) {
			if (other.userstate != null)
				return false;
		} else if (!userstate.equals(other.userstate))
			return false;
		if (usertotalamount == null) {
			if (other.usertotalamount != null)
				return false;
		} else if (!usertotalamount.equals(other.usertotalamount))
			return false;
		if (usertype == null) {
			if (other.usertype != null)
				return false;
		} else if (!usertype.equals(other.usertype))
			return false;
		if (userweight == null) {
			if (other.userweight != null)
				return false;
		} else if (!userweight.equals(other.userweight))
			return false;
		return true;
	}

	public boolean isTeacher() {
		return getUsertype() == 1;
	}

}
