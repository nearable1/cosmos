package com.xiaoniu.call.video.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`video_behavior`")
public class VideoBehavior {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 视频编号
     */
    @Column(name = "`video_number`")
    private String videoNumber;

    /**
     * 视频标题
     */
    @Column(name = "`title`")
    private String title;

    /**
     * 视频类型(1-首页视频、2-探索视频、3-小视频)
     */
    @Column(name = "`video_type`")
    private Integer videoType;

    /**
     * 操作类型(1-收藏数、2-转发数、3-播放数、4-设为闹钟数、5-设为来电秀数、6-取消收藏)
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 标签列表
     */
    @Column(name = "`tags`")
    private String tags;

    /**
     * 类别编号
     */
    @Column(name = "`category_number`")
    private String categoryNumber;

    /**
     * 视频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     */
    @Column(name = "`video_source`")
    private Integer videoSource;

    /**
     * 权重
     */
    @Column(name = "`weight`")
    private Integer weight;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

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
     * 获取视频编号
     *
     * @return video_number - 视频编号
     */
    public String getVideoNumber() {
        return videoNumber;
    }

    /**
     * 设置视频编号
     *
     * @param videoNumber 视频编号
     */
    public void setVideoNumber(String videoNumber) {
        this.videoNumber = videoNumber;
    }

    /**
     * 获取视频标题
     *
     * @return title - 视频标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置视频标题
     *
     * @param title 视频标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取视频类型(1-首页视频、2-探索视频、3-小视频)
     *
     * @return video_type - 视频类型(1-首页视频、2-探索视频、3-小视频)
     */
    public Integer getVideoType() {
        return videoType;
    }

    /**
     * 设置视频类型(1-首页视频、2-探索视频、3-小视频)
     *
     * @param videoType 视频类型(1-首页视频、2-探索视频、3-小视频)
     */
    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    /**
     * 获取操作类型(1-收藏数、2-转发数、3-播放数、4-设为闹钟数、5-设为来电秀数)
     *
     * @return type - 操作类型(1-收藏数、2-转发数、3-播放数、4-设为闹钟数、5-设为来电秀数)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置操作类型(1-收藏数、2-转发数、3-播放数、4-设为闹钟数、5-设为来电秀数)
     *
     * @param type 操作类型(1-收藏数、2-转发数、3-播放数、4-设为闹钟数、5-设为来电秀数)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取标签列表
     *
     * @return tags - 标签列表
     */
    public String getTags() {
        return tags;
    }

    /**
     * 设置标签列表
     *
     * @param tags 标签列表
     */
    public void setTags(String tags) {
        this.tags = tags;
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
     * 获取视频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     *
     * @return video_source - 视频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     */
    public Integer getVideoSource() {
        return videoSource;
    }

    /**
     * 设置视频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     *
     * @param videoSource 视频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     */
    public void setVideoSource(Integer videoSource) {
        this.videoSource = videoSource;
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