package com.itbox.grzl.bean;

import java.io.Serializable;
/**
 * 
 * @author youzh
 *
 */
public class UpdateUserList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2650168147186056515L;
    //1:成功；0:失败
	private int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
