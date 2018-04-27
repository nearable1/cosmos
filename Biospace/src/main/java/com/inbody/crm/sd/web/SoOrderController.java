/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.web;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sd.domain.ReceiveExcel;
import com.inbody.crm.sd.domain.SmStorageInfoExcel;
import com.inbody.crm.sd.domain.SmStorageInfoSearch;
import com.inbody.crm.sd.domain.SoOrderExcel;
import com.inbody.crm.sd.domain.SoOrderSearch;
import com.inbody.crm.sd.entity.CmCustomerInfo;
import com.inbody.crm.sd.entity.ImInvoiceDtl;
import com.inbody.crm.sd.entity.SmSnInfo;
import com.inbody.crm.sd.entity.SoApplyDeliver;
import com.inbody.crm.sd.entity.SoGatheringInfo;
import com.inbody.crm.sd.entity.SoOrder;
import com.inbody.crm.sd.entity.SoOrderDtl;
import com.inbody.crm.sd.service.SoOrderService;

/**
 * 合同信息录入Controller
 * @author zhanglulu
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sd/soOrder")
public class SoOrderController extends BaseController {

	@Autowired
	private SoOrderService soOrderService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CommonService commonService;

	/**
	 * 合同管理一览取得
	 */
	@RequiresPermissions("sd:soOrder:view")
	@RequestMapping(value = "list")
	public String list(SoOrderSearch soOrderSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		if (StringUtils.isEmptyNull(soOrderSearch.getWorkflowStatus())) {

			soOrderSearch.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_50);
		}
		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user, "sd:soOrder:view"));
		soOrderSearch.setSqlMap(uerMap);

		// list数据取得
		Page<SoOrderSearch> page = soOrderService.findSoOrderPage(
				new Page<SoOrderSearch>(request, response),
				soOrderSearch);
		model.addAttribute("page", page);
		model.addAttribute("searchForm", soOrderSearch);
		
		return "sd/so/so002";
	}
	
	/**
	 * 合同信息表单取得
	 */
	@RequiresPermissions("sd:soOrder:view")
	@RequestMapping(value = "form")
	public String soOrderform(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "act.procInsId", required = false) String procInsId,
			@RequestParam(value = "act.procDefId", required = false) String procDefId,
			@RequestParam(value = "businessOppNo", required = false) String businessOppNo,
			@RequestParam(value = "dataType", required = false) String dataType,
			Model model) {

		Task task = null;
		if (!StringUtils.isBlank(procInsId)) {
			task = taskService.createTaskQuery()
					.processInstanceId(procInsId).singleResult();
		}
		
		if (StringUtils.isEmptyNull(dataType)) {
			if (task != null) {
				String pbkey = task.getProcessDefinitionId();
				String[] keys = pbkey.split(":");
				dataType = keys[0];
			}
		}
		
		if (!StringUtils.isEmptyNull(businessOppNo)) {
			dataType = "order";
		}
		
		if (StringUtils.isEmptyNull(dataType) && !StringUtils.isEmptyNull(procDefId)) {
			String pbkey = procDefId;
			String[] keys = pbkey.split(":");
			dataType = keys[0];
		}

		// 画面主数据取得
		SoOrder soOrderInfo = getViewData(id, businessOppNo, dataType);
		if (task != null) {
			soOrderInfo.getAct().setTask(task);
			soOrderInfo.getAct().setTaskId(task.getId());
			soOrderInfo.getAct().setProcInsId(procInsId);
		} else {
			if (StringUtils.equals(dataType, "invoice")) {
				if (soOrderInfo.getImInvoice() != null) {
					procInsId = soOrderInfo.getImInvoice().getProcInsId();
				}
			} else if (StringUtils.equals(dataType, "order")) {
				procInsId = soOrderInfo.getProcInsId();
			}
			
			if (!StringUtils.isEmptyNull(procInsId)) {

				task = taskService.createTaskQuery()
						.processInstanceId(procInsId).singleResult();
				if (task != null) {
					soOrderInfo.getAct().setTask(task);
					soOrderInfo.getAct().setTaskId(task.getId());
					soOrderInfo.getAct().setProcInsId(procInsId);
				}
			}
		}
		
		// 返回画面及数据
		return returnView(soOrderInfo, model);
	}
	
	/**
	 * 申请时验证
	 * @param soOrder
	 *        画面信息
	 * @return 提示信息
	 */
	@RequestMapping(value = "soOrderInfoValidator", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> soOrderInfoValidator(SoOrder soOrder,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String message = soOrderService.soOrderInfoValidator(soOrder, null);

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
	 * 合同信息保存
	 */
	@RequiresPermissions(value = {"sd:soOrder:edit", "sd:imInvoice:edit", "cm:manager:edit"}, logical = Logical.OR)
	@RequestMapping(value = "orderInfo/save")
//	public String saveSoOrderInfo(SoOrder soOrder, Model model,
	public @ResponseBody Map<String, Object> saveSoOrderInfo(SoOrder soOrder, Model model,
			RedirectAttributes redirectAttributes) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		String optStattus = this.getWorkflowStatusByOpt(soOrder.getOpt());
		
		String message = null;

		if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_60) || StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10)) {

			message = soOrderService.soOrderInfoValidator(soOrder, null);
		}

		if (StringUtils.isEmptyNull(message)) {
			String urlType = null;
			if (StringUtils.isEmptyNull(soOrder.getOrderNo())) {

				Calendar c = Calendar.getInstance();
				String year = Integer.toString(c.get(Calendar.YEAR));
				String code = commonService.getNextSequence("HT", year, 5);
				String orderNo = "InBody" + year + "-" + code;
				soOrder.setOrderNo(orderNo);
			}
			try {

				if (StringUtils.equals(optStattus,
						CommonConstants.WORKFLOW_STATUS_60)) {

					if (StringUtils.equals(soOrder.getDataType(), "order")) {
						soOrderService.saveOrderInfo(soOrder);
					} else {
						soOrderService.updateSoOrderInfo(soOrder);
					}
					
					urlType = "0";
				} else {

					if (StringUtils.equals(soOrder.getDataType(), "invoice")) {
						// 发票信息保存
						soOrderService.saveImInvoiceInfo(soOrder, optStattus);
					} else if (StringUtils.equals(soOrder.getDataType(),
							"order")) {
						// 合同信息保存(商机合同)
						soOrderService.saveSoOrderInfo(soOrder, optStattus);
					}

					urlType = "1";
				}
			} catch (ServiceException e) {

				jsonMap.put("success", false);
				jsonMap.put("message", e.getMessage());
				
				return jsonMap;
			}

			jsonMap.put("success", true);
			jsonMap.put("message", null);
			jsonMap.put("urlType", urlType);
			jsonMap.put("orderId", soOrder.getId());
		} else {
			jsonMap.put("success", false);
			jsonMap.put("message", message);
		}
		return jsonMap;

