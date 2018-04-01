package com.cosmos.controller;

import java.net.URL;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	//根据种类获取音乐文件
	@RequestMapping(value="getRunData.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRunData(@RequestParam(value="appid")String appid, 
			@RequestParam(value="secret")String secret,
			@RequestParam(value="js_code")String js_code) {
		// url: 'https://api.weixin.qq.com/sns/jscode2session?
		//appid=' + appid + '&secret=' + secret + '&js_code=' + res.code + 
		//		'&grant_type=authorization_code'
		URL url = new URL();
		return null;
	}
}
