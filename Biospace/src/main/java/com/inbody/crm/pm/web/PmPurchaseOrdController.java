/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.pm.domain.PmAccPurchaseOrdDtlExcel;
import com.inbody.crm.pm.domain.PmPurchaseOrdDtlExcel;
import com.inbody.crm.pm.domain.PmPurchaseOrdExcel;
import com.inbody.crm.pm.domain.PmPurchaseOrdSearch;
import com.inbody.crm.pm.entity.PmPurchaseOrd;
import com.inbody.crm.pm.service.PmPurchaseOrdService;

/**
 * 主子表Controller
 * @author yangj
 * @version 2017-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/pm/purchase")
public class PmPurchaseOrdController extends BaseController {

    @Autowired
    private PmPurchaseOrdService pmPurchaseOrdService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommonService commonService;

    /**
     * 机器/配件采购订单表单取得<br>
     * 管理部权限可同时查看机器和配件
     */
    @RequiresPermissions("pm:purchaseList:view")
    @RequestMapping(value = "list")
    public String list(PmPurchaseOrdSearch purchaseOrdSearch,
            HttpServletRequest request, HttpServletResponse response,
            Model model) {

        // 采单订单list数据取得
        Page<PmPurchaseOrdSearch> page = pmPurchaseOrdService.findPurPage(
                new Page<PmPurchaseOrdSearch>(request, response),
                purchaseOrdSearch);
        model.addAttribute("page", page);
        model.addAttribute("searchForm", purchaseOrdSearch);

        return "inbody/pm/pm003";
    }

    /**
     * 配件采购订单表单取得<br>
     * 具备工程部权限的人只能查看配件
     */
    @RequiresPermissions("pm:acPurchaseList:view")
    @RequestMapping(value = "ac/list")
    public String acList(PmPurchaseOrdSearch purchaseOrdSearch,
            HttpServletRequest request, HttpServletResponse response,
            Model model) {

        // 采单订单list数据取得
        purchaseOrdSearch.setPurchaseType(CommonConstants.PURCHASE_TYPE_2);
        Page<PmPurchaseOrdSearch> page = pmPurchaseOrdService.findPurPage(
                new Page<PmPurchaseOrdSearch>(request, response),
                purchaseOrdSearch);
        model.addAttribute("page", page);
        model.addAttribute("searchForm", purchaseOrdSearch);

        return "inbody/pm/pm005";
    }

    /**
     * 机器采购订单表单取得
     */
    @RequiresPermissions("pm:mcPurchase:view")
    @RequestMapping(value = "mc/form")
    public String mcform(PmPurchaseOrd purOrd, Model model) {

        // 画面主数据取得
        PmPurchaseOrd purData = getViewData(purOrd.getId(),
                CommonConstants.PURCHASE_TYPE_1);
        // 工作流信息返回到画面
        purData.setAct(purOrd.getAct());

        // 画面可操状态
        model.addAttribute("viewsts", commonService.getProcViewStatus(purOrd
                .getAct(), purOrd.getId(), true));
        model.addAttribute("pmPurchaseOrd", purData);

        // 返回画面及数据
        return "inbody/pm/pm001";
    }

    /**
     * 配件采购订单表单取得
     */
    @RequiresPermissions("pm:acPurchase:view")
    @RequestMapping(value = "acc/form")
    public String accform(PmPurchaseOrd purOrd, Model model, HttpSession session) {

        // 画面主数据取得
        PmPurchaseOrd purData = getViewData(purOrd.getId(),
                CommonConstants.PURCHASE_TYPE_2);
        // 工作流信息返回到画面
        purData.setAct(purOrd.getAct());
        // 配件采购时，未被采购的报价单明细取得
        purData.setRmQuotationDtlList(pmPurchaseOrdService
                .getNoPuredQtnDtlList());

        // 画面可操状态
        Map<String, Boolean> viewSts = commonService.getProcViewStatus(purOrd
                .getAct(), purOrd.getId(), true);
        model.addAttribute("viewsts", viewSts);
        model.addAttribute("pmPurchaseOrd", purData);

        
        // 返回画面及数据
        return "inbody/pm/pm002";
    }

    /**
     * 机器采购订单保存
     */
    @RequiresPermissions("pm:mcPurchase:edit")
    @RequestMapping(value = "mc/save")
    public String saveMachine(PmPurchaseOrd purOrd, Model model,
            RedirectAttributes redirectAttributes) {
        // 采购类型机器
        purOrd.setPurchaseType(CommonConstants.PURCHASE_TYPE_1);

        // 画面输入值验证
        if (!beanValidator(model, purOrd)) {
            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    purOrd.getAct(), purOrd.getId(), true));
            model.addAttribute("pmPurchaseOrd", purOrd);
            return "inbody/pm/pm001";
        }

        // 画面输入值保存
        PmPurchaseOrd purOrdClone = SerializationUtils.clone(purOrd);
        // 画面操作所对应的工作流状态取得
        String workflowStatus = this.getWorkflowStatusByOpt(purOrd.getOpt());

        try {
            // 工作流状态设定
            purOrd.setWorkflowStatus(workflowStatus);
            // 机器采购保存
            pmPurchaseOrdService.mcSave(purOrd);
        } catch (ServiceException e) {
            e.printStackTrace();
            // 保存失败，返回消息添加
            this.addMessage(model, e.getMessage());

            // 获取流程实例对象
            Act act = purOrdClone.getAct();
            // 工作流实例存在，且当前操作为再申请、或临时保存、或删除时
            if (act.getProcInsId() != null
                    && !(StringUtils.equals(workflowStatus,
                            CommonConstants.WORKFLOW_STATUS_10)
                            || StringUtils.equals(workflowStatus,
                                    CommonConstants.WORKFLOW_STATUS_60) || StringUtils
                                .equals(workflowStatus,
                                        CommonConstants.WORKFLOW_STATUS_70))) {
                // 画面数据取得
                PmPurchaseOrd viewData = getViewData(purOrdClone.getId(),
                        CommonConstants.PURCHASE_TYPE_1);
                viewData.setAct(act);

                purOrdClone = viewData;
            }

            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    purOrdClone.getAct(),
                    purOrdClone.getId(), true));
            model.addAttribute("pmPurchaseOrd", purOrdClone);
            return "inbody/pm/pm001";
        }

        // 保存成功
        // 临时保存的场合，需要停留在本画面。返回画面提交的数据，更新当前任务
        if (StringUtils.equals(purOrd.getWorkflowStatus(),
                CommonConstants.WORKFLOW_STATUS_60)) {
            // 保存成功消息
            this.addMessage(model, "机器采购订单保存成功！");
            // 画面最新数据重新取得
            PmPurchaseOrd viewData = getViewData(purOrd.getId(),
                    CommonConstants.PURCHASE_TYPE_1);
            // 工作流实例id
            viewData.getAct().setProcInsId(viewData.getProcInsId());
            // 当前任务id
            viewData.getAct().setTaskId(
                    taskService.createTaskQuery()
                            .processInstanceId(viewData.getProcInsId())
                            .singleResult().getId());

            // 画面当前状态取得
            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    viewData.getAct(), viewData.getId(), true));
            model.addAttribute("pmPurchaseOrd", viewData);

            return "inbody/pm/pm001";
        } else {
            // 临时保存以外的场合，操作完毕后画面跳转至我的任务画面，并显示相应的操作消息
            if (StringUtils.equals(purOrd.getWorkflowStatus(),
                    CommonConstants.WORKFLOW_STATUS_10)) {
                this.addMessage(redirectAttributes, "机器采购订单申请提交成功！");
            } else if (StringUtils.equals(purOrd.getWorkflowStatus(),
                    CommonConstants.WORKFLOW_STATUS_20)) {
                this.addMessage(redirectAttributes, "机器采购订单节点审批成功！");
            } else if (StringUtils.equals(purOrd.getWorkflowStatus(),
                    CommonConstants.WORKFLOW_STATUS_30)) {
                this.addMessage(redirectAttributes, "机器采购订单申请退回成功！");
            } else if (StringUtils.equals(purOrd.getWorkflowStatus(),
                    CommonConstants.WORKFLOW_STATUS_40)) {
                this.addMessage(redirectAttributes, "机器采购订单申请撤回成功！");
            } else if (StringUtils.equals(purOrd.getWorkflowStatus(),
                    CommonConstants.WORKFLOW_STATUS_70)) {
                this.addMessage(redirectAttributes, "机器采购订单申请删除成功！");
            }

            return "redirect:" + adminPath + "/act/task/todo";
        }
    }

    /**
     * 管理员权限机器采购订单保存
     */
    @RequiresPermissions("cm:manager:edit")
    @RequestMapping(value = "mc/manager/save")
    public String machineManagerSave(PmPurchaseOrd purOrd, Model model,
            RedirectAttributes redirectAttributes) {
        // 采购类型机器
        purOrd.setPurchaseType(CommonConstants.PURCHASE_TYPE_1);

        // 画面输入值验证
        if (!beanValidator(model, purOrd)) {
            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    purOrd.getAct(), purOrd.getId(), true));
            model.addAttribute("pmPurchaseOrd", purOrd);
            return "inbody/pm/pm001";
        }

        // 画面输入值保存
        PmPurchaseOrd purOrdClone = SerializationUtils.clone(purOrd);

        try {
            // 机器采购保存
            pmPurchaseOrdService.save(purOrd);
        } catch (ServiceException e) {
            e.printStackTrace();
            // 保存失败，返回消息添加
            this.addMessage(model, e.getMessage());

            // 画面数据取得
            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    purOrdClone.getAct(), purOrdClone.getId(),
                    true));
            model.addAttribute("pmPurchaseOrd", purOrdClone);
            return "inbody/pm/pm001";
        }

        // 保存成功
        // 画面最新数据重新取得
        PmPurchaseOrd viewData = getViewData(purOrd.getId(),
                CommonConstants.PURCHASE_TYPE_1);
        // 保存成功消息
        this.addMessage(model, "机器采购订单保存成功！");
        // 画面当前状态取得
        model.addAttribute("viewsts", commonService.getProcViewStatus(
                viewData.getAct(), viewData.getId(), true));
        // 画面当前数据返回
        model.addAttribute("pmPurchaseOrd", viewData);

        return "inbody/pm/pm001";
    }

    /**
     * 配件采购订单保存
     */
    @RequiresPermissions("pm:acPurchase:edit")
    @RequestMapping(value = "acc/save")
    public String saveAccessory(PmPurchaseOrd acPurOrd, Model model,
            RedirectAttributes redirectAttributes, HttpSession session,
            SessionStatus status) {
        // 采购类型为配件
        acPurOrd.setPurchaseType(CommonConstants.PURCHASE_TYPE_2);

        // 画面输入值验证
        if (!beanValidator(model, acPurOrd)) {
            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    acPurOrd.getAct(), acPurOrd.getId(), true));
            model.addAttribute("pmPurchaseOrd", acPurOrd);
            return "inbody/pm/pm002";
        }

        // 画面输入值保存
        PmPurchaseOrd acPurOrdClone = SerializationUtils.clone(acPurOrd);
        // 画面操作所对应的工作流状态取得
        String workflowStatus = this.getWorkflowStatusByOpt(acPurOrd.getOpt());

        try {
            // 画面操作所对应的工作流状态取得
            acPurOrd.setWorkflowStatus(workflowStatus);
            // 配件采购保存
            pmPurchaseOrdService.acSave(acPurOrd);
        } catch (ServiceException e) {
            e.printStackTrace();
            // 保存失败，返因消息添加
            this.addMessage(model, e.getMessage());

            // 获取流程实例对象
            Act act = acPurOrdClone.getAct();
            // 工作流实例存在，且当前操作不是再申请、或临时保存、或删除时，重新取得画面数据
            if (act.getProcInsId() != null
                    && !(StringUtils.equals(workflowStatus,
                            CommonConstants.WORKFLOW_STATUS_10)
                            || StringUtils.equals(workflowStatus,
                                    CommonConstants.WORKFLOW_STATUS_60) || StringUtils
                                .equals(workflowStatus,
                                        CommonConstants.WORKFLOW_STATUS_70))) {
                // 画面数据取得
                PmPurchaseOrd viewData = getViewData(acPurOrdClone.getId(),
                        CommonConstants.PURCHASE_TYPE_2);
                viewData.setAct(act);

                acPurOrdClone = viewData;
            } else {
                // 配件采购时，未被采购的报价单明细取得
                acPurOrdClone.setRmQuotationDtlList(pmPurchaseOrdService
                        .getNoPuredQtnDtlList());
            }

            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    acPurOrdClone.getAct(),
                    acPurOrdClone.getId(), true));
            model.addAttribute("pmPurchaseOrd", acPurOrdClone);

            return "inbody/pm/pm002";
        }

        // 保存成功
        // 临时保存的场合，需要停留在本画面。返回画面提交的数据，更新当前任务
        if (StringUtils.equals(workflowStatus,
                CommonConstants.WORKFLOW_STATUS_60)) {
            // 保存成功消息
            this.addMessage(model, "配件采购订单保存成功！");
            // 画面最新数据重新取得
            PmPurchaseOrd viewData = getViewData(acPurOrd.getId(),
                    CommonConstants.PURCHASE_TYPE_2);
            // 配件采购时，未被采购的报价单明细取得
            viewData.setRmQuotationDtlList(pmPurchaseOrdService
                    .getNoPuredQtnDtlList());
            // 工作流实例id
            viewData.getAct().setProcInsId(viewData.getProcInsId());
            // 当前任务id
            viewData.getAct().setTaskId(
                    taskService.createTaskQuery()
                            .processInstanceId(viewData.getProcInsId())
                            .singleResult().getId());

            // 画面当前状态取得
            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    viewData.getAct(), viewData.getId(), true));
            model.addAttribute("pmPurchaseOrd", viewData);

            return "inbody/pm/pm002";
        } else {
            // 临时保存以外的场合，操作完毕后画面跳转至我的任务画面，并显示相应的操作消息
            if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_10)) {
                this.addMessage(redirectAttributes, "配件采购订单申请提交成功！");
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_20)) {
                this.addMessage(redirectAttributes, "配件采购订单节点审批成功！");
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_30)) {
                this.addMessage(redirectAttributes, "配件采购订单申请退回成功！");
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_40)) {
                this.addMessage(redirectAttributes, "配件采购订单申请撤回成功！");
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_70)) {
                this.addMessage(redirectAttributes, "配件采购订单申请删除成功！");
            }

            return "redirect:" + adminPath + "/act/task/todo";
        }
    }

    /**
     * 管理员权限配件采购订单保存
     */
    @RequiresPermissions("cm:manager:edit")
    @RequestMapping(value = "acc/manager/save")
    public String accessoryManagerSave(PmPurchaseOrd acPurOrd, Model model,
            RedirectAttributes redirectAttributes, HttpSession session,
            SessionStatus status) {
        // 采购类型为配件
        acPurOrd.setPurchaseType(CommonConstants.PURCHASE_TYPE_2);

        // 画面输入值验证
        if (!beanValidator(model, acPurOrd)) {
            // 配件采购时，未被采购的报价单明细取得
            acPurOrd.setRmQuotationDtlList(pmPurchaseOrdService
                    .getNoPuredQtnDtlList());

            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    acPurOrd.getAct(), acPurOrd.getId(), true));
            model.addAttribute("pmPurchaseOrd", acPurOrd);
            return "inbody/pm/pm002";
        }

        // 画面输入值保存
        PmPurchaseOrd acPurOrdClone = SerializationUtils.clone(acPurOrd);

        try {
            // 配件采购保存
            pmPurchaseOrdService.save(acPurOrd);
        } catch (ServiceException e) {
            e.printStackTrace();
            // 保存失败，返因消息添加
            this.addMessage(model, e.getMessage());

            // 配件采购时，未被采购的报价单明细取得
            acPurOrdClone.setRmQuotationDtlList(pmPurchaseOrdService
                    .getNoPuredQtnDtlList());

            model.addAttribute("viewsts", commonService.getProcViewStatus(
                    acPurOrdClone.getAct(),
                    acPurOrdClone.getId(), true));
            model.addAttribute("pmPurchaseOrd", acPurOrdClone);

            return "inbody/pm/pm002";
        }

        // 保存成功
        // 画面最新数据重新取得
        PmPurchaseOrd viewData = getViewData(acPurOrd.getId(),
                CommonConstants.PURCHASE_TYPE_2);
        // 配件采购时，未被采购的报价单明细取得
        viewData.setRmQuotationDtlList(pmPurchaseOrdService
                .getNoPuredQtnDtlList());
        // 保存成功消息
        this.addMessage(model, "配件采购订单保存成功！");

        // 画面当前状态取得
        model.addAttribute("viewsts", commonService.getProcViewStatus(
                viewData.getAct(), viewData.getId(),
                true));
        model.addAttribute("pmPurchaseOrd", viewData);

        return "inbody/pm/pm002";
    }

	/**
	 * 导出机器采购明细数据
	 * 
	 * @param purchaseNo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("pm:mcPurchase:view")
    @RequestMapping(value = "machine/export")
	public String exportMachineFile(@RequestParam String purchaseNo,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "机器采购明细_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<PmPurchaseOrdDtlExcel> list = pmPurchaseOrdService
					.exportPurchaseOrdDtl(purchaseNo);
			new ExportExcel("采购明细", PmPurchaseOrdDtlExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
		    e.printStackTrace();
			addMessage(redirectAttributes, "导出采购明细！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/pm/purchase/mc/form?id=";
	}

	/**
	 * 导出配件采购明细数据
	 * 
	 * @param purchaseNo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("pm:acPurchase:view")
    @RequestMapping(value = "accessory/export")
	public String exportAccessoryFile(@RequestParam String purchaseNo,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "配件采购明细_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<PmAccPurchaseOrdDtlExcel> list = pmPurchaseOrdService
					.exportAccPurchaseOrdDtl(purchaseNo);
			new ExportExcel("配件采购明细", PmAccPurchaseOrdDtlExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
		    e.printStackTrace();
			addMessage(redirectAttributes, "导出配件采购明细！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/pm/purchase/acc/form?id=";
	}

	/**
	 * 导出采购订单一览数据
	 * 
	 * @param purchaseNo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("pm:purchaseList:view")
    @RequestMapping(value = "list/export")
	public String exportPurFile(PmPurchaseOrdSearch purchaseOrdSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "采购订单列表_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<PmPurchaseOrdExcel> list = pmPurchaseOrdService
					.exportPurchaseOrdList(purchaseOrdSearch);
			new ExportExcel("采购订单列表", PmPurchaseOrdExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
		    e.printStackTrace();
			addMessage(redirectAttributes, "导出采购订单！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/pm/purchase/list?repage";
	}

    /**
     * 配件采购订单一览数据
     * 
     * @param purchaseNo
     * @param request
     * @param response
     * @param redirectAttributes
     */
    @RequiresPermissions("pm:acPurchaseList:view")
    @RequestMapping(value = "ac/list/export")
    public String exportAcPurFile(PmPurchaseOrdSearch purchaseOrdSearch,
            HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        try {
            purchaseOrdSearch.setPurchaseType(CommonConstants.PURCHASE_TYPE_2);
            String fileName = "配件采购订单列表_" + DateUtils.getDate("yyyyMMddHHmmss")
                    + ".xlsx";
            List<PmPurchaseOrdExcel> list = pmPurchaseOrdService
                    .exportPurchaseOrdList(purchaseOrdSearch);
            new ExportExcel("配件采购订单列表", PmPurchaseOrdExcel.class)
                    .setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出配件采购订单！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/pm/purchase/ac/list?repage";
    }

	/**
	 * 机器/配件采购订单数据取得
	 * 
	 * @param id
	 *            采购订单id
	 * @param purchaseType
	 *            订单类型
	 * 
	 * @return 订单数据
	 */
	public PmPurchaseOrd getViewData(String id, String purchaseType) {
		PmPurchaseOrd pur = null;
		if (StringUtils.isNotBlank(id)) {
			pur = pmPurchaseOrdService.get(id);
		}
		if (pur == null) {
			pur = new PmPurchaseOrd();
			pur.setCreateName(UserUtils.getUser().getName());
			pur.setCreateDate(new Date());
			pur.setPurchaseType(purchaseType);
		}
		return pur;
	}

	/**
	 * 机器/配件采购订单画面及必要辅助数据取得
	 * 
	 * @param pmPurchaseOrd
	 *            画面数据
	 * @param type
	 *            操作种类
	 * @param model
	 *            面面model
	 * 
	 * @return 画面名
	 */
	public String returnView(PmPurchaseOrd purOrd, String type, Model model) {

		// 配件采购时，未被采购的报价单明细取得
		if (StringUtils.equals(purOrd.getPurchaseType(),
				CommonConstants.PURCHASE_TYPE_2)) {
			purOrd.setRmQuotationDtlList(pmPurchaseOrdService
					.getNoPuredQtnDtlList());
		}
		model.addAttribute("viewsts", commonService.getProcViewStatus(purOrd
				.getAct(), purOrd.getId(), true));
		model.addAttribute("pmPurchaseOrd", purOrd);

		// 根据采购类型不同，取得不同画面
		if (StringUtils.equals(purOrd.getPurchaseType(),
				CommonConstants.PURCHASE_TYPE_1)) {
			return "inbody/pm/pm001";
		} else if (StringUtils.equals(purOrd.getPurchaseType(),
				CommonConstants.PURCHASE_TYPE_2)) {
			return "inbody/pm/pm002";
		}
		return "inbody/pm/pm001";
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