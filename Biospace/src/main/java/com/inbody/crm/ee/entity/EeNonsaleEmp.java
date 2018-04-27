/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.ActEntity;
import com.inbody.crm.common.persistence.DataEntity;
import com.inbody.crm.modules.sys.entity.Office;

/**
 * 非销售员工评价Entity
 * @author 11
 * @version 2017-10-27
 */
public class EeNonsaleEmp extends ActEntity<EeNonsaleEmp> {
	
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
	public EeNonsaleEmp() {
		super();
	}

	public EeNonsaleEmp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=4, message="年度长度必须介于 1 和 4 之间")
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	
}