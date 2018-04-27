/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.CommonDao;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.mm.dao.InStorageManagementDao;
import com.inbody.crm.mm.entity.SmLendingMat;
import com.inbody.crm.mm.entity.SmSnInfo;
import com.inbody.crm.mm.entity.SmStorageApp;
import com.inbody.crm.mm.entity.SmStorageAppDtl;
import com.inbody.crm.mm.entity.SmStorageInfo;
import com.inbody.crm.mm.entity.SmWarehouseInfo;
import com.inbody.crm.modules.act.service.ActProcessService;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sd.dao.SoOrderDao;
import com.inbody.crm.sd.dao.SoOrderDtlDao;
import com.inbody.crm.sd.entity.SoOrder;
import com.inbody.crm.sd.entity.SoOrderDtl;

/**
 * 主子表Service
 * @author zhanglulu
 * @version 2017-08-15
 */
@Service
@Transactional(readOnly = true)
public class InStorageManagementService {

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private ActProcessService actProcessService;
	
	@Autowired
	private HistoryService historyService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private InStorageManagementDao inStorageManagementDao;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private SoOrderDao soOrderDao;

	@Autowired
	private SoOrderDtlDao soOrderDtlDao;

	/**
	 * 其它入库申请申请画面显示结果取得
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	public SmStorageApp getSmStorageAppInfo(String id, String type) {
		
		SmStorageApp smStorageApp = inStorageManagementDao.getSmStorageAppInfoById(id);
		List<SmStorageAppDtl> smStorageAppDtlList = null;

		SmStorageApp searchSmStorageApp = new SmStorageApp();
		searchSmStorageApp.setId(smStorageApp.getId());

		if (StringUtils.equals(type, "sendBack")) {
			searchSmStorageApp.setResponsiblePersonId(smStorageApp.getResponsiblePersonId());
			searchSmStorageApp.setDelFlag(null);
			smStorageAppDtlList = inStorageManagementDao.getSendBackSmStorageAppDtl(searchSmStorageApp);
		} else if (StringUtils.equals(type, "refund")) {

			smStorageAppDtlList = inStorageManagementDao.getRefundSmStorageAppDtl(searchSmStorageApp);
		} else if (StringUtils.equals(type, "exchange")) {

			smStorageAppDtlList = inStorageManagementDao.getExchangeSmStorageAppDtl(searchSmStorageApp);
		} else if (StringUtils.equals(type, "other")) {

			smStorageAppDtlList = inStorageManagementDao.getOtherSmStorageAppDtl(searchSmStorageApp);
		}
		
		if (smStorageAppDtlList != null && smStorageAppDtlList.size() > 0) {
			smStorageApp.setSmStorageAppDtlList(smStorageAppDtlList);
			
			if (StringUtils.equals(type, "other")) {
				smStorageApp.setMaterialNo(smStorageAppDtlList.get(0).getMaterialNo());
			} else if (StringUtils.equals(type, "refund") || StringUtils.equals(type, "exchange")) {
				smStorageApp.setOrderNo(smStorageAppDtlList.get(0).getSoOrderDtl().getOrderNo());
				
				if (StringUtils.equals(type, "refund")) {
					smStorageApp.setWarehouse(smStorageAppDtlList.get(0).getWarehouse());
				}
			}
		}

		return smStorageApp;
	}

	/**
	 * 其它入库申请申请画面初始化结果取得
	 * 
	 * @param type
	 * @return
	 */
	public SmStorageApp initSmStorageApp(String type) {
		SmStorageApp smStorageApp = new SmStorageApp();
		List<SmStorageAppDtl> smStorageAppDtlList = null;
		User user = UserUtils.getUser();
		
		if (StringUtils.equals(type, "sendBack")) {
			
			SmStorageApp searchSmStorageApp = new SmStorageApp();
			searchSmStorageApp.setResponsiblePersonId(user.getId());
			searchSmStorageApp.setDelFlag(CommonConstants.NO);
			smStorageAppDtlList = inStorageManagementDao.initSendBackSmStorageApp(searchSmStorageApp);

		}

		smStorageApp.setResponsiblePersonId(user.getId());
		smStorageApp.setResponsiblePersonName(user.getName());
		smStorageApp.setProcessDate(new Date());
		
		if (smStorageAppDtlList != null && smStorageAppDtlList.size() > 0) {
			smStorageApp.setSmStorageAppDtlList(smStorageAppDtlList);
		}

		return smStorageApp;
	}

	/**
	 * 其它入库申请申请画面查询结果取得
	 * 
	 * @param type
	 * @return
	 */
	public SmStorageApp searchSmStorageApp(SmStorageApp smStorageApp, String type) {
		SmStorageApp searchSmStorageApp = new SmStorageApp();
		List<SmStorageAppDtl> smStorageAppDtlList = null;
		
		if (StringUtils.equals(type, "refund")) {
			if (!StringUtils.isEmptyNull(smStorageApp.getOrderNo())) {

				searchSmStorageApp.setOrderNo(smStorageApp.getOrderNo());
				searchSmStorageApp.setSnNo(smStorageApp.getSnNo());
				
				smStorageAppDtlList = inStorageManagementDao.searchRefundSmStorageAppDtl(searchSmStorageApp);
			}
		} else if (StringUtils.equals(type, "exchange")) {
			if (!StringUtils.isEmptyNull(smStorageApp.getOrderNo())) {

				searchSmStorageApp.setOrderNo(smStorageApp.getOrderNo());
				
				smStorageAppDtlList = inStorageManagementDao.searchExchangeSmStorageAppDtl(searchSmStorageApp);
			}
		} else if (StringUtils.equals(type, "other")) {
			if (!StringUtils.isEmptyNull(smStorageApp.getMaterialNo())) {

				searchSmStorageApp.setMaterialNo(smStorageApp.getMaterialNo());
				smStorageAppDtlList= inStorageManagementDao.searchOtherSmStorageAppDtl(searchSmStorageApp);
			}
//			smStorageAppDtlList = smStorageApp.getSmStorageAppDtlList();
//			
//			List<SmStorageAppDtl> smStorageAppDtls= inStorageManagementDao.searchOtherSmStorageAppDtl(searchSmStorageApp);
//			
//			if (smStorageAppDtlList != null && smStorageAppDtlList.size() > 0) {
//				
//				smStorageAppDtlList.addAll(smStorageAppDtls);
//			} else {
//
//				smStorageAppDtlList = smStorageAppDtls;
//			}
		}

		if (smStorageAppDtlList != null && smStorageAppDtlList.size() > 0) {
			smStorageApp.setSmStorageAppDtlList(smStorageAppDtlList);
		} else {
			smStorageApp.setSmStorageAppDtlList(null);
		}

		return smStorageApp;
	}

