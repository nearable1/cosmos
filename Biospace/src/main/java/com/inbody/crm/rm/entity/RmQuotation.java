/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.ActEntity;

/**
 * 主子表Entity
 * 
 * @author yangj
 * @version 2017-09-04
 */
@Alias("RepairQuotation")
public class RmQuotation extends ActEntity<RmQuotation> {

    private static final long serialVersionUID = 1L;
    private String procInsId; // 流程编号
    private String quotationNo; // 报价单编号 
    private String quotationType; // 报价单类型 : 代码
    private String repairNo; // 维修编号 : YYMMDD(6)+流水(4-年循环)
    private String responsiblePersonId; // 负责人
    private String responsiblePersonName; // 负责人
    private String organize;
    private String customerId; // 客户代码
    private String customerName; // 客户名称
    private Date quoteDate; // 报价日期
    private String contactsName; // 联系人
    private String telephone; // 电话
    private String address; // 地址
    private String receiveStatus; // 收款状态 : 代码
    private Date actDateFrom; // 收款日期
    private String invoiceTitle; // 开票抬头
    private String newRemarks; // 备注说明
    private String invoiceStatus; // 开票状态 : 代码
    private String ifOccupy; // 是否占用 : 代码
    private String ifOut; // 是否出库 : 代码
    private String snNo; // 报价单对应的机器sn号码
    private String repairType; // 报价单对应的维修分类
    private BigDecimal totalAmount; // 报价单总金额
    private BigDecimal defaultTax;  // 报价单明细默认税金
    private boolean canInvoiceApply; // 报价单发票是否可申请 
    private String defaultWarranty;  // 报价单明细是否保内默认值：1，是，0，否

    private BigDecimal quotationDtlTotalAmount; // 报价单总金额
    private BigDecimal quotationDtlActTotalAmount; // 报价单实际总金额
	private BigDecimal imInvoiceTotalAmount;	// 开票总金额
    private String depositRate; // 折扣率

    public BigDecimal getQuotationDtlTotalAmount() {
		return quotationDtlTotalAmount;
	}

	public void setQuotationDtlTotalAmount(BigDecimal quotationDtlTotalAmount) {
		this.quotationDtlTotalAmount = quotationDtlTotalAmount;
	}

	public BigDecimal getQuotationDtlActTotalAmount() {
		return quotationDtlActTotalAmount;
	}

	public void setQuotationDtlActTotalAmount(BigDecimal quotationDtlActTotalAmount) {
		this.quotationDtlActTotalAmount = quotationDtlActTotalAmount;
	}

	public BigDecimal getImInvoiceTotalAmount() {
		return imInvoiceTotalAmount;
	}

	public void setImInvoiceTotalAmount(BigDecimal imInvoiceTotalAmount) {
		this.imInvoiceTotalAmount = imInvoiceTotalAmount;
	}

	public String getDepositRate() {
		return depositRate;
	}

	public void setDepositRate(String depositRate) {
		this.depositRate = depositRate;
	}

	/** 报价单明细一览 */
    private List<RmQuotationDtl> quotationDtlList;

    /** 附件信息 */
    private List<CoAttachments> attachmentsList;

    /** 报价单发票信息（保存用） */
    private ImInvoice invoice;

    /** 报价单发票明细信息（保存用） */
    private List<ImInvoiceDtl> invoiceDtlList;

    /** 报价单发票信息 */
    private List<ImInvoice> invoiceList;

    /** 报价单发票明细信息 */
    private List<List<ImInvoiceDtl>> invoiceDtlListGroup;

    /** 附件文件 */
    private MultipartFile[] files;

    /** 工作流画面操作按钮 */
    private String opt;

    public RmQuotation() {
        super();
    }

    public RmQuotation(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "流程编号长度必须介于 0 和 64 之间")
    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    @Length(min = 1, max = 15, message = "报价单编号 : 维修编号+&ldquo;_&rdquo;+01(预报价单）/02（最终报价单）长度必须介于 1 和 15 之间")
    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    @Length(min = 1, max = 2, message = "报价单类型 : 代码长度必须介于 1 和 2 之间")
    public String getQuotationType() {
        return quotationType;
    }

