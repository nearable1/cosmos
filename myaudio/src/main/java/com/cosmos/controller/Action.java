package com.cosmos.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	//根据种类获取音乐文件
	@RequestMapping("getAudio.html")
	//@ResponseBody
	public String getAudio(@RequestParam(value="type")String type) {
		ArrayList<String> audioList = us.getAudio(type);
		
		return JSON.toJSONString(audioList);
	}
	
	////获取种类的个数
	@RequestMapping("getTypes.html")
	//@ResponseBody
	public String getTypes() {
		ArrayList<String> typesList = us.getTypes();
		
		return JSON.toJSONString(typesList);
	}

}
