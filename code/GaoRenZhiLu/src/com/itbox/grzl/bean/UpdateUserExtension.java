package com.itbox.grzl.bean;

import java.io.Serializable;
/**
 * 
 * @author youzh
 *
 */
public class UpdateUserExtension implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8287768121375028504L;
	
	// 0、修改失败；1、成功；2、电话咨询价格大于可设定范围；3、图文咨询价格大于可设定范围；
	private int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
