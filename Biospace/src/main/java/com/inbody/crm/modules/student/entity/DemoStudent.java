/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.student.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 班级管理Entity
 * @author liuyigeng
 * @version 2018-04-29
 */
public class DemoStudent extends DataEntity<DemoStudent> {
	
	private static final long serialVersionUID = 1L;
	private DemoClasses demoClasses;		// 班级 父类
	private String studentname;		// 学生名称
	
	public DemoStudent() {
		super();
	}

	public DemoStudent(String id){
		super(id);
	}

	public DemoStudent(DemoClasses demoClasses){
		this.demoClasses = demoClasses;
	}

	@Length(min=0, max=64, message="班级长度必须介于 0 和 64 之间")
	public DemoClasses getDemoClasses() {
		return demoClasses;
	}

	public void setDemoClasses(DemoClasses demoClasses) {
		this.demoClasses = demoClasses;
	}
	
	@Length(min=0, max=200, message="学生名称长度必须介于 0 和 200 之间")
	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	
}