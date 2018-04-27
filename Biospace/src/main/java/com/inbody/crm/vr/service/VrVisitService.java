/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.vr.dao.VrVisitDao;
import com.inbody.crm.vr.dao.VrVisitDtlDao;
import com.inbody.crm.vr.entity.VrVisit;
import com.inbody.crm.vr.entity.VrVisitDtl;

/**
 * 单表生成Service
 * @author NSSOL
 * @version 2017-07-20
 */
@Service
@Transactional(readOnly = true)
public class VrVisitService extends CrudService<VrVisitDao, VrVisit> {
	//
	private static final String DEFAULT_TITLE = "拜访申请";
	
	private static final String DEFAULT_REPORT_TITLE = "拜访报告";
	
	//流水号
	private static final String VISIT_SEQUENCE = "visitSequence";
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VrVisitDtlDao vrVisitDtlDao;
	
	public VrVisit get(String id) {
		return super.get(id);
	}
	
	public List<VrVisit> findList(VrVisit vrVisit) {
		return super.findList(vrVisit);
	}
	
	public Page<VrVisit> findPage(Page<VrVisit> page, VrVisit vrVisit) {
		return super.findPage(page, vrVisit);
	}
	
	@Transactional(readOnly = false)
	public void delete(VrVisit vrVisit) {
		super.delete(vrVisit);
	}
	
