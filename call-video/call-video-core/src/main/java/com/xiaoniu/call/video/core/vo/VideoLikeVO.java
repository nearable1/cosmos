package com.xiaoniu.call.video.core.vo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 视频实体
 *
 * @author wuwen
 * @date 2019-07-02 17:42
 */
@Getter
@Setter
@ToString
public class VideoLikeVO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = -3781514684452283825L;

    /**
     * 设备号
     */
    private String deviceId;

    private Boolean webp;
}
