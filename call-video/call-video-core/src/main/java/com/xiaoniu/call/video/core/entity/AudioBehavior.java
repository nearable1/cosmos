package com.xiaoniu.call.video.core.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "audio_behavior")
public class AudioBehavior {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 音频编号
     */
    @Column(name = "audio_number")
    private String audioNumber;

    /**
     * 音频标题
     */
    private String title;

    /**
     * 操作类型(1-收藏数、2-转发数、3-播放数、4-设为铃声、5-取消收藏)
     */
    private Integer type;

    /**
     * 类别编号
     */
    @Column(name = "category_number")
    private String categoryNumber;

    /**
     * 音频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     */
    @Column(name = "audio_source")
    private Integer audioSource;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
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
     * 获取音频编号
     *
     * @return audio_number - 音频编号
     */
    public String getAudioNumber() {
        return audioNumber;
    }

    /**
     * 设置音频编号
     *
     * @param audioNumber 音频编号
     */
    public void setAudioNumber(String audioNumber) {
        this.audioNumber = audioNumber;
    }

    /**
     * 获取音频标题
     *
     * @return title - 音频标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置音频标题
     *
     * @param title 音频标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取操作类型(1-收藏数、2-转发数、3-播放数、4-设为铃声、5-取消收藏)
     *
     * @return type - 操作类型(1-收藏数、2-转发数、3-播放数、4-设为铃声、5-取消收藏)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置操作类型(1-收藏数、2-转发数、3-播放数、4-设为铃声、5-取消收藏)
     *
     * @param type 操作类型(1-收藏数、2-转发数、3-播放数、4-设为铃声、5-取消收藏)
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取音频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     *
     * @return audio_source - 音频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     */
    public Integer getAudioSource() {
        return audioSource;
    }

    /**
     * 设置音频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     *
     * @param audioSource 音频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     */
    public void setAudioSource(Integer audioSource) {
        this.audioSource = audioSource;
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