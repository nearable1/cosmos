/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ps.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.cm.entity.CustomerAddManagement;
import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.SelectEntity;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.ps.dao.PsAgreementDao;
import com.inbody.crm.ps.entity.PsApplyImportForm;
import com.inbody.crm.ps.entity.PsApplyPrice;
import com.inbody.crm.ps.entity.PsApplyPriceDtl;
import com.inbody.crm.ps.service.PsApplyPriceService;

/**
 * 主子表生成Controller
 * @author Nssol
 * @version 2017-07-20
 */
@Controller
@RequestMapping(value = "${adminPath}/ps/psApplyPrice")
public class PsApplyPriceController extends BaseController {

	@Autowired
	private PsApplyPriceService psApplyPriceService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PsAgreementDao cmAgreementDao;
	
	@ModelAttribute
	public PsApplyPrice get(@RequestParam(required=false) String id) {
		PsApplyPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = psApplyPriceService.get(id);
		}
		if (entity == null){
			entity = new PsApplyPrice();
		}
		return entity;
	}
	
	@RequiresPermissions("ps:psApplyPrice:view")
	@RequestMapping(value = {"list", ""})
	public String list(PsApplyPriceDtl psApplyPriceDtl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PsApplyPriceDtl> page = psApplyPriceService.findPage(new Page<PsApplyPriceDtl>(request, response), psApplyPriceDtl); 
		model.addAttribute("page", page);
		model.addAttribute("psApplyPriceDtl", psApplyPriceDtl);
		return "inbody/ps/ps001";
	}
	
	@RequiresPermissions("ps:psApplyPrice:view")
	@RequestMapping(value = {"searchExport"})
	public String searchExport(PsApplyPriceDtl psApplyPriceDtl, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		try {
			psApplyPriceService.exportSearchResult(psApplyPriceDtl, response);
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage()); 	
		}
		model.addAttribute("psApplyPriceDtl", psApplyPriceDtl);
		return "redirect:"+Global.getAdminPath()+"/ps/psApplyPrice/list?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = {"getProtocols"})
	public List<SelectEntity> getProtocols(String priceSystem) {
		CustomerAddManagement cam = new CustomerAddManagement();
		if(priceSystem == null || priceSystem.equals(""))
			cam.setCustomerType("");
		//代理的话，协议方的类型是代理
		else if(priceSystem.equals("1"))
			cam.setCustomerType("2");
		//协议直销的话，协议方的类型是最终客户
		else if(priceSystem.equals("3"))
			cam.setCustomerType("1");
		//协议经销的话，协议方的类型是经销商
		else if(priceSystem.equals("5"))
			cam.setCustomerType("3");
		else cam.setCustomerType("-");
		
		return cmAgreementDao.listProtocols(cam);
	}
	
	@ResponseBody
	@RequestMapping(value = {"getStartDays"})
	public List<SelectEntity> getStartDays(String parentId) {
		return cmAgreementDao.listStartDays(parentId);
	}
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("ps:psApplyPrice:edit")
    @RequestMapping(value = "upload", method=RequestMethod.POST)
    public String uploadExcel(MultipartFile file,PsApplyImportForm psApplyImport, Model model,RedirectAttributes redirectAttributes) {
		PsApplyPrice psApplyPrice = new PsApplyPrice();
		psApplyPrice.setAct(psApplyImport.getAct());
		psApplyPrice.setId(psApplyImport.getId());
		
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(psApplyPrice.getAct(),psApplyPrice.getId(),true);
		try {
			psApplyPriceService.excelAnalyse(file,psApplyPrice);
			psApplyPrice.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
			
			//psApplyPrice.setPriceSystem(psApplyImport.getPriceSystemCopy());
			psApplyPrice.setSelectPriceSystem(psApplyImport.getPriceSystemCopy());
			psApplyPrice.setProtocols(psApplyImport.getProtocolsCopy());
			psApplyPrice.setStartdays(psApplyImport.getStartdaysCopy());
			
			psApplyPriceService.saveTemp(psApplyPrice);
			
			model.addAttribute("psApplyPrice", psApplyPrice);
			model.addAttribute("psApplyImport", psApplyImport);
			
			addMessage(redirectAttributes, "导入成功！");
			
			model.addAttribute("viewsts", buttonlList);
			return "inbody/ps/ps002";
		} catch (Exception e) {
			//addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
			
			psApplyPrice.setSelectPriceSystem(psApplyImport.getPriceSystemCopy());
			psApplyPrice.setProtocols(psApplyImport.getProtocolsCopy());
			psApplyPrice.setStartdays(psApplyImport.getStartdaysCopy());
			model.addAttribute("psApplyPrice", psApplyPrice);
			model.addAttribute("psApplyImport", psApplyImport);
			model.addAttribute("message","导入失败！失败信息："+e.getMessage());
			model.addAttribute("viewsts", buttonlList);
			return "inbody/ps/ps002";
			
			//return "redirect:"+Global.getAdminPath()+"/ps/psApplyPrice/form?repage";
		}
		
    }
	
	@RequiresPermissions("ps:psApplyPrice:edit")
	@RequestMapping(value = "export")
	public String export(PsApplyPrice psApplyPrice, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, psApplyPrice)){
//			return form(psApplyPrice, model);
//		}
		try {
			//生成模板，包含上传的sheet页和物料信息sheet页
			psApplyPriceService.exportTemplateExcel(response, psApplyPrice);
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage().replace("\"", ""));
			model.addAttribute("psApplyPrice", psApplyPrice);
			return "redirect:"+Global.getAdminPath()+"/ps/psApplyPrice/form?repage";
//			Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(psApplyPrice.getAct(),psApplyPrice.getId(),true);
//			model.addAttribute("message","导入失败！失败信息："+e.getMessage());
//			model.addAttribute("psApplyPrice", psApplyPrice);
//			PsApplyImportForm psApplyImport = new PsApplyImportForm();
//			psApplyImport.setAct(psApplyPrice.getAct());
//			psApplyImport.setId(psApplyPrice.getId());
//			psApplyPrice.setSelectPriceSystem(psApplyPrice.getPriceSystem());
//			model.addAttribute("psApplyImport", psApplyImport);
//			model.addAttribute("viewsts", buttonlList);
//			return "inbody/ps/ps002";
		}
	}

	@RequiresPermissions("ps:psApplyPrice:view")
	@RequestMapping(value = "form")
	public String form(PsApplyPrice psApplyPrice, Model model) {
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(psApplyPrice.getAct(),psApplyPrice.getId(),true);
		model.addAttribute("psApplyPrice", psApplyPrice);
		PsApplyImportForm psApplyImport = new PsApplyImportForm();
		psApplyImport.setAct(psApplyPrice.getAct());
		psApplyImport.setId(psApplyPrice.getId());
		psApplyPrice.setSelectPriceSystem(psApplyPrice.getPriceSystem());
		model.addAttribute("psApplyImport", psApplyImport);
		model.addAttribute("viewsts", buttonlList);
		
		return "inbody/ps/ps002";
	}

	@RequiresPermissions("ps:psApplyPrice:edit")
	@RequestMapping(value = "save")
	public String save(PsApplyPrice psApplyPrice, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, psApplyPrice)){
//			return form(psApplyPrice, model);
//		}
//		psApplyPriceService.save(psApplyPrice);
		psApplyPriceService.doprocess(psApplyPrice);
		addMessage(redirectAttributes, "价格申请提交成功");
		//return "redirect:"+Global.getAdminPath()+"/ps/psApplyPrice/form?repage";
		return "redirect:" + adminPath + "/act/task/todo";
	}
	
	@RequiresPermissions("ps:psApplyPrice:edit")
	@RequestMapping(value = "applyback")
	public String applyback(PsApplyPrice psApplyPrice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, psApplyPrice)){
			return form(psApplyPrice, model);
		}
		psApplyPriceService.process(psApplyPrice);
		addMessage(redirectAttributes, "价格申请撤回成功");
		//return "redirect:"+Global.getAdminPath()+"/ps/psApplyPrice/form?repage";
		return "redirect:" + adminPath + "/act/task/todo";
	}
	
	@RequiresPermissions("ps:psApplyPrice:edit")
	@RequestMapping(value = "audit")
	public String audit(PsApplyPrice psApplyPrice, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(psApplyPrice.getAct().getFlag())){
			return form(psApplyPrice, model);
		}
		psApplyPriceService.process(psApplyPrice);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("ps:psApplyPrice:edit")
	@RequestMapping(value = "delete")
	public String delete(PsApplyPrice psApplyPrice, RedirectAttributes redirectAttributes) {
		psApplyPriceService.delete(psApplyPrice);
		addMessage(redirectAttributes, "删除价格申请成功");
		//return "redirect:"+Global.getAdminPath()+"/ps/psApplyPrice/?repage";
		return "redirect:" + adminPath + "/act/task/todo";
	}

}