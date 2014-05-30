package com.itbox.grzl.bean;

/**
 * 参加活动的用户
 * 
 * @author baoboy
 * @date 2014-5-26下午10:32:03
 */
public class EventUser {

	private String activityid;
	private String userid;
	private String applytime;
	private String applystate;
	private String initiator;
	private String createtime;
	private String useravatarversion;

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getApplytime() {
		return applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public String getApplystate() {
		return applystate;
	}

	public void setApplystate(String applystate) {
		this.applystate = applystate;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUseravatarversion() {
		return useravatarversion;
	}

	public void setUseravatarversion(String useravatarversion) {
		this.useravatarversion = useravatarversion;
	}

}