    public void setQuotationType(String quotationType) {
        this.quotationType = quotationType;
    }

    @Length(min = 1, max = 15, message = "维修编号 : YYMMDD(6)+流水(4-年循环)长度必须介于 1 和 15 之间")
    public String getRepairNo() {
        return repairNo;
    }

    public void setRepairNo(String repairNo) {
        this.repairNo = repairNo;
    }

    @Length(min = 1, max = 50, message = "负责人长度必须介于 1 和 50 之间")
    public String getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(String responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	public String getOrganize() {
        return organize;
    }

    public void setOrganize(String organize) {
        this.organize = organize;
    }

    @Length(min = 0, max = 15, message = "客户代码 长度必须介于 0 和 15 之间")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        this.quoteDate = quoteDate;
    }

    @Length(min = 0, max = 100, message = "联系人长度必须介于 0 和 100 之间")
    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    @Length(min = 0, max = 50, message = "电话长度必须介于 0 和 50 之间")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Length(min = 0, max = 300, message = "地址长度必须介于 0 和 300 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min = 0, max = 2, message = "收款状态 : 代码长度必须介于 0 和 2 之间")
    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getActDateFrom() {
        return actDateFrom;
    }

    public void setActDateFrom(Date actDateFrom) {
        this.actDateFrom = actDateFrom;
    }

    @Length(min = 0, max = 100, message = "开票抬头长度必须介于 0 和 100 之间")
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    @Length(min = 0, max = 300, message = "备注说明长度必须介于 0 和 300 之间")
    public String getNewRemarks() {
        return newRemarks;
    }

    public void setNewRemarks(String newRemarks) {
        this.newRemarks = newRemarks;
    }

    @Length(min = 0, max = 2, message = "开票状态 : 代码长度必须介于 0 和 2 之间")
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Length(min = 0, max = 2, message = "是否占用 : 代码长度必须介于 0 和 2 之间")
    public String getIfOccupy() {
        return ifOccupy;
    }

    public void setIfOccupy(String ifOccupy) {
        this.ifOccupy = ifOccupy;
    }

    @Length(min = 0, max = 2, message = "是否出库 : 代码长度必须介于 0 和 2 之间")
    public String getIfOut() {
        return ifOut;
    }

    public void setIfOut(String ifOut) {
        this.ifOut = ifOut;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDefaultTax() {
        return defaultTax;
    }

    public void setDefaultTax(BigDecimal defaultTax) {
        this.defaultTax = defaultTax;
    }

    public boolean getCanInvoiceApply() {
        return canInvoiceApply;
    }

    public void setCanInvoiceApply(boolean canInvoiceApply) {
        this.canInvoiceApply = canInvoiceApply;
    }

    public String getDefaultWarranty() {
        return defaultWarranty;
    }

    public void setDefaultWarranty(String defaultWarranty) {
        this.defaultWarranty = defaultWarranty;
    }

    public List<RmQuotationDtl> getQuotationDtlList() {
        return quotationDtlList;
    }

    public void setQuotationDtlList(List<RmQuotationDtl> quotationDtlList) {
        this.quotationDtlList = quotationDtlList;
    }

    public List<CoAttachments> getAttachmentsList() {
        return attachmentsList;
    }

    public void setAttachmentsList(List<CoAttachments> attachmentsList) {
        this.attachmentsList = attachmentsList;
    }

    public ImInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(ImInvoice invoice) {
        this.invoice = invoice;
    }

    public List<ImInvoiceDtl> getInvoiceDtlList() {
        return invoiceDtlList;
    }

    public void setInvoiceDtlList(List<ImInvoiceDtl> invoiceDtlList) {
        this.invoiceDtlList = invoiceDtlList;
    }

    public List<ImInvoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<ImInvoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public List<List<ImInvoiceDtl>> getInvoiceDtlListGroup() {
        return invoiceDtlListGroup;
    }

    public void setInvoiceDtlListGroup(List<List<ImInvoiceDtl>> invoiceDtlListGroup) {
        this.invoiceDtlListGroup = invoiceDtlListGroup;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}