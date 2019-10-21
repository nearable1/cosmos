package com.xiaoniu.call.video.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class VideoTagWebDTO {
    /**
     * 主键编号
     */
    private Integer id;

    /**
     * 标签编号
     */
    private String tagNumber;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 权重
     */
    private Integer weights;

    /**
     * 状态(0-无效、1-有效)
     */
    private Integer status;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
}