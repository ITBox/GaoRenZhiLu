package com.itbox.grzl.event.bean;

import com.itbox.grzl.Api;

/**
 * 活动列表(发出)
 * @author hyh 
 * creat_at：2013-9-6-上午9:00:27
 */
public class EventListTModel {
	private int userprovince;
	private int usercity;
	private int pagesize = Api.PAGE_SIZE;
	private int pageindex = Api.PAGE_DEFAULT_INDEX;
	
	public int getUserprovince() {
		return userprovince;
	}
	public void setUserprovince(int userprovince) {
		this.userprovince = userprovince;
	}
	public int getUsercity() {
		return usercity;
	}
	public void setUsercity(int usercity) {
		this.usercity = usercity;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getPageindex() {
		return pageindex;
	}
	public void setPageindex(int pageindex) {
		this.pageindex = pageindex;
	}
}
