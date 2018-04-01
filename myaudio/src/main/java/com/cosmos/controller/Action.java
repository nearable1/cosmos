package com.cosmos.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cosmos.entity.Sound;
import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	//根据种类获取音乐文件
	@RequestMapping(value="getAudio.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getAudio(@RequestParam(value="type")String type) {
		ArrayList<Sound> audioList = us.getAudio(type);
		
		return JSON.toJSONString(audioList);
	}
	
	//获取种类的个数
	@RequestMapping(value="getTypes.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getTypes() {
		ArrayList<String> typesList = us.getTypes();
		return JSON.toJSONString(typesList);
	}
	
	//获取种类的个数
	@RequestMapping(value="getHots.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getHots() {
		ArrayList<Sound> typesList = us.getHot();
		return JSON.toJSONString(typesList);
	}

}
