package com.xiaoniu.call.video.api.vo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 视频分页请求参数
 *
 * @author liuyinkai
 * @date 2019-08-14
 */
@Setter
@Getter
@ToString
public class TeachingVideoPageVO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = -3024591026244637818L;


    /**
     * 视频编号
     */
    private String videoNumber;

    /**
     * app名称： 1-最来电，2-动态壁纸，3-爱来电，4-铃声多多
     */
    @NotNull(message = "app名称不能为空")
    private Integer appName;

}
