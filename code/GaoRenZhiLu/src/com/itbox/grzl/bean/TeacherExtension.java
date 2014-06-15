package com.itbox.grzl.bean;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.itbox.grzl.constants.TeacherExtensionTable;

/**
 * { "userid": 14, "usercode": 422801132265623230, "userbank":
 * "321321432432432", "bankaddress": "北京市昌平区支行", "teachertype": 1, "jobtype": 6,
 * "pictureprice": 100, "phoneprice": 100, "starttime": 20, "endtime": 22,
 * "teacherlevel": 1, "remarkcount": 1 }
 * 
 * @author malinkang 2014年5月28日
 * 
 */
@Table(name = TeacherExtensionTable.TABLE_NAME)
public class TeacherExtension extends Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7065479262403661229L;
	@Column(name = TeacherExtensionTable.COLUMN_USERID)
	private Integer userid;
	@Column(name = TeacherExtensionTable.COLUMN_USERCODE)
	private String usercode;
	@Column(name = TeacherExtensionTable.COLUMN_USERBANK)
	private String userbank;
	@Column(name = TeacherExtensionTable.COLUMN_BANKADDRESS)
	private String bankaddress;
	@Column(name = TeacherExtensionTable.COLUMN_TEACHERTYPE)
	private String teachertype;
	@Column(name = TeacherExtensionTable.COLUMN_JOBTYPE)
	private String jobtype;
	@Column(name = TeacherExtensionTable.COLUMN_PICTUREPICE)
	private String picturepice;
	@Column(name = TeacherExtensionTable.COLUMN_PHONEPRICE)
	private String phoneprice;
	@Column(name = TeacherExtensionTable.COLUMN_STARTTIME)
	private String starttime;
	@Column(name = TeacherExtensionTable.COLUMN_ENDTIME)
	private String endtime;
	@Column(name = TeacherExtensionTable.COLUMN_TEACHERLEVEL)
	private String teacherlevel;
	@Column(name = TeacherExtensionTable.COLUMN_REMARKCOUNT)
	private String remarkcount;

	private double finalPhoneprice;
	private double finalPictureprice;

	public double getFinalPhoneprice() {
		return (double)Math.round(finalPhoneprice*100)/100;
	}

	public void setFinalPhoneprice(double finalPhoneprice) {
		this.finalPhoneprice = finalPhoneprice;
	}

	public double getFinalPictureprice() {
		return (double)Math.round(finalPictureprice*100)/100;
	}

	public void setFinalPictureprice(double finalPictureprice) {
		this.finalPictureprice = finalPictureprice;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
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

	public String getPicturepice() {
		return picturepice != null ? picturepice : "0.00";
	}

	public void setPicturepice(String picturepice) {
		this.picturepice = picturepice;
	}

	public String getPhoneprice() {
		return phoneprice != null ? phoneprice : "0.00";
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

}
