/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.sys.dao;

import java.util.List;

import com.inbody.crm.common.persistence.TreeTermDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeTermDao<Office> {
	
	/**
	 * 获取组织记录
	 * @param id
	 * @return
	 */
	public Office getDefault(String id);
	
	public List<Office> getManagerListByPrimaryPerson(String primaryPersonId);
	
	public List<Office> getGroupListByPrimaryPerson(String primaryPersonId);
	
	public List<Office> getGroupsListByPrimaryPerson(String primaryPersonId);
}
