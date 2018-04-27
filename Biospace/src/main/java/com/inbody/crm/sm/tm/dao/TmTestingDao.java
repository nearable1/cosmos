/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.tm.dao;

import java.util.List;

import com.inbody.crm.common.entity.SnInfo;
import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sm.tm.entity.TmTesting;
import com.inbody.crm.sm.tm.entity.TmTestingDtl;

/**
 * 主子表DAO接口
 * @author yangj
 * @version 2017-09-20
 */
@MyBatisDao
public interface TmTestingDao extends CrudDao<TmTesting> {

	public List<TmTesting> findPageLatest(TmTesting bean);
	/**
	 * 根据客户加载商机
	 * @param entity
	 * @return
	 */
	public List<SnInfo> findSnInfoList(TmTesting bean);
	
	public int hasSnwarehouseInfo(TmTesting bean);
	public void updateSnInfo(TmTestingDtl bean);
}