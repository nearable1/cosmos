package com.xiaoniu.call.customer.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "asm_config_log")
public class AsmConfigLog {
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
     * 厂商
     */
    @Column(name = "manufacturer")
    private String manufacturer;

    /**
     * 状态1:启用；2-禁止
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 上报类型：1=客户端，2=服务端
     */
    @Column(name = "report_type")
    private Integer reportType;

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
     * 获取厂商
     *
     * @return manufacturer - 厂商
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 设置厂商
     *
     * @param manufacturer 厂商
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
     * 获取上报类型：1=客户端，2=服务端
     *
     * @return report_type - 上报类型：1=客户端，2=服务端
     */
    public Integer getReportType() {
        return reportType;
    }

    /**
     * 设置上报类型：1=客户端，2=服务端
     *
     * @param reportType 上报类型：1=客户端，2=服务端
     */
    public void setReportType(Integer reportType) {
        this.reportType = reportType;
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
}