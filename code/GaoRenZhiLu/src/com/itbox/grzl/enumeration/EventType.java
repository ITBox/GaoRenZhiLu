package com.itbox.grzl.enumeration;

/**
 * 活动类型
 * 
 * @author byz
 * @date 2014-5-18下午5:41:09
 */
public enum EventType {

	School(2, "校园招聘"), Other(8, "各类培训"), Activity(16, "公益活动"), Recruitment(32,
			"招聘兼职"), TourPALActivity(64, "驴友活动"), SchoolGathering(128, "校友聚会"), Industry(
			256, "行业聚会");

	private String name;
	private int index;

	private EventType(int index, String name) {
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
		for (EventType c : EventType.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	public static String[] getAllName() {
		EventType[] values = EventType.values();
		String[] all = new String[values.length];
		for (int i = 0; i < all.length; i++) {
			all[i] = values[i].getName();
		}
		return all;
	}

	public static EventType getByIndex(int index) {
		for (EventType c : EventType.values()) {
			if (c.ordinal() == index) {
				return c;
			}
		}
		return null;
	}
}
