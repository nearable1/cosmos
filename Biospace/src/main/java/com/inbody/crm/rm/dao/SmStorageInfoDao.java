/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.entity.SmStorageInfo;


/**
 * 单表生成DAO接口
 * 
 * @author yangj
 * @version 2017-10-13
 */
@MyBatisDao("QuotaStorageInfoDao")
public interface SmStorageInfoDao extends CrudDao<SmStorageInfo> {

}