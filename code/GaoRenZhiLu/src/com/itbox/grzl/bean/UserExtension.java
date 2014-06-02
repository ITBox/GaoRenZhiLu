package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 更多资料
 * 
 * @author youzh
 * 
 */
@Table(name = "userextension", id = UserExtension.ID)
public class UserExtension extends BaseModel {

	public static final String ID = "_id";
	public static final String USERID = "ue_userid";
	public static final String USERCODE = "ue_usercode";
	public static final String USERBANK = "ue_userbank";
	public static final String BANKADDRESS = "ue_bankaddress";
	public static final String TEACHERTYPE = "ue_teachertype";
	public static final String JOBTYPE = "ue_jobtype";
	public static final String PICTUREPRICE = "ue_pictureprice";
	public static final String PHONEPRICE = "ue_phoneprice";
	public static final String STARTTIME = "ue_starttime";
	public static final String ENDTIME = "ue_endtime";
	public static final String TEACHERLEVEL = "ue_teacherlevel";
	public static final String REMARKCOUNT = "ue_remarkcount";

	@Column(name = UserExtension.USERID)
	private String userid;
	@Column(name = UserExtension.USERCODE)
	private String usercode;// 身份证
	@Column(name = UserExtension.USERBANK)
	private String userbank;// 银行卡号
	@Column(name = UserExtension.BANKADDRESS)
	private String bankaddress;// 开户行名称
	@Column(name = UserExtension.TEACHERTYPE)
	private String teachertype;// 导师类型
	@Column(name = UserExtension.JOBTYPE)
	private String jobtype;// 职位类型
	@Column(name = UserExtension.PICTUREPRICE)
	private String pictureprice;// 图文咨询价格
	@Column(name = UserExtension.PHONEPRICE)
	private String phoneprice;// 电话咨询价格
	@Column(name = UserExtension.STARTTIME)
	private String starttime;// 咨询时段开始时间
	@Column(name = UserExtension.ENDTIME)
	private String endtime;// 咨询时段结束时间
	@Column(name = UserExtension.TEACHERLEVEL)
	private String teacherlevel;// 导师级别
	@Column(name = UserExtension.REMARKCOUNT)
	private String remarkcount;// 导师评论数

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUserbank() {
		return userbank;
	}

	public void setUserbank(String userbank) {
		this.userbank = userbank;
	}

	public String getBankaddress() {
		return bankaddress;
	}

	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
	}

	public String getTeachertype() {
		return teachertype;
	}

	public void setTeachertype(String teachertype) {
		this.teachertype = teachertype;
	}

	public String getJobtype() {
		return jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	public String getPictureprice() {
		return pictureprice;
	}

	public void setPictureprice(String pictureprice) {
		this.pictureprice = pictureprice;
	}

	public String getPhoneprice() {
		return phoneprice;
	}

	public void setPhoneprice(String phoneprice) {
		this.phoneprice = phoneprice;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTeacherlevel() {
		return teacherlevel;
	}

	public void setTeacherlevel(String teacherlevel) {
		this.teacherlevel = teacherlevel;
	}

	public String getRemarkcount() {
		return remarkcount;
	}

	public void setRemarkcount(String remarkcount) {
		this.remarkcount = remarkcount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bankaddress == null) ? 0 : bankaddress.hashCode());
		result = prime * result + ((endtime == null) ? 0 : endtime.hashCode());
		result = prime * result + ((jobtype == null) ? 0 : jobtype.hashCode());
		result = prime * result + ((phoneprice == null) ? 0 : phoneprice.hashCode());
		result = prime * result + ((pictureprice == null) ? 0 : pictureprice.hashCode());
		result = prime * result + ((remarkcount == null) ? 0 : remarkcount.hashCode());
		result = prime * result + ((starttime == null) ? 0 : starttime.hashCode());
		result = prime * result + ((teacherlevel == null) ? 0 : teacherlevel.hashCode());
		result = prime * result + ((teachertype == null) ? 0 : teachertype.hashCode());
		result = prime * result + ((userbank == null) ? 0 : userbank.hashCode());
		result = prime * result + ((usercode == null) ? 0 : usercode.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		UserExtension other = (UserExtension) obj;
		if (bankaddress == null) {
			if (other.bankaddress != null)
				return false;
		} else if (!bankaddress.equals(other.bankaddress))
			return false;
		if (endtime == null) {
			if (other.endtime != null)
				return false;
		} else if (!endtime.equals(other.endtime))
			return false;
		if (jobtype == null) {
			if (other.jobtype != null)
				return false;
		} else if (!jobtype.equals(other.jobtype))
			return false;
		if (phoneprice == null) {
			if (other.phoneprice != null)
				return false;
		} else if (!phoneprice.equals(other.phoneprice))
			return false;
		if (pictureprice == null) {
			if (other.pictureprice != null)
				return false;
		} else if (!pictureprice.equals(other.pictureprice))
			return false;
		if (remarkcount == null) {
			if (other.remarkcount != null)
				return false;
		} else if (!remarkcount.equals(other.remarkcount))
			return false;
		if (starttime == null) {
			if (other.starttime != null)
				return false;
		} else if (!starttime.equals(other.starttime))
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
		if (userbank == null) {
			if (other.userbank != null)
				return false;
		} else if (!userbank.equals(other.userbank))
			return false;
		if (usercode == null) {
			if (other.usercode != null)
				return false;
		} else if (!usercode.equals(other.usercode))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
    
}
