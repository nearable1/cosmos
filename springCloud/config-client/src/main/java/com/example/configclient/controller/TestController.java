package com.example.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {
	@Value("${from}")
	private String from;
	//除了通过@Value注解绑定注入之外， 也可以通过Environment对象来获取配置属性，
	//@Autowired
	//private Environment env; 
	
	@RequestMapping("/from")
	public String from() {
		//return env.getProperty("from", "undefined");
		return this.from; 
	}
}
