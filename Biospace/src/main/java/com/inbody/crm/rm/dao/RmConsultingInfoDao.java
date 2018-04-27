/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.domain.RmConsultingSearch;
import com.inbody.crm.rm.entity.RmConsultingInfo;

/**
 * 单表生成DAO接口
 * @author yangj
 * @version 2017-08-16
 */
@MyBatisDao
public interface RmConsultingInfoDao extends CrudDao<RmConsultingInfo> {

	List<RmConsultingSearch> findListBySelective(RmConsultingSearch rmConsultingSearch);

}