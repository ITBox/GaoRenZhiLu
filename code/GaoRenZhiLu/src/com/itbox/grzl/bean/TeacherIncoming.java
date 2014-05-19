package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.itbox.grzl.enumeration.PayState;

/**
 * 收入明细
 * 
 * @author byz
 * @date 2014-5-11下午6:53:43
 */
@Table(name = "teacher_incoming", id = TeacherIncoming.ID)
public class TeacherIncoming extends BaseModel {

	public static final String ID = "_id";
	public static final String PAYTYPE = "ti_paytype";
	public static final String PRICE = "ti_price";
	public static final String PAYID = "ti_payid";
	public static final String PAYSTATE = "ti_paystate";
	public static final String CREATETIME = "ti_create";

	@Column(name = TeacherIncoming.PAYTYPE)
	private String paytype;
	@Column(name = TeacherIncoming.PRICE)
	private double price;
	@Column(name = TeacherIncoming.PAYID)
	private long payid;
	@Column(name = TeacherIncoming.PAYSTATE)
	private int paystate;
	@Column(name = TeacherIncoming.CREATETIME)
	private String createtime;

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getPayid() {
		return payid;
	}

	public void setPayid(long payid) {
		this.payid = payid;
	}

	public int getPaystate() {
		return paystate;
	}

	public void setPaystate(int paystate) {
		this.paystate = paystate;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPayStateName() {
		return PayState.getName(paystate);
	}

}
