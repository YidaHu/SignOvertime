package com.gswtek.huyd.util;

import java.util.List;
import java.util.Map;

/**
 * Author: huyd
 * Date: 2017-07-24
 * Time: 15:33
 * Describe:
 */
public interface SigndateService {
	public boolean addPersion(Object[] params);

	public boolean deletePerson(Object[] params);

	public boolean updatePerson(Object[] params);

	//使用 Map<String, String> 做一个封装，比如说查询数据库的时候返回的单条记录
	public Map<String, String> viewPerson(String[] selectionArgs);

	//使用 List<Map<String, String>> 做一个封装，比如说查询数据库的时候返回的多条记录
	public List<Map<String, String>> listPersonMaps(String[] selectionArgs);
}
