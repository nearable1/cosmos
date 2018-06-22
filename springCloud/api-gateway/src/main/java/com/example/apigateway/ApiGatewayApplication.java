package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

import com.example.apigateway.filter.AccessFilter;
import com.example.apigateway.utils.DidiErrorAttributes;

//开启Zuul的API网关服务功能
@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayApplication {
	
	public static void main(String[] args) {
		//来启用自定义的核心处理器以完成我们的优化目标。
		//FilterProcessor.setProcessor(new DidiFilterProcessor());
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter(); 
	}
	
	@Bean
	public PatternServiceRouteMapper serviceRouteMapper () {
		return new PatternServiceRouteMapper(
			"(?<name>^.+)-(?<version>v.+$)","${version}/${name}");
	}
	
	@Bean
	public DefaultErrorAttributes errorAttributes () {
		return new DidiErrorAttributes (); 
	}
}
