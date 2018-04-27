/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.web;

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
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.service.AreaService;
import com.inbody.crm.modules.sys.service.SystemService;
import com.inbody.crm.vr.entity.VrVisit;
import com.inbody.crm.vr.entity.VrVisitDtl;
import com.inbody.crm.vr.service.VrVisitDtlService;
import com.inbody.crm.vr.service.VrVisitService;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/vr/visitingReport")
public class VisitingReportController extends BaseController {

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired 
	private VrVisitDtlService vrVisitDtlService;
	
	@Autowired
	private VrVisitService vrVisitService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("vr:visitingReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(VrVisitDtl vrVisitDtl, HttpServletRequest request, HttpServletResponse response, Model model) {
		String procInsId = request.getParameter("procInsId");
		VrVisit vrVisit = new VrVisit();
		if(!StringUtils.isEmpty(procInsId)){
			vrVisit = vrVisitService.getByProcInsId(procInsId);
			vrVisitDtl.setProcInsId(procInsId);
			List<VrVisitDtl> vrVisitDtlList = vrVisitDtlService.findAllListById(vrVisitDtl);
			//vrVisit.setAct(act);
	        model.addAttribute("data", vrVisitDtlList);
		}
		model.addAttribute("vrVisit", vrVisit);
		return "inbody/vr/visitingReport";
	}
	
	
	/*@RequiresPermissions("vr:visitingReport:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(VrVisit vrVisit, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/vr/visitingRecord?repage";
		}
		String workFlowStatus = request.getParameter("workFlowStatus");
		String procInsId = request.getParameter("procInsId");
		if(StringUtils.isEmpty(procInsId)){
			String[] customerArr = request.getParameterValues("customer");
			String[] ifVisitArr = request.getParameterValues("ifVisit");
			String[] businessOppArr = request.getParameterValues("businessOpp");
			String[] addressArr = request.getParameterValues("address");
			String[] purposeArr = request.getParameterValues("purpose");
			String[] expDateFormArr = request.getParameterValues("expDateForm");
			String responsiblePersonId = request.getParameter("responsiblePerson");
			//拜访记录申请头表
			String visitNo = vrVisitService.getVisitNo();
			VrVisit vrVisitBean = new VrVisit();
			vrVisitBean.setVisitNo(visitNo);
			vrVisitBean.setWorkflowStatus("1");
			vrVisitBean.setResponsiblePersonId(responsiblePersonId);
			vrVisitService.save(vrVisitBean);
			
			vrVisitBean = vrVisitService.get(vrVisitBean.getId());
			//保存备份信息
			for(int i=0;i<customerArr.length;i++){
				VrVisitDtl bean = new VrVisitDtl();
				bean.setProcInsId(vrVisitBean.getProcInsId());
				bean.setCustomerName(customerArr[i]);
				bean.setIfVisit(ifVisitArr[i]);
				bean.setBusinessOppNo(businessOppArr[i]);
				bean.setPurpose(purposeArr[i]);
				bean.setAddress(addressArr[i]);
				bean.setExpDateFrom(expDateFormArr[i]);
				bean.setVisitNo(visitNo);
				bean.setLineNo(String.valueOf(i));
				//bean.set
				vrVisitDtlService.save(bean);
			}
		}else{
			VrVisit vrVisitBean = new VrVisit();
			vrVisitBean = vrVisitService.getByProcInsId(procInsId);
			vrVisitBean.setWorkflowStatus("2");
			vrVisitBean.setAct(vrVisit.getAct());
			vrVisitService.save(vrVisitBean);
			//return "redirect:" + adminPath + "/vr/visitingRecordManager?repage";
		}	
//		// 清除当前用户缓存
//		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
//			UserUtils.clearCache();
//			//UserUtils.getCacheMap().clear();
//		}
		addMessage(redirectAttributes, "保存成功");
		List<VrVisitDtl> vrVisitList = vrVisitDtlService.findList(null);
        model.addAttribute("data", vrVisitList);
		return "redirect:" + adminPath + "/vr/visitingRecord?repage";
	}*/
	

}
