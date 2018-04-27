/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.inbody.crm.common.beanvalidator.BeanValidators;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.Encodes;
import com.inbody.crm.common.utils.ListUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.rm.domain.QuotaSelect;
import com.inbody.crm.rm.domain.RmRepairInfoEx;
import com.inbody.crm.rm.entity.CmCustomerInfo;
import com.inbody.crm.rm.entity.ImInvoice;
import com.inbody.crm.rm.entity.ImInvoiceDtl;
import com.inbody.crm.rm.entity.RmQuotation;
import com.inbody.crm.rm.entity.RmQuotationDtl;
import com.inbody.crm.rm.service.RmQuotationService;

/**
 * 报价单Controller
 * 
 * @author yangj
 * @version 2017-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/rm/quotation")
public class RmQuotationController extends BaseController {

    @Autowired
    private RmQuotationService rmQuotationService;

    @Autowired
    private AttachmentsService attachmentsService;

    @Autowired
    private CommonService commonService;

    /**
     * 预报价单画面取得
     */
    @RequiresPermissions("rm:quotation:view")
    @RequestMapping(value = "pre/form")
    public String preQuotaForm(RmQuotation quota, Model model) {
        RmQuotation input = null;
        if (StringUtils.isNotBlank(quota.getId())) {
            input = rmQuotationService.get(quota.getId());
        }
        if (input == null) {
            input = new RmQuotation();
            // 预报价单
            input.setQuotationType(CommonConstants.QUOTATION_TYPE_1);
            // 记录人
            input.setCreateBy(UserUtils.getUser());
            // 报价日期
            input.setQuoteDate(new Date());
            // sn
            input.setSnNo(quota.getSnNo());
            // 维修编号
            input.setRepairNo(quota.getRepairNo());

            // 报价单维修信息取得，报价单默认值设定
            RmRepairInfoEx repairInfo = rmQuotationService
                    .getQuotaRepairInfo(quota.getRepairNo());
            // 报价负责人
            if (StringUtils.isBlank(repairInfo.getResponsiblePersonId())) {
                input.setResponsiblePersonId(UserUtils.getUser().getId());
            } else {
                input.setResponsiblePersonId(repairInfo.getResponsiblePersonId());
            }
            // 联系人
            input.setContactsName(repairInfo.getContactsName());
            // 电话
            input.setTelephone(repairInfo.getTelephone());
            // 地址
            input.setAddress(repairInfo.getAddress());
            // 报价单明细是否保内默认设定
            input.setDefaultWarranty(
                    rmQuotationService.getDefaultWarranty(repairInfo.getRepairType()));
        }

        input.setQuotationDtlTotalAmount(getQuotationTotalAmount(input.getQuotationDtlList(), CommonConstants.QUOTATION_TYPE_1));
        
        model.addAttribute("quota", input);
        return "inbody/rm/rm006";
    }

    /**
     * 预报价单画面保存
     */
    @RequiresPermissions("rm:quotation:edit")
    @RequestMapping(value = "pre/save")
    @ResponseBody
    public Map<String, Object> preSave(RmQuotation input) {

        // 预报价单
        input.setQuotationType(CommonConstants.QUOTATION_TYPE_1);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            BeanValidators.validateWithException(validator, input);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            resultMap.put("message", StringUtils.join(list, "<br>"));
            return resultMap;
        }

        // 报价单保存数据验证
        List<String> messages = rmQuotationService.validQuotaData(input);
        // 验证不通过
        if (ListUtils.size(messages) > 0) {
            resultMap.put("success", false);
            resultMap.put("message", StringUtils.join(messages, "<br>"));
            return resultMap;
        }

        RmQuotation quota = rmQuotationService.save(input);
        // 清空文件
        quota.setFiles(null);
        resultMap.put("quota", quota);
        resultMap.put("url", "/rm/quotation/pre/form");
        resultMap.put("success", true);
        resultMap.put("message", "报价单保存成功！");
        return resultMap;
    }

    /**
     * 预报价单库存占用
     * 
     * @param preQuotaId
     *            预报价单id
     * @param type
     *            操作类型：1，占用，0，取消占用
     */
    @RequiresPermissions("rm:quotation:edit")
    @RequestMapping(value = "occupy")
    public String inventoryOccupancy(@RequestParam String preQuotaId,
            @RequestParam String type, Model model) {

        try {
            // 占用/取消占用
            RmQuotation quotation = rmQuotationService.inventoryOccupancy(preQuotaId,
                    type);

            // 占用时
            if (StringUtils.equals(type, CommonConstants.OCCUPANCY_TYPE_1)) {
                // 有占用时
                if (StringUtils.equals(quotation.getIfOccupy(),
                        CommonConstants.OCCUPANCY_TYPE_1)) {
                    this.addMessage(model, "占用配件库存成功！");
                } else {
                    this.addMessage(model, "占用库存失败：报价单明细无需库存占用。");
                }
            } else {
                // 取消占用时
                this.addMessage(model, "取消配件库存成功！");
            }

            model.addAttribute("quota", quotation);
            return "inbody/rm/rm006";

        } catch (ServiceException e) {
            this.addMessage(model, e.getMessage());
            model.addAttribute("quota", rmQuotationService.get(preQuotaId));
            return "inbody/rm/rm006";
        }
    }

    /**
     * 最终报价单画面取得
     */
    @RequiresPermissions("rm:finalQuotation:view")
    @RequestMapping(value = "final/form")
    public String finalQuotaForm(RmQuotation quota, Model model) {
        // 最终报价单信息取得
        RmQuotation input = rmQuotationService.getFinalQuotation(quota.getId(),
                quota.getRepairNo(), quota.getSnNo());
        // 最终报价单信息为空时，报价单默认值设定
        if (input == null) {
            input = new RmQuotation();
            // 终报价单
            input.setQuotationType(CommonConstants.QUOTATION_TYPE_2);
            // 记录人
            input.setCreateBy(UserUtils.getUser());
            // 报价日期
            input.setQuoteDate(new Date());
            // sn
            input.setSnNo(quota.getSnNo());
            // 维修编号
            input.setRepairNo(quota.getRepairNo());

            // 报价单维修信息取得
            RmRepairInfoEx repairInfo = rmQuotationService
                    .getQuotaRepairInfo(quota.getRepairNo());
            // 报价负责人
            if (StringUtils.isBlank(repairInfo.getResponsiblePersonId())) {
                input.setResponsiblePersonId(UserUtils.getUser().getId());
            } else {
                input.setResponsiblePersonId(repairInfo.getResponsiblePersonId());
            }
            // 联系人
            input.setContactsName(repairInfo.getContactsName());
            // 电话
            input.setTelephone(repairInfo.getTelephone());
            // 地址
            input.setAddress(repairInfo.getAddress());
            // 报价单明细是否保内默认设定
            input.setDefaultWarranty(
                    rmQuotationService.getDefaultWarranty(repairInfo.getRepairType()));
        }
        
        input.setQuotationDtlTotalAmount(getQuotationTotalAmount(input.getQuotationDtlList(), CommonConstants.QUOTATION_TYPE_1));
        input.setQuotationDtlActTotalAmount(getQuotationTotalAmount(input.getQuotationDtlList(), CommonConstants.QUOTATION_TYPE_2));

        model.addAttribute("quota", input);
        return "inbody/rm/rm006";
    }

    /**
     * 最终报价单画面保存
     */
    @RequiresPermissions("rm:finalQuotation:edit")
    @RequestMapping(value = "final/save")
    @ResponseBody
    public Map<String, Object> finalSave(RmQuotation input) {

        // 最终报价单
        input.setQuotationType(CommonConstants.QUOTATION_TYPE_2);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 画面输入值验证
        try {
            BeanValidators.validateWithException(validator, input);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            resultMap.put("message", StringUtils.join(list, "<br>"));
            return resultMap;
        }

        // 报价单保存数据验证
        List<String> messages = rmQuotationService.validQuotaData(input);
        // 验证不通过
        if (ListUtils.size(messages) > 0) {
            resultMap.put("success", false);
            resultMap.put("message", StringUtils.join(messages, "<br>"));
            return resultMap;
        }

        RmQuotation quota = rmQuotationService.save(input);
        // 清空文件
        quota.setFiles(null);
        resultMap.put("quota", quota);
        resultMap.put("url", "/rm/quotation/final/form");
        resultMap.put("success", true);
        resultMap.put("message", "报价单保存成功！");
        return resultMap;
    }

    /**
     * 发票申请数据验证
     */
    @RequestMapping(value = "form/valid")
    @ResponseBody
    public Map<String, Object> quotaFormValid(RmQuotation input) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 画面输入值验证
        try {
            BeanValidators.validateWithException(validator, input);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            resultMap.put("message", StringUtils.join(list, "<br>"));
            return resultMap;
        }

        List<String> messages = rmQuotationService.validQuotaData(input);
        if (ListUtils.size(messages) > 0) {
            resultMap.put("success", false);
            resultMap.put("message", StringUtils.join(messages, "<br>"));
        } else {
            resultMap.put("success", true);
        }

        return resultMap;
    }

    /**
     * 最终报价单确认出库
     * 
     * @param finalQuotaId
     *            最终报价单id
     */
    @RequiresPermissions("rm:quotation:edit")
    @RequestMapping(value = "outstock")
    public String outstock(@RequestParam String finalQuotaId, Model model) {

        try {
            // 占用/取消占用
            RmQuotation quotation = rmQuotationService.finalQuotaOutstock(finalQuotaId);
            this.addMessage(model, "报价单出库成功！");
            model.addAttribute("quota", quotation);
            return "inbody/rm/rm006";

        } catch (ServiceException e) {
            this.addMessage(model, e.getMessage());
            model.addAttribute("quota", rmQuotationService.get(finalQuotaId));
            return "inbody/rm/rm006";
        }
    }

    /**
     * 报价单发票画面取得
     */
    @RequiresPermissions("rm:quotaInvoice:view")
    @RequestMapping(value = "invoice/form")
    public String invoiceForm(RmQuotation quota, String ivcMode, Model model) {
        // 最终报价单信息取得
        RmQuotation invoiceData = rmQuotationService.getQuotationInvoice(quota.getId(),
                quota.getAct().getBusinessId());
        // 工作流信息返回到画面
        invoiceData.setAct(quota.getAct());

        // 报价单发票画面操作方式
        // 操作模式为空
        if (StringUtils.isBlank(ivcMode) || (!StringUtils.equals(ivcMode, "apply")
                && !StringUtils.equals(ivcMode, "edit"))) {
            ivcMode = "apply";
        }

        // 当前发票头取得
        ImInvoice ivcHead = invoiceData.getInvoice();
        // 申请模式且能进行申请 且入口不为管理画面时
        if (StringUtils.equals(ivcMode, "apply") && invoiceData.getCanInvoiceApply()
                && StringUtils.isBlank(quota.getAct().getProcInsId())) {
            // 将发票头id置为空，表示全新的申请
            ivcHead.setId(null);
            invoiceData.setInvoice(ivcHead);
        }

        invoiceData.setQuotationDtlTotalAmount(getQuotationTotalAmount(invoiceData.getQuotationDtlList(), CommonConstants.QUOTATION_TYPE_1));
        invoiceData.setQuotationDtlActTotalAmount(getQuotationTotalAmount(invoiceData.getQuotationDtlList(), CommonConstants.QUOTATION_TYPE_2));
        invoiceData.setImInvoiceTotalAmount(getImInvoiceTotalAmount(invoiceData.getInvoiceDtlListGroup()));

        // 画面可操状态
        Map<String, Boolean> viewSts = commonService.getProcViewStatus(quota.getAct(),
                ivcHead.getId(), true);
        model.addAttribute("viewsts", viewSts);
        model.addAttribute("quota", invoiceData);
        model.addAttribute("entry", "invoice");
        model.addAttribute("ivcMode", ivcMode);
        return "inbody/rm/rm006";
    }

    /**
     * 发票申请数据保存验证
     */
    @RequestMapping(value = "invoice/valid")
    @ResponseBody
    public Map<String, Object> invoiceValid(RmQuotation input) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 画面输入值验证
        try {
            BeanValidators.validateWithException(validator, input);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            resultMap.put("message", StringUtils.join(list, "<br>"));
            return resultMap;
        }

        List<String> messages = rmQuotationService.validInvoiceInfo(input);
        if (ListUtils.size(messages) > 0) {
            resultMap.put("success", false);
            resultMap.put("message", StringUtils.join(messages, "<br>"));
        } else {
            resultMap.put("success", true);
        }

        return resultMap;
    }

    /**
     * 发票申请
     */
    @RequiresPermissions("rm:quotaInvoice:apply")
    @RequestMapping(value = "invoice/apply")
