/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.web;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.SnInfo;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.rm.domain.RmRepairInfoEx;
import com.inbody.crm.rm.domain.RmRepairInput;
import com.inbody.crm.rm.domain.RmRepairListExcel;
import com.inbody.crm.rm.domain.RmRepairListSearch;
import com.inbody.crm.rm.domain.RmRepairSnSearch;
import com.inbody.crm.rm.service.RmRepairService;

/**
 * 单表生成Controller
 * 
 * @author yangj
 * @version 2017-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/rm/repair")
public class RmRepairController extends BaseController {

	@Autowired
	private RmRepairService rmRepairService;

	@RequiresPermissions("rm:repair:view")
	@RequestMapping(value = "search")
	public String search(RmRepairSnSearch search, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		// 查询类型为0，初始
		if (StringUtils.isBlank(search.getSearchType())) {
			search.setSearchType(CommonConstants.REPAIR_SN_SEARCH_TYPE_2);
			model.addAttribute("search", search);
//			model.addAttribute("page", new Page<RmRepairSnSearch>(request, response));
//			return "inbody/rm/rm003";
		}

//		if (StringUtils.isBlank(search.getSnNo())
//				&& StringUtils.isBlank(search.getModel())
//				&& StringUtils.isBlank(search.getCustomerChName())
//				&& StringUtils.isBlank(search.getEndCustomerName())
//				&& StringUtils.isBlank(search.getTelephone())) {
//			this.addMessage(model, "查询失败：请至少一个查询条件！");
//		}

		Page<RmRepairSnSearch> page = rmRepairService.findRepairSnList(
				new Page<RmRepairSnSearch>(request, response), search);

		model.addAttribute("search", search);
		model.addAttribute("page", page);

		return "inbody/rm/rm003";
	}
	
	@RequiresPermissions("rm:repair:view")
	@RequestMapping(value = "form")
    public String repairInput(@RequestParam(required = false) String snNo,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String repairType, Model model) {
		
		if (StringUtils.isBlank(snNo) && StringUtils.isBlank(id)){
			this.addMessage(model, "维修录入信息画面打开失败：参数不正确！");
		}

		RmRepairInput repair = rmRepairService.getRepairInput(snNo, id);
        // 其它画面链接维修分类设定
        if (StringUtils.isBlank(id) && !StringUtils.isBlank(repairType)) {
            repair.getRepairInfo().setRepairType(repairType);
        }
		model.addAttribute("repair", repair);

		return "inbody/rm/rm004";
	}
	
	@RequiresPermissions("rm:repair:edit")
	@RequestMapping(value = "save")
	public String saveRepair(RmRepairInput input, Model model,
			RedirectAttributes redirectAttributes) {

		// 画面输入值验证
		if (!beanValidator(model, input)) {
			model.addAttribute("repair", input);
			rmRepairService.setSnInfoAndHis(input.getRepairInfo().getSnNo(),
					input);
			return "inbody/rm/rm004";
		}

		// 画面输入关联验证
		if (!validRepairInput(input, model)) {
			model.addAttribute("repair", input);
			rmRepairService.setSnInfoAndHis(input.getRepairInfo().getSnNo(),
					input);
			return "inbody/rm/rm004";
		}

		// 画面输入值保存
		RmRepairInput inputClone = SerializationUtils.clone(input);

		try {
			String id = rmRepairService.saveRepairInput(input);
			input.setId(id);
			this.addMessage(redirectAttributes, "维修信息保存成功！");
		} catch (ServiceException e) {
			this.addMessage(model, e.getMessage());
			rmRepairService.setSnInfoAndHis(inputClone.getRepairInfo().getSnNo(),
					inputClone);
			model.addAttribute("repair", inputClone);
			return "inbody/rm/rm004";
		}

		return "redirect:" + adminPath + "/rm/repair/form?id="
				+ input.getId();
	}
	
	/**
	 * 维修记录管理list取得
	 */
	@RequiresPermissions("rm:repairList:view")
	@RequestMapping(value = "list")
	public String list(RmRepairListSearch search, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<RmRepairListSearch> page = rmRepairService.findRepairPageList(
				new Page<RmRepairListSearch>(request, response), search);

		model.addAttribute("search", search);
		model.addAttribute("page", page);
		return "inbody/rm/rm005";
	}
	
    /**
     * 维修信息excel导出
     */
    @RequiresPermissions("rm:repair:view")
    @RequestMapping(value = "export")
    public String export(RmRepairListSearch search, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "维修记录一览_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<RmRepairListExcel> list = rmRepairService.exportRepairList(search);
            new ExportExcel("维修记录一览", RmRepairListExcel.class).setDataList(list)
                    .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出维修记录一览！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rm/repair/list?repage";
    }

	/**
	 * 取得样机信息
	 */
	@RequiresPermissions("rm:repair:view")
	@RequestMapping(value = "prototype/{ptySn}", method = RequestMethod.GET)
	public @ResponseBody RmRepairInfoEx getPtyProductionDate(@PathVariable String ptySn) {
		RmRepairInfoEx ptyInfo = rmRepairService.getPrototypeInfo(ptySn);
		if (ptyInfo == null) {
			ptyInfo = new RmRepairInfoEx();
		}
		return ptyInfo;
	}
	
	/**
	 * 维修录入信息关联验证
	 */
	private boolean validRepairInput(RmRepairInput input, Model model) {
		// 关联验证
		RmRepairInfoEx repairInfo = input.getRepairInfo();
		// 处理状态为"已取消"时，状态说明必须填写
		if (StringUtils.equals(repairInfo.getRepairStatus(),
				CommonConstants.REPAIR_STATUS_1)
				&& StringUtils.isBlank(repairInfo.getStatusRemarks())) {
			this.addMessage(model, "保存失败：处理状态为[已取消]时，处理说明为必填项！");
			return false;
		}
		
		// TODO
		//验证LBSN输入值是否存在
		//验证LBSN输入值是否LB软件类型

		// 处理方式为"返厂"并且替换样机"有"时，样机信息必须真写
		if (StringUtils.equals(repairInfo.getRepairMethod(),
				CommonConstants.REPAIR_METHOD_4)
				&& StringUtils.equals(repairInfo.getIfPrototype(),
						CommonConstants.YES)) {
			if (StringUtils.isBlank(repairInfo.getPrototypeSnNo())
					|| repairInfo.getPrototypeDateFrom() == null) {
				this.addMessage(model, "保存失败：有替换机时，样机S/N、样机发出时间是必填项！");
				return false;
			}
			
			// 样子sn不能等于维修sn
			if (StringUtils.equals(repairInfo.getPrototypeSnNo(),
					repairInfo.getSnNo())) {
				this.addMessage(model, "保存失败：样机S/N不能与维修物料的S/N相同！");
				return false;
			}

			/*
			// 处理状态为"处理完毕"时，样机返回时间必填
			if (StringUtils.equals(repairInfo.getRepairStatus(),
					CommonConstants.REPAIR_STATUS_3)
					&& repairInfo.getPrototypeDateTo() == null) {
				this.addMessage(model, "保存失败：处理状态为[处理完毕]且有替换样机时，样机返回时间是必填项！");
				return false;
			}
			*/

			// 样机发出时间不能大于样机返回时间
			if (repairInfo.getPrototypeDateFrom() != null
					&& repairInfo.getPrototypeDateTo() != null
					&& repairInfo.getPrototypeDateFrom().getTime() > repairInfo
							.getPrototypeDateTo().getTime()) {
				this.addMessage(model, "保存失败：样机发出时间不能晚于样机返回时间！");
				return false;
			}
		}
		
		// 处理方式为"返厂"并且替换样机"有"时，样机信息必须真写
		if (StringUtils.equals(repairInfo.getRepairMethod(),
				CommonConstants.REPAIR_METHOD_4)) {
			// 维修机到货时间不能大于维修机出库时间
			if (repairInfo.getMaintenanceDateFrom() != null
					&& repairInfo.getMaintenanceDateTo() != null
					&& repairInfo.getMaintenanceDateFrom().getTime() > repairInfo
							.getMaintenanceDateTo().getTime()) {
				this.addMessage(model, "保存失败：维修机到货时间不能晚于维修机出库时间！");
				return false;
			}
		}
		
		return true;
	}
    
    @RequestMapping(value = "getSnInfo")
    @ResponseBody
    public  Map<String, Object>  getSnInfo(String snNo){

    	SnInfo sm = rmRepairService.getSnInfo(snNo);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("snNo", sm.getSnNo());
    	map.put("snVersion", sm.getSnVersion());
    	map.put("model", StringUtils.defaultString(sm.getModel()));
    	map.put("productionDateStr", StringUtils.defaultString(DateUtils.formatDate(sm.getProductionDate(), "yyyy-MM-dd")));
    	map.put("actualInstallDateStr", StringUtils.defaultString(DateUtils.formatDate(sm.getActualInstallDate(), "yyyy-MM-dd")));
    	map.put("warrantyDateFromStr", StringUtils.defaultString(DateUtils.formatDate(sm.getWarrantyDateFrom(), "yyyy-MM-dd")));
    	map.put("warrantyDateToStr", StringUtils.defaultString(DateUtils.formatDate(sm.getWarrantyDateTo(), "yyyy-MM-dd")));
    	return map;
    }
}