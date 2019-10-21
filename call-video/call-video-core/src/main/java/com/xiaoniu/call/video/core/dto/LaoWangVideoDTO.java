package com.xiaoniu.call.video.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 老王视频信息
 *
 * @author wuwen
 * @date 2019-07-06 14:12
 */
@Getter
@Setter
@ToString
public class LaoWangVideoDTO {

    /**
     * 视频编号
     */
    private String videoId;

    /**
     * 标题
     */
    private String title;

    /**
     * 视频封面
     */
    private String coverImage;

    /**
     * 上传类型
     */
    private String uploadType;

    /**
     * 视屏文件地址
     */
    private String url;

    /**
     * 视频时长
     */
    private String duration;

    /**
     * 观看次数
     */
    private Integer watchedTimes;
}
