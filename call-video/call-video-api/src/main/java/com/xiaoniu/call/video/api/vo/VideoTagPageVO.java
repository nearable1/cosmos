package com.xiaoniu.call.video.api.vo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 视频标签分页请求参数
 *
 * @author wuwen
 * @date 2019-07-15 16:40
 */
@Setter
@Getter
@ToString
public class VideoTagPageVO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 视频编号
     */
    private String tagNumber;

    /**
     * 标题
     */
    private String tagName;

}
