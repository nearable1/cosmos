/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.vr.entity.VrVisit;

/**
 * 单表生成DAO接口
 * @author NSSOL
 * @version 2017-07-20
 */
@MyBatisDao
public interface VrVisitDao extends CrudDao<VrVisit> {
	
	public VrVisit getByProcInsId(String procInsId);
	
}