/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sd.entity.BoBusinessOpp;
import com.inbody.crm.sd.entity.BoBusinessOppDtl;
import com.inbody.crm.sd.entity.TaTenderAuthor;
import com.inbody.crm.sd.entity.TaTenderAuthorDtl;
import com.inbody.crm.sd.service.BoBusinessOppService;
import com.inbody.crm.sd.service.TaTenderAuthorService;

/**
 * 招标授权Controller
 * @author qidd
 * @version 2017-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sd/taTenderAuthor")
public class TaTenderAuthorController extends BaseController {

	@Autowired
	private TaTenderAuthorService taTenderAuthorService;
	@Autowired
	private BoBusinessOppService boBusinessOppService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AttachmentsService attachmentsService;
	
	@RequiresPermissions("sd:taTenderAuthor:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaTenderAuthor taTenderAuthor, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		taTenderAuthor.setSqlMap(uerMap);
		
		Page<TaTenderAuthor> page = taTenderAuthorService.findPage(new Page<TaTenderAuthor>(request, response), taTenderAuthor); 
		model.addAttribute("page", page);
		return "sd/ta/ta002";
	}

	/**
	 * 导出招标授权数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sd:taTenderAuthor:view")
    @RequestMapping(value = "export")
    public String exportFile(TaTenderAuthor taTenderAuthor, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			Map<String, String> uerMap = new HashMap<String, String>();
			uerMap.put("dataScope", BaseService.dataScopeFilter(user));
			taTenderAuthor.setSqlMap(uerMap);
			
            String fileName = "招标授权一览"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<TaTenderAuthor> page = taTenderAuthorService.findList(taTenderAuthor);

    		for(TaTenderAuthor tenderAuthor :page){
    			tenderAuthor.setBidOpeningDateStr(DateUtils.formatDate(tenderAuthor.getBidOpeningDate(), "yyyy-MM-dd"));
    			tenderAuthor.setValidityDateFromStr(DateUtils.formatDate(tenderAuthor.getValidityDateFrom(), "yyyy-MM-dd"));
    			tenderAuthor.setValidityDateToStr(DateUtils.formatDate(tenderAuthor.getValidityDateTo(), "yyyy-MM-dd"));
    		}
    		
    		new ExportExcel("招标授权一览", TaTenderAuthor.class).setDataList(page).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出招标授权一览失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/taTenderAuthor/list?repage";
    }

	@RequiresPermissions("sd:taTenderAuthor:view")
	@RequestMapping(value = "form")
	public String form(TaTenderAuthor taTenderAuthor, Model model) {
		TaTenderAuthor entity;
		if (StringUtils.isNotBlank(taTenderAuthor.getId())){
			entity = taTenderAuthorService.get(taTenderAuthor.getId());
		}else{
			entity = new TaTenderAuthor(); 
		}
		
		// 设置从商机中带出的信息
		if (StringUtils.isNotBlank(taTenderAuthor.getBusinessOppId())){
			BoBusinessOpp boBusinessOpp =boBusinessOppService.get(taTenderAuthor.getBusinessOppId());
			entity.setEndCustomerId(boBusinessOpp.getEndCustomerId());
			entity.setEndCustomerName(boBusinessOpp.getEndCustomerName());
			entity.setIndustry(boBusinessOpp.getIndustry());
			entity.setCustomerSegmentation(boBusinessOpp.getCustomerSegmentation());
			entity.setAgentId(boBusinessOpp.getAgentId());
			entity.setAgentName(boBusinessOpp.getAgentName());
			entity.setBusinessOppId(taTenderAuthor.getBusinessOppId());
			
			List<TaTenderAuthorDtl> taTenderAuthorDtlList = Lists.newArrayList();
			int dtlIndex = 0;
			for(BoBusinessOppDtl boBusinessOppDtl :boBusinessOpp.getBoBusinessOppDtlList()){
				TaTenderAuthorDtl taTenderAuthorDtl = new TaTenderAuthorDtl();
				dtlIndex++;
				taTenderAuthorDtl.setLineNo(String.valueOf(dtlIndex));
				taTenderAuthorDtl.setMaterialNo(boBusinessOppDtl.getMaterialNo());
				taTenderAuthorDtl.setModel(boBusinessOppDtl.getModel());
				taTenderAuthorDtlList.add(taTenderAuthorDtl);
			}
			entity.setTaTenderAuthorDtlList(taTenderAuthorDtlList);

			User user = UserUtils.getUser();
			entity.setApplyName(user.getName());
		}else{
			User user = UserUtils.get(entity.getCreateBy().getId());
			entity.setApplyName(user.getName());
		}

        // 工作流信息返回到画面
		entity.setAct(taTenderAuthor.getAct());
		Map<String, Boolean> viewsts = commonService.getProcViewStatus(taTenderAuthor
                .getAct(), taTenderAuthor.getId(), true);
		if(viewsts.get("isApprovalCompleted")){
			model.addAttribute("viewsts", viewsts);// 画面可操状态
		}
        // 画面可操状态
        model.addAttribute("viewsts", viewsts);// 画面可操状态
		model.addAttribute("taTenderAuthor", entity);
		return "sd/ta/ta001";
	}

	@RequiresPermissions("sd:taTenderAuthor:edit")
//	@ResponseBody
	@RequestMapping(value = "checkFormData")
//	public Map<String,String> checkFormData(TaTenderAuthor taTenderAuthor) {
	public @ResponseBody Map<String, Object> checkFormData(TaTenderAuthor taTenderAuthor, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

//		Map<String,String> messageList = Maps.newHashMap();
		Boolean hasError = false;
		List<String> list =Lists.newArrayList();

		TaTenderAuthor entity = null;
		if (StringUtils.isNotBlank(taTenderAuthor.getId())){
			entity = taTenderAuthorService.get(taTenderAuthor.getId());
			if(entity.getUpdateDate().compareTo(taTenderAuthor.getUpdateDate())!=0){
//				list.add("该招标授权信息已经被其他人更新了。");
//				return messageList;
				jsonMap.put("success", false);
				jsonMap.put("message", "该招标授权信息已经被其他人更新了。");
				
				return jsonMap;
			}
		}
		// 画面操作所对应的工作流状态取得
        String workflowStatus = this.getWorkflowStatusByOpt(taTenderAuthor.getOpt());
        // 临时保存、申请、再申请、或是 审批同意时执行订单信息保存
        if (StringUtils.isBlank(taTenderAuthor.getAct().getProcInsId())
                || StringUtils.equals(workflowStatus,
                        CommonConstants.WORKFLOW_STATUS_10)
//                || StringUtils.equals(workflowStatus,
//                        CommonConstants.WORKFLOW_STATUS_20)
                || StringUtils.equals(workflowStatus,
                        CommonConstants.WORKFLOW_STATUS_60)) {
        	if(DateUtils.compareDate(taTenderAuthor.getValidityDateFrom(), taTenderAuthor.getValidityDateTo()) >0){
    			list.add("授权有效期间的开始日期不能在结束日期之后。");
    		}
        	if (StringUtils.isEmptyNull(taTenderAuthor.getId())){

            	if (taTenderAuthor.getFiles() == null || taTenderAuthor.getFiles().length == 0) {
            		list.add("需要上传承诺书附件。");
            	}
        	} else {
        		if (taTenderAuthor.getFiles() == null || taTenderAuthor.getFiles().length == 0) {

            		List<CoAttachments> attachmentsList = attachmentsService.getAttachmentList(taTenderAuthor.getId());
                	if (attachmentsList == null || attachmentsList.size() == 0) {
                		list.add("需要上传承诺书附件。");
                	}
        		}
        	}
    		List<TaTenderAuthorDtl> detailList = Lists.newArrayList();		// 子表列表
    		if(taTenderAuthor.getTaTenderAuthorDtlList().size() == 0){ 
    			list.add("授权对象信息的授权型号至少需要录入一条。");
    		}else{
    			for(TaTenderAuthorDtl testingDetail :taTenderAuthor.getTaTenderAuthorDtlList()){
    				if(!"1".equals(testingDetail.getDelFlag())){
    					detailList.add(testingDetail);
    					if(StringUtils.isEmptyNull(testingDetail.getMaterialNo())){
    						//如果物料号没有录入，认为是无效行。
    						list.add("第"+testingDetail.getLineNo()+"行，物料号必须输入。");
    					}
    				}
    			}
    			
    			if(detailList.size() == 0){ 
    				list.add("授权对象信息的授权型号至少需要录入一条。");
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
//    			messageList.put("error", "true");
//    			messageList.put("errorMessage", sb.toString());
    			jsonMap.put("success", false);
    			jsonMap.put("message", sb.toString());
    			
    			return jsonMap;
    		}else{

    			TaTenderAuthor taTenderAuthorSearch = new TaTenderAuthor();
    			taTenderAuthorSearch.setEndCustomerIdSearch(taTenderAuthor.getEndCustomerId());
    			taTenderAuthorSearch.setValidityDateFromSearch(taTenderAuthor.getValidityDateFrom());
    			taTenderAuthorSearch.setValidityDateToSearch(taTenderAuthor.getValidityDateTo());
    			List<TaTenderAuthor> tenderAuthorList = taTenderAuthorService.findList(taTenderAuthorSearch);
    			boolean isDiff = false;
    			for(TaTenderAuthor tempEntity:tenderAuthorList){
    				if(!taTenderAuthor.getAgentId().equals(tempEntity.getAgentId())){
    					isDiff = true;
    				}
    			}
    			if(isDiff){
//    				messageList.put("warning", "true");
//    				messageList.put("warningMessage", "请注意，最终客户"+taTenderAuthor.getEndCustomerName()
//    						+"在期间("+DateUtils.formatDate(taTenderAuthor.getValidityDateFrom(), "yyyy-MM-dd")+"~"
//    						+DateUtils.formatDate(taTenderAuthor.getValidityDateTo(), "yyyy-MM-dd")+")内，<br/>其他经销商也存在授权，请确认是否进行本操作！");
        			jsonMap.put("success", true);
        			jsonMap.put("warning", "请注意，最终客户"+taTenderAuthor.getEndCustomerName()
    						+"在期间("+DateUtils.formatDate(taTenderAuthor.getValidityDateFrom(), "yyyy-MM-dd")+"~"
    						+DateUtils.formatDate(taTenderAuthor.getValidityDateTo(), "yyyy-MM-dd")+")内，<br/>其他经销商也存在授权，请确认是否进行本操作！");
        			
        			return jsonMap;
    			}
    		}
        }

		jsonMap.put("success", true);
		jsonMap.put("message", null);

		return jsonMap;
	}

	@RequiresPermissions("sd:taTenderAuthor:edit")
	@RequestMapping(value = "save")
	public @ResponseBody Map<String, Object> save(TaTenderAuthor taTenderAuthor, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
//	public String save(TaTenderAuthor taTenderAuthor, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, taTenderAuthor)){
//			return form(taTenderAuthor, model);
//		}
		
		// 画面输入值保存
//    	TaTenderAuthor tenderAuthorClone = SerializationUtils.clone(taTenderAuthor);
        // 画面操作所对应的工作流状态取得
        String workflowStatus = this.getWorkflowStatusByOpt(taTenderAuthor.getOpt());
        
        try {
        	// 工作流状态设定
            taTenderAuthor.setWorkflowStatus(workflowStatus);
            
            if (StringUtils.equals(workflowStatus, CommonConstants.WORKFLOW_STATUS_60)) {
            	
            	taTenderAuthorService.managerSave(taTenderAuthor);
            } else {

        		taTenderAuthorService.save(taTenderAuthor);
            }
        } catch (ServiceException e) {

    		jsonMap.put("success", false);
    		jsonMap.put("message", e.getMessage());

    		return jsonMap;
//            e.printStackTrace();
//            // 保存失败，返回消息添加
//            this.addMessage(model, e.getMessage());
//
//            // 获取流程实例对象
//            Act act = tenderAuthorClone.getAct();
//            // 工作流实例存在，且当前操作为再申请、或临时保存、或删除时
//            if (act.getProcInsId() != null
//                    && !(StringUtils.equals(workflowStatus,
//                            CommonConstants.WORKFLOW_STATUS_10)
//                            || StringUtils.equals(workflowStatus,
//                                    CommonConstants.WORKFLOW_STATUS_60) || StringUtils
//                                .equals(workflowStatus,
//                                        CommonConstants.WORKFLOW_STATUS_70))) {
//                // 画面数据取得
//            	TaTenderAuthor taTenderAuthorData = taTenderAuthorService.get(taTenderAuthor.getId());
//            	taTenderAuthorData.setAct(act);
//
//                tenderAuthorClone = taTenderAuthorData;
//            }
//
//            model.addAttribute("viewsts", commonService.getProcViewStatus(
//                    tenderAuthorClone.getAct(),
//                    tenderAuthorClone.getId(), true));
//            model.addAttribute("taTenderAuthor", tenderAuthorClone);
//            return "sd/ta/ta001";
        }

        // 保存成功
        // 临时保存的场合，需要停留在本画面。返回画面提交的数据，更新当前任务
//        if (StringUtils.equals(taTenderAuthor.getWorkflowStatus(),
//                CommonConstants.WORKFLOW_STATUS_60)) {
//            // 保存成功消息
//    		addMessage(redirectAttributes, "保存招标授权成功");
//            // 画面最新数据重新取得
//            TaTenderAuthor taTenderAuthorData = taTenderAuthorService.get(taTenderAuthor.getId());
//            // 工作流实例id
//            taTenderAuthorData.getAct().setProcInsId(taTenderAuthorData.getProcInsId());
//            // 当前任务id
//            taTenderAuthorData.getAct().setTaskId(
//                    taskService.createTaskQuery()
//                            .processInstanceId(taTenderAuthorData.getProcInsId())
//                            .singleResult().getId());
//
//            // 画面当前状态取得
//            model.addAttribute("viewsts", commonService.getProcViewStatus(
//            		taTenderAuthorData.getAct(), taTenderAuthorData.getId(), true));
//            model.addAttribute("taTenderAuthor", taTenderAuthorData);
//
//            return "sd/ta/ta001";
//        } else {
        // 临时保存以外的场合，操作完毕后画面跳转至我的任务画面，并显示相应的操作消息
//        if (StringUtils.equals(taTenderAuthor.getWorkflowStatus(),
//                CommonConstants.WORKFLOW_STATUS_10)) {
//            this.addMessage(redirectAttributes, "招标授权申请提交成功！");
//        } else if (StringUtils.equals(taTenderAuthor.getWorkflowStatus(),
//                CommonConstants.WORKFLOW_STATUS_20)) {
//            this.addMessage(redirectAttributes, "招标授权审批成功！");
//        } else if (StringUtils.equals(taTenderAuthor.getWorkflowStatus(),
//                CommonConstants.WORKFLOW_STATUS_30)) {
//            this.addMessage(redirectAttributes, "招标授权申请退回成功！");
//        } else if (StringUtils.equals(taTenderAuthor.getWorkflowStatus(),
//                CommonConstants.WORKFLOW_STATUS_40)) {
//            this.addMessage(redirectAttributes, "招标授权申请撤回成功！");
//        } else if (StringUtils.equals(taTenderAuthor.getWorkflowStatus(),
//                CommonConstants.WORKFLOW_STATUS_70)) {
//            this.addMessage(redirectAttributes, "招标授权申请删除成功！");
//        }

//        return "redirect:" + adminPath + "/act/task/todo";

		jsonMap.put("success", true);
		jsonMap.put("message", null);

		return jsonMap;
	}

//	@RequiresPermissions("sd:taTenderAuthor:edit")
//	@RequestMapping(value = "manager/save")
//	public String managerSave(TaTenderAuthor taTenderAuthor, Model model, RedirectAttributes redirectAttributes) {
//		taTenderAuthorService.managerSave(taTenderAuthor);
//        model.addAttribute("taTenderAuthor", taTenderAuthor);
//        return "sd/ta/ta001";
//	}
	
//	@RequiresPermissions("sd:taTenderAuthor:edit")
//	@RequestMapping(value = "delete")
//	public String delete(TaTenderAuthor taTenderAuthor, RedirectAttributes redirectAttributes) {
//		taTenderAuthorService.delete(taTenderAuthor);
//		addMessage(redirectAttributes, "删除招标授权成功");
//		return "redirect:"+Global.getAdminPath()+"/sd/taTenderAuthor/?repage";
//	}

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
//        } else if (StringUtils.equals(opt, "临时保存")) {
        } else if (StringUtils.equals(opt, "保存")) {
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

    /**
     * 附件下载
     * 
     * @param fileId
     *            文件id
     * @param request
     *            请求信息
     * @param response
     *            响应信息
     */
    @RequiresPermissions("sd:taTenderAuthor:view")
    @RequestMapping(value = "download/file/{fileId}")
    public void downLoadFile(@PathVariable String fileId, HttpServletRequest request,
            HttpServletResponse response) {
        attachmentsService.downloadFile(fileId, request, response);
    }

    /**
     * 附件删除
     * 
     * @param fileId
     *            附件id
     */
    @RequiresPermissions("sd:taTenderAuthor:edit")
    @RequestMapping(value = "delete/file/{fileId}")
    @ResponseBody
    public Map<String, Object> deleteFile(@PathVariable String fileId) {
        String delFileName = attachmentsService.deleteFile(fileId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", "文件：" + delFileName + "删除成功！");
        resultMap.put("success", new Boolean(true));
        return resultMap;
    }
}