//		if (StringUtils.isEmptyNull(soOrder.getOrderNo())) {
//
//			Calendar c = Calendar.getInstance();
//			String year = Integer.toString(c.get(Calendar.YEAR));
//			String code=commonService.getNextSequence("HT", year, 5);
//			String orderNo = "InBody" + year + "-"+code;
//			soOrder.setOrderNo(orderNo);
//		}
//		
//		// 画面输入值验证
//		if (!beanValidator(model, soOrder)) {
//			return returnView(soOrder, model);
//		}
//		
//		// 画面输入值保存
//		SoOrder cloneSoOrder = SerializationUtils
//				.clone(soOrder);
//		
//		String optStattus = this.getWorkflowStatusByOpt(soOrder.getOpt());
//		String url = null;
//
//		try {
//
//			if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_60)) {
//				
//				if (StringUtils.equals(soOrder.getDataType(), "order")) {
//					soOrderService.saveOrderInfo(soOrder);
//				} else {
//					soOrderService.updateSoOrderInfo(soOrder);
//				}
//				
//				url = "redirect:" + adminPath + "/sd/soOrder/form?id=" + soOrder.getId();
//			} else {
//
//				if (StringUtils.equals(soOrder.getDataType(), "invoice")) {
//					// 发票信息保存
//					soOrderService.saveImInvoiceInfo(soOrder, optStattus);
//				} else if (StringUtils.equals(soOrder.getDataType(), "order")) {
//					// 合同信息保存(商机合同)
//					soOrderService.saveSoOrderInfo(soOrder, optStattus);
//				}
//				
//				url = "redirect:" + adminPath + "/act/task/todo";
//			}
//		} catch (ServiceException e) {
//			this.addMessage(model, e.getMessage());
//			return returnView(cloneSoOrder, model);
//		}
//		
//		this.addMessage(redirectAttributes, "信息处理成功！");
//		return url;
	}
	
	/**
	 * 收款信息保存
	 * @param soOrder
	 * @return
	 */
	@RequestMapping(value = "saveGatheringInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveGatheringInfo(SoOrder soOrder,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			String message = soOrderService.soOrderInfoValidator(soOrder, "gathering");
			
			if (StringUtils.isEmptyNull(message)) {

				soOrderService.saveSoGatheringInfo(soOrder );
			} else {

				jsonMap.put("message", message);
				jsonMap.put("success", false);
				return jsonMap;
			}
		} catch (ServiceException e) {
			jsonMap.put("message", "收款信息保存失败！");
			jsonMap.put("success", false);
			return jsonMap;
		}

		jsonMap.put("message", "收款信息保存成功！");
		jsonMap.put("success", true);
		return jsonMap;
	}
	
	/**
	 * 输出报价单
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("sd:soOrder:view")
    @RequestMapping(value = "orderDtl/export", method=RequestMethod.GET)
	public String exportOrderDtl(@RequestParam String orderId,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
//			String fileName = "合同明细列表_" + DateUtils.getDate("yyyyMMddHHmmss")
//					+ ".xlsx";
//			List<SoOrderDtlExcel> list = soOrderService
//					.exportOrderDtl(orderId);
//			new ExportExcel(null, SoOrderDtlExcel.class)
//					.setDataList(list).write(response, fileName).dispose();
			soOrderService.exportOrderQuota(response, orderId);
			return null;
		} catch (Exception e) {
//			addMessage(redirectAttributes, "导出合同明细！失败信息：" + e.getMessage());
			addMessage(redirectAttributes, "报价单输出失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/soOrder/form?id=" + orderId;
	}
	
	/**
	 * 应收账款一览数据
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("sd:soOrder:view")
    @RequestMapping(value = "list/exportReceive", method=RequestMethod.GET)
	public String exportReceive(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "应收账款一览_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<ReceiveExcel> list = soOrderService.exportReceive();
			new ExportExcel(null, ReceiveExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出应收账款一览！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/soOrder/list?repage";
	}
	
	/**
	 * 合同查询画面合同明细数据(方案2)
	 * 
	 * @param soOrderSearch
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequiresPermissions("sd:soOrder:view")
    @RequestMapping(value = "list/exportDtl", method=RequestMethod.GET)
	public String exportDtl(SoOrderSearch soOrderSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user, "sd:soOrder:view"));
		soOrderSearch.setSqlMap(uerMap);
		
		try {
			String fileName = "合同明细_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<SoOrderExcel> list = soOrderService.exportDtl(soOrderSearch);
			new ExportExcel(null, SoOrderExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出应收账款一览！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/soOrder/list?repage";
	}

	/**
	 * 合同信息数据取得
	 * 
	 * @param id
	 *            合同id
	 * 
	 * @return 合同数据
	 */
	public SoOrder getViewData(String id, String businessOppNo, String dataType) {
		SoOrder soOrder = null;
		if (StringUtils.isNotBlank(id)) {
			soOrder = soOrderService.getSoOrderInfo(id);
		} else {
			if (StringUtils.isNotBlank(businessOppNo)) {
				soOrder = soOrderService.getBusinessOppInfo(businessOppNo);
				soOrder.setOrderType(CommonConstants.ORDERE_TYPE_1);
			}
		}
		if (soOrder == null) {
			soOrder = new SoOrder();
			soOrder.setCreateBy(UserUtils.getUser());
			soOrder.setCreateDate(new Date());
			
			soOrder.setOrderDate(new Date());
			soOrder.setEmployeeNo(UserUtils.getUser().getId());
			soOrder.setCurrency(CommonConstants.CURRENCY_1);
			// 合同分类
			soOrder.setOrderType(CommonConstants.ORDERE_TYPE_4);
		}
		
		if (!StringUtils.isEmptyNull(dataType)) {
			soOrder.setDataType(dataType);
		}
		
		soOrder.setOrderDtlTotalAmount(getOrderDtlTotalAmount(soOrder.getSoOrderDtlList()));
		soOrder.setGatheringTotalAmount(getGatheringTotalAmount(soOrder.getSoGatheringInfoList()));
		soOrder.setImInvoiceTotalAmount(getImInvoiceTotalAmount(soOrder.getImInvoiceDtlList()));

		return soOrder;
	}
	
	/**
	 * 取得合同明细总金额
	 * @param soOrderDtlList
	 *            合同明细
	 * @return 合同明细总金额
	 */
	public BigDecimal getOrderDtlTotalAmount(List<SoOrderDtl> soOrderDtlList) {
		
		BigDecimal orderDtlTotalAmount = BigDecimal.ZERO;
		
		if (soOrderDtlList != null && soOrderDtlList.size() > 0) {
			
			for (SoOrderDtl soOrderDtl : soOrderDtlList) {
				
				BigDecimal totalAmount = BigDecimal.ZERO;
				if (!StringUtils.isEmptyNull(soOrderDtl.getTotalAmount())) {
					totalAmount = new BigDecimal(soOrderDtl.getTotalAmount());
				}
				
				orderDtlTotalAmount = StringUtils.add(orderDtlTotalAmount, totalAmount);
			}
		}
		
		return orderDtlTotalAmount;
	}
	
	/**
	 * 取得收款明细总金额
	 * @param soGatheringInfoList
	 *            收款明细
	 * @return 收款明细总金额
	 */
	public BigDecimal getGatheringTotalAmount(List<SoGatheringInfo> soGatheringInfoList) {
		
		BigDecimal gatheringTotalAmount = BigDecimal.ZERO;
		
		if (soGatheringInfoList != null && soGatheringInfoList.size() > 0) {
			
			for (SoGatheringInfo soGatheringInfo : soGatheringInfoList) {
				
				BigDecimal totalAmount = BigDecimal.ZERO;
				if (!StringUtils.isEmptyNull(soGatheringInfo.getTotalAmount())) {
					totalAmount = new BigDecimal(soGatheringInfo.getTotalAmount());
				}
				
				gatheringTotalAmount = StringUtils.add(gatheringTotalAmount, totalAmount);
			}
		}
		
		return gatheringTotalAmount;
	}
	
	/**
	 * 取得开票明细总金额
	 * @param imInvoiceDtlList
	 *            开票明细
	 * @return 开票明细总金额
	 */
	public BigDecimal getImInvoiceTotalAmount(List<ImInvoiceDtl> imInvoiceDtlList) {
		
		BigDecimal imInvoiceTotalAmount = BigDecimal.ZERO;
		
		if (imInvoiceDtlList != null && imInvoiceDtlList.size() > 0) {
			
			for (ImInvoiceDtl imInvoiceDtl : imInvoiceDtlList) {
				
				BigDecimal totalAmount = BigDecimal.ZERO;
				if (!StringUtils.isEmptyNull(imInvoiceDtl.getInvoiceAmount())) {
					totalAmount = new BigDecimal(imInvoiceDtl.getInvoiceAmount());
				}
				
				imInvoiceTotalAmount = StringUtils.add(imInvoiceTotalAmount, totalAmount);
			}
		}
		
		return imInvoiceTotalAmount;
	}
	
	/**
	 * 画面及必要辅助数据取得
	 * 
	 * @param soOrderInfo
	 *            画面数据
	 * @param model
	 *            面面model
	 * 
	 * @return 画面名
	 */
	public String returnView(SoOrder soOrderInfo, Model model) {

		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("soOrder", soOrderInfo);

		return "sd/so/so001";
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
	
	/**
	 * 合同查询画面合同明细数据(方案1)
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 *//*
	@RequiresPermissions("sd:soOrder:edit")
    @RequestMapping(value = "list/exportDtl", method=RequestMethod.GET)
	public String exportDtl(SoOrderSearch soOrderSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "合同明细_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Map<SoOrderSearch, List<SoOrderDtl>> dtlMap = soOrderService.exportDtl(soOrderSearch);

			this.writeStringToExcel(response, fileName, dtlMap);
			
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出应收账款一览！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/soOrder/list?repage";
	}
	
	*//**
	 * 往excel文件中写入字符串(合并单元格)
	 * 
	 * @param fileName
	 * @param dtlMap
	 * 
	 * @return excel工作簿
	 *//*
	private void writeStringToExcel(HttpServletResponse response, String fileName, Map<SoOrderSearch, List<SoOrderDtl>> dtlMap) {
		
		// 工作薄对象
		SXSSFWorkbook wb = new SXSSFWorkbook(500);
		// 工作表对象
		Sheet sheet = wb.createSheet("Export");
		// 样式列表
		Map<String, CellStyle> styles = createStyles(wb);
		// 当前行号
		int rownum = 0;
		
		// 头信息标题
		String[] headers = {"合同编号", "订立日期", "销售方式", "业务员", "签约地", "签约方", "合同分类", "币种", "汇率", "佣金/扣除费用", "收款状态", "开票状态", "发货状态"};
		List<String> headerList = Lists.newArrayList(headers);
		// 明细信息标题
		String[] dtlHeaders = {"最终客户", "行业", "具体分类", "科室/系", "省市", "城市", "地区", "地区负责人", "组", "物料名称", "数量", "单价", "金额", "质保期（年）", "延保（年）", "行作废", "已开票数量", "已发货数量"};
		List<String> dtlHeaderList = Lists.newArrayList(dtlHeaders);
		
		for (Map.Entry<SoOrderSearch, List<SoOrderDtl>> entry : dtlMap.entrySet()) {
			
			createHeader(sheet, headerList, rownum++, styles);
			
			createData(entry.getKey(), sheet, wb, rownum++, styles);
			
			createHeader(sheet, dtlHeaderList, rownum++, styles);
			
			rownum = createDtlData(entry.getValue(), sheet, wb, rownum++, styles);
		}
		
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
        try {
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	*//**
	 * 创建表格样式
	 * @param wb 工作薄对象
	 * @return 样式列表
	 *//*
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		return styles;
	}
	
	*//**
	 * 创建标题行
	 * @param sheet 工作表对象
	 * @param headerList 标题列表
	 * @param rownum 行号
	 * @param styles 样式列表
	 *//*
	private void createHeader(Sheet sheet, List<String> headerList, int rownum, Map<String, CellStyle> styles) {

		Row headerRow = sheet.createRow(rownum);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			cell.setCellValue(headerList.get(i));
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
	}
	
	*//**
	 * 创建数据行
	 * @param sheet 工作表对象
	 * @param headerList 标题列表
	 * @param rownum 行号
	 * @param styles 样式列表
	 *//*
	private void createData(SoOrderSearch soOrderSearch ,Sheet sheet, Workbook wb, int rownum, Map<String, CellStyle> styles) {

		Row row = sheet.createRow(rownum);

		addCell(row, 0, soOrderSearch.getOrderNo(), 2, styles, wb);
		addCell(row, 1, soOrderSearch.getOrderDate(), 3, styles, wb);
		addCell(row, 2, DictUtils.getDictLabel(soOrderSearch.getPriceSystem(), "DM0005", ""), 3, styles, wb);
		addCell(row, 3, soOrderSearch.getEmployeeName(), 3, styles, wb);
		addCell(row, 4, soOrderSearch.getCityName(), 3, styles, wb);
		addCell(row, 5, soOrderSearch.getCustomerName(), 3, styles, wb);
		addCell(row, 6, DictUtils.getDictLabel(soOrderSearch.getOrderType(), "DM0008", ""), 3, styles, wb);
		addCell(row, 7, DictUtils.getDictLabel(soOrderSearch.getCurrency(), "DM0009", ""), 3, styles, wb);
		addCell(row, 8, soOrderSearch.getExchangeRate(), 3, styles, wb);
		addCell(row, 9, soOrderSearch.getCommission(), 3, styles, wb);
		addCell(row, 10, DictUtils.getDictLabel(soOrderSearch.getReceiveStatus(), "DM0011", ""), 3, styles, wb);
		addCell(row, 11, DictUtils.getDictLabel(soOrderSearch.getInvoiceStatus(), "DM0012", ""), 3, styles, wb);
		addCell(row, 12, DictUtils.getDictLabel(soOrderSearch.getDeliverStatus(), "DM0010", ""), 3, styles, wb);
	}
	private int createDtlData(List<SoOrderDtl> soOrderDtlList ,Sheet sheet, Workbook wb, int rownum, Map<String, CellStyle> styles) {
		
		int index = rownum;
		for (SoOrderDtl soOrderDtl : soOrderDtlList) {

			Row row = sheet.createRow(index);

			addCell(row, 0, soOrderDtl.getCustomerChName(), 3, styles, wb);
			addCell(row, 1, DictUtils.getDictLabel(soOrderDtl.getIndustry(), "DM0002", ""), 3, styles, wb);
			addCell(row, 2, DictUtils.getDictLabel(soOrderDtl.getCustomerDiff(), "DM0003", ""), 3, styles, wb);
			addCell(row, 3, DictUtils.getDictLabel(soOrderDtl.getCustomerSegmentation(), "DM0039", ""), 3, styles, wb);
			addCell(row, 4, soOrderDtl.getProvinceName(), 3, styles, wb);
			addCell(row, 5, soOrderDtl.getCityName(), 3, styles, wb);
			addCell(row, 6, DictUtils.getDictLabel(soOrderDtl.getRegion(), "DM0049", ""), 3, styles, wb);
			addCell(row, 7, soOrderDtl.getResponsiblePersonName(), 3, styles, wb);
			addCell(row, 8, soOrderDtl.getOrganizeName(), 3, styles, wb);
			addCell(row, 9, soOrderDtl.getMaterialName(), 3, styles, wb);
			addCell(row, 10, soOrderDtl.getNum(), 3, styles, wb);
			addCell(row, 11, soOrderDtl.getUnitPrice(), 3, styles, wb);
			addCell(row, 12, soOrderDtl.getTotalAmount(), 3, styles, wb);
			addCell(row, 13, soOrderDtl.getWarrantyPeriod(), 3, styles, wb);
			addCell(row, 14, soOrderDtl.getExtendedWarrPeriod(), 3, styles, wb);
			addCell(row, 15, DictUtils.getDictLabel(soOrderDtl.getIfEffective(), "yes_no", ""), 3, styles, wb);
			addCell(row, 16, soOrderDtl.getInvoiceNum(), 3, styles, wb);
			addCell(row, 17, soOrderDtl.getDeliverNum(), 3, styles, wb);
			
			index++;
		}
		
		sheet.createRow(index++);

		return index;
	}
	
	private void addCell(Row row, int column, Object val, int align, Map<String, CellStyle> styles, Workbook wb) {
		Cell cell = row.createCell(column);
		String cellFormatString = "@";
		try {
			if(val == null){
				cell.setCellValue("");
			}else{
				if(val instanceof String) {
					cell.setCellValue((String) val);
				}else if(val instanceof Integer) {
					cell.setCellValue((Integer) val);
					cellFormatString = "0";
				}else if(val instanceof Long) {
					cell.setCellValue((Long) val);
					cellFormatString = "0";
				}else if(val instanceof Double) {
					cell.setCellValue((Double) val);
					cellFormatString = "0.00";
				}else if(val instanceof Float) {
					cell.setCellValue((Float) val);
					cellFormatString = "0.00";
				}else if(val instanceof Date) {
					cell.setCellValue((Date) val);
					cellFormatString = "yyyy-MM-dd HH:mm";
				}else {
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}

				CellStyle style = styles.get("data_column_"+column);
				if (style == null){
					style = wb.createCellStyle();
					style.cloneStyleFrom(styles.get("data"+(align>=1&&align<=3?align:"")));
			        style.setDataFormat(wb.createDataFormat().getFormat(cellFormatString));
					styles.put("data_column_" + column, style);
				}
				cell.setCellStyle(style);
		} catch (Exception ex) {
			cell.setCellValue(val.toString());
		}
	}*/
	
	/**
	 * 发货申请画面显示
	 */
	@RequiresPermissions("sd:soApplyDeliver:view")
	@RequestMapping(value = "soApplyDeliver/form")
	public String soApplyDeliverForm(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "act.procInsId", required = false) String procInsId,
			Model model) {

		// 画面主数据取得
		SoApplyDeliver soApplyDeliver = null;
		if (StringUtils.isNotBlank(id)) {
			SoApplyDeliver searchSoApplyDeliver = new SoApplyDeliver();
			searchSoApplyDeliver.setId(id);
			soApplyDeliver = soOrderService.initSoApplyDeliver(searchSoApplyDeliver);
		} else {
			soApplyDeliver = new SoApplyDeliver();
		}
		
		if (!StringUtils.isBlank(procInsId)) {
			Task task = taskService.createTaskQuery()
					.processInstanceId(procInsId).singleResult();
			if (task != null) {
				soApplyDeliver.getAct().setTask(task);
				soApplyDeliver.getAct().setTaskId(task.getId());
				soApplyDeliver.getAct().setProcInsId(procInsId);
			}
		}
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("soApplyDeliver", soApplyDeliver);

		return "sd/so/so003";
	}
	
	/**
	 * 发货申请画面查找
	 */
	@RequiresPermissions("sd:soApplyDeliver:view")
	@RequestMapping(value = "soApplyDeliver/search")
	public String soApplyDeliverSearch(@RequestParam String orderNo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		SoApplyDeliver searchSoApplyDeliver = new SoApplyDeliver();
		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
		searchSoApplyDeliver.setSqlMap(uerMap);
		searchSoApplyDeliver.setOrderNo(orderNo);

		// 画面主数据取得
		SoApplyDeliver soApplyDeliver = soOrderService.getSoApplyDeliverInfo(searchSoApplyDeliver);
		
		if (soApplyDeliver == null) {
			soApplyDeliver = new SoApplyDeliver();
			soApplyDeliver.setOrderNo(orderNo);
			
			this.addMessage(model, "没有找到相关数据！");
		} else {
			if (soApplyDeliver.getSoApplyDeliverDtlList() == null || soApplyDeliver.getSoApplyDeliverDtlList().size() == 0) {
				this.addMessage(model, "已经全部发货完毕！");
			}
		}
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("soApplyDeliver", soApplyDeliver);

		return "sd/so/so003";
	}
	
	/**
	 * 发货申请提交
	 */
	@RequiresPermissions("sd:soApplyDeliver:edit")
	@RequestMapping(value = "soApplyDeliver/save")
