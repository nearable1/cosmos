/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.google.common.collect.Lists;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.ActEntity;
import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 招标授权Entity
 * @author qidd
 * @version 2017-10-19
 */
public class TaTenderAuthor extends ActEntity<TaTenderAuthor> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String authorizationNo;		// 招标编号
	private String tenderee;		// 招标单位
	private String projectName;		// 项目名称
	private String endCustomerId;		// 最终客户
	private String customerSegmentation;		// 客户细分
	private String bidPurpose;		// 投标目的
	private String tenderType;		// 招标类型
	private Date bidOpeningDate;		// 开标日期
	private String bidOpeningDateStr;		// 开标日期
	private String bidResult;		// 招标结果
	private String bidRemarks;		// 中标说明
	private String workflowStatus;		// 工作流状态 : 代码
	private String opt;             // 流程操作
	private Date validityDateFrom;		// 协议有效期起
	private Date validityDateTo;		// 协议有效期止
	private String validityDateFromStr;		// 协议有效期起
	private String validityDateToStr;		// 协议有效期止
	private String authorOfferType;		// 授权提供方式
	private String offerRemarks;		// 方式说明
	private String newRemarks;		// 备注说明
	private String contactsName;		// 联系人
	private String agentId;		// 代理商/经销商
	private String telephone;		// 电话
	private String address;		// 地址
	private List<TaTenderAuthorDtl> taTenderAuthorDtlList = Lists.newArrayList();		// 子表列表
	
	private String endCustomerName;
	private String industry;
	private String agentName;
	private String materialNo;
	private String materialName;
	private String model;
	private String applyName;
	
	//查询条件用项目
	private String authorizationNoSearch;	// 招标编号
	private String projectNameSearch;		// 项目名称
	private Date validityDateFromSearch;	// 协议有效期起（授权日期）
	private Date validityDateToSearch;		// 协议有效期止（授权日期）
	private Date bidOpeningDateFromSearch;		// 开标日期起
	private Date bidOpeningDateToSearch;		// 开标日期止
	private String tendereeSearch;		// 招标单位
	private String endCustomerIdSearch;		// 最终客户
	private String agentIdSearch;		// 代理商/经销商
	private String workflowStatusSearch;		// 工作流状态
	private String bidResultSearch;		// 招标结果

	private String businessOppId;  // 商机ID(商机一览页面选择的ID)

    /** 附件信息 */
    private List<CoAttachments> attachmentsList;

    /** 附件文件 */
    private MultipartFile[] files;
	
	public TaTenderAuthor() {
		super();
	}

	public TaTenderAuthor(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@ExcelField(title="招标编号", align=1, sort=10)
	public String getAuthorizationNo() {
		return authorizationNo;
	}

	public void setAuthorizationNo(String authorizationNo) {
		this.authorizationNo = authorizationNo;
	}
	
	@Length(min=0, max=100, message="招标单位长度必须介于 0 和 100 之间")
	@ExcelField(title="招标单位", align=1, sort=30)
	public String getTenderee() {
		return tenderee;
	}

	public void setTenderee(String tenderee) {
		this.tenderee = tenderee;
	}
	
	@Length(min=0, max=100, message="项目名称长度必须介于 0 和 100 之间")
	@ExcelField(title="项目名称", align=1, sort=20)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=50, message="最终客户长度必须介于 0 和 50 之间")
	public String getEndCustomerId() {
		return endCustomerId;
	}

	public void setEndCustomerId(String endCustomerId) {
		this.endCustomerId = endCustomerId;
	}
	
	@Length(min=0, max=4, message="客户细分长度必须介于 0 和 4 之间")
	public String getCustomerSegmentation() {
		return customerSegmentation;
	}

	public void setCustomerSegmentation(String customerSegmentation) {
		this.customerSegmentation = customerSegmentation;
	}
	
	@Length(min=0, max=5, message="投标目的长度必须介于 0 和 5 之间")
	@ExcelField(title="招标目的", align=1, sort=70, dictType="DM0013")
	public String getBidPurpose() {
		return bidPurpose;
	}

	public void setBidPurpose(String bidPurpose) {
		this.bidPurpose = bidPurpose;
	}
	
	@Length(min=0, max=5, message="招标类型长度必须介于 0 和 5 之间")
	@ExcelField(title="招标性质", align=1, sort=80, dictType="DM0014")
	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBidOpeningDate() {
		return bidOpeningDate;
	}

	public void setBidOpeningDate(Date bidOpeningDate) {
		this.bidOpeningDate = bidOpeningDate;
	}
	
	@ExcelField(title="开标日期", align=1, sort=40)
	public String getBidOpeningDateStr() {
		return bidOpeningDateStr;
	}

	public void setBidOpeningDateStr(String bidOpeningDateStr) {
		this.bidOpeningDateStr = bidOpeningDateStr;
	}
	
	@Length(min=0, max=5, message="招标结果长度必须介于 0 和 5 之间")
	@ExcelField(title="招标结果", align=1, sort=140, dictType="DM0044")
	public String getBidResult() {
		return bidResult;
	}

	public void setBidResult(String bidResult) {
		this.bidResult = bidResult;
	}
	
	@Length(min=0, max=300, message="中标说明长度必须介于 0 和 300 之间")
	@ExcelField(title="备注", align=1, sort=150)
	public String getBidRemarks() {
		return bidRemarks;
	}

	public void setBidRemarks(String bidRemarks) {
		this.bidRemarks = bidRemarks;
	}
	
	@Length(min=0, max=5, message="工作流状态 : 代码长度必须介于 0 和 5 之间")
	@ExcelField(title="申请状态", align=1, sort=130, dictType="DM0043")
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getValidityDateFrom() {
		return validityDateFrom;
	}

	public void setValidityDateFrom(Date validityDateFrom) {
		this.validityDateFrom = validityDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getValidityDateTo() {
		return validityDateTo;
	}

	public void setValidityDateTo(Date validityDateTo) {
		this.validityDateTo = validityDateTo;
	}

	@ExcelField(title="授权开始日期", align=1, sort=110)
	public String getValidityDateFromStr() {
		return validityDateFromStr;
	}

	public void setValidityDateFromStr(String validityDateFromStr) {
		this.validityDateFromStr = validityDateFromStr;
	}
	
	@ExcelField(title="授权结束日期", align=1, sort=120)
	public String getValidityDateToStr() {
		return validityDateToStr;
	}

	public void setValidityDateToStr(String validityDateToStr) {
		this.validityDateToStr = validityDateToStr;
	}
	
	@Length(min=0, max=5, message="授权提供方式长度必须介于 0 和 5 之间")
	public String getAuthorOfferType() {
		return authorOfferType;
	}

	public void setAuthorOfferType(String authorOfferType) {
		this.authorOfferType = authorOfferType;
	}
	
	@Length(min=0, max=300, message="方式说明长度必须介于 0 和 300 之间")
	public String getOfferRemarks() {
		return offerRemarks;
	}

	public void setOfferRemarks(String offerRemarks) {
		this.offerRemarks = offerRemarks;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
	@Length(min=0, max=100, message="联系人长度必须介于 0 和 100 之间")
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@Length(min=0, max=50, message="代理商/经销商长度必须介于 0 和 50 之间")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title="最终客户", align=1, sort=50)
	public String getEndCustomerName() {
		return endCustomerName;
	}

	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
	}

	@ExcelField(title="行业", align=1, sort=60, dictType="DM0002")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@ExcelField(title="代理商/经销商", align=1, sort=90)
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@ExcelField(title="授权型号", align=1, sort=100)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<TaTenderAuthorDtl> getTaTenderAuthorDtlList() {
		return taTenderAuthorDtlList;
	}

	public void setTaTenderAuthorDtlList(List<TaTenderAuthorDtl> taTenderAuthorDtlList) {
		this.taTenderAuthorDtlList = taTenderAuthorDtlList;
	}

	public String getAuthorizationNoSearch() {
		return authorizationNoSearch;
	}

	public void setAuthorizationNoSearch(String authorizationNoSearch) {
		this.authorizationNoSearch = authorizationNoSearch;
	}

	public String getProjectNameSearch() {
		return projectNameSearch;
	}

	public void setProjectNameSearch(String projectNameSearch) {
		this.projectNameSearch = projectNameSearch;
	}

	public Date getValidityDateFromSearch() {
		return validityDateFromSearch;
	}

	public void setValidityDateFromSearch(Date validityDateFromSearch) {
		this.validityDateFromSearch = validityDateFromSearch;
	}

	public Date getValidityDateToSearch() {
		return validityDateToSearch;
	}

	public void setValidityDateToSearch(Date validityDateToSearch) {
		this.validityDateToSearch = validityDateToSearch;
	}

	public Date getBidOpeningDateFromSearch() {
		return bidOpeningDateFromSearch;
	}

	public void setBidOpeningDateFromSearch(Date bidOpeningDateFromSearch) {
		this.bidOpeningDateFromSearch = bidOpeningDateFromSearch;
	}

	public Date getBidOpeningDateToSearch() {
		return bidOpeningDateToSearch;
	}

	public void setBidOpeningDateToSearch(Date bidOpeningDateToSearch) {
		this.bidOpeningDateToSearch = bidOpeningDateToSearch;
	}

	public String getTendereeSearch() {
		return tendereeSearch;
	}

	public void setTendereeSearch(String tendereeSearch) {
		this.tendereeSearch = tendereeSearch;
	}

	public String getEndCustomerIdSearch() {
		return endCustomerIdSearch;
	}

	public void setEndCustomerIdSearch(String endCustomerIdSearch) {
		this.endCustomerIdSearch = endCustomerIdSearch;
	}

	public String getAgentIdSearch() {
		return agentIdSearch;
	}

	public void setAgentIdSearch(String agentIdSearch) {
		this.agentIdSearch = agentIdSearch;
	}

	public String getWorkflowStatusSearch() {
		return workflowStatusSearch;
	}

	public void setWorkflowStatusSearch(String workflowStatusSearch) {
		this.workflowStatusSearch = workflowStatusSearch;
	}

	public String getBidResultSearch() {
		return bidResultSearch;
	}

	public void setBidResultSearch(String bidResultSearch) {
		this.bidResultSearch = bidResultSearch;
	}

	public String getBusinessOppId() {
		return businessOppId;
	}

	public void setBusinessOppId(String businessOppId) {
		this.businessOppId = businessOppId;
	}

	@ExcelField(title="申请人", align=1, sort=131)
	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public List<CoAttachments> getAttachmentsList() {
		return attachmentsList;
	}

	public void setAttachmentsList(List<CoAttachments> attachmentsList) {
		this.attachmentsList = attachmentsList;
	}

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}