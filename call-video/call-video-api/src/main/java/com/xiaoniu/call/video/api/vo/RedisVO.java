package com.xiaoniu.call.video.api.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author :LiuYinkai
 * @date :2019-08-07
 */
@Setter
@Getter
@ToString
public class RedisVO implements Serializable {

    /**
     * 键
     */
    private String key ;

    /**
     * 值
     */
    private String value;

    /**
     * 时间
     */
    private String time;
}
