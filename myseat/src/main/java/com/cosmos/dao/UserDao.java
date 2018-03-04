package com.cosmos.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.cosmos.entity.ClassTable;
import com.cosmos.entity.School;
import com.cosmos.entity.Seat;
import com.cosmos.entity.User;

public interface UserDao {
	public User getUserList(HashMap map);
	
	//查询school--在弹窗显示
	public ArrayList<String> selectSchoolByRegion(String province, String city, String area);
	
	//查询schoolId
	public String selectSchoolId(String province, String city, String area);
	
	//查询classId
	public String selectClassId(String year, String className, String times, String schoolId);
	
	//插入school
	public int insertSchool(School school);
	
	//插入class
	public int insertClass(ClassTable classTable);
	
	//插入seat
	public int insertSeat(Seat seat);
	
	//查询以及分享seat
	public ArrayList<Seat> selectSeat(String schoolId, String classId);
	
}
