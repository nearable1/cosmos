/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ps.dao;

import java.util.List;

import com.inbody.crm.cm.entity.CustomerAddManagement;
import com.inbody.crm.common.entity.SelectEntity;
import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.ps.entity.PsAgreement;

/**
 * 单表生成DAO接口
 * @author NSSOL
 * @version 2017-07-21
 */
@MyBatisDao
public interface PsAgreementDao extends CrudDao<PsAgreement> {
	public List<SelectEntity> listProtocols(CustomerAddManagement cam);

	public List<SelectEntity> listStartDays(String agreementPatiesId);
	
	public String getCustomerName(String id);
}