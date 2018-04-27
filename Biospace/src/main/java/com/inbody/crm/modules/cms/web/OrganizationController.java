/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.cms.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.utils.CookieUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.cms.entity.Site;
import com.inbody.crm.modules.cms.service.SiteService;
import com.inbody.crm.modules.gendemo.entity.TreeNode;
import com.inbody.crm.modules.gendemo.service.TestTreeService;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 组织维度Controller
 * @author ThinkGem
 * @version 2017-1-12
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/organization")
public class OrganizationController extends BaseController {

	@Autowired
	private SiteService siteService;
	
	@Autowired
	private TestTreeService testTreeService;
	
	@ModelAttribute
	public Site get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return siteService.get(id);
		}else{
			return new Site();
		}
	}
	
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,Model model) {
		return "modules/cms/organization";
	}
	@ResponseBody
	@RequestMapping(value = "getZtreeData")
	public List<TreeNode> getZtreeData(HttpServletRequest request,Model model){
		List<TreeNode> json = new ArrayList<TreeNode>();
		String id = request.getParameter("id");
		json = testTreeService.getZtreeData(id);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/addNode")
	public TreeNode addNode(HttpServletRequest request,ModelMap map,RedirectAttributes redirectAttributes){
		String name = request.getParameter("name");
		String pId = request.getParameter("pId");
		testTreeService.addNode(name,pId);
		TreeNode addNode = testTreeService.findNewest();
		return addNode;
	}
	
	@ResponseBody
	@RequestMapping("/removeNode")
	public String removeNode(HttpServletRequest request,ModelMap map,RedirectAttributes redirectAttributes){
		String id = request.getParameter("id");
		testTreeService.removeNode(id);
		//addMessage(redirectAttributes, "删除站点失败");
		return "";
	}
	
	@ResponseBody
	@RequestMapping("/moveNode")
	public String moveNode(HttpServletRequest request,ModelMap map){
		String pId = request.getParameter("pId");
		String id = request.getParameter("id");
		testTreeService.moveNode(pId, id);
		return "";
	}
	
	@ResponseBody
	@RequestMapping("/renameNode")
	public String renameNode(HttpServletRequest request,ModelMap map,RedirectAttributes redirectAttributes){
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		testTreeService.renameNode(name, id);
		return "";
	}
	
	@RequiresPermissions("cms:organization:view")
	@RequestMapping(value = {"list", ""})
	public String list(Site site, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Site> page = siteService.findPage(new Page<Site>(request, response), site); 
        model.addAttribute("page", page);
		return "modules/cms/siteList";
	}

	@RequiresPermissions("cms:organization:view")
	@RequestMapping(value = "form")
	public String form(Site site, Model model) {
		model.addAttribute("site", site);
		return "modules/cms/siteForm";
	}

	@RequiresPermissions("cms:organization:edit")
	@RequestMapping(value = "save")
	public String save(Site site, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/site/?repage";
		}
		if (!beanValidator(model, site)){
			return form(site, model);
		}
		siteService.save(site);
		addMessage(redirectAttributes, "保存站点'" + site.getName() + "'成功");
		return "redirect:" + adminPath + "/cms/site/?repage";
	}
	
	@RequiresPermissions("cms:organization:edit")
	@RequestMapping(value = "delete")
	public String delete(Site site, @RequestParam(required=false) Boolean isRe, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/site/?repage";
		}
		if (Site.isDefault(site.getId())){
			addMessage(redirectAttributes, "删除站点失败, 不允许删除默认站点");
		}else{
			siteService.delete(site, isRe);
			addMessage(redirectAttributes, (isRe!=null&&isRe?"恢复":"")+"删除站点成功");
		}
		return "redirect:" + adminPath + "/cms/site/?repage";
	}
	
	/**
	 * 选择站点
	 * @param id
	 * @return
	 */
	@RequiresPermissions("cms:organization:select")
	@RequestMapping(value = "select")
	public String select(String id, boolean flag, HttpServletResponse response){
		if (id!=null){
			UserUtils.putCache("siteId", id);
			// 保存到Cookie中，下次登录后自动切换到该站点
			CookieUtils.setCookie(response, "siteId", id);
		}
		if (flag){
			return "redirect:" + adminPath;
		}
		return "modules/cms/siteSelect";
	}
}
