/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.im.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.im.domain.AxImInvoiceDtlExcel;
import com.inbody.crm.im.domain.ImInvoiceDtlExcel;
import com.inbody.crm.im.domain.ImInvoiceSearch;
import com.inbody.crm.im.service.ImInvoiceService;
import com.inbody.crm.modules.sys.entity.Dict;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 主子表Controller
 * @author zhanglulu
 * @version 2017-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/im/invoice")
public class ImInvoiceController extends BaseController {

	@Autowired
	private ImInvoiceService imInvoiceService;

	/**
	 * 发票信息一览取得
	 */
	@RequiresPermissions("im:invoice:view")
	@RequestMapping(value = "list")
	public String list(ImInvoiceSearch imInvoiceSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		if (DateUtils.compareDate(imInvoiceSearch.getInvoiceDateEnd(), imInvoiceSearch.getInvoiceDateBegin(), "yyyy-MM-dd") < 0) {
			
			ImInvoiceSearch cloneImInvoiceSearch = SerializationUtils
					.clone(imInvoiceSearch);
			
			this.addMessage(model, "开票开始日期必须小于结束日期");

			model.addAttribute("user", UserUtils.getUser());
			model.addAttribute("searchForm", cloneImInvoiceSearch);
			return "inbody/im/im001";
		}
		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		imInvoiceSearch.setSqlMap(uerMap);

		// 发票list数据取得
		Page<ImInvoiceSearch> page = imInvoiceService.findImInvoicePage(
				new Page<ImInvoiceSearch>(request, response),
				imInvoiceSearch);
		model.addAttribute("page", page);
		model.addAttribute("searchForm", imInvoiceSearch);
		
		return "inbody/im/im001";
	}

	@RequestMapping(value = "listPopu")
	public String listPopu(
			@RequestParam(value = "employeeNo", required = false) String employeeNo,
			@RequestParam(value = "organize", required = false) String organize,
			@RequestParam(value = "year", required = true) int year,
			@RequestParam(value = "month", required = true) int month,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		ImInvoiceSearch imInvoiceSearch = new ImInvoiceSearch();

		imInvoiceSearch.setCommissionPeison(employeeNo);
		imInvoiceSearch.setOrganize(organize);
		
		imInvoiceSearch.setInvoiceDateBegin(DateUtils.formatDate(DateUtils.getFirstDayOfMonth(year, month), "yyyy-MM-dd"));
		imInvoiceSearch.setInvoiceDateEnd(DateUtils.formatDate(DateUtils.getLastDayOfMonth(year, month), "yyyy-MM-dd"));

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		imInvoiceSearch.setSqlMap(uerMap);

		// 发票list数据取得
		Page<ImInvoiceSearch> page = imInvoiceService.findImInvoicePage(
				new Page<ImInvoiceSearch>(request, response),
				imInvoiceSearch);
		model.addAttribute("page", page);
		model.addAttribute("searchForm", imInvoiceSearch);
		
		return "inbody/im/im001";
	}
	
	/**
	 * 组信息取得
	 * 
	 * @param invoiceSource
	 * 
	 */
//	@RequiresPermissions("im:invoice:view")
//    @RequestMapping(value = "organizeList", method=RequestMethod.GET)
//	public @ResponseBody Map<String,Object> getOrganizeList(String invoiceSource) {
//		
//		Map<String,Object> jsonMap = new HashMap<String, Object>();
//
//		List<Dict> organizeList = imInvoiceService.getOrganizeList(invoiceSource);
//		
//		jsonMap.put("organizeList", organizeList);
//		return jsonMap;
//	}
	
	/**
	 * 提成人信息取得
	 * 
	 * @param organize
	 * 
	 */
	@RequiresPermissions("im:invoice:view")
    @RequestMapping(value = "commissionPeisonList", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getCommissionPeisonList(String organize) {
		
		Map<String,Object> jsonMap = new HashMap<String, Object>();

		List<Dict> commissionPeisonList = imInvoiceService.getCommissionPeisonList(organize);
		
		jsonMap.put("commissionPeisonList", commissionPeisonList);
		return jsonMap;
	}
	
	/**
	 * 导出发票明细数据
	 * 
	 * @param imInvoiceSearch
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("im:invoice:view")
    @RequestMapping(value = "list/export", method=RequestMethod.GET)
	public String exportImInvoiceFile(ImInvoiceSearch imInvoiceSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		imInvoiceSearch.setSqlMap(uerMap);

		try {
			String fileName = "发票列表_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<ImInvoiceDtlExcel> list = imInvoiceService
					.exportImInvoiceDtlList(imInvoiceSearch);
			new ExportExcel("发票列表", ImInvoiceDtlExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出发票列表！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/im/invoice/list/?repage";
	}
	
	/**
	 * AX销售发票导出
	 * 
	 * @param imInvoiceSearch
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("im:invoice:view")
    @RequestMapping(value = "list/axExport", method=RequestMethod.GET)
	public String axExportImInvoiceFile(ImInvoiceSearch imInvoiceSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		imInvoiceSearch.setSqlMap(uerMap);

		try {
			String fileName = "AX销售发票列表_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<AxImInvoiceDtlExcel> list = imInvoiceService
					.exportAxImInvoiceDtlList(imInvoiceSearch);
			new ExportExcel("AX销售发票列表", AxImInvoiceDtlExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出AX销售发票列表！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/im/invoice/list/?repage";
	}
}