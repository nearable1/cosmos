/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.web;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drew.lang.StringUtil;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.service.AreaService;
import com.inbody.crm.modules.sys.service.SystemService;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.vr.dao.VrVisitDao;
import com.inbody.crm.vr.entity.VrVisit;
import com.inbody.crm.vr.entity.VrVisitDtl;
import com.inbody.crm.vr.entity.VrVisitDtlExcel;
import com.inbody.crm.vr.service.VrVisitDtlService;
import com.inbody.crm.vr.service.VrVisitService;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/vr/visitingRecordManager")
public class VisitingRecordManagerController extends BaseController {

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private VrVisitDtlService vrVisitDtlService;
	
	@Autowired
	private VrVisitService vrVisitService;
	
	@Autowired
	private VrVisitDao vrVisitDao;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CommonService commonService;

	@RequiresPermissions("vr:visitingRecordManager:view")
	@RequestMapping(value = {"list", ""})
	public String list(VrVisitDtl vrVisitDtl, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		Map<String, String>  uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		vrVisitDtl.setSqlMap(uerMap);
		
		//List<VrVisitDtl> vrVisitList = vrVisitDtlService.findAllParentList(vrVisitDtl);
		Page<VrVisitDtl> page = vrVisitDtlService.findPage(new Page<VrVisitDtl>(request, response), vrVisitDtl); 
		model.addAttribute("page", page);
		
		Act act = vrVisitDtl.getAct(); 
		act.setProcDefKey(ActUtils.VISITING_AUDIT[0]);	
		List<Act> list = actTaskService.todoList(act);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, list);
		}
		model.addAttribute("user", user);
		return "inbody/vr/visitingRecordManager";// list数据取得
	}
	
	@RequestMapping(value = "form")
	public String form(VrVisitDtl vrVisitDtl, Model model) {
		model.addAttribute("vrVisitDtl", vrVisitDtl);
		return "inbody/vr/visitingRecordManager";
	}
	
	@RequiresPermissions("vr:visitingRecordManager:view")
	@RequestMapping(value = "reportForm")
	public String reportForm(VrVisit vrVisit,HttpServletRequest request, Model model) {
		
		String opFlag = request.getParameter("opFlag");
		//画面工作流权限
		Map<String, Boolean> buttonlList = null;
		if(StringUtils.isEmpty(opFlag)){
			buttonlList  = commonService.getProcViewStatus(vrVisit.getAct(),vrVisit.getId(),false);
		}else{
			buttonlList  = commonService.getProcViewStatus(null,null,false);
		}
		
		Act act = vrVisit.getAct(); 
		if(!StringUtils.isEmpty(vrVisit.getAct().getProcInsId())){
			vrVisit = vrVisitService.getByProcInsId(vrVisit.getAct().getProcInsId());
		}
		
		VrVisitDtl vrVisitDtl = new VrVisitDtl();
		
		if(!StringUtils.isEmpty(vrVisit.getId())){
			vrVisit = vrVisitService.get(vrVisit.getId());
			vrVisitDtl.setVisitNo(vrVisit.getVisitNo());
			List<VrVisitDtl> vrVisitDtlList = vrVisitDtlService.findAllListById(vrVisitDtl);
			act.setComment(vrVisit.getNewRemarks());
			act.setProcInsId(vrVisit.getProcInsId());
			vrVisit.setAct(act);
			vrVisit.setVrVisitDtlList(vrVisitDtlList);
	        model.addAttribute("data", vrVisitDtlList);
	        
		}
		
		model.addAttribute("viewsts", buttonlList);
		
		model.addAttribute("vrVisit", vrVisit);
		return "inbody/vr/visitingReport";
	}
	
	@RequiresPermissions("vr:visitingRecordManager:edit")
	@RequestMapping(value = "saveInfo")
	@Transactional
	public String saveInfo(VrVisit vrVisit, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws IllegalAccessException, InvocationTargetException {
		if (!beanValidator(model, vrVisit)){
			model.addAttribute("viewsts", commonService.getProcViewStatus(vrVisit.getAct(),vrVisit.getId(),true));
			if(!StringUtils.isEmpty(vrVisit.getId())){
				VrVisitDtl conditionDtl = new VrVisitDtl();
				conditionDtl.setVisitNo(vrVisit.getVisitNo());
				List<VrVisitDtl> vrVisitDtlList = vrVisitDtlService.findAllListById(conditionDtl);
				vrVisit.setVrVisitDtlList(vrVisitDtlList);
				model.addAttribute("vrVisit", vrVisit);
				model.addAttribute("data", vrVisitDtlList);
			}
			return "inbody/vr/visitingReport";
		}
		String workflowStatus2 = this.getWorkflowStatusByOpt(vrVisit.getOpt());
		
		try {
            // 工作流状态设定
			if(!StringUtils.isEmpty(workflowStatus2)){
				vrVisit.setWorkflowStatus2(workflowStatus2);
			}
            // 保存
			VrVisit dbVrVisit = vrVisitService.get(vrVisit.getId());
			if(StringUtils.isEmpty(dbVrVisit.getWorkflowStatus2())){
				vrVisit.setProcInsId(null);
				vrVisit.getAct().setProcInsId(null);
			}
        	vrVisitService.reportSave(vrVisit);
        	
        	//手动更新workflowStatus
        	VrVisit vrVisitBean = vrVisitService.get(vrVisit.getId());
        	vrVisitBean.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_50);
        	if(!StringUtils.equals(vrVisitBean.getWorkflowStatus2(),
					CommonConstants.WORKFLOW_STATUS_50)){
        		vrVisitBean.setWorkflowStatus2(workflowStatus2);
        	}
        	vrVisitBean.preUpdate();
        	vrVisitDao.update(vrVisitBean);
        	
        } catch (ServiceException e) {
            e.printStackTrace();
            // 保存失败，返回消息添加
            this.addMessage(model, e.getMessage());

            model.addAttribute("viewsts", commonService.getProcViewStatus(vrVisit.getAct(),vrVisit.getId(),true));
			if(!StringUtils.isEmpty(vrVisit.getId())){
				VrVisitDtl conditionDtl = new VrVisitDtl();
				conditionDtl.setVisitNo(vrVisit.getVisitNo());
				List<VrVisitDtl> vrVisitDtlList = vrVisitDtlService.findAllListById(conditionDtl);
				vrVisit.setVrVisitDtlList(vrVisitDtlList);
				model.addAttribute("vrVisit", vrVisit);
				model.addAttribute("data", vrVisitDtlList);
			}
			return "inbody/vr/visitingReport";
        }
        
        // 保存成功
        if (StringUtils.equals(workflowStatus2,
                CommonConstants.WORKFLOW_STATUS_10)) {
            this.addMessage(redirectAttributes, "拜访报告申请提交成功！");
        } else if (StringUtils.equals(workflowStatus2,
                CommonConstants.WORKFLOW_STATUS_20)) {
            this.addMessage(redirectAttributes, "拜访报告节点审批成功！");
        } else if (StringUtils.equals(workflowStatus2,
                CommonConstants.WORKFLOW_STATUS_30)) {
            this.addMessage(redirectAttributes, "拜访报告申请退回成功！");
        } else if (StringUtils.equals(workflowStatus2,
                CommonConstants.WORKFLOW_STATUS_40)) {
            this.addMessage(redirectAttributes, "拜访报告申请撤回成功！");
        } else if(StringUtils.isEmpty(workflowStatus2)){
        	model.addAttribute("viewsts", commonService.getProcViewStatus(vrVisit.getAct(),vrVisit.getId(),true));
			if(!StringUtils.isEmpty(vrVisit.getId())){
				VrVisitDtl conditionDtl = new VrVisitDtl();
				conditionDtl.setVisitNo(vrVisit.getVisitNo());
				List<VrVisitDtl> vrVisitDtlList = vrVisitDtlService.findAllListById(conditionDtl);
				vrVisit.setVrVisitDtlList(vrVisitDtlList);
				model.addAttribute("vrVisit", vrVisit);
				model.addAttribute("data", vrVisitDtlList);
			}
			return "inbody/vr/visitingReport";
        }
		
		return "redirect:" + adminPath + "/act/task/todo";
	}
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("vr:visitingRecordManager:view")
    @RequestMapping(value = "export", method=RequestMethod.GET)
    public String exportFile(VrVisitDtl vrVisitDtl, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "拜访管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            User user = UserUtils.getUser();
    		Map<String, String>  uerMap = new HashMap<String, String>();
    		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
    		vrVisitDtl.setSqlMap(uerMap);
            List<VrVisitDtl> vrVisitList = vrVisitDtlService.findList(vrVisitDtl);
    		new ExportExcel("拜访管理", VrVisitDtlExcel.class).setDataList(vrVisitList).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出拜访信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/vr/visitingRecordManager?repage";
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
