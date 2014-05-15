package com.itbox.fx.util;

import java.lang.reflect.Method;

import android.database.Cursor;
import android.graphics.Point;

/**
 * 
 * @author Baoyz
 * 
 */
public class BeanUtil {

	public static <T> T fillData(T bean, Cursor cursor) {
		if (bean != null && cursor != null && !cursor.isClosed()) {
		}
		return bean;
	}

	public static void setProperty(String proName, Object value)
			throws Exception {
//		Intr
//		PropertyDescriptor proDescriptor = new PropertyDescriptor(proName,
//				value.getClass());
//		Method methodSetX = proDescriptor.getWriteMethod();
//		methodSetX.invoke(, 8);
	}
}
