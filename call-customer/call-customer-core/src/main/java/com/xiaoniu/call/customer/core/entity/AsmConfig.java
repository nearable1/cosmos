package com.xiaoniu.call.customer.core.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "asm_config")
public class AsmConfig {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * rom
     */
    @Column(name = "rom")
    private String rom;

    /**
     * api
     */
    @Column(name = "api")
    private String api;

    /**
     * model
     */
    @Column(name = "model")
    private String model;

    /**
     * model
     */
    @Column(name = "manufacturer")
    private String manufacturer;

    /**
     * 修改人
     */
    @Column(name = "modify_man")
    private String modifyMan;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 权重，越大越靠前
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 权限id
     */
    @Column(name = "asm_data_id")
    private Long asmDataId;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取rom
     *
     * @return rom - rom
     */
    public String getRom() {
        return rom;
    }

    /**
     * 设置rom
     *
     * @param rom rom
     */
    public void setRom(String rom) {
        this.rom = rom;
    }

    /**
     * 获取api
     *
     * @return api - api
     */
    public String getApi() {
        return api;
    }

    /**
     * 设置api
     *
     * @param api api
     */
    public void setApi(String api) {
        this.api = api;
    }

    /**
     * 获取model
     *
     * @return model - model
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置model
     *
     * @param model model
     */
    public void setModel(String model) {
        this.model = model;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * 获取权重，越大越靠前
     *
     * @return sort - 权重，越大越靠前
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置权重，越大越靠前
     *
     * @param sort 权重，越大越靠前
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getAsmDataId() {
        return asmDataId;
    }

    public void setAsmDataId(Long asmDataId) {
        this.asmDataId = asmDataId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}