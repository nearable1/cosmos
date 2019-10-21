package com.xiaoniu.walking.web.core.model.auto;

import java.util.Date;
import javax.persistence.*;

@Table(name = "bottom_icon")
public class BottomIcon {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 一级icon名称
     */
    @Column(name = "pre_icon_name")
    private String preIconName;

    /**
     * 二级icon名称
     */
    @Column(name = "icon_name")
    private String iconName;

    /**
     * app名称：1-爱步行
     */
    @Column(name = "app_name")
    private Integer appName;

    /**
     * app版本
     */
    @Column(name = "app_version")
    private String appVersion;

    /**
     * 1-有效，2-无效
     */
    private Integer state;

    /**
     * 点击前icon图片
     */
    @Column(name = "pre_icon_img")
    private String preIconImg;

    /**
     * 点击后icon图片
     */
    @Column(name = "aft_icon_img")
    private String aftIconImg;

    /**
     * 跳转链接
     */
    @Column(name = "link_url")
    private String linkUrl;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 创建人
     */
    @Column(name = "create_man")
    private String createMan;

    /**
     * 修改人
     */
    @Column(name = "modify_man")
    private String modifyMan;

    /**
     * 备注
     */
    private String remark;

    /**
     * icon位置
     */
    private Integer position;

    /**
     * 作用范围：1-大于，2-大于等于，3-等于，4-小于，5-小于等于，6-不等于
     */
    @Column(name = "range_version")
    private Integer rangeVersion;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取一级icon名称
     *
     * @return pre_icon_name - 一级icon名称
     */
    public String getPreIconName() {
        return preIconName;
    }

    /**
     * 设置一级icon名称
     *
     * @param preIconName 一级icon名称
     */
    public void setPreIconName(String preIconName) {
        this.preIconName = preIconName;
    }

    /**
     * 获取二级icon名称
     *
     * @return icon_name - 二级icon名称
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * 设置二级icon名称
     *
     * @param iconName 二级icon名称
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    /**
     * 获取app名称：1-爱步行
     *
     * @return app_name - app名称：1-爱步行
     */
    public Integer getAppName() {
        return appName;
    }

    /**
     * 设置app名称：1-爱步行
     *
     * @param appName app名称：1-爱步行
     */
    public void setAppName(Integer appName) {
        this.appName = appName;
    }

    /**
     * 获取app版本
     *
     * @return app_version - app版本
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置app版本
     *
     * @param appVersion app版本
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取1-有效，2-无效
     *
     * @return state - 1-有效，2-无效
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置1-有效，2-无效
     *
     * @param state 1-有效，2-无效
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取点击前icon图片
     *
     * @return pre_icon_img - 点击前icon图片
     */
    public String getPreIconImg() {
        return preIconImg;
    }

    /**
     * 设置点击前icon图片
     *
     * @param preIconImg 点击前icon图片
     */
    public void setPreIconImg(String preIconImg) {
        this.preIconImg = preIconImg;
    }

    /**
     * 获取点击后icon图片
     *
     * @return aft_icon_img - 点击后icon图片
     */
    public String getAftIconImg() {
        return aftIconImg;
    }

    /**
     * 设置点击后icon图片
     *
     * @param aftIconImg 点击后icon图片
     */
    public void setAftIconImg(String aftIconImg) {
        this.aftIconImg = aftIconImg;
    }

    /**
     * 获取跳转链接
     *
     * @return link_url - 跳转链接
     */
    public String getLinkUrl() {
        return linkUrl;
    }

    /**
     * 设置跳转链接
     *
     * @param linkUrl 跳转链接
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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
     * 获取icon位置
     *
     * @return position - icon位置
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * 设置icon位置
     *
     * @param position icon位置
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * 获取作用范围：1-大于，2-大于等于，3-等于，4-小于，5-小于等于，6-不等于
     *
     * @return range_version - 作用范围：1-大于，2-大于等于，3-等于，4-小于，5-小于等于，6-不等于
     */
    public Integer getRangeVersion() {
        return rangeVersion;
    }

    /**
     * 设置作用范围：1-大于，2-大于等于，3-等于，4-小于，5-小于等于，6-不等于
     *
     * @param rangeVersion 作用范围：1-大于，2-大于等于，3-等于，4-小于，5-小于等于，6-不等于
     */
    public void setRangeVersion(Integer rangeVersion) {
        this.rangeVersion = rangeVersion;
    }
}