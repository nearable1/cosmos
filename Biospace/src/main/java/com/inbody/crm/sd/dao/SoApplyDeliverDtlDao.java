/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.SoApplyDeliverDtl;

/**
 * 发货申请明细DAO接口
 * @author zhanglulu
 * @version 2017-09-11
 */
@MyBatisDao
public interface SoApplyDeliverDtlDao extends CrudDao<SoApplyDeliverDtl> {
	
	List<SoApplyDeliverDtl> getSoApplyDeliverDtlListByAppId(String appId);
	
	int deleteSoApplyDeliverDtlByAppId(String appId);
	
}