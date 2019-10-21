package com.xiaoniu.call.video.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`video_classification`")
public class VideoClassification {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 类别编号
     */
    @Column(name = "`category_number`")
    private String categoryNumber;

    /**
     * 类别名称
     */
    @Column(name = "`category_name`")
    private String categoryName;

    /**
     * 类别图标
     */
    @Column(name = "`category_icon`")
    private String categoryIcon;

    /**
     * 权重
     */
    @Column(name = "`weight`")
    private Integer weight;

    /**
     * 状态(0-无效、1-有效)
     */
    @Column(name = "`status`")
    private Boolean status;

    /**
     * 操作人
     */
    @Column(name = "`operator`")
    private String operator;

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
     * 获取类别编号
     *
     * @return category_number - 类别编号
     */
    public String getCategoryNumber() {
        return categoryNumber;
    }

    /**
     * 设置类别编号
     *
     * @param categoryNumber 类别编号
     */
    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    /**
     * 获取类别名称
     *
     * @return category_name - 类别名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置类别名称
     *
     * @param categoryName 类别名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 获取类别图标
     *
     * @return category_icon - 类别图标
     */
    public String getCategoryIcon() {
        return categoryIcon;
    }

    /**
     * 设置类别图标
     *
     * @param categoryIcon 类别图标
     */
    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    /**
     * 获取权重
     *
     * @return weight - 权重
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 设置权重
     *
     * @param weight 权重
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 获取状态(0-无效、1-有效)
     *
     * @return status - 状态(0-无效、1-有效)
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态(0-无效、1-有效)
     *
     * @param status 状态(0-无效、1-有效)
     */
    public void setStatus(Boolean status) {
        this.status = status;
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