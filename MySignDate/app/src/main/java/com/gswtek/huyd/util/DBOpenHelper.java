package com.gswtek.huyd.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Author: huyd
 * Date: 2017-07-24
 * Time: 15:33
 * Describe:
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	private static String name = "mydb.db"; // 表示数据库的名称
	private static int version = 1; // 表示数据库的版本号

	public DBOpenHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	// 当数据库创建的时候，是第一次被执行，完成对数据库的表的创建
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// SQLite 数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
		// 数据库这边有一个特点，就是SQLite数据库中文本类型没有过多的约束，也就是可以把布尔类型的数据存储到文本类型中，这样也是可以的
		String sql = "create table signdate(id integer primary key autoincrement,timename varchar(64),content varchar(64),flag varchar(8))";
		db.execSQL(sql); // 完成数据库的创建
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}  