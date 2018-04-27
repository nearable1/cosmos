package com.inbody.crm.sm.dao;

import java.util.List;
import java.util.Map;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagementInput;

@MyBatisDao
public interface SmStorageInfoManagementDao extends CrudDao<SmStorageInfoManagement>{
	
	public List<Map<String,String>> selectById(String id);
	
	public int update(SmStorageInfoManagement smStorageInfoManagement);
	
	public int insert(SmStorageInfoManagement smStorageInfoManagement);
	
	public void insertBySelect(SmStorageInfoManagement smStorageInfoManagement);
	
	public void insertBySelectOrder(SmStorageInfoManagement smStorageInfoManagemen);
	
	public void deleteDtlAppId(String Id);
	
	public void deleteDtlId(String Id);
	
	public void updateWorkflowStatusById(SmStorageInfoManagement smStorageInfoManagement);
	
	public void updateSmStorageAppById(SmStorageInfoManagement smStorageInfoManagement);
	
	public List<Map<String,String>> selectByResponsiblePersonId(SmStorageInfoManagement smStorageInfoManagement);

	public List<Map<String,String>> selectBySsiId(String Id);
	
	public List<Map<String,String>> selectBySsiInfoId(String Id);
	
	public List<SmStorageInfoManagement> findAllList(SmStorageInfoManagement smStorageInfo);
	
	/*
	 * snNo  虚拟SN 、actualSN 实际SN
	 */
	public void updateSn(SmStorageInfoManagement sm);
	
	public void updateStorageSn(SmStorageInfoManagement sm);
	
	public void updateTestingSn(SmStorageInfoManagement sm);
	
	public void updateWarehouseSn(SmStorageInfoManagement sm);
	
	/*
	 * 查询SN修改时间，排它
	 */
	public SmStorageInfoManagement selectSnUpdateDate(String snNo);
	
	/*
	 * 查询storage
	 */
	public SmStorageInfoManagement selectStorageUpdateDate(String id);
	
	public void insertLendingBySelectOrder(SmStorageInfoManagement smStorageInfoManagement);

	public void insertInfoByInfoId(SmStorageInfoManagement smStorageInfoManagement);

	public void updateByStorageId(SmStorageInfoManagement smStorageInfoManagement);
	
	/*
	 *保存附件信息
	 */
	public void save(SmStorageInfoManagementInput input);
	/*
	 * 保存安装日期  SN表
	 */
	public void updateInstall(SmStorageInfoManagementInput input);
	
	/*
	 * 保存安装日期  storage表
	 */
	public void updateStorageInstall(SmStorageInfoManagementInput input);
	
	/*
	 * 根据物料号查询  物料信息表中的 是否自动生成虚拟SN号(IF_VIRTUAL_SN)
	 */
	public List<SmStorageInfoManagement> selectStorageMat(String snNo);
	/*
	 * 获取质保期时间和延保期时间
	 */
	public Map<String, Integer> getPeriod(String id); 
	
}
