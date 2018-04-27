/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 单表生成Entity
 * 
 * @author yangj
 * @version 2017-08-16
 */
public class RmRepairListExcel {

    /** sn */
    private String snNo;

    /** 机器型号 */
    private String model;

    /** 生产日期 */
    private String productionDate;

    /** 安装日期 */
    private String actualInstallDate;

    /** 单位名称 */
    private String repairCusName;

    /** 报修日 */
    private String askRepairDate;

    /** 维修分类 */
    private String repairType;

    /** 处理状态 */
    private String repairStatus;

    /** 处理方式 */
    private String repairMethod;

    /** 情况确认 */
    private String issueDetail;

    /** 处理内容 */
    private String processingContent;

    /** 预报价单是否占用 */
    private String ifOccupy;

    /** 最终使用配件 */
    private String acMaterialName;

    /** 数量 */
    private String num;

    /** 开票情况 */
    private String invoiceStatus;

    /** 收款情况 */
    private String receiveStatus;

    /** 最终报价单金额 */
    private String totalAmount;

    /** 负责工程师id */
    private String engineer;

    @ExcelField(title = "机器S/N", type = 1, align = 1, sort = 1)
    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    @ExcelField(title = "机器型号", type = 1, align = 1, sort = 2)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @ExcelField(title = "生产日期", type = 1, align = 1, sort = 3)
    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    @ExcelField(title = "安装日期", type = 1, align = 1, sort = 4)
    public String getActualInstallDate() {
        return actualInstallDate;
    }

    public void setActualInstallDate(String actualInstallDate) {
        this.actualInstallDate = actualInstallDate;
    }

    @ExcelField(title = "单位名称", type = 1, align = 1, sort = 5)
    public String getRepairCusName() {
        return repairCusName;
    }

    public void setRepairCusName(String repairCusName) {
        this.repairCusName = repairCusName;
    }

    @ExcelField(title = "报修日期", type = 1, align = 1, sort = 6)
    public String getAskRepairDate() {
        return askRepairDate;
    }

    public void setAskRepairDate(String askRepairDate) {
        this.askRepairDate = askRepairDate;
    }

    @ExcelField(title = "维修分类", type = 1, align = 1, sort = 7, dictType = "DM0026")
    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    @ExcelField(title = "处理状态", type = 1, align = 1, sort = 8, dictType = "DM0029")
    public String getRepairStatus() {
        return repairStatus;
    }
    
    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    @ExcelField(title = "处理方式", type = 1, align = 1, sort = 9, dictType = "DM0028")
    public String getRepairMethod() {
        return repairMethod;
    }

    public void setRepairMethod(String repairMethod) {
        this.repairMethod = repairMethod;
    }

    @ExcelField(title = "情况确认", type = 1, align = 1, sort = 10)
    public String getIssueDetail() {
        return issueDetail;
    }

    public void setIssueDetail(String issueDetail) {
        this.issueDetail = issueDetail;
    }

    @ExcelField(title = "处理内容", type = 1, align = 1, sort = 11)
    public String getProcessingContent() {
        return processingContent;
    }

    public void setProcessingContent(String processingContent) {
        this.processingContent = processingContent;
    }

    @ExcelField(title = "是否占用", type = 1, align = 1, sort = 12, dictType = "yes_no")
    public String getIfOccupy() {
        return ifOccupy;
    }

    public void setIfOccupy(String ifOccupy) {
        this.ifOccupy = ifOccupy;
    }

    @ExcelField(title = "最终使用配件", type = 1, align = 1, sort = 13)
    public String getAcMaterialName() {
        return acMaterialName;
    }

    public void setAcMaterialName(String acMaterialName) {
        this.acMaterialName = acMaterialName;
    }

    @ExcelField(title = "数量", type = 1, align = 3, sort = 14)
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @ExcelField(title = "开票状态", type = 1, align = 1, sort = 15, dictType = "DM0012")
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @ExcelField(title = "收款状态", type = 1, align = 1, sort = 16, dictType = "DM0011")
    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    @ExcelField(title = "最终报价单金额", type = 1, align = 3, sort = 17)
    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @ExcelField(title = "负责工程师", type = 1, align = 1, sort = 18)
    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }
}