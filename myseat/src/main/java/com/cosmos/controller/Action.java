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
	
	//查询schoolId
	@RequestMapping(value="getSchoolId.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findSchoolId(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area,
			@RequestParam(value="chineseName")String chineseName) {
		String schoolId = us.selectSchoolId(province, city, area, chineseName);
		
		return schoolId;
	}
	
	//查询classId
	@RequestMapping(value="getClassId.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findClassId(@RequestParam(value="year")String year, 
			@RequestParam(value="className")String className, 
			@RequestParam(value="times")String times,
			@RequestParam(value="schoolId")String schoolId) {
		String classId = us.selectClassId(year, className, times, schoolId);
		return classId;
	}
	
	//查询school--在弹窗显示
	@RequestMapping(value="selectSchoolByRegion.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String showSchoolByRegion(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area) {
		ArrayList<String> schoolList = us.selectSchoolByRegion(province, city, area);
		
		return JSON.toJSONString(schoolList);
	}
	
	//插入school
	@RequestMapping(value="insertSchool.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addSchool(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area,
			@RequestParam(value="chineseName")String chineseName) {
		
		ArrayList<String> schoolList = us.selectSchoolByRegion(province, city, area);
		if(schoolList.size()>0) {
			for(int i=0; i<schoolList.size(); i++) {
				if(schoolList.get(i).equals(chineseName)) {
					return "0";
				}
			}
			School school = new School();
			school.setProvince(province);
			school.setCity(city);
			school.setArea(area);
			school.setChineseName(chineseName);
			
			//使用uuid创建schoolId
			String schoolId = UUID.randomUUID().toString();
			school.setSchoolId(schoolId);
			synchronized(Action.class) {
				us.insertSchool(school);
			}
			
			//返回1表示插入成功
			return "1";
			
		}else {
			School school = new School();
			school.setProvince(province);
			school.setCity(city);
			school.setArea(area);
			school.setChineseName(chineseName);
			
			//使用uuid创建schoolId
			String schoolId = UUID.randomUUID().toString();
			school.setSchoolId(schoolId);
			
			synchronized(Action.class) {
				us.insertSchool(school);
			}
			//返回1表示插入成功
			return "1";
		}
	}
	
	//插入class
	@RequestMapping(value="insertClass.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addClass(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area,
			@RequestParam(value="chineseName")String chineseName, 
			@RequestParam(value="year")String year, 
			@RequestParam(value="className")String className, 
			@RequestParam(value="times")String times) {
		String schoolId = us.selectSchoolId(province, city, area, chineseName);
		String aclassId = us.selectClassId(year, className, times, schoolId);
		if(aclassId=="" || aclassId==null) {
			ClassTable classTable = new ClassTable();
			classTable.setClassName(className);
			classTable.setSchoolId(schoolId);
			classTable.setTimes(times);
			classTable.setYear(year);
			String classId = UUID.randomUUID().toString();
			classTable.setClassId(classId);
			synchronized(Action.class) {
				us.insertClass(classTable);
			}
			
			return findSeat(schoolId, classId);
		}else {
			return findSeat(schoolId, aclassId);
		}
		
	}
	
	//插入seat
	@RequestMapping(value="insertSeat.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addSeat(@RequestParam(value="schoolId")String schoolId,
			@RequestParam(value="classId")String classId,
			@RequestParam(value="name")String name,
			@RequestParam(value="seatId")String seatId) {
		Seat seat = new Seat();
		seat.setClassId(classId);
		seat.setName(name);
		seat.setSchoolId(schoolId);
		seat.setSeatId(seatId);
		String stId = us.selectOneSeat(seat);
		synchronized(Action.class) {
			if(stId!=null) {
				us.updateSeat(seat);
				return "1";
			}else {
				us.insertSeat(seat);
				return "1";
			}
		}
		
	}
	
	//查询以及分享seat
	@RequestMapping(value="selectSeat.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findSeat(@RequestParam(value="schoolId")String schoolId, 
			@RequestParam(value="classId")String classId) {
		ArrayList<Seat> list = us.selectSeat(schoolId, classId);
		String seatList = JSON.toJSONString(list);
		
		return seatList;
	}
}
