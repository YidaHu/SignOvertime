package com.gswtek.huyd.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: huyd
 * Date: 2017-07-24
 * Time: 15:33
 * Describe:
 */
public class SigndateDAO implements SigndateService {

	//获得 helper对象用来操纵数据库
	private DBOpenHelper helper = null;

	public SigndateDAO(Context context) {
		helper = new DBOpenHelper(context);
	}

	/**
	 * 下面四个方法实现对数据库的增删改查功能
	 */
	@Override
	public boolean addPersion(Object[] params) {

		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			//这里面问好表示占位符，所以要需要传入所有的占位符的值,传入值有这个方法中的参数传递
			String sql = "insert into signdate(timename,content,flag) values(?,?,?)";
			database = helper.getWritableDatabase(); //实现对数据库写的操作
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public boolean deletePerson(Object[] params) {
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			String sql = "delete from signdate where timename = ? ";
			database = helper.getWritableDatabase();
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public boolean updatePerson(Object[] params) {
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			String sql = "update signdate set timename = ?, content = ?, flag = ? where timename = ? ";
			database = helper.getWritableDatabase();
			database.execSQL(sql, params);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	//根据Id号来查询，查询的每一行数据返回用 Map 集合来存储
	@Override
	public Map<String, String> viewPerson(String[] selectionArgs) {
		Map<String, String> map = new HashMap<String, String>();
		SQLiteDatabase database = null;
		try {
			String sql = "select * from signdate where timename = ? ";
			database = helper.getReadableDatabase(); //查询读取数据，查询结果使用Map来存储
			//声明一个游标，这个是行查询的操作，支持原生SQL语句的查询
			Cursor cursor = database.rawQuery(sql, selectionArgs); //ID所在行查询
			int colums = cursor.getColumnCount();//获得数据库的列的个数
			//cursor.moveToNext() 移动到下一条记录
			while (cursor.moveToNext()) {
				for (int i = 0; i < colums; i++) {
					String cols_name = cursor.getColumnName(i); //提取列的名称
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name)); //根据列的名称提取列的值
					//数据库中有写记录是允许有空值的,所以这边需要做一个处理
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return map;
	}

	//多条记录 用 List<Map<String, String>> 来封装,每一行产生一个 Map集合来装载这一行的数据
	//这样就有多个Map值，然后放入List中.
	@Override
	public List<Map<String, String>> listPersonMaps(String[] selectionArgs) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		SQLiteDatabase database = null;
		try {
			String sql = "select * from signdate "; //这个是查询表中所有的内容，所以就不需要传入的这个参数值了
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, selectionArgs);
			int colums = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < colums; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
					if (cols_name == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}
}
