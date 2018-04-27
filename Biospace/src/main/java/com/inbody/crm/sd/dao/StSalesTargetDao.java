/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.domain.StSalesTargetCompare;
import com.inbody.crm.sd.entity.StSalesTarget;

/**
 * 销售目标DAO接口
 * @author zhangulu
 * @version 2017-10-12
 */
@MyBatisDao
public interface StSalesTargetDao extends CrudDao<StSalesTarget> {
	
	List<StSalesTarget> getStSalesTargetList(StSalesTarget stSalesTarget);
	
	int deleteStSalesTargetInfo(StSalesTarget stSalesTarget);
	
	List<StSalesTargetCompare> getEmployeeCompareStSalesTargetDtl(StSalesTarget stSalesTarget);
	
	List<StSalesTargetCompare> getOrganizeCompareStSalesTargetDtl(StSalesTarget stSalesTarget);
}