package com.example.feignconsumer.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.feignconsumer.entity.User;

@Component//加入spring bean中
public class HelloServiceFallback implements HelloService{

	@Override
	public String hello() {
		// TODO Auto-generated method stub
		return "error";
	}

	@Override
	public String hello(String name) {
		// TODO Auto-generated method stub
		return "error";
	}

	@Override
	public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
		// TODO Auto-generated method stub
		return new User("未知", 0);
	}

	@Override
	public String hello(@RequestBody User user) {
		// TODO Auto-generated method stub
		return "error";
	}

}
