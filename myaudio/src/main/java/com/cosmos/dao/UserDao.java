package com.cosmos.dao;

import java.util.ArrayList;

import com.cosmos.entity.Sound;

public interface UserDao {
	
	//获取种类的个数
	public ArrayList<String> getTypes();
	
	//根据种类获取音乐文件
	public ArrayList<Sound> getAudio(String type);
}