//    public String invoiceApply(RmQuotation quota, Model model,
    public @ResponseBody Map<String, Object> invoiceApply(RmQuotation quota, Model model,
            RedirectAttributes redirectAttributes) {
    	
    	 Map<String, Object> resultMap = new HashMap<String, Object>();

         // 画面输入值验证
         try {
             BeanValidators.validateWithException(validator, quota);
         } catch (ConstraintViolationException ex) {
             List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
             list.add(0, "数据验证失败：");
             resultMap.put("message", StringUtils.join(list, "<br>"));
             return resultMap;
         }

         List<String> messages = null;

        // 画面操作所对应的工作流状态取得
        String workflowStatus = this.getWorkflowStatusByOpt(quota.getOpt());
        
        if (StringUtils.equals(workflowStatus, CommonConstants.WORKFLOW_STATUS_60) || StringUtils.equals(workflowStatus, CommonConstants.WORKFLOW_STATUS_10)) {

            messages = rmQuotationService.validInvoiceInfo(quota);
        }
        
        if (ListUtils.size(messages) > 0) {
            resultMap.put("success", false);
            resultMap.put("message", StringUtils.join(messages, "<br>"));
        } else {
            // 流程状态
            quota.getInvoice().setWorkflowStatus(workflowStatus);

            // 报价单发票流程流转
            rmQuotationService.flowInvoiceApplyProc(quota);

            resultMap.put("success", true);
        }

        return resultMap;
        // 流程状态
//        quota.getInvoice().setWorkflowStatus(workflowStatus);

        // 报价单发票流程流转
//        rmQuotationService.flowInvoiceApplyProc(quota);

        // 保存成功
        // 临时保存以外的场合，操作完毕后画面跳转至我的任务画面，并显示相应的操作消息
//        if (StringUtils.equals(workflowStatus, CommonConstants.WORKFLOW_STATUS_10)) {
//            this.addMessage(redirectAttributes, "报价单发票申请提交成功！");
//        } else if (StringUtils.equals(workflowStatus,
//                CommonConstants.WORKFLOW_STATUS_20)) {
//            this.addMessage(redirectAttributes, "报价单发票申请节点审批成功！");
//        } else if (StringUtils.equals(workflowStatus,
//                CommonConstants.WORKFLOW_STATUS_30)) {
//            this.addMessage(redirectAttributes, "报价单发票申请退回成功！");
//        } else if (StringUtils.equals(workflowStatus,
//                CommonConstants.WORKFLOW_STATUS_40)) {
//            this.addMessage(redirectAttributes, "报价单发票申请撤回成功！");
//        } else if (StringUtils.equals(workflowStatus,
//                CommonConstants.WORKFLOW_STATUS_70)) {
//            this.addMessage(redirectAttributes, "报价单发票申请删除成功！");
//        }

//        return "redirect:" + adminPath + "/act/task/todo";
    }

    /**
     * 报价单发票审批完成后编辑
     */
    @RequiresPermissions("rm:quotaInvoice:edit")
    @RequestMapping(value = "invoice/save")
    public String invoiceSave(RmQuotation quota, Model model) {

        // 报价单发票信息保存
        rmQuotationService.editInvoice(quota);

        // 保存成功
        // 画面最新数据重新取得
        RmQuotation invoiceData = rmQuotationService.getQuotationInvoice(quota.getId(),
                quota.getAct().getBusinessId());
        // 工作流信息返回到画面
        invoiceData.setAct(quota.getAct());

        invoiceData.setQuotationDtlTotalAmount(getQuotationTotalAmount(invoiceData.getQuotationDtlList(), CommonConstants.QUOTATION_TYPE_1));
        invoiceData.setQuotationDtlActTotalAmount(getQuotationTotalAmount(invoiceData.getQuotationDtlList(), CommonConstants.QUOTATION_TYPE_2));
        invoiceData.setImInvoiceTotalAmount(getImInvoiceTotalAmount(invoiceData.getInvoiceDtlListGroup()));
        // 保存成功消息
        this.addMessage(model, "发票信息保存成功！");
        // 画面当前状态取得
        Map<String, Boolean> viewSts = commonService
                .getProcViewStatus(quota.getAct(), quota.getId(), true);
        model.addAttribute("viewsts", viewSts);
        model.addAttribute("quota", invoiceData);
        model.addAttribute("entry", "invoice");
        model.addAttribute("ivcMode", "edit");

        return "inbody/rm/rm006";
    }

    /**
     * 报价单明细物料信息取得
     * 
     * @param type
     *            物料类型
     * @param kw
     *            查询关键字
     * @param snNo
     *            报价单所对应的机器sn
     */
    @RequestMapping(value = "materials")
    @ResponseBody
    public Map<String, Object> getSelectMaterials(String type,
            @RequestParam(required = false) String kw, @RequestParam String snNo,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        // 类型为未择时，返回空
        if (StringUtils.isBlank(type)) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("items", Lists.newArrayList());
            resultMap.put("totalCount", 0);
            return resultMap;
        }

        Page<QuotaSelect> page = new Page<QuotaSelect>(request, response);
        page.setPageNo(pageNum);
        page.setPageSize(pageSize);

        page = rmQuotationService.getQuotaMaterialDictList(page, type,
                Encodes.urlDecode(kw), snNo);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("items", page.getList());
        resultMap.put("totalCount", page.getCount());

        return resultMap;
    }

    /**
     * 报价单明细新sn信息取得
     * 
     * @param materialNo
     *            物料号
     * @param warehouse
     *            库房
     * @param kw
     *            查询关键字
     */
    @RequestMapping(value = "material/sns")
    @ResponseBody
    public Map<String, Object> getSelectMaterialSnList(String materialNo,
            String warehouse, @RequestParam(required = false) String kw,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        // 类型为未择时，返回空
        if (StringUtils.isBlank(materialNo) || StringUtils.isBlank(warehouse)) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("items", Lists.newArrayList());
            resultMap.put("totalCount", 0);
            return resultMap;
        }

        Page<QuotaSelect> page = new Page<QuotaSelect>(request, response);
        page.setPageNo(pageNum);
        page.setPageSize(pageSize);

        page = rmQuotationService.getQuotaMatSnNoDictList(page, Encodes.urlDecode(kw),
                materialNo, warehouse);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("items", page.getList());
        resultMap.put("totalCount", page.getCount());

        return resultMap;
    }

    /**
     * 自动获取开票信息
     * 
     * @param customerId
     *            客户代码
     * @return 开票客户信息
     */
    @RequestMapping(value = "invoice/customer")
    @ResponseBody
    public CmCustomerInfo getCustomerInfo(@RequestParam String customerId) {
        CmCustomerInfo customerInfo = rmQuotationService.getCustomerInfo(customerId);
        return customerInfo;
    }

    /**
     * 报价单附件下载
     * 
     * @param fileId
     *            报价单文件id
     * @param request
     *            请求信息
     * @param response
     *            响应信息
     */
    @RequiresPermissions("rm:quotation:view")
    @RequestMapping(value = "download/file/{fileId}")
    public void downLoadFile(@PathVariable String fileId, HttpServletRequest request,
            HttpServletResponse response) {
        attachmentsService.downloadFile(fileId, request, response);
    }

    /**
     * 报价单附件删除
     * 
     * @param fileId
     *            报价单附件id
     */
    @RequiresPermissions("rm:quotation:edit")
    @RequestMapping(value = "delete/file/{fileId}")
    @ResponseBody
    public Map<String, Object> deleteFile(@PathVariable String fileId) {
        String delFileName = attachmentsService.deleteFile(fileId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", "文件：" + delFileName + "删除成功！");
        resultMap.put("success", new Boolean(true));
        return resultMap;
    }

    /**
     * sn对应的生产日期取得
     */
    @RequestMapping(value = "sn/dod/{snNo}")
    @ResponseBody
    public QuotaSelect getSnProductionDate(@PathVariable String snNo) {
        return rmQuotationService.getSnProductionDate(snNo);
    }

    /**
     * 预报价单明细导出
     * 
     * @param cd
     *            画面区分：pre 预报价单；final 终最报价单；invoice 报价单发票
     * @param quotaId
     *            报价单id
     * @param request
     *            请求信息
     * @param response
     *            响应信息
     * @param redirectAttributes
     *            重定向信息
     */
    @RequiresPermissions(value = { "rm:quotation:view", "rm:finalQuotation:view",
            "rm:quotaInvoice:apply", "rm:quotaInvoice:edit" }, logical = Logical.OR)
    @RequestMapping(value = "export")
    public String exportMachineFile(@RequestParam String viewCd, @RequestParam String quotaId,
            HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        try {
//            String fileName = "报价单明细_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
//            List<RmQuotationDtlExcel> list = rmQuotationService
//                    .exportQuotaDtlList(quotaId);
//            new ExportExcel("报价单明细", RmQuotationDtlExcel.class).setDataList(list)
//                    .write(response, fileName).dispose();
        	if (StringUtils.equals("pre", viewCd)) {

                rmQuotationService.exportQuota01(response, quotaId);
        	} else if (StringUtils.equals("final", viewCd)) {

                rmQuotationService.exportQuota02(response, quotaId);
        	}
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出报价单明细！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rm/quotation/" + viewCd + "/form?id=" + quotaId;
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
        } else if (StringUtils.equals(opt, "删除") || StringUtils.equals(opt, "撤消")) {
            return CommonConstants.WORKFLOW_STATUS_70;
        } else {
            return "";
        }
    }
	
	/**
	 * 取得报价单明细总金额
	 * @param rmQuotationDtlList
	 *            报价单明细
	 * @param type
	 *            报价单类型（1：预报价单；2：终报价单）
	 * @return 报价单明细总金额
	 */
	public BigDecimal getQuotationTotalAmount(List<RmQuotationDtl> quotationDtlList, String type) {
		
		BigDecimal quotationTotalAmount = BigDecimal.ZERO;
		
		if (quotationDtlList != null && quotationDtlList.size() > 0) {
			
			for (RmQuotationDtl rmQuotationDtl : quotationDtlList) {
				if (StringUtils.equals(type, CommonConstants.QUOTATION_TYPE_1)) {

					quotationTotalAmount = StringUtils.add(quotationTotalAmount, rmQuotationDtl.getTotalAmount());
				} else if (StringUtils.equals(type, CommonConstants.QUOTATION_TYPE_2)) {

					quotationTotalAmount = StringUtils.add(quotationTotalAmount, rmQuotationDtl.getActAmount());
				}
			}
		}
		
		return quotationTotalAmount;
	}
	
	/**
	 * 取得开票明细总金额
	 * @param imInvoiceDtlList
	 *            开票明细
	 * @return 开票明细总金额
	 */
	public BigDecimal getImInvoiceTotalAmount(List<List<ImInvoiceDtl>> invoiceDtlListGroup) {
		
		BigDecimal imInvoiceTotalAmount = BigDecimal.ZERO;
		
		if (invoiceDtlListGroup != null && invoiceDtlListGroup.size() > 0) {
			
			for (List<ImInvoiceDtl> imInvoiceDtls : invoiceDtlListGroup) {
				
				for (ImInvoiceDtl imInvoiceDtl : imInvoiceDtls) {
					
					imInvoiceTotalAmount = StringUtils.add(imInvoiceTotalAmount, imInvoiceDtl.getInvoiceAmount());
				}
			}
		}
		
		return imInvoiceTotalAmount;
	}
}