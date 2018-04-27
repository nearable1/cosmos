package com.inbody.crm.sm.dao;

import java.util.List;
import java.util.Map;

import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;

@MyBatisDao
public interface SoOrderDtlManagementDao {
	public List<Map<String,String>> selectByOrderId(String orderNo);
	
	public void update(SmStorageInfoManagement smStorageInfoManagement);
	
	public void insert(SmStorageInfoManagement smStorageInfoManagement);
	
	public void insertBySelect(SmStorageInfoManagement smStorageInfoManagement);
	
	public  List<Map<String,String>> smStorageAppDtl(String id);

}
