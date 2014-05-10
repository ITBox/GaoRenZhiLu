package com.itbox.grzl.common.db;


import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author WCT
 * create at：2012-11-20 下午02:14:57
 */
public class AreaSQLHelper extends DBHelper {
	public static final String DB_AREAE = "Areae.db";

	
	public AreaSQLHelper() {
		super(DB_AREAE);
	}
	
	@Override
	public SQLiteDatabase open() throws SQLException {
		return getReadableDatabase();
	}
}
