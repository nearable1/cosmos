/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sd.entity.BoBusinessOpp;
import com.inbody.crm.sd.entity.BoBusinessOppDtl;
import com.inbody.crm.sd.entity.CmCustomerInfo;
import com.inbody.crm.sd.service.BoBusinessOppService;

/**
 * 主子表生成Controller
 * @author Nssol
 * @version 2017-07-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sd/boBusinessOpp")
public class BoBusinessOppController extends BaseController {

	@Autowired
	private BoBusinessOppService boBusinessOppService;
	@Autowired
	private CommonService commonService;
	
	@RequiresPermissions("sd:boBusinessOpp:view")
	@RequestMapping(value = {"list", ""})
	public String list(BoBusinessOpp boBusinessOpp, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(boBusinessOpp == null){
			boBusinessOpp = new BoBusinessOpp();
		}
		
		User user = UserUtils.getUser();
		/*if(!StringUtils.isEmptyNull(boBusinessOpp.getResponsiblePersonId())){
			User person = UserUtils.get(boBusinessOpp.getResponsiblePersonId(), user.getCurrentLocaleId());
			boBusinessOpp.setResponsiblePersonName(person.getName());
		}else{
			boBusinessOpp.setResponsiblePersonId(user.getId());
			boBusinessOpp.setResponsiblePersonName(user.getName());
		}*/
		
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user, "sd:boBusinessOpp:view"));
		boBusinessOpp.setSqlMap(uerMap);
		
		/*if(StringUtils.isEmptyNull(boBusinessOpp.getIfSalesPlan())){
			boBusinessOpp.setIfSalesPlan("1");
		}*/
		Page<BoBusinessOpp> page = boBusinessOppService.findPage(new Page<BoBusinessOpp>(request, response), boBusinessOpp); 
		model.addAttribute("page", page);
		//model.addAttribute("boBusinessOpp", "boBusinessOpp");
		return "/sd/bo/bo002";
	}

	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sd:boBusinessOpp:view")
    @RequestMapping(value = "export")
    public String exportFile(BoBusinessOpp boBusinessOpp, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			/*if(!StringUtils.isEmptyNull(boBusinessOpp.getResponsiblePersonId())){
				User person = UserUtils.get(boBusinessOpp.getResponsiblePersonId(), user.getCurrentLocaleId());
				boBusinessOpp.setResponsiblePersonName(person.getName());
			}else{
				boBusinessOpp.setResponsiblePersonId(user.getId());
				boBusinessOpp.setResponsiblePersonName(user.getName());
			}*/
			Map<String, String> uerMap = new HashMap<String, String>();
			uerMap.put("dataScope", BaseService.dataScopeFilter(user, "sd:boBusinessOpp:view"));
			boBusinessOpp.setSqlMap(uerMap);
			
            String fileName = "商机信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<BoBusinessOpp> page = boBusinessOppService.findExportList(boBusinessOpp);
    		new ExportExcel("商机信息", BoBusinessOpp.class).setDataList(page).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出商机信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/boBusinessOpp/list?repage";
    }

	@RequiresPermissions("sd:boBusinessOpp:view")
	@RequestMapping(value = "form")
	public String form(BoBusinessOpp boBusinessOpp, Model model) {
		if(StringUtils.isNotBlank(boBusinessOpp.getId())){
			boBusinessOpp = boBusinessOppService.get(boBusinessOpp.getId());
		}
		User user = UserUtils.getUser();
		if(!StringUtils.isEmptyNull(boBusinessOpp.getResponsiblePersonId())){
			User person = UserUtils.get(boBusinessOpp.getResponsiblePersonId(), user.getCurrentLocaleId());
			boBusinessOpp.setResponsiblePersonName(person.getName());
		}else{
			boBusinessOpp.setResponsiblePersonId(user.getId());
			boBusinessOpp.setResponsiblePersonName(user.getName());
		}
		if(boBusinessOpp.getBoBusinessOppDtlList() != null && boBusinessOpp.getBoBusinessOppDtlList().size()>0){
			for(BoBusinessOppDtl boBusinessOppDtl:boBusinessOpp.getBoBusinessOppDtlList()){
				if("0".equals(boBusinessOppDtl.getIfSpecialOffer())){
					boBusinessOppDtl.setIfSpecialOfferLabel("标准价");
				}else{
					boBusinessOppDtl.setIfSpecialOfferLabel("特价");
				}
			}
		}

		if(StringUtils.isEmptyNull( boBusinessOpp.getIfSalesPlan())){
			boBusinessOpp.setIfSalesPlan("0");
		}
		if(StringUtils.isEmptyNull(boBusinessOpp.getIfFail())){
			boBusinessOpp.setIfFail("0");
		}
		if(!StringUtils.isEmptyNull(boBusinessOpp.getIndustry())){
			boBusinessOpp.setIndustryCode("DM0002_"+boBusinessOpp.getIndustry());
		}
		// 组的取得
		if(StringUtils.isEmptyNull(boBusinessOpp.getOrganize())){
			boBusinessOpp.setOrganize(user.getOffice().getId());
		}
		model.addAttribute("boBusinessOpp", boBusinessOpp);
		return "/sd/bo/bo001";
	}

	@RequiresPermissions("sd:boBusinessOpp:edit")
	@ResponseBody
	@RequestMapping(value = "checkFormData")
	public Map<String,String> checkFormData(BoBusinessOpp boBusinessOpp,Model model) {
		Map<String,String> messageList = Maps.newHashMap();
		Boolean hasError = false;
		List<String> list =Lists.newArrayList();
		
		BoBusinessOpp entity = null;
		if (StringUtils.isNotBlank(boBusinessOpp.getId())){
			entity = boBusinessOppService.get(boBusinessOpp.getId());
			if(entity.getUpdateDate().compareTo(boBusinessOpp.getUpdateDate())!=0){
				list.add("该商机已经被其他人更新了。");
			}
		}
			
		if(boBusinessOpp.getBoBusinessOppDtlList().size() == 0){ 
			list.add("商机明细至少需要录入一条。");
		}else{
			List<BoBusinessOppDtl> detailList = Lists.newArrayList();		// 子表列表
			for(BoBusinessOppDtl boDetail :boBusinessOpp.getBoBusinessOppDtlList()){
				Boolean addFlag = true;
				if("1".equals(boDetail.getDelFlag())){
					addFlag = false;
				}
				if(!StringUtils.isEmptyNull(boDetail.getMaterialNo())){
					if(StringUtils.isEmptyNull(boDetail.getNum())){
						addFlag = false;
						list.add("商机明细第"+boDetail.getLineNo()+"行中录入物料号必须输入数量。");
					}
					if(StringUtils.isEmptyNull(boDetail.getUnitPrice())){
						addFlag = false;
						list.add("商机明细第"+boDetail.getLineNo()+"行中录入物料号必须输入单价。");
					}
				}else{
					//如果物料号没有录入，认为是无效行。
					addFlag = false;
				}
				if(addFlag){
					detailList.add(boDetail);
				}
			}
			
			if(detailList.size() == 0){ 
				list.add("商机明细至少需要录入一条。");
			}
		}
		if(list.size() > 0){
			hasError = true;
		}
		if(hasError){
			StringBuilder sb = new StringBuilder();
			for (String message : list){
				sb.append(message).append(list.size()>1?"<br/>":"");
			}
			messageList.put("error", "true");
			messageList.put("errorMessage", sb.toString());
		}
		return messageList;
	}
		
	@RequiresPermissions("sd:boBusinessOpp:edit")
	@RequestMapping(value = "save")
	public String save(BoBusinessOpp boBusinessOpp, Model model, RedirectAttributes redirectAttributes) {
		Calendar c = Calendar.getInstance();
		
		if(StringUtils.isEmptyNull(boBusinessOpp.getBusinessOppNo())){
			String code=commonService.getNextSequence("SJ",5);
			String businessOppNo = "SJ_"+c.get(Calendar.YEAR)+"_"+code;
			boBusinessOpp.setBusinessOppNo(businessOppNo);
		}
		
		if(StringUtils.isEmptyNull( boBusinessOpp.getIfSalesPlan())){
			boBusinessOpp.setIfSalesPlan("0");
		}
		if(StringUtils.isEmptyNull(boBusinessOpp.getIfFail())){
			boBusinessOpp.setIfFail("0");
		}
		boBusinessOpp.setIfTenderAuthor("0");
		boBusinessOpp.setIfContractGeneration("0");
		boBusinessOppService.save(boBusinessOpp);
		addMessage(redirectAttributes, "保存商机信息成功");
		return "redirect:"+Global.getAdminPath()+"/sd/boBusinessOpp/list?repage";
	}
	
	@RequiresPermissions("sd:boBusinessOpp:edit")
	@RequestMapping(value = "delete")
	public String delete(BoBusinessOpp boBusinessOpp, RedirectAttributes redirectAttributes) {
		boBusinessOppService.delete(boBusinessOpp);
		addMessage(redirectAttributes, "删除商机信息成功");
		return "redirect:"+Global.getAdminPath()+"/sd/bo/bo002/?repage";
	}
	
	/**
	 * 取得经销商信息
	 * @param customerId
	 *        客户代码
	 * @return 经销商信息
	 */
	@RequestMapping(value = "getCustomerInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getCustomerInfo(
			@RequestParam(value = "customerId", required = false) String customerId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		CmCustomerInfo customerInfo = boBusinessOppService.getCustomerInfo(customerId);
		
		jsonMap.put("customerInfo", customerInfo);
		return jsonMap;
	}
	
	/**
	 * 取得物料信息
	 * @return
	 */
