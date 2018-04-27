/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 报价单发票明细Entity
 * 
 * @author yangj
 * @version 2017-10-08
 */
@Alias("QuotaInvoiceDtl")
public class ImInvoiceDtl extends DataEntity<ImInvoiceDtl> {

    private static final long serialVersionUID = 1L;
    private String procInsId; // 流程编号
    private String appId; // 申请ID
    private String invoiceSource; // 发票来源 : 代码
    private String orderNo; // 合同号 :
    private String lineNo; // 行号
    private String lineNo2; // 发票行号
    private String invoiceType; // 开票类型 : 代码
    private String ifRed; // 是否红票
    private BigDecimal tax; // 税金
    private Date invoiceDate; // 开票日期
    private BigDecimal invoiceAmount; // 开票金额
    private Integer num; // 数量
    private String invoiceNo; // 发票号码
    private String expressNo; // 快递编号
    private String expressCompany; // 快递公司
    private String invoiceTitle; // 开票抬头
    private String workflowStatus; // 发票申请状态

    public ImInvoiceDtl() {
        super();
    }

    public ImInvoiceDtl(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "流程编号长度必须介于 0 和 64 之间")
    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    @Length(min = 1, max = 64, message = "申请ID长度必须介于 1 和 64 之间")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Length(min = 1, max = 2, message = "发票来源 : 代码长度必须介于 1 和 2 之间")
    public String getInvoiceSource() {
        return invoiceSource;
    }

    public void setInvoiceSource(String invoiceSource) {
        this.invoiceSource = invoiceSource;
    }

    @Length(min = 1, max = 50, message = "合同号 :长度必须介于 1 和 50 之间")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Length(min = 1, max = 2, message = "行号长度必须介于 1 和 2 之间")
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @Length(min = 1, max = 2, message = "发票行号长度必须介于 1 和 2 之间")
    public String getLineNo2() {
        return lineNo2;
    }

    public void setLineNo2(String lineNo2) {
        this.lineNo2 = lineNo2;
    }

    @Length(min = 1, max = 2, message = "开票类型 : 代码长度必须介于 1 和 2 之间")
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Length(min = 1, max = 1, message = "是否红票长度必须介于 1 和 1 之间")
    public String getIfRed() {
        return ifRed;
    }

    public void setIfRed(String ifRed) {
        this.ifRed = ifRed;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Length(min = 1, max = 3, message = "数量长度必须介于 1 和 3 之间")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Length(min = 0, max = 50, message = "发票号码长度必须介于 0 和 50 之间")
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Length(min = 0, max = 50, message = "快递编号长度必须介于 0 和 50 之间")
    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    @Length(min = 0, max = 100, message = "快递公司长度必须介于 0 和 100 之间")
    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

}