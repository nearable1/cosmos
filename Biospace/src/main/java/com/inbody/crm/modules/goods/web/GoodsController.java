/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.goods.web;

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
import com.inbody.crm.modules.family.entity.FamilyTwo;
import com.inbody.crm.modules.family.service.FamilyService;
import com.inbody.crm.modules.goods.entity.Goods;
import com.inbody.crm.modules.goods.service.GoodsService;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 商品管理Controller
 * @author liuyigeng
 * @version 2018-05-02
 */
@Controller
@RequestMapping(value = "${adminPath}/goods/goods")
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private FamilyService familyService;
	
	@ModelAttribute
	public Goods get(@RequestParam(required=false) String id) {
		Goods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsService.get(id);
		}
		if (entity == null){
			entity = new Goods();
		}
		return entity;
	}
	
	@RequiresPermissions("goods:goods:view")
	@RequestMapping(value = {"list", ""})
	public String list(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Goods> page = goodsService.findPage(new Page<Goods>(request, response), goods); 
		String id = UserUtils.getUser().getId();
		String sellerName = UserUtils.getUser().getName();
		model.addAttribute("sellerid", id);
		model.addAttribute("sellerName", sellerName);
		List<FamilyTwo> family = familyService.findFamily(id);
		model.addAttribute("family", family);
		model.addAttribute("page", page);
		return "modules/goods/goodsList";
	}

	@RequiresPermissions("goods:goods:view")
	@RequestMapping(value = "form")
	public String form(Goods goods, Model model) {
		String id = UserUtils.getUser().getId();
		String sellerName = UserUtils.getUser().getName();
		model.addAttribute("sellerid", id);
		model.addAttribute("sellerName", sellerName);
		model.addAttribute("goods", goods);

		List<FamilyTwo> family = familyService.findFamily(id);
		model.addAttribute("family", family);
		return "modules/goods/goodsForm";
	}

	@RequiresPermissions("goods:goods:edit")
	@RequestMapping(value = "save")
	public String save(Goods goods, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goods)){
			return form(goods, model);
		}
		goodsService.save(goods);
		addMessage(redirectAttributes, "保存保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goods/?repage";
	}
	
	@RequiresPermissions("goods:goods:edit")
	@RequestMapping(value = "delete")
	public String delete(Goods goods, RedirectAttributes redirectAttributes) {
		goodsService.delete(goods);
		addMessage(redirectAttributes, "删除保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goods/?repage";
	}

}