package com.itbox.grzl.bean;

import com.itbox.grzl.enumeration.EventType;

/**
 * 活动详情
 * 
 * @author baoboy
 * @date 2014-5-24下午5:06:52
 */
public class EventDetailGet {

	private String userid;
	private String typeid;
	private String title;
	private String begintime;
	private String endtime;
	private String userprovince;
	private String usercity;
	private String userdistrict;
	private String address;
	private String longitude; // 经度
	private String latitude; // 纬度
	private String activitypicture;
	private String activitydescription;
	private String peoplecount;
	private String activityphone;
	private String activityid;
	private String commentcount;
	private String joinnumber;
	private String createtime;
	private int isinterest;
	private int isjoin;

	public String getActivityId() {
		return activityid;
	}

	public void setActivityId(String activityId) {
		this.activityid = activityId;
	}

	public String getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}

	public String getJoinnumber() {
		return joinnumber;
	}

	public void setJoinnumber(String joinnumber) {
		this.joinnumber = joinnumber;
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

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getActivitypicture() {
		return activitypicture;
	}

	public void setActivitypicture(String activitypicture) {
		this.activitypicture = activitypicture;
	}

	public String getActivitydescription() {
		return activitydescription;
	}

	public void setActivitydescription(String activitydescription) {
		this.activitydescription = activitydescription;
	}

	public String getPeoplecount() {
		return peoplecount;
	}

	public void setPeoplecount(String peoplecount) {
		this.peoplecount = peoplecount;
	}

	public String getActivityphone() {
		return activityphone;
	}

	public void setActivityphone(String activityphone) {
		this.activityphone = activityphone;
	}

	public String getTypeName() {
		int type = Integer.parseInt(typeid);
		return EventType.getName(type);
	}

	public void setInterest(boolean b) {
		isinterest = b ? 1 : 2;
	}

	public void setJoin(boolean b) {
		isjoin = b ? 1 : 2;
	}

	public boolean isInterest() {
		return isinterest == 1;
	}

	public boolean isJoin() {
		return isjoin == 1;
	}
}