//	@RequestMapping(value = "getMaterialInfo", method = RequestMethod.GET)
//	public @ResponseBody Map<String, Object> getMaterialInfo(
//			@RequestParam(value = "customerId", required = false) String customerId,
//			@RequestParam(value = "industry", required = false) String industry,
//			@RequestParam(value = "priceSystem", required = false) String priceSystem,
//			@RequestParam(value = "materialNo", required = false) String materialNo,
//			@RequestParam(value = "orderType", required = false) String orderType,
//			@RequestParam(value = "region", required = false) String region) {
//
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		PsPriceInfo materialPriceinfo = null;
//		if (!StringUtils.isEmptyNull(materialNo)
//				&& !StringUtils.isEmptyNull(priceSystem) && !StringUtils.isEmptyNull(orderType)
//				&& !StringUtils.equals(orderType, "2") && !StringUtils.isEmptyNull(region)) {
//			PsPriceInfo psPriceInfo = new PsPriceInfo();
//			psPriceInfo.setPriceSystem(priceSystem);
//			psPriceInfo.setCustomerId(customerId);
//			psPriceInfo.setMaterialNo(materialNo);
//			psPriceInfo.setIndustry(industry);
//			psPriceInfo.setRegion(region);
//			psPriceInfo.setOrderDate(new Date());
//			
//			materialPriceinfo = boBusinessOppService.getPsPriceInfo(psPriceInfo);
//		}
//		jsonMap.put("materialPriceinfo", materialPriceinfo);
//		return jsonMap;
//	}
	
	@RequiresPermissions("sd:boBusinessOpp:edit")
	@RequestMapping(value = "setIfSalePlan")
	public String setIfSalePlan(BoBusinessOpp boBusinessOpp, HttpServletRequest request, HttpServletResponse response, Model model) {
		String ifSalesPlan = boBusinessOpp.getIsSalesPlan();
		List<String> boBusinessNoList = boBusinessOpp.getSelectedBobusinessList();
		String boBusinessNoOld = "";
		for(String buBusinessNo:boBusinessNoList){
			if(!boBusinessNoOld.equals(buBusinessNo)){
				BoBusinessOpp boBusinessOppUpdate = new BoBusinessOpp();
				boBusinessOppUpdate.setBusinessOppNo(buBusinessNo);
				boBusinessOppUpdate.setIfSalesPlan(ifSalesPlan);
				boBusinessOppService.updateBoBusinessOpp(boBusinessOppUpdate);
			}
		}
		
		return "redirect:"+Global.getAdminPath()+"/sd/boBusinessOpp/list?repage";
	}

	@RequiresPermissions("sd:boBusinessOpp:edit")
	@ResponseBody
	@RequestMapping(value = "turnTenderAuthor")
	public Map<String,String> turnTenderAuthor(String id) {
		Map<String,String> messageList = Maps.newHashMap();
		Boolean hasError = false;
		List<String> list =Lists.newArrayList();
		
		BoBusinessOpp entity = boBusinessOppService.get(id);
		if (StringUtils.isEmptyNull(entity.getEndCustomerId())){
			list.add("该商机的最终客户未维护，请先维护。");
		}
//		if (StringUtils.isEmptyNull(entity.getCustomerSegmentation())){
//			list.add("该商机的科室/系未维护，请先维护。");
//		}
		if (StringUtils.isEmptyNull(entity.getAgentId())){
			list.add("该商机的经销商/代理商未维护，请先维护。");
		}
		if(list.size() > 0){
			hasError = true;
		}
		if(hasError){
			StringBuilder sb = new StringBuilder();
			for (String message : list){
				sb.append(message).append(list.size()>1?"<br/>":"");
			}
			messageList.put("error", "true");
			messageList.put("errorMessage", sb.toString());
		}
		return messageList;
	}
}