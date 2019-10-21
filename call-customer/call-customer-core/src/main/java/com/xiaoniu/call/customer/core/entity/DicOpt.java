package com.xiaoniu.call.customer.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dic_opt")
public class DicOpt {
    /**
     * 字典值选择项ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 字典值编码
     */
    @Column(name = "dic_code")
    private String dicCode;

    /**
     * 字典选项编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_man")
    private String createMan;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 修改人
     */
    @Column(name = "modify_man")
    private String modifyMan;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 状态1:启用；2-禁止
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 值
     */
    @Column(name = "value")
    private String value;

    /**
     * 获取字典值选择项ID
     *
     * @return id - 字典值选择项ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置字典值选择项ID
     *
     * @param id 字典值选择项ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取字典值编码
     *
     * @return dic_code - 字典值编码
     */
    public String getDicCode() {
        return dicCode;
    }

    /**
     * 设置字典值编码
     *
     * @param dicCode 字典值编码
     */
    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    /**
     * 获取字典选项编码
     *
     * @return code - 字典选项编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置字典选项编码
     *
     * @param code 字典选项编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人
     *
     * @return create_man - 创建人
     */
    public String getCreateMan() {
        return createMan;
    }

    /**
     * 设置创建人
     *
     * @param createMan 创建人
     */
    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取修改人
     *
     * @return modify_man - 修改人
     */
    public String getModifyMan() {
        return modifyMan;
    }

    /**
     * 设置修改人
     *
     * @param modifyMan 修改人
     */
    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取状态1:启用；2-禁止
     *
     * @return state - 状态1:启用；2-禁止
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态1:启用；2-禁止
     *
     * @param state 状态1:启用；2-禁止
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取值
     *
     * @return value - 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value;
    }
}