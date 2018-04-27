package com.inbody.crm.sp.domain;

import java.math.BigDecimal;

/**
 * 销售履历
 * 
 * @author yangj
 * @version 2017-10-24
 */
public class SpSalesResume {

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
    private BigDecimal unitPrice;
    /** 总额 */
    private BigDecimal totalAmount;
    /** 预计成交率 */
    private Integer expTurnover;
    /** 合同状态 */
    private String orderStatus;
    /** 开票状态 */
    private String invoiceStatus;
    /** 商机备注 */
    private String newRemarks;
    /** 是否生成合同 */
    private String ifContractGeneration;


    public String getOrganize() {
        return organize;
    }

    public void setOrganize(String organize) {
        this.organize = organize;
    }


    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
    }


    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }


    public String getEndCustomer() {
        return endCustomer;
    }

    public void setEndCustomer(String endCustomer) {
        this.endCustomer = endCustomer;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    public Integer getExpTurnover() {
        return expTurnover;
    }

    public void setExpTurnover(Integer expTurnover) {
        this.expTurnover = expTurnover;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }


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
