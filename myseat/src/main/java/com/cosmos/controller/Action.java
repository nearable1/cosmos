package com.cosmos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cosmos.entity.School;
import com.cosmos.entity.User;
import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	//登陆
	@RequestMapping("login.do")
	public String getUser(@RequestParam(value="userCode") String name, @RequestParam(value="userPassword") String password, Model mod) {
		
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("password",password);
		User user = us.getUserList(map);
		if(user!=null) {
			mod.addAttribute("user", user);
			return "show.jsp";
		}else {
			return "error.jsp";
		}
	}
	
	//查询school--在弹窗显示
	@RequestMapping("selectSchoolByRegion.html")
	@ResponseBody
	public String showSchoolByRegion(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area) {
		ArrayList<String> schoolList = us.selectSchoolByRegion(province, city, area);
	 
		return JSON.toJSONString(schoolList);
	}
	
	//查询schoolId
	@RequestMapping("selectSchoolId.html")
	@ResponseBody
	public String findSchoolId(@RequestParam(value="province")String province, @RequestParam(value="city")String city, @RequestParam(value="area")String area) {
		String schoolId = us.selectSchoolId(province, city, area);
	 
		return schoolId;
	}
	
	//查询classId
	@RequestMapping("selectClassId.html")
	@ResponseBody
	public String findClassId(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area, 
			@RequestParam(value="year")String year, 
			@RequestParam(value="className")String className, 
			@RequestParam(value="times")String times) {
		String schoolId = us.selectSchoolId(province, city, area);
		String classId = us.selectClassId(year, className, times, schoolId);
	 
		return classId;
	}
	
	//插入school
	@RequestMapping("insertSchool.html")
	@ResponseBody
	public int addSchool(@RequestParam(value="province")String province, 
			@RequestParam(value="city")String city, 
			@RequestParam(value="area")String area) {
		
		ArrayList<String> schoolList = us.selectSchoolByRegion(province, city, area);
		if(schoolList.size()>0) {
			School school = new School();
			school.setProvince(province);
			school.setCity(city);
			school.setArea(area);
			
			//使用uuid创建schoolId
			String schoolId = UUID.randomUUID().toString();
			school.setSchoolId(schoolId);
			
			us.insertSchool(school);
			
			//返回1表示插入成功
			return 1;
		}else {
			//返回0表示已经有重名的
			return 0;
		}
		
	}
	
}
