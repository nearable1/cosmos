package com.xiaoniu.call.customer.api.dto;

import java.util.Date;

public class SmallDictDTO {

    //添加0,修改1
    private String smallDictType;

    /**
     * 字典值选择项ID
     */
    private Integer smallId;

    /**
     * 字典值编码
     */
    private String bigDicCode;

    /**
     * 字典选项编码
     */
    private String smallCode;

    /**
     * 名称
     */
    private String smallName;

    /**
     * 值
     */
    private String smallValue;
    private String[] smallVal;
    private Integer valueType;//数据类型：1：数组类型，2字符串类型

    /**
     * 排序
     */
    private Integer smallSort;

    /**
     * 创建时间
     */
    private Date smallCreateTime;

    /**
     * 创建人
     */
    private String smallCreateMan;

    /**
     * 修改时间
     */
    private Date smallModifyTime;

    /**
     * 修改人
     */
    private String smallModifyMan;

    /**
     * 备注
     */
    private String smallRemark;

    /**
     * 状态1:启用；2-禁止
     */
    private Integer smallState;
    private String smallStateVal;

    public String getSmallDictType() {
        return smallDictType;
    }

    public void setSmallDictType(String smallDictType) {
        this.smallDictType = smallDictType;
    }

    public Integer getSmallId() {
        return smallId;
    }

    public void setSmallId(Integer smallId) {
        this.smallId = smallId;
    }

    public String getBigDicCode() {
        return bigDicCode;
    }

    public void setBigDicCode(String bigDicCode) {
        this.bigDicCode = bigDicCode.replaceAll(" ", "");
    }

    public String getSmallCode() {
        return smallCode;
    }

    public void setSmallCode(String smallCode) {
        this.smallCode = smallCode.replaceAll(" ", "");
    }

    public String getSmallName() {
        return smallName;
    }

    public void setSmallName(String smallName) {
        this.smallName = smallName;
    }

    public String getSmallValue() {
        return smallValue;
    }

    public void setSmallValue(String smallValue) {
        this.smallValue = smallValue.trim();
    }

    public String[] getSmallVal() {
        return smallVal;
    }

    public void setSmallVal(String[] smallVal) {
        this.smallVal = smallVal;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Integer getSmallSort() {
        return smallSort;
    }

    public void setSmallSort(Integer smallSort) {
        this.smallSort = smallSort;
    }

    public Date getSmallCreateTime() {
        return smallCreateTime;
    }

    public void setSmallCreateTime(Date smallCreateTime) {
        this.smallCreateTime = smallCreateTime;
    }

    public String getSmallCreateMan() {
        return smallCreateMan;
    }

    public void setSmallCreateMan(String smallCreateMan) {
        this.smallCreateMan = smallCreateMan;
    }

    public Date getSmallModifyTime() {
        return smallModifyTime;
    }

    public void setSmallModifyTime(Date smallModifyTime) {
        this.smallModifyTime = smallModifyTime;
    }

    public String getSmallModifyMan() {
        return smallModifyMan;
    }

    public void setSmallModifyMan(String smallModifyMan) {
        this.smallModifyMan = smallModifyMan;
    }

    public String getSmallRemark() {
        return smallRemark;
    }

    public void setSmallRemark(String smallRemark) {
        this.smallRemark = smallRemark;
    }

    public Integer getSmallState() {
        return smallState;
    }

    public void setSmallState(Integer smallState) {
        this.smallState = smallState;
    }

    public String getSmallStateVal() {
        return smallStateVal;
    }

    public void setSmallStateVal(String smallStateVal) {
        this.smallStateVal = smallStateVal;
    }
}
