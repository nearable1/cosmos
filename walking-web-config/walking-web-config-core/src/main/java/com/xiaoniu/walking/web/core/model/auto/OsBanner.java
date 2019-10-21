package com.xiaoniu.walking.web.core.model.auto;

import java.util.Date;
import javax.persistence.*;

@Table(name = "os_banner")
public class OsBanner {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "banner_id")
    private Integer bannerId;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * banner位置
     */
    @Column(name = "banner_position")
    private String bannerPosition;

    /**
     * 点击banner跳转地址
     */
    @Column(name = "forward_url")
    private String forwardUrl;

    /**
     * 排序序号：升序
     */
    private Integer sort;

    /**
     * 状态：1-开启，2-关闭
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
    private String remark;

    /**
     * app名称：1-来这记，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    @Column(name = "app_name")
    private Integer appName;

    /**
     * 获取主键编号
     *
     * @return banner_id - 主键编号
     */
    public Integer getBannerId() {
        return bannerId;
    }

    /**
     * 设置主键编号
     *
     * @param bannerId 主键编号
     */
    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取图片地址
     *
     * @return image_url - 图片地址
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片地址
     *
     * @param imageUrl 图片地址
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取banner位置
     *
     * @return banner_position - banner位置
     */
    public String getBannerPosition() {
        return bannerPosition;
    }

    /**
     * 设置banner位置
     *
     * @param bannerPosition banner位置
     */
    public void setBannerPosition(String bannerPosition) {
        this.bannerPosition = bannerPosition;
    }

    /**
     * 获取点击banner跳转地址
     *
     * @return forward_url - 点击banner跳转地址
     */
    public String getForwardUrl() {
        return forwardUrl;
    }

    /**
     * 设置点击banner跳转地址
     *
     * @param forwardUrl 点击banner跳转地址
     */
    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    /**
     * 获取排序序号：升序
     *
     * @return sort - 排序序号：升序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序序号：升序
     *
     * @param sort 排序序号：升序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取状态：1-开启，2-关闭
     *
     * @return state - 状态：1-开启，2-关闭
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态：1-开启，2-关闭
     *
     * @param state 状态：1-开启，2-关闭
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
     * 获取app名称：1-来这记，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     *
     * @return app_name - app名称：1-来这记，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    public Integer getAppName() {
        return appName;
    }

    /**
     * 设置app名称：1-来这记，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     *
     * @param appName app名称：1-来这记，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    public void setAppName(Integer appName) {
        this.appName = appName;
    }
}