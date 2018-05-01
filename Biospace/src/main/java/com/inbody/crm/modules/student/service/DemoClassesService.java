/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.student.entity.DemoClasses;
import com.inbody.crm.modules.student.dao.DemoClassesDao;
import com.inbody.crm.modules.student.entity.DemoStudent;
import com.inbody.crm.modules.student.dao.DemoStudentDao;

/**
 * 班级管理Service
 * @author liuyigeng
 * @version 2018-04-29
 */
@Service
@Transactional(readOnly = true)
public class DemoClassesService extends CrudService<DemoClassesDao, DemoClasses> {

	@Autowired
	private DemoStudentDao demoStudentDao;
	
	public DemoClasses get(String id) {
		DemoClasses demoClasses = super.get(id);
		demoClasses.setDemoStudentList(demoStudentDao.findList(new DemoStudent(demoClasses)));
		return demoClasses;
	}
	
	public List<DemoClasses> findList(DemoClasses demoClasses) {
		return super.findList(demoClasses);
	}
	
	public Page<DemoClasses> findPage(Page<DemoClasses> page, DemoClasses demoClasses) {
		return super.findPage(page, demoClasses);
	}
	
	@Transactional(readOnly = false)
	public void save(DemoClasses demoClasses) {
		super.save(demoClasses);
		for (DemoStudent demoStudent : demoClasses.getDemoStudentList()){
			if (demoStudent.getId() == null){
				continue;
			}
			if (DemoStudent.DEL_FLAG_NORMAL.equals(demoStudent.getDelFlag())){
				if (StringUtils.isBlank(demoStudent.getId())){
					demoStudent.setDemoClasses(demoClasses);
					demoStudent.preInsert();
					demoStudentDao.insert(demoStudent);
				}else{
					demoStudent.preUpdate();
					demoStudentDao.update(demoStudent);
				}
			}else{
				demoStudentDao.delete(demoStudent);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(DemoClasses demoClasses) {
		super.delete(demoClasses);
		demoStudentDao.delete(new DemoStudent(demoClasses));
	}
	
}