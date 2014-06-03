package com.itbox.grzl.bean;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.itbox.grzl.constants.UserLevelTable;

@Table(name = UserLevelTable.TABLE_NAME, id = BaseColumns._ID)
public class UserLevel extends Model {

	@Column(name = UserLevelTable.COLUMN_MEMBERID)
	private Integer memberid;
	@Column(name = UserLevelTable.COLUMN_TITLE)
	private String title;
	@Column(name = UserLevelTable.COLUMN_PRICE)
	private Float price;
	@Column(name = UserLevelTable.COLUMN_PHOTO)
	private String photo;
	@Column(name = UserLevelTable.COLUMN_DISCOUNT)
	private Float discount;
	@Column(name = UserLevelTable.COLUMN_CREATETIME)
	private String createtime;
	@Column(name = UserLevelTable.COLUMN_USER_ID)
	private String userid;

	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
