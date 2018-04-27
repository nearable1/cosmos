/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sd.domain.AtAgentTargetSearch;
import com.inbody.crm.sd.entity.AtAgentTarget;

/**
 * 代理商目标DAO接口
 * @author zhanglulu
 * @version 2017-09-26
 */
@MyBatisDao
public interface AtAgentTargetDao extends CrudDao<AtAgentTarget> {
	
	List<AtAgentTargetSearch> findCompareAtAgentTargetPageList(AtAgentTargetSearch atAgentTargetSearch);
	
	List<AtAgentTargetSearch> findAtAgentTargetPageList(AtAgentTargetSearch atAgentTargetSearch);
	
	List<AtAgentTarget> getCompareAtAgentTargetDtl(AtAgentTarget atAgentTarget);
	
	List<AtAgentTarget> getAtAgentTargetDtl(AtAgentTarget atAgentTarget);
	
	AtAgentTargetSearch getCmAgreementInfo(String agreementId);
	
	int deleteInfo(AtAgentTarget atAgentTarget);
	
}