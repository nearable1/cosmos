/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.gym.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.modules.gym.entity.Gym;

/**
 * 健身知识DAO接口
 * @author liuyigeng
 * @version 2018-05-02
 */
@MyBatisDao
public interface GymDao extends CrudDao<Gym> {
	
}