/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.SmPackageInfoEntity;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.NumberToCNUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportFileDtl;
import com.inbody.crm.common.utils.excel.ExportFileHead;
import com.inbody.crm.common.utils.excel.FileExportUtil;
import com.inbody.crm.modules.act.service.ActProcessService;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.modules.sys.utils.DictUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.pm.dao.SmMatInfoDao;
import com.inbody.crm.pm.entity.SmMatInfo;
import com.inbody.crm.sd.dao.BoBusinessOppDao;
import com.inbody.crm.sd.dao.BoBusinessOppDtlDao;
import com.inbody.crm.sd.dao.ImInvoiceDao;
import com.inbody.crm.sd.dao.SoApplyDeliverDao;
import com.inbody.crm.sd.dao.SoApplyDeliverDtlDao;
import com.inbody.crm.sd.dao.SoGatheringInfoDao;
import com.inbody.crm.sd.dao.SoOrderDao;
import com.inbody.crm.sd.dao.SoOrderDtlDao;
import com.inbody.crm.sd.domain.ReceiveExcel;
import com.inbody.crm.sd.domain.SmStorageInfoExcel;
import com.inbody.crm.sd.domain.SmStorageInfoSearch;
import com.inbody.crm.sd.domain.SoOrderDtlExcel;
import com.inbody.crm.sd.domain.SoOrderExcel;
import com.inbody.crm.sd.domain.SoOrderSearch;
import com.inbody.crm.sd.entity.BoBusinessOpp;
import com.inbody.crm.sd.entity.BoBusinessOppDtl;
import com.inbody.crm.sd.entity.CmCustomerInfo;
import com.inbody.crm.sd.entity.EmployeeInfo;
import com.inbody.crm.sd.entity.ImInvoice;
import com.inbody.crm.sd.entity.ImInvoiceDtl;
import com.inbody.crm.sd.entity.SmSnInfo;
import com.inbody.crm.sd.entity.SmStorageInfo;
import com.inbody.crm.sd.entity.SmWarehouseInfo;
import com.inbody.crm.sd.entity.SoApplyDeliver;
import com.inbody.crm.sd.entity.SoApplyDeliverDtl;
import com.inbody.crm.sd.entity.SoGatheringInfo;
import com.inbody.crm.sd.entity.SoOrder;
import com.inbody.crm.sd.entity.SoOrderDtl;
import com.inbody.crm.sd.entity.SoRmQuotationDtl;
import com.inbody.crm.sd.entity.SoRmRepairInfo;

/**
 * 合同信息录入Service
 * @author zhanglulu
 * @version 2017-08-22
 */
@Service
@Transactional(readOnly = true)
public class SoOrderService extends CrudService<SoOrderDao, SoOrder> {

	@Value("${quotaTemplatePath}")
	private String quotaTemplatePath;
	
	private final String TEMPLATE_NAME = "合同报价单.xls";
	
	private final String EXPORT_FILE_NAME = "报价单";
	
	private final String EXPORT_FILE_SUFIX = ".xls";

	@Autowired
	private SoOrderDtlDao soOrderDtlDao;

	@Autowired
	private SoOrderDao soOrderDao;

	@Autowired
	private SoGatheringInfoDao soGatheringInfoDao;

	@Autowired
	private ImInvoiceDao imInvoiceDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private SoApplyDeliverDao soApplyDeliverDao;

	@Autowired
	private SoApplyDeliverDtlDao soApplyDeliverDtlDao;

	@Autowired
	private ActProcessService actProcessService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private BoBusinessOppDao boBusinessOppDao;
	
	@Autowired
	private BoBusinessOppDtlDao boBusinessOppDtlDao;
	
	@Autowired
	private SmMatInfoDao smMatInfoDao;
	

	/**
	 * 查询结果取得
	 * 
	 * @param page
	 * @param soOrderSearch
	 * @return
	 */
	public Page<SoOrderSearch> findSoOrderPage(Page<SoOrderSearch> page,
			SoOrderSearch soOrderSearch) {

		soOrderSearch.setPage(page);
		List<SoOrderSearch> orderSearchList = soOrderDao
				.findPageList(soOrderSearch);
		page.setList(orderSearchList);
		return page;
	}
	
	public SoOrder getSoOrderInfo(String id) {
		
		SoOrder soOrder = soOrderDao.getSoOrderInfo(id);
		
		if (soOrder != null) {
			List<SoOrderDtl> soOrderDtlList = soOrderDtlDao.getSoOrderDtlList(id);
			
			if (soOrderDtlList != null && soOrderDtlList.size() > 0) {
				for (SoOrderDtl soOrderDtl : soOrderDtlList) {
					if (!StringUtils.isEmptyNull(soOrderDtl.getPackageMertiralNo())) {

						soOrderDtl.setDeliverNumMax(soOrderDtlDao.getDeliverNumMax(soOrderDtl));
						soOrderDtl.setInvoiceNumMax(soOrderDtlDao.getInvoiceNumMax(soOrderDtl));
					} else {
						soOrderDtl.setDeliverNumMax(soOrderDtl.getDeliverNum());
						soOrderDtl.setInvoiceNumMax(soOrderDtl.getInvoiceNum());
					}
				}
				soOrder.setSoOrderDtlList(soOrderDtlList);
			}
			
			SoApplyDeliver soApplyDeliver = soApplyDeliverDao.getSoAppDelWorkStatusByOrderNo(soOrder.getOrderNo());
			
			if (soApplyDeliver != null) {
				soOrder.setDeliverWorkflowStatus(soApplyDeliver.getWorkflowStatus());
			}
			
			List<SoGatheringInfo> soGatheringInfoList = soGatheringInfoDao.getSoGatheringInfoList(soOrder.getOrderNo());
			
			if (soGatheringInfoList != null && soGatheringInfoList.size() > 0) {
				soOrder.setSoGatheringInfoList(soGatheringInfoList);
			}
			
			ImInvoice imInvoice = imInvoiceDao.getApplyingImInvoiceByOrderNo(soOrder.getOrderNo());
			
			if (imInvoice == null) {
				imInvoice = imInvoiceDao.getAppOverImInvoiceByOrderNo(soOrder.getOrderNo());
			}
			
			List<ImInvoiceDtl> imInvoiceDtlList = imInvoiceDao.getImInvoiceDtlListByOrderNo(soOrder.getOrderNo());
			
			String invoiceType = null;
			
			if (imInvoiceDtlList != null && imInvoiceDtlList.size() > 0) {
				
				invoiceType = imInvoiceDtlList.get(0).getInvoiceType();
				
				soOrder.setImInvoiceDtlList(imInvoiceDtlList);
			} else {
				
				CmCustomerInfo customerinfo = getCustomerInfo(soOrder.getCustomerId());

				invoiceType = customerinfo.getInvoiceType();
			}
			
			if (imInvoice != null) {
				
				imInvoice.setInvoiceType(invoiceType);
				
				soOrder.setImInvoice(imInvoice);
			}
		}

		return soOrder;
	}
	
	public SoOrder getBusinessOppInfo(String businessOppNo) {
		// 取得商机信息
		BoBusinessOpp boBusinessOpp = boBusinessOppDao.getInfoByBusinessOppNo(businessOppNo);
		
		// 合同信息
		SoOrder soOrderInfo = null;
		if (boBusinessOpp != null && StringUtils.equals(boBusinessOpp.getIfContractGeneration(), CommonConstants.NO)) {
			soOrderInfo =  new SoOrder();

			soOrderInfo.setCreateBy(UserUtils.getUser());
			soOrderInfo.setCreateDate(new Date());
			
			soOrderInfo.setOrderDate(new Date());
			soOrderInfo.setOrderType(CommonConstants.ORDERE_TYPE_1);
			soOrderInfo.setCurrency(CommonConstants.CURRENCY_1);
			
			// 商机号
			soOrderInfo.setBusinessOppNo(boBusinessOpp.getBusinessOppNo());
			
			// 价格体系
			soOrderInfo.setPriceSystem(boBusinessOpp.getPriceSystem());
			
			// 负责人
			soOrderInfo.setEmployeeNo(boBusinessOpp.getResponsiblePersonId());
			
			// 签约方
			if (StringUtils.equals(boBusinessOpp.getPriceSystem(), CommonConstants.PRICE_SYSTEM_2)
					|| StringUtils.equals(boBusinessOpp.getPriceSystem(), CommonConstants.PRICE_SYSTEM_3)) {

				soOrderInfo.setCustomerId(boBusinessOpp.getEndCustomerId());
				soOrderInfo.setCustomerChName(boBusinessOpp.getEndCustomerName());
			} else {

				soOrderInfo.setCustomerId(boBusinessOpp.getAgentId());
				soOrderInfo.setCustomerChName(boBusinessOpp.getAgentName());
			}
			
			// 取得商机明细
			BoBusinessOppDtl searchBoBusinessOppDtl = new BoBusinessOppDtl();
			searchBoBusinessOppDtl.setDelFlag(CommonConstants.NO);
			searchBoBusinessOppDtl.setBusinessOppNo(boBusinessOpp.getBusinessOppNo());
			
			List<BoBusinessOppDtl> boBusinessOppDtlList = boBusinessOppDtlDao.findList(searchBoBusinessOppDtl);
			
			List<SoOrderDtl> soOrderDtlList = new ArrayList<SoOrderDtl>();
			SoOrderDtl soOrderDtl = null;

			String endCustomerId = boBusinessOpp.getEndCustomerId();
			String customerChName = null;
			String industry = null;
			String province = null;	// 省市
			String provinceName = null;	// 省市
			String city = null;	// 城市
			String cityName = null;	// 城市
			String region = null;	// 地区
			String customerDiff = null;	//具体分类
			// 取得客户信息
			CmCustomerInfo customerinfo = getCustomerInfo(boBusinessOpp.getEndCustomerId());
			if (customerinfo != null) {
				customerChName = customerinfo.getCustomerChName();
				industry = customerinfo.getIndustry();
				province = customerinfo.getProvince();
				provinceName = customerinfo.getProvinceName();
				city = customerinfo.getCity();
				cityName = customerinfo.getCityName();
				region = customerinfo.getRegion();
				customerDiff = customerinfo.getCustomerDiff();
			}

			String responsiblePersonId = boBusinessOpp.getResponsiblePersonId();
			String commissionPeisonId = boBusinessOpp.getResponsiblePersonId();
			String responsiblePersonName = null;
			String commissionPeisonName = null;
			String organize = null;
			String organizeName = null;
			// 取得负责人信息
			EmployeeInfo employeeInfo = getEmployeeInfo(boBusinessOpp.getResponsiblePersonId());
			if (employeeInfo != null) {

				commissionPeisonName = employeeInfo.getEmployeeName();
				responsiblePersonName = employeeInfo.getEmployeeName();
				organize = employeeInfo.getOfficeId();
				organizeName = employeeInfo.getOfficeName();
			}

			for (BoBusinessOppDtl boBusinessOppDtl : boBusinessOppDtlList) {

				List<SoOrderDtl> packageSoOrderDtlList = new ArrayList<SoOrderDtl>();
				
				soOrderDtl = new SoOrderDtl();
				
				// 最终客户
				soOrderDtl.setEndCustomerId(endCustomerId);
				soOrderDtl.setCustomerChName(customerChName);
				// 行业
				soOrderDtl.setIndustry(industry);
				// 具体分类
				soOrderDtl.setCustomerDiff(customerDiff);
				// 科室/系
				soOrderDtl.setCustomerSegmentation(boBusinessOpp.getCustomerSegmentation());
				// 省市
				soOrderDtl.setProvince(province);
				// 省市名
				soOrderDtl.setProvinceName(provinceName);
				// 城市
				soOrderDtl.setCity(city);
				// 城市名
				soOrderDtl.setCityName(cityName);
				// 地区
				soOrderDtl.setRegion(region);
				// 负责人
				soOrderDtl.setResponsiblePersonId(responsiblePersonId);
				soOrderDtl.setResponsiblePersonName(responsiblePersonName);
				// 提成人
				soOrderDtl.setCommissionPeisonId(commissionPeisonId);
				soOrderDtl.setCommissionPeisonName(commissionPeisonName);
				// 组别
				soOrderDtl.setOrganize(organize);
				soOrderDtl.setOrganizeName(organizeName);
				// 是否有效
				soOrderDtl.setIfEffective(CommonConstants.NO);
				// 是否退货
				soOrderDtl.setIfReturn(CommonConstants.NO);
				
				// 取得物料信息
				SmMatInfo searchSmMatInfo =  new SmMatInfo();
				searchSmMatInfo.setDelFlag(CommonConstants.NO);
				searchSmMatInfo.setMaterialNo(boBusinessOppDtl.getMaterialNo());
				SmMatInfo smMatInfo = smMatInfoDao.findByMaterialAndType(searchSmMatInfo);
				if (smMatInfo != null) {
					
					if (StringUtils.equals(smMatInfo.getMaterialType(), CommonConstants.MATERIAL_TYPE_3)) {

						List<SmPackageInfoEntity> smPackageInfos = commonService.getSmPackageInfoByMaterialNo(smMatInfo.getMaterialNo());
						
						int index = 0;
						for (SmPackageInfoEntity smPackageInfoEntity : smPackageInfos) {
							
							SoOrderDtl packageSoOrderDtl = SerializationUtils.clone(soOrderDtl);

							// 物料号
							packageSoOrderDtl.setMaterialNo(smPackageInfoEntity.getMaterialNo());
							// 物料号
							packageSoOrderDtl.setMaterialName(smPackageInfoEntity.getMaterialName() + " " + smPackageInfoEntity.getModel());
							// 物料型号
							packageSoOrderDtl.setModel(smPackageInfoEntity.getModel());
							// 物料类型
							packageSoOrderDtl.setMaterialType(smPackageInfoEntity.getMaterialType());
							// 套餐物料
							packageSoOrderDtl.setPackageMertiralNo(smMatInfo.getMaterialNo());
							// 数量
							packageSoOrderDtl.setNum(boBusinessOppDtl.getNum());
							if (index == 0) {

								// 标准价
								packageSoOrderDtl.setStandardPrice(boBusinessOppDtl.getStandardPrice());
								// 单价
								packageSoOrderDtl.setUnitPrice(boBusinessOppDtl.getUnitPrice());
								// 总金额
								packageSoOrderDtl.setTotalAmount(boBusinessOppDtl.getTotalAmount());
							} else {

								// 标准价
								packageSoOrderDtl.setStandardPrice("0");
								// 单价
								packageSoOrderDtl.setUnitPrice("0");
								// 总金额
								packageSoOrderDtl.setTotalAmount("0");
							}
							// 质保期 : 单位-年
							packageSoOrderDtl.setWarrantyPeriod("1");
							
							index++;
							
							packageSoOrderDtlList.add(packageSoOrderDtl);
						}
						
					} else {

						// 物料号
						soOrderDtl.setMaterialNo(smMatInfo.getMaterialNo());
						// 物料号
						soOrderDtl.setMaterialName(smMatInfo.getMaterialName() + " " + smMatInfo.getModel());
						// 物料型号
						soOrderDtl.setModel(smMatInfo.getModel());
						// 物料类型
						soOrderDtl.setMaterialType(smMatInfo.getMaterialType());
						// 套餐物料
						soOrderDtl.setPackageMertiralNo(null);
						// 数量
						soOrderDtl.setNum(boBusinessOppDtl.getNum());
						// 标准价
						soOrderDtl.setStandardPrice(boBusinessOppDtl.getStandardPrice());
						// 单价
						soOrderDtl.setUnitPrice(boBusinessOppDtl.getUnitPrice());
						// 总金额
						soOrderDtl.setTotalAmount(boBusinessOppDtl.getTotalAmount());
						// 质保期 : 单位-年
						soOrderDtl.setWarrantyPeriod("1");
					}
				}
				
				if (packageSoOrderDtlList != null && packageSoOrderDtlList.size() > 0) {

					soOrderDtlList.addAll(packageSoOrderDtlList);
				} else {

					soOrderDtlList.add(soOrderDtl);
				}
			}
			
			soOrderInfo.setSoOrderDtlList(soOrderDtlList);
		}
		
		return soOrderInfo;
		
	}

