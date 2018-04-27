/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 维修信息Entity
 * @author yangj
 * @version 2017-08-22
 */
public class RmRepairInfo extends DataEntity<RmRepairInfo> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String repairNo;		// 维修编号 : YYMMDD(6)+流水(4-年循环)
	private String repairType;		// 维修分类 : 代码
	private String snNo;		// SN
	private Date askRepairDate;		// 报修日期
	private String repairWay;		// 报修方式 : 代码
	private String repairMethod;		// 报修处理方式 : 代码
	private String repairStatus;		// 报修处理状态 : 代码
	private String statusRemarks;		// 状态说明
	private String responsiblePersonId;		// 负责人
	private String faultType;		// 故障分类1 : 代码
	private String faultType2;		// 故障分类2 : 代码
	private String contactsName;		// 联系人
	private String telephone;		// 电话
	private String repairCusName;		// 报修客户
	private String address;		// 地址
	private String newRemarks;
	private String issueDescribe;   // 问题描述
	private String issueDetail;		// 情况确认
	private String processingContent;		// 处理内容
	private String consultType;		// 咨询问题分类 : 代码
	private String ifPrototype;		// 是否替代样机
	private String prototypeSnNo;		// 样机S/N
	private Date prototypeDateFrom;		// 样机发出时间
	private Date prototypeDateTo;		// 样机返回时间
	private Date maintenanceDateFrom;		// 维修机到货时间
	private Date maintenanceDateTo;		// 维修机出库时间
	private Date processDate;           // 处理日期
	
	public RmRepairInfo() {
		super();
	}

	public RmRepairInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="维修编号 : YYMMDD(6)+流水(4-年循环)长度必须介于 1 和 15 之间")
	public String getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}
	
	@Length(min=1, max=2, message="维修分类 : 代码长度必须介于 1 和 2 之间")
	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}
	
	@Length(min=1, max=50, message="SN长度必须介于 1 和 50 之间")
	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAskRepairDate() {
		return askRepairDate;
	}

	public void setAskRepairDate(Date askRepairDate) {
		this.askRepairDate = askRepairDate;
	}
	
	@Length(min=0, max=2, message="报修方式 : 代码长度必须介于 0 和 2 之间")
	public String getRepairWay() {
		return repairWay;
	}

	public void setRepairWay(String repairWay) {
		this.repairWay = repairWay;
	}
	
	@Length(min=0, max=2, message="报修处理方式 : 代码长度必须介于 0 和 2 之间")
	public String getRepairMethod() {
		return repairMethod;
	}

	public void setRepairMethod(String repairMethod) {
		this.repairMethod = repairMethod;
	}
	
	@Length(min=0, max=2, message="报修处理状态 : 代码长度必须介于 0 和 2 之间")
	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}
	
	@Length(min=0, max=300, message="状态说明长度必须介于 0 和 300 之间")
	public String getStatusRemarks() {
		return statusRemarks;
	}

	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}
	
	@Length(min=0, max=50, message="负责人长度必须介于 0 和 50 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	@Length(min=0, max=2, message="故障分类1 : 代码长度必须介于 0 和 2 之间")
	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	
	@Length(min=0, max=2, message="故障分类2 : 代码长度必须介于 0 和 2 之间")
	public String getFaultType2() {
		return faultType2;
	}

	public void setFaultType2(String faultType2) {
		this.faultType2 = faultType2;
	}
	
	@Length(min=0, max=100, message="联系人长度必须介于 0 和 100 之间")
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=300, message="报修客户长度必须介于 0 和 300 之间")
	public String getRepairCusName() {
		return repairCusName;
	}

	public void setRepairCusName(String repairCusName) {
		this.repairCusName = repairCusName;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min=0, max=300, message="备注长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}

	@Length(min=0, max=500, message="问题描述长度必须介于 0 和 500 之间")
	public String getIssueDescribe() {
		return issueDescribe;
	}

	public void setIssueDescribe(String issueDescribe) {
		this.issueDescribe = issueDescribe;
	}

	@Length(min=0, max=500, message="情况确认长度必须介于 0 和 500 之间")
	public String getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(String issueDetail) {
		this.issueDetail = issueDetail;
	}
	
	@Length(min=0, max=500, message="处理内容长度必须介于 0 和 500 之间")
	public String getProcessingContent() {
		return processingContent;
	}

	public void setProcessingContent(String processingContent) {
		this.processingContent = processingContent;
	}
	
	@Length(min=0, max=2, message="咨询问题分类 : 代码长度必须介于 0 和 2 之间")
	public String getConsultType() {
		return consultType;
	}

	public void setConsultType(String consultType) {
		this.consultType = consultType;
	}
	
	@Length(min=0, max=1, message="是否替代样机长度必须介于 0 和 1 之间")
	public String getIfPrototype() {
		return ifPrototype;
	}

	public void setIfPrototype(String ifPrototype) {
		this.ifPrototype = ifPrototype;
	}
	
	@Length(min=0, max=50, message="样机S/N长度必须介于 0 和 50 之间")
	public String getPrototypeSnNo() {
		return prototypeSnNo;
	}

	public void setPrototypeSnNo(String prototypeSnNo) {
		this.prototypeSnNo = prototypeSnNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPrototypeDateFrom() {
		return prototypeDateFrom;
	}

	public void setPrototypeDateFrom(Date prototypeDateFrom) {
		this.prototypeDateFrom = prototypeDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPrototypeDateTo() {
		return prototypeDateTo;
	}

	public void setPrototypeDateTo(Date prototypeDateTo) {
		this.prototypeDateTo = prototypeDateTo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMaintenanceDateFrom() {
		return maintenanceDateFrom;
	}

	public void setMaintenanceDateFrom(Date maintenanceDateFrom) {
		this.maintenanceDateFrom = maintenanceDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMaintenanceDateTo() {
		return maintenanceDateTo;
	}

	public void setMaintenanceDateTo(Date maintenanceDateTo) {
		this.maintenanceDateTo = maintenanceDateTo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

}