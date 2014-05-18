package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 提现记录
 * 
 * @author byz
 * @date 2014-5-11下午6:53:43
 */
@Table(name = "teacher_withdrawals", id = TeacherWithdrawals.ID)
public class TeacherWithdrawals extends BaseModel {

	public static final String ID = "_id";
	public static final String TWID = "tw_id";
	public static final String USERID = "tw_userid";
	public static final String PRICE = "tw_price";
	public static final String STATUS = "tw_status";
	public static final String PROCESSINGDATE = "tw_processingdate";
	public static final String DESCRIPTION = "tw_description";
	public static final String CREATETIME = "tw_createtime";

	@Column(name = TeacherWithdrawals.TWID)
	private String id;
	@Column(name = TeacherWithdrawals.USERID)
	private String userid;
	@Column(name = TeacherWithdrawals.PRICE)
	private double price;
	@Column(name = TeacherWithdrawals.STATUS)
	private int status;
	@Column(name = TeacherWithdrawals.PROCESSINGDATE)
	private String processingdate;
	@Column(name = TeacherWithdrawals.DESCRIPTION)
	private String description;
	@Column(name = TeacherWithdrawals.CREATETIME)
	private String createtime;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getTwId() {
		return id;
	}

	public void setTwId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProcessingdate() {
		return processingdate;
	}

	public void setProcessingdate(String processingdate) {
		this.processingdate = processingdate;
	}

}
