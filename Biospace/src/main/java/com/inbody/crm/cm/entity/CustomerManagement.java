package com.inbody.crm.cm.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.ActEntity;
import com.inbody.crm.common.utils.DateUtils;

public class CustomerManagement extends ActEntity<CustomerManagement>{
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String customerId;		// 客户代码 : &ldquo;KH&rdquo;(2)+流水(6)
	private String customerChName;		// 客户名称
	private String customerParentCo;		// 客户总公司代码 : customer_id
	private String customerParentCoName;		// 客户总公司名称
	private String customerType;		// 客户类型 : 代码
	private String axCusNo;		// AX编号
	private Date developDate;		// 发展日期
	private String developDateStr;		// 发展日期 字符串
	private Date developDateFrom;		// 发展日期起
	private Date developDateTo;		// 发展日期止
	private Date lastVisitDate;		// 最后拜访日期
//	private String lastVisitDateStr;		// 最后拜访日期 字符串
	private Integer ifAgreement;		// 是否协议
	private String validityDateFrom;//协议开始日期
	private String officeId;
	private String officeName;
	private String createId;
	private String updateId;
	private Integer count;//个数
	private MultipartFile[] files;
	
	private String ifFromBusiness;

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getValidityDateFrom() {
		return validityDateFrom;
	}

	public void setValidityDateFrom(String validityDateFrom) {
		this.validityDateFrom = validityDateFrom;
	}
	public String getValidityDateTo() {
		return validityDateTo;
	}

	public void setValidityDateTo(String validityDateTo) {
		this.validityDateTo = validityDateTo;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	private String validityDateTo;//协议结束日期
	private String responsiblePersonId;		// 负责人：代码
	private String responsiblePersonName;		// 负责人:名称
	private Integer ifImportant;		// 是否重点对象
	private Integer ifEffective;		// 是否有效
	private String ifImportantStr;		// 是否重点对象
	private String ifEffectiveStr;		// 是否有效
	private String industry;		// 行业 : 代码
	private String customerDiff;		// 客户分类 : 代码
	private String region;		// 地区 : 代码
	private String province;		// 省市 : 代码
	private String city;		// 城市 : 代码
	private String provinceName;		// 省市 : 名称
	private String cityName;		// 城市 : 名称
	private String address;		// 地址
	private String invoiceType;		// 开票类型 : 代码
	private String invoiceTitle;		// 开票抬头
	private String taxpayerIdentNo;		// 纳税人识别号
	private String depositBank;		// 开户行
	private String bankAccount;		// 银行账号
	private String invoiceAddress;		// 开票地址
	private String telephone;		// 电话
	private String zipCode;	
	private String group;
	private List<CmAgreement> cmAgreement;
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public CustomerManagement() {
		super();
	}

	public CustomerManagement(String id){
		super(id);
	}
	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="客户代码 : &ldquo;KH&rdquo;(2)+流水(6)长度必须介于 1 和 15 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	@Length(min=1, max=100, message="客户名称长度必须介于 1 和 100 之间")
	public String getCustomerChName() {
		return customerChName;
	}

	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}
	@Length(min=0, max=50, message="客户总公司代码 : customer_id长度必须介于 0 和 50 之间")
	public String getCustomerParentCo() {
		return customerParentCo;
	}

	public void setCustomerParentCo(String customerParentCo) {
		this.customerParentCo = customerParentCo;
	}
	@Length(min=1, max=2, message="客户类型 : 代码长度必须介于 1 和 2 之间")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	@Length(min=0, max=50, message="AX编号长度必须介于 0 和 50 之间")
	public String getAxCusNo() {
		return axCusNo;
	}

	public void setAxCusNo(String axCusNo) {
		this.axCusNo = axCusNo;
	}
	
	
	

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastVisitDate() {
		return lastVisitDate;
	}
	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}

	public String getLastVisitDateStr() {
		return DateUtils.formatDate(lastVisitDate, "yyyy-MM-dd");
	}

