/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.entity.SmSnInfo;

/**
 * 报价单SN管理DAO接口
 * 
 * @author yangj
 * @version 2017-10-15
 */
@MyBatisDao("QuotaSnInfoDao")
public interface SmSnInfoDao extends CrudDao<SmSnInfo> {
    int getSnCountByNo(SmSnInfo smSnInfo);
}