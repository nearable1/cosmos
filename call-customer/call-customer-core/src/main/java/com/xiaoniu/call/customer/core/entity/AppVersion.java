package com.xiaoniu.call.customer.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "app_version")
public class AppVersion {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * APP类型 0：Android；1：IOS；
     */
    @Column(name = "app_type")
    private Integer appType;

    /**
     * 渠道
     */
    @Column(name = "channel")
    private String channel;

    /**
     * 是否弹窗(0:否,1:是)
     */
    @Column(name = "is_popup")
    private Boolean isPopup;

    /**
     * 版本号
     */
    @Column(name = "version_number")
    private String versionNumber;

    /**
     * 更新下载地址
     */
    @Column(name = "download_url")
    private String downloadUrl;

    /**
     * 强制更新版本
     */
    @Column(name = "is_forced_update")
    private String isForcedUpdate;

    /**
     * 不是强制更新版本
     */
    @Column(name = "version_update")
    private String versionUpdate;

    /**
     * 更新标题
     */
    @Column(name = "change_title")
    private String changeTitle;

    /**
     * 更新描述
     */
    @Column(name = "change_desc")
    private String changeDesc;

    /**
     * 开始更新时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 结束更新时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 状态：1：生效；2：失效
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 操作人
     */
    @Column(name = "operator")
    private String operator;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取主键编号
     *
     * @return id - 主键编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键编号
     *
     * @param id 主键编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取APP类型 0：Android；1：IOS；
     *
     * @return app_type - APP类型 0：Android；1：IOS；
     */
    public Integer getAppType() {
        return appType;
    }

    /**
     * 设置APP类型 0：Android；1：IOS；
     *
     * @param appType APP类型 0：Android；1：IOS；
     */
    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    /**
     * 获取渠道
     *
     * @return channel - 渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置渠道
     *
     * @param channel 渠道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取是否弹窗(0:否,1:是)
     *
     * @return is_popup - 是否弹窗(0:否,1:是)
     */
    public Boolean getIsPopup() {
        return isPopup;
    }

    /**
     * 设置是否弹窗(0:否,1:是)
     *
     * @param isPopup 是否弹窗(0:否,1:是)
     */
    public void setIsPopup(Boolean isPopup) {
        this.isPopup = isPopup;
    }

    /**
     * 获取版本号
     *
     * @return version_number - 版本号
     */
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * 设置版本号
     *
     * @param versionNumber 版本号
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * 获取更新下载地址
     *
     * @return download_url - 更新下载地址
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * 设置更新下载地址
     *
     * @param downloadUrl 更新下载地址
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    /**
     * 获取强制更新版本
     *
     * @return is_forced_update - 强制更新版本
     */
    public String getIsForcedUpdate() {
        return isForcedUpdate;
    }

    /**
     * 设置强制更新版本
     *
     * @param isForcedUpdate 强制更新版本
     */
    public void setIsForcedUpdate(String isForcedUpdate) {
        this.isForcedUpdate = isForcedUpdate;
    }

    /**
     * 获取不是强制更新版本
     *
     * @return version_update - 不是强制更新版本
     */
    public String getVersionUpdate() {
        return versionUpdate;
    }

    /**
     * 设置不是强制更新版本
     *
     * @param versionUpdate 不是强制更新版本
     */
    public void setVersionUpdate(String versionUpdate) {
        this.versionUpdate = versionUpdate;
    }

    /**
     * 获取更新标题
     *
     * @return change_title - 更新标题
     */
    public String getChangeTitle() {
        return changeTitle;
    }

    /**
     * 设置更新标题
     *
     * @param changeTitle 更新标题
     */
    public void setChangeTitle(String changeTitle) {
        this.changeTitle = changeTitle;
    }

    /**
     * 获取更新描述
     *
     * @return change_copy - 更新描述
     */
    public String getChangeDesc() {
        return changeDesc;
    }

    /**
     * 设置更新描述
     *
     * @param changeDesc 更新描述
     */
    public void setChangeCopy(String changeDesc) {
        this.changeDesc = changeDesc;
    }

    /**
     * 获取开始更新时间
     *
     * @return begin_time - 开始更新时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置开始更新时间
     *
     * @param beginTime 开始更新时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取结束更新时间
     *
     * @return end_time - 结束更新时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束更新时间
     *
     * @param endTime 结束更新时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取状态：1：生效；2：失效
     *
     * @return state - 状态：1：生效；2：失效
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态：1：生效；2：失效
     *
     * @param state 状态：1：生效；2：失效
     */
    public void setState(Integer state) {
        this.state = state;
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
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
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
}