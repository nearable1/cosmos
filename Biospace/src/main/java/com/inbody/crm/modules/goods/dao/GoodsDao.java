/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.goods.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.modules.goods.entity.Goods;

/**
 * 商品管理DAO接口
 * @author liuyigeng
 * @version 2018-05-07
 */
@MyBatisDao
public interface GoodsDao extends CrudDao<Goods> {
	
}