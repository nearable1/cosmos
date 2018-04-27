package com.inbody.crm.common.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.EmployeeSearch;
import com.inbody.crm.common.entity.SelectEntity;
import com.inbody.crm.common.entity.SmPackageInfoEntity;
import com.inbody.crm.common.persistence.CommonDao;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.dao.ActDao;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.act.service.ActProcessService;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.vr.dao.VrVisitDao;
import com.inbody.crm.vr.entity.VrVisit;
@Service
@Transactional(readOnly = false)
public class CommonService extends BaseService {
	@Autowired
	protected CommonDao dao;
	
	@Autowired
	protected ActDao actDao;
	
	@Autowired
	protected VrVisitDao vrVisitDao;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private ActProcessService actProcessService;
	
	/**
	 * 获取下一个流水号(（取得当前业务编码下对应条件的下一个流水号）)
	 * @param sequenceCode 业务码
	 * @param keyCode 条件
	 * @return
	 */
	public int getNextSequence(String sequenceCode,String keyCode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("sequence_code", sequenceCode);
		map.put("key_code", keyCode);
        
        return dao.getNextSequence(map);
	}
	
	/**
	 * 获取下一个流水号(（取得当前业务编码下对应条件的下一个流水号）)
	 * @param sequenceCode 业务码
	 * @param keyCode 条件
	 * @param length 返回数值的位数，不足则补0
	 * @return
	 */
	public String getNextSequence(String sequenceCode,String keyCode,int length)
	{
        int seq = getNextSequence(sequenceCode,keyCode);
        
        String format = "";
        for(int i=0; i < length; i++){
        	format += "0";
        }
        DecimalFormat df = new DecimalFormat(format);
        return df.format(seq);
	}
	
	/**
	 * 获取下一个流水号（取得当前业务编码下生效的下一个流水号）
	 * @param sequenceCode 业务编码
	 * @return
	 */
	public int getNextSequence(String sequenceCode)
	{
		return getNextSequence(sequenceCode,"");
	}
	
	/**
	 * 获取下一个流水号（取得当前业务编码下生效的下一个流水号）
	 * @param sequenceCode 业务编码
	 * @param length 返回数值的位数，不足则补0
	 * @return
	 */
	public String getNextSequence(String sequenceCode,int length)
	{
		return getNextSequence(sequenceCode,"",length);
	}

    /**
     * 通过搜索关键字和类型模糊查询物料信息
     * 
     * @param page
     *            分页器
     * @param keyword
     *            物料模糊查询关皱键字
     * @param type
     *            物料类型
     * 
     * @return 物料信息List
     */
    public Page<SelectEntity> getMaterialDictList(Page<SelectEntity> page, String keyword,
            String type) {

        if (StringUtils.isBlank(keyword)) {
            return page;
        }
        Map<String, Object> qMap = new HashMap<String, Object>();
        qMap.put("key", keyword);
        qMap.put("type", type);
        qMap.put("page", page);

        return page.setList(dao.getMaterialDictList(qMap));
    }

	/**
	 * 通过物料号取得物料信息
	 * 
	 * @param materialNo 物料号
	 * 
	 * @return 物料信息
	 */
	public SelectEntity getMaterialInfoByNo(String materialNo) {
		return dao.getMaterialByNo(materialNo);
	}
	
	/**
	 * 通过搜索关键字和类型模糊查询客户信息
	 * 
	 * @param keyword 客户模糊查询关皱键字
	 * @param type 客户类型
	 * 
	 * @return 客户信息List
	 */
	public List<SelectEntity> getCustomerDictList(String keyword, String type) {
		
		if (StringUtils.isBlank(keyword)) {
			return Lists.newArrayList();
		}
		Map<String, String> qMap = new HashMap<String, String>();
		qMap.put("key", keyword);
		qMap.put("type", type);
		return dao.getCustomerDictList(qMap);
	}

	/**
	 * 通过客户ID取得客户信息
	 * 
	 * @param customersId 客户ID
	 * 
	 * @return 客户信息
	 */
	public SelectEntity getCustomerInfoByNo(String customersId) {
		return dao.getCustomerById(customersId);
	}

