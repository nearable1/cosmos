package com.xiaoniu.call.video.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class VideoClassificationWebDTO {
    /**
     * 主键编号
     */
    private Integer id;

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
     * 权重
     */
    private Integer weight;

    /**
     * 状态(0-无效、1-有效)
     */
    private Boolean status;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
}