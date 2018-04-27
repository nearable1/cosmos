package com.inbody.crm.demo.sysjob;

import org.springframework.stereotype.Service;

@Service
public class DemoTaskJob {

	public void demoJob(){
		System.out.println("Demo job running...method:by spring xml");
	}
}
