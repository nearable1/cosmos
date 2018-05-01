/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.student.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.student.entity.DemoClasses;
import com.inbody.crm.modules.student.service.DemoClassesService;

/**
 * 班级管理Controller
 * @author liuyigeng
 * @version 2018-04-29
 */
@Controller
@RequestMapping(value = "${adminPath}/student/demoClasses")
public class DemoClassesController extends BaseController {

	@Autowired
	private DemoClassesService demoClassesService;
	
	@ModelAttribute
	public DemoClasses get(@RequestParam(required=false) String id) {
		DemoClasses entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = demoClassesService.get(id);
		}
		if (entity == null){
			entity = new DemoClasses();
		}
		return entity;
	}
	
	@RequiresPermissions("student:demoClasses:view")
	@RequestMapping(value = {"list", ""})
	public String list(DemoClasses demoClasses, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DemoClasses> page = demoClassesService.findPage(new Page<DemoClasses>(request, response), demoClasses); 
		model.addAttribute("page", page);
		return "modules/student/demoClassesList";
	}

	@RequiresPermissions("student:demoClasses:view")
	@RequestMapping(value = "form")
	public String form(DemoClasses demoClasses, Model model) {
		model.addAttribute("demoClasses", demoClasses);
		return "modules/student/demoClassesForm";
	}

	@RequiresPermissions("student:demoClasses:edit")
	@RequestMapping(value = "save")
	public String save(DemoClasses demoClasses, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, demoClasses)){
			return form(demoClasses, model);
		}
		demoClassesService.save(demoClasses);
		addMessage(redirectAttributes, "保存班级信息成功");
		return "redirect:"+Global.getAdminPath()+"/student/demoClasses/?repage";
	}
	
	@RequiresPermissions("student:demoClasses:edit")
	@RequestMapping(value = "delete")
	public String delete(DemoClasses demoClasses, RedirectAttributes redirectAttributes) {
		demoClassesService.delete(demoClasses);
		addMessage(redirectAttributes, "删除班级信息成功");
		return "redirect:"+Global.getAdminPath()+"/student/demoClasses/?repage";
	}

}