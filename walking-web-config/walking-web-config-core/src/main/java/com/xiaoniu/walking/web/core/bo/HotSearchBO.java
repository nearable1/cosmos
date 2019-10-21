package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;

/**
 * 热词参数
 *
 * @author lihoujing
 * @date 2019/7/8 21:28
 */
@Data
public class HotSearchBO extends DefaultPageParameter implements Serializable {
    private static final long serialVersionUID = -2209877979851432563L;

    /**
     * 热词关键字
     */
    private String keyWord;

    /**
     * APP名称
     */
    private Integer appName;

    /**
     * 查询入参时间数组
     */
    private String[] times;


    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;
}
