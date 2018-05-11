/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.family.web;

import java.util.List;

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
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.family.entity.Family;
import com.inbody.crm.modules.family.entity.FamilyTwo;
import com.inbody.crm.modules.family.service.FamilyService;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 商品类型Controller
 * @author liuyigeng
 * @version 2018-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/family/family")
public class FamilyController extends BaseController {

	@Autowired
	private FamilyService familyService;
	
	@ModelAttribute
	public Family get(@RequestParam(required=false) String id) {
		Family entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = familyService.get(id);
		}
		if (entity == null){
			entity = new Family();
		}
		return entity;
	}
	
	@RequiresPermissions("family:family:view")
	@RequestMapping(value = {"list", ""})
	public String list(Family family, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Family> page = familyService.findPage(new Page<Family>(request, response), family); 
		String id = UserUtils.getUser().getId();
		List<FamilyTwo> familys = familyService.findFamily(id);
		model.addAttribute("familys", familys);
		model.addAttribute("page", page);
		return "modules/family/familyList";
	}

	@RequiresPermissions("family:family:view")
	@RequestMapping(value = "form")
	public String form(Family family, Model model) {
		String userId = UserUtils.getUser().getId();
		String userName = UserUtils.getUser().getName();
		model.addAttribute("family", family);
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		return "modules/family/familyForm";
	}

	@RequiresPermissions("family:family:edit")
	@RequestMapping(value = "save")
	public String save(Family family, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, family)){
			return form(family, model);
		}
		familyService.save(family);
		addMessage(redirectAttributes, "保存商品类别成功");
		return "redirect:"+Global.getAdminPath()+"/family/family/?repage";
	}
	
	@RequiresPermissions("family:family:edit")
	@RequestMapping(value = "delete")
	public String delete(Family family, RedirectAttributes redirectAttributes) {
		familyService.delete(family);
		addMessage(redirectAttributes, "删除商品类别成功");
		return "redirect:"+Global.getAdminPath()+"/family/family/?repage";
	}

}