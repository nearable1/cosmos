package com.cosmos.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cosmos.entity.ClassTable;
import com.cosmos.entity.School;
import com.cosmos.entity.Seat;
import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	//��ѯschool--�ڵ�����ʾ
	@RequestMapping("selectSchoolByRegion.html")
	@ResponseBody
	public String showSchoolByRegion(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area) {
		ArrayList<String> schoolList = us.selectSchoolByRegion(province, city, area);
	 
		return JSON.toJSONString(schoolList);
	}
	
	//����school
	@RequestMapping("insertSchool.html")
	@ResponseBody
	public int addSchool(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area,
			@RequestParam(value="chineseName")String chineseName) {
		
		ArrayList<String> schoolList = us.selectSchoolByRegion(province, city, area);
		if(schoolList.size()>0) {
			for(int i=0; i<schoolList.size(); i++) {
				if(schoolList.get(i)==chineseName) {
					return 0;
				}
			}
			School school = new School();
			school.setProvince(province);
			school.setCity(city);
			school.setArea(area);
			
			//ʹ��uuid����schoolId
			String schoolId = UUID.randomUUID().toString();
			school.setSchoolId(schoolId);
			
			us.insertSchool(school);
			
			//����1��ʾ����ɹ�
			return 1;
			
		}else {
			School school = new School();
			school.setProvince(province);
			school.setCity(city);
			school.setArea(area);
			
			//ʹ��uuid����schoolId
			String schoolId = UUID.randomUUID().toString();
			school.setSchoolId(schoolId);
			
			us.insertSchool(school);
			
			//����1��ʾ����ɹ�
			return 1;
		}
	}
	
	//����class
	@RequestMapping("insertClass.html")
	@ResponseBody
	public String addClass(@RequestParam(value="schoolId")String schoolId, 
			@RequestParam(value="year")String year, 
			@RequestParam(value="className")String className, 
			@RequestParam(value="times")String times) {
		String aclassId = us.selectClassId(year, className, times, schoolId);
		if(aclassId=="" || aclassId==null) {
			ClassTable classTable = new ClassTable();
			classTable.setClassName(className);
			classTable.setSchoolId(schoolId);
			classTable.setTimes(times);
			classTable.setYear(year);
			String classId = UUID.randomUUID().toString();
			classTable.setClassId(classId);
			
			us.insertClass(classTable);
			
			return findSeat(schoolId, classId);
		}else {
			return findSeat(schoolId, aclassId);
		}
		
	}
	
	//����seat
	@RequestMapping("insertSeat.html")
	@ResponseBody
	public int addSeat(@RequestParam(value="schoolId")String schoolId,
			@RequestParam(value="classId")String classId,
			@RequestParam(value="name")String name,
			@RequestParam(value="seatId")String seatId) {
		Seat seat = new Seat();
		seat.setClassId(classId);
		seat.setName(name);
		seat.setSchoolId(schoolId);
		seat.setSeatId(seatId);
		
		String stId = us.selectOneSeat(seat);
		
		if(stId!=null) {
			us.updateSeat(seat);
			return 1;
		}else {
			us.insertSeat(seat);
			return 1;
		}
	}
	
	//��ѯ�Լ�����seat
	@RequestMapping("selectSeat.html")
	@ResponseBody
	public String findSeat(@RequestParam(value="schoolId")String schoolId, 
			@RequestParam(value="classId")String classId) {
		ArrayList<Seat> list = us.selectSeat(schoolId, classId);
		String seatList = JSON.toJSONString(list);
		
		return seatList;
	}
}
