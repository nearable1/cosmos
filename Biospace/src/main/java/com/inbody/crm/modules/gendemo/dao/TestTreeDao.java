/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.gendemo.dao;

import java.util.List;

import com.inbody.crm.common.persistence.TreeDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.modules.gendemo.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author ThinkGem
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	public List<TestTree> findByParentId(TestTree entity);
	
	public List<TestTree> findNewest();
	
	public void updateName(TestTree entity);
	
	public void deleteOnce(TestTree entity);
	
	public List<TestTree> findById(TestTree entity);
}