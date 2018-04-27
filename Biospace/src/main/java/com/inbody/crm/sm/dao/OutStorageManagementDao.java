package com.inbody.crm.sm.dao;

import java.util.List;
import java.util.Map;

import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sm.entity.OutStorageManagement;
@MyBatisDao
public interface OutStorageManagementDao {
	
	
   public List<Map<String,String>> loadWarehouseList(String qureyMaterialNo);
   
   public void addByOutStorageManagement(OutStorageManagement  outStorageManagement);
   
   public void addBySmStorageApp(OutStorageManagement  outStorageManagement);
   
   public void update(OutStorageManagement  outStorageManagement);

   public List<Map<String,String>> smStorageApp (String id);
   
   public  List<Map<String,String>> smStorageAppDtl(String id);
   
   public  List<Map<String,String>> selectById(String id);
   
   public  List<Map<String,String>> selectBySnNo( OutStorageManagement outStorageManagement);
   
    

}
