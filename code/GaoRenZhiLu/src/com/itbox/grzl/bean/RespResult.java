package com.itbox.grzl.bean;

/**
 * http请求响应结果
 * 
 * @author byz
 * @date 2014-5-11下午4:16:13
 */
public class RespResult {

	public static final int SUCCESS = 1;
	public static final int FAIL = 0;

	private int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return result == SUCCESS;
	}
}
