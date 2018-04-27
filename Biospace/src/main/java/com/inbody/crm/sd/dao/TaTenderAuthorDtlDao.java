/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.TaTenderAuthorDtl;

/**
 * 招标授权DAO接口
 * @author qidd
 * @version 2017-10-19
 */
@MyBatisDao
public interface TaTenderAuthorDtlDao extends CrudDao<TaTenderAuthorDtl> {

	public int deleteByAppId(String id);
}