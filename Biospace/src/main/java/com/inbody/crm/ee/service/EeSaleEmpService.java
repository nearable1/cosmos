/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.ee.dao.EeEmpEvaluationDao;
import com.inbody.crm.ee.dao.EeEmpPlanDao;
import com.inbody.crm.ee.dao.EeSaleEmpDao;
import com.inbody.crm.ee.entity.EeEmpEvaluation;
import com.inbody.crm.ee.entity.EeEmpPlan;
import com.inbody.crm.ee.entity.EeSaleEmp;
import com.inbody.crm.modules.act.utils.ActUtils;

/**
 * 销售员工评价Service
 * @author 11
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class EeSaleEmpService extends CrudService<EeSaleEmpDao, EeSaleEmp> {
	@Autowired
    private CommonService commonService;
	
	@Autowired
    private EeSaleEmpDao eeSaleEmpDao;
	
	@Autowired
    private EeEmpEvaluationDao eeEmpEvaluationDao;
	
	@Autowired
    private EeEmpPlanDao eeEmpPlanDao;
	 
	public EeSaleEmp get(String id) {
		return super.get(id);
	}
	
	public List<EeSaleEmp> getByUserAndYear(EeSaleEmp eeSaleEmp) {
		return eeSaleEmpDao.getByUserAndYear(eeSaleEmp);
	}
	
	public List<EeSaleEmp> findList(EeSaleEmp eeSaleEmp) {
		return super.findList(eeSaleEmp);
	}
	
	public EeSaleEmp findListbyId(EeSaleEmp eeSaleEmp) {
		EeSaleEmp newEeSaleEmp = this.get(eeSaleEmp.getId());
		if(newEeSaleEmp != null){
			//评价明细
			EeEmpEvaluation condition = new EeEmpEvaluation();
			condition.setAppId(eeSaleEmp.getId());
			List<EeEmpEvaluation> empEvaluations = eeEmpEvaluationDao.findList(condition);
			if(empEvaluations != null){
				newEeSaleEmp.setEeEmpEvaluations(empEvaluations);
			}
			//计划明细
			EeEmpPlan condition2 = new EeEmpPlan();
			condition2.setAppId(eeSaleEmp.getId());
			List<EeEmpPlan> empPlans = eeEmpPlanDao.findList(condition2);
			if(empPlans != null){
				newEeSaleEmp.setEeEmpPlans(empPlans);
			}
		}
		return newEeSaleEmp;
	}
	
	public Page<EeSaleEmp> findPage(Page<EeSaleEmp> page, EeSaleEmp eeSaleEmp) {
		return super.findPage(page, eeSaleEmp);
	}
	
	@Transactional(readOnly = false)
	public void delete(EeSaleEmp eeSaleEmp) {
		super.delete(eeSaleEmp);
	}
	
	 /**
     * 配件采购订单保存
     */
    @Transactional(readOnly = false)
    public String acSave(EeSaleEmp actEeSaleEmp) {

        // 配件采购订单申请发起
        // 临时保存、申请、或是再申请时执行订单信息保存
        if (StringUtils.isBlank(actEeSaleEmp.getAct().getProcInsId())
                || StringUtils.equals(actEeSaleEmp.getWorkflowStatus(),
                        CommonConstants.WORKFLOW_STATUS_10)
                || StringUtils.equals(actEeSaleEmp.getWorkflowStatus(),
                        CommonConstants.WORKFLOW_STATUS_20)        
                || StringUtils.equals(actEeSaleEmp.getWorkflowStatus(),
                        CommonConstants.WORKFLOW_STATUS_60)) {
            // 保存采购订单
        	this.save(actEeSaleEmp);
        }

        // 流程流转
        actEeSaleEmp.getAct().setBusinessId(actEeSaleEmp.getId());
        commonService.flowProcess(actEeSaleEmp.getAct(), ActUtils.ACT_EE_SALE_EMP,
                CommonConstants.PROCESS_TITLE_AC_SALE,
                actEeSaleEmp.getWorkflowStatus());

        return actEeSaleEmp.getId();
    }
	
    
    /**
	 * 采购订单保存
	 */
	@Transactional(readOnly = false)
	public void save(EeSaleEmp eeSaleEmp) {

		EeSaleEmp dbEeSaleEmp = null;
		if (!StringUtils.isEmptyNull(eeSaleEmp.getId())) {
			// 更新前采购订单取得
			dbEeSaleEmp = this.get(eeSaleEmp.getId());

            // 排他性验证，更新日时不相等，数据已被更改
            if (DateUtils.compareDate(eeSaleEmp.getUpdateDate(),
            		dbEeSaleEmp.getUpdateDate()) != 0) {
                throw new ServiceException("保存失败：数据已经被更改，请取得最新数据后再操作。");
            }
		}
		
		super.save(eeSaleEmp);
		
		//保存评价明细
		this.saveEmpEvaluation(eeSaleEmp,dbEeSaleEmp);
		
		//计划明细表
		this.saveEmpPlan(eeSaleEmp,dbEeSaleEmp);
		
	}
	
	/**
	 * 员工评价明细
	 * 
	 * @param purchaseOrd
	 *        画面数据
	 * @param dbPurchaseOrd
	 *        DB中更新前的数据
	 */
	@Transactional(readOnly = false)
	public void saveEmpEvaluation(EeSaleEmp eeSaleEmp,
			EeSaleEmp dbEeSaleEmp) {
		// 更新/登录判断
		if (dbEeSaleEmp == null) {
			// 采购明细登录处理
			for (EeEmpEvaluation bean : eeSaleEmp.getEeEmpEvaluations()) {
				
				if(StringUtils.isEmpty(bean.getDescribe1())&&StringUtils.isEmpty(bean.getDescribe2())
						&StringUtils.isEmpty(bean.getDescribe3())&&StringUtils.isEmpty(bean.getDescribe4())
						&StringUtils.isEmpty(bean.getAverage())){
					
				}else{
					bean.setAppId(eeSaleEmp.getId());
					
					bean.preInsert();
					eeEmpEvaluationDao.insert(bean);
				}
			}
		} else {
			
			// 画面采购明细行迭代
			for (EeEmpEvaluation bean : eeSaleEmp.getEeEmpEvaluations()) {
				EeEmpEvaluation empEvaluation = eeEmpEvaluationDao.get(bean.getId());
				//先删除在插入
				if(empEvaluation != null){
					//eeEmpEvaluationDao.deleteById(empEvaluation);
					if(StringUtils.equals(eeSaleEmp.getWorkflowStatus(),CommonConstants.WORKFLOW_STATUS_20)&&
							StringUtils.equals(empEvaluation.getEvaluateType(),"1")){
					}else{
						empEvaluation.setAppId(eeSaleEmp.getId());
						empEvaluation.setDescribe1(bean.getDescribe1());
						empEvaluation.setDescribe2(bean.getDescribe2());
						empEvaluation.setDescribe3(bean.getDescribe3());
						empEvaluation.setDescribe4(bean.getDescribe4());
						
						empEvaluation.setEvaluate1(bean.getEvaluate1());
						empEvaluation.setEvaluate2(bean.getEvaluate2());
						empEvaluation.setEvaluate3(bean.getEvaluate3());
						empEvaluation.setEvaluate4(bean.getEvaluate4());
						empEvaluation.setEvaluate5(bean.getEvaluate5());
						empEvaluation.setEvaluate6(bean.getEvaluate6());
						empEvaluation.setEvaluate7(bean.getEvaluate7());
						empEvaluation.setEvaluate8(bean.getEvaluate8());
						empEvaluation.setAverage(bean.getAverage());
						empEvaluation.preUpdate();
						eeEmpEvaluationDao.update(empEvaluation);
					}
				}else{
					
					if(StringUtils.isEmpty(bean.getDescribe1())&&StringUtils.isEmpty(bean.getDescribe2())
							&StringUtils.isEmpty(bean.getDescribe3())&&StringUtils.isEmpty(bean.getDescribe4())
							&StringUtils.isEmpty(bean.getAverage())){
						EeEmpEvaluation empEvaluation1 = eeEmpEvaluationDao.get(bean.getId());
						if(empEvaluation1 != null ){
							
							eeEmpEvaluationDao.deleteById(empEvaluation1);
						}
					}else{
						bean.setAppId(eeSaleEmp.getId());
						
						bean.preInsert();
						eeEmpEvaluationDao.insert(bean);
					}
				}
				
			}
			
			
		}
	}
	
	/**
	 * 员工计划明细
	 * 
	 * @param purchaseOrd
	 *        画面数据
	 * @param dbPurchaseOrd
	 *        DB中更新前的数据
	 */
	@Transactional(readOnly = false)
	public void saveEmpPlan(EeSaleEmp eeSaleEmp,
			EeSaleEmp dbEeSaleEmp) {
		// 行号
		int lineNo = 0;
		// 更新/登录判断
		if (dbEeSaleEmp == null) {
			// 采购明细登录处理
			for (EeEmpPlan bean : eeSaleEmp.getEeEmpPlans()) {
				// 明细行号自增
				lineNo = lineNo + 1;
				// 计划订单号
				bean.setAppId(eeSaleEmp.getId());
				bean.setLineNo(String.valueOf(lineNo));
				if(StringUtils.equals(bean.getShowArea(), "2")){
					bean.setDisplayItem6(eeSaleEmp.getNextYearMemo());
				}
				
				bean.preInsert();
				eeEmpPlanDao.insert(bean);
			}
		} else {
			
			// 画面采购明细行迭代
			for (EeEmpPlan bean : eeSaleEmp.getEeEmpPlans()) {
				EeEmpPlan newPlan = eeEmpPlanDao.get(bean.getId());
				//先删除在插入
				if(newPlan != null){
					eeEmpPlanDao.deleteById(newPlan);
				}
				// 明细行号自增
				lineNo = lineNo + 1;
				// 计划订单号
				bean.setAppId(eeSaleEmp.getId());
				bean.setLineNo(String.valueOf(lineNo));
				if(StringUtils.equals(bean.getShowArea(), "2")){
					bean.setDisplayItem6(eeSaleEmp.getNextYearMemo());
				}
				
				bean.preInsert();
				eeEmpPlanDao.insert(bean);
			}
			
			
		}
	}
}