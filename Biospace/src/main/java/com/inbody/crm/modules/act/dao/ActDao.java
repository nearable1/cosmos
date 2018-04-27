/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.act.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.modules.act.entity.Act;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
	public int updateWorkFlowStatusByBusinessId(Act act);
	
	public String getWorkFlowStatusByBusinessId(Act act);
	
	public String getWorkFlowStatus2ByBusinessId(Act act);

	public int updateWorkFlowStatus2ByBusinessId(Act act);
	
	public int updateCustomerVisitDateByBusinessId(Act act);
	
	public String getOrderNoByBusinessId(String businessId);
	
	public int updateWorkFlowStatusByOrderNo(Act act);
	
	public int deleteBusinessDataById(Act act);
}
