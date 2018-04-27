/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.BoBusinessOpp;

/**
 * 主子表生成DAO接口
 * @author Nssol
 * @version 2017-07-20
 */
@MyBatisDao
public interface BoBusinessOppDao extends CrudDao<BoBusinessOpp> {

	/**
	 * 只更新 是否销售计划字段
	 * @param entity
	 * @return
	 */
	public int updateSalesPlan(BoBusinessOpp entity);
	
	/**
	 * 根据商机号取得商机信息
	 * @param businessOppNo
	 * @return
	 */
	public BoBusinessOpp getInfoByBusinessOppNo(String businessOppNo);
	
	/**
	 * 根据客户加载商机
	 * @param entity
	 * @return
	 */
	public List<BoBusinessOpp> findListByCustomerId(BoBusinessOpp bean);
	
	/**
	 * 根据客户加载商机
	 * @param entity
	 * @return
	 */
	public List<BoBusinessOpp> findListByCustomer(BoBusinessOpp bean);
	
}