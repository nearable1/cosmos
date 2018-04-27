package com.inbody.crm.mm.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.Logical;
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
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.mm.entity.SmSnInfo;
import com.inbody.crm.mm.entity.SmStorageApp;
import com.inbody.crm.mm.service.InStorageManagementService;
import com.inbody.crm.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/mm/inStorageManagement")
public class InStorageManagementController extends BaseController {

	@Autowired
	private InStorageManagementService inStorageManagementService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 其它入库申请申请画面初始化
	 */
	@RequestMapping(value = "init/{type}")
	public String init(@PathVariable String type) {

		if (StringUtils.equals(type, "sendBack")) {

			return "redirect:" + adminPath + "/mm/inStorageManagement/sendBack/init";
		} else if (StringUtils.equals(type, "refund")) {

			return "redirect:" + adminPath + "/mm/inStorageManagement/refund/init";
		} else if (StringUtils.equals(type, "exchange")) {

			return "redirect:" + adminPath + "/mm/inStorageManagement/exchange/init";
		} else if (StringUtils.equals(type, "other")) {

			return "redirect:" + adminPath + "/mm/inStorageManagement/other/init";
		}
		
		return null;
	}
	
	/**
	 * 其它入库申请申请画面初始化
	 */
	@RequiresPermissions(value = {"mm:inStorageManagement:sendBackView", "mm:inStorageManagement:refundView", "mm:inStorageManagement:exchangeView", "mm:inStorageManagement:otherView"}, logical = Logical.OR)
	@RequestMapping(value = "{type}/init")
	public String init(@PathVariable String type,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		SmStorageApp smStorageApp = inStorageManagementService.initSmStorageApp(type);
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("smStorageApp", smStorageApp);

		if (StringUtils.equals(type, "sendBack")) {

			return "inbody/mm/sm/sm002";
		} else if (StringUtils.equals(type, "refund")) {

			return "inbody/mm/sm/sm002_1";
		} else if (StringUtils.equals(type, "exchange")) {

			return "inbody/mm/sm/sm002_2";
		} else if (StringUtils.equals(type, "other")) {

			return "inbody/mm/sm/sm002_3";
		}
		
		return null;
	}
	
	/**
	 * 其它入库申请申请画面初始化
	 */
	@RequiresPermissions(value = {"mm:inStorageManagement:sendBackView", "mm:inStorageManagement:refundView", "mm:inStorageManagement:exchangeView", "mm:inStorageManagement:otherView"}, logical = Logical.OR)
	@RequestMapping(value = "{type}/search")
	public String search(@PathVariable String type,
			SmStorageApp smStorageApp,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		smStorageApp = inStorageManagementService.searchSmStorageApp(smStorageApp, type);
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("smStorageApp", smStorageApp);

		if (StringUtils.equals(type, "sendBack")) {

			return "inbody/mm/sm/sm002";
		} else if (StringUtils.equals(type, "refund")) {

			return "inbody/mm/sm/sm002_1";
		} else if (StringUtils.equals(type, "exchange")) {

			return "inbody/mm/sm/sm002_2";
		} else if (StringUtils.equals(type, "other")) {

			return "inbody/mm/sm/sm002_3";
		}
		
		return null;
	}
	
	/**
	 * 其它入库申请(归还)申请画面显示
	 */
	@RequestMapping(value = "sendBack/form")
	public String sendBackForm(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "act.procInsId", required = true) String procInsId,
			Model model) {

		// 画面主数据取得
		SmStorageApp smStorageApp = inStorageManagementService.getSmStorageAppInfo(id, "sendBack");

		Task task = taskService.createTaskQuery()
				.processInstanceId(procInsId).singleResult();
		if (task != null) {
			smStorageApp.getAct().setTask(task);
			smStorageApp.getAct().setTaskId(task.getId());
			smStorageApp.getAct().setProcInsId(procInsId);
		}
	
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("smStorageApp", smStorageApp);

		return "inbody/mm/sm/sm002";
	}
	
	/**
	 * 其它入库申请(退货)申请画面显示
	 */
	@RequestMapping(value = "refund/form")
	public String refundForm(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "act.procInsId", required = true) String procInsId,
			Model model) {

		// 画面主数据取得
		SmStorageApp smStorageApp = inStorageManagementService.getSmStorageAppInfo(id, "refund");

		Task task = taskService.createTaskQuery()
				.processInstanceId(procInsId).singleResult();
		if (task != null) {
			smStorageApp.getAct().setTask(task);
			smStorageApp.getAct().setTaskId(task.getId());
			smStorageApp.getAct().setProcInsId(procInsId);
		}
	
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("smStorageApp", smStorageApp);

		return "inbody/mm/sm/sm002_1";
	}
	
	/**
	 * 其它入库申请(换货)申请画面显示
	 */
	@RequestMapping(value = "exchange/form")
	public String exchangeForm(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "act.procInsId", required = true) String procInsId,
			Model model) {

		// 画面主数据取得
		SmStorageApp smStorageApp = inStorageManagementService.getSmStorageAppInfo(id, "exchange");

		Task task = taskService.createTaskQuery()
				.processInstanceId(procInsId).singleResult();
		if (task != null) {
			smStorageApp.getAct().setTask(task);
			smStorageApp.getAct().setTaskId(task.getId());
			smStorageApp.getAct().setProcInsId(procInsId);
		}
	
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("smStorageApp", smStorageApp);

		return "inbody/mm/sm/sm002_2";
	}
	
	/**
	 * 其它入库申请(换货)申请画面显示
	 */
	@RequestMapping(value = "other/form")
	public String otherForm(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "act.procInsId", required = true) String procInsId,
			Model model) {

		// 画面主数据取得
		SmStorageApp smStorageApp = inStorageManagementService.getSmStorageAppInfo(id, "other");

		Task task = taskService.createTaskQuery()
				.processInstanceId(procInsId).singleResult();
		if (task != null) {
			smStorageApp.getAct().setTask(task);
			smStorageApp.getAct().setTaskId(task.getId());
			smStorageApp.getAct().setProcInsId(procInsId);
		}
	
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("smStorageApp", smStorageApp);

		return "inbody/mm/sm/sm002_3";
	}
	
	/**
	 * 其它入库申请(归还)申请提交
	 */
	@RequestMapping(value = "save/{type}")
