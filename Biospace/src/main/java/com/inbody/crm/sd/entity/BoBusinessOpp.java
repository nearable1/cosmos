/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.DataEntity;
import com.inbody.crm.common.utils.excel.annotation.ExcelField;
import com.inbody.crm.cm.entity.CustomerManagement;
import com.inbody.crm.modules.sys.entity.User;

/**
 * 主子表生成Entity
 * @author Nssol
 * @version 2017-07-20
 */
public class BoBusinessOpp extends DataEntity<BoBusinessOpp> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String procInsGroupid;		// 流程组号
	private String businessOppNo;		// 商机号 :
	private String priceSystem;		// 价格体系 : 代码
	private String customerSource;		// 客户来源
	private String responsiblePersonId;		// 负责人Id
	private String responsiblePersonName;		// 负责人Name
	private String organize;
	private String organizeName;
	private String expTurnover;		// 预计成交率 : 单位-%
	private Date expTurnoverMonth;		// 预计成交月
	private String ifSalesPlan;		// 是否销售计划
	private String ifTenderAuthor;		// 是否招标授权 :
	private String ifContractGeneration;		// 是否合同生成
	private String agentId;		// 代理商/经销商
	private String agentName;		// 代理商/经销商
	private String agentContactsName;		// 联系人
	private String agentPosition;		// 职位
	private String agentTelephone;		// 电话
	private String agentEmail;		// 邮件
	private String agentAddress;		// 地址
	private String endCustomerId;		// 最终客户
	private String endCustomerName;		// 最终客户
	private String customerSegmentation;		// 客户细分
	private String contactsName;		// 联系人
	private String position;		// 职位
	private String telephone;		// 电话
	private String email;		// 邮件
	private String address;		// 地址
	private String newRemarks;		// 备注说明
	private String ifFail;		// 是否失败
	private String failRemarks;		// 商机失败备注
	private List<BoBusinessOppDtl> boBusinessOppDtlList = Lists.newArrayList();		// 子表列表
	private List<CustomerManagement> endCustomerList = Lists.newArrayList();
	private List<CustomerManagement> agentInfoList = Lists.newArrayList();
	private List<String> selectedBobusinessList = Lists.newArrayList();	//list页面操作中选中的商机信息
	private List<User> userList = Lists.newArrayList();
	
	private String industry;//行业
	private String saleType;//销售方式
	private String area;//销售方式
	private String industryCode;//行业(科室/系对应的parentID)
	
	// 一览显示用
	private String officeId;
	private String officeName;
	private String lineNo;		// 行号
	private String materialNo;		// 物料号
	private String model;		// 型号
	private String num;		// 数量
	private String unitPrice;		// 单价
	private String totalAmount;		// 总金额
	private String standardPrice;		// 标准价
	private String ifSpecialOffer;		// 是否特价
	private String ifSpecialOfferLabel;		// 是否特价标签显示内容

	private String expTurnoverMonthStr;		// 预计成交月
	
	private String isSalesPlan;// 一览页面的按钮点击
	
	public BoBusinessOpp() {
		super();
	}

	public BoBusinessOpp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=64, message="流程组号长度必须介于 0 和 64 之间")
	public String getProcInsGroupid() {
		return procInsGroupid;
	}

	public void setProcInsGroupid(String procInsGroupid) {
		this.procInsGroupid = procInsGroupid;
	}
	
	@Length(min=1, max=15, message="商机号 :长度必须介于 1 和 15 之间")
	@ExcelField(title="商机号", align=1, sort=10)
	public String getBusinessOppNo() {
		return businessOppNo;
	}

	public void setBusinessOppNo(String businessOppNo) {
		this.businessOppNo = businessOppNo;
	}
	
	@Length(min=0, max=2, message="价格体系 : 代码长度必须介于 0 和 2 之间")
	public String getPriceSystem() {
		return priceSystem;
	}

	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}
	
	@Length(min=0, max=2, message="客户来源长度必须介于 0 和 2 之间")
	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	
	@Length(min=1, max=50, message="负责人长度必须介于 1 和 50 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	@Length(min=0, max=2, message="预计成交率 : 单位-%长度必须介于 0 和 2 之间")
	@ExcelField(title="预计成交率 ", align=1, sort=100, dictType="DM0040")
	public String getExpTurnover() {
		return expTurnover;
	}

	public void setExpTurnover(String expTurnover) {
		this.expTurnover = expTurnover;
	}
	
	@JsonFormat(pattern = "yyyy-MM")
	public Date getExpTurnoverMonth() {
		return expTurnoverMonth;
	}

	public void setExpTurnoverMonth(Date expTurnoverMonth) {
		this.expTurnoverMonth = expTurnoverMonth;
	}

	@ExcelField(title="预定成交月", type=1, align=1, sort=90)
	public String getExpTurnoverMonthStr() {
		return expTurnoverMonthStr;
	}

	public void setExpTurnoverMonthStr(String expTurnoverMonthStr) {
		this.expTurnoverMonthStr = expTurnoverMonthStr;
	}

	@Length(min=1, max=1, message="是否销售计划长度必须介于 1 和 1 之间")
	@ExcelField(title="已转为销售计划", align=1, sort=150, dictType="yes_no")
	public String getIfSalesPlan() {
		return ifSalesPlan;
	}

	public void setIfSalesPlan(String ifSalesPlan) {
		this.ifSalesPlan = ifSalesPlan;
	}
	
	@Length(min=0, max=1, message="是否招标授权 :长度必须介于 1 和 1 之间")
	@ExcelField(title="招标授权", align=1, sort=170, dictType="yes_no")
	public String getIfTenderAuthor() {
		return ifTenderAuthor;
	}

	public void setIfTenderAuthor(String ifTenderAuthor) {
		this.ifTenderAuthor = ifTenderAuthor;
	}
	
	@Length(min=0, max=1, message="是否合同生成长度必须介于 1 和 1 之间")
	@ExcelField(title="合同状态", align=1, sort=160, dictType="yes_no")
	public String getIfContractGeneration() {
		return ifContractGeneration;
	}

	public void setIfContractGeneration(String ifContractGeneration) {
		this.ifContractGeneration = ifContractGeneration;
	}
	
	@Length(min=0, max=50, message="代理商/经销商长度必须介于 0 和 50 之间")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	@Length(min=0, max=50, message="最终客户长度必须介于 0 和 50 之间")
	public String getEndCustomerId() {
		return endCustomerId;
	}

	public void setEndCustomerId(String endCustomerId) {
		this.endCustomerId = endCustomerId;
	}
	
	@Length(min=0, max=30, message="客户细分长度必须介于 0 和 30 之间")
	@ExcelField(title="科室/系", align=1, sort=50, dictType="DM0039")
	public String getCustomerSegmentation() {
		return customerSegmentation;
	}

	public void setCustomerSegmentation(String customerSegmentation) {
		this.customerSegmentation = customerSegmentation;
	}
	
	@Length(min=0, max=100, message="联系人长度必须介于 0 和 100 之间")
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@Length(min=0, max=100, message="职位长度必须介于 0 和 100 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Email(message="邮箱格式不正确")
	@Length(min=0, max=100, message="邮件长度必须介于 0 和 100 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="联系人长度必须介于 0 和 100 之间")
	public String getAgentContactsName() {
		return agentContactsName;
	}

	public void setAgentContactsName(String agentContactsName) {
		this.agentContactsName = agentContactsName;
	}

	@Length(min=0, max=100, message="职位长度必须介于 0 和 100 之间")
	public String getAgentPosition() {
		return agentPosition;
	}

	public void setAgentPosition(String agentPosition) {
		this.agentPosition = agentPosition;
	}

	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getAgentTelephone() {
		return agentTelephone;
	}

	public void setAgentTelephone(String agentTelephone) {
		this.agentTelephone = agentTelephone;
	}

	@Email(message="邮箱格式不正确")
	@Length(min=0, max=100, message="邮件长度必须介于 0 和 100 之间")
	public String getAgentEmail() {
		return agentEmail;
	}

	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}

	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAgentAddress() {
		return agentAddress;
	}

	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	@ExcelField(title="商机备注", align=1, sort=120)
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
	@Length(min=0, max=1, message="是否失败长度必须介于 0 和 1 之间")
	@ExcelField(title="商机失败 ", align=1, sort=110, dictType="yes_no")
	public String getIfFail() {
		return ifFail;
	}

	public void setIfFail(String ifFail) {
		this.ifFail = ifFail;
	}
	
	@Length(min=0, max=300, message="商机失败备注长度必须介于 0 和 300 之间")
	public String getFailRemarks() {
		return failRemarks;
	}

	public void setFailRemarks(String failRemarks) {
		this.failRemarks = failRemarks;
	}
	
	public List<BoBusinessOppDtl> getBoBusinessOppDtlList() {
		return boBusinessOppDtlList;
	}

	public void setBoBusinessOppDtlList(List<BoBusinessOppDtl> boBusinessOppDtlList) {
		this.boBusinessOppDtlList = boBusinessOppDtlList;
	}
	
	public String getIndustry() {
		return industry;
	}
	
	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@ExcelField(title="销售员", align=1, sort=140)
	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	public List<CustomerManagement> getEndCustomerList() {
		return endCustomerList;
	}

	public void setEndCustomerList(List<CustomerManagement> endCustomerList) {
		this.endCustomerList = endCustomerList;
	}

	public List<CustomerManagement> getAgentInfoList() {
		return agentInfoList;
	}

	public void setAgentInfoList(List<CustomerManagement> agentInfoList) {
		this.agentInfoList = agentInfoList;
	}

	@ExcelField(title="代理商/经销商", align=1, sort=30)
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@ExcelField(title="最终客户", align=1, sort=40)
	public String getEndCustomerName() {
		return endCustomerName;
	}

	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
	}

	@ExcelField(title="行", align=1, sort=20)
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	@ExcelField(title="数量", align=3, sort=70)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@ExcelField(title="金额", align=3, sort=80)
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}

	public String getIfSpecialOffer() {
		return ifSpecialOffer;
	}

	public void setIfSpecialOffer(String ifSpecialOffer) {
		this.ifSpecialOffer = ifSpecialOffer;
	}

	public String getIfSpecialOfferLabel() {
		return ifSpecialOfferLabel;
	}

	public void setIfSpecialOfferLabel(String ifSpecialOfferLabel) {
		this.ifSpecialOfferLabel = ifSpecialOfferLabel;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public List<String> getSelectedBobusinessList() {
		return selectedBobusinessList;
	}

	public void setSelectedBobusinessList(List<String> selectedBobusinessList) {
		this.selectedBobusinessList = selectedBobusinessList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getOrganize() {
		return organize;
	}

	public void setOrganize(String organize) {
		this.organize = organize;
	}

	@ExcelField(title="组", align=1, sort=130)
	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	@ExcelField(title="型号", align=1, sort=60)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getIsSalesPlan() {
		return isSalesPlan;
	}

	public void setIsSalesPlan(String isSalesPlan) {
		this.isSalesPlan = isSalesPlan;
	}
}