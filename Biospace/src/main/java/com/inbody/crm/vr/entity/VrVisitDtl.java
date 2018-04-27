/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.ActEntity;

/**
 * 主子表生成Entity
 * @author Nssol
 * @version 2017-07-20
 */
public class VrVisitDtl extends ActEntity<VrVisitDtl> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	protected  String visitNo;		// 拜访编号 :
	private String lineNo;		// 行号
	protected String ifVisit;		// 是否拜访
	private String businessOppNo;		// 商机号 :
	private String customerName;		// 客户
	protected String customerChName;		// 客户
	private String purpose;		// 目的 : 代码
	protected String ifLocal;		// 是否本地
	private String address;		// 地址
	protected String expDateFrom;		// 预定日期起
	private String expDateTo;		// 预定日期止
	protected String actDateFrom;		// 实际日期起
	private String actDateTo;		// 实际日期止
	private String newRemarks;		// 备注说明
	private String leaderOpinion;		// 组长评价
	private String directorOpinion;		// 总监评价
	private String workflowStatus;		// 申请状态
	private String workflowStatus2;		// 报告状态
	protected String workflowStatusName;		// 申请状态
	protected String workflowStatus2Name;		// 报告状态
	private String headRowId;
	private String responsiblePersonId;
	protected String createName;
	protected String ifVisitName;
	
	public VrVisitDtl() {
		super();
	}

	public VrVisitDtl(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="拜访编号 :长度必须介于 1 和 15 之间")
	public String getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=0, max=1, message="是否拜访长度必须介于 0 和 1 之间")
	public String getIfVisit() {
		return ifVisit;
	}

	public void setIfVisit(String ifVisit) {
		this.ifVisit = ifVisit;
	}
	
	@Length(min=0, max=15, message="商机号 :长度必须介于 0 和 15 之间")
	public String getBusinessOppNo() {
		return businessOppNo;
	}

	public void setBusinessOppNo(String businessOppNo) {
		this.businessOppNo = businessOppNo;
	}
	
	@Length(min=0, max=100, message="客户长度必须介于 0 和 100 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=2, message="目的 : 代码长度必须介于 0 和 2 之间")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	@Length(min=0, max=1, message="是否本地长度必须介于 0 和 1 之间")
	public String getIfLocal() {
		return ifLocal;
	}

	public void setIfLocal(String ifLocal) {
		this.ifLocal = ifLocal;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getExpDateFrom() {
		return expDateFrom;
	}

	public void setExpDateFrom(String expDateFrom) {
		this.expDateFrom = expDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getExpDateTo() {
		return expDateTo;
	}

	public void setExpDateTo(String expDateTo) {
		this.expDateTo = expDateTo;
	}
	
	public String getActDateFrom() {
		return actDateFrom;
	}

	public void setActDateFrom(String actDateFrom) {
		this.actDateFrom = actDateFrom;
	}
	
	public String getActDateTo() {
		return actDateTo;
	}

	public void setActDateTo(String actDateTo) {
		this.actDateTo = actDateTo;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
	@Length(min=0, max=300, message="组长评价长度必须介于 0 和 300 之间")
	public String getLeaderOpinion() {
		return leaderOpinion;
	}

	public void setLeaderOpinion(String leaderOpinion) {
		this.leaderOpinion = leaderOpinion;
	}
	
	@Length(min=0, max=300, message="总监评价长度必须介于 0 和 300 之间")
	public String getDirectorOpinion() {
		return directorOpinion;
	}

	public void setDirectorOpinion(String directorOpinion) {
		this.directorOpinion = directorOpinion;
	}
	
	@Length(min=0, max=2, message="工作流状态 : 代码长度必须介于 0 和 2 之间")
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	@Length(min=0, max=2, message="工作流状态 : 代码长度必须介于 0 和 2 之间")
	public String getWorkflowStatus2() {
		return workflowStatus2;
	}

	public void setWorkflowStatus2(String workflowStatus2) {
		this.workflowStatus2 = workflowStatus2;
	}

	public String getHeadRowId() {
		return headRowId;
	}

	public void setHeadRowId(String headRowId) {
		this.headRowId = headRowId;
	}

	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}

	public String getCustomerChName() {
		return customerChName;
	}

	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getIfVisitName() {
		return ifVisitName;
	}

	public void setIfVisitName(String ifVisitName) {
		this.ifVisitName = ifVisitName;
	}

	public String getWorkflowStatusName() {
		return workflowStatusName;
	}

	public void setWorkflowStatusName(String workflowStatusName) {
		this.workflowStatusName = workflowStatusName;
	}

	public String getWorkflowStatus2Name() {
		return workflowStatus2Name;
	}

	public void setWorkflowStatus2Name(String workflowStatus2Name) {
		this.workflowStatus2Name = workflowStatus2Name;
	}
	
}