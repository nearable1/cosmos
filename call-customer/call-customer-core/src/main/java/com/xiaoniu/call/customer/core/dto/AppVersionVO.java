package com.xiaoniu.loan.config.api.vo;

import java.io.Serializable;
import java.util.Date;

public class AppVersionVO implements Serializable {
    private static final long serialVersionUID = 6446804685011174869L;
    /**
     * 版本ID
     */
    private String appVersionId;

    /**
     * APP名称：1-闪贷；2-速贷；3-应急现金；4-惠花花；5-秒购；6-分多多
     */
    private Integer appName;

    /**
     * APP类型-1:android,2:ios
     */
    private String appType;

    /**
     * 是否弹窗-2:否,1:是
     */
    private String isPopup;

    /**
     * 版本号
     */
    private String versionNumber;

    /**
     * 更新下载地址
     */
    private String downloadUrl;

    /**
     * 强制更新版本
     */
    private String isForcedUpdate;

    /**
     * 不是强制更新版本
     */
    private String versionUpdate;

    /**
     * 更新标题
     */
    private String changeTitle;

    /**
     * 更新描述
     */
    private String changeCopy;

    /**
     * 开始更新时间
     */
    private Date beginTime;

    /**
     * 结束更新时间
     */
    private Date endTime;

    /**
     * 状态: 1 有效  2关闭 
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createMan;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private String modifyMan;

    /**
     * 备注：渠道
     */
    private String remark;

    /**
     * 版本ID
     * @return app_version_id 版本ID
     */
    public String getAppVersionId() {
        return appVersionId;
    }

    /**
     * 版本ID
     * @param appVersionId 版本ID
     */
    public void setAppVersionId(String appVersionId) {
        this.appVersionId = appVersionId == null ? null : appVersionId.trim();
    }

    /**
     * APP名称：1-闪贷；2-速贷；3-应急现金；4-惠花花；5-秒购；6-分多多
     * @return app_name APP名称：1-闪贷；2-速贷；3-应急现金；4-惠花花；5-秒购；6-分多多
     */
    public Integer getAppName() {
        return appName;
    }

    /**
     * APP名称：1-闪贷；2-速贷；3-应急现金；4-惠花花；5-秒购；6-分多多
     * @param appName APP名称：1-闪贷；2-速贷；3-应急现金；4-惠花花；5-秒购；6-分多多
     */
    public void setAppName(Integer appName) {
        this.appName = appName;
    }

    /**
     * APP类型-1:android,2:ios
     * @return app_type APP类型-1:android,2:ios
     */
    public String getAppType() {
        return appType;
    }

    /**
     * APP类型-1:android,2:ios
     * @param appType APP类型-1:android,2:ios
     */
    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    /**
     * 是否弹窗-2:否,1:是
     * @return is_popup 是否弹窗-2:否,1:是
     */
    public String getIsPopup() {
        return isPopup;
    }

    /**
     * 是否弹窗-2:否,1:是
     * @param isPopup 是否弹窗-2:否,1:是
     */
    public void setIsPopup(String isPopup) {
        this.isPopup = isPopup == null ? null : isPopup.trim();
    }

    /**
     * 版本号
     * @return version_number 版本号
     */
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * 版本号
     * @param versionNumber 版本号
     */
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber == null ? null : versionNumber.trim();
    }

    /**
     * 更新下载地址
     * @return download_url 更新下载地址
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * 更新下载地址
     * @param downloadUrl 更新下载地址
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    /**
     * 强制更新版本
     * @return is_forced_update 强制更新版本
     */
    public String getIsForcedUpdate() {
        return isForcedUpdate;
    }

    /**
     * 强制更新版本
     * @param isForcedUpdate 强制更新版本
     */
    public void setIsForcedUpdate(String isForcedUpdate) {
        this.isForcedUpdate = isForcedUpdate == null ? null : isForcedUpdate.trim();
    }

    /**
     * 不是强制更新版本
     * @return version_update 不是强制更新版本
     */
    public String getVersionUpdate() {
        return versionUpdate;
    }

    /**
     * 不是强制更新版本
     * @param versionUpdate 不是强制更新版本
     */
    public void setVersionUpdate(String versionUpdate) {
        this.versionUpdate = versionUpdate == null ? null : versionUpdate.trim();
    }

    /**
     * 更新标题
     * @return change_title 更新标题
     */
    public String getChangeTitle() {
        return changeTitle;
    }

    /**
     * 更新标题
     * @param changeTitle 更新标题
     */
    public void setChangeTitle(String changeTitle) {
        this.changeTitle = changeTitle == null ? null : changeTitle.trim();
    }

    /**
     * 更新描述
     * @return change_copy 更新描述
     */
    public String getChangeCopy() {
        return changeCopy;
    }

    /**
     * 更新描述
     * @param changeCopy 更新描述
     */
    public void setChangeCopy(String changeCopy) {
        this.changeCopy = changeCopy == null ? null : changeCopy.trim();
    }

    /**
     * 开始更新时间
     * @return begin_time 开始更新时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 开始更新时间
     * @param beginTime 开始更新时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 结束更新时间
     * @return end_time 结束更新时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 结束更新时间
     * @param endTime 结束更新时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 状态: 1 有效  2关闭 
     * @return state 状态: 1 有效  2关闭 
     */
    public Integer getState() {
        return state;
    }

    /**
     * 状态: 1 有效  2关闭 
     * @param state 状态: 1 有效  2关闭 
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人
     * @return create_man 创建人
     */
    public String getCreateMan() {
        return createMan;
    }

    /**
     * 创建人
     * @param createMan 创建人
     */
    public void setCreateMan(String createMan) {
        this.createMan = createMan == null ? null : createMan.trim();
    }

    /**
     * 修改时间
     * @return modify_time 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改时间
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 修改人
     * @return modify_man 修改人
     */
    public String getModifyMan() {
        return modifyMan;
    }

    /**
     * 修改人
     * @param modifyMan 修改人
     */
    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan == null ? null : modifyMan.trim();
    }

    /**
     * 备注：渠道
     * @return remark 备注：渠道
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注：渠道
     * @param remark 备注：渠道
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "ScAppVersionVO{" +
                "appVersionId='" + appVersionId + '\'' +
                ", appName=" + appName +
                ", appType='" + appType + '\'' +
                ", isPopup='" + isPopup + '\'' +
                ", versionNumber='" + versionNumber + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", isForcedUpdate='" + isForcedUpdate + '\'' +
                ", versionUpdate='" + versionUpdate + '\'' +
                ", changeTitle='" + changeTitle + '\'' +
                ", changeCopy='" + changeCopy + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", state=" + state +
                ", createTime=" + createTime +
                ", createMan='" + createMan + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifyMan='" + modifyMan + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}