/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.entity.SmWarehouseInfo;

/**
 * 报价单库存DAO接口
 * 
 * @author yangj
 * @version 2017-10-12
 */
@MyBatisDao("QuotaWarehouseInfoDao")
public interface SmWarehouseInfoDao extends CrudDao<SmWarehouseInfo> {
    /** 报价单明细对应的库信息取得 */
    List<SmWarehouseInfo> getQuotaDtlInventory(String preQuotaId);
}