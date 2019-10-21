package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;

/**
 * APP版本管理
 *
 * @author liuyinkai
 * @date 2019年6月24日11:21:20
 */
@Data
public class AppVersionBO extends DefaultPageParameter implements Serializable {

    /**
     * app名称
     */
    private String appName;

    /**
     * app类型
     */
    private String appType;

    /**
     * 状态：1-开启，2-关闭
     */
    private Integer state;

    /**
     * 查询时间数组
     */
    private String[] times;

    /**
     * 创建时间
     */
    private String sTime;

    /**
     * 结束时间
     */
    private String eTime;





}