package com.xiaoniu.call.video.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 视频分类
 *
 * @author liuyinkai
 * @date 2019-07-25 14:23
 */
@Getter
@Setter
@ToString
public class AudioClassificationDTO {
    /**
     * 类别编号
     */
    private String categoryNumber;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 类别图标
     */
    private String categoryIcon;

    /**
     * 类别背景
     */
    private String backImg;

    /**
     * 分类歌曲预览信息
     */
    private List <AudioDTO> audioInfo;
}