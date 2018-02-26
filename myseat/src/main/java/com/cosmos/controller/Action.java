package com.cosmos.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cosmos.entity.User;
import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
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
}
