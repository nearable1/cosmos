/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.entity.SmMatInfo;

/**
 * 报价单发票DAO接口
 * 
 * @author yangj
 * @version 2017-10-15
 */
@MyBatisDao("QuotaMatInfoDao")
public interface SmMatInfoDao extends CrudDao<SmMatInfo> {

    SmMatInfo getMaterialInfoByNo(String materialNo);
}