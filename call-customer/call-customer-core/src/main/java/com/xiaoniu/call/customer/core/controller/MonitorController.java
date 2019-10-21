package com.xiaoniu.call.customer.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 	服务监控API
 * 	@author conly.wang
 * 	2018-05-18
 */
@RestController
public class MonitorController {
	
	private static org.slf4j.Logger log = LoggerFactory.getLogger(MonitorController.class);

    @RequestMapping(value = "/callcustomer/monitor")
    public Map<String ,Object> health(String message, Model model) {
    	log.info(" callcustomer server is health !");
		Map<String ,Object> result = new HashMap<>() ;
		result.put("code", 200) ;
		result.put("data", null) ;
		result.put("message", "success") ;
		return result;
    }
    
	@RequestMapping(value = "/customer/monitor")
	public String monitor() {
		return "I'm alive";
	}
}
