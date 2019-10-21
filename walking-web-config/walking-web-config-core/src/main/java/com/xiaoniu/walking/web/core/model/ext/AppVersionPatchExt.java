package com.xiaoniu.walking.web.core.model.ext;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_version_patch")
public class AppVersionPatchExt implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "app_version_patch_id")
    private Integer appVersionPatchId;

    /**
     * APP版本主键
     */
    @Column(name = "app_version_id")
    private Integer appVersionId;

    /**
     * 更新地址
     */
    @Column(name = "update_url")
    private String updateUrl;

    /**
     * APP版本号
     */
    @Column(name = "app_version")
    private String appVersion;

    /**
     * 客户端版本码
     */
    @Column(name = "app_version_code")
    private String appVersionCode;

    /**
     * 客户端MD5校验码
     */
    @Column(name = "encrypt_code")
    private String encryptCode;

    /**
     * APP名称：1-来这花，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    @Column(name = "app_name")
    private Integer appName;

    /**
     * 开关：1-开启，2-关闭
     */
    private Integer state;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 创建人
     */
    @Column(name = "create_man")
    private String createMan;

    /**
     * 更新人
     */
    @Column(name = "modify_man")
    private String modifyMan;

    /**
     * 开始时间Str
     */
    private String startTimeStr;

    /**
     * 结束时间
     */
    private String endTimeStr;

    /**
     * 创建时间
     */
    private String createTimeStr;

    /**
     * 修改时间
     */
    private String modifyTimeStr;

    /**
     * app类型
     */
    private Integer appType;

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getModifyTimeStr() {
        return modifyTimeStr;
    }

    public void setModifyTimeStr(String modifyTimeStr) {
        this.modifyTimeStr = modifyTimeStr;
    }

    /**
     * 获取主键ID
     *
     * @return app_version_patch_id - 主键ID
     */
    public Integer getAppVersionPatchId() {
        return appVersionPatchId;
    }

    /**
     * 设置主键ID
     *
     * @param appVersionPatchId 主键ID
     */
    public void setAppVersionPatchId(Integer appVersionPatchId) {
        this.appVersionPatchId = appVersionPatchId;
    }

    /**
     * 获取APP版本主键
     *
     * @return app_version_id - APP版本主键
     */
    public Integer getAppVersionId() {
        return appVersionId;
    }

    /**
     * 设置APP版本主键
     *
     * @param appVersionId APP版本主键
     */
    public void setAppVersionId(Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    /**
     * 获取更新地址
     *
     * @return update_url - 更新地址
     */
    public String getUpdateUrl() {
        return updateUrl;
    }

    /**
     * 设置更新地址
     *
     * @param updateUrl 更新地址
     */
    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    /**
     * 获取APP版本号
     *
     * @return app_version - APP版本号
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置APP版本号
     *
     * @param appVersion APP版本号
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取客户端版本码
     *
     * @return app_version_code - 客户端版本码
     */
    public String getAppVersionCode() {
        return appVersionCode;
    }

    /**
     * 设置客户端版本码
     *
     * @param appVersionCode 客户端版本码
     */
    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    /**
     * 获取客户端MD5校验码
     *
     * @return encrypt_code - 客户端MD5校验码
     */
    public String getEncryptCode() {
        return encryptCode;
    }

    /**
     * 设置客户端MD5校验码
     *
     * @param encryptCode 客户端MD5校验码
     */
    public void setEncryptCode(String encryptCode) {
        this.encryptCode = encryptCode;
    }

    /**
     * 获取APP名称：1-来这花，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     *
     * @return app_name - APP名称：1-来这花，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    public Integer getAppName() {
        return appName;
    }

    /**
     * 设置APP名称：1-来这花，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     *
     * @param appName APP名称：1-来这花，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    public void setAppName(Integer appName) {
        this.appName = appName;
    }

    /**
     * 获取开关：1-开启，2-关闭
     *
     * @return state - 开关：1-开启，2-关闭
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置开关：1-开启，2-关闭
     *
     * @param state 开关：1-开启，2-关闭
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
     * 获取更新时间
     *
     * @return modify_time - 更新时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置更新时间
     *
     * @param modifyTime 更新时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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
     * 获取更新人
     *
     * @return modify_man - 更新人
     */
    public String getModifyMan() {
        return modifyMan;
    }

    /**
     * 设置更新人
     *
     * @param modifyMan 更新人
     */
    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan;
    }
}