package com.cosmos.dao;

import java.util.ArrayList;

import com.cosmos.entity.Sound;

public interface UserDao {
	
	//��ȡ����ĸ���
	public ArrayList<String> getTypes();
	
	//���������ȡ�����ļ�
	public ArrayList<Sound> getAudio(String type);
}
