/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.demo.test.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.inbody.crm.common.beanvalidator.BeanValidators;
import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.utils.excel.ImportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.demo.test.entity.Test;
import com.inbody.crm.demo.test.entity.TestExcel;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.service.SystemService;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 用户Controller
 * 
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testExcel")
public class TestExcelController extends BaseController {

	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}

	@RequiresPermissions("test:testExcel:view")
	@RequestMapping(value = { "list", "" })
	public String list(Test test, Model model) {
		return "modules/test/testExcel";
	}

	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("test:testExcel:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "exporttest_"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<TestExcel> list = Lists.newArrayList(); 
            TestExcel test = new TestExcel();
            test.setLoginName("loginnametest");
            test.setName("nametest");
            list.add(test);
            new ExportExcel("", TestExcel.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/test/testExcel/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("test:testExcel:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<TestExcel> list = ei.getDataList(TestExcel.class);
			String result = "";
			for (TestExcel user : list){
				result += user.getLoginName() + ",";
			}
			addMessage(redirectAttributes, "import test sucess."+result);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/test/testExcel/list?repage";
    }
	
}