//	public void setLastVisitDateStr(String lastVisitDateStr) {
//		this.lastVisitDateStr = lastVisitDateStr;
//	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDevelopDate() {
		return developDate;
	}

	public void setDevelopDate(Date developDate) {
		this.developDate = developDate;
	}

	public String getDevelopDateStr() {
		return developDateStr;
	}

	public void setDevelopDateStr(String developDateStr) {
		this.developDateStr = developDateStr;
	}

	@Length(min=0, max=50, message="负责人长度必须介于 0 和 50 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	
	public Integer getIfEffective() {
		return ifEffective;
	}
	
	public void setIfEffective(Integer ifEffective) {
		this.ifEffective = ifEffective;
	}
	@Length(min=0, max=1, message="是否重点对象长度必须介于 0 和 1 之间")
	public Integer getIfImportant() {
		return ifImportant;
	}

	public void setIfImportant(Integer ifImportant) {
		this.ifImportant = ifImportant;
	}

	public Integer getIfAgreement() {
		return ifAgreement;
	}

	public void setIfAgreement(Integer ifAgreement) {
		this.ifAgreement = ifAgreement;
	}

	@Length(min=0, max=2, message="行业 : 代码长度必须介于 0 和 2 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=2, message="客户分类 : 代码长度必须介于 0 和 2 之间")
	public String getCustomerDiff() {
		return customerDiff;
	}

	public void setCustomerDiff(String customerDiff) {
		this.customerDiff = customerDiff;
	}
	@Length(min=0, max=2, message="地区 : 代码长度必须介于 0 和 2 之间")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	@Length(min=0, max=10, message="省市 : 代码长度必须介于 0 和 10 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	@Length(min=0, max=10, message="城市 : 代码长度必须介于 0 和 10 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=2, message="开票类型 : 代码长度必须介于 0 和 2 之间")
	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	@Length(min=0, max=100, message="开票抬头长度必须介于 0 和 100 之间")
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	
	@Length(min=0, max=50, message="纳税人识别号长度必须介于 0 和 50 之间")
	public String getTaxpayerIdentNo() {
		return taxpayerIdentNo;
	}

	public void setTaxpayerIdentNo(String taxpayerIdentNo) {
		this.taxpayerIdentNo = taxpayerIdentNo;
	}
	
	@Length(min=0, max=100, message="开户行长度必须介于 0 和 100 之间")
	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	
	@Length(min=0, max=100, message="银行账号长度必须介于 0 和 100 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@Length(min=0, max=100, message="开票地址长度必须介于 0 和 100 之间")
	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	
	@Length(min=1, max=50, message="电话长度必须介于 1 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=10, message="邮编长度必须介于 0 和 10 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getDevelopDateFrom() {
		return developDateFrom;
	}

	public void setDevelopDateFrom(Date developDateFrom) {
		this.developDateFrom = developDateFrom;
	}

	public Date getDevelopDateTo() {
		return developDateTo;
	}

	public void setDevelopDateTo(Date developDateTo) {
		this.developDateTo = developDateTo;
	}

	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCustomerParentCoName() {
		return customerParentCoName;
	}

	public void setCustomerParentCoName(String customerParentCoName) {
		this.customerParentCoName = customerParentCoName;
	}

	public List<CmAgreement> getCmAgreement() {
		return cmAgreement;
	}

	public void setCmAgreement(List<CmAgreement> cmAgreement) {
		this.cmAgreement = cmAgreement;
	}

	public String getIfImportantStr() {
		return ifImportantStr;
	}

	public void setIfImportantStr(String ifImportantStr) {
		this.ifImportantStr = ifImportantStr;
	}

	public String getIfEffectiveStr() {
		return ifEffectiveStr;
	}

	public void setIfEffectiveStr(String ifEffectiveStr) {
		this.ifEffectiveStr = ifEffectiveStr;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getIfFromBusiness() {
		return ifFromBusiness;
	}

	public void setIfFromBusiness(String ifFromBusiness) {
		this.ifFromBusiness = ifFromBusiness;
	}

	public List<CmAgreement> cmAgreementList = Lists.newArrayList();

	public List<CmAgreement> getCmAgreementList() {
		return cmAgreementList;
	}

	public void setCmAgreementList(List<CmAgreement> cmAgreementList) {
		this.cmAgreementList = cmAgreementList;
	}
}
