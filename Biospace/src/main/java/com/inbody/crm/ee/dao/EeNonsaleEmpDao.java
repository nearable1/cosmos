/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.ee.entity.EeNonsaleEmp;
import com.inbody.crm.ee.entity.EeSaleEmp;

/**
 * 非销售员工评价DAO接口
 * @author 11
 * @version 2017-10-27
 */
@MyBatisDao
public interface EeNonsaleEmpDao extends CrudDao<EeNonsaleEmp> {
	/**
	 * 获取记录
	 * @param entity
	 * @return
	 */
	public List<EeNonsaleEmp> getByUserAndYear(EeNonsaleEmp eeNonsaleEmp);
}