	/**
	 * 通过物料号取得套餐物料信息
	 * 
	 * @param materialNo 物料号
	 * 
	 * @return 物料信息List
	 */
	public List<SmPackageInfoEntity> getSmPackageInfoByMaterialNo(String materialNo) {
		
		List<SmPackageInfoEntity> list = dao.getSmPackageInfoByMaterialNo(materialNo);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return Lists.newArrayList();
		}
	}

	/**
	 * 签约方信息取得
	 * @param employeeId
	 *        人
	 * @return 组信息
	 */
	public String getOrganize(String employeeId) {
		String officeId = dao.getOrganize(employeeId);
		
		return officeId;
	}

	/**
	 * 标准价格取得
	 * @param priceSystem
	 *        价格体系
	 * @param customerId
	 *        客户
	 * @param materialNo
	 *        物料号
	 * @param region
	 *        地区
	 * @param industry
	 *        行业
	 * @return 标准价格
	 */
	public String getStandardPrice(String priceSystem, String customerId, String materialNo, String region, String industry) {

		Map<String, String> params = new HashMap<String, String>();
		String standardPrice = null;

		params.put("materialNo", materialNo);
		params.put("priceSystem", priceSystem);
		
		if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_3)) {
			
			if (!StringUtils.isEmptyNull(customerId)) {

				params.put("customerId", customerId);
				
				standardPrice = dao.getStandardPrice3(params);
				
				if (StringUtils.isEmptyNull(standardPrice)) {
					
					String customerParentCo = dao.getCustomerParentCo(customerId);
					
					if (!StringUtils.isEmptyNull(customerParentCo)) {

						params.put("customerId", customerParentCo);
						
						standardPrice = dao.getStandardPrice3(params);
					}
				}
			} else {
				standardPrice = dao.getStandardPrice3(params);
			}
		}
		
		if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_1)) {
			if (!StringUtils.isEmptyNull(customerId)) {
				params.put("customerId", customerId);
			}
			if (!StringUtils.isEmptyNull(region)) {
				params.put("region", region);
			}
			if (!StringUtils.isEmptyNull(industry)) {
				params.put("industry", industry);
			}
			
			standardPrice = dao.getStandardPrice1(params);
		}
		
		if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_5)) {
			if (!StringUtils.isEmptyNull(region)) {
				params.put("region", region);
			}
			if (!StringUtils.isEmptyNull(industry)) {
				params.put("industry", industry);
			}
			
			standardPrice = dao.getStandardPrice5(params);
		}
		
		if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_2)
				|| StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_4)) {
			
			if (!StringUtils.isEmptyNull(region)) {
				params.put("region", region);
			}
			if (!StringUtils.isEmptyNull(industry)) {
				params.put("industry", industry);
			}
			
			standardPrice = dao.getStandardPrice24(params);
			if (!StringUtils.isEmptyNull(region) || !StringUtils.isEmptyNull(industry)) {

				if (StringUtils.isEmptyNull(standardPrice)) {
					params.remove("region");
					params.remove("industry");
					
					standardPrice = dao.getStandardPrice24(params);
				}
			}
		}

