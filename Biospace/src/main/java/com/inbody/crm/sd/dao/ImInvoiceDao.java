/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.CmCustomerInfo;
import com.inbody.crm.sd.entity.EmployeeInfo;
import com.inbody.crm.sd.entity.ImInvoice;
import com.inbody.crm.sd.entity.ImInvoiceDtl;

/**
 * 发票信息管理DAO接口
 * @author zhanglulu
 * @version 2017-08-23
 */
@MyBatisDao
public interface ImInvoiceDao extends CrudDao<ImInvoice> {
	
	ImInvoice getImInvoiceByOrderNo(String orderNo);
	
	ImInvoice getApplyingImInvoiceByOrderNo(String orderNo);
	
	ImInvoice getAppOverImInvoiceByOrderNo(String orderNo);
	
	List<ImInvoiceDtl> getImInvoiceDtlListByOrderNo(String orderNo);
	
	int deleteImInvoiceDtlByAppId(String appId);
	
	int insertImInvoiceDtl(ImInvoiceDtl imInvoiceDtl);
	
	int updateImInvoiceDtl(ImInvoiceDtl imInvoiceDtl);
	
	CmCustomerInfo getCustomerInfo(String customerId);
	
	EmployeeInfo getEmployeeInfo(String employeeId);
	
	ImInvoiceDtl getImInvoiceDtlById(String id);
}