package com.itbox.grzl.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PrivateTalkDBHelper extends SQLiteOpenHelper {
	public static String CHAT_FROM = "chatfrom";
	public static String CHAT_TO = "chatto";
	public static String CHAT_CONTENT = "content";
	public static String CHAT_TIME = "chattime";
	public static String CHAT_SUCCESS = "success";

	public PrivateTalkDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table privatetalk(_id integer primary key autoincrement,chatfrom integer,content varchar,chattime varchar,chatto integer,success integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
