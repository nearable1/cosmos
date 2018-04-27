package com.inbody.crm.demo.test.web;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;

@Controller
@RequestMapping(value = "${adminPath}/test/testManualJob")
public class TestManualJobController extends BaseController {
	
	@RequiresPermissions("test:testManualJob:view")
	@RequestMapping(value = { "list", "" })
	public String list(User user, Model model) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
			public void run(){
				System.out.println("Demo job running...method:by manual job");
			}
		};
		timer.schedule(task, 0);
		
		return "modules/test/testManualJob";
	}
}
