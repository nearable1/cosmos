package com.example.hello.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.helloserviceapi.entity.User;
import com.example.helloserviceapi.service.HelloService;

@RestController
public class RefactorHelloController implements HelloService{

	@Override
	public String hello(String name) {
		// TODO Auto-generated method stub
		return "hello"+ name;
	}

	@Override
	//@RequestHeader一定需要，不然feign中的参数无法传递过来
	public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
		// TODO Auto-generated method stub
		return new User(name, age);
	}

	@Override
	public String hello(@RequestBody User user) {
		// TODO Auto-generated method stub
		return "Hello "+ user.getName() + ", " + user.getAge();
	}
	
}
