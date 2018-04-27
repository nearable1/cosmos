/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.BoBusinessOppDtl;

/**
 * 主子表生成DAO接口
 * @author Nssol
 * @version 2017-07-20
 */
@MyBatisDao
public interface BoBusinessOppDtlDao extends CrudDao<BoBusinessOppDtl> {
	
	public int deleteByBusinessNo(String id);
}