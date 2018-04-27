/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.web;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.ee.entity.EeSaleEmp;
import com.inbody.crm.ee.service.EeSaleEmpService;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 销售员工评价Controller
 * @author 11
 * @version 2017-10-27
 */
@Controller
@RequestMapping(value = "${adminPath}/ee/eeSaleEmp")
public class EeSaleEmpController extends BaseController {

	@Autowired
	private EeSaleEmpService eeSaleEmpService;
	
	@Autowired
	private CommonService commonService;
	
	@ModelAttribute
	public EeSaleEmp get(@RequestParam(required=false) String id) {
		EeSaleEmp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = eeSaleEmpService.get(id);
		}
		if (entity == null){
			entity = new EeSaleEmp();
		}
		return entity;
	}
	
	@RequiresPermissions("ee:eeSaleEmp:view")
	@RequestMapping(value = {"list", ""})
	public String list(EeSaleEmp eeSaleEmp, HttpServletRequest request, HttpServletResponse response, Model model) {
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(eeSaleEmp.getAct(),eeSaleEmp.getId(),true);
		model.addAttribute("viewsts", buttonlList);
		User user = UserUtils.getUser();
		
		eeSaleEmp.setCreateBy(user);
		eeSaleEmp.setYear(DateUtils.getYear());
		List<EeSaleEmp> eSaleEmps = eeSaleEmpService.getByUserAndYear(eeSaleEmp);
		if(eSaleEmps.size()>0 ){
			eeSaleEmp = eeSaleEmpService.findListbyId(eSaleEmps.get(0));
		}
		model.addAttribute("eeSaleEmp", eeSaleEmp);
		
		return "inbody/ee/eeSaleEmpList";
	}

	@RequiresPermissions("ee:eeSaleEmp:view")
	@RequestMapping(value = "form")
	public String form(EeSaleEmp eeSaleEmp, Model model) {
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(eeSaleEmp.getAct(),eeSaleEmp.getId(),true);
		model.addAttribute("viewsts", buttonlList);
		User user = UserUtils.getUser();
		Act dbAct = eeSaleEmp.getAct();
		
		eeSaleEmp = eeSaleEmpService.findListbyId(eeSaleEmp);
		eeSaleEmp.setAct(dbAct);
		model.addAttribute("eeSaleEmp", eeSaleEmp);
		if(StringUtils.equals(eeSaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_10)|| 
		   StringUtils.equals(eeSaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_30)|| 
		   StringUtils.equals(eeSaleEmp.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_40)||
		   StringUtils.equals(eeSaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_60)){
		   if(StringUtils.equals(eeSaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_10)&&
				   !StringUtils.equals(eeSaleEmp.getCreateBy().getId(),user.getId().toString()) ){
			   return "inbody/ee/eeSaleEmpApprove";
		   }
		   return "inbody/ee/eeSaleEmpList";
		}/*else if(StringUtils.equals(eeSaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_20)&&
				   StringUtils.equals(eeSaleEmp.getCreateBy().getId(),user.getId().toString()) ){
			return "inbody/ee/eeSaleEmpList";
		}*/else{
			return "inbody/ee/eeSaleEmpApprove";	
		}
		
	}
	
	@RequiresPermissions("ee:eeSaleEmp:view")
	@RequestMapping(value = "searchInfo")
	public String searchInfo(EeSaleEmp eeSaleEmp, Model model) {
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(eeSaleEmp.getAct(),eeSaleEmp.getId(),true);
		model.addAttribute("viewsts", buttonlList);
		User user = UserUtils.getUser();
		eeSaleEmp.setCreateBy(user);
		Act dbAct = eeSaleEmp.getAct();
		
		List<EeSaleEmp> eSaleEmps = eeSaleEmpService.getByUserAndYear(eeSaleEmp);
		if(eSaleEmps.size()>0 ){
			eeSaleEmp = eeSaleEmpService.findListbyId(eSaleEmps.get(0));
			eeSaleEmp.setAct(dbAct);
		}
		model.addAttribute("eeSaleEmp", eeSaleEmp);
		if(StringUtils.equals(DateUtils.getYear(), eeSaleEmp.getYear())){
			return "inbody/ee/eeSaleEmpList";
		}else{
			if(eSaleEmps.size()==0){
				List<String> list = new ArrayList<String>();
				list.add(0, "未获取到所选年度的评价信息。");
				addMessage(model, list.toArray(new String[]{}));
				return "inbody/ee/eeSaleEmpList";
			}
			
			return "inbody/ee/eeSaleEmpHistory";	
		}
		
	}

	@RequiresPermissions("ee:eeSaleEmp:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(EeSaleEmp eeSaleEmp, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, eeSaleEmp)){
			model.addAttribute("viewsts", commonService.getProcViewStatus(eeSaleEmp.getAct(),eeSaleEmp.getId(),true));
			eeSaleEmpService.findListbyId(eeSaleEmp);
			model.addAttribute("eeSaleEmp", eeSaleEmp);
			return "inbody/ee/eeSaleEmpList";
		}
		User user = UserUtils.getUser();
		eeSaleEmp.setCreateBy(user);
		eeSaleEmp.setOrganize(user.getOffice());
		// 画面操作所对应的工作流状态取得
		String workflowStatus = this.getWorkflowStatusByOpt(eeSaleEmp.getOpt());
		
        try {
            // 工作流状态设定
        	eeSaleEmp.setWorkflowStatus(workflowStatus);
        	/*//验证本年度是否已提交
    		List<EeSaleEmp> eSaleEmps = eeSaleEmpService.getByUserAndYear(eeSaleEmp);
    		if(eSaleEmps.size()>0 ){
    			List<String> list = new ArrayList<String>();
    			list.add(0, "本年度员工评价已提交。");
    			addMessage(model, list.toArray(new String[]{}));
    			
    			model.addAttribute("viewsts", commonService.getProcViewStatus(eeSaleEmp.getAct(),eeSaleEmp.getId(),true));
    			eeSaleEmp = eeSaleEmpService.findListbyId(eeSaleEmp);
    			model.addAttribute("eeSaleEmp", eeSaleEmp);
    			return "inbody/ee/eeSaleEmpList";
    		}*/
            // 保存
        	eeSaleEmpService.acSave(eeSaleEmp);
        } catch (ServiceException e) {
            e.printStackTrace();
            // 保存失败，返回消息添加
            this.addMessage(model, e.getMessage());

            eeSaleEmp = eeSaleEmpService.findListbyId(eeSaleEmp);
    		model.addAttribute("eeSaleEmp", eeSaleEmp);
            model.addAttribute("viewsts", commonService.getProcViewStatus(
            		eeSaleEmp.getAct(),
            		eeSaleEmp.getId(), true));
            model.addAttribute("eeSaleEmp", eeSaleEmp);

            return "inbody/ee/eeSaleEmpList";
        }
        
        // 保存成功
        // 临时保存的场合，需要停留在本画面。返回画面提交的数据，更新当前任务
        if (StringUtils.equals(workflowStatus,
                CommonConstants.WORKFLOW_STATUS_60)) {
            // 保存成功消息
            this.addMessage(model, "员工评价保存成功！");

            return "inbody/ee/eeSaleEmpList";
        } else {
            // 临时保存以外的场合，操作完毕后画面跳转至我的任务画面，并显示相应的操作消息
            if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_10)) {
                this.addMessage(redirectAttributes, "员工评价申请提交成功！");
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_20)) {
                this.addMessage(redirectAttributes, "员工评价节点审批成功！");
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_30)) {
                this.addMessage(redirectAttributes, "员工评价申请退回成功！");
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_40)) {
                this.addMessage(redirectAttributes, "员工评价申请撤回成功！");
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_70)) {
                this.addMessage(redirectAttributes, "员工评价申请删除成功！");
            }

            return "redirect:" + adminPath + "/act/task/todo";
        }
	}
	
	@RequiresPermissions("ee:eeSaleEmp:edit")
	@RequestMapping(value = "delete")
	public String delete(EeSaleEmp eeSaleEmp, RedirectAttributes redirectAttributes) {
		eeSaleEmpService.delete(eeSaleEmp);
		addMessage(redirectAttributes, "删除销售员工评价成功");
		return "redirect:"+Global.getAdminPath()+"/ee/eeSaleEmp/?repage";
	}

	 /**
     * 根据操作按钮取得工作流状态
     * 
     * @param opt
     *            操作按钮
     * @return 工作流状态
     */
    private String getWorkflowStatusByOpt(String opt) {
        if (StringUtils.equals(opt, "提交申请") || StringUtils.equals(opt, "再申请")) {
            return CommonConstants.WORKFLOW_STATUS_10;
        } else if (StringUtils.equals(opt, "临时保存")) {
            return CommonConstants.WORKFLOW_STATUS_60;
        } else if (StringUtils.equals(opt, "同意")) {
            return CommonConstants.WORKFLOW_STATUS_20;
        } else if (StringUtils.equals(opt, "退回")) {
            return CommonConstants.WORKFLOW_STATUS_30;
        } else if (StringUtils.equals(opt, "撤回申请")) {
            return CommonConstants.WORKFLOW_STATUS_40;
        } else if (StringUtils.equals(opt, "删除")
                || StringUtils.equals(opt, "撤消")) {
            return CommonConstants.WORKFLOW_STATUS_70;
        } else {
            return "";
        }
    }
}