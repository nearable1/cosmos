package com.xiaoniu.walking.web.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 视频分类
 *
 * @author wuwen
 * @date 2019-07-02 17:10
 */
@Getter
@Setter
@ToString
public class VideoClassificationDTO {

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
}
