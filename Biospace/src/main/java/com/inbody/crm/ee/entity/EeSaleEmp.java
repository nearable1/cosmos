/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.ActEntity;
import com.inbody.crm.modules.sys.entity.Office;

/**
 * 销售员工评价Entity
 * @author 11
 * @version 2017-10-27
 */
public class EeSaleEmp extends ActEntity<EeSaleEmp> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String year;		// 年度
	private String workflowStatus;		// 工作流状态 : 代码
	private String opt;             // 流程操作
	private String nextYearMemo;    //明年改善
	private Office organize; //组织id
	private String organizeName;
	
	private List<EeEmpEvaluation> eeEmpEvaluations = new ArrayList<EeEmpEvaluation>();
	private List<EeEmpPlan> eeEmpPlans = new ArrayList<EeEmpPlan>();
	public EeSaleEmp() {
		super();
	}

	public EeSaleEmp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public List<EeEmpEvaluation> getEeEmpEvaluations() {
		return eeEmpEvaluations;
	}

	public void setEeEmpEvaluations(List<EeEmpEvaluation> eeEmpEvaluations) {
		this.eeEmpEvaluations = eeEmpEvaluations;
	}

	public List<EeEmpPlan> getEeEmpPlans() {
		return eeEmpPlans;
	}

	public void setEeEmpPlans(List<EeEmpPlan> eeEmpPlans) {
		this.eeEmpPlans = eeEmpPlans;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getNextYearMemo() {
		return nextYearMemo;
	}

	public void setNextYearMemo(String nextYearMemo) {
		this.nextYearMemo = nextYearMemo;
	}

	public Office getOrganize() {
		return organize;
	}

	public void setOrganize(Office organize) {
		this.organize = organize;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	
}