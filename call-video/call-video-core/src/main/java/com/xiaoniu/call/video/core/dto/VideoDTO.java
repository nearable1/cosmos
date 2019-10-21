package com.xiaoniu.call.video.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 视频返回信息
 *
 * @author wuwen
 * @date 2019-07-02 18:14
 */
@Getter
@Setter
@ToString
public class VideoDTO implements Serializable {

    private static final long serialVersionUID = 1040560877154001945L;

    /**
     * 视频编号
     */
    private String videoNumber;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频类型(1-首页视频、2-探索视频、3-小视频)
     */
    private Integer videoType;

    /**
     * 类别编号
     */
    private String categoryNumber;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 封面
     */
    private String videoCover;

    /**
     * 视频地址
     */
    private String videoAddress;

    /**
     * GIF地址
     */
    private String gifAddress;

    /**
     * 音频地址
     */
    private String audioAddress;

    /**
     * 视频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     */
    private Integer videoSource;

    /**
     * 收藏数
     */
    private Integer collectionNumber;

    /**
     * 转发数
     */
    private Integer forwardNumber;

    /**
     * 播放数
     */
    private Integer view;

    /**
     * 设为闹钟数
     */
    private Integer alarmNumber;

    /**
     * 设为来电秀数
     */
    private Integer callNumber;

    /**
     * 喜欢状态(true-已喜欢、false-未喜欢)
     */
    private Boolean likeState;

    /**
     * 真实收藏数
     */
    private Integer collectionNumberReal;

    /**
     * 真实转发数
     */
    private Integer forwardNumberReal;

    /**
     * 真实播放数
     */
    private Integer viewReal;

    /**
     * 视频库标签列表
     */
    private List<String> videoDbTags;
}
