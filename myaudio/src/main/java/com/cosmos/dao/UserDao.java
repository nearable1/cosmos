package com.cosmos.dao;

import java.util.ArrayList;

public interface UserDao {
	
	//获取种类的个数
	public ArrayList<String> getTypes();
	
	//根据种类获取音乐文件
	public ArrayList<String> getAudio(String type);
}
