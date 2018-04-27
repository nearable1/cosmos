package com.inbody.crm.cm.dao;

import java.util.List;
import java.util.Map;

import com.inbody.crm.cm.entity.CmAgreement;
import com.inbody.crm.cm.entity.CmAgreementDtl;
import com.inbody.crm.cm.entity.CustomerManagement;
import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;


@MyBatisDao
public interface CustomerManagementDao extends CrudDao<CustomerManagement>  {
	
	//查询客户主数据
	public List<CustomerManagement> selectCustomerById(CustomerManagement customer);

	//保存数据
	public void saveOrUpdate(CustomerManagement customerManagement);
	
	
	public void insertCustomer(CustomerManagement customerManagement);
	
	public void updateCustomer(CustomerManagement customerManagement);
	
	//查入协议信息数据
	public void insertAgreement(List<CmAgreement> list);
	//插入协议明细信息
	public void insertAgreementDtl(List<CmAgreementDtl> list);
	
	//根据customerId批量获取协议信息
	public List<CmAgreement> selectAgreement(String customerId);
	//删除协议信息
	public int deleteAgreement(String customerId);
	//删除协议信息明细表
	public int deleteAgreementDtl(Map<String,String> map);
	//根据协议id删除信息
	public int deleteAgreementBy(String id);
	
	public List<CustomerManagement> selectCustomerParent();
	//根据客户ID获取组信息
	public CustomerManagement selectOfficeById(String id);
}
