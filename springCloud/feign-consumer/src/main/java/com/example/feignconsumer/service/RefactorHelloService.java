package com.example.feignconsumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "HELLO-SERVICE") 
public interface RefactorHelloService extends com.example.helloserviceapi.service.HelloService{
	
}
