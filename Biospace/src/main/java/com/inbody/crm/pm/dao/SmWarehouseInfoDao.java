/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.pm.entity.SmWarehouseInfo;

/**
 * 单表生成DAO接口
 * 
 * @author NSSOL
 * @version 2017-08-10
 */
@MyBatisDao
public interface SmWarehouseInfoDao extends CrudDao<SmWarehouseInfo> {
    /** 取得非sn物料对应的库存信息  */
    List<SmWarehouseInfo> getNonSnStockInfo(SmWarehouseInfo warehouseInfo);
}