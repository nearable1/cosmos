package com.xiaoniu.call.video.api.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :LiuYinkai
 * @date :2019-08-09 12:48.
 */
@Data
public class AudioOnlineOfflineBO implements Serializable {

    /**
     * 上下线状态
     */
    private Boolean status;

    private String audioNumber;
}
