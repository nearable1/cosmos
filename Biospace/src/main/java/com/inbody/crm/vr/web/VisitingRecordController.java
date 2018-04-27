/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.service.AreaService;
import com.inbody.crm.modules.sys.service.SystemService;
import com.inbody.crm.modules.sys.utils.UserUtils;
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
@RequestMapping(value = "${adminPath}/vr/visitingRecord")
public class VisitingRecordController extends BaseController {

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private VrVisitDtlService vrVisitDtlService;
	
	@Autowired
	private VrVisitService vrVisitService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CommonService commonService;
	
	@RequiresPermissions("vr:visitingRecord:view")
	@RequestMapping(value = "list")
	public String list(VrVisit vrVisit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Act dbAct = vrVisit.getAct(); 
		User user = UserUtils.getUser();
		if(StringUtils.isEmpty(vrVisit.getId())&&!StringUtils.isEmpty(vrVisit.getAct().getProcInsId())){
			vrVisit = vrVisitService.getByProcInsId(vrVisit.getAct().getProcInsId());
			if(vrVisit == null){
				throw new ServiceException("该流程申请单据已被报告覆盖。");
			}
		}
		
		//画面工作流权限
		Map<String, Boolean> buttonlList  = commonService.getProcViewStatus(vrVisit.getAct(),vrVisit.getId(),true);
		 
		
		if(!StringUtils.isEmpty(vrVisit.getId())){
			vrVisit = vrVisitService.get(vrVisit.getId());
			VrVisitDtl vrVisitDtl = new VrVisitDtl();
			vrVisitDtl.setVisitNo(vrVisit.getVisitNo());
			List<VrVisitDtl> vrVisitDtlList = vrVisitDtlService.findAllListById(vrVisitDtl);
			dbAct.setComment(vrVisit.getNewRemarks());
			vrVisit.setAct(dbAct);
	        model.addAttribute("data", vrVisitDtlList);
		}
		
		//申请人
		if(StringUtils.isEmpty(vrVisit.getResponsiblePersonId())){
			vrVisit.setResponsiblePersonId(user.getId());
		}
		model.addAttribute("viewsts", buttonlList);
		model.addAttribute("vrVisit", vrVisit);
		return "inbody/vr/visitingRecord";
	}
	
	@RequiresPermissions("vr:visitingRecord:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(VrVisit vrVisit, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vrVisit)){
			model.addAttribute("viewsts", commonService.getProcViewStatus(vrVisit.getAct(),vrVisit.getId(),true));
			vrVisit = vrVisitService.get(vrVisit.getId());
			model.addAttribute("data", vrVisit);
			model.addAttribute("vrVisit", vrVisit);
			return "inbody/vr/visitingRecord";
		}
		// 画面操作所对应的工作流状态取得
        String workflowStatus = this.getWorkflowStatusByOpt(vrVisit.getOpt());
        try {
            // 工作流状态设定
        	vrVisit.setWorkflowStatus(workflowStatus);
            // 保存
        	vrVisitService.applySave(vrVisit);
        } catch (ServiceException e) {
            e.printStackTrace();
            // 保存失败，返回消息添加
            this.addMessage(model, e.getMessage());

            model.addAttribute("viewsts", commonService.getProcViewStatus(vrVisit.getAct(),vrVisit.getId(),true));
			vrVisit = vrVisitService.get(vrVisit.getId());
			if(!StringUtils.isEmpty(vrVisit.getId())){
				VrVisitDtl vrVisitDtl = new VrVisitDtl();
				vrVisitDtl.setVisitNo(vrVisit.getVisitNo());
				List<VrVisitDtl> vrVisitDtlList = vrVisitDtlService.findAllListById(vrVisitDtl);
		        model.addAttribute("data", vrVisitDtlList);
			}
			model.addAttribute("vrVisit", vrVisit);
			return "inbody/vr/visitingRecord";
        }
        
        // 临时保存以外的场合，操作完毕后画面跳转至我的任务画面，并显示相应的操作消息
        if (StringUtils.equals(workflowStatus,
                CommonConstants.WORKFLOW_STATUS_10)) {
            this.addMessage(redirectAttributes, "拜访申请提交成功！");
        } else if (StringUtils.equals(workflowStatus,
                CommonConstants.WORKFLOW_STATUS_20)) {
            this.addMessage(redirectAttributes, "拜访申请节点审批成功！");
        } else if (StringUtils.equals(workflowStatus,
                CommonConstants.WORKFLOW_STATUS_30)) {
            this.addMessage(redirectAttributes, "拜访申请退回成功！");
        } else if (StringUtils.equals(workflowStatus,
                CommonConstants.WORKFLOW_STATUS_40)) {
            this.addMessage(redirectAttributes, "拜访申请撤回成功！");
        }

        return "redirect:" + adminPath + "/act/task/todo";
        
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
