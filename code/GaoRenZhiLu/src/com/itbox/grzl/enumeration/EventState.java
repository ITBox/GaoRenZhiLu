package com.itbox.grzl.enumeration;

/**
 * 活动状态
 * 
 * @author byz
 * @date 2014-5-18下午5:41:09
 */
public enum EventState {

	Registrationing(1, "报名中"), End(2, "已结束");

	private String name;
	private int index;

	private EventState(int index, String name) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (EventState c : EventState.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	public static String[] getAllName() {
		EventState[] values = EventState.values();
		String[] all = new String[values.length];
		for (int i = 0; i < all.length; i++) {
			all[i] = values[i].getName();
		}
		return all;
	}
	
	public static EventState getByIndex(int index) {
		for (EventState c : EventState.values()) {
			if (c.ordinal() == index) {
				return c;
			}
		}
		return null;
	}
}
