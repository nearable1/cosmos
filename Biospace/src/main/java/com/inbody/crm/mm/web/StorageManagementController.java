/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.mm.domain.UnrestoredExcel;
import com.inbody.crm.mm.domain.UnrestoredSearch;
import com.inbody.crm.mm.service.StorageManagementService;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 库存查询管理Controller
 * @author zhanglulu
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/mm/storageManagement")
public class StorageManagementController extends BaseController {

	@Autowired
	private StorageManagementService storageManagementService;

	/**
	 * 待归还信息一览取得
	 */
	@RequiresPermissions("mm:storageManagement:view")
	@RequestMapping(value = "unrestoredList")
	public String unrestoredList(UnrestoredSearch unrestoredSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		unrestoredSearch.setSqlMap(uerMap);

		// 待归还list数据取得
		Page<UnrestoredSearch> page = storageManagementService.findUnrestoredPage(
				new Page<UnrestoredSearch>(request, response),
				unrestoredSearch);
		model.addAttribute("page", page);
		model.addAttribute("searchForm", unrestoredSearch);
		
		return "inbody/mm/sm/sm004";
	}
	
	/**
	 * 导出待归还列表明细数据
	 * 
	 * @param unrestoredSearch
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("mm:storageManagement:view")
    @RequestMapping(value = "unrestoredList/exportDtl", method=RequestMethod.GET)
	public String exportUnrestoredFile(UnrestoredSearch unrestoredSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		unrestoredSearch.setSqlMap(uerMap);

		try {
			String fileName = "待归还列表_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<UnrestoredExcel> list = storageManagementService
					.exportUnrestoredList(unrestoredSearch);
			new ExportExcel("待归还列表", UnrestoredExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出待归还列表！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/mm/storageManagement/unrestoredList/?repage";
	}
}