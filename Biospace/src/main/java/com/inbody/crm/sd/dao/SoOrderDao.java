/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.domain.SoOrderSearch;
import com.inbody.crm.sd.entity.SmSnInfo;
import com.inbody.crm.sd.entity.SmStorageInfo;
import com.inbody.crm.sd.entity.SmWarehouseInfo;
import com.inbody.crm.sd.entity.SoApplyDeliver;
import com.inbody.crm.sd.entity.SoOrder;
import com.inbody.crm.sd.entity.SoRmQuotationDtl;
import com.inbody.crm.sd.entity.SoRmRepairInfo;

/**
 * 合同信息录入DAO接口
 * @author zhanglulu
 * @version 2017-08-22
 */
@MyBatisDao
public interface SoOrderDao extends CrudDao<SoOrder> {
	
	SoOrder getSoOrderInfo(String id);
	
	int updateDelFlag(SoOrder soOrder);
	
	SoOrder getEffectiveInfoByOrderNo(String orderNo);
	
	SoOrder getApplyingInfoByOrderNo(String orderNo);
	
	List<SoOrderSearch> findPageList(SoOrderSearch soOrderSearch);
	
	List<SoOrder> getReSoOrderInfo();
	
	String getMaxStorageProcessDate(String orderNo);
	
	String getMaxInstallDate(String orderNo);
	
	List<SoRmRepairInfo> getReRmRepairInfoList();
	
	List<SoRmQuotationDtl> getReRmQuotationDtl(String quotationNo);
	
	SoOrder getDeliverInfoByOrderNo(SoApplyDeliver searchSoApplyDeliver);
	
	List<SmWarehouseInfo> getSmWarehouseInfo(SmWarehouseInfo smWarehouseInfo);
	
	SmSnInfo getSmSnInfo(SmSnInfo smSnInfo);
	
	int insertSmStorageInfo(SmStorageInfo smStorageInfo);
	
	int updateSmSnInfo(SmSnInfo smSnInfo);
	
	int updateSmWarehouseInfo(SmWarehouseInfo smWarehouseInfo);
	
}