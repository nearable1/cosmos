/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.student.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 班级管理Entity
 * @author liuyigeng
 * @version 2018-04-29
 */
public class DemoClasses extends DataEntity<DemoClasses> {
	
	private static final long serialVersionUID = 1L;
	private String classname;		// classname
	private List<DemoStudent> demoStudentList = Lists.newArrayList();		// 子表列表
	
	public DemoClasses() {
		super();
	}

	public DemoClasses(String id){
		super(id);
	}

	@Length(min=0, max=200, message="classname长度必须介于 0 和 200 之间")
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public List<DemoStudent> getDemoStudentList() {
		return demoStudentList;
	}

	public void setDemoStudentList(List<DemoStudent> demoStudentList) {
		this.demoStudentList = demoStudentList;
	}
}