/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.ActEntity;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author NSSOL
 * @version 2017-07-20
 */
public class VrVisit extends ActEntity<VrVisit> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String visitNo;		// 拜访编号 :
	private String responsiblePersonId;		// 负责人
	private String workflowStatus;		// 工作流状态 : 代码
	private String procInsId2;		// 拜访报告流程编号
	private String workflowStatus2;		// 拜访报告工作流状态 : 代码
	private String newRemarks;		// 备注说明
	private String opt;             // 流程操作
	
	private List<VrVisitDtl> vrVisitDtlList = new ArrayList<VrVisitDtl>();
	public VrVisit() {
		super();
	}

	public VrVisit(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	public String getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}
	
	@Length(min=1, max=50, message="负责人长度必须介于 1 和 50 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	@Length(min=0, max=2, message="工作流状态 : 代码长度必须介于 0 和 2 之间")
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Length(min=0, max=64, message="拜访报告流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId2() {
		return procInsId2;
	}

	public void setProcInsId2(String procInsId2) {
		this.procInsId2 = procInsId2;
	}
	
	@Length(min=0, max=2, message="拜访报告工作流状态 : 代码长度必须介于 0 和 2 之间")
	public String getWorkflowStatus2() {
		return workflowStatus2;
	}

	public void setWorkflowStatus2(String workflowStatus2) {
		this.workflowStatus2 = workflowStatus2;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}

	public List<VrVisitDtl> getVrVisitDtlList() {
		return vrVisitDtlList;
	}

	public void setVrVisitDtlList(List<VrVisitDtl> vrVisitDtlList) {
		this.vrVisitDtlList = vrVisitDtlList;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}
	
}