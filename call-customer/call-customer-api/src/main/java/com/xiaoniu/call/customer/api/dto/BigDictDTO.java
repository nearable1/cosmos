package com.xiaoniu.call.customer.api.dto;

import java.util.Date;

public class BigDictDTO {
    /**
     * 字典值ID
     */
    private Integer bigId;

    /**
     * 编码
     */
    private String bigCode;

    /**
     * 名称
     */
    private String bigName;

    /**
     * 排序
     */
    private Integer bigSort;

    /**
     * 创建时间
     */
    private Date bigCreateTime;

    /**
     * 创建人
     */
    private String bigCreateMan;

    /**
     * 修改时间
     */
    private Date bigModifyTime;

    /**
     * 修改人
     */
    private String bigModifyMan;

    /**
     * 备注
     */
    private String bigRemark;

    /**
     * 状态1:启用；2-禁止
     */
    private Integer bigState;
    private String bigStateVal;

    public Integer getBigId() {
        return bigId;
    }

    public void setBigId(Integer bigId) {
        this.bigId = bigId;
    }

    public String getBigCode() {
        return bigCode;
    }

    public void setBigCode(String bigCode) {
        this.bigCode = bigCode.replaceAll(" ", "");
    }

    public String getBigName() {
        return bigName;
    }

    public void setBigName(String bigName) {
        this.bigName = bigName;
    }

    public Integer getBigSort() {
        return bigSort;
    }

    public void setBigSort(Integer bigSort) {
        this.bigSort = bigSort;
    }

    public Date getBigCreateTime() {
        return bigCreateTime;
    }

    public void setBigCreateTime(Date bigCreateTime) {
        this.bigCreateTime = bigCreateTime;
    }

    public String getBigCreateMan() {
        return bigCreateMan;
    }

    public void setBigCreateMan(String bigCreateMan) {
        this.bigCreateMan = bigCreateMan;
    }

    public Date getBigModifyTime() {
        return bigModifyTime;
    }

    public void setBigModifyTime(Date bigModifyTime) {
        this.bigModifyTime = bigModifyTime;
    }

    public String getBigModifyMan() {
        return bigModifyMan;
    }

    public void setBigModifyMan(String bigModifyMan) {
        this.bigModifyMan = bigModifyMan;
    }

    public String getBigRemark() {
        return bigRemark;
    }

    public void setBigRemark(String bigRemark) {
        this.bigRemark = bigRemark;
    }

    public Integer getBigState() {
        return bigState;
    }

    public void setBigState(Integer bigState) {
        this.bigState = bigState;
    }

    public String getBigStateVal() {
        return bigStateVal;
    }

    public void setBigStateVal(String bigStateVal) {
        this.bigStateVal = bigStateVal;
    }

    @Override
    public String toString() {
        return "BigDictVO{" +
                "bigId=" + bigId +
                ", bigCode='" + bigCode + '\'' +
                ", bigName='" + bigName + '\'' +
                ", bigSort=" + bigSort +
                ", bigCreateTime=" + bigCreateTime +
                ", bigCreateMan='" + bigCreateMan + '\'' +
                ", bigModifyTime=" + bigModifyTime +
                ", bigModifyMan='" + bigModifyMan + '\'' +
                ", bigRemark='" + bigRemark + '\'' +
                ", bigState=" + bigState +
                ", bigStateVal='" + bigStateVal + '\'' +
                '}';
    }
}
