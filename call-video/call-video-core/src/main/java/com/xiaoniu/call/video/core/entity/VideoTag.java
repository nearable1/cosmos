package com.xiaoniu.call.video.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`video_tag`")
public class VideoTag {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 标签编号
     */
    @Column(name = "`tag_number`")
    private String tagNumber;

    /**
     * 标签名称
     */
    @Column(name = "`tag_name`")
    private String tagName;

    /**
     * 权重
     */
    @Column(name = "`weights`")
    private Integer weights;

    /**
     * 状态(0-无效、1-有效)
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 修改时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
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
     * 获取标签编号
     *
     * @return tag_number - 标签编号
     */
    public String getTagNumber() {
        return tagNumber;
    }

    /**
     * 设置标签编号
     *
     * @param tagNumber 标签编号
     */
    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    /**
     * 获取标签名称
     *
     * @return tag_name - 标签名称
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * 设置标签名称
     *
     * @param tagName 标签名称
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * 获取权重
     *
     * @return weights - 权重
     */
    public Integer getWeights() {
        return weights;
    }

    /**
     * 设置权重
     *
     * @param weights 权重
     */
    public void setWeights(Integer weights) {
        this.weights = weights;
    }

    /**
     * 获取状态(0-无效、1-有效)
     *
     * @return status - 状态(0-无效、1-有效)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态(0-无效、1-有效)
     *
     * @param status 状态(0-无效、1-有效)
     */
    public void setStatus(Integer status) {
        this.status = status;
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