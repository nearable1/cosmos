package com.inbody.crm.demo.sysjob;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("demo2TaskJob")
public class Demo2TaskJob {

	@Scheduled(cron = "0 * * * * ?")  
    public void job1() {  
        System.out.println("Demo job running...method:by source label");  
    }
}
