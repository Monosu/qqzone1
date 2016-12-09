package com.higgs.qqzone1.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库操作类。包含所有的Sqlite数据库操作
 * 
 * @author Administrator
 * 
 */
public class DBHelper {
	private SQLiteDatabase db;
	private DBManager oDbManager;
	private int version = 1;

	private DBHelper() {
	}

	/**
	 * 实例化DBHelper。并创建数据库
	 * 
	 * @param oContext
	 */
	public DBHelper(Context oContext) {
		// 先实例化DBManager对象。如果此时没有数据库会新建数据库
		oDbManager = new DBManager(oContext, version);
		// 从DBManager中获取数据库连接
		db = oDbManager.getWritableDatabase();
	}

	/**
	 * 插入数据到指定表
	 * 
	 * @param tableName
	 *            数据库表名
	 * @param parm
	 *            要插入的列名及值。以Map格式传入。比如insert into(id)values(1)则传入Map.put("id",1)
	 * @return
	 */
	public boolean insertModel(String tableName, Map<String, String> parm) {
		boolean result = false;
		if (parm != null && parm.size() > 0) {
			ContentValues parmValues = new ContentValues();
			for (String rowName : parm.keySet()) {
				parmValues.put(rowName, parm.get(rowName));
			}
			result = db.insert(tableName, null, parmValues) > 0;
		}
		return result;
	}

	/**
	 * 删除指定表的数据
	 * 
	 * @param tableName
	 *            要删除数据的表名
	 * @param whereString
	 *            删除条件.和sql一样。不需要加where 比如id=? and username=?
	 * @param whereParm
	 *            条件的值。如上面的条件则为 new String[]{'1','dsfs'}
	 * @return
	 */
	public boolean deleteModel(String tableName, String whereString,
			String[] whereParm) {
		boolean result = false;
		result = db.delete(tableName, whereString, whereParm) > 0;
		return result;
	}

	/**
	 * 更新表数据
	 * 
	 * @param tableName
	 *            要更新的表名
	 * @param parm
	 *            更新的参数。如同insertModel
	 * @param whereString
	 *            更新条件。如同deleteModel
	 * @param whereParm
	 *            更新条件值。如同deleteModel
	 * @return
	 */
	public boolean updateModel(String tableName, Map<String, String> parm,
			String whereString, String[] whereParm) {
		boolean result = false;
		if (parm != null && parm.size() > 0) {
			ContentValues parmValues = new ContentValues();
			for (String rowName : parm.keySet()) {
				parmValues.put(rowName, parm.get(rowName));
			}
			result = db.update(tableName, parmValues, whereString, whereParm) > 0;
		}
		return result;
	}

	/**
	 * 根据sql语句查询数据库指定对象。
	 * 
	 * @param sql
	 *            要执行的sql语句 如：select * from userinfo where id = ?
	 * @param parm
	 *            执行sql语句的参数。没有值为null 有则为String[]{'1'}
	 * @return
	 */
	public Map<String, String> getModel(String sql, String[] parm) {
		Map<String, String> oMap = new HashMap<String, String>();
		Cursor oCursor = db.rawQuery(sql, parm);
		if (oCursor.moveToNext()) {
			oMap = getDateItem(oCursor);
		}
		return oMap;
	}

	/**
	 * 根据sql语句查询数据库指定对象。
	 * 
	 * @param sql
	 *            要执行的sql语句 如：select * from userinfo where id = ?
	 * @param parm
	 *            执行sql语句的参数。没有值为null 有则为String[]{'1'}
	 * @return
	 */
	public Map<String, String> getModel(String sql) {
		return getModel(sql, null);
	}

	/**
	 * 根据sql语句查询数据库指定对象集合。
	 * 
	 * @param sql
	 *            要执行的sql语句 如：select * from userinfo where id = ?
	 * @param parm
	 *            执行sql语句的参数。没有值为null 有则为String[]{'1'}
	 * @return
	 */
	public List<Map<String, String>> getModels(String sql, String[] parm) {
		List<Map<String, String>> dataList = null;
		Cursor oCursor = db.rawQuery(sql, parm);
		if (oCursor != null && oCursor.getCount() > 0) {
			dataList = new ArrayList<Map<String, String>>();
			while (oCursor.moveToNext()) {
				dataList.add(getDateItem(oCursor));

			}
		}
		return dataList;
	}

	/**
	 * 根据sql语句查询数据库指定对象。
	 * 
	 * @param sql
	 *            要执行的sql语句 如：select * from userinfo where id = ?
	 * @param parm
	 *            执行sql语句的参数。没有值为null 有则为String[]{'1'}
	 * @return
	 */
	public List<Map<String, String>> getModels(String sql) {
		return getModels(sql, null);
	}

	/**
	 * 根据传入的Cursor对象。封装成HashMap。key为数据表里面的列名。值为数据库中的值
	 * 
	 * @param oCursor
	 * @return
	 */
	private Map<String, String> getDateItem(Cursor oCursor) {
		Map<String, String> oMap = null;
		if (oCursor != null && oCursor.getCount() > 0) {
			oMap = new HashMap<String, String>();
			for (String rowsName : oCursor.getColumnNames()) {
				oMap.put(rowsName,
						oCursor.getString(oCursor.getColumnIndex(rowsName)));
			}
		}
		return oMap;
	}
	public void closeDB() {
		db.close();
	}
}
