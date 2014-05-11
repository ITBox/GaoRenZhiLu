package com.itbox.grzl.event.bean;

import com.itbox.grzl.Api;
/**
 * @author hyh 
 * creat_at：2013-10-17-下午3:00:32
 */
public class EventListOtherTModel {
	private int UserId;
	private int Type;
	private int pageindex = Api.PAGE_DEFAULT_INDEX;
	private int pagesize = Api.PAGE_SIZE;
	
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public int getPageindex() {
		return pageindex;
	}
	public void setPageindex(int pageindex) {
		this.pageindex = pageindex;
	}
	public int getPagesize() {
		return pagesize;
	}
}
