/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.web;

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
import com.inbody.crm.vr.entity.VrVisitDtl;
import com.inbody.crm.vr.service.VrVisitDtlService;

/**
 * 主子表生成Controller
 * @author Nssol
 * @version 2017-07-20
 */
@Controller
@RequestMapping(value = "${adminPath}/vr/vrVisitDtl")
public class VrVisitDtlController extends BaseController {

	@Autowired
	private VrVisitDtlService vrVisitDtlService;
	
	@ModelAttribute
	public VrVisitDtl get(@RequestParam(required=false) String id) {
		VrVisitDtl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vrVisitDtlService.get(id);
		}
		if (entity == null){
			entity = new VrVisitDtl();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(VrVisitDtl vrVisitDtl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<VrVisitDtl> page = vrVisitDtlService.findPage(new Page<VrVisitDtl>(request, response), vrVisitDtl); 
		model.addAttribute("page", page);
		return "inbody/vr/vrVisitDtlList";
	}

	@RequestMapping(value = "form")
	public String form(VrVisitDtl vrVisitDtl, Model model) {
		model.addAttribute("vrVisitDtl", vrVisitDtl);
		return "inbody/vr/vrVisitDtlForm";
	}

	@RequestMapping(value = "save")
	public String save(VrVisitDtl vrVisitDtl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vrVisitDtl)){
			return form(vrVisitDtl, model);
		}
		vrVisitDtlService.save(vrVisitDtl);
		addMessage(redirectAttributes, "保存主子表成功");
		return "redirect:"+Global.getAdminPath()+"/vr/vrVisitDtl/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(VrVisitDtl vrVisitDtl, RedirectAttributes redirectAttributes) {
		vrVisitDtlService.delete(vrVisitDtl);
		addMessage(redirectAttributes, "删除主子表成功");
		return "redirect:"+Global.getAdminPath()+"/vr/vrVisitDtl/?repage";
	}

}