package com.example.ribbonconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ConsumerService {
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "helloFallback")
	public String helloService() {
		return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class) .getBody(); 
	}
	
	public String helloFallback () {
		return "error"; 
	}
}
