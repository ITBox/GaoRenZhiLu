package com.itbox.grzl.enumeration;

/**
 * 提现审核状态
 * 
 * @author byz
 * @date 2014-5-18下午5:41:09
 */
public enum ExamineState {

	INITIAL(1, "初始化"), SUCCESS(2, "审核通过"), FAIL(3, "提现失败"), MONEYSUCCESS(4, "提现成功");

	private String name;
	private int index;

	private ExamineState(int index, String name) {
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
		for (ExamineState c : ExamineState.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}
}
