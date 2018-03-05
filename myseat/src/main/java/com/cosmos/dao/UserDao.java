package com.cosmos.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.cosmos.entity.ClassTable;
import com.cosmos.entity.School;
import com.cosmos.entity.Seat;
import com.cosmos.entity.User;

public interface UserDao {
	public User getUserList(HashMap map);
	
	//��ѯschool--�ڵ�����ʾ
	public ArrayList<String> selectSchoolByRegion(String province, String city, String area);
	
	//��ѯschoolId
	public String selectSchoolId(String province, String city, String area);
	
	//��ѯclassId
	public String selectClassId(String year, String className, String times, String schoolId);
	
	//����school
	public int insertSchool(School school);
	
	//����class
	public int insertClass(ClassTable classTable);
	
	//����seat
	public int insertSeat(Seat seat);
	
	//��ѯ�Լ�����seat
	public ArrayList<Seat> selectSeat(String schoolId, String classId);
	
}
