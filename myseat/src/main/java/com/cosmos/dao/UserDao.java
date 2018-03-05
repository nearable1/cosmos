package com.cosmos.dao;

import java.util.ArrayList;

import com.cosmos.entity.ClassTable;
import com.cosmos.entity.School;
import com.cosmos.entity.Seat;

public interface UserDao {
	
	//��ѯschool--�ڵ�����ʾ
	public ArrayList<String> selectSchoolByRegion(String province, String city, String area);
	
	//��ѯschoolId
	public String selectSchoolId(String province, String city, String area);
	
	//��ѯclassId
	public String selectClassId(String year, String className, String times, String schoolId);
	
	//��ѯclassName
	public ArrayList<String> selectClass(String year, String schoolId);
	
	//����school
	public int insertSchool(School school);
	
	//����class
	public int insertClass(ClassTable classTable);
	
	//����seat
	public int insertSeat(Seat seat);
	
	//����seat
	public int updateSeat(Seat seat);
	
	//��ѯseat
	public String selectOneSeat(Seat seat);
	
	//��ѯ�Լ�����seat
	public ArrayList<Seat> selectSeat(String schoolId, String classId);
	
}
