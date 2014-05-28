package com.itbox.grzl.bean;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 活动信息
 * 
 * @author baoboy
 * @date 2014-5-24下午5:06:52
 */
@Table(name = "event_search_list", id = EventSearchGet.ID)
public class EventSearchGet extends BaseModel{

	public static final String ID = "_id";
	public static final String USERID = "el_userid";
	public static final String TYPEID = "el_typeid";
	public static final String TITLE = "el_title";
	public static final String BEGINTIME = "el_begintime";
	public static final String ENDTIME = "el_endtime";
	public static final String USERPROVINCE = "el_userprovince";
	public static final String USERCITY = "el_usercity";
	public static final String USERDISTRICT = "el_userdistrict";
	public static final String ADDRESS = "el_address";
	public static final String LONGITUDE = "el_longitude"; // 经度
	public static final String LATITUDE = "el_latitude"; // 纬度
	public static final String ACTIVITYPICTURE = "el_activitypicture";
	public static final String ACTIVITYDESCRIPTION = "el_activitydescription";
	public static final String PEOPLECOUNT = "el_peoplecount";
	public static final String ACTIVITYPHONE = "el_activityphone";
	public static final String ACTIVITYID = "el_activityId";
	public static final String COMMENTCOUNT = "el_commentcount";
	public static final String JOINNUMBER = "el_joinnumber";
	public static final String CREATETIME = "el_createtime";

	@Column(name = EventSearchGet.USERID)
	private String userid;
	@Column(name = EventSearchGet.TYPEID)
	private String typeid;
	@Column(name = EventSearchGet.TITLE)
	private String title;
	@Column(name = EventSearchGet.BEGINTIME)
	private String begintime;
	@Column(name = EventSearchGet.ENDTIME)
	private String endtime;
	@Column(name = EventSearchGet.USERPROVINCE)
	private String userprovince;
	@Column(name = EventSearchGet.USERCITY)
	private String usercity;
	@Column(name = EventSearchGet.USERDISTRICT)
	private String userdistrict;
	@Column(name = EventSearchGet.ADDRESS)
	private String address;
	@Column(name = EventSearchGet.LONGITUDE)
	private String longitude; // 经度
	@Column(name = EventSearchGet.LATITUDE)
	private String latitude; // 纬度
	@Column(name = EventSearchGet.ACTIVITYPICTURE)
	private String activitypicture;
	@Column(name = EventSearchGet.ACTIVITYDESCRIPTION)
	private String activitydescription;
	@Column(name = EventSearchGet.PEOPLECOUNT)
	private String peoplecount;
	@Column(name = EventSearchGet.ACTIVITYPHONE)
	private String activityphone;
	@Column(name = EventSearchGet.ACTIVITYID)
	private String activityid;
	@Column(name = EventSearchGet.COMMENTCOUNT)
	private String commentcount;
	@Column(name = EventSearchGet.JOINNUMBER)
	private String joinnumber;
	@Column(name = EventSearchGet.CREATETIME)
	private String createtime;

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

}
