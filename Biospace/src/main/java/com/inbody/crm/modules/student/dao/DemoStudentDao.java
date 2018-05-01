/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.student.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.modules.student.entity.DemoStudent;

/**
 * 班级管理DAO接口
 * @author liuyigeng
 * @version 2018-04-29
 */
@MyBatisDao
public interface DemoStudentDao extends CrudDao<DemoStudent> {
	
}