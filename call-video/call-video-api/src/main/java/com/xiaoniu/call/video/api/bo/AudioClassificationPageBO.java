package com.xiaoniu.call.video.api.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 音频分页请求参数
 *
 * @author liuyinkai
 * @date 2019-07-30
 */
@Setter
@Getter
@ToString
public class AudioClassificationPageBO extends DefaultPageParameter implements Serializable {
    /**
     * app名称
     */
//    @NotNull(message = "app名称不能为空")
    private Integer appName;
}
