/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.entity;

import org.apache.ibatis.type.Alias;

/**
 * 客户信息Entity
 * 
 * @author yangj
 * @version 2017-10-08
 */
@Alias("QuotaCustomer")
public class CmCustomerInfo {

    private String customerChName; // 客户名
    private String invoiceType; // 发票类型
    private String invoiceTitle; // 开票抬头
    private String taxpayerIdentNo; // 纳税人识别号
    private String depositBank; // 开户行
    private String bankAccount; // 银行账号
    private String invoiceAddress; // 地址
    private String telephone; // 电话
    private String industry; // 行业
    private String customerDiff; // 客户分类
    private String province; // 省市
    private String provinceName; // 省市
    private String city; // 城市
    private String cityName; // 城市
    private String region; // 地区

    public String getCustomerChName() {
        return customerChName;
    }

    public void setCustomerChName(String customerChName) {
        this.customerChName = customerChName;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxpayerIdentNo() {
        return taxpayerIdentNo;
    }

    public void setTaxpayerIdentNo(String taxpayerIdentNo) {
        this.taxpayerIdentNo = taxpayerIdentNo;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCustomerDiff() {
        return customerDiff;
    }

    public void setCustomerDiff(String customerDiff) {
        this.customerDiff = customerDiff;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}