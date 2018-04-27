/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.ee.entity.EeEmpEvaluation;
import com.inbody.crm.vr.entity.VrVisitDtl;

/**
 * 员工评价DAO接口
 * @author 11
 * @version 2017-10-30
 */
@MyBatisDao
public interface EeEmpEvaluationDao extends CrudDao<EeEmpEvaluation> {
	
	/**
	 * 删除记录
	 * @param entity
	 * @return
	 */
	public void deleteById(EeEmpEvaluation entity);
}