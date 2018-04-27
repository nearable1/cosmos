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
import com.inbody.crm.ee.entity.EeNonsaleEmp;
import com.inbody.crm.ee.service.EeNonsaleEmpService;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 非销售员工评价Controller
 * @author 11
 * @version 2017-10-27
 */
@Controller
@RequestMapping(value = "${adminPath}/ee/eeNonsaleEmp")
public class EeNonsaleEmpController extends BaseController {

	@Autowired
	private EeNonsaleEmpService eeNonsaleEmpService;
	
	@Autowired
	private CommonService commonService;
	
	@ModelAttribute
	public EeNonsaleEmp get(@RequestParam(required=false) String id) {
		EeNonsaleEmp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = eeNonsaleEmpService.get(id);
		}
		if (entity == null){
			entity = new EeNonsaleEmp();
		}
		return entity;
	}
	
	@RequiresPermissions("ee:eeNonsaleEmp:view")
	@RequestMapping(value = {"list", ""})
	public String list(EeNonsaleEmp eeNonsaleEmp, HttpServletRequest request, HttpServletResponse response, Model model) {
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(eeNonsaleEmp.getAct(),eeNonsaleEmp.getId(),true);
		model.addAttribute("viewsts", buttonlList);
		User user = UserUtils.getUser();
		
		eeNonsaleEmp.setCreateBy(user);
		eeNonsaleEmp.setYear(DateUtils.getYear());
		List<EeNonsaleEmp> eNonSaleEmps = eeNonsaleEmpService.getByUserAndYear(eeNonsaleEmp);
		if(eNonSaleEmps.size()>0 ){
			eeNonsaleEmp = eeNonsaleEmpService.findListbyId(eNonSaleEmps.get(0));
		}
		model.addAttribute("eeNonsaleEmp", eeNonsaleEmp);
		
		return "inbody/ee/eeNonsaleEmpList";
	}

	@RequiresPermissions("ee:eeNonsaleEmp:view")
	@RequestMapping(value = "form")
	public String form(EeNonsaleEmp eeNonsaleEmp, Model model) {
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(eeNonsaleEmp.getAct(),eeNonsaleEmp.getId(),true);
		model.addAttribute("viewsts", buttonlList);
		User user = UserUtils.getUser();
		Act dbAct = eeNonsaleEmp.getAct();
		
		eeNonsaleEmp = eeNonsaleEmpService.findListbyId(eeNonsaleEmp);
		eeNonsaleEmp.setAct(dbAct);
		model.addAttribute("eeNonsaleEmp", eeNonsaleEmp);
		if(StringUtils.equals(eeNonsaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_10)|| 
		   StringUtils.equals(eeNonsaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_30)|| 
		   StringUtils.equals(eeNonsaleEmp.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_40)||
		   StringUtils.equals(eeNonsaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_60)){
		   if(StringUtils.equals(eeNonsaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_10)&&
				   !StringUtils.equals(eeNonsaleEmp.getCreateBy().getId(),user.getId().toString()) ){
			   return "inbody/ee/eeNonsaleEmpApprove";
		   }
		   return "inbody/ee/eeNonsaleEmpList";
		}else{
			return "inbody/ee/eeNonsaleEmpApprove";	
		}
	}
	
	@RequiresPermissions("ee:eeNonsaleEmp:view")
	@RequestMapping(value = "searchInfo")
	public String searchInfo(EeNonsaleEmp eeNonsaleEmp, Model model) {
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(eeNonsaleEmp.getAct(),eeNonsaleEmp.getId(),true);
		model.addAttribute("viewsts", buttonlList);
		User user = UserUtils.getUser();
		eeNonsaleEmp.setCreateBy(user);
		Act dbAct = eeNonsaleEmp.getAct();
		
		List<EeNonsaleEmp> eNonSaleEmps = eeNonsaleEmpService.getByUserAndYear(eeNonsaleEmp);
		if(eNonSaleEmps.size()>0 ){
			eeNonsaleEmp = eeNonsaleEmpService.findListbyId(eNonSaleEmps.get(0));
			eeNonsaleEmp.setAct(dbAct);
		}
		model.addAttribute("eeNonsaleEmp", eeNonsaleEmp);
		if(StringUtils.equals(DateUtils.getYear(), eeNonsaleEmp.getYear())){
			return "inbody/ee/eeNonsaleEmpList";
		}else{
			if(eNonSaleEmps.size()==0){
				List<String> list = new ArrayList<String>();
				list.add(0, "未获取到所选年度的评价信息。");
				addMessage(model, list.toArray(new String[]{}));
			}
			
			return "inbody/ee/eeNonsaleEmpHistory";	
		}
		
	}

	@RequiresPermissions("ee:eeNonsaleEmp:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(EeNonsaleEmp eeNonsaleEmp, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, eeNonsaleEmp)){
			model.addAttribute("viewsts", commonService.getProcViewStatus(eeNonsaleEmp.getAct(),eeNonsaleEmp.getId(),true));
			eeNonsaleEmpService.findListbyId(eeNonsaleEmp);
			model.addAttribute("eeNonsaleEmp", eeNonsaleEmp);
			return "inbody/ee/eeNonsaleEmpList";
		}
		User user = UserUtils.getUser();
		eeNonsaleEmp.setCreateBy(user);
		eeNonsaleEmp.setOrganize(user.getOffice());
		// 画面操作所对应的工作流状态取得
		String workflowStatus = this.getWorkflowStatusByOpt(eeNonsaleEmp.getOpt());
		
        try {
            // 工作流状态设定
        	eeNonsaleEmp.setWorkflowStatus(workflowStatus);
            // 保存
        	eeNonsaleEmpService.acSave(eeNonsaleEmp);
        } catch (ServiceException e) {
            e.printStackTrace();
            // 保存失败，返回消息添加
            this.addMessage(model, e.getMessage());

            eeNonsaleEmp = eeNonsaleEmpService.findListbyId(eeNonsaleEmp);
            model.addAttribute("viewsts", commonService.getProcViewStatus(
            		eeNonsaleEmp.getAct(),
            		eeNonsaleEmp.getId(), true));
            model.addAttribute("eeNonsaleEmp", eeNonsaleEmp);

            return "inbody/ee/eeNonsaleEmpList";
        }
        
        // 保存成功
        // 临时保存的场合，需要停留在本画面。返回画面提交的数据，更新当前任务
        if (StringUtils.equals(workflowStatus,
                CommonConstants.WORKFLOW_STATUS_60)) {
            // 保存成功消息
            this.addMessage(model, "员工评价保存成功！");

            return "inbody/ee/eeNonsaleEmpList";
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
	
	@RequiresPermissions("ee:eeNonsaleEmp:edit")
	@RequestMapping(value = "delete")
	public String delete(EeNonsaleEmp eeNonsaleEmp, RedirectAttributes redirectAttributes) {
		eeNonsaleEmpService.delete(eeNonsaleEmp);
		addMessage(redirectAttributes, "删除非销售员工评价成功");
		return "redirect:"+Global.getAdminPath()+"/ee/eeNonsaleEmp/?repage";
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