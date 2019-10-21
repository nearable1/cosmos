package com.xiaoniu.call.video.api.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author :LiuYinkai
 * @date :2019-08-09 17:24.
 */
@Data
public class DeleteRedisKeyBO implements Serializable {

    /**
     * 键
     */
    @NotNull(message = "键不为空")
    private String key;
}
