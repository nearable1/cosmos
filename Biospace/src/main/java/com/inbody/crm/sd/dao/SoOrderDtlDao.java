/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.SoOrder;
import com.inbody.crm.sd.entity.SoOrderDtl;

/**
 * 合同信息录入DAO接口
 * @author zhanglulu
 * @version 2017-08-22
 */
@MyBatisDao
public interface SoOrderDtlDao extends CrudDao<SoOrderDtl> {
	List<SoOrderDtl> getSoOrderDtlList(String orderId);
	List<SoOrderDtl> getSoOrderDtlExList(SoOrder soOrder);
	
	SoOrderDtl getSoOrderDtlInfo(SoOrderDtl soOrderDtl);
	
	int deleteSoOrderDtlByOrderId(String orderId);
	
	String getMaxLineNo(String orderId);
	
	String getDeliverNumMax(SoOrderDtl soOrderDtl);
	
	String getInvoiceNumMax(SoOrderDtl soOrderDtl);
	
}