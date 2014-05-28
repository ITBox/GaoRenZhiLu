package com.itbox.grzl.bean;

import java.io.Serializable;
/**
 * 身份证
 * @author youzh
 *
 */
public class AddUserAuthEntication implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7270923875909560495L;
    // 0、修改失败；1、成功；
	private int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
