/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.SoGatheringInfo;

/**
 * 收款信息管理DAO接口
 * @author zhanglulu
 * @version 2017-08-22
 */
@MyBatisDao
public interface SoGatheringInfoDao extends CrudDao<SoGatheringInfo> {
	
	
	List<SoGatheringInfo> getSoGatheringInfoList(String orderNo);
	
	int deleteSoGatheringInfoByOrderNo(String orderNo);
	
	List<SoGatheringInfo> getReSoGatheringInfo(String orderNo);
	
}