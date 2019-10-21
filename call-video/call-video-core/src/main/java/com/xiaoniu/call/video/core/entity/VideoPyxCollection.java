package com.xiaoniu.call.video.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`video_pyx_collection`")
public class VideoPyxCollection {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 设备编号
     */
    @Column(name = "`device_id`")
    private String deviceId;

    /**
     * 视频编号
     */
    @Column(name = "`video_number`")
    private String videoNumber;

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
    public Long getId() {
        return id;
    }

    /**
     * 设置主键编号
     *
     * @param id 主键编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取设备编号
     *
     * @return device_id - 设备编号
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 设置设备编号
     *
     * @param deviceId 设备编号
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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