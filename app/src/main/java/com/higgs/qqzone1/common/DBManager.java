package com.higgs.qqzone1.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库创建类.根据此类来创建数据库对象
 * 
 * @author Administrator
 * 
 */
public class DBManager extends SQLiteOpenHelper {
	private static final String dbName = "myqzone.db";
	private static final int dbVersion = 1;
	public DBManager(Context context, int version) {
		super(context, dbName, null, dbVersion);
	}
	public DBManager(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO 自动生成的构造函数存根
	}

	/**
	 * 创建表
	 */
	public void onCreate(SQLiteDatabase db) {
		// TODO 自动生成的方法存根
		db.execSQL("CREATE TABLE IF NOT EXISTS USERINFO(id INTEGER,loginname VARCHAR,password VARCHAR,lasttime VARCHAR,registertime VARCHAR,sex VARCHAR,nikeName VARCHAR,islogin INTEGER)");
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自动生成的方法存根

	}


}