	/**
	 * 整合性验证
	 */
	public String smStorageAppValid(SmStorageApp smStorageApp, String type) {
		
		List<String> message = new ArrayList<String>();
		
		if (!StringUtils.isEmpty(smStorageApp.getId())) {

			SmStorageApp updateSmStorageApp = inStorageManagementDao.getSmStorageAppInfoById(smStorageApp.getId());
			
			if (updateSmStorageApp == null) {
				message.add("申请信息已经被删除！");
			} else {

				if (DateUtils.compareDate(updateSmStorageApp.getUpdateDate(), smStorageApp.getUpdateDate()) != 0) {
					message.add("申请信息已经被修改！");
				}
			}
		}
		
		if (!StringUtils.equals(type, "other")) {
			if (smStorageApp.getSelectedList() == null || smStorageApp.getSelectedList().size() == 0) {
				message.add("请至少选择一条记录进行申请！");
			} else {
				if (StringUtils.equals(type, "sendBack")) {
					for (String selectedLendingId : smStorageApp.getSelectedList()) {
						
						int index = Integer.parseInt(selectedLendingId);
						
						SmStorageAppDtl smStorageAppDtl = smStorageApp.getSmStorageAppDtlList().get(index);
						
						Date lendingDateTo = smStorageAppDtl.getLendingDateTo();
						
						if (DateUtils.compareDate(DateUtils.getDate("yyyy-MM-dd"), DateUtils.formatDate(lendingDateTo, "yyyy-MM-dd"), "yyyy-MM-dd") < 0) {
							message.add("归还日期不可大于系统日期，请确认！");
						} else {

							String lendingId = smStorageAppDtl.getLendingId();
							SmLendingMat smLendingMat = inStorageManagementDao.getSmLendingMatById(lendingId);
							Date lendingDateFrom = smLendingMat.getLendingDateFrom();
							if (DateUtils.compareDate(DateUtils.formatDate(lendingDateTo, "yyyy-MM-dd"), DateUtils.formatDate(lendingDateFrom, "yyyy-MM-dd"), "yyyy-MM-dd") < 0) {
								message.add("归还日期不可小于借出日期，请确认！");
							}
						}
					}
				}
				if (StringUtils.equals(type, "exchange")) {
					for (String selectedLendingId : smStorageApp.getSelectedList()) {
						
						int index = Integer.parseInt(selectedLendingId);
						
						SmStorageAppDtl smStorageAppDtl = smStorageApp.getSmStorageAppDtlList().get(index);

						SmSnInfo searchSmSnInfo = new SmSnInfo();
						searchSmSnInfo.setMaterialNo(smStorageAppDtl.getMaterialNo());
						searchSmSnInfo.setSnNo(smStorageAppDtl.getSnNo());
						SmSnInfo smSnInfo = inStorageManagementDao.getSmSnInfo(searchSmSnInfo);
						
						if (smSnInfo == null) {
							message.add("入库SN号码不存在，请确认！");
						} else {
							if (!StringUtils.isEmptyNull(smSnInfo.getOrderNo())) {
								if (!StringUtils.equals(smStorageAppDtl.getOrderNo(), smSnInfo.getOrderNo())
										&& !StringUtils.equals(smStorageAppDtl.getLineNo(), smSnInfo.getLineNo())) {
									message.add("入库SN号码d对应的合同号以及行号不一致，请确认！");
								}
							} else if (!StringUtils.isEmptyNull(smSnInfo.getOldOrderNo())) {
								if (!StringUtils.equals(smStorageAppDtl.getOrderNo(), smSnInfo.getOldOrderNo())
										&& !StringUtils.equals(smStorageAppDtl.getLineNo(), smSnInfo.getOldLineNo())) {
									message.add("入库SN号码d对应的原合同号以及行号不一致，请确认！");
								}
							}
						}
					
					}
				}
			}
		} else {
			for (SmStorageAppDtl smStorageAppDtl : smStorageApp.getSmStorageAppDtlList()) {

				if (!StringUtils.isEmptyNull(smStorageAppDtl.getSnNo())) {

					SmSnInfo searchSmSnInfo = new SmSnInfo();
					searchSmSnInfo.setSnNo(smStorageAppDtl.getSnNo());
					SmSnInfo smSnInfo = inStorageManagementDao.getSmSnInfo(searchSmSnInfo);
					
					if (smSnInfo != null) {
						message.add("SN号码已经存在，请确认！");
					}
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

	@Transactional(readOnly = false)
	public void saveSmStorageAppInfo(SmStorageApp smStorageApp, String optStattus, String type) {
		
		String procDefKey = null;
		String businessTable = null;
		String title = null;
		String[] pdkey = null;
		// 完成流程任务
		Map<String, Object> vars = Maps.newHashMap();

		if (StringUtils.equals(type, "sendBack")) {
			// 是否工程部人员
			vars.put("engineer", ifEngineer(smStorageApp.getResponsiblePersonId()));

			procDefKey = ActUtils.SM_IN_SENDBACK[0];
			businessTable = ActUtils.SM_IN_SENDBACK[1];
			title = CommonConstants.PROCESS_TITLE_SM_IN_SENDBACK;
			pdkey = ActUtils.SM_IN_SENDBACK;
		} else if (StringUtils.equals(type, "refund")) {

			procDefKey = ActUtils.SM_IN_REFUND[0];
			businessTable = ActUtils.SM_IN_REFUND[1];
			title = CommonConstants.PROCESS_TITLE_SM_IN_REFUND;
			pdkey = ActUtils.SM_IN_REFUND;
		} else if (StringUtils.equals(type, "exchange")) {

			procDefKey = ActUtils.SM_IN_EXCHANGE[0];
			businessTable = ActUtils.SM_IN_EXCHANGE[1];
			title = CommonConstants.PROCESS_TITLE_SM_IN_EXCHANGE;
			pdkey = ActUtils.SM_IN_EXCHANGE;
		} else if (StringUtils.equals(type, "other")) {

			procDefKey = ActUtils.SM_IN_OTHER[0];
			businessTable = ActUtils.SM_IN_OTHER[1];
			title = CommonConstants.PROCESS_TITLE_SM_IN_OTHER;
			pdkey = ActUtils.SM_IN_OTHER;
		}

		// 启动流程
		// 其他入库申请发起
		if (StringUtils.isBlank(smStorageApp.getAct().getProcInsId())) {
			// 保存出入库申请信息
			saveSmStorageAppInfo(smStorageApp, type);
			// 启动流程
			String procInsId = actTaskService.startProcess(procDefKey,
					businessTable, smStorageApp.getId(),
					title);
			// 完成第一个任务，进入申请中状态
			actTaskService.completeFirstTask(procInsId, null, null, vars);
			
			smStorageApp.setProcInsId(procInsId);
		} else {
			// 节点审批通过
			if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_20)) {
				// 通过审批
				vars.put("pass", CommonConstants.YES);
				actTaskService.complete(smStorageApp.getAct().getTaskId(), smStorageApp
						.getAct().getProcInsId(), smStorageApp.getAct().getComment(),
						title, vars);
				
				if (actTaskService.getProcIns(smStorageApp.getAct().getProcInsId()) != null) {
					ActUtils.updateWorkflowStatus(pdkey,
							CommonConstants.WORKFLOW_STATUS_20, smStorageApp.getId());
				} else {
					// 取得申请人信息
					getApplierInfo(smStorageApp);
					// 保存出入库信息
					String storageId = creatSmStorageInfo(smStorageApp, type);
					// 出入库其他数据更新
					updateOtherInfo(smStorageApp, type, storageId);
					
					if (StringUtils.equals(type, "refund")) {

						// 合同相关数据更新
						updateSoOrderInfo(smStorageApp);
					}
				}
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_30)) {
				// 退回审批
				vars.put("pass", CommonConstants.NO);
				actTaskService.complete(smStorageApp.getAct().getTaskId(), smStorageApp
						.getAct().getProcInsId(), smStorageApp.getAct().getComment(),
						title, vars);
				ActUtils.updateWorkflowStatus(pdkey,
						CommonConstants.WORKFLOW_STATUS_30, smStorageApp.getId());
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_40)) {
				// 任务撤回
				if (StringUtils.equals(type, "sendBack")) {

					List<HistoricTaskInstance> his = historyService
							.createHistoricTaskInstanceQuery()
							.processInstanceId(smStorageApp.getAct().getProcInsId())
							.finished().orderByHistoricTaskInstanceEndTime()
							.asc().list();
					actTaskService.jumpTask(smStorageApp.getAct().getProcInsId(), his.get(0).getTaskDefinitionKey(), vars);
				} else {

					actTaskService.taskBack(smStorageApp.getAct().getProcInsId(), vars);
				}
				ActUtils.updateWorkflowStatus(pdkey,
						CommonConstants.WORKFLOW_STATUS_40, smStorageApp.getId());
			} else if (!StringUtils.equals(smStorageApp.getWorkflowStatus(), CommonConstants.WORKFLOW_STATUS_50) && StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_10)) {
				// 保存出入库申请信息
				saveSmStorageAppInfo(smStorageApp, type);
				actTaskService.complete(smStorageApp.getAct().getTaskId(), smStorageApp
						.getAct().getProcInsId(), null, null, null);
			} else if (StringUtils.equals(optStattus, CommonConstants.WORKFLOW_STATUS_70)) {
				// 删除流程实例
				actProcessService.deleteProcIns(smStorageApp.getAct().getProcInsId(), "删除["
						+ title + "]流程");
				// 删除历史任务实例
				List<HistoricTaskInstance> hisTaskList = historyService
						.createHistoricTaskInstanceQuery()
						.processInstanceId(smStorageApp.getAct().getProcInsId()).list();
				for (HistoricTaskInstance hisTask : hisTaskList) {
					historyService.deleteHistoricTaskInstance(hisTask.getId());
				}

				// 删除出入库申请信息
				deleteSmStorageAppInfo(smStorageApp.getId());
			}
		}
	}

	/**
	 * 保存出入库申请信息
	 */
	@Transactional(readOnly = false)
	public void saveSmStorageAppInfo(SmStorageApp smStorageApp, String type) {

		// 出入库申请信息保存
		this.saveSmStorageApp(smStorageApp, type);
		// 出入库申请信息明细保存
		this.saveSmStorageAppDtl(smStorageApp, type);
	}
	
	/**
	 * 出入库申请信息保存
	 * 
	 */
	@Transactional(readOnly = false)
	public void saveSmStorageApp(SmStorageApp smStorageApp, String type) {
		
		if (StringUtils.isEmpty(smStorageApp.getId())) {
			
			String storageType = null;

			if (StringUtils.equals(type, "sendBack")) {
				storageType = CommonConstants.STORAGE_TYPE_13;
			} else if (StringUtils.equals(type, "refund")) {
				storageType = CommonConstants.STORAGE_TYPE_14;
			} else if (StringUtils.equals(type, "exchange")) {
				storageType = CommonConstants.STORAGE_TYPE_12;
			} else if (StringUtils.equals(type, "other")) {
				storageType = CommonConstants.STORAGE_TYPE_16;
			}
			smStorageApp.setStorageType(storageType);
			smStorageApp.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
			smStorageApp.setDelFlag(CommonConstants.NO);
			
			smStorageApp.preInsert();
			inStorageManagementDao.insertSmStorageApp(smStorageApp);
		} else {
			
			SmStorageApp updateSmStorageApp = inStorageManagementDao.getSmStorageAppInfoById(smStorageApp.getId());
			
			updateSmStorageApp.setNewRemarks(smStorageApp.getNewRemarks());
			updateSmStorageApp.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
			updateSmStorageApp.preUpdate();
			inStorageManagementDao.updateSmStorageApp(updateSmStorageApp);
		}
	}
	
	/**
	 * 出入库申请信息明细保存
	 * 
	 */
	@Transactional(readOnly = false)
	public void saveSmStorageAppDtl(SmStorageApp smStorageApp, String type) {
		
		inStorageManagementDao.deleteSmStorageAppDtlByAppId(smStorageApp.getId());
		
		if (StringUtils.equals(type, "other")) {

			for (SmStorageAppDtl smStorageAppDtl : smStorageApp.getSmStorageAppDtlList()) {

				// 申请ID
				smStorageAppDtl.setAppId(smStorageApp.getId());

				smStorageAppDtl.preInsert();
				inStorageManagementDao.insertSmStorageAppDtl(smStorageAppDtl);
			}
		} else {

			for (String selectedLendingId : smStorageApp.getSelectedList()) {
				
				int index = Integer.parseInt(selectedLendingId);
				
				SmStorageAppDtl smStorageAppDtl = smStorageApp.getSmStorageAppDtlList().get(index);
				// 申请ID
				smStorageAppDtl.setAppId(smStorageApp.getId());
				
				// 退回库房
				if (StringUtils.equals(type, "refund")) {
					smStorageAppDtl.setWarehouse(smStorageApp.getWarehouse());
				}

				smStorageAppDtl.preInsert();
				inStorageManagementDao.insertSmStorageAppDtl(smStorageAppDtl);
			}
		}
	}
	
	/**
	 * 取得申请人信息
	 * 
	 */
	public void getApplierInfo(SmStorageApp smStorageApp) {
		SmStorageApp smStorageAppTemp = inStorageManagementDao.getSmStorageAppInfoById(smStorageApp.getId());
		
		if (smStorageAppTemp != null) {
			smStorageApp.setCreateBy(smStorageAppTemp.getCreateBy());
		}
	}
	
	/**
	 * 保存出入库信息
	 * 
	 */
	@Transactional(readOnly = false)
	public String creatSmStorageInfo(SmStorageApp smStorageApp, String type) {
		
		SmStorageInfo smStorageInfo = null;
		String storageType = null;

		if (StringUtils.equals(type, "sendBack")) {
			storageType = CommonConstants.STORAGE_TYPE_13;
		} else if (StringUtils.equals(type, "refund")) {
			storageType = CommonConstants.STORAGE_TYPE_14;
		} else if (StringUtils.equals(type, "exchange")) {
			storageType = CommonConstants.STORAGE_TYPE_12;
		} else if (StringUtils.equals(type, "other")) {
			storageType = CommonConstants.STORAGE_TYPE_16;
		}

		String code=commonService.getNextSequence("storage_id", storageType, 8);
		String storageId = storageType + code;

		for (SmStorageAppDtl smStorageAppDtl : smStorageApp.getSmStorageAppDtlList()) {
			
			smStorageInfo = new SmStorageInfo();

			// 出入库履历编号
			smStorageInfo.setStorageId(storageId);
			// 出入库类型
			smStorageInfo.setStorageType(storageType);

			// 处理日期
			smStorageInfo.setProcessDate(smStorageApp.getProcessDate());
			// 负责人
			smStorageInfo.setResponsiblePersonId(smStorageApp.getResponsiblePersonId());
			// 备注说明
			smStorageInfo.setRemarks(smStorageApp.getRemarks());
			// 出入库申请编号
			smStorageInfo.setStorageApplyId(smStorageApp.getId());

			// S/N
			smStorageInfo.setSnNo(smStorageAppDtl.getSnNo());
			// 物料号
			smStorageInfo.setMaterialNo(smStorageAppDtl.getMaterialNo());
			// 库房
			if (StringUtils.equals(type, "refund")) {
				smStorageInfo.setWarehouse(smStorageApp.getWarehouse());
			} else {
				smStorageInfo.setWarehouse(smStorageAppDtl.getWarehouse());
			}

			if (StringUtils.equals(type, "sendBack")) {

				String lendingId = smStorageAppDtl.getLendingId();
				SmLendingMat smLendingMat = inStorageManagementDao.getSmLendingMatById(lendingId);
				
				Date lendingDateFrom = smLendingMat.getLendingDateFrom();
				
				smStorageInfo.setLendingDateFrom(lendingDateFrom);
			}
			// 借出日期止
			smStorageInfo.setLendingDateTo(smStorageAppDtl.getLendingDateTo());
			// 数量
			smStorageInfo.setNum(Integer.parseInt(smStorageAppDtl.getNum()));
			
			smStorageInfo.preInsert();
			
			smStorageInfo.setCreateBy(smStorageApp.getCreateBy());
			inStorageManagementDao.insertSmStorageInfo(smStorageInfo);
		}
		
		return storageId;
	}

	/**
	 * 更新合同相关信息
	 * 
	 */
	@Transactional(readOnly = false)
	public void updateSoOrderInfo(SmStorageApp smStorageApp) {

		Map<String,Integer> refundNum = new HashMap<String, Integer>();
		for (SmStorageAppDtl smStorageAppDtl : smStorageApp.getSmStorageAppDtlList()) {
			
			if (refundNum.containsKey(smStorageAppDtl.getLineNo())) {
				
				refundNum.put(smStorageAppDtl.getLineNo(), refundNum.get(smStorageAppDtl.getLineNo()) + Integer.parseInt(smStorageAppDtl.getNum()));
			} else {
				refundNum.put(smStorageAppDtl.getLineNo(), Integer.parseInt(smStorageAppDtl.getNum()));
			}
		}
		if (!refundNum.isEmpty()) {

			SoOrder updSoOrder = soOrderDao
					.getEffectiveInfoByOrderNo(smStorageApp.getSmStorageAppDtlList().get(0).getOrderNo());
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

						if (refundNum.containsKey(soOrderDtl.getLineNo())) {

							int delNum = 0;
							if (!StringUtils.isEmptyNull(soOrderDtl.getDeliverNum())) {

								delNum = Integer.parseInt(soOrderDtl
										.getDeliverNum())
										- refundNum.get(soOrderDtl.getLineNo());
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
//
//										totalDelNum = totalDelNum + delNum;
//										soOrderDtlInfo.setDeliverNum(Integer.toString(delNum));
//										soOrderDtlInfo.setIfReturn(CommonConstants.YES);
//
//										soOrderDtlInfo.preUpdate();
//										soOrderDtlDao.update(soOrderDtlInfo);
//									}
//									lineNo = lineNo + 1;
//								}
//							} else {

								totalDelNum = totalDelNum + delNum;
								soOrderDtl.setDeliverNum(Integer.toString(delNum));
								soOrderDtl.setIfReturn(CommonConstants.YES);

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

	/**
	 * 保存其他相关信息信息
	 * 
	 */
	@Transactional(readOnly = false)
	public void updateOtherInfo(SmStorageApp smStorageApp, String type, String storageId) {
		SmSnInfo smSnInfo = null;
		SmWarehouseInfo smWarehouseInfo = null;
		SmLendingMat smLendingMat = null;

		for (SmStorageAppDtl smStorageAppDtl : smStorageApp
				.getSmStorageAppDtlList()) {
			if (StringUtils.equals(type, "other")) {

				if (StringUtils.equals(smStorageAppDtl.getIfSn(),
						CommonConstants.YES)) {

					smSnInfo = new SmSnInfo();

					// S/N
					smSnInfo.setSnNo(smStorageAppDtl.getSnNo());
					// 物料号
					smSnInfo.setMaterialNo(smStorageAppDtl.getMaterialNo());
					// 出入库履历编号
					smSnInfo.setStorageId(storageId);
					// 库房
					smSnInfo.setWarehouse(smStorageAppDtl.getWarehouse());
					// 机器类型
					smSnInfo.setMachineType(CommonConstants.MACHINE_TYPE_1);
					// 机器状态
					smSnInfo.setStatus(CommonConstants.MACHINE_STATUS_1);
					// 生产日期
					smSnInfo.setProductionDate(smStorageAppDtl
							.getProductionDate());
					// 入库日期
					smSnInfo.setEntryDate(smStorageApp.getProcessDate());

					smSnInfo.preInsert();
					smSnInfo.setCreateBy(smStorageApp.getCreateBy());
					inStorageManagementDao.insertSmSnInfo(smSnInfo);

					smWarehouseInfo = new SmWarehouseInfo();

					// 出入库履历编号
					smWarehouseInfo.setStorageId(storageId);
					// S/N
					smWarehouseInfo.setSnNo(smStorageAppDtl.getSnNo());
					// 物料号
					smWarehouseInfo.setMaterialNo(smStorageAppDtl.getMaterialNo());
					// 库房
					smWarehouseInfo.setWarehouse(smStorageAppDtl.getWarehouse());
					// 在库状态
					smWarehouseInfo
							.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
					// 数量
					smWarehouseInfo.setNum(smStorageAppDtl.getNum());

					smWarehouseInfo.preInsert();
					smWarehouseInfo.setCreateBy(smStorageApp.getCreateBy());
					inStorageManagementDao.insertSmWarehouseInfo(smWarehouseInfo);
				} else {
					SmWarehouseInfo searchSmWarehouseInfo = new SmWarehouseInfo();
					// 物料号
					searchSmWarehouseInfo.setMaterialNo(smStorageAppDtl.getMaterialNo());
					// 库房
					searchSmWarehouseInfo.setWarehouse(smStorageAppDtl.getWarehouse());
					// 在库状态
					searchSmWarehouseInfo
							.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);

					List<SmWarehouseInfo> smWarehouseInfoList = inStorageManagementDao
							.getSmWarehouseInfo(searchSmWarehouseInfo);

					if (smWarehouseInfoList != null
							&& smWarehouseInfoList.size() > 0) {
						SmWarehouseInfo updateSmWarehouseInfo = smWarehouseInfoList
								.get(0);
						
						int iNum = Integer.parseInt(smStorageAppDtl.getNum()) + Integer.parseInt(updateSmWarehouseInfo.getNum());

						updateSmWarehouseInfo.setNum(Integer.toString(iNum));

						updateSmWarehouseInfo.preUpdate();
						inStorageManagementDao
								.updateSmWarehouseInfo(updateSmWarehouseInfo);
					} else {

						smWarehouseInfo = new SmWarehouseInfo();

						// 出入库履历编号
						smWarehouseInfo.setStorageId(storageId);
						// S/N
						smWarehouseInfo.setSnNo(smStorageAppDtl.getSnNo());
						// 物料号
						smWarehouseInfo.setMaterialNo(smStorageAppDtl.getMaterialNo());
						// 库房
						smWarehouseInfo.setWarehouse(smStorageAppDtl.getWarehouse());
						// 在库状态
						smWarehouseInfo
								.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
						// 数量
						smWarehouseInfo.setNum(smStorageAppDtl.getNum());

						smWarehouseInfo.preInsert();
						smWarehouseInfo.setCreateBy(smStorageApp.getCreateBy());
						inStorageManagementDao.insertSmWarehouseInfo(smWarehouseInfo);
					}
				}

//				smWarehouseInfo = new SmWarehouseInfo();
//
//				// 出入库履历编号
//				smWarehouseInfo.setStorageId(storageId);
//				// S/N
//				smWarehouseInfo.setSnNo(smStorageAppDtl.getSnNo());
//				// 物料号
//				smWarehouseInfo.setMaterialNo(smStorageAppDtl.getMaterialNo());
//				// 库房
//				smWarehouseInfo.setWarehouse(smStorageAppDtl.getWarehouse());
//				// 在库状态
//				smWarehouseInfo
//						.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
//				// 数量
//				smWarehouseInfo.setNum(smStorageAppDtl.getNum());
//
//				smWarehouseInfo.preInsert();
//				inStorageManagementDao.insertSmWarehouseInfo(smWarehouseInfo);
			} else if (StringUtils.equals(type, "sendBack")) {

				if (!StringUtils.isEmptyNull(smStorageAppDtl.getSnNo())) {

					smLendingMat = new SmLendingMat();
					smLendingMat.setId(smStorageAppDtl.getLendingId());

					smLendingMat.preUpdate();
					inStorageManagementDao.deleteSmLendingMat(smLendingMat);

					SmSnInfo searchSmSnInfo = new SmSnInfo();
					searchSmSnInfo.setMaterialNo(smStorageAppDtl
							.getMaterialNo());
					searchSmSnInfo.setSnNo(smStorageAppDtl.getSnNo());

					smSnInfo = inStorageManagementDao
							.getSmSnInfo(searchSmSnInfo);
					if (smSnInfo != null) {

						// 出入库履历编号
						smSnInfo.setStorageId(storageId);
						// 库房
						smSnInfo.setWarehouse(smStorageAppDtl.getWarehouse());
						// 机器类型
						smSnInfo.setMachineType(CommonConstants.MACHINE_TYPE_2);
						// 机器状态
						smSnInfo.setStatus(CommonConstants.MACHINE_STATUS_1);
						// 入库日期
						smSnInfo.setEntryDate(smStorageAppDtl
								.getLendingDateTo());

						smSnInfo.preUpdate();
						inStorageManagementDao.updateSmSnInfo(smSnInfo);
					}

					SmWarehouseInfo searchWarehouseInfo = new SmWarehouseInfo();
					searchWarehouseInfo.setMaterialNo(smStorageAppDtl
							.getMaterialNo());
					searchWarehouseInfo.setSnNo(smStorageAppDtl.getSnNo());
//					searchWarehouseInfo.setWarehouse(smStorageAppDtl
//							.getWarehouse());

					List<SmWarehouseInfo> smWarehouseInfoList = inStorageManagementDao
							.getSmWarehouseInfo(searchWarehouseInfo);

					if (smWarehouseInfoList != null
							&& smWarehouseInfoList.size() > 0) {
						SmWarehouseInfo updateSmWarehouseInfo = smWarehouseInfoList
								.get(0);

						updateSmWarehouseInfo
								.setWarehouse(smStorageAppDtl.getWarehouse());

						updateSmWarehouseInfo
								.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);

						updateSmWarehouseInfo.preUpdate();
						inStorageManagementDao
								.updateSmWarehouseInfo(updateSmWarehouseInfo);
					}
				} else {

					smLendingMat = inStorageManagementDao.getSmLendingMatById(smStorageAppDtl.getLendingId());
					
					int dNum = Integer.parseInt(smLendingMat.getNum()) - Integer.parseInt(smStorageAppDtl.getNum());
					
					if (dNum > 0) {

						smLendingMat.setNum(Integer.toString(dNum));

						smLendingMat.preUpdate();
						inStorageManagementDao.updateSmLendingMat(smLendingMat);
					} else if (dNum == 0) {

						smLendingMat.preUpdate();
						inStorageManagementDao.deleteSmLendingMat(smLendingMat);
					}

					SmWarehouseInfo searchWarehouseInfo = new SmWarehouseInfo();
					searchWarehouseInfo.setStorageId(smLendingMat.getStorageId());
					searchWarehouseInfo.setMaterialNo(smStorageAppDtl
							.getMaterialNo());
					searchWarehouseInfo
							.setInStockStatus(CommonConstants.IN_STOCK_STATUS_3);

					List<SmWarehouseInfo> lendSmWarehouseInfoList = inStorageManagementDao
							.getSmWarehouseInfo(searchWarehouseInfo);
					if (lendSmWarehouseInfoList != null
							&& lendSmWarehouseInfoList.size() > 0) {

						SmWarehouseInfo updateSmWarehouseInfo = lendSmWarehouseInfoList
								.get(0);

						updateSmWarehouseInfo.setNum(Integer.toString(dNum));

						updateSmWarehouseInfo.preUpdate();
						inStorageManagementDao
								.updateSmWarehouseInfo(updateSmWarehouseInfo);
					}
					
					searchWarehouseInfo = new SmWarehouseInfo();
					searchWarehouseInfo.setMaterialNo(smStorageAppDtl
							.getMaterialNo());
					searchWarehouseInfo.setSnNo(smStorageAppDtl.getSnNo());
					searchWarehouseInfo.setWarehouse(smStorageAppDtl
							.getWarehouse());
					searchWarehouseInfo
							.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);

					List<SmWarehouseInfo> smWarehouseInfoList = inStorageManagementDao
							.getSmWarehouseInfo(searchWarehouseInfo);

					if (smWarehouseInfoList != null
							&& smWarehouseInfoList.size() > 0) {
						SmWarehouseInfo updateSmWarehouseInfo = smWarehouseInfoList
								.get(0);
						
						int iNum = Integer.parseInt(smStorageAppDtl.getNum()) + Integer.parseInt(updateSmWarehouseInfo.getNum());

						updateSmWarehouseInfo.setNum(Integer.toString(iNum));

						updateSmWarehouseInfo.preUpdate();
						inStorageManagementDao
								.updateSmWarehouseInfo(updateSmWarehouseInfo);
					} else {

						SmWarehouseInfo insertSmWarehouseInfo = new SmWarehouseInfo();

						// 出入库履历编号
						insertSmWarehouseInfo.setStorageId(storageId);
						// 物料号
						insertSmWarehouseInfo.setMaterialNo(smStorageAppDtl
								.getMaterialNo());
						// 库房
						insertSmWarehouseInfo.setWarehouse(smStorageAppDtl
								.getWarehouse());
						// 在库状态
						insertSmWarehouseInfo
								.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
						// 数量
						insertSmWarehouseInfo.setNum(smStorageAppDtl.getNum());

						insertSmWarehouseInfo.preInsert();
						insertSmWarehouseInfo.setCreateBy(smStorageApp.getCreateBy());
						inStorageManagementDao
								.insertSmWarehouseInfo(insertSmWarehouseInfo);
					}
				}
			} else if (StringUtils.equals(type, "refund")) {

				if (!StringUtils.isEmptyNull(smStorageAppDtl.getSnNo())) {

					SmSnInfo searchSmSnInfo = new SmSnInfo();
					searchSmSnInfo.setMaterialNo(smStorageAppDtl
							.getMaterialNo());
					searchSmSnInfo.setSnNo(smStorageAppDtl.getSnNo());

					smSnInfo = inStorageManagementDao
							.getSmSnInfo(searchSmSnInfo);
					if (smSnInfo != null) {

						// 出入库履历编号
						smSnInfo.setStorageId(storageId);
						// 库房
						smSnInfo.setWarehouse(smStorageApp.getWarehouse());
						// 机器类型
						smSnInfo.setMachineType(CommonConstants.MACHINE_TYPE_2);
						// 机器状态
						smSnInfo.setStatus(CommonConstants.MACHINE_STATUS_1);

						smSnInfo.setWarrantyDateFrom(null);
						smSnInfo.setWarrantyDateTo(null);

						smSnInfo.setIfInstall(null);
						smSnInfo.setInstallPersonId(null);

						smSnInfo.setLatestInstallDate(null);
						smSnInfo.setActualInstallDate(null);

						smSnInfo.setOrderNo(null);
						smSnInfo.setLineNo(null);

						smSnInfo.setOldOrderNo(smStorageAppDtl.getOrderNo());
						smSnInfo.setOldLineNo(smStorageAppDtl.getLineNo());

						smSnInfo.preUpdate();
						inStorageManagementDao.updateSmSnInfo(smSnInfo);
					}

					SmWarehouseInfo searchWarehouseInfo = new SmWarehouseInfo();
					searchWarehouseInfo.setMaterialNo(smStorageAppDtl
							.getMaterialNo());
					searchWarehouseInfo.setSnNo(smStorageAppDtl.getSnNo());
//					searchWarehouseInfo.setWarehouse(smStorageAppDtl
//							.getWarehouse());

					List<SmWarehouseInfo> smWarehouseInfoList = inStorageManagementDao
							.getSmWarehouseInfo(searchWarehouseInfo);

					if (smWarehouseInfoList != null
							&& smWarehouseInfoList.size() > 0) {
						SmWarehouseInfo updateSmWarehouseInfo = smWarehouseInfoList
								.get(0);

						updateSmWarehouseInfo
								.setWarehouse(smStorageApp.getWarehouse());

						updateSmWarehouseInfo
								.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);

						updateSmWarehouseInfo.preUpdate();
						inStorageManagementDao
								.updateSmWarehouseInfo(updateSmWarehouseInfo);
					}
				} else {

					SmWarehouseInfo searchWarehouseInfo = new SmWarehouseInfo();
					searchWarehouseInfo.setMaterialNo(smStorageAppDtl
							.getMaterialNo());
					searchWarehouseInfo.setSnNo(smStorageAppDtl.getSnNo());
					searchWarehouseInfo.setWarehouse(smStorageApp.getWarehouse());
					searchWarehouseInfo
							.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);

					List<SmWarehouseInfo> smWarehouseInfoList = inStorageManagementDao
							.getSmWarehouseInfo(searchWarehouseInfo);

					if (smWarehouseInfoList != null
							&& smWarehouseInfoList.size() > 0) {
						SmWarehouseInfo updateSmWarehouseInfo = smWarehouseInfoList
								.get(0);
						
						int iNum = Integer.parseInt(smStorageAppDtl.getNum()) + Integer.parseInt(updateSmWarehouseInfo.getNum());

						updateSmWarehouseInfo.setNum(Integer.toString(iNum));

						updateSmWarehouseInfo.preUpdate();
						inStorageManagementDao
								.updateSmWarehouseInfo(updateSmWarehouseInfo);
					} else {

						SmWarehouseInfo insertSmWarehouseInfo = new SmWarehouseInfo();

						// 出入库履历编号
						insertSmWarehouseInfo.setStorageId(storageId);
						// 物料号
						insertSmWarehouseInfo.setMaterialNo(smStorageAppDtl
								.getMaterialNo());
						// 库房
						insertSmWarehouseInfo.setWarehouse(smStorageApp.getWarehouse());
						// 在库状态
						insertSmWarehouseInfo
								.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
						// 数量
						insertSmWarehouseInfo.setNum(smStorageAppDtl.getNum());

						insertSmWarehouseInfo.preInsert();
						insertSmWarehouseInfo.setCreateBy(smStorageApp.getCreateBy());
						inStorageManagementDao
								.insertSmWarehouseInfo(insertSmWarehouseInfo);
					}
				}
			} else if (StringUtils.equals(type, "exchange")) {

				SmSnInfo searchSmSnInfo = new SmSnInfo();
				searchSmSnInfo.setMaterialNo(smStorageAppDtl
						.getMaterialNo());
				searchSmSnInfo.setSnNo(smStorageAppDtl.getSnNo());

				smSnInfo = inStorageManagementDao
						.getSmSnInfo(searchSmSnInfo);
				if (smSnInfo != null) {

					// 出入库履历编号
					smSnInfo.setStorageId(storageId);
					// 库房
					smSnInfo.setWarehouse(smStorageAppDtl.getWarehouse());
					// 机器状态
					smSnInfo.setStatus(CommonConstants.MACHINE_STATUS_1);

					smSnInfo.setWarrantyDateFrom(null);
					smSnInfo.setWarrantyDateTo(null);

					smSnInfo.setIfInstall(null);
					smSnInfo.setInstallPersonId(null);

					smSnInfo.setLatestInstallDate(null);
					smSnInfo.setActualInstallDate(null);
					
					if (StringUtils.isEmptyNull(smSnInfo.getOldOrderNo())) {
						smSnInfo.setOldOrderNo(smStorageAppDtl.getOrderNo());
						smSnInfo.setOldLineNo(smStorageAppDtl.getLineNo());
					}

					smSnInfo.preUpdate();
					inStorageManagementDao.updateSmSnInfo(smSnInfo);
				}

				SmWarehouseInfo searchWarehouseInfo = new SmWarehouseInfo();
				searchWarehouseInfo.setMaterialNo(smStorageAppDtl
						.getMaterialNo());
				searchWarehouseInfo.setSnNo(smStorageAppDtl.getSnNo());
//				searchWarehouseInfo.setWarehouse(smStorageAppDtl
//						.getWarehouse());

				List<SmWarehouseInfo> smWarehouseInfoList = inStorageManagementDao
						.getSmWarehouseInfo(searchWarehouseInfo);

				if (smWarehouseInfoList != null
						&& smWarehouseInfoList.size() > 0) {
					SmWarehouseInfo updateSmWarehouseInfo = smWarehouseInfoList
							.get(0);

					updateSmWarehouseInfo
							.setWarehouse(smStorageAppDtl.getWarehouse());

					updateSmWarehouseInfo
							.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);

					updateSmWarehouseInfo.preUpdate();
					inStorageManagementDao
							.updateSmWarehouseInfo(updateSmWarehouseInfo);
				}
			}
		}
	}
	
	/**
	 * 删除出入库申请信息
	 * 
	 * @param id 出入库申请
	 */
	@Transactional(readOnly = false)
	public void deleteSmStorageAppInfo(String id) {
		
		// 当前数据信息
		SmStorageApp smStorageApp = inStorageManagementDao.getSmStorageAppInfoById(id);

		if (smStorageApp != null) {
			smStorageApp.setDelFlag(CommonConstants.YES);
			smStorageApp.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_70);
			smStorageApp.setProcInsId(null);

			smStorageApp.preUpdate();
			inStorageManagementDao.updateSmStorageApp(smStorageApp);
		}
	}
	
	public SmSnInfo getSnInfo(String snNo, String materialNo) {
		
		// S/N管理信息
		SmSnInfo searchSmSnInfo = new SmSnInfo();
		searchSmSnInfo.setSnNo(snNo);
		searchSmSnInfo.setMaterialNo(materialNo);
		SmSnInfo smSnInfo = inStorageManagementDao.getSmSnInfo(searchSmSnInfo);
		
		return smSnInfo;
	}
	
	// 是否工程部人员
	public String ifEngineer(String responsiblePersonId) {
		
		String engineer = CommonConstants.NO;

		StringBuilder enNames = new StringBuilder();
		enNames.append("'crm-05',");
//		enNames.append("'crm-06',");
//		enNames.append("'crm-07',");
//		enNames.append("'crm-08',");
//		enNames.append("'crm-09',");
		enNames.append("'crm_22',");
		enNames.append("'crm_23',");
		enNames.append("'crm-10'");
		Map<String, String> qMap = new HashMap<String, String>();
		qMap.put("enNames", enNames.toString());
		qMap.put("id", responsiblePersonId);
		String userId = commonDao.getUserByRoles(qMap);
		
		if (!StringUtils.isEmptyNull(userId)) {
			engineer = CommonConstants.YES;
		}
		
		return engineer;
	}
}