package com.itbox.grzl.common.db;


import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author WCT
 * create at：2012-11-20 下午02:14:57
 */
public class CitiesSQLHelper extends DBHelper {
	public static final String DB_CITIES = "Cities.db";
	public CitiesSQLHelper() {
		super(DB_CITIES);
	}
	
	@Override
	public SQLiteDatabase open() throws SQLException {
		return getReadableDatabase();
	}
}
