package com.xiaoniu.call.video.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 老王视频数据
 *
 * @author wuwen
 * @date 2019-07-06 14:52
 */
@Getter
@Setter
@ToString
public class LaoWangVideo implements Serializable {


    private static final long serialVersionUID = -9041392638766733427L;

    @Id
    private String id;

    /**
     * 视频编号
     */
    private String videoNumber;

    /**
     * 播放数
     */
    private Integer views;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;
}
