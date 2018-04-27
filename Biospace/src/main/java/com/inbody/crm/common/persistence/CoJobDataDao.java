/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.persistence;

import java.util.List;

import com.inbody.crm.common.entity.CoJobData;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;

/**
 * 履历DAO接口
 * 
 * @author yangj
 * @version 2017-10-24
 */
@MyBatisDao
public interface CoJobDataDao extends CrudDao<CoJobData> {

    /** 履历一览分页查询 */
    List<CoJobData> findPageList(CoJobData coJobData);

}