	public VrVisit getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}
	
	/**
	 * 审核新增或编辑
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void applySave(VrVisit vrVisit) {
		String workflowStatus = vrVisit.getWorkflowStatus();
		// 启动流程
		if (StringUtils.isBlank(vrVisit.getAct().getProcInsId())
				|| StringUtils.equals(vrVisit.getWorkflowStatus(),
						CommonConstants.WORKFLOW_STATUS_10)) {
			// 保存采购订单
			vrVisit.setWorkflowStatus(null);
			this.save(vrVisit);
		}
		// 流程流转
		vrVisit.getAct().setBusinessId(vrVisit.getId());
		commonService.flowProcess(vrVisit.getAct(), ActUtils.VISITING_AUDIT,
				DEFAULT_TITLE,workflowStatus);
	}
	/**
	 * 审核新增或编辑
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void reportSave(VrVisit vrVisit) {
		
		// 启动流程
		if (StringUtils.isBlank(vrVisit.getAct().getProcInsId())
				|| StringUtils.equals(vrVisit.getWorkflowStatus2(),
						CommonConstants.WORKFLOW_STATUS_10)) {
			// 保存采购订单
			this.save01(vrVisit);
		}else if(StringUtils.equals(vrVisit.getWorkflowStatus2(),
				CommonConstants.WORKFLOW_STATUS_20)
				||StringUtils.equals(vrVisit.getWorkflowStatus2(),
						CommonConstants.WORKFLOW_STATUS_30)
				||StringUtils.equals(vrVisit.getWorkflowStatus2(),
						CommonConstants.WORKFLOW_STATUS_50)){
			for (VrVisitDtl bean : vrVisit.getVrVisitDtlList()) {
				VrVisitDtl newPlan = vrVisitDtlDao.get(bean.getId());
				// 计划订单号
				if(StringUtils.equals(vrVisit.getWorkflowStatus2(),
						CommonConstants.WORKFLOW_STATUS_20)
						||StringUtils.equals(vrVisit.getWorkflowStatus2(),
								CommonConstants.WORKFLOW_STATUS_30)){
				newPlan.setLeaderOpinion(bean.getLeaderOpinion());
				}
				newPlan.setDirectorOpinion(bean.getDirectorOpinion());
				
				newPlan.preUpdate();
				vrVisitDtlDao.update(newPlan);
			}
		}
		// 流程流转
		if(!StringUtils.equals(vrVisit.getWorkflowStatus2(),
				CommonConstants.WORKFLOW_STATUS_50)){
			vrVisit.getAct().setBusinessId(vrVisit.getId());
			commonService.flowProcessStatus2(vrVisit.getAct(), ActUtils.VISITING_REPORT_AUDIT,
					DEFAULT_REPORT_TITLE,
					vrVisit.getWorkflowStatus2());
		}
			
	}
	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save01(VrVisit vrVisit) {

		VrVisit dbVrVisit = null;
		if (!StringUtils.isEmptyNull(vrVisit.getId())) {
			// 更新前采购订单取得
			dbVrVisit = this.get(vrVisit.getId());

            // 排他性验证，更新日时不相等，数据已被更改
            /*if (DateUtils.compareDate(vrVisit.getUpdateDate(),
            		dbVrVisit.getUpdateDate()) != 0) {
                throw new ServiceException("保存失败：数据已经被更改，请取得最新数据后再操作。");
            }*/
		}
		//super.save(vrVisit);
		//保存明细
		this.saveVisitDtl(vrVisit,dbVrVisit);
		
	}
	
	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save(VrVisit vrVisit) {

		VrVisit dbVrVisit = null;
		if (!StringUtils.isEmptyNull(vrVisit.getId())) {
			// 更新前
			dbVrVisit = this.get(vrVisit.getId());

            // 排他性验证，更新日时不相等，数据已被更改
            /*if (DateUtils.compareDate(vrVisit.getUpdateDate(),
            		dbVrVisit.getUpdateDate()) != 0) {
                throw new ServiceException("保存失败：数据已经被更改，请取得最新数据后再操作。");
            }*/
		}else{
			vrVisit.setVisitNo(this.getVisitNo());
		}
		
		super.save(vrVisit);
		
		//保存明细
		this.saveVisitDtl(vrVisit,dbVrVisit);
		
	}
	/**
	 * 拜访明细
	 * 
	 * @param purchaseOrd
	 *        画面数据
	 * @param dbPurchaseOrd
	 *        DB中更新前的数据
	 */
	@Transactional(readOnly = false)
	public void saveVisitDtl(VrVisit vrVisit,
			VrVisit dbVrVisit) {
		// 行号
		int lineNo = 0;
		// 更新/登录判断
		if (dbVrVisit == null) {
			for (VrVisitDtl bean : vrVisit.getVrVisitDtlList()) {
				// 明细行号自增
				lineNo = lineNo + 1;
				
				if(StringUtils.isEmpty(bean.getExpDateFrom())){
					bean.setExpDateFrom(null);
				}
				if(StringUtils.isEmpty(bean.getActDateFrom())){
					bean.setActDateFrom(null);
				}
				bean.setVisitNo(vrVisit.getVisitNo());
				bean.setLineNo(String.valueOf(lineNo));
				bean.setProcInsId(vrVisit.getProcInsId());
				bean.preInsert();
				vrVisitDtlDao.insert(bean);
			}
		} else {
			//整单清空dtl
			VrVisitDtl newPlan = new VrVisitDtl();
			newPlan.setVisitNo(vrVisit.getVisitNo());
			vrVisitDtlDao.deleteByVisitNo(newPlan);
			
			for (VrVisitDtl bean : vrVisit.getVrVisitDtlList()) {
				/*VrVisitDtl newPlan = vrVisitDtlDao.get(bean.getId());
				//先删除在插入
				if(newPlan != null){
					vrVisitDtlDao.deleteById(newPlan);
				}*/
				// 明细行号自增
				lineNo = lineNo + 1;
				// 计划订单号
				bean.setVisitNo(vrVisit.getVisitNo());
				bean.setLineNo(String.valueOf(lineNo));
				bean.setProcInsId(vrVisit.getProcInsId());
				if(StringUtils.isEmpty(bean.getExpDateFrom())){
					bean.setExpDateFrom(null);
				}
				if(StringUtils.isEmpty(bean.getActDateFrom())){
					bean.setActDateFrom(null);
				}
				
				bean.preInsert();
				vrVisitDtlDao.insert(bean);
			}
			
			
		}
	}
	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void auditSave(VrVisit vrVisit) {
		
		// 设置意见
		vrVisit.getAct().setComment(("0".equals(vrVisit.getAct().getFlag())?"[同意] ":"[驳回] ")+vrVisit.getAct().getComment());
		
		vrVisit.preUpdate();
		dao.update(vrVisit);
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", vrVisit.getAct().getFlag());
		actTaskService.complete(vrVisit.getAct().getTaskId(), vrVisit.getAct().getProcInsId(), vrVisit.getAct().getComment(), vars);

	}
	
	@Transactional(readOnly = false)
	public String getVisitNo() {
		
		return "BF"+DateUtils.getDate(Global.DEFAULT_DATE_YYMM)+commonService.getNextSequence(VISIT_SEQUENCE,DateUtils.getDate("yyyy"),5);
	}
	
	
}