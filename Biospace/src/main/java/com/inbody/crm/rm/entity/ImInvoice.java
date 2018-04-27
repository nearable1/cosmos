/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.entity;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 报价单发票信息Entity
 * 
 * @author yangj
 * @version 2017-10-08
 */
@Alias("QuotaInvoice")
public class ImInvoice extends DataEntity<ImInvoice> {

    private static final long serialVersionUID = 1L;
    private String procInsId; // 流程编号
    private String orderNo; // 合同号 :
    private String ticketMethod; // 取票方式 : 代码
    private String invoiceTitle; // 开票抬头
    private String taxpayerIdentNo; // 纳税人识别号
    private String depositBank; // 开户行
    private String bankAccount; // 银行账号
    private String invoiceAddress; // 开票地址
    private String telephone; // 电话
    private String newRemarks; // 备注说明
    private String recipients; // 收件人
    private String repTelephone; // 收件电话
    private String workflowStatus; // 工作流状态 : 代码
    private String address; // 地址
    private String invoiceType; // 开票类型 : 代码

    public ImInvoice() {
        super();
    }

    public ImInvoice(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "流程编号长度必须介于 0 和 64 之间")
    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    @Length(min = 1, max = 50, message = "合同号 :长度必须介于 1 和 50 之间")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Length(min = 0, max = 2, message = "取票方式 : 代码长度必须介于 0 和 2 之间")
    public String getTicketMethod() {
        return ticketMethod;
    }

    public void setTicketMethod(String ticketMethod) {
        this.ticketMethod = ticketMethod;
    }

    @Length(min = 0, max = 100, message = "开票抬头长度必须介于 0 和 100 之间")
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    @Length(min = 0, max = 50, message = "纳税人识别号长度必须介于 0 和 50 之间")
    public String getTaxpayerIdentNo() {
        return taxpayerIdentNo;
    }

    public void setTaxpayerIdentNo(String taxpayerIdentNo) {
        this.taxpayerIdentNo = taxpayerIdentNo;
    }

    @Length(min = 0, max = 100, message = "开户行长度必须介于 0 和 100 之间")
    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    @Length(min = 0, max = 100, message = "银行账号长度必须介于 0 和 100 之间")
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Length(min = 0, max = 100, message = "开票地址长度必须介于 0 和 100 之间")
    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    @Length(min = 0, max = 50, message = "电话长度必须介于 0 和 50 之间")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Length(min = 0, max = 300, message = "备注说明长度必须介于 0 和 300 之间")
    public String getNewRemarks() {
        return newRemarks;
    }

    public void setNewRemarks(String newRemarks) {
        this.newRemarks = newRemarks;
    }

    @Length(min = 0, max = 50, message = "收件人长度必须介于 0 和 50 之间")
    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    @Length(min = 0, max = 50, message = "收件电话长度必须介于 0 和 50 之间")
    public String getRepTelephone() {
        return repTelephone;
    }

    public void setRepTelephone(String repTelephone) {
        this.repTelephone = repTelephone;
    }

    @Length(min = 1, max = 2, message = "工作流状态 : 代码长度必须介于 1 和 2 之间")
    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    @Length(min = 0, max = 300, message = "地址长度必须介于 0 和 300 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

}