//	public String saveSoApplyDeliverInfo(SoApplyDeliver soApplyDeliver, Model model,
	public @ResponseBody Map<String, Object> saveSoApplyDeliverInfo(SoApplyDeliver soApplyDeliver, Model model,
			RedirectAttributes redirectAttributes) {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		// 画面输入值验证
//		if (!beanValidator(model, soApplyDeliver)) {
//
//			model.addAttribute("user", UserUtils.getUser());
//			model.addAttribute("soApplyDeliver", soApplyDeliver);
//
//			return "sd/so/so003";
//		}
		
		// 画面输入值保存
//		SoApplyDeliver cloneSoApplyDeliver = SerializationUtils
//				.clone(soApplyDeliver);
		
		String optStattus = this.getWorkflowStatusByOpt(soApplyDeliver.getOpt());

		String message = null;
		
		if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_60) || StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10)) {
			message = soOrderService.soApplyDeliverValid(soApplyDeliver);
		}

//		try {
//
//			// 发货申请提交
//			soOrderService.saveSoApplyDeliverInfo(soApplyDeliver, optStattus);
//		} catch (ServiceException e) {
//			this.addMessage(model, e.getMessage());
//
//			model.addAttribute("user", UserUtils.getUser());
//			model.addAttribute("soApplyDeliver", cloneSoApplyDeliver);
//
//			return "sd/so/so003";
//		}
		
