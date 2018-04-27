/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.pm.entity.PmPurInvoice;

/**
 * 主子表DAO接口
 * @author yangj
 * @version 2017-07-27
 */
@MyBatisDao
public interface PmPurInvoiceDao extends CrudDao<PmPurInvoice> {
	
}