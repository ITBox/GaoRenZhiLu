package com.itbox.fx.net;

import java.util.List;

import com.itbox.fx.util.GSON;



/**
 * @author hyh 
 * creat_at：2013-11-7-上午11:11:34
 */
public class GsonResponseHandler <T> extends ResponseHandler {

	private Class<T> cls;

	public GsonResponseHandler(Class<T> clazz) {
		super();
		cls = clazz;
	}

	public GsonResponseHandler(boolean isShowProgress, Class<T> clazz) {
		super(isShowProgress);
		cls = clazz;
	}
	
	@Override
	public void onSuccess(String content) {
		T object = GSON.getObject(content, cls);
		if(null == object){
			onSuccess(GSON.getList(content, cls));
		}
		onSuccess(object);
	}
	
	public void onSuccess(T object) {

	}
	public void onSuccess(List<T> list) {
		
	}
}