//		if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_1)
//				|| StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_3)
//				|| StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_5)) {
//
//			params.put("customerId", customerId);
//		}
//		
//		if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_1)
//				|| StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_2)
//				|| StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_4)) {
//
//			params.put("region", region);
//		}
//		
//		if (!StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_3)) {
//
//			params.put("industry", industry);
//		}
//
//		if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_2)
//				|| StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_4)) {
//			if (StringUtils.isEmptyNull(region) || StringUtils.isEmptyNull(industry)) {
//				params.put("yesNo", CommonConstants.YES);
//			}
//		}
//		
//		String standardPrice = dao.getStandardPrice(params);
//		
//		if (StringUtils.isEmptyNull(standardPrice)) {
//			
//			params = new HashMap<String, String>();
//			params.put("materialNo", materialNo);
//
//			if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_2)
//					|| StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_4)) {
//				params.put("yesNo", CommonConstants.YES);
//				standardPrice = dao.getStandardPrice(params);
//			} else if (StringUtils.equals(priceSystem, CommonConstants.PRICE_SYSTEM_3)) {
//				
//				String customerParentCo = dao.getCustomerParentCo(customerId);
//				
//				if (!StringUtils.isEmptyNull(customerParentCo)) {
//
//					params.put("customerId", customerParentCo);
//					
//					standardPrice = dao.getStandardPrice(params);
//				}
//			}
//		}
		
		return standardPrice;
	}

	/**
	 * 工作流流转
	 * 
	 * @param act
	 *            流程信息
	 * @param procDef
	 *            流程定义常量
	 * @param procTitle
	 *            流程标题
	 * @param workflowStatus
	 *            画面操作所对应的流程状态
	 *         
	 * @return procInsId 流程实例id
	 */
	@Transactional(readOnly = false)
	public String flowProcess(Act act, String[] procDef, String procTitle,
			String workflowStatus) {

		return commonFlowProcess(act, procDef, procTitle,
				workflowStatus, true);
	}
	
	/**
	 * 工作流流转
	 * 
	 * @param act
	 *            流程信息
	 * @param procDef
	 *            流程定义常量
	 * @param procTitle
	 *            流程标题
	 * @param workflowStatus
	 *            画面操作所对应的流程状态
	 *         
	 * @return procInsId 流程实例id
	 */
	@Transactional(readOnly = false)
	public String flowProcessStatus2(Act act, String[] procDef, String procTitle,
			String workflowStatus) {

		return commonFlowProcess(act, procDef, procTitle,
				workflowStatus, false);
	}

    /**
     * 工作流流转<br>
     * <br>
     * 说明：<br>
     * 工作流的业务表ID（businessId）并不一定总是等于画面的主业务表ID（mainBusinessId），例如：<br>
     * 例1&nbsp;&nbsp;合同发票申请流程：businessId = 发票管理.ID，主业务表ID = 合同.ID；<br>
     * 例2&nbsp;&nbsp;报价单发票申请流程：businessId = 发票管理.ID，主业务表ID = 报价单.ID；<br>
     * <br>
     * 基于以上原因，启动流时指定的流程实例业务KEY即InstanceBusinessKey的结构为如下：<br>
     * 如果工作流业务表即为主业务表时，流程实例业务KEY=工作流业务表表名:工作流业务表ID;<br>
     * 如果工作流业务表不为主业务表时，流程实例业务KEY=工作流业务表表名:工作流业务表ID:主业务表ID;<br>
     * 
     * @param act
     *            流程信息
     * @param procDef
     *            流程定义常量
     * @param procTitle
     *            流程标题
     * @param workflowStatus
     *            画面操作所对应的流程状态
     * @param isNormalFlow
     *            业务表工作流状态字段：true, workflowStatus; false, workflowStatus2
     * 
     * @return procInsId 流程实例ID
     */
    public String commonFlowProcess(Act act, String[] procDef, String procTitle,
            String workflowStatus, boolean isNormalFlow) {

		// 流程实例id为空
		if (StringUtils.isBlank(act.getProcInsId())) {

			// 要设置的流程状态验证
			// 状态不为空 或不为 申请中 或 不为临时保存
			if (!(StringUtils.isBlank(workflowStatus)
					|| StringUtils.equals(CommonConstants.WORKFLOW_STATUS_10,
							workflowStatus)
					|| StringUtils.equals(CommonConstants.WORKFLOW_STATUS_60,
							workflowStatus))) {
				throw new ServiceException("流程[" + procTitle
						+ "]流转失败：业务流程状态不整合。");
			}

            // 启动流程
            String procInsId = null;
            if (StringUtils.isBlank(act.getMainBusinessId())) {
                procInsId = actTaskService.startProcess(procDef[0], procDef[1],
                        act.getBusinessId(), procTitle);
            } else {
                procInsId = actTaskService.startProcess(procDef[0], procDef[1],
                        act.getBusinessId() + ":" + act.getMainBusinessId(), procTitle);
            }

            // 状态为空默认申请中
            if (StringUtils.isBlank(workflowStatus)) {
                workflowStatus = CommonConstants.WORKFLOW_STATUS_10;
            }

            // 更新工作流业务主表workflowStatus字段状态
            act.setProcInsId(procInsId);
            // 业务主表名设置
            act.setBusinessTable(procDef[1]);
            // 流程状态设定
            act.setStatus(workflowStatus);
            // 更新业务表业务流程状态
            actDao.updateWorkFlowStatusByBusinessId(act);

			// 不为临时保存时
			if (!StringUtils.equals(CommonConstants.WORKFLOW_STATUS_60,
					workflowStatus)) {
				// 完成第一个任务，进入申请中状态
				actTaskService.completeFirstTask(procInsId, "[提交申请]",
						procTitle, null);
			}

		} else {

			// 业务流程状态为空
			if (StringUtils.isBlank(workflowStatus)) {
				throw new ServiceException("流程[" + procTitle
						+ "]流转失败：业务流程状态不整合。");
			}

			// 临时保存时，什么也不做
			if (StringUtils.equals(CommonConstants.WORKFLOW_STATUS_60,
					workflowStatus)) {
				return act.getProcInsId();
			}

			// db业务表的流程状态取得
			act.setBusinessTable(procDef[1]);
			String dbworkflowStatus = null;
			if(isNormalFlow){
				dbworkflowStatus = actDao.getWorkFlowStatusByBusinessId(act);
			}else{
				VrVisit vrVisit = vrVisitDao.get(act.getBusinessId());
				dbworkflowStatus = vrVisit.getWorkflowStatus2();
			}
			
			// 申请人取得
			String applyId = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceId(act.getProcInsId()).singleResult()
					.getStartUserId();

			// 只有临时保存 或 撤回 或 退回时 能够提出申请、临时保存、删除申请
			if (!(StringUtils.equals(dbworkflowStatus,
					CommonConstants.WORKFLOW_STATUS_60)
					|| StringUtils.equals(dbworkflowStatus,
							CommonConstants.WORKFLOW_STATUS_40) || StringUtils
						.equals(dbworkflowStatus,
								CommonConstants.WORKFLOW_STATUS_30))
					&& (StringUtils.equals(workflowStatus,
							CommonConstants.WORKFLOW_STATUS_10)
							|| StringUtils.equals(workflowStatus,
									CommonConstants.WORKFLOW_STATUS_60)
							|| StringUtils.equals(workflowStatus,
										CommonConstants.WORKFLOW_STATUS_70))) {
				throw new ServiceException("流程[" + procTitle
						+ "]流转失败：操作不合法。");
			}

			// 只有申请中能够撤回
			if (!StringUtils.equals(dbworkflowStatus,
					CommonConstants.WORKFLOW_STATUS_10)
					&& StringUtils.equals(workflowStatus,
							CommonConstants.WORKFLOW_STATUS_40)) {
				throw new ServiceException("流程[" + procTitle
						+ "]流转失败：操作不合法。");
			}

			// 只有申请人自已能够撤回、临时保存、再申请、删除
			if (!StringUtils.equals(applyId, UserUtils.getUser().getLoginName())
					&& (StringUtils.equals(workflowStatus,
							CommonConstants.WORKFLOW_STATUS_40)
							|| StringUtils.equals(workflowStatus,
									CommonConstants.WORKFLOW_STATUS_10)
							|| StringUtils.equals(workflowStatus,
										CommonConstants.WORKFLOW_STATUS_70))) {
				throw new ServiceException("流程[" + procTitle
						+ "]流转失败：您不是申请人，不能执行临时保存、再申请、撤回、删除等操作。");
			}

			// 只有申请中和审批中能退回、同意
			if (!(StringUtils.equals(dbworkflowStatus,
					CommonConstants.WORKFLOW_STATUS_10) || StringUtils.equals(
					dbworkflowStatus, CommonConstants.WORKFLOW_STATUS_20))
					&& (StringUtils.equals(workflowStatus,
							CommonConstants.WORKFLOW_STATUS_20) || StringUtils
							.equals(workflowStatus,
									CommonConstants.WORKFLOW_STATUS_30))) {
				throw new ServiceException("流程[" + procTitle
						+ "]流转失败：操作不合法。");
			}

			// 删除申请时
			if (StringUtils.equals(workflowStatus,
					CommonConstants.WORKFLOW_STATUS_70)) {
				// 删除流程实例
				actProcessService.deleteProcIns(act.getProcInsId(), "删除["
						+ procTitle + "]流程");
				// 删除历史任务实例
				List<HistoricTaskInstance> hisTaskList = historyService
						.createHistoricTaskInstanceQuery()
						.processInstanceId(act.getProcInsId()).list();
				for (HistoricTaskInstance hisTask : hisTaskList) {
					historyService.deleteHistoricTaskInstance(hisTask.getId());
				}

				// 业务表更新
				// 业务主表名设置
				act.setBusinessTable(procDef[1]);
				// 流程状态设定
				act.setStatus(workflowStatus);
				// 删除业务主表数据（del_flag置1，工作流状态置70，流程id置null）
				actDao.deleteBusinessDataById(act);
				return "";
			}

			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			if (StringUtils.equals(workflowStatus,
					CommonConstants.WORKFLOW_STATUS_20)) {
				// 通过审批
				vars.put("pass", "1");
				act.setComment("[节点审批通过] " + StringUtils.defaultString(act.getComment()));
			} else if (StringUtils.equals(workflowStatus,
					CommonConstants.WORKFLOW_STATUS_30)) {
				// 退回审批
				vars.put("pass", "0");
				act.setComment("[审批退回] " + StringUtils.defaultString(act.getComment()));
			} else if (StringUtils.equals(workflowStatus,
					CommonConstants.WORKFLOW_STATUS_40)) {
				act.setComment("[申请撤回] " + StringUtils.defaultString(act.getComment()));
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_10)
                    && (StringUtils.equals(dbworkflowStatus,
                            CommonConstants.WORKFLOW_STATUS_40)
                            || StringUtils.equals(dbworkflowStatus,
                                    CommonConstants.WORKFLOW_STATUS_30))) {
				act.setComment("[再申请] " + StringUtils.defaultString(act.getComment()));
            } else if (StringUtils.equals(workflowStatus,
                    CommonConstants.WORKFLOW_STATUS_10)
                    && StringUtils.equals(dbworkflowStatus,
                            CommonConstants.WORKFLOW_STATUS_60)) {
                act.setComment("[提交申请] " + StringUtils.defaultString(act.getComment()));
            }

            // 更新工作流业务主表workflowStatus字段状态
            // 业务主表名设置
            act.setBusinessTable(procDef[1]);
            // 流程状态设定
            act.setStatus(workflowStatus);
            // 更新业务表业务流程状态
            actDao.updateWorkFlowStatusByBusinessId(act);

			// 执行流程任务
			if (StringUtils.equals(workflowStatus,
					CommonConstants.WORKFLOW_STATUS_40)) {
//				actTaskService.addTaskComment(act.getTaskId(),
//						act.getProcInsId(), act.getComment());
				actTaskService.taskBack(act.getProcInsId(), vars);
			} else {
				actTaskService.complete(act.getTaskId(), act.getProcInsId(),
						act.getComment(), procTitle, vars);
			}
		}

		return act.getProcInsId();
	}

	/**
	 * 取得工作流申请/审批画面可操作状态<br>
	 * <br>
	 * 说明：<br>
	 * 目前有工作流的画面可以从【我的任务】和【查询一览】两个入口进入。<br>
	 * 此共通会根据流程实例id 和 业务id这两个参数进行判断画面是从哪个入口进入的。<br>
	 * 判断逻辑如下：<br>
	 * 1.当流程实例id 和 业务id都不为空，代表画面是从【我的任务】进入，此时画面可编辑根据工作流状态进行判断，可进行工作流相关操作，<br>
	 * 2.当流程实例id为空 且 业务id不为空，代表画面是从【查询一览】进入，此时画面不可编辑，不可进行工作流相关操作，<br>
	 * 3.当流程实例id为空 和 业务id都为空，代表工作流画面状态为提交申请前，此时画面可编辑，可提交申请，
	 * 
	 * @param act
	 *            工作流信息
	 * @param businessId
	 *            业务主表数据id
	 * @param isNormalFlow
	 *            (work_flow_status:true,work_flow_status2:false)
	 * 
	 * 
	 * @return 画面状态Map，返回结果Map包含如下状态：<br>
	 *         状态标识，true: 可以进行该操作，false: 不可进行该操作<br>
	 *         canEdit：是否可以编辑<br>
	 *         canSave： 是否可以【临时保存】<br>
	 *         canApply： 是否可以【提交申请】<br>
	 *         canReapply： 是否可以【再申请】<br>
	 *         canBack： 是否可以【撤回】<br>
	 *         canApproval： 是否可以【审批】<br>
	 *         canDelete：是否可以【删除】申请<br>
	 *         isApprovalCompleted：审批是否完成
	 */
	public Map<String, Boolean> getProcViewStatus(Act act,
			String businessId, boolean isNormalFlow) {

        // 流程实例id初始化
        String procInsId = "";
        // 工作流信息不为空
        if (act != null) {
            procInsId = act.getProcInsId();
        }

		// 流程实例id不为空 且 业务id为空，数据不整合
		if (!StringUtils.isBlank(procInsId) && StringUtils.isBlank(businessId)) {
			throw new ServiceException("参数不整合。流程实例ID存在时，业务ID不能为空。");
		}

		Map<String, Boolean> viewsts = new HashMap<String, Boolean>();
		// 画面是否可以[编辑]
		viewsts.put("canEdit", false);
		// 是否可以[临时保存]
		viewsts.put("canSave", false);
		// 是否可以[申请]
		viewsts.put("canApply", false);
		// 是否可以[再申请]
		viewsts.put("canReapply", false);
		// 是否可以[撤回]
		viewsts.put("canBack", false);
		// 是否可以审批
		viewsts.put("canApproval", false);
		// 是否可以删除申请
		viewsts.put("canDelete", false);
		// 是否审批完成
		viewsts.put("isApprovalCompleted", false);

		// 流程实例id为空 且 业务id为空，表示提交申请前，可临时保存、可申请、可编辑
		if (StringUtils.isBlank(procInsId) && StringUtils.isBlank(businessId)) {
			// 可以[临时保存]
			viewsts.put("canSave", true);
			// 可以[申请]
			viewsts.put("canApply", true);
			// 可编辑
			viewsts.put("canEdit", true);
			return viewsts;
		}

        // 流程实例id为空，业务id不为空，代表画面是从【查询一览】进入，此时画面不可编辑，不可进行工作流相关操作，
        if (StringUtils.isBlank(procInsId) && !StringUtils.isBlank(businessId)) {
            // 根据businessId查看流程是否完成
            long unfinishedCount = historyService
                    .createHistoricTaskInstanceQuery()
                    .processInstanceBusinessKeyLike("%" + businessId + "%")
                    .processUnfinished().count();
            if (unfinishedCount > 0) {
                // 未完成
                viewsts.put("isApprovalCompleted", false);
            }else{
                // 审批完成
                viewsts.put("isApprovalCompleted", true);
            }

            return viewsts;
        }

		// 流程实例取得
		ProcessInstance procIns = actTaskService.getProcIns(procInsId);
		// 流程实例为空
		if (procIns == null) {
			return viewsts;
		}

		// 实例定义key
		String[] procDef = StringUtils.split(procIns.getBusinessKey(), ":");
		// 实例定义keycheck
		if (procDef == null || procDef.length < 2
				|| StringUtils.isBlank(procDef[0])
				|| StringUtils.isBlank(procDef[1])) {
			throw new ServiceException("流程实例定义key取得失败。");
		}

        // 从业务主表取得工作流状态
        Act searchAct = new Act();
        searchAct.setBusinessTable(procDef[0]);
        searchAct.setBusinessId(procDef[1]);
        String workflowStatus = null;
        if (isNormalFlow) {
            workflowStatus = actDao.getWorkFlowStatusByBusinessId(searchAct);
        } else {
            VrVisit vrVisit = vrVisitDao.get(businessId);
            workflowStatus = vrVisit.getWorkflowStatus2();
        }

		// 画面控制
		// 当前登陆用户
		String loginName = UserUtils.getUser().getLoginName();
		// 申请人取得
		String applyId = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(procInsId).singleResult()
				.getStartUserId();
		// 审批人取得
		String assignee = taskService.createTaskQuery()
				.processInstanceId(procInsId).singleResult()
				.getAssignee();

        // 从[我的任务>已办任务]中详情链接进入业务画面时，
        if (StringUtils.equals(act.getStatus(), "finish")) {
            // 状态为申请中且是申请人，仅可对最近的一次申请撤回，否则只可查看
            if (StringUtils.equals(workflowStatus, CommonConstants.WORKFLOW_STATUS_10)
                    && StringUtils.equals(loginName, applyId)) {
                // 取得最后一次已完成的【提交申请】任务的任务id
                String lastFinishApplyTaskId = historyService
                        .createHistoricTaskInstanceQuery()
                        .processInstanceId(act.getProcInsId()).finished()
                        .orderByHistoricTaskInstanceEndTime().desc().list().get(0)
                        .getId();
                if (StringUtils.equals(act.getTaskId(), lastFinishApplyTaskId)) {
                    // 可撤回
                    viewsts.put("canBack", true);
                }
            }
            return viewsts;
        }

		// 状态为审请中
		if (StringUtils.equals(workflowStatus,
				CommonConstants.WORKFLOW_STATUS_10)) {
			// 审批人可审批
			if (StringUtils.equals(loginName, assignee)) {
				// 可审批
				viewsts.put("canApproval", true);
			}

			// 状态为审批中
		} else if (StringUtils.equals(workflowStatus,
				CommonConstants.WORKFLOW_STATUS_20)) {
			// 审批人可审批
			if (StringUtils.equals(loginName, assignee)) {
				// 可审批
				viewsts.put("canApproval", true);
			}

			// 退回中、撤回中、临时保存时可编辑，不可可审批，不可撤回
		} else if (StringUtils.equals(workflowStatus,
				CommonConstants.WORKFLOW_STATUS_30)
				|| StringUtils.equals(workflowStatus,
						CommonConstants.WORKFLOW_STATUS_40)
				|| StringUtils.equals(workflowStatus,
						CommonConstants.WORKFLOW_STATUS_60)) {
			// 申请人可临时保存、可再申请、可删除
			if (StringUtils.equals(loginName, applyId)) {
                // 可以[临时保存]
                viewsts.put("canSave", true);

                // 流程状态为临时保存时
                if (StringUtils.equals(workflowStatus,
                        CommonConstants.WORKFLOW_STATUS_60)) {
                    // 可以[提交申请]
                    viewsts.put("canApply", true);
                } else {
                    // 可以[再申请]
                    viewsts.put("canReapply", true);
                }
				// 可以[删除]
				viewsts.put("canDelete", true);
				// 可编辑
				viewsts.put("canEdit", true);
			}

			// 审批完成
		} else if (StringUtils.equals(workflowStatus,
				CommonConstants.WORKFLOW_STATUS_50)) {
			// 审批完成
			viewsts.put("isApprovalCompleted", true);
		}

		return viewsts;
	}
	
	/**
	 * 通过搜索关键字和类型模糊查询人员信息
	 * 
	 * @param keyword 人员模糊查询关皱键字
	 * @param type 人员类型
	 * 
	 * @return 人员信息List
	 */
	public List<SelectEntity> getEmployeeDictList(EmployeeSearch employeeSearch) {
		
		if (StringUtils.isBlank(employeeSearch.getKey())) {
			return Lists.newArrayList();
		}
//		Map<String, String> qMap = new HashMap<String, String>();
//		qMap.put("key", keyword);
//		qMap.put("type", type);
//		return dao.getEmployeeDictList(qMap, sqlMap);
		return dao.getEmployeeDictList(employeeSearch);
	}

	/**
	 * 根据员工ID取得已删除的员工信息
	 * 
	 * @param id 员工ID
	 * 
	 * @return 员工信息
	 */
	public SelectEntity getEmployeeInfoById(String employeeId) {
		return dao.getEmployeeInfoById(employeeId);
	}
}
