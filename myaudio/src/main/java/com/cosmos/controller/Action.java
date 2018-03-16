package com.cosmos.controller;

import java.sql.Blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	//≤È—ØschoolId
	@RequestMapping("getAudio.html")
	//@ResponseBody
	public void getSchoolId() {
		Blob blob = us.getAudio(1);
		System.out.println(blob);
	}

}