//	public String saveSmStorageAppInfo(@PathVariable String type,
	public @ResponseBody Map<String, Object> saveSmStorageAppInfo(@PathVariable String type,
			SmStorageApp smStorageApp, Model model,
			RedirectAttributes redirectAttributes) {
		
		// 画面输入值保存
//		SmStorageApp cloneSmStorageApp = SerializationUtils
//				.clone(smStorageApp);

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		String optStattus = this.getWorkflowStatusByOpt(smStorageApp.getOpt());

		String message = null;
		if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_60) || StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10)) {

			message = inStorageManagementService.smStorageAppValid(smStorageApp, type);
		}
		
		if (StringUtils.isEmptyNull(message)) {
			try {

				// 其它入库申请提交
				inStorageManagementService.saveSmStorageAppInfo(smStorageApp, optStattus, type);
			} catch (ServiceException e) {
				jsonMap.put("success", false);
				jsonMap.put("message", e.getMessage());
				return jsonMap;
//			this.addMessage(model, e.getMessage());
//
//			model.addAttribute("user", UserUtils.getUser());
//			model.addAttribute("smStorageApp", cloneSmStorageApp);
//
//			if (StringUtils.equals(type, "sendBack")) {
//
//				return "inbody/mm/sm/sm002";
//			} else if (StringUtils.equals(type, "refund")) {
//
//				return "inbody/mm/sm/sm002_1";
//			} else if (StringUtils.equals(type, "exchange")) {
//
//				return "inbody/mm/sm/sm002_2";
//			} else if (StringUtils.equals(type, "other")) {
//
//				return "inbody/mm/sm/sm002_3";
//			}
			}
		
//		this.addMessage(redirectAttributes, "信息处理成功！");

//		return "redirect:" + adminPath + "/act/task/todo";

			jsonMap.put("success", true);
			jsonMap.put("message", null);
		} else {
			jsonMap.put("success", false);
			jsonMap.put("message", message);
		}
		return jsonMap;
	}
	
	/**
	 * 其它入库申请画面数据验证
	 * @param smStorageApp
	 * 
	 */
	@RequestMapping(value = "valid/{type}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> smStorageAppValid(@PathVariable String type,
			SmStorageApp smStorageApp,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String message = inStorageManagementService.smStorageAppValid(smStorageApp, type);

		if (StringUtils.isEmptyNull(message)) {

			jsonMap.put("success", true);
			jsonMap.put("message", null);
		} else {
			jsonMap.put("success", false);
			jsonMap.put("message", message);
		}
		return jsonMap;
	}
	
	/**
	 * SN信息取得
	 * @param snNo
	 *        S/N号
	 * @param materialNo
	 *        物料号
	 *        
	 * @return SN信息
	 */
	@RequestMapping(value = "getSnInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSnInfo(@RequestParam String snNo, @RequestParam String materialNo) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		SmSnInfo smSnInfo = inStorageManagementService.getSnInfo(snNo, materialNo);

		if (smSnInfo != null) {

			jsonMap.put("productionDate", DateUtils.formatDate(smSnInfo.getProductionDate(), "yyyy-MM-dd"));
		}
		return jsonMap;
	}
	
	/**
	 * 根据操作按钮取得工作流状态
	 * 
	 * @param opt
	 *            操作按钮
	 * @return 工作流状态
	 */
	private String getWorkflowStatusByOpt(String opt) {
		if (StringUtils.equals(opt, "提交申请") || StringUtils.equals(opt, "再申请") || StringUtils.isEmptyNull(opt)) {
			return CommonConstants.WORKFLOW_STATUS_10;
		} else if (StringUtils.equals(opt, "同意")) {
			return CommonConstants.WORKFLOW_STATUS_20;
		} else if (StringUtils.equals(opt, "退回")) {
			return CommonConstants.WORKFLOW_STATUS_30;
		} else if (StringUtils.equals(opt, "撤回")) {
			return CommonConstants.WORKFLOW_STATUS_40;
		} else if (StringUtils.equals(opt, "保存")) {
			return CommonConstants.WORKFLOW_STATUS_60;
		} else if (StringUtils.equals(opt, "删除")) {
			return CommonConstants.WORKFLOW_STATUS_70;
		} else {
			return "";
		}
	}
}
