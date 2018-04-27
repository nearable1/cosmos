/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.entity.TaTenderAuthor;

/**
 * 招标授权DAO接口
 * @author qidd
 * @version 2017-10-19
 */
@MyBatisDao
public interface TaTenderAuthorDao extends CrudDao<TaTenderAuthor> {

	public int updateBO(String id);
}