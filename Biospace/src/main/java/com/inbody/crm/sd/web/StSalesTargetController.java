/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.sd.domain.StSalesTargetCompareEmployeeExcel;
import com.inbody.crm.sd.domain.StSalesTargetCompareOrganizeExcel;
import com.inbody.crm.sd.domain.StSalesTargetExcel;
import com.inbody.crm.sd.domain.StSalesTargetSearch;
import com.inbody.crm.sd.service.StSalesTargetService;

/**
 * 销售目标Controller
 * @author zhanglulu
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sd/stSalesTarget")
public class StSalesTargetController extends BaseController {

	@Autowired
	private StSalesTargetService stSalesTargetService;

	/**
	 * 销售目标查询一览取得
	 */
	@RequiresPermissions("sd:stSalesTarget:view")
	@RequestMapping(value = "list")
	public String list(StSalesTargetSearch stSalesTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		String year = stSalesTargetSearch.getYearSearch();
		if (StringUtils.isEmptyNull(year)) {
			year = DateUtils.getYear();
			stSalesTargetSearch.setYearSearch(year);
		}

//		stSalesTargetSearch.setYear(stSalesTargetSearch.getYearSearch());
		stSalesTargetSearch.setYear(year);
		// list数据取得
		stSalesTargetSearch = stSalesTargetService.getStSalesTargetList(stSalesTargetSearch);

		model.addAttribute("stSalesTargetSearch", stSalesTargetSearch);
		
		return "sd/st/st001";
	}
	
	/**
	 * 目标制定保存
	 * @param atAgentTarget
	 * @return
	 */
	@RequiresPermissions("sd:stSalesTarget:edit")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(StSalesTargetSearch stSalesTargetSearch,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			String message = stSalesTargetService.validator(stSalesTargetSearch);
			
			if (StringUtils.isEmptyNull(message)) {

				stSalesTargetService.save(stSalesTargetSearch);
			} else {
				jsonMap.put("success", false);
				jsonMap.put("message", message);
				return jsonMap;
			}
		} catch (ServiceException e) {
			jsonMap.put("success", false);
			jsonMap.put("message", "目标制定失败！");
			return jsonMap;
		}

		jsonMap.put("success", true);
		return jsonMap;
	}
	
	/**
	 * 导出销售目标数据
	 * 
	 * @param soOrderSearch
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("sd:stSalesTarget:view")
    @RequestMapping(value = "export", method=RequestMethod.GET)
	public String export(StSalesTargetSearch stSalesTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		
		try {
			String fileName = "销售目标_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<StSalesTargetExcel> list = stSalesTargetService.export(stSalesTargetSearch);
			new ExportExcel(null, StSalesTargetExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出销售目标一览！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/stSalesTarget/list?repage";
	}

	/**
	 * 销售业绩与目标查询-销售员一览取得
	 */
	@RequiresPermissions("sd:stSalesTarget:view")
	@RequestMapping(value = "compare/employeeList")
	public String employeeList(StSalesTargetSearch stSalesTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		String year = stSalesTargetSearch.getYear();
		if (StringUtils.isEmptyNull(year)) {
			year = DateUtils.getYear();
			stSalesTargetSearch.setYear(year);
		}

		// list数据取得
		stSalesTargetSearch = stSalesTargetService.getStSalesTargetCompareEmployeeList(stSalesTargetSearch);

		model.addAttribute("stSalesTargetSearch", stSalesTargetSearch);
		
		return "sd/st/st002";
	}
	
	/**
	 * 导出销售目标与业绩比较查询-销售员数据
	 * 
	 * @param soOrderSearch
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("sd:stSalesTarget:view")
    @RequestMapping(value = "compare/employeeList/export", method=RequestMethod.GET)
	public String employeeListExport(StSalesTargetSearch stSalesTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		
		try {
			String fileName = "销售目标与业绩比较查询-销售员_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<StSalesTargetCompareEmployeeExcel> list = stSalesTargetService.exportEmployeeList(stSalesTargetSearch);
			new ExportExcel(null, StSalesTargetCompareEmployeeExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出销售目标与业绩比较查询-销售员一览！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/stSalesTarget/compare/employeeList?repage";
	}

	/**
	 * 销售业绩与目标查询-组一览取得
	 */
	@RequiresPermissions("sd:stSalesTarget:view")
	@RequestMapping(value = "compare/organizeList")
	public String organizeList(StSalesTargetSearch stSalesTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		String year = stSalesTargetSearch.getYear();
		if (StringUtils.isEmptyNull(year)) {
			year = DateUtils.getYear();
			stSalesTargetSearch.setYear(year);
		}

		// list数据取得
		stSalesTargetSearch = stSalesTargetService.getStSalesTargetCompareOrganizeList(stSalesTargetSearch);

		model.addAttribute("stSalesTargetSearch", stSalesTargetSearch);
		
		return "sd/st/st003";
	}
	
	/**
	 * 导出销售目标与业绩比较查询-组数据
	 * 
	 * @param soOrderSearch
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("sd:stSalesTarget:view")
    @RequestMapping(value = "compare/organizeList/export", method=RequestMethod.GET)
	public String organizeListExport(StSalesTargetSearch stSalesTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		
		try {
			String fileName = "销售目标与业绩比较查询-组_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<StSalesTargetCompareOrganizeExcel> list = stSalesTargetService.exportOrganizeList(stSalesTargetSearch);
			new ExportExcel(null, StSalesTargetCompareOrganizeExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出销售目标与业绩比较查询-组一览！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/stSalesTarget/compare/organizeList?repage";
	}
}