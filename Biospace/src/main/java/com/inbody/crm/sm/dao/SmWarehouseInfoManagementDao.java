package com.inbody.crm.sm.dao;

import java.util.List;
import java.util.Map;

import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sm.entity.SmWarehouseInfoManagement;

@MyBatisDao
public interface SmWarehouseInfoManagementDao {
	
	public void update(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public void updateBySn(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public void insert(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public void updateStock(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public void updateStockBySnNo(Map<String,String> map);
	
	public void updateBySelect(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public void updateByNum(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public void insertBySelect(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public List<Map<String,String>> selectByWarehouse(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public List<Map<String,String>> selectByWarehouseAndMaterialNo(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public List<Map<String,String>> selectByWid(String Id);
	
	public List<Map<String,String>> selectAll(SmWarehouseInfoManagement smWarehouseInfoManagement);
	
	public void updateSnNo(Map<String,String> map);
	
	public void updateNewSnNo(Map<String,String> map);
	
	public void updateSnBySn(SmWarehouseInfoManagement smWarehouseInfoManagement);

	
	
	
	
}
