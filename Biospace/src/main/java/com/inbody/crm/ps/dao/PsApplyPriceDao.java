/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ps.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.ps.entity.PsApplyPrice;

/**
 * 主子表生成DAO接口
 * @author Nssol
 * @version 2017-07-20
 */
@MyBatisDao
public interface PsApplyPriceDao extends CrudDao<PsApplyPrice> {
	public void updateStatus(PsApplyPrice psApplyPrice);

	public void deleteDetails(PsApplyPrice psApplyPrice);
	
	public List<PsApplyPrice> findTempList(String userId);
}