package com.xiaoniu.call.video.api.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author :LiuYinkai
 * @date :2019-08-09 17:24.
 */
@Data
public class AddRedisKeyBO implements Serializable {
    private static final long serialVersionUID = -7101326444858210396L;

    /**
     * 键
     */
    @NotNull(message = "键不为空")
    private String key;

    /**
     * 值
     */
    @NotNull(message = "值不为空")
    private String value;

    /**
     * 数据类型
     */
    @NotNull(message = "数据类型不为空")
    private String redisType;

    /**
     * 时间
     */
    private Integer time;

    /**
     * 域  hash
     */
    private String field;

    /**
     * 分   zset
     */
    private Integer score;
}
