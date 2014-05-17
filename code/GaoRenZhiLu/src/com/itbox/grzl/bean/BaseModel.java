package com.itbox.grzl.bean;

import java.lang.reflect.Field;

import com.activeandroid.Model;

public class BaseModel extends Model {

	public void setId(Long id) {
		try {
			Field field = Model.class.getDeclaredField("mId");
			field.setAccessible(true);
			field.set(this, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
