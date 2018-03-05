package com.cosmos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosmos.dao.UserDao;
import com.cosmos.entity.ClassTable;
import com.cosmos.entity.School;
import com.cosmos.entity.Seat;
import com.cosmos.entity.User;
import com.cosmos.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao ud;

	public User getUserList(HashMap map) {
		// TODO Auto-generated method stub
		return ud.getUserList(map);
	}

	public ArrayList<String> selectSchoolByRegion(String province, String city, String area) {
		// TODO Auto-generated method stub
		return ud.selectSchoolByRegion(province, city, area);
	}

	public String selectSchoolId(String province, String city, String area) {
		// TODO Auto-generated method stub
		return ud.selectSchoolId(province, city, area);
	}

	public String selectClassId(String year, String className, String times, String schoolId) {
		// TODO Auto-generated method stub
		return ud.selectClassId(year, className, times, schoolId);
	}

	@Transactional
	public int insertSchool(School school) {
		// TODO Auto-generated method stub
		return ud.insertSchool(school);
	}

	@Transactional
	public int insertClass(ClassTable classTable) {
		// TODO Auto-generated method stub
		return ud.insertClass(classTable);
	}

	@Transactional
	public int insertSeat(Seat seat) {
		// TODO Auto-generated method stub
		return ud.insertSeat(seat);
	}

	public ArrayList<Seat> selectSeat(String schoolId, String classId) {
		// TODO Auto-generated method stub
		return ud.selectSeat(schoolId, classId);
	}

}