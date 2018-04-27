/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.ee.entity.EeEmpPlan;
import com.inbody.crm.ee.entity.EeSaleEmp;

/**
 * 计划明细DAO接口
 * @author 11
 * @version 2017-10-30
 */
@MyBatisDao
public interface EeEmpPlanDao extends CrudDao<EeEmpPlan> {
	
	/**
	 * 删除记录
	 * @param entity
	 * @return
	 */
	public void deleteById(EeEmpPlan entity);
	
}