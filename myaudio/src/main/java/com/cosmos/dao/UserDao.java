package com.cosmos.dao;

import java.util.ArrayList;

public interface UserDao {
	
	//��ȡ����ĸ���
	public ArrayList<String> getTypes();
	
	//���������ȡ�����ļ�
	public ArrayList<String> getAudio(String type);
}
