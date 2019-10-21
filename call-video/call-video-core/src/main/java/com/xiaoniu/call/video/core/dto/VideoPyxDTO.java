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
public class VideoPyxDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 封面
     */
    private String videoCover;

    /**
     * 视频地址
     */
    private String videoAddress;

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
     * 时长
     */
    private String duration;

    /**
     * 宽
     */
    private Integer width;
    /**
     * 高
     */
    private Integer height;

}
