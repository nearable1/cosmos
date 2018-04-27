package com.inbody.crm.cm.dao;

import java.util.List;
import java.util.Map;

import com.inbody.crm.cm.entity.CustomerAddManagement;
import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;


@MyBatisDao
public interface CustomerAddManagementDao extends CrudDao<CustomerAddManagement>  {
//	
//	public List<Map<String,String>>  loadList(int page);
//	
//	public List<Map<String,String>>  loadListByModel(Map<String,Object> paramMap);
//	
//	public Integer pageByEntity(Map<String,Object> paramMap);
	
	public Integer addByCustomerAddManagement(Map<String,Object> paramMap);
	
	
	public List<Map<String,String>> getList();

	 
	public List<CustomerAddManagement> getLists(CustomerAddManagement customerAddManagement);
}
