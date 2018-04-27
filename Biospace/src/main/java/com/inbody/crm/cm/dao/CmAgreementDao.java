package com.inbody.crm.cm.dao;

import java.util.Map;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;


@MyBatisDao
public interface CmAgreementDao extends CrudDao<CmAgreementDao>  {
//	
//	public List<Map<String,String>>  loadList(int page);
//	
//	public List<Map<String,String>>  loadListByModel(Map<String,Object> paramMap);
//	
//	public Integer pageByEntity(Map<String,Object> paramMap);	
	
	public void insertByMap(Map<String,Object> paramMap);

	

}
