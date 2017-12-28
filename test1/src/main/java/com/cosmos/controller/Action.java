package com.cosmos.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cosmos.entity.User;
import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	@RequestMapping("login.do")
	public String getUser(@RequestParam("userCode") String name, @RequestParam("userPassword") String password){
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("password", password);
		
		User list = us.getUserList(map);
		
		if(list!=null) {
			System.out.println(list.getId()+"\t"+list.getName()+"\t"+list.getPassword());
			return "/jsp/frame.jsp";
		}else {
			return "error.jsp";
		}
		
	}

}
