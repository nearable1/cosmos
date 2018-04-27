package com.inbody.crm.common.persistence;

import java.util.List;
import java.util.Map;

import com.inbody.crm.common.entity.EmployeeSearch;
import com.inbody.crm.common.entity.SelectEntity;
import com.inbody.crm.common.entity.SmPackageInfoEntity;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface CommonDao extends BaseDao {
	public int getNextSequence(Map<String, String> params);

    /** 根据关键字或类型模糊查询物料信息 */
    public List<SelectEntity> getMaterialDictList(Map<String, Object> params);

	public SelectEntity getMaterialByNo(String materialNo);

	public List<SelectEntity> getCustomerDictList(Map<String, String> params);
	
	public SelectEntity getCustomerById(String customersId);
	
	public List<SmPackageInfoEntity> getSmPackageInfoByMaterialNo(String materialNo);
	
	public String getOrganize(String employeeId);
	
	public String getStandardPrice1(Map<String, String> params);
	
	public String getStandardPrice24(Map<String, String> params);
	
	public String getStandardPrice3(Map<String, String> params);
	
	public String getStandardPrice5(Map<String, String> params);
	
	public String getCustomerParentCo(String customersId);

//	public List<SelectEntity> getEmployeeDictList(Map<String, String> params, Map<String, String> sqlMap);
	public List<SelectEntity> getEmployeeDictList(EmployeeSearch employeeSearch);
	
	public SelectEntity getEmployeeInfoById(String id);
	
	public String getUserByRoles(Map<String, String> params);
}
