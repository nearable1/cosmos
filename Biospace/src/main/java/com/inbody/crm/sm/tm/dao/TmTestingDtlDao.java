/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.tm.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sm.tm.entity.TmTestingDtl;

/**
 * 主子表DAO接口
 * @author yangj
 * @version 2017-09-20
 */
@MyBatisDao
public interface TmTestingDtlDao extends CrudDao<TmTestingDtl> {
	
	public int deleteByTestingNo(String testingNo);
}