//		this.addMessage(redirectAttributes, "信息处理成功！");

//		return "redirect:" + adminPath + "/act/task/todo";

		if (StringUtils.isEmptyNull(message)) {
			
			try {

				// 发货申请提交
				soOrderService.saveSoApplyDeliverInfo(soApplyDeliver, optStattus);
			} catch (ServiceException e) {

				jsonMap.put("success", false);
				jsonMap.put("message", e.getMessage());
				
				return jsonMap;
			}

			jsonMap.put("success", true);
			jsonMap.put("message", null);
		} else {
			jsonMap.put("success", false);
			jsonMap.put("message", message);
		}
		return jsonMap;
	}
	
	/**
	 * 发货申请画面数据验证
	 * @param soApplyDeliver
	 * 
	 */
	@RequestMapping(value = "soApplyDeliver/valid", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> soApplyDeliverValid(SoApplyDeliver soApplyDeliver,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String message = soOrderService.soApplyDeliverValid(soApplyDeliver);

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
	 * 合同出库画面显示
	 */
	@RequiresPermissions("sd:smStorageInfo:view")
	@RequestMapping(value = "smStorageInfo/form")
	public String smStorageInfoForm(Model model) {

		// 画面主数据取得
		SmStorageInfoSearch smStorageInfoSearch = new SmStorageInfoSearch();
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("smStorageInfoSearch", smStorageInfoSearch);

		return "sd/so/so004";
	}
	
	/**
	 * 合同出库画面查找
	 */
	@RequiresPermissions("sd:smStorageInfo:edit")
	@RequestMapping(value = "smStorageInfo/search")
	public String smStorageInfoSearch(@RequestParam String orderNo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		// 画面主数据取得
		SmStorageInfoSearch smStorageInfoSearch = soOrderService.getSmStorageInfo(orderNo);
		
		if (smStorageInfoSearch == null) {
			smStorageInfoSearch = new SmStorageInfoSearch();
			smStorageInfoSearch.setOrderNo(orderNo);
			
			this.addMessage(model, "没有找到相关数据！");
		} else {
			if (smStorageInfoSearch.getSmStorageInfoList() == null || smStorageInfoSearch.getSmStorageInfoList().size() == 0) {
				this.addMessage(model, "已经全部发货完毕！");
			}
		}
		
		// 返回画面及数据
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("smStorageInfoSearch", smStorageInfoSearch);

		return "sd/so/so004";
	}
	
	/**
	 * 合同出库画面数据验证
	 * @param smStorageInfoSearch
	 * 
	 */
	@RequestMapping(value = "smStorageInfo/valid", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> smStorageInfoValid(SmStorageInfoSearch smStorageInfoSearch,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String message = soOrderService.smStorageInfoValid(smStorageInfoSearch);

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
	 * 确认出库提交
	 */
	@RequiresPermissions("sd:smStorageInfo:edit")
	@RequestMapping(value = "smStorageInfo/save")
//	public String saveSmStorageInfo(SmStorageInfoSearch smStorageInfoSearch, Model model,
	public @ResponseBody Map<String, Object> saveSmStorageInfo(SmStorageInfoSearch smStorageInfoSearch, Model model,
			RedirectAttributes redirectAttributes) {
		
		// 画面输入值验证
//		if (!beanValidator(model, smStorageInfoSearch)) {
//
//			model.addAttribute("user", UserUtils.getUser());
//			model.addAttribute("smStorageInfoSearch", smStorageInfoSearch);
//
//			return "sd/so/so004";
//		}
//
//		try {
//
//			// 出库提交
//			soOrderService.saveSmStorageInfo(smStorageInfoSearch);
//		} catch (ServiceException e) {
//			this.addMessage(model, e.getMessage());
//
//			model.addAttribute("user", UserUtils.getUser());
//			model.addAttribute("smStorageInfoSearch", smStorageInfoSearch);
//
//			return "sd/so/so004";
//		}
//		
//		this.addMessage(redirectAttributes, "信息处理成功！");
//
//		return "redirect:" + adminPath + "/sd/soOrder/smStorageInfo/form";

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		String message = soOrderService.smStorageInfoValid(smStorageInfoSearch);

		if (StringUtils.isEmptyNull(message)) {

			try {

				// 出库提交
				soOrderService.saveSmStorageInfo(smStorageInfoSearch);
			} catch (ServiceException e) {
				jsonMap.put("success", false);
				jsonMap.put("message", e.getMessage());
				
				return jsonMap;
			}

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
	@RequestMapping(value = "smStorageInfo/getSnInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSnInfo(@RequestParam String snNo, @RequestParam String materialNo) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		SmSnInfo smSnInfo = soOrderService.getSnInfo(snNo, materialNo);

		if (smSnInfo != null) {

			jsonMap.put("warehouse", smSnInfo.getWarehouse());
			jsonMap.put("productionDate", DateUtils.formatDate(smSnInfo.getProductionDate(), "yyyy-MM-dd"));
		}
		return jsonMap;
	}
	
	/**
	 * 合同出库导出
	 * 
	 * @param smStorageInfoSearch
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("sd:smStorageInfo:view")
    @RequestMapping(value = "smStorageInfo/exportDtl")
	public String smStorageInfoExportDtl(SmStorageInfoSearch smStorageInfoSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		try {
			String fileName = "合同出库_" + smStorageInfoSearch.getOrderNo() + "_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<SmStorageInfoExcel> list = soOrderService.smStorageInfoExportDtl(smStorageInfoSearch);
			new ExportExcel(null, SmStorageInfoExcel.class)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出合同出库明细一览！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/soOrder/smStorageInfo/search?orderNo=" + smStorageInfoSearch.getOrderNo();
	}

	/**
	 * 签约方信息取得
	 * @param customerId
	 *        客户代码
	 * @return 签约方信息
	 */
	@RequestMapping(value = "getCustomerInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getCustomerInfo(
			@RequestParam(value = "customerId", required = false) String customerId) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		CmCustomerInfo customerInfo = soOrderService.getCustomerInfo(customerId);

		jsonMap.put("customerInfo", customerInfo);
		return jsonMap;
	}

	/**
	 * 合同行信息取得
	 * @param orderId
	 *        合同ID
	 * @param lineNo
	 *        行号
	 * @return 合同行信息
	 */
	@RequestMapping(value = "getOrderDtlInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getOrderDtlInfo(
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "lineNo", required = false) String lineNo) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		SoOrderDtl orderDtlInfo = soOrderService.getOrderDtlInfo(orderId, lineNo);

		jsonMap.put("orderDtlInfo", orderDtlInfo);
		return jsonMap;
	}
}