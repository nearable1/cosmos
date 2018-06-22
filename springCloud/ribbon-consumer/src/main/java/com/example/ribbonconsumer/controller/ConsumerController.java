package com.example.ribbonconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ribbonconsumer.service.ConsumerService;

@RestController
public class ConsumerController {
	@Autowired
	ConsumerService consumerService;
	
	@RequestMapping("/index")
	public String index() {
		return "ribbon";
	}
	
	@RequestMapping(value = "/ribbon",method = RequestMethod.GET)
	public String helloConsumer() {
		return consumerService.helloService();
	}
	
	@RequestMapping(value = "/ribbon1",method = RequestMethod.GET)
	public String helloConsumer1() {
		return "index";
	}
}