	@Transactional(readOnly = false)
	public void saveSoOrderInfo(SoOrder soOrder, String optStattus) {

		// 完成流程任务
		Map<String, Object> vars = Maps.newHashMap();
		// 是否合同中包含血压计
		vars.put("sphygr", ifSphygrForSo(soOrder));
		// 是否特价合同
		vars.put("sale", ifSaleOrder(soOrder));
		// 启动流程
		// 合同申请发起
		if (StringUtils.isBlank(soOrder.getAct().getProcInsId())
				|| (StringUtils.equals(soOrder.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50) && StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10))) {
			// 保存合同信息
			saveOrderInfo(soOrder);
			// 启动流程
			String procInsId = actTaskService.startProcess(ActUtils.PD_SO_ORDER[0],
					ActUtils.PD_SO_ORDER[1], soOrder.getId(),
					CommonConstants.PROCESS_TITLE_SO_ORDER);
			// 完成第一个任务，进入申请中状态
			actTaskService.completeFirstTask(procInsId, null, null, vars);
			
			soOrder.setProcInsId(procInsId);
		} else {
			// 节点审批通过
			if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_20)) {
				// 通过审批
				vars.put("pass", CommonConstants.YES);
				actTaskService.complete(soOrder.getAct().getTaskId(), soOrder
						.getAct().getProcInsId(), soOrder.getAct().getComment(),
						CommonConstants.PROCESS_TITLE_SO_ORDER, vars);
				
				if (actTaskService.getProcIns(soOrder.getAct().getProcInsId()) != null) {
					ActUtils.updateWorkflowStatus(ActUtils.PD_SO_ORDER,
							CommonConstants.WORKFLOW_STATUS_20, soOrder.getId());
				} else {
					// 合同是否有效数据更新
					updateSoOrderDelFlag(soOrder.getId(), optStattus);
					// 合同状态数据更新
					updateSoOrderStatusInfo(soOrder.getOrderNo(), null);
				}
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_30)) {
				// 退回审批
				vars.put("pass", CommonConstants.NO);
				actTaskService.complete(soOrder.getAct().getTaskId(), soOrder
						.getAct().getProcInsId(), soOrder.getAct().getComment(),
						CommonConstants.PROCESS_TITLE_SO_ORDER, vars);
				ActUtils.updateWorkflowStatus(ActUtils.PD_SO_ORDER,
						CommonConstants.WORKFLOW_STATUS_30, soOrder.getId());
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_40)) {
				// 任务撤回
				actTaskService.taskBack(soOrder.getAct().getProcInsId(), vars);
				ActUtils.updateWorkflowStatus(ActUtils.PD_SO_ORDER,
						CommonConstants.WORKFLOW_STATUS_40, soOrder.getId());
			} else if (!StringUtils.equals(soOrder.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50) && StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10)) {
				// 保存合同信息
				saveOrderInfo(soOrder);
				actTaskService.complete(soOrder.getAct().getTaskId(), soOrder
						.getAct().getProcInsId(), null, null, null);
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_70)) {
				// 删除流程实例
				actProcessService.deleteProcIns(soOrder.getAct().getProcInsId(), "删除["
						+ CommonConstants.PROCESS_TITLE_SO_ORDER + "]流程");
				// 删除历史任务实例
				List<HistoricTaskInstance> hisTaskList = historyService
						.createHistoricTaskInstanceQuery()
						.processInstanceId(soOrder.getAct().getProcInsId()).list();
				for (HistoricTaskInstance hisTask : hisTaskList) {
					historyService.deleteHistoricTaskInstance(hisTask.getId());
				}

				// 业务表更新
				updateSoOrderDelFlag(soOrder.getId(), optStattus);
			}
		}
	}
	
	// 是否是特价合同
	public String ifSaleOrder(SoOrder soOrder) {
		
		String sale = CommonConstants.NO;
		
		// 标准价
		BigDecimal standardPrice = null;
		
		// 单价
		BigDecimal unitPrice = null;
		for (SoOrderDtl soOrderDtl : soOrder.getSoOrderDtlList()) {
			
			if (StringUtils.isEmptyNull(soOrderDtl.getStandardPrice())) {
				standardPrice = BigDecimal.ZERO;
			} else {
				standardPrice = new BigDecimal(soOrderDtl.getStandardPrice());
			}
			
			if (StringUtils.isEmptyNull(soOrderDtl.getUnitPrice())) {
				unitPrice = BigDecimal.ZERO;
			} else {
				unitPrice = new BigDecimal(soOrderDtl.getUnitPrice());
			}
			
			if ((standardPrice.equals(BigDecimal.ZERO) && !unitPrice.equals(BigDecimal.ZERO)) || unitPrice.compareTo(standardPrice) < 0) {
				sale = CommonConstants.YES;
				
				return sale;
			}
		}
		
		return sale;
	}

	/**
	 * 合同信息保存
	 */
	@Transactional(readOnly = false)
	public void saveOrderInfo(SoOrder soOrder) {

		// 合同信息保存
		this.saveSoOrder(soOrder);
		// 合同明细保存
		this.saveSoOrderDtl(soOrder);
		// 收款管理保存
//		this.saveSoGatheringInfo(soOrder);
	}
	
	/**
	 * 合同信息保存
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void saveSoOrder(SoOrder soOrder) {
		
		
		if (soOrder.getSoGatheringInfoList() != null && soOrder.getSoGatheringInfoList().size() > 0) {
			// 收款方式
			soOrder.setReceiptType(soOrder.getSoGatheringInfoList().get(0).getGatheringType());
			// 收款备注
			soOrder.setReceiptRemarks(soOrder.getSoGatheringInfoList().get(0).getNewRemarks());
		}
		// 是否有效
		String ifEffective = CommonConstants.YES;
		if (StringUtils.equals(soOrder.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {
			ifEffective = CommonConstants.NO;
		}
		soOrder.setIfEffective(ifEffective);
		
		// 汇率
		if (StringUtils.isEmptyNull(soOrder.getExchangeRate())) {
			soOrder.setExchangeRate(null);
		}
		
		// 佣金
		if (StringUtils.isEmptyNull(soOrder.getCommission())) {
			soOrder.setCommission(null);
		}
		
		if (StringUtils.isEmptyNull(soOrder.getBusinessOppNo())) {
			
			if (!StringUtils.isEmpty(soOrder.getId())) {
				
				this.updateSoOrder(soOrder);
			} else {
				
				soOrder.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_50);
				soOrder.setReceiveStatus("10");
				soOrder.setInvoiceStatus("10");
				soOrder.setDeliverStatus("10");

				soOrder.setDelFlag(CommonConstants.NO);
				
				soOrder.setOrderType(CommonConstants.ORDERE_TYPE_4);
				
				soOrder.preInsert();
				soOrderDao.insert(soOrder);
			}
		} else {

			if (StringUtils.isEmpty(soOrder.getId()) || StringUtils.equals(soOrder.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {

				soOrder.setReceiveStatus("10");
				soOrder.setInvoiceStatus("10");
				soOrder.setDeliverStatus("10");

				if (StringUtils.isEmpty(soOrder.getId())) {

					soOrder.setDelFlag(CommonConstants.NO);
					
					BoBusinessOpp boBusinessOpp = boBusinessOppDao.getInfoByBusinessOppNo(soOrder.getBusinessOppNo());
					
					boBusinessOpp.setIfContractGeneration(CommonConstants.YES);
					boBusinessOpp.preUpdate();
					boBusinessOppDao.update(boBusinessOpp);

				}
				
				if (StringUtils.equals(soOrder.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {

					soOrder.setDelFlag("2");
				}
				
				soOrder.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
				
				soOrder.preInsert();
				soOrderDao.insert(soOrder);
			} else {
				
				soOrder.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);

				soOrder.preUpdate();
				soOrderDao.update(soOrder);
			}
		}
	}
	
	/**
	 * 合同明细信息保存
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void saveSoOrderDtl(SoOrder soOrder) {
		
		if (!StringUtils.equals(soOrder.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50) || StringUtils.isEmptyNull(soOrder.getBusinessOppNo())) {
			soOrderDtlDao.deleteSoOrderDtlByOrderId(soOrder.getId());
		}

		// 行号
		int lineNo = 0;
		
		for (SoOrderDtl soOrderDtl : soOrder.getSoOrderDtlList()) {
			String standardPrice = null;
			
			if (StringUtils.equals(soOrder.getOrderType(), CommonConstants.ORDERE_TYPE_1)
					|| StringUtils.equals(soOrder.getOrderType(), CommonConstants.ORDERE_TYPE_4)) {

				standardPrice = commonService.getStandardPrice(
						soOrder.getPriceSystem(), soOrder.getCustomerId(),
						soOrderDtl.getMaterialNo(), soOrderDtl.getRegion(),
						soOrderDtl.getIndustry());
			}
			// 标准价
			if (StringUtils.isEmptyNull(standardPrice)) {
				soOrderDtl.setStandardPrice(null);
			} else {
				soOrderDtl.setStandardPrice(standardPrice);
			}
			
			// 合同号
			soOrderDtl.setOrderId(soOrder.getId());
			
			if (StringUtils.isEmptyNull(soOrderDtl.getLineNo()) || StringUtils.equals(soOrderDtl.getLineNo(), "0")) {

				String maxLineNo = soOrderDtlDao.getMaxLineNo(soOrder.getId());
				
				if (StringUtils.isEmptyNull(maxLineNo)) {
					lineNo = 0;
				} else {
					lineNo = Integer.parseInt(maxLineNo);
				}
				// 明细行号自增
				lineNo = lineNo + 1;
				// 行号
				soOrderDtl.setLineNo(Integer.toString(lineNo));
			}
			
			// 数量
			if (StringUtils.isEmptyNull(soOrderDtl.getNum())) {
				soOrderDtl.setNum(null);
			}
			// 已发货量
			if (StringUtils.isEmptyNull(soOrderDtl.getDeliverNum())) {
				soOrderDtl.setDeliverNum(null);
			}
			// 单价
			if (StringUtils.isEmptyNull(soOrderDtl.getUnitPrice())) {
				soOrderDtl.setUnitPrice(null);
			}
			// 总金额
			if (StringUtils.isEmptyNull(soOrderDtl.getTotalAmount())) {
				soOrderDtl.setTotalAmount(null);
			}
			// 质保期
			if (StringUtils.isEmptyNull(soOrderDtl.getWarrantyPeriod())) {
				soOrderDtl.setWarrantyPeriod(null);
			}
			// 延保期
			if (StringUtils.isEmptyNull(soOrderDtl.getExtendedWarrPeriod())) {
				soOrderDtl.setExtendedWarrPeriod(null);
			}
			// 已开票数量
			if (StringUtils.isEmptyNull(soOrderDtl.getInvoiceNum())) {
				soOrderDtl.setInvoiceNum(null);
			}

			soOrderDtl.preInsert();
			soOrderDtlDao.insert(soOrderDtl);
		}
	}

	@Transactional(readOnly = false)
	public void saveImInvoiceInfo(SoOrder soOrder, String optStattus) {
		// 完成流程任务
		Map<String, Object> vars = Maps.newHashMap();
		// 是否先开票未收款
		vars.put("deliver", ifDeliver(soOrder));
		// 启动流程
		// 发票申请发起
		if (StringUtils.isBlank(soOrder.getAct().getProcInsId())
				|| (StringUtils.equals(soOrder.getImInvoice().getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50) && StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10))) {
			// 保存发票信息
			saveInvoiceInfo(soOrder);
			// 启动流程
			String procInsId = actTaskService.startIminvoiceProcess(ActUtils.PD_IM_INVOICE[0],
					ActUtils.PD_IM_INVOICE[1], soOrder.getId(), soOrder.getImInvoice().getId(),
					CommonConstants.PROCESS_TITLE_IM_INVOICE);
			// 完成第一个任务，进入申请中状态
			actTaskService.completeFirstTask(procInsId, null, null, vars);

			soOrder.getImInvoice().setProcInsId(procInsId);
		} else {
			// 节点审批通过
			if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_20)) {
				// 通过审批
				vars.put("pass", CommonConstants.YES);
				actTaskService.complete(soOrder.getAct().getTaskId(), soOrder
						.getAct().getProcInsId(), soOrder.getAct().getComment(),
						CommonConstants.PROCESS_TITLE_IM_INVOICE, vars);
				if (actTaskService.getProcIns(soOrder.getAct().getProcInsId()) != null) {

					ActUtils.updateWorkflowStatus(ActUtils.PD_IM_INVOICE,
							CommonConstants.WORKFLOW_STATUS_20, soOrder.getImInvoice().getId());
				} else {

					// 合同状态数据更新
					updateSoOrderStatusInfo(soOrder.getOrderNo(), null);
				}
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_30)) {
				// 退回审批
				vars.put("pass", CommonConstants.NO);
				actTaskService.complete(soOrder.getAct().getTaskId(), soOrder
						.getAct().getProcInsId(), soOrder.getAct().getComment(),
						CommonConstants.PROCESS_TITLE_IM_INVOICE, vars);
				ActUtils.updateWorkflowStatus(ActUtils.PD_IM_INVOICE,
						CommonConstants.WORKFLOW_STATUS_30, soOrder.getImInvoice().getId());
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_40)) {
				// 任务撤回
				actTaskService.taskBack(soOrder.getAct().getProcInsId(), vars);
				ActUtils.updateWorkflowStatus(ActUtils.PD_IM_INVOICE,
						CommonConstants.WORKFLOW_STATUS_40, soOrder.getImInvoice().getId());
			} else if (!StringUtils.equals(soOrder.getImInvoice().getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50) && StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10)) {
				// 再申请
				// 更新
				saveInvoiceInfo(soOrder);
				actTaskService.complete(soOrder.getAct().getTaskId(), soOrder
						.getAct().getProcInsId(), null, null, null);
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_70)) {
				// 删除流程实例
				actProcessService.deleteProcIns(soOrder.getAct().getProcInsId(), "删除["
						+ CommonConstants.PROCESS_TITLE_IM_INVOICE + "]流程");
				// 删除历史任务实例
				List<HistoricTaskInstance> hisTaskList = historyService
						.createHistoricTaskInstanceQuery()
						.processInstanceId(soOrder.getAct().getProcInsId()).list();
				for (HistoricTaskInstance hisTask : hisTaskList) {
					historyService.deleteHistoricTaskInstance(hisTask.getId());
				}

				// 业务表更新
				ImInvoice updImInvoice = imInvoiceDao.get(soOrder.getImInvoice().getId());
				updImInvoice.setDelFlag(CommonConstants.YES);
				updImInvoice.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_70);
				updImInvoice.setProcInsId(null);
				updImInvoice.preUpdate();
				imInvoiceDao.update(updImInvoice);
				updImInvoice.preUpdate();
				imInvoiceDao.delete(updImInvoice);
			}
		}
	}
	
	// 是否先开票未收款
	public String ifDeliver(SoOrder soOrder) {
		
		String deliver = CommonConstants.NO;
		
		if (StringUtils.isEmptyNull(soOrder.getReceiveStatus()) || StringUtils.equals(soOrder.getReceiveStatus(), "10")) {
			
			deliver = CommonConstants.YES;
		}
		
		return deliver;
	}

	/**
	 * 发票信息保存
	 */
	@Transactional(readOnly = false)
	public void saveInvoiceInfo(SoOrder soOrder) {

		// 发票管理保存
		this.saveImInvoice(soOrder);
		// 发票明细保存
		this.saveImInvoiceDtl(soOrder);
		// 收款管理保存
//		this.saveSoGatheringInfo(soOrder);
	}
	
	/**
	 * 发票信息保存
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void saveImInvoice(SoOrder soOrder) {
		
		ImInvoice imInvoice = soOrder.getImInvoice();
		
		if (StringUtils.isEmpty(imInvoice.getId()) || StringUtils.equals(imInvoice.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {
			
			imInvoice.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);

			// 合同号
			imInvoice.setOrderNo(soOrder.getOrderNo());

			imInvoice.setDelFlag(CommonConstants.NO);
			
			imInvoice.preInsert();
			imInvoiceDao.insert(imInvoice);
		} else {
			
			imInvoice.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
			imInvoice.preUpdate();
			imInvoiceDao.update(imInvoice);
		}
	}
	
	/**
	 * 发票明细保存
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void saveImInvoiceDtl(SoOrder soOrder) {
		if (!StringUtils.equals(soOrder.getImInvoice().getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {
			imInvoiceDao.deleteImInvoiceDtlByAppId(soOrder.getImInvoice().getId());
		}

		// 行号
		int lineNo = 0;
		BigDecimal invoicAmount = BigDecimal.ZERO;
		for (ImInvoiceDtl imInvoiceDtl : soOrder.getImInvoiceDtlList()) {

			if (!StringUtils.equals(imInvoiceDtl.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {
				
				// 申请ID
				imInvoiceDtl.setAppId(soOrder.getImInvoice().getId());
				
				// 合同号
				imInvoiceDtl.setOrderNo(soOrder.getOrderNo());
				
				// 明细行号自增
				lineNo = lineNo + 1;
				invoicAmount = invoicAmount.add(new BigDecimal(imInvoiceDtl.getInvoiceAmount()));
				
				// 发票来源
				imInvoiceDtl.setInvoiceSource("10");
	
				// 发票行号
				imInvoiceDtl.setLineNo2(Integer.toString(lineNo));
				// 开票类型
				// 是否红票
				BigDecimal invoiceAmount = new BigDecimal(imInvoiceDtl.getInvoiceAmount());
				if (invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {
					imInvoiceDtl.setIfRed(CommonConstants.YES);
				} else {
					imInvoiceDtl.setIfRed(CommonConstants.NO);
				}
				
				// 税金
				if (StringUtils.isEmptyNull(imInvoiceDtl.getTax())) {
					imInvoiceDtl.setTax(null);
				}
				// 开票金额
				if (StringUtils.isEmptyNull(imInvoiceDtl.getInvoiceAmount())) {
					imInvoiceDtl.setInvoiceAmount(null);
				}
				// 数量
				if (StringUtils.isEmptyNull(imInvoiceDtl.getNum())) {
					imInvoiceDtl.setNum(null);
				}
				
				// 发票类型
				imInvoiceDtl.setInvoiceType(soOrder.getImInvoice().getInvoiceType());

				imInvoiceDtl.preInsert();
				imInvoiceDao.insertImInvoiceDtl(imInvoiceDtl);
			} else {
				ImInvoiceDtl updImInvoiceDtl = imInvoiceDao.getImInvoiceDtlById(imInvoiceDtl.getId());

				// 税金
				if (StringUtils.isEmptyNull(imInvoiceDtl.getTax())) {
					updImInvoiceDtl.setTax(null);
				} else {
					updImInvoiceDtl.setTax(imInvoiceDtl.getTax());
				}
				// 发票类型
				updImInvoiceDtl.setInvoiceType(soOrder.getImInvoice().getInvoiceType());
				
				// 开票日期
				updImInvoiceDtl.setInvoiceDate(imInvoiceDtl.getInvoiceDate());
				// 发票号码
				updImInvoiceDtl.setInvoiceNo(imInvoiceDtl.getInvoiceNo());
				// 开票抬头
				updImInvoiceDtl.setInvoiceTitle(imInvoiceDtl.getInvoiceTitle());
				// 快递编号
				updImInvoiceDtl.setExpressNo(imInvoiceDtl.getExpressNo());
				// 快递公司
				updImInvoiceDtl.setExpressCompany(imInvoiceDtl.getExpressCompany());
				
				updImInvoiceDtl.preUpdate();
				imInvoiceDao.updateImInvoiceDtl(updImInvoiceDtl);
			}
		}
	}
	
	/**
	 * 收款明细保存
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void saveSoGatheringInfo(SoOrder soOrder) {
		
		soGatheringInfoDao.deleteSoGatheringInfoByOrderNo(soOrder.getOrderNo());

		// 行号
		int lineNo = 0;
		
		for (SoGatheringInfo gatheringInfo : soOrder.getSoGatheringInfoList()) {
			// 明细行号自增
			lineNo = lineNo + 1;
			
			// 合同号
			gatheringInfo.setOrderNo(soOrder.getOrderNo());
			// 行号
			gatheringInfo.setLineNo(Integer.toString(lineNo));
			// 总金额
			if (StringUtils.isEmptyNull(gatheringInfo.getTotalAmount())) {
				gatheringInfo.setTotalAmount(null);
			}

			gatheringInfo.preInsert();
			soGatheringInfoDao.insert(gatheringInfo);
		}
		// 合同状态数据更新
		this.updateSoOrderStatusInfo(soOrder.getOrderNo(), "gathering");
	}
	
	/**
	 * 合同是否有效信息更新
	 * 
	 * @param orderId 合同Id
	 */
	@Transactional(readOnly = false)
	public void updateSoOrderDelFlag(String orderId, String optStattus) {
		
		// 当前数据信息
		SoOrder nowSoOrder = soOrderDao.get(orderId);

		if (nowSoOrder != null) {

			SoOrder oldSoOrder = soOrderDao.getEffectiveInfoByOrderNo(nowSoOrder.getOrderNo());
			
			if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_70)) {
				nowSoOrder.setDelFlag(CommonConstants.YES);
				nowSoOrder.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_70);
				nowSoOrder.setProcInsId(null);
				
				if (oldSoOrder != null && StringUtils.equals(orderId, oldSoOrder.getId())) {
					BoBusinessOpp boBusinessOpp = boBusinessOppDao.getInfoByBusinessOppNo(nowSoOrder.getBusinessOppNo());
					
					boBusinessOpp.setIfContractGeneration(CommonConstants.NO);
					boBusinessOpp.preUpdate();
					boBusinessOppDao.update(boBusinessOpp);
				}
			} else {

				// 更新旧数据为无效数据
				if (oldSoOrder != null) {
					oldSoOrder.setDelFlag(CommonConstants.YES);
					oldSoOrder.setIfEffective(CommonConstants.NO);
					oldSoOrder.preUpdate();
					soOrderDao.updateDelFlag(oldSoOrder);
				}

				// 更新当前数据为有效数据
				nowSoOrder.setDelFlag(CommonConstants.NO);
				if (oldSoOrder != null) {
					nowSoOrder.setIfEffective(CommonConstants.YES);
				}
			}
			
			nowSoOrder.preUpdate();
			soOrderDao.updateDelFlag(nowSoOrder);
		}
	}
	
	/**
	 * 合同状态信息更新
	 * 
	 * @param orderNo 合同号
	 * @param updType 更新类型
	 */
	@Transactional(readOnly = false)
	public void updateSoOrderStatusInfo(String orderNo, String updType) {
		
		List<SoGatheringInfo> soGatheringInfoList = soGatheringInfoDao.getSoGatheringInfoList(orderNo);
		// 收款明细行金额
		BigDecimal gatheringAmount = BigDecimal.ZERO;
		
		for (SoGatheringInfo gatheringInfo : soGatheringInfoList) {

			if (gatheringInfo.getActDateFrom() != null) {

				gatheringAmount = gatheringAmount.add(new BigDecimal(gatheringInfo.getTotalAmount()));
			}
		}

		BigDecimal invoicAmount = BigDecimal.ZERO;
		Map<String, Integer> invoiceNum = new HashMap<String, Integer>();
		if (updType == null) {

			List<ImInvoiceDtl> imInvoiceDtlList = imInvoiceDao.getImInvoiceDtlListByOrderNo(orderNo);
			for (ImInvoiceDtl imInvoiceDtl : imInvoiceDtlList) {
				
				if (invoiceNum.containsKey(imInvoiceDtl.getLineNo())) {
					
					invoiceNum.put(imInvoiceDtl.getLineNo(), invoiceNum.get(imInvoiceDtl.getLineNo()) + Integer.parseInt(imInvoiceDtl.getNum()));
				} else {
					invoiceNum.put(imInvoiceDtl.getLineNo(), Integer.parseInt(imInvoiceDtl.getNum()));
				}

				invoicAmount = invoicAmount.add(new BigDecimal(imInvoiceDtl.getInvoiceAmount()));
			}
		}
		
		SoOrder updSoOrder = soOrderDao.getEffectiveInfoByOrderNo(orderNo);
		if (updSoOrder != null) {

			// 发货状态(发货申请时更新)
			int num = 0;
			int deliverNum = 0;
			// 合同明细行金额
			BigDecimal orderAmount = BigDecimal.ZERO;
			List<SoOrderDtl> soOrderDtlList = soOrderDtlDao.getSoOrderDtlList(updSoOrder.getId());
			// 收款状态
			for (SoOrderDtl soOrderDtl : soOrderDtlList) {
				if (StringUtils.equals(soOrderDtl.getIfEffective(), CommonConstants.NO)) {

					orderAmount = orderAmount.add(new BigDecimal(soOrderDtl.getTotalAmount()));

					if (updType == null) {
						num = num + Integer.parseInt(soOrderDtl.getNum());
						if (!StringUtils.isEmptyNull(soOrderDtl.getDeliverNum())) {
							deliverNum = deliverNum + Integer.parseInt(soOrderDtl.getDeliverNum());
						}
					}
				}
				if (updType == null) {
					if (!invoiceNum.isEmpty()) {
						
						if (invoiceNum.containsKey(soOrderDtl.getLineNo())) {
							
//							if (!StringUtils.isEmptyNull(soOrderDtl.getPackageMertiralNo())) {
//								int lineNo = Integer.parseInt(soOrderDtl.getLineNo());
//								
//								List<SmPackageInfoEntity> smPackageInfos = commonService.getSmPackageInfoByMaterialNo(soOrderDtl.getPackageMertiralNo());
//								
//								SoOrderDtl searchSoOrderDtlInfo = new SoOrderDtl();
//								searchSoOrderDtlInfo.setOrderId(soOrderDtl.getOrderId());
//								for (SmPackageInfoEntity smPackageInfo : smPackageInfos) {
//
//									searchSoOrderDtlInfo.setLineNo(Integer.toString(lineNo));
//									searchSoOrderDtlInfo.setMaterialNo(smPackageInfo.getMaterialNo());
//									searchSoOrderDtlInfo.setPackageMertiralNo(soOrderDtl.getPackageMertiralNo());
//
//									SoOrderDtl soOrderDtlInfo = soOrderDtlDao.getSoOrderDtlInfo(searchSoOrderDtlInfo);
//									
//									if (soOrderDtlInfo != null) {
//										soOrderDtlInfo.setInvoiceNum(invoiceNum.get(soOrderDtl.getLineNo()).toString());
//										
//										soOrderDtlInfo.preUpdate();
//										soOrderDtlDao.update(soOrderDtlInfo);
//									}
//
//									lineNo = lineNo + 1;
//								}
//							} else {
								
								soOrderDtl.setInvoiceNum(invoiceNum.get(soOrderDtl.getLineNo()).toString());
		
								soOrderDtl.preUpdate();
								soOrderDtlDao.update(soOrderDtl);
//							}
						}
					}
				}
			}
			
			// 已收款(30)：无作废标识的合同行金额=收款管理合计金额
			// 部分收款(20)：无作废标识的合同行金额>收款管理合计金额（不为0）
			// 未收款(10)：收款管理合计金额为0，且无作废标识的合同行金额不为0
			if (gatheringAmount.compareTo(BigDecimal.ZERO) == 0 && orderAmount.compareTo(BigDecimal.ZERO) > 0) {
				updSoOrder.setReceiveStatus("10");
			} else if (gatheringAmount.compareTo(BigDecimal.ZERO) != 0 && orderAmount.compareTo(gatheringAmount) > 0) {
				updSoOrder.setReceiveStatus("20");
			} else if (orderAmount.compareTo(gatheringAmount) == 0) {
				updSoOrder.setReceiveStatus("30");
			}

			if (updType == null) {

				// 已开票(30)：无作废标识的合同行金额=收款管理合计金额
				// 部分开票(20)：无作废标识的合同行金额>收款管理合计金额（不为0）
				// 未开票(10)：收款管理合计金额为0，且无作废标识的合同行金额不为0
				if (invoicAmount.compareTo(BigDecimal.ZERO) == 0 && orderAmount.compareTo(BigDecimal.ZERO) > 0) {
					updSoOrder.setInvoiceStatus("10");
				} else if (invoicAmount.compareTo(BigDecimal.ZERO) != 0 && orderAmount.compareTo(invoicAmount) > 0) {
					updSoOrder.setInvoiceStatus("20");
				} else if (orderAmount.compareTo(invoicAmount) == 0) {
					updSoOrder.setInvoiceStatus("30");
				}
				
				// 发货信息
				if (num == deliverNum) {
					updSoOrder.setDeliverStatus("30");
				} else if (num > deliverNum && deliverNum != 0) {
					updSoOrder.setDeliverStatus("20");
				} else if (num != 0 && deliverNum == 0) {
					updSoOrder.setDeliverStatus("10");
				}
			}
			
			updSoOrder.preUpdate();
			soOrderDao.update(updSoOrder);
		}
	}

	/**
	 * 合同以及发票整合性验证
	 */
	public String soOrderInfoValidator(SoOrder soOrder, String valType) {
		
		List<String> message = new ArrayList<String>();
		
		if (valType == null) {
			if (!StringUtils.isEmptyNull(soOrder.getId())) {
				
				SoOrder updSoOrder = soOrderDao.get(soOrder.getId());
				
				if (DateUtils.compareDate(updSoOrder.getUpdateDate(), soOrder.getUpdateDate()) != 0) {
					message.add("合同" + soOrder.getOrderNo() + "信息已经被修改！");
				}
			}
			if (soOrder.getImInvoice() != null && !StringUtils.isEmptyNull(soOrder.getImInvoice().getId())) {
				
				ImInvoice updImInvoice = imInvoiceDao.get(soOrder.getImInvoice().getId());
				
				if (DateUtils.compareDate(updImInvoice.getUpdateDate(), soOrder.getImInvoice().getUpdateDate()) != 0) {
					message.add("合同" + soOrder.getOrderNo() + "的开票信息已经被修改！");
				}
			}
		}
		
		// 合同至少有一条明细
		if (soOrder.getSoOrderDtlList() == null || soOrder.getSoOrderDtlList().size() == 0) {

			message.add("至少需要有一条合同明细信息！");
		} else {
			
			// 合同明细金额
			BigDecimal orderDtlAmount = BigDecimal.ZERO;
			Map<String, BigDecimal> orderDtlLineAmount = new HashMap<String, BigDecimal>();
			for (SoOrderDtl soOrderDtl : soOrder.getSoOrderDtlList()) {
				if (StringUtils.equals(soOrderDtl.getIfEffective(), CommonConstants.NO)) {

//					if (soOrderDtl.getTotalAmount() != null && !StringUtils.equals(soOrderDtl.getLineNo(), "0")) {
					if (!StringUtils.equals(soOrderDtl.getLineNo(), "0")) {

						orderDtlAmount = orderDtlAmount.add(new BigDecimal(soOrderDtl.getTotalAmount()));
						
						int deliverNum = 0;
						int invoiceNum = 0;
//						BigDecimal unitPrice = new BigDecimal(soOrderDtl.getUnitPrice());
						int num = Integer.parseInt(soOrderDtl.getNum());
						
						if (!StringUtils.isEmptyNull(soOrderDtl.getDeliverNum())) {
							deliverNum = Integer.parseInt(soOrderDtl.getDeliverNum());
						}

						if (!StringUtils.isEmptyNull(soOrderDtl.getInvoiceNum())) {
							invoiceNum = Integer.parseInt(soOrderDtl.getInvoiceNum());
						}
//						if (unitPrice.compareTo(BigDecimal.ZERO) > 0 && num < deliverNum) {
						if (num < deliverNum) {
							message.add("合同明细第" + soOrderDtl.getLineNo() + "行的数量小于已发货数量，请确认！");
						}
//						if (unitPrice.compareTo(BigDecimal.ZERO) > 0 && num < invoiceNum) {
						if (num < invoiceNum) {
							message.add("合同明细第" + soOrderDtl.getLineNo() + "行的数量小于已开票数量，请确认！");
						}

						if (valType == null) {

//							if (unitPrice.compareTo(BigDecimal.ZERO) > 0) {

								if (orderDtlLineAmount.containsKey(soOrderDtl.getLineNo())) {
									
									orderDtlLineAmount.put(soOrderDtl.getLineNo(), orderDtlLineAmount.get(soOrderDtl.getLineNo()).add(new BigDecimal(soOrderDtl.getTotalAmount())));
								} else {
									orderDtlLineAmount.put(soOrderDtl.getLineNo(), new BigDecimal(soOrderDtl.getTotalAmount()));
								}
//							}
						}
					}
				}
			}
			
			// 收款金额不能大于合同明细金额
			// 收款明细行金额
			BigDecimal gatheringAmount = BigDecimal.ZERO;
			
			for (SoGatheringInfo gatheringInfo : soOrder.getSoGatheringInfoList()) {

				if (gatheringInfo.getActDateFrom() != null) {

					if (gatheringInfo.getTotalAmount() != null) {

						gatheringAmount = gatheringAmount.add(new BigDecimal(gatheringInfo.getTotalAmount()));
					}
				}
			}
			
			if (gatheringAmount.compareTo(orderDtlAmount) > 0) {

				message.add("收款金额大于合同总金额，请确认！");
			}

			if (valType == null) {

				// 相对行的开票金额不得大于明细金额
				Map<String, BigDecimal> invoiceLineTotalAmount = new HashMap<String, BigDecimal>();
				Map<String, BigDecimal> invoiceLineAmount = new HashMap<String, BigDecimal>();
				Map<String, BigDecimal> invoiceLineRedAmount = new HashMap<String, BigDecimal>();
				for (ImInvoiceDtl imInvoiceDtl : soOrder.getImInvoiceDtlList()) {
					
					if (imInvoiceDtl.getInvoiceAmount() != null) {
						BigDecimal invoiceAmount = new BigDecimal(imInvoiceDtl.getInvoiceAmount());

						if (invoiceAmount.compareTo(BigDecimal.ZERO) > 0) {

							if (invoiceLineAmount.containsKey(imInvoiceDtl.getLineNo())) {
								
								invoiceLineAmount.put(imInvoiceDtl.getLineNo(), invoiceLineAmount.get(imInvoiceDtl.getLineNo()).add(invoiceAmount));
							} else {
								invoiceLineAmount.put(imInvoiceDtl.getLineNo(), invoiceAmount);
							}
						} else if (invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {

							if (invoiceLineRedAmount.containsKey(imInvoiceDtl.getLineNo())) {
								
								invoiceLineRedAmount.put(imInvoiceDtl.getLineNo(), invoiceLineRedAmount.get(imInvoiceDtl.getLineNo()).add(invoiceAmount));
							} else {
								invoiceLineRedAmount.put(imInvoiceDtl.getLineNo(), invoiceAmount);
							}
						}

						if (invoiceLineTotalAmount.containsKey(imInvoiceDtl.getLineNo())) {
							
							invoiceLineTotalAmount.put(imInvoiceDtl.getLineNo(), invoiceLineTotalAmount.get(imInvoiceDtl.getLineNo()).add(invoiceAmount));
						} else {
							invoiceLineTotalAmount.put(imInvoiceDtl.getLineNo(), invoiceAmount);
						}
					}
				}
				
				for (Map.Entry<String, BigDecimal> entry : invoiceLineRedAmount.entrySet()) {

					String lineNo = entry.getKey();
					BigDecimal totalRedAmount = entry.getValue().abs();

					if (invoiceLineAmount.containsKey(lineNo)) {

						BigDecimal invoiceAmount = invoiceLineAmount.get(lineNo);

						if (totalRedAmount.compareTo(invoiceAmount) > 0) {

							message.add("合同明细第" + lineNo + "行的开票冲红总金额大于开票总金额，请确认！");
						}
					} else {
						message.add("合同明细第" + lineNo + "行的开票冲红总金额大于开票总金额，请确认！");
					}
				}
				
//				for (Map.Entry<String, BigDecimal> entry : orderDtlLineAmount.entrySet()) {
//					
//					String lineNo = entry.getKey();
//					BigDecimal totalAmount = entry.getValue();
//					
//					if (invoiceLineTotalAmount.containsKey(lineNo)) {
//
//						BigDecimal invoiceTotalAmount = invoiceLineTotalAmount.get(lineNo);
//						
//						if (invoiceTotalAmount.compareTo(totalAmount) > 0) {
//
//							message.add("合同明细第" + lineNo + "行的开票总金额大于合同金额，请确认！");
//						}
//					}
//				}
				
				for (Map.Entry<String, BigDecimal> entry : invoiceLineTotalAmount.entrySet()) {
					
					String lineNo = entry.getKey();
					BigDecimal invoiceTotalAmount = entry.getValue();
					
					if (orderDtlLineAmount.containsKey(lineNo)) {

						BigDecimal totalAmount = orderDtlLineAmount.get(lineNo);
						
						if (invoiceTotalAmount.compareTo(totalAmount) > 0) {

							message.add("合同明细第" + lineNo + "行的开票总金额大于合同金额，请确认！");
						}
					} else {
						message.add("合同明细第" + lineNo + "行已经被删除，不可以开票。请确认！");
					}
				}
			}
		}
		
		if (valType == null) {

			if (StringUtils.equals(soOrder.getDataType(), "invoice")) {
				
				if (soOrder.getImInvoiceDtlList() == null || soOrder.getImInvoiceDtlList().size() == 0) {
					
					message.add("至少需要有一条开票明细信息！");
				} else {
					
					int applyingNum = 0;
					for (ImInvoiceDtl imInvoiceDtl : soOrder.getImInvoiceDtlList()) {
						if (!StringUtils.isEmptyNull(imInvoiceDtl.getId()) && StringUtils.equals(imInvoiceDtl.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {
							applyingNum++;
						}
					}
					if (applyingNum == soOrder.getImInvoiceDtlList().size()) {
						message.add("至少需要有一条开票明细信息！");
					} else if (applyingNum < soOrder.getImInvoiceDtlList().size()) {

						// 合同申请中，不可以申请开票
						SoOrder applyingSoOrder = soOrderDao.getApplyingInfoByOrderNo(soOrder.getOrderNo());
						
						if (applyingSoOrder != null && !StringUtils.equals(applyingSoOrder.getId(), soOrder.getId())) {
							message.add("存在正在申请中的合同信息！");
						}
					}
				}
			} else if (StringUtils.equals(soOrder.getDataType(), "order")) {

				// 发票申请中，不可以申请合同
				ImInvoice applyingImInvoice = imInvoiceDao.getApplyingImInvoiceByOrderNo(soOrder.getOrderNo());
				
				if (applyingImInvoice != null && !StringUtils.equals(applyingImInvoice.getId(), soOrder.getImInvoice().getId())) {
					message.add("存在正在申请中的发票信息！");
				}

				// 发货申请中，不可以申请合同
				SoApplyDeliver applyingSoApplyDeliver = soApplyDeliverDao.getSoAppDelWorkStatusByOrderNo(soOrder.getOrderNo());
				
				if (applyingSoApplyDeliver != null
						&& !StringUtils.equals(
								applyingSoApplyDeliver.getWorkflowStatus(),
								CommonConstants.WORKFLOW_STATUS_50)
						&& !StringUtils.equals(
								applyingSoApplyDeliver.getWorkflowStatus(),
								CommonConstants.WORKFLOW_STATUS_70)) {
					message.add("存在正在申请中的发货信息！");
				}
			}
		}
		
		StringBuilder sbMessage = new StringBuilder();
		if (message.size() > 0) {
			for (String str : message) {
				int index = 1;
				
				if (index == message.size()) {
					sbMessage.append(str);
				} else {
					sbMessage.append(str).append("<br/>");
				}
				
				index++;
			}
		} else {
			return null;
		}
		
		return sbMessage.toString();
	}

	/**
	 * 合同以及发票信息更新
	 */
	@Transactional(readOnly = false)
	public void updateSoOrderInfo(SoOrder soOrder) {

		if (StringUtils.equals(soOrder.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {

			// 合同信息更新
			this.updateSoOrder(soOrder);
			// 合同明细更新
			this.updateSoOrderDtl(soOrder);
		}

		if (soOrder.getImInvoice() != null && StringUtils.equals(soOrder.getImInvoice().getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {

			// 发票管理更新
			this.updateImInvoice(soOrder);
			// 发票明细更新
			this.updateImInvoiceDtl(soOrder);
		}
		// 收款管理保存
		this.saveSoGatheringInfo(soOrder);
		
		// 合同状态数据更新
		updateSoOrderStatusInfo(soOrder.getOrderNo(), null);
	}
	
	/**
	 * 合同信息更新
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void updateSoOrder(SoOrder soOrder) {
		
		SoOrder updSoOrder = soOrderDao.get(soOrder.getId());
		// 收款方式
		// 收款备注
		if (soOrder.getSoGatheringInfoList() != null && soOrder.getSoGatheringInfoList().size() > 0) {
			updSoOrder.setReceiptType(soOrder.getSoGatheringInfoList().get(0).getGatheringType());
			updSoOrder.setReceiptRemarks(soOrder.getSoGatheringInfoList().get(0).getRemarks());
		}
		// 订立日期
		updSoOrder.setOrderDate(soOrder.getOrderDate());
		// 销售方式
		updSoOrder.setPriceSystem(soOrder.getPriceSystem());
		// 业务员
		updSoOrder.setEmployeeNo(soOrder.getEmployeeNo());
		// 签约地
		updSoOrder.setCity(soOrder.getCity());
		// 签约方
		updSoOrder.setCustomerId(soOrder.getCustomerId());
		// 签约方
		updSoOrder.setCustomerChName(soOrder.getCustomerChName());
		// 合同分类
		if (!StringUtils.isEmptyNull(updSoOrder.getBusinessOppNo())) {
			updSoOrder.setOrderType(soOrder.getOrderType());
		}
		// 币种
		updSoOrder.setCurrency(soOrder.getCurrency());
		// 汇率
		if (StringUtils.isEmptyNull(soOrder.getExchangeRate())) {
			updSoOrder.setExchangeRate(null);
		} else {
			updSoOrder.setExchangeRate(soOrder.getExchangeRate());
		}
		// 佣金/扣除费用
		if (StringUtils.isEmptyNull(soOrder.getCommission())) {
			updSoOrder.setCommission(null);
		} else {
			updSoOrder.setCommission(soOrder.getCommission());
		}
		// 备注
		updSoOrder.setNewRemarks(soOrder.getNewRemarks());
		// 支付条件
		updSoOrder.setPaymaentCon(soOrder.getPaymaentCon());
		// 支付条件备注
		updSoOrder.setConditionRemarks(soOrder.getConditionRemarks());

		updSoOrder.preUpdate();
		soOrderDao.update(updSoOrder);
	}
	
	/**
	 * 合同明细信息更新
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void updateSoOrderDtl(SoOrder soOrder) {
		
		soOrderDtlDao.deleteSoOrderDtlByOrderId(soOrder.getId());

		// 行号
		int lineNo = 0;
		
		for (SoOrderDtl soOrderDtl : soOrder.getSoOrderDtlList()) {

			String standardPrice = null;
			String materialNo = null;
			
			if (StringUtils.isEmptyNull(soOrderDtl.getPackageMertiralNo())) {
				materialNo = soOrderDtl.getMaterialNo();
			} else {
				materialNo = soOrderDtl.getPackageMertiralNo();
			}
			
			if (StringUtils.equals(soOrder.getOrderType(), CommonConstants.ORDERE_TYPE_1)
					|| StringUtils.equals(soOrder.getOrderType(), CommonConstants.ORDERE_TYPE_4)) {

				standardPrice = commonService.getStandardPrice(
//						soOrder.getPriceSystem(), soOrder.getCustomerId(),
						soOrder.getPriceSystem(), soOrderDtl.getEndCustomerId(),
//						soOrderDtl.getMaterialNo(), soOrderDtl.getRegion(),
						materialNo, soOrderDtl.getRegion(),
						soOrderDtl.getIndustry());
			}
			// 标准价
			if (StringUtils.isEmptyNull(standardPrice)) {
				soOrderDtl.setStandardPrice(null);
			} else {
				soOrderDtl.setStandardPrice(standardPrice);
			}
			
			// 合同号
			soOrderDtl.setOrderId(soOrder.getId());
			
			if (StringUtils.equals(soOrderDtl.getLineNo(), "0")) {

				String maxLineNo = soOrderDtlDao.getMaxLineNo(soOrder.getId());
				
				if (StringUtils.isEmptyNull(maxLineNo)) {
					lineNo = 0;
				} else {
					lineNo = Integer.parseInt(maxLineNo);
				}
				// 明细行号自增
				lineNo = lineNo + 1;
				// 行号
				soOrderDtl.setLineNo(Integer.toString(lineNo));
			}
			
			// 数量
			if (StringUtils.isEmptyNull(soOrderDtl.getNum())) {
				soOrderDtl.setNum(null);
			}
			// 已发货量
			if (StringUtils.isEmptyNull(soOrderDtl.getDeliverNum())) {
				soOrderDtl.setDeliverNum(null);
			}
			// 单价
			if (StringUtils.isEmptyNull(soOrderDtl.getUnitPrice())) {
				soOrderDtl.setUnitPrice(null);
			}
			// 总金额
			if (StringUtils.isEmptyNull(soOrderDtl.getTotalAmount())) {
				soOrderDtl.setTotalAmount(null);
			}
			// 质保期
			if (StringUtils.isEmptyNull(soOrderDtl.getWarrantyPeriod())) {
				soOrderDtl.setWarrantyPeriod(null);
			}
			// 延保期
			if (StringUtils.isEmptyNull(soOrderDtl.getExtendedWarrPeriod())) {
				soOrderDtl.setExtendedWarrPeriod(null);
			}
			// 已开票数量
			if (StringUtils.isEmptyNull(soOrderDtl.getInvoiceNum())) {
				soOrderDtl.setInvoiceNum(null);
			}

			soOrderDtl.preInsert();
			soOrderDtlDao.insert(soOrderDtl);
		}
	}
	
	/**
	 * 发票信息更新 
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void updateImInvoice(SoOrder soOrder) {
		
		ImInvoice imInvoice = soOrder.getImInvoice();
		
		ImInvoice updImInvoice = imInvoiceDao.get(imInvoice.getId());
		
		// 取票方式
		updImInvoice.setTicketMethod(imInvoice.getTicketMethod());
		// 开票抬头
		updImInvoice.setInvoiceTitle(imInvoice.getInvoiceTitle());
		// 纳税人识别号
		updImInvoice.setTaxpayerIdentNo(imInvoice.getTaxpayerIdentNo());
		// 开户行
		updImInvoice.setDepositBank(imInvoice.getDepositBank());
		// 银行账号
		updImInvoice.setBankAccount(imInvoice.getBankAccount());
		// 开票地址
		updImInvoice.setInvoiceAddress(imInvoice.getInvoiceAddress());
		// 电话
		updImInvoice.setTelephone(imInvoice.getTelephone());
		// 备注说明
		updImInvoice.setNewRemarks(imInvoice.getNewRemarks());
		// 收件人
		updImInvoice.setRecipients(imInvoice.getRecipients());
		// 收件电话
		updImInvoice.setRepTelephone(imInvoice.getRepTelephone());
		// 地址
		updImInvoice.setAddress(imInvoice.getAddress());
		
		updImInvoice.preUpdate();
		imInvoiceDao.update(updImInvoice);
	}
	
	/**
	 * 发票明细更新
	 * 
	 * @param soOrder
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void updateImInvoiceDtl(SoOrder soOrder) {
		if (!StringUtils.equals(soOrder.getImInvoice().getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {

			imInvoiceDao.deleteImInvoiceDtlByAppId(soOrder.getImInvoice().getId());
		}

		// 行号
		int lineNo = 0;
		BigDecimal invoicAmount = BigDecimal.ZERO;
		for (ImInvoiceDtl imInvoiceDtl : soOrder.getImInvoiceDtlList()) {
			
			// 申请ID
			imInvoiceDtl.setAppId(soOrder.getImInvoice().getId());
			
			// 合同号
			imInvoiceDtl.setOrderNo(soOrder.getOrderNo());
			
			// 明细行号自增
			lineNo = lineNo + 1;
			invoicAmount = invoicAmount.add(new BigDecimal(imInvoiceDtl.getInvoiceAmount()));
			
			// 发票来源
			imInvoiceDtl.setInvoiceSource("10");

			// 发票行号
			imInvoiceDtl.setLineNo2(Integer.toString(lineNo));
			// 开票类型
			// 是否红票
			BigDecimal invoiceAmount = new BigDecimal(imInvoiceDtl.getInvoiceAmount());
			if (invoiceAmount.compareTo(BigDecimal.ZERO) < 0) {
				imInvoiceDtl.setIfRed(CommonConstants.YES);
			} else {
				imInvoiceDtl.setIfRed(CommonConstants.NO);
			}
			
			// 税金
			if (StringUtils.isEmptyNull(imInvoiceDtl.getTax())) {
				imInvoiceDtl.setTax(null);
			}
			// 开票金额
			if (StringUtils.isEmptyNull(imInvoiceDtl.getInvoiceAmount())) {
				imInvoiceDtl.setInvoiceAmount(null);
			}
			// 数量
			if (StringUtils.isEmptyNull(imInvoiceDtl.getNum())) {
				imInvoiceDtl.setNum(null);
			}
			
			// 发票类型
			imInvoiceDtl.setInvoiceType(soOrder.getImInvoice().getInvoiceType());

			if (!StringUtils.equals(imInvoiceDtl.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50)) {

				imInvoiceDtl.preInsert();
				imInvoiceDao.insertImInvoiceDtl(imInvoiceDtl);
			} else {

				ImInvoiceDtl updImInvoiceDtl = imInvoiceDao.getImInvoiceDtlById(imInvoiceDtl.getId());

				// 税金
				if (StringUtils.isEmptyNull(imInvoiceDtl.getTax())) {
					updImInvoiceDtl.setTax(null);
				} else {
					updImInvoiceDtl.setTax(imInvoiceDtl.getTax());
				}
				// 发票类型
				updImInvoiceDtl.setInvoiceType(soOrder.getImInvoice().getInvoiceType());
				
				// 开票日期
				updImInvoiceDtl.setInvoiceDate(imInvoiceDtl.getInvoiceDate());
				// 发票号码
				updImInvoiceDtl.setInvoiceNo(imInvoiceDtl.getInvoiceNo());
				// 开票抬头
				updImInvoiceDtl.setInvoiceTitle(imInvoiceDtl.getInvoiceTitle());
				// 快递编号
				updImInvoiceDtl.setExpressNo(imInvoiceDtl.getExpressNo());
				// 快递公司
				updImInvoiceDtl.setExpressCompany(imInvoiceDtl.getExpressCompany());
				
				updImInvoiceDtl.preUpdate();
				imInvoiceDao.updateImInvoiceDtl(updImInvoiceDtl);
			
			}
		}
	}
	
	// 导出合同明细行
	public List<SoOrderDtlExcel> exportOrderDtl(String orderId) {

		List<SoOrderDtl> soOrderDtlList = soOrderDtlDao.getSoOrderDtlList(orderId);

		SoOrderDtlExcel soOrderDtlExcel = null;
		List<SoOrderDtlExcel> soOrderDtlExcelList = Lists.newArrayList();
		BigDecimal total = BigDecimal.ZERO;
		int index = 1;
		NumberFormat nf = new DecimalFormat("#,##0.00");
		for (SoOrderDtl soOrderDtl : soOrderDtlList) {
			soOrderDtlExcel = new SoOrderDtlExcel();

			soOrderDtlExcel.setIndex(Integer.toString(index++));
			soOrderDtlExcel.setMaterialName(soOrderDtl.getMaterialName());
			soOrderDtlExcel.setModel(soOrderDtl.getModel());;
			soOrderDtlExcel.setNum(soOrderDtl.getNum());
			soOrderDtlExcel.setUnitPrice(nf.format(new BigDecimal(soOrderDtl.getUnitPrice())));
			soOrderDtlExcel.setTotalAmount(nf.format(new BigDecimal(soOrderDtl.getTotalAmount())));
			
			total = total.add(new BigDecimal(soOrderDtl.getTotalAmount()));

			soOrderDtlExcelList.add(soOrderDtlExcel);
		}
		soOrderDtlExcel = new SoOrderDtlExcel();
		soOrderDtlExcel.setTotal("合计（含税）：" + nf.format(total));
		soOrderDtlExcel.setUcTotal("（合计大写：" + NumberToCNUtils.number2CNMontrayUnit(total) + "）");
		soOrderDtlExcelList.add(index-1, soOrderDtlExcel);

		return soOrderDtlExcelList;
	}
	
	// 导出应收账款一览
	public List<ReceiveExcel> exportReceive() {

		ReceiveExcel receiveExcel = null;
		List<ReceiveExcel> receiveExcelList = Lists.newArrayList();
		
		// 来源：销售合同
		// 合同：抽取所有收款状态不等于已收款，且，开票=已开票或者部分开票或者发货状态=已发货或者部分发货合同
		List<SoOrder> soOrderList = soOrderDao.getReSoOrderInfo();
		
		for (SoOrder reSoOrder : soOrderList) {
			
			// 取得合同明细信息
			// 型号：合同明细，多行用","分隔
			List<String> models = new ArrayList<String>();
			// 合同总金额：合同明细行金额汇总（去掉作废）
			BigDecimal dtlTotalAmount = BigDecimal.ZERO;

			List<SoOrderDtl> soOrderDtlList = soOrderDtlDao.getSoOrderDtlList(reSoOrder.getId());
			for (SoOrderDtl reSoOrderDtl : soOrderDtlList) {
				
				if (!StringUtils.isEmptyNull(StringUtils.trim(reSoOrderDtl.getModel()))) {

					models.add(reSoOrderDtl.getModel());
				}
				
				if (StringUtils.equals(reSoOrderDtl.getIfEffective(), CommonConstants.NO)) {
					
					dtlTotalAmount = dtlTotalAmount.add(new BigDecimal(reSoOrderDtl.getTotalAmount()));
				}
			}
			
			StringBuilder sbModels = new StringBuilder();
			if (models.size() > 0) {
				int index = 1;
				for (String str : models) {
					
					if (index == models.size()) {
						sbModels.append(str);
					} else {
						sbModels.append(str).append("，");
					}
					
					index++;
				}
			}
			
			// 发货日期：合同出库明细中最大发货日期
			String maxProcessDate = soOrderDao.getMaxStorageProcessDate(reSoOrder.getOrderNo());

			// 验收日期：合同出库明细中最大安装日期
			String maxInstallDate = soOrderDao.getMaxInstallDate(reSoOrder.getOrderNo());

			// 以收款中的明细行为单位，收款行中，实际收款日期未填写的行
			List<SoGatheringInfo> soGatheringInfoList = soGatheringInfoDao.getReSoGatheringInfo(reSoOrder.getOrderNo());
			
			for (SoGatheringInfo soGatheringInfo : soGatheringInfoList) {
				
				receiveExcel = new ReceiveExcel();
				// 合同号：合同编号
				receiveExcel.setOrderNo(reSoOrder.getOrderNo());
				// 类型：“销售合同”或"其他"
				if (StringUtils.isEmptyNull(reSoOrder.getBusinessOppNo())) {

					receiveExcel.setType("其他");
				} else {

					receiveExcel.setType("销售合同");
				}
				// 客户名：签约方
				receiveExcel.setCustomerName(reSoOrder.getCustomerChName());
				// 年度：订立日期对应的年
				receiveExcel.setYear(DateUtils.formatDate(reSoOrder.getOrderDate(), "yyyy"));
				// 发票：合同头发票状态
				receiveExcel.setInvoiceStatus(reSoOrder.getInvoiceStatus());
				// 型号：合同明细，多行用","分隔
//				receiveExcel.setModel(models.toString());
				receiveExcel.setModel(sbModels.toString());
				// 合同总金额：合同明细行金额汇总（去掉作废）
				receiveExcel.setTotalAmount(dtlTotalAmount.toString());
				// 应收款总金额：收款明细行的收款金额
				receiveExcel.setReceiveTotalAmount(soGatheringInfo.getTotalAmount());
				// 发货日期：合同出库明细中最大发货日期
				receiveExcel.setDeliveryDate(maxProcessDate);

				if (soGatheringInfo.getExpDateFrom() != null) {

					// 正常结账日期：收款明细到期日
					receiveExcel.setActDate(DateUtils.formatDate(soGatheringInfo.getExpDateFrom(), "yyyy-MM-dd"));
					// 账龄：系统日期-正常结账日期
					receiveExcel.setAging(String.valueOf(DateUtils.getDistanceOfTwoDate(soGatheringInfo.getExpDateFrom() ,new Date())));
				}
				// 验收日期：合同出库明细中最大安装日期
				receiveExcel.setProcessDate(maxInstallDate);
				// 付款方式：收款明细行的收款金额
				receiveExcel.setGatheringType(soGatheringInfo.getGatheringType());
				// 业务员：合同的业务员
				receiveExcel.setEmployeeName(reSoOrder.getEmployeeName());
				
				receiveExcelList.add(receiveExcel);
			}
		}
		
		// 报价单：抽取所有收款状态不等于已收款的数据，且维修信息表处理状态=处理完毕数据
		List<SoRmRepairInfo> reRmRepairInfoList = soOrderDao.getReRmRepairInfoList();
		
		for (SoRmRepairInfo rmRepairInfo : reRmRepairInfoList) {
			
			receiveExcel = new ReceiveExcel();
			
			// 取得报价单明细行信息
			// 型号：报价单明细，多行用“,”分隔
			List<String> models = new ArrayList<String>();
			// 合同总金额：报价单明细行汇总
			BigDecimal dtlTotalAmount = BigDecimal.ZERO;
			 
			List<SoRmQuotationDtl> rmQuotationDtlList = soOrderDao.getReRmQuotationDtl(rmRepairInfo.getQuotationNo());
			for (SoRmQuotationDtl rmQuotationDtl : rmQuotationDtlList) {
				
				if (!StringUtils.isEmptyNull(StringUtils.trim(rmQuotationDtl.getModel()))) {

					models.add(rmQuotationDtl.getModel());
				}
				
				dtlTotalAmount = dtlTotalAmount.add(rmQuotationDtl.getActAmount());
			}
			
			StringBuilder sbModels = new StringBuilder();
			if (models.size() > 0) {
				int index = 1;
				for (String str : models) {
					
					if (index == models.size()) {
						sbModels.append(str);
					} else {
						sbModels.append(str).append("，");
					}
					
					index++;
				}
			}
			// 合同号：维修编号
			receiveExcel.setOrderNo(rmRepairInfo.getRepairNo());
			// 类型：固定"维修合同"
			receiveExcel.setType("维修合同");
			// 客户名：维修编号的单位名称
			receiveExcel.setCustomerName(rmRepairInfo.getRepairCusName());
			// 年度：处理日期对应的年
			receiveExcel.setYear(DateUtils.formatDate(rmRepairInfo.getProcessDate(), "yyyy"));
			// 发票：报价单头发票状态
			receiveExcel.setInvoiceStatus(rmRepairInfo.getInvoiceStatus());
			// 型号：报价单明细，多行用“,”分隔
//			receiveExcel.setModel(models.toString());
			receiveExcel.setModel(sbModels.toString());
			// 合同总金额：报价单明细行汇总
			receiveExcel.setTotalAmount(dtlTotalAmount.toString());
			// 应收款总金额：报价单明细行汇总
			receiveExcel.setReceiveTotalAmount(dtlTotalAmount.toString());
			// 发货日期：维修记录的处理日期
			receiveExcel.setDeliveryDate(DateUtils.formatDate(rmRepairInfo.getProcessDate(), "yyyy-MM-dd"));
			// 正常结账日期：维修记录的处理日期
			receiveExcel.setActDate(DateUtils.formatDate(rmRepairInfo.getProcessDate(), "yyyy-MM-dd"));
			// 验收日期：维修记录的处理日期
			receiveExcel.setProcessDate(DateUtils.formatDate(rmRepairInfo.getProcessDate(), "yyyy-MM-dd"));
			// 付款方式：空
			receiveExcel.setGatheringType("");
			// 账龄：系统日期-正常结账日期
			receiveExcel.setAging(String.valueOf(DateUtils.getDistanceOfTwoDate(rmRepairInfo.getProcessDate() ,new Date())));
			// 业务员：负责工程师
			receiveExcel.setEmployeeName(rmRepairInfo.getResponsiblePersonName());
			
			receiveExcelList.add(receiveExcel);
		}

		return receiveExcelList;
	}
	
	// 导出合同查询明细(方案2)
	public List<SoOrderExcel> exportDtl(SoOrderSearch soOrderSearch) {

		List<SoOrderSearch> orderSearchList = soOrderDao.findPageList(soOrderSearch);

		SoOrderExcel soOrderExcel = null;
		List<SoOrderExcel> soOrderExcelList = Lists.newArrayList();
		
		if (orderSearchList != null && orderSearchList.size() > 0) {
			
			NumberFormat nf = new DecimalFormat("#,##0.00");
			
			SoOrder soOrderEx = null;
			Map<String, String> orderNos = new HashMap<String, String>();
			for (SoOrderSearch soOrder : orderSearchList) {
				
				if (orderNos.containsKey(soOrder.getOrderNo())) {
					continue;
				} else {
					orderNos.put(soOrder.getOrderNo(), soOrder.getOrderNo());
				}

//				List<SoOrderDtl> SoOrderDtlList = soOrderDtlDao.getSoOrderDtlList(soOrder.getId());
				soOrderEx = new SoOrder();
				soOrderEx.setId(soOrder.getId());
				soOrderEx.setOrderNo(soOrder.getOrderNo());
				List<SoOrderDtl> SoOrderDtlList = soOrderDtlDao.getSoOrderDtlExList(soOrderEx);
				
				if (orderSearchList != null && orderSearchList.size() > 0) {

					for (SoOrderDtl soOrderDtl : SoOrderDtlList) {
						soOrderExcel = new SoOrderExcel();
						
						soOrderExcel.setOrderNo(soOrder.getOrderNo());
						soOrderExcel.setOrderDate(soOrder.getOrderDate());
						soOrderExcel.setPriceSystem(soOrder.getPriceSystem());
						soOrderExcel.setEmployeeName(soOrder.getEmployeeName());
						soOrderExcel.setCityName(soOrder.getCityName());
						soOrderExcel.setCustomerName(soOrder.getCustomerChName());
						soOrderExcel.setOrderType(soOrder.getOrderType());
						soOrderExcel.setCurrency(soOrder.getCurrency());
						if (!StringUtils.isEmptyNull(soOrder.getExchangeRate())) {

							soOrderExcel.setExchangeRate(nf.format(new BigDecimal(soOrder.getExchangeRate())));
						}
						if (!StringUtils.isEmptyNull(soOrder.getCommission())) {

							soOrderExcel.setCommission(nf.format(new BigDecimal(soOrder.getCommission())));
						}
						soOrderExcel.setReceiveStatus(soOrder.getReceiveStatus());
						soOrderExcel.setInvoiceStatus(soOrder.getInvoiceStatus());
						soOrderExcel.setDeliverStatus(soOrder.getDeliverStatus());

						if (soOrderDtl.getInvoiceDate() != null) {

							soOrderExcel.setInvoiceDate(DateUtils.formatDate(soOrderDtl.getInvoiceDate(), "yyyyMM"));
						}
						soOrderExcel.setPaymaentCon(soOrder.getPaymaentCon());
						soOrderExcel.setConditionRemarks(soOrder.getConditionRemarks());

						soOrderExcel.setCustomerChName(soOrderDtl.getCustomerChName());
						soOrderExcel.setIndustry(soOrderDtl.getIndustry());
						soOrderExcel.setCustomerDiff(soOrderDtl.getCustomerDiff());
						soOrderExcel.setCustomerSegmentation(soOrderDtl.getCustomerSegmentation());
						soOrderExcel.setProvinceName(soOrderDtl.getProvinceName());
						soOrderExcel.setDtlCityName(soOrderDtl.getCityName());
						soOrderExcel.setRegion(soOrderDtl.getRegion());
						soOrderExcel.setResponsiblePersonName(soOrderDtl.getResponsiblePersonName());
						soOrderExcel.setOrganizeName(soOrderDtl.getOrganizeName());
						soOrderExcel.setMaterialName(soOrderDtl.getMaterialName());
						soOrderExcel.setNum(soOrderDtl.getNum());
						if (!StringUtils.isEmptyNull(soOrderDtl.getUnitPrice())) {

							soOrderExcel.setUnitPrice(nf.format(new BigDecimal(soOrderDtl.getUnitPrice())));
						}
						if (!StringUtils.isEmptyNull(soOrderDtl.getTotalAmount())) {

							soOrderExcel.setTotalAmount(nf.format(new BigDecimal(soOrderDtl.getTotalAmount())));
						}
						soOrderExcel.setWarrantyPeriod(soOrderDtl.getWarrantyPeriod());
						soOrderExcel.setExtendedWarrPeriod(soOrderDtl.getExtendedWarrPeriod());
						soOrderExcel.setIfEffective(soOrderDtl.getIfEffective());
						soOrderExcel.setInvoiceNum(soOrderDtl.getInvoiceNum());
						soOrderExcel.setDeliverNum(soOrderDtl.getDeliverNum());
						
						soOrderExcelList.add(soOrderExcel);
					}
				}
			}
		}
		
		return soOrderExcelList;
	}
	
	/*// 导出合同查询明细(方案1)
	public Map<SoOrderSearch, List<SoOrderDtl>> exportDtl(SoOrderSearch soOrderSearch) {

		List<SoOrderSearch> orderSearchList = soOrderDao.findPageList(soOrderSearch);
		
		Map<SoOrderSearch, List<SoOrderDtl>> dtlMap = new HashMap<SoOrderSearch, List<SoOrderDtl>>();
		
		if (orderSearchList != null && orderSearchList.size() > 0) {
			
			for (SoOrderSearch soOrder : orderSearchList) {

				List<SoOrderDtl> SoOrderDtlList = soOrderDtlDao.getSoOrderDtlList(soOrder.getId());
				
				if (orderSearchList != null && orderSearchList.size() > 0) {

					dtlMap.put(soOrder, SoOrderDtlList);
				}
			}
		}
		
		return dtlMap;
	}*/
	
	// 发货申请画面初始化
	public SoApplyDeliver initSoApplyDeliver(SoApplyDeliver searchSoApplyDeliver) {

		SoApplyDeliver soApplyDeliver = soApplyDeliverDao.getSoApplyDeliverInfo(searchSoApplyDeliver);
		
		if (soApplyDeliver != null) {
			
			List<SoApplyDeliverDtl> soApplyDeliverDtlList = soApplyDeliverDtlDao.getSoApplyDeliverDtlListByAppId(soApplyDeliver.getId());
			List<SoOrderDtl> soOrderDtlList = soOrderDtlDao.getSoOrderDtlList(soApplyDeliver.getOrderId());
			
			Map<String, SoOrderDtl> mapSoOrderDtl = new HashMap<String, SoOrderDtl>();
			if (soOrderDtlList != null && soOrderDtlList.size() > 0) {
				for (SoOrderDtl soOrderDtl : soOrderDtlList) {
					mapSoOrderDtl.put(soOrderDtl.getLineNo(), soOrderDtl);
				}
			}
			
			if (soOrderDtlList != null && soOrderDtlList.size() > 0) {

				if (soApplyDeliverDtlList != null && soApplyDeliverDtlList.size() > 0) {
					
					for (SoApplyDeliverDtl soApplyDeliverDtl : soApplyDeliverDtlList) {
						
//						SoOrderDtl soOrderDtl = soOrderDtlList.get(Integer.parseInt(soApplyDeliverDtl.getLineNo())-1);
						SoOrderDtl soOrderDtl = mapSoOrderDtl.get(soApplyDeliverDtl.getLineNo());
						
						soApplyDeliverDtl.setSoOrderDtlId(soOrderDtl.getId());
						soApplyDeliverDtl.setOrderNo(soApplyDeliver.getOrderNo());
						soApplyDeliverDtl.setLineNo(soOrderDtl.getLineNo());
						soApplyDeliverDtl.setCustomerChName(soOrderDtl.getCustomerChName());
						soApplyDeliverDtl.setMaterialNo(soOrderDtl.getMaterialNo());
						soApplyDeliverDtl.setMaterialName(soOrderDtl.getMaterialName());
						soApplyDeliverDtl.setPackageMertiralNo(soOrderDtl.getPackageMertiralNo());
						soApplyDeliverDtl.setPackageMertiralName(soOrderDtl.getPackageMertiralName());
						soApplyDeliverDtl.setTotalNum(soOrderDtl.getNum());
						soApplyDeliverDtl.setDeliverNum(soOrderDtl.getDeliverNum());
					}
					
					soApplyDeliver.setSoApplyDeliverDtlList(soApplyDeliverDtlList);
				}
			}
		}
		
		return soApplyDeliver;
	}
	
	// 发货申请画面检索按钮处理
	public SoApplyDeliver getSoApplyDeliverInfo(SoApplyDeliver searchSoApplyDeliver) {
		SoApplyDeliver soApplyDeliver = initSoApplyDeliver(searchSoApplyDeliver);
		
		if (soApplyDeliver == null) {

			SoOrder soOrder = soOrderDao.getDeliverInfoByOrderNo(searchSoApplyDeliver);
			
			if (soOrder != null) {
				soApplyDeliver = new SoApplyDeliver();
				
				soApplyDeliver.setOrderNo(soOrder.getOrderNo());
				soApplyDeliver.setCustomerName(soOrder.getCustomerChName());
				soApplyDeliver.setInvoiceStatus(soOrder.getInvoiceStatus());
				soApplyDeliver.setReceiveStatus(soOrder.getReceiveStatus());
				
				List<SoOrderDtl> soOrderDtlList = soOrderDtlDao.getSoOrderDtlList(soOrder.getId());
				
				List<SoApplyDeliverDtl> soApplyDeliverDtlList = Lists.newArrayList();
				SoApplyDeliverDtl soApplyDeliverDtl = null;
				
				if (soOrderDtlList != null && soOrderDtlList.size() > 0) {
					for (SoOrderDtl soOrderDtl : soOrderDtlList) {
						if (StringUtils.equals(soOrderDtl.getIfEffective(), CommonConstants.NO)
								&& !StringUtils.equals(soOrderDtl.getNum(), soOrderDtl.getDeliverNum())) {

//							if (StringUtils.isEmptyNull(soOrderDtl.getPackageMertiralNo())
//									|| (!StringUtils.isEmptyNull(soOrderDtl.getPackageMertiralNo()) && BigDecimal.ZERO.compareTo(new BigDecimal(soOrderDtl.getTotalAmount())) < 0)) {

								soApplyDeliverDtl = new SoApplyDeliverDtl();
								
								soApplyDeliverDtl.setSoOrderDtlId(soOrderDtl.getId());
								soApplyDeliverDtl.setOrderNo(soOrder.getOrderNo());
								soApplyDeliverDtl.setLineNo(soOrderDtl.getLineNo());
								soApplyDeliverDtl.setCustomerChName(soOrderDtl.getCustomerChName());
								soApplyDeliverDtl.setMaterialNo(soOrderDtl.getMaterialNo());
								soApplyDeliverDtl.setMaterialName(soOrderDtl.getMaterialName());
								soApplyDeliverDtl.setPackageMertiralNo(soOrderDtl.getPackageMertiralNo());
								soApplyDeliverDtl.setPackageMertiralName(soOrderDtl.getPackageMertiralName());
								soApplyDeliverDtl.setTotalNum(soOrderDtl.getNum());
								soApplyDeliverDtl.setDeliverNum(soOrderDtl.getDeliverNum());
								
								soApplyDeliverDtlList.add(soApplyDeliverDtl);
//							}
						}
					}
					
					soApplyDeliver.setSoApplyDeliverDtlList(soApplyDeliverDtlList);
				}
			}
		}
		
		return soApplyDeliver;
	}

	@Transactional(readOnly = false)
	public void saveSoApplyDeliverInfo(SoApplyDeliver soApplyDeliver, String optStattus) {
		// 完成流程任务
		Map<String, Object> vars = Maps.newHashMap();
		// 是否合同中包含血压计
		vars.put("sphygr", ifSphygr(soApplyDeliver));
		// 启动流程
		// 发货申请发起
		if (StringUtils.isBlank(soApplyDeliver.getAct().getProcInsId())
				|| (StringUtils.equals(soApplyDeliver.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50) && StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10))) {
			// 保存发货申请信息
			saveSoApplyDeliverInfo(soApplyDeliver);
			// 启动流程
			String procInsId = actTaskService.startProcess(ActUtils.PD_SO_APPLY_DELIVER[0],
					ActUtils.PD_SO_APPLY_DELIVER[1], soApplyDeliver.getId(),
					CommonConstants.PROCESS_TITLE_SO_APPLY_DELIVER);
			// 完成第一个任务，进入申请中状态
			actTaskService.completeFirstTask(procInsId, null, null, vars);
			
			soApplyDeliver.setProcInsId(procInsId);
		} else {
			// 节点审批通过
			if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_20)) {
				// 通过审批
				vars.put("pass", "1");
				actTaskService.complete(soApplyDeliver.getAct().getTaskId(), soApplyDeliver
						.getAct().getProcInsId(), soApplyDeliver.getAct().getComment(),
						CommonConstants.PROCESS_TITLE_SO_APPLY_DELIVER, vars);
				ActUtils.updateWorkflowStatus(ActUtils.PD_SO_APPLY_DELIVER,
						CommonConstants.WORKFLOW_STATUS_20, soApplyDeliver.getId());
				
				/*if (actTaskService.getProcIns(soApplyDeliver.getAct().getProcInsId()) != null) {
				} else {
					
					// 更新合同信息的发货数量
					updateSoOrderDeliver(soApplyDeliver);
				}*/
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_30)) {
				// 退回审批
				vars.put("pass", "0");
				actTaskService.complete(soApplyDeliver.getAct().getTaskId(), soApplyDeliver
						.getAct().getProcInsId(), soApplyDeliver.getAct().getComment(),
						CommonConstants.PROCESS_TITLE_SO_APPLY_DELIVER, vars);
				ActUtils.updateWorkflowStatus(ActUtils.PD_SO_APPLY_DELIVER,
						CommonConstants.WORKFLOW_STATUS_30, soApplyDeliver.getId());
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_40)) {
				// 任务撤回

				List<HistoricTaskInstance> his = historyService
						.createHistoricTaskInstanceQuery()
						.processInstanceId(soApplyDeliver.getAct().getProcInsId())
						.finished().orderByHistoricTaskInstanceEndTime()
						.asc().list();
				actTaskService.jumpTask(soApplyDeliver.getAct().getProcInsId(), his.get(0).getTaskDefinitionKey(), vars);
			
//				actTaskService.taskBack(soApplyDeliver.getAct().getProcInsId(), vars);
				ActUtils.updateWorkflowStatus(ActUtils.PD_SO_APPLY_DELIVER,
						CommonConstants.WORKFLOW_STATUS_40, soApplyDeliver.getId());
			} else if (!StringUtils.equals(soApplyDeliver.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50) && StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10)) {
				// 保存发货申请信息
				saveSoApplyDeliverInfo(soApplyDeliver);
				actTaskService.complete(soApplyDeliver.getAct().getTaskId(), soApplyDeliver
						.getAct().getProcInsId(), null, null, null);
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_70)) {
				// 删除流程实例
				actProcessService.deleteProcIns(soApplyDeliver.getAct().getProcInsId(), "删除["
						+ CommonConstants.PROCESS_TITLE_SO_APPLY_DELIVER + "]流程");
				// 删除历史任务实例
				List<HistoricTaskInstance> hisTaskList = historyService
						.createHistoricTaskInstanceQuery()
						.processInstanceId(soApplyDeliver.getAct().getProcInsId()).list();
				for (HistoricTaskInstance hisTask : hisTaskList) {
					historyService.deleteHistoricTaskInstance(hisTask.getId());
				}

				// 业务表更新
				SoApplyDeliver updSoApplyDeliver = soApplyDeliverDao.get(soApplyDeliver.getId());
				updSoApplyDeliver.setDelFlag(CommonConstants.YES);
				updSoApplyDeliver.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_70);
				updSoApplyDeliver.setProcInsId(null);
				updSoApplyDeliver.preUpdate();
				soApplyDeliverDao.update(updSoApplyDeliver);
				updSoApplyDeliver.preUpdate();
				soApplyDeliverDao.delete(updSoApplyDeliver);
			}
		}
	}
	
	// 合同物料是否为血压计
	public String ifSphygrForSo(SoOrder soOrder) {
		
		String ifSphygr = CommonConstants.NO;
		for (SoOrderDtl soOrderDtl : soOrder.getSoOrderDtlList()) {
			String materialMark = soApplyDeliverDao.getMaterialMarkByMaterialNo(soOrderDtl.getMaterialNo());
			if (StringUtils.equals(materialMark, CommonConstants.MATERIAL_MARK_1)) {
				
				ifSphygr = CommonConstants.YES;
				break;
			}
		}
		
		return ifSphygr;
	}
	
	// 发货物料是否为血压计
	public String ifSphygr(SoApplyDeliver soApplyDeliver) {
		
		String ifSphygr = CommonConstants.NO;
		for (SoApplyDeliverDtl soApplyDeliverDtl : soApplyDeliver.getSoApplyDeliverDtlList()) {
			String materialMark = soApplyDeliverDao.getMaterialMarkByMaterialNo(soApplyDeliverDtl.getMaterialNo());
			if (StringUtils.equals(materialMark, CommonConstants.MATERIAL_MARK_1)) {
				
				ifSphygr = CommonConstants.YES;
				break;
			}
		}
		
		return ifSphygr;
	}

	/**
	 * 发货申请信息保存
	 */
	@Transactional(readOnly = false)
	public void saveSoApplyDeliverInfo(SoApplyDeliver soApplyDeliver) {

		// 发货申请保存
		this.saveSoApplyDeliver(soApplyDeliver);
		// 发货申请明细保存
		this.saveSoApplyDeliverDtl(soApplyDeliver);
	}
	
	/**
	 * 发货申请信息保存
	 * 
	 * @param soApplyDeliver
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void saveSoApplyDeliver(SoApplyDeliver soApplyDeliver) {
		
		soApplyDeliver.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
		soApplyDeliver.setIfOut(CommonConstants.NO);
		
		if (StringUtils.isEmptyNull(soApplyDeliver.getId())) {
			
			soApplyDeliver.preInsert();
			soApplyDeliverDao.insert(soApplyDeliver);
		} else {
			soApplyDeliver.preUpdate();
			soApplyDeliverDao.update(soApplyDeliver);
		}
	}
	
	/**
	 * 发货申请明细保存
	 * 
	 * @param soApplyDeliver
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void saveSoApplyDeliverDtl(SoApplyDeliver soApplyDeliver) {
		
		soApplyDeliverDtlDao.deleteSoApplyDeliverDtlByAppId(soApplyDeliver.getId());

		for (SoApplyDeliverDtl soApplyDeliverDtl : soApplyDeliver.getSoApplyDeliverDtlList()) {
			
			if (!StringUtils.isEmptyNull(soApplyDeliverDtl.getNum()) && soApplyDeliverDtl.getNum() != "0") {

				// 申请ID
				soApplyDeliverDtl.setAppId(soApplyDeliver.getId());

				soApplyDeliverDtl.preInsert();
				soApplyDeliverDtlDao.insert(soApplyDeliverDtl);
			}
		}
	}

	/**
	 * 发货以及合同整合性验证
	 */
	public String soApplyDeliverValid(SoApplyDeliver soApplyDeliver) {
		
		List<String> message = new ArrayList<String>();
		
		if (soApplyDeliver.getExpectDate() == null) {
			message.add("要求发货日必须填写！");
		} else {
			
			// 要求发货日期不可以早于系统日期
			String sysDate = DateUtils.getDate();
			Calendar afterC = Calendar.getInstance();
			afterC.add(Calendar.DAY_OF_MONTH, 1);
			String afterDate = DateUtils.formatDate(afterC.getTime(), "yyyy-MM-dd");
			String expectDate = DateUtils.formatDate(soApplyDeliver.getExpectDate(), "yyyy-MM-dd");
			// 要求发货日期如果等于系统日期
			String sysHour = DateUtils.getTime();
			Calendar c = Calendar.getInstance();

			if (DateUtils.compareDate(expectDate, sysDate, "yyyy-MM-dd") < 0) {
				message.add("要求发货日期不可以早于系统日期！");
			} else if (DateUtils.compareDate(expectDate, sysDate, "yyyy-MM-dd") == 0) {

				if (DateUtils.compareDate(sysHour, "18:00:00", "HH:mm:ss") <= 0) {

					// 系统时间18：00之前，该日期需要在系统日期+1或更晚，提示“要求发货日不能早于yyyy-mm-dd(系统日期+1）”。
					c.add(Calendar.DAY_OF_MONTH, 1);
					message.add("要求发货日不能早于" + DateUtils.formatDate(c.getTime(), "yyyy-MM-dd"));
					
				} else {

					// 系统日期18：00之后的话，该日期需要在系统日期+2或更晚，提示，要求发货日不能早于yyyy-mm-dd(系统日期+2）
					c.add(Calendar.DAY_OF_MONTH, 2);
					message.add("要求发货日不能早于" + DateUtils.formatDate(c.getTime(), "yyyy-MM-dd"));
				}
			} else if (DateUtils.compareDate(expectDate, sysDate, "yyyy-MM-dd") > 0 && DateUtils.compareDate(expectDate, afterDate, "yyyy-MM-dd") == 0) {
				if (DateUtils.compareDate(sysHour, "18:00:00", "HH:mm:ss") > 0) {

					// 系统日期18：00之后的话，该日期需要在系统日期+2或更晚，提示，要求发货日不能早于yyyy-mm-dd(系统日期+2）
					c.add(Calendar.DAY_OF_MONTH, 2);
					message.add("要求发货日不能早于" + DateUtils.formatDate(c.getTime(), "yyyy-MM-dd"));
				}
			}
		}

		if (!StringUtils.isEmptyNull(soApplyDeliver.getId())) {
			
			SoApplyDeliver updSoApplyDeliver = soApplyDeliverDao.get(soApplyDeliver.getId());
			
			if (DateUtils.compareDate(updSoApplyDeliver.getUpdateDate(), soApplyDeliver.getUpdateDate()) != 0) {
				message.add("合同" + soApplyDeliver.getOrderNo() + "的发货信息已经被修改！");
			}
		}
		
		// 发货申请明细的申请发货数量至少输入一条
		if (soApplyDeliver.getSoApplyDeliverDtlList() != null && soApplyDeliver.getSoApplyDeliverDtlList().size() != 0) {
			
			boolean ifNumBlank = true;
			Map<String, String> packageNum = new HashMap<String, String>();
			for (SoApplyDeliverDtl soApplyDeliverDtl : soApplyDeliver.getSoApplyDeliverDtlList()) {
				
				if (!StringUtils.isEmptyNull(soApplyDeliverDtl.getNum()) && soApplyDeliverDtl.getNum() != "0") {
					ifNumBlank = false;
					
					if (!StringUtils.isEmptyNull(soApplyDeliverDtl.getPackageMertiralNo())) {
						if (packageNum.containsKey(soApplyDeliverDtl.getPackageMertiralNo())) {
							
							if (!StringUtils.equals(soApplyDeliverDtl.getNum(), packageNum.get(soApplyDeliverDtl.getPackageMertiralNo()))) {
								message.add("套餐物料" + soApplyDeliverDtl.getPackageMertiralName() + "的发货数量请保持一致！");
							}
						} else {
							packageNum.put(soApplyDeliverDtl.getPackageMertiralNo(), soApplyDeliverDtl.getNum());
						}
					}
					
					if (StringUtils.isEmptyNull(soApplyDeliverDtl.getIfInstall())) {
						message.add("请选择发货明细第" + soApplyDeliverDtl.getLineNo() + "行是否安装！");
					}
				}
			}
			
			if (ifNumBlank) {
				message.add("申请发货数量请至少输入一条！");
			}
		}

		// 合同申请中，不可以申请开票
		SoOrder applyingSoOrder = soOrderDao.getApplyingInfoByOrderNo(soApplyDeliver.getOrderNo());
		
		if (applyingSoOrder != null) {
			message.add("存在正在申请中的合同信息！");
		}
		
		StringBuilder sbMessage = new StringBuilder();
		if (message.size() > 0) {
			for (String str : message) {
				int index = 1;
				
				if (index == message.size()) {
					sbMessage.append(str);
				} else {
					sbMessage.append(str).append("<br/>");
				}
				
				index++;
			}
		} else {
			return null;
		}
		
		return sbMessage.toString();
	}
	
	/**
	 * 更新合同信息的发货数量
	 * 
	 * @param deliverId
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void updateSoOrderDeliver(String deliverId) {
		
		SoApplyDeliver soApplyDeliver = soApplyDeliverDao.get(deliverId);
		List<SoApplyDeliverDtl> soApplyDeliverDtlList = null;
		
		if (soApplyDeliver != null) {
			soApplyDeliverDtlList = soApplyDeliverDtlDao.getSoApplyDeliverDtlListByAppId(soApplyDeliver.getId());
		}

		Map<String,Integer> deliverNum = new HashMap<String, Integer>();
		for (SoApplyDeliverDtl soApplyDeliverDtl : soApplyDeliverDtlList) {
			
			if (deliverNum.containsKey(soApplyDeliverDtl.getLineNo())) {
				
				deliverNum.put(soApplyDeliverDtl.getLineNo(), deliverNum.get(soApplyDeliverDtl.getLineNo()) + Integer.parseInt(soApplyDeliverDtl.getNum()));
			} else {
				deliverNum.put(soApplyDeliverDtl.getLineNo(), Integer.parseInt(soApplyDeliverDtl.getNum()));
			}
		}
		if (!deliverNum.isEmpty()) {

			SoOrder updSoOrder = soOrderDao
					.getEffectiveInfoByOrderNo(soApplyDeliver.getOrderNo());
			if (updSoOrder != null) {

				int totalDelNum = 0;
				int totalNum = 0;

				// 更新合同明细的发货数量
				SoOrderDtl entity = new SoOrderDtl();
				entity.setOrderId(updSoOrder.getId());
				entity.setDelFlag(CommonConstants.NO);
				List<SoOrderDtl> soOrderDtlList = soOrderDtlDao
						.findList(entity);

				if (soOrderDtlList != null && soOrderDtlList.size() > 0) {
					for (SoOrderDtl soOrderDtl : soOrderDtlList) {

						if (deliverNum.containsKey(soOrderDtl.getLineNo())) {

							int delNum = 0;
							if (StringUtils.isEmptyNull(soOrderDtl.getDeliverNum())) {
								delNum = deliverNum.get(soOrderDtl.getLineNo());
							} else {
								delNum = Integer.parseInt(soOrderDtl
										.getDeliverNum())
										+ deliverNum.get(soOrderDtl.getLineNo());
							}
							
//							if (!StringUtils.isEmptyNull(soOrderDtl.getPackageMertiralNo())) {
//								int lineNo = Integer.parseInt(soOrderDtl.getLineNo());
//								
//								List<SmPackageInfoEntity> smPackageInfos = commonService.getSmPackageInfoByMaterialNo(soOrderDtl.getPackageMertiralNo());
//								
//								SoOrderDtl searchSoOrderDtlInfo = new SoOrderDtl();
//								searchSoOrderDtlInfo.setOrderId(soOrderDtl.getOrderId());
//								for (SmPackageInfoEntity smPackageInfo : smPackageInfos) {
//									searchSoOrderDtlInfo.setLineNo(Integer.toString(lineNo));
//									searchSoOrderDtlInfo.setMaterialNo(smPackageInfo.getMaterialNo());
//									searchSoOrderDtlInfo.setPackageMertiralNo(soOrderDtl.getPackageMertiralNo());
//
//									SoOrderDtl soOrderDtlInfo = soOrderDtlDao.getSoOrderDtlInfo(searchSoOrderDtlInfo);
//									
//									if (soOrderDtlInfo != null) {
//										totalDelNum = totalDelNum + delNum;
//										soOrderDtlInfo.setDeliverNum(Integer.toString(delNum));
//										
//										soOrderDtlInfo.preUpdate();
//										soOrderDtlDao.update(soOrderDtlInfo);
//									}
//									lineNo = lineNo + 1;
//								}
//							} else {

								totalDelNum = totalDelNum + delNum;
								soOrderDtl.setDeliverNum(Integer.toString(delNum));

								soOrderDtl.preUpdate();
								soOrderDtlDao.update(soOrderDtl);
//							}
						}
						
						if (StringUtils.equals(soOrderDtl.getIfEffective(),
								CommonConstants.NO)) {

							totalNum = totalNum + Integer.parseInt(soOrderDtl.getNum());
						}
					}
				}

				// 发货状态
				// 已发货：无作废标识的合同行数量=发货数量
				// 部分发货：无作废标识的合同行数量>发货数量（不为0）
				// 未发货：发货数量=0，且无作废标识的合同行数量不为0
				if (totalDelNum == 0 && totalNum > 0) {
					updSoOrder.setDeliverStatus("10");
				} else if (totalDelNum > 0 && totalNum > 0 && totalNum > totalDelNum) {
					updSoOrder.setDeliverStatus("20");
				} else if (totalNum == totalDelNum) {
					updSoOrder.setDeliverStatus("30");
				}

				updSoOrder.preUpdate();
				soOrderDao.update(updSoOrder);
			}
		}
	}

	// 合同出库画面查询
	public SmStorageInfoSearch getSmStorageInfo(String orderNo) {
		
		SmStorageInfoSearch smStorageInfoSearch = null;

		// 取得未发货的发货申请
		SoApplyDeliver applyingSoApplyDeliver = soApplyDeliverDao.getSoAppDelWorkStatusByOrderNo(orderNo);
		
		if (applyingSoApplyDeliver != null) {
			
			smStorageInfoSearch = new SmStorageInfoSearch();
			
			smStorageInfoSearch.setDeliverId(applyingSoApplyDeliver.getId());
			
			// 取得签约方名称
			SoOrder soOrder = soOrderDao.getEffectiveInfoByOrderNo(orderNo);
			
			smStorageInfoSearch.setOrderNo(orderNo);
			smStorageInfoSearch.setCustomerName(soOrder.getCustomerChName());
			
			// 获取发货明细
			List<SmStorageInfo> smStorageInfoList = Lists.newArrayList();
			SmStorageInfo smStorageInfo = null;
			
			List<SoApplyDeliverDtl> soApplyDeliverDtlList = soApplyDeliverDtlDao.getSoApplyDeliverDtlListByAppId(applyingSoApplyDeliver.getId());
			List<SoOrderDtl> soOrderDtlList = soOrderDtlDao.getSoOrderDtlList(soOrder.getId());
			
			if (soOrderDtlList != null && soOrderDtlList.size() > 0) {

				if (soApplyDeliverDtlList != null && soApplyDeliverDtlList.size() > 0) {
					
					for (SoApplyDeliverDtl soApplyDeliverDtl : soApplyDeliverDtlList) {
						
						smStorageInfo = new SmStorageInfo();
						
						SoOrderDtl soOrderDtl = soOrderDtlList.get(Integer.parseInt(soApplyDeliverDtl.getLineNo())-1);
						
						if (StringUtils.isEmptyNull(soOrderDtl.getPackageMertiralNo())) {

							// 合同号 :
							smStorageInfo.setOrderNo(orderNo);
							// 合同行号
							smStorageInfo.setOrderLineNo(soOrderDtl.getLineNo());
							// 最终客户
							smStorageInfo.setCustomerName(soOrderDtl.getCustomerChName());
							// 物料号
							smStorageInfo.setMaterialNo(soOrderDtl.getMaterialNo());
							// 物料名称
							smStorageInfo.setMaterialName(soOrderDtl.getMaterialName());
							// 物料类型
							smStorageInfo.setMaterialType(soOrderDtl.getMaterialType());
							
							// 预定发货日期
							smStorageInfo.setExpectDate(applyingSoApplyDeliver.getExpectDate());
							// 发货联系人
							smStorageInfo.setDeliverContactsName(soApplyDeliverDtl.getContactsName());
							// 发货电话
							smStorageInfo.setDeliverTelephone(soApplyDeliverDtl.getTelephone());
							// 发货地址
							smStorageInfo.setDeliverAddress(soApplyDeliverDtl.getAddress());
							// 是否安装
							smStorageInfo.setIfInstall(soApplyDeliverDtl.getIfInstall());

							Calendar c = Calendar.getInstance();
							// 出库日期
							smStorageInfo.setProcessDate(c.getTime());

							// 质保期
							String warrantyPeriod = soOrderDtl.getWarrantyPeriod();
							// 延保期
							String extendedWarrPeriod = soOrderDtl.getExtendedWarrPeriod();
							
							int period = Integer.parseInt(warrantyPeriod);
							if (!StringUtils.isEmptyNull(extendedWarrPeriod)) {
								period = period + Integer.parseInt(extendedWarrPeriod);
							}
							// 保期
							smStorageInfo.setPeriod(period);
							// 最晚安装日期
							if (StringUtils.equals(soApplyDeliverDtl.getIfInstall(), CommonConstants.YES)) {
								
								c.add(Calendar.YEAR, period);
								c.add(Calendar.DAY_OF_MONTH, -1);
								smStorageInfo.setLatestInstallDate(c.getTime());
							}

							SmMatInfo searchSmMatInfo =  new SmMatInfo();
							searchSmMatInfo.setDelFlag(CommonConstants.NO);
							searchSmMatInfo.setMaterialNo(soOrderDtl.getMaterialNo());
							SmMatInfo smMatInfo = smMatInfoDao.findByMaterialAndType(searchSmMatInfo);
							if (smMatInfo != null && (StringUtils.equals(smMatInfo.getIfSn(), CommonConstants.YES)
														|| StringUtils.equals(smMatInfo.getIfVirtualSn(), CommonConstants.YES))) {

								// 是否有SN
								smStorageInfo.setIfSn(CommonConstants.YES);
								
								for (int i=0; i < Integer.parseInt(soApplyDeliverDtl.getNum()); i++) {

									SmStorageInfo smStorageInfoSn = smStorageInfo;
									
									// 发货数量
									smStorageInfoSn.setDeliverNum("1");
									
									smStorageInfoList.add(smStorageInfoSn);
								}
							} else {

								// 是否有SN
								smStorageInfo.setIfSn(CommonConstants.NO);
								
								// 发货数量
								smStorageInfo.setDeliverNum(soApplyDeliverDtl.getNum());
								
								smStorageInfoList.add(smStorageInfo);
							}
						}
					}
					
					Map<String, String> packageNum = new HashMap<String, String>();
					for (SoApplyDeliverDtl soApplyDeliverDtl : soApplyDeliverDtlList) {
						
						SoOrderDtl soOrderDtl = soOrderDtlList.get(Integer.parseInt(soApplyDeliverDtl.getLineNo())-1);
						
						if (!StringUtils.isEmptyNull(soOrderDtl.getPackageMertiralNo()) && !packageNum.containsKey(soOrderDtl.getPackageMertiralNo())) {
							packageNum.put(soOrderDtl.getPackageMertiralNo(), soApplyDeliverDtl.getNum());

							List<SmPackageInfoEntity> smPackageInfos = commonService.getSmPackageInfoByMaterialNo(soOrderDtl.getPackageMertiralNo());
							for (int i=0; i < Integer.parseInt(soApplyDeliverDtl.getNum()); i++) {

								int lineNo = Integer.parseInt(soApplyDeliverDtl.getLineNo());
								for (int j=0; j < smPackageInfos.size(); j++) {

									soOrderDtl = soOrderDtlList.get(lineNo + j - 1);
									
									smStorageInfo = new SmStorageInfo();

									// 合同号 :
									smStorageInfo.setOrderNo(orderNo);
									// 合同行号
									smStorageInfo.setOrderLineNo(soOrderDtl.getLineNo());
									// 最终客户
									smStorageInfo.setCustomerName(soOrderDtl.getCustomerChName());
									// 物料号
									smStorageInfo.setMaterialNo(soOrderDtl.getMaterialNo());
									// 物料名称
									smStorageInfo.setMaterialName(soOrderDtl.getMaterialName());
									// 物料类型
									smStorageInfo.setMaterialType(soOrderDtl.getMaterialType());

									// 套餐物料号
									smStorageInfo.setPackageMertiralNo(soOrderDtl.getPackageMertiralNo());
									// 套餐物料名称
									smStorageInfo.setPackageMertiralName(soOrderDtl.getPackageMertiralName());
									
									// 预定发货日期
									smStorageInfo.setExpectDate(applyingSoApplyDeliver.getExpectDate());
									// 发货联系人
									smStorageInfo.setDeliverContactsName(soApplyDeliverDtl.getContactsName());
									// 发货电话
									smStorageInfo.setDeliverTelephone(soApplyDeliverDtl.getTelephone());
									// 发货地址
									smStorageInfo.setDeliverAddress(soApplyDeliverDtl.getAddress());
									// 是否安装
									smStorageInfo.setIfInstall(soApplyDeliverDtl.getIfInstall());

									Calendar c = Calendar.getInstance();
									// 出库日期
									smStorageInfo.setProcessDate(c.getTime());

									// 质保期
									String warrantyPeriod = soOrderDtl.getWarrantyPeriod();
									// 延保期
									String extendedWarrPeriod = soOrderDtl.getExtendedWarrPeriod();
									
									int period = Integer.parseInt(warrantyPeriod);
									if (!StringUtils.isEmptyNull(extendedWarrPeriod)) {
										period = period + Integer.parseInt(extendedWarrPeriod);
									}
									// 保期
									smStorageInfo.setPeriod(period);
									// 最晚安装日期
									if (StringUtils.equals(soApplyDeliverDtl.getIfInstall(), CommonConstants.YES)) {
										
										c.add(Calendar.YEAR, period);
										c.add(Calendar.DAY_OF_MONTH, -1);
										smStorageInfo.setLatestInstallDate(c.getTime());
									}

									SmMatInfo searchSmMatInfo =  new SmMatInfo();
									searchSmMatInfo.setDelFlag(CommonConstants.NO);
									searchSmMatInfo.setMaterialNo(soOrderDtl.getMaterialNo());
									SmMatInfo smMatInfo = smMatInfoDao.findByMaterialAndType(searchSmMatInfo);
									if (smMatInfo != null && (StringUtils.equals(smMatInfo.getIfSn(), CommonConstants.YES)
																|| StringUtils.equals(smMatInfo.getIfVirtualSn(), CommonConstants.YES))) {

										// 是否有SN
										smStorageInfo.setIfSn(CommonConstants.YES);
									} else {

										// 是否有SN
										smStorageInfo.setIfSn(CommonConstants.NO);
									}
									
									// 发货数量
									smStorageInfo.setDeliverNum("1");
									
									smStorageInfoList.add(smStorageInfo);
								}
							}
						}
					}
					
					smStorageInfoSearch.setSmStorageInfoList(smStorageInfoList);
				}
			}
		}
		
		return smStorageInfoSearch;
	}

	/**
	 * 发货以及合同整合性验证
	 */
	public String smStorageInfoValid(SmStorageInfoSearch smStorageInfoSearch) {
		
		List<String> message = new ArrayList<String>();
		
		// 明细必输项验证
		if (smStorageInfoSearch.getSmStorageInfoList() != null && smStorageInfoSearch.getSmStorageInfoList().size() != 0) {

			int index = 1;
			Map<String, Integer> snMap = new HashMap<String, Integer>();
			for (SmStorageInfo smStorageInfo : smStorageInfoSearch.getSmStorageInfoList()) {
				
				// S/N必须输入
				if (StringUtils.equals(smStorageInfo.getIfSn(), CommonConstants.YES)) {
					if (StringUtils.isEmptyNull(smStorageInfo.getSnNo())) {
						message.add("出库明细第" + index + "行的S/N必须输入！");
					} else {
						
						if (snMap.containsKey(smStorageInfo.getSnNo())) {
							message.add("出库明细第" + index + "行的S/N（" + smStorageInfo.getSnNo() + "）与第" + snMap.get(smStorageInfo.getSnNo()) + "行重复！");
						} else {
							snMap.put(smStorageInfo.getSnNo(), index);
						}

						// S/N是否存在
						// S/N是否在库
						SmWarehouseInfo searchSmWarehouseInfo = new SmWarehouseInfo();
						searchSmWarehouseInfo.setSnNo(smStorageInfo.getSnNo());
						searchSmWarehouseInfo.setMaterialNo(smStorageInfo.getMaterialNo());
						searchSmWarehouseInfo.setWarehouse(smStorageInfo.getWarehouse());
						searchSmWarehouseInfo.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
						List<SmWarehouseInfo> smWarehouseInfo = soOrderDao.getSmWarehouseInfo(searchSmWarehouseInfo);
						if (smWarehouseInfo == null || smWarehouseInfo.size() == 0) {
							message.add("出库明细第" + index + "行的S/N（" + smStorageInfo.getSnNo() + "）未找到在库信息！");
						} else {
							int num = smWarehouseInfo.get(0).getNum();
							int occupationNo = 0;
							int deliverNum = 0;
							if (!StringUtils.isEmptyNull(smWarehouseInfo.get(0).getOccupationNo())) {
								occupationNo = Integer.parseInt(smWarehouseInfo.get(0).getOccupationNo());
							}
							if (!StringUtils.isEmptyNull(smStorageInfo.getDeliverNum())) {
								deliverNum = Integer.parseInt(smStorageInfo.getDeliverNum());
							}
							
							int remNum = num - occupationNo;
							if (deliverNum > remNum) {
								message.add("出库明细第" + index + "行的S/N（" + smStorageInfo.getSnNo() + "）的库存数量不足（剩余：" + remNum + "）");
							}
						}
						
						// S/N管理信息
						SmSnInfo searchSmSnInfo = new SmSnInfo();
						searchSmSnInfo.setSnNo(smStorageInfo.getSnNo());
						searchSmSnInfo.setMaterialNo(smStorageInfo.getMaterialNo());
						SmSnInfo smSnInfo = soOrderDao.getSmSnInfo(searchSmSnInfo);
						if (smSnInfo == null) {
							message.add("出库明细第" + index + "行的S/N（" + smStorageInfo.getSnNo() + "）未找到SN管理信息！");
						} else {
							if (!StringUtils.equals(smSnInfo.getStatus(), CommonConstants.MACHINE_STATUS_3)) {
								message.add("出库明细第" + index + "行的S/N（" + smStorageInfo.getSnNo() + "）的机器状态为：" + DictUtils.getDictLabel(smSnInfo.getStatus(), "DM0033", ""));
							} else {
								if (StringUtils.equals(smSnInfo.getIfLocked(), CommonConstants.YES)) {
									message.add("出库明细第" + index + "行的S/N（" + smStorageInfo.getSnNo() + "）已被锁定，不可以出售！");
								}
							}
						}
					}
				// 库房必须输入
				} else {

					if (!StringUtils.equals(smStorageInfo.getMaterialType(), CommonConstants.MATERIAL_TYPE_6)) {

						if (StringUtils.isEmptyNull(smStorageInfo.getWarehouse())) {
							message.add("出库明细第" + index + "行的库房必须选择！");
						} else {

							SmWarehouseInfo searchSmWarehouseInfo = new SmWarehouseInfo();
							searchSmWarehouseInfo.setMaterialNo(smStorageInfo.getMaterialNo());
							searchSmWarehouseInfo.setWarehouse(smStorageInfo.getWarehouse());
							searchSmWarehouseInfo.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
							List<SmWarehouseInfo> smWarehouseInfo = soOrderDao.getSmWarehouseInfo(searchSmWarehouseInfo);
							if (smWarehouseInfo == null || smWarehouseInfo.size() == 0) {
								message.add("出库明细第" + index + "行的物料号（" + smStorageInfo.getMaterialNo() + "）未找到在库信息！");
							} else {
								int num = smWarehouseInfo.get(0).getNum();
								int occupationNo = 0;
								int deliverNum = 0;
								if (!StringUtils.isEmptyNull(smWarehouseInfo.get(0).getOccupationNo())) {
									occupationNo = Integer.parseInt(smWarehouseInfo.get(0).getOccupationNo());
								}
								if (!StringUtils.isEmptyNull(smStorageInfo.getDeliverNum())) {
									deliverNum = Integer.parseInt(smStorageInfo.getDeliverNum());
								}
								
								int remNum = num - occupationNo;
								if (deliverNum > remNum) {
									message.add("出库明细第" + index + "行的的物料号（" + smStorageInfo.getMaterialNo() + "）的库存数量不足（剩余：" + remNum + "）");
								}
							}
						}
					}
				}

				if (!StringUtils.equals(smStorageInfo.getMaterialType(), CommonConstants.MATERIAL_TYPE_6)) {

					// 出库日期必须输入
					if (smStorageInfo.getProcessDate() == null) {
						message.add("出库明细第" + index + "行的出库日期必须输入！");
					}

					// 最晚安装日期必须输入
					if (StringUtils.equals(smStorageInfo.getIfInstall(), CommonConstants.YES)) {
						if (smStorageInfo.getLatestInstallDate() == null) {
							message.add("出库明细第" + index + "行的最晚安装日期必须输入！");
						}
					}
				}
				
				index++;
			}
		}
		
		StringBuilder sbMessage = new StringBuilder();
		if (message.size() > 0) {
			for (String str : message) {
				int index = 1;
				
				if (index == message.size()) {
					sbMessage.append(str);
				} else {
					sbMessage.append(str).append("<br/>");
				}
				
				index++;
			}
		} else {
			return null;
		}
		
		return sbMessage.toString();
	}

	/**
	 * 合同出库的出库提交
	 */
	@Transactional(readOnly = false)
	public void saveSmStorageInfo(SmStorageInfoSearch smStorageInfoSearch) {

		String code=commonService.getNextSequence("storage_id", CommonConstants.STORAGE_TYPE_21, 8);
		String storageId = CommonConstants.STORAGE_TYPE_21 + code;

		// 保存出入库信息
		this.creatSmStorageInfo(smStorageInfoSearch, storageId);
		// 更新SN管理
		this.updateSmSnInfo(smStorageInfoSearch, storageId);
		// 更新库存信息
		this.updateSmWarehouseInfo(smStorageInfoSearch, storageId);
		// 更新发货申请
		this.updateSoApplyDeliver(smStorageInfoSearch);

		// 更新合同信息的发货数量
		updateSoOrderDeliver(smStorageInfoSearch.getDeliverId());
	}
	
	/**
	 * 保存出入库信息
	 * 
	 * @param smStorageInfoSearch
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void creatSmStorageInfo(SmStorageInfoSearch smStorageInfoSearch, String storageId) {

		for (SmStorageInfo smStorageInfo : smStorageInfoSearch.getSmStorageInfoList()) {

			if (!StringUtils.equals(smStorageInfo.getMaterialType(), CommonConstants.MATERIAL_TYPE_6)) {

				// 出入库履历编号
				smStorageInfo.setStorageId(storageId);
				// 出入库类型
				smStorageInfo.setStorageType(CommonConstants.STORAGE_TYPE_21);
				// 行号
				smStorageInfo.setLineNo(smStorageInfo.getOrderLineNo());
				// 数量
				smStorageInfo.setNum(Integer.parseInt(smStorageInfo.getDeliverNum()));
				// 是否安装
				smStorageInfo.setIfInstall(smStorageInfo.getIfInstall());
				// 最晚安装日期
				smStorageInfo.setLatestInstallDate(smStorageInfo.getLatestInstallDate());
				
				smStorageInfo.preInsert();
				soOrderDao.insertSmStorageInfo(smStorageInfo);
			}
		}
	}
	
	/**
	 * 更新SN管理
	 * 
	 * @param smStorageInfoSearch
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void updateSmSnInfo(SmStorageInfoSearch smStorageInfoSearch, String storageId) {

		Map<String, String> packageInfo = new  HashMap<String, String>();
		for (SmStorageInfo smStorageInfo : smStorageInfoSearch.getSmStorageInfoList()) {

			if (StringUtils.equals(smStorageInfo.getIfSn(), CommonConstants.YES)) {

				SmSnInfo searchSmSnInfo = new SmSnInfo();
				searchSmSnInfo.setSnNo(smStorageInfo.getSnNo());
				searchSmSnInfo.setMaterialNo(smStorageInfo.getMaterialNo());
				SmSnInfo smSnInfo = soOrderDao.getSmSnInfo(searchSmSnInfo);
				
				if (!StringUtils.isEmptyNull(smStorageInfo.getPackageMertiralNo())) {

					SmMatInfo searchSmMatInfo =  new SmMatInfo();
					searchSmMatInfo.setDelFlag(CommonConstants.NO);
					searchSmMatInfo.setMaterialNo(smStorageInfo.getMaterialNo());
					SmMatInfo smMatInfo = smMatInfoDao.findByMaterialAndType(searchSmMatInfo);
					
					if (StringUtils.equals(CommonConstants.MATERIAL_TYPE_1, smMatInfo.getMaterialType())) {
						packageInfo.put(smStorageInfo.getPackageMertiralNo(), smStorageInfo.getSnNo());
					} else {

						String materialMark = soApplyDeliverDao.getMaterialMarkByMaterialNo(smStorageInfo.getMaterialNo());
						if (StringUtils.equals(materialMark, CommonConstants.MATERIAL_MARK_2)) {

							SmSnInfo searchPackageSmSnInfo = new SmSnInfo();
							searchPackageSmSnInfo.setSnNo(packageInfo.get(smStorageInfo.getPackageMertiralNo()));
							SmSnInfo packageSmSnInfo = soOrderDao.getSmSnInfo(searchPackageSmSnInfo);
							
							packageSmSnInfo.setLbSnNo(smStorageInfo.getSnNo());
							
							packageSmSnInfo.preUpdate();
							soOrderDao.updateSmSnInfo(packageSmSnInfo);
						}
					}
					
				}

				// 出入库履历编号
				smSnInfo.setStorageId(storageId);
				// 是否安装
				smSnInfo.setIfInstall(smStorageInfo.getIfInstall());
				if (StringUtils.equals(smStorageInfo.getIfInstall(), CommonConstants.YES)) {

					// 最晚安装日期
					smSnInfo.setLatestInstallDate(smStorageInfo.getLatestInstallDate());
				} else {
					
					// 保修日期起
					smSnInfo.setWarrantyDateFrom(smStorageInfo.getProcessDate());

					Calendar c = Calendar.getInstance();
					c.setTime(smStorageInfo.getProcessDate());
					c.add(Calendar.YEAR, smStorageInfo.getPeriod());
					c.add(Calendar.DAY_OF_MONTH, -1);
					// 保修到期止
					smSnInfo.setWarrantyDateTo(c.getTime());
				}
				// 合同号
				smSnInfo.setOrderNo(smStorageInfo.getOrderNo());
				// 行号
				smSnInfo.setLineNo(smStorageInfo.getOrderLineNo());
				
				smSnInfo.preUpdate();
				soOrderDao.updateSmSnInfo(smSnInfo);
			}
		}
	}
	
	/**
	 * 更新库存信息
	 * 
	 * @param smStorageInfoSearch
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void updateSmWarehouseInfo(SmStorageInfoSearch smStorageInfoSearch, String storageId) {

		for (SmStorageInfo smStorageInfo : smStorageInfoSearch.getSmStorageInfoList()) {

			SmWarehouseInfo searchSmWarehouseInfo = new SmWarehouseInfo();
			searchSmWarehouseInfo.setSnNo(smStorageInfo.getSnNo());
			searchSmWarehouseInfo.setMaterialNo(smStorageInfo.getMaterialNo());
			searchSmWarehouseInfo.setWarehouse(smStorageInfo.getWarehouse());
			searchSmWarehouseInfo.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
			List<SmWarehouseInfo> smWarehouseInfo = soOrderDao.getSmWarehouseInfo(searchSmWarehouseInfo);

			if (smWarehouseInfo != null && smWarehouseInfo.size() > 0) {

				// 出入库履历编号
				smWarehouseInfo.get(0).setStorageId(storageId);

				if (StringUtils.equals(smStorageInfo.getIfSn(), CommonConstants.NO)) {
					
					// 库存数量
					int deliverNum = Integer.parseInt(smStorageInfo.getDeliverNum());
					smWarehouseInfo.get(0).setNum(smWarehouseInfo.get(0).getNum() - deliverNum);
				} else {
					smWarehouseInfo.get(0).setInStockStatus(CommonConstants.IN_STOCK_STATUS_2);
				}
				
				smWarehouseInfo.get(0).preUpdate();
				soOrderDao.updateSmWarehouseInfo(smWarehouseInfo.get(0));
			}
		}
	}
	
	/**
	 * 更新发货申请
	 * 
	 * @param smStorageInfoSearch
	 *        画面数据
	 */
	@Transactional(readOnly = false)
	public void updateSoApplyDeliver(SmStorageInfoSearch smStorageInfoSearch) {
		
		SoApplyDeliver soApplyDeliver = soApplyDeliverDao.get(smStorageInfoSearch.getDeliverId());
		
		soApplyDeliver.setIfOut(CommonConstants.YES);
		soApplyDeliver.preUpdate();
		soApplyDeliverDao.update(soApplyDeliver);
	}
	
	// 导出合同出库信息
	public List<SmStorageInfoExcel> smStorageInfoExportDtl(SmStorageInfoSearch smStorageInfoSearch) {

		SmStorageInfoExcel smStorageInfoExcel = null;
		List<SmStorageInfoExcel> smStorageInfoExcelList = Lists.newArrayList();

		for (SmStorageInfo smStorageInfo : smStorageInfoSearch.getSmStorageInfoList()) {
			smStorageInfoExcel = new SmStorageInfoExcel();

			smStorageInfoExcel.setCustomerChName(smStorageInfo.getCustomerName());
			smStorageInfoExcel.setDeliverAddress(smStorageInfo.getDeliverAddress());
			smStorageInfoExcel.setDeliverContactsName(smStorageInfo.getDeliverContactsName());
			smStorageInfoExcel.setDeliverNum(smStorageInfo.getDeliverNum());
			smStorageInfoExcel.setDeliverTelephone(smStorageInfo.getDeliverTelephone());
			smStorageInfoExcel.setExpectDate(smStorageInfo.getExpectDate());
			smStorageInfoExcel.setExpressCompany(smStorageInfo.getExpressCompany());
			smStorageInfoExcel.setExpressNo(smStorageInfo.getExpressNo());
			smStorageInfoExcel.setIfInstall(smStorageInfo.getIfInstall());
			smStorageInfoExcel.setLatestInstallDate(smStorageInfo.getLatestInstallDate());
			smStorageInfoExcel.setMaterialName(smStorageInfo.getMaterialName());
			smStorageInfoExcel.setMaterialNo(smStorageInfo.getMaterialNo());
			smStorageInfoExcel.setOrderNo(smStorageInfo.getOrderNo());
			smStorageInfoExcel.setProcessDate(smStorageInfo.getProcessDate());
			smStorageInfoExcel.setProductionDate(smStorageInfo.getProductionDate());
			smStorageInfoExcel.setSnNo(smStorageInfo.getSnNo());
			smStorageInfoExcel.setWarehouse(smStorageInfo.getWarehouse());
			
			smStorageInfoExcelList.add(smStorageInfoExcel);
		}
		
		return smStorageInfoExcelList;
	}
	
	public SmSnInfo getSnInfo(String snNo, String materialNo) {
		
		// S/N管理信息
		SmSnInfo searchSmSnInfo = new SmSnInfo();
		searchSmSnInfo.setSnNo(snNo);
		searchSmSnInfo.setMaterialNo(materialNo);
		SmSnInfo smSnInfo = soOrderDao.getSmSnInfo(searchSmSnInfo);
		
		return smSnInfo;
	}
	
	public CmCustomerInfo getCustomerInfo(String customerId) {
		return imInvoiceDao.getCustomerInfo(customerId);
	}
	
	public EmployeeInfo getEmployeeInfo(String employeeId) {
		return imInvoiceDao.getEmployeeInfo(employeeId);
	}
	
	public SoOrderDtl getOrderDtlInfo(String orderId, String lineNo) {

		SoOrderDtl searchSoOrderDtlInfo = new SoOrderDtl();
		searchSoOrderDtlInfo.setOrderId(orderId);
		searchSoOrderDtlInfo.setLineNo(lineNo);
		SoOrderDtl soOrderDtlInfo = soOrderDtlDao.getSoOrderDtlInfo(searchSoOrderDtlInfo);

		return soOrderDtlInfo;
	}

	// 根据模板输出合同报价单
	public void exportOrderQuota(HttpServletResponse response, String orderId) {
		
		SoOrder soOrder = soOrderDao.getSoOrderInfo(orderId);
		BeanMap singleBean = BeanMap.create(new ExportFileHead());
		
		singleBean.put("kehumingcheng", soOrder.getCustomerChName());
		singleBean.put("fukuanfangshi", DictUtils.getDictLabel(soOrder.getPaymaentCon(), "DM0057", ""));
		singleBean.put("baojiaren", soOrder.getEmployeeName());
		singleBean.put("baojiariqi", DateUtils.getDate("yyyy年MM月dd日"));

		List<SoOrderDtl> soOrderDtlList = soOrderDtlDao.getSoOrderDtlList(orderId);
		
		List<BeanMap> multiBeans = new ArrayList<BeanMap>();
		BeanMap multiBean = null;

		BigDecimal total = BigDecimal.ZERO;
		int index = 1;
		NumberFormat nf = new DecimalFormat("#,##0.00");
		for (SoOrderDtl soOrderDtl : soOrderDtlList) {
			
			multiBean = BeanMap.create(new ExportFileDtl());

			multiBean.put("xuhao", Integer.toString(index++));
			multiBean.put("chanpinmingcheng", soOrderDtl.getMaterialName());
			multiBean.put("xinghao", soOrderDtl.getModel());
			multiBean.put("danjia", nf.format(new BigDecimal(soOrderDtl.getUnitPrice())));
			multiBean.put("shuliang", soOrderDtl.getNum());
			multiBean.put("zongjia", nf.format(new BigDecimal(soOrderDtl.getTotalAmount())));
			
			total = total.add(new BigDecimal(soOrderDtl.getTotalAmount()));

			multiBeans.add(multiBean);
		}

		singleBean.put("heji", "合计（含税）￥" + nf.format(total));
		singleBean.put("daxieheji", "（合计大写：" + NumberToCNUtils.number2CNMontrayUnit(total) + "）");

		Workbook workbook = FileExportUtil.wrokbookWithTemplate(quotaTemplatePath + TEMPLATE_NAME, singleBean, multiBeans);
		String fileName = EXPORT_FILE_NAME + "_" + soOrder.getOrderNo() + EXPORT_FILE_SUFIX;
		
		FileExportUtil.write(response, workbook, fileName);
	}
}