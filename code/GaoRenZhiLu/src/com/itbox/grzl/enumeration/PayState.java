package com.itbox.grzl.enumeration;

/**
 * 支付状态
 * 
 * @author byz
 * @date 2014-5-18下午5:41:09
 */
public enum PayState {

	ALL(-1, "全部"), INITIAL(0, "初始化"), SUCCESS(1, "支付成功"), FAIL(2, "支付失败");

	private String name;
	private int index;

	private PayState(int index, String name) {
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
		for (PayState c : PayState.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}
}
