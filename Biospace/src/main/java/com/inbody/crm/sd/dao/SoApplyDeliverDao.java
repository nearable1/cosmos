/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.SoApplyDeliver;

/**
 * 发货申请DAO接口
 * @author zhanglulu
 * @version 2017-09-11
 */
@MyBatisDao
public interface SoApplyDeliverDao extends CrudDao<SoApplyDeliver> {
	
	SoApplyDeliver getSoApplyDeliverInfo(SoApplyDeliver searchSoApplyDeliver);
	
	SoApplyDeliver getSoAppDelWorkStatusByOrderNo(String orderNo);
	
	String getMaterialMarkByMaterialNo(String materialNo);
}