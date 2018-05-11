/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.gym.web;

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
import com.inbody.crm.modules.gym.entity.Gym;
import com.inbody.crm.modules.gym.service.GymService;

/**
 * 健身知识Controller
 * @author liuyigeng
 * @version 2018-05-02
 */
@Controller
@RequestMapping(value = "${adminPath}/gym/gym")
public class GymController extends BaseController {

	@Autowired
	private GymService gymService;
	
	@ModelAttribute
	public Gym get(@RequestParam(required=false) String id) {
		Gym entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gymService.get(id);
		}
		if (entity == null){
			entity = new Gym();
		}
		return entity;
	}
	
	@RequiresPermissions("gym:gym:view")
	@RequestMapping(value = {"list", ""})
	public String list(Gym gym, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gym> page = gymService.findPage(new Page<Gym>(request, response), gym); 
		model.addAttribute("page", page);
		return "modules/gym/gymList";
	}

	@RequiresPermissions("gym:gym:view")
	@RequestMapping(value = "form")
	public String form(Gym gym, Model model) {
		model.addAttribute("gym", gym);
		return "modules/gym/gymForm";
	}

	@RequiresPermissions("gym:gym:edit")
	@RequestMapping(value = "save")
	public String save(Gym gym, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gym)){
			return form(gym, model);
		}
		gymService.save(gym);
		addMessage(redirectAttributes, "保存保存健身知识成功");
		return "redirect:"+Global.getAdminPath()+"/gym/gym/?repage";
	}
	
	@RequiresPermissions("gym:gym:edit")
	@RequestMapping(value = "delete")
	public String delete(Gym gym, RedirectAttributes redirectAttributes) {
		gymService.delete(gym);
		addMessage(redirectAttributes, "删除保存健身知识成功");
		return "redirect:"+Global.getAdminPath()+"/gym/gym/?repage";
	}

}