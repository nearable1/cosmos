package com.inbody.crm.sp.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 销售履历excel
 * 
 * @author yangj
 * @version 2017-10-24
 */
public class SpSalesResumeExcel {

    /** 组 */
    private String organize;
    /** 销售员 */
    private String saler;
    /** 代理商/经销商 */
    private String agent;
    /** 最终客户 */
    private String endCustomer;
    /** 型号 */
    private String model;
    /** 数量 */
    private String num;
    /** 单价 */
    private String unitPrice;
    /** 总额 */
    private String totalAmount;
    /** 预计成交率 */
    private String expTurnover;
    /** 合同状态 */
    private String orderStatus;
    /** 开票状态 */
    private String invoiceStatus;
    /** 商机备注 */
    private String newRemarks;
    /** 是否生成合同 */
    private String ifContractGeneration;

    @ExcelField(title = "组", type = 1, align = 1, sort = 1)
    public String getOrganize() {
        return organize;
    }

    public void setOrganize(String organize) {
        this.organize = organize;
    }

    @ExcelField(title = "销售员", type = 1, align = 1, sort = 2)
    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
    }

    @ExcelField(title = "代理商/经销商", type = 1, align = 1, sort = 3)
    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @ExcelField(title = "最终客户", type = 1, align = 1, sort = 4)
    public String getEndCustomer() {
        return endCustomer;
    }

    public void setEndCustomer(String endCustomer) {
        this.endCustomer = endCustomer;
    }

    @ExcelField(title = "型号", type = 1, align = 1, sort = 5)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @ExcelField(title = "数量", type = 1, align = 3, sort = 6)
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @ExcelField(title = "单价", type = 1, align = 3, sort = 7)
    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    @ExcelField(title = "总额", type = 1, align = 3, sort = 8)
    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @ExcelField(title = "预计成交率", type = 1, align = 3, sort = 9)
    public String getExpTurnover() {
        return expTurnover;
    }

    public void setExpTurnover(String expTurnover) {
        this.expTurnover = expTurnover;
    }

    @ExcelField(title = "合同状态", type = 1, align = 1, sort = 10)
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @ExcelField(title = "开票状态", type = 1, align = 1, sort = 11, dictType="DM0012")
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @ExcelField(title = "商机备注", type = 1, align = 1, sort = 12)
    public String getNewRemarks() {
        return newRemarks;
    }

    public void setNewRemarks(String newRemarks) {
        this.newRemarks = newRemarks;
    }

    public String getIfContractGeneration() {
        return ifContractGeneration;
    }

    public void setIfContractGeneration(String ifContractGeneration) {
        this.ifContractGeneration = ifContractGeneration;
    }

}
