package com.xiaoniu.walking.web.core.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * APP版本管理
 *
 * @author liuyinkai
 * @date 2019年6月25日11:21:20
 */
@Data
public class QueryAppVersionBO implements Serializable {

    /**
     * app名称
     */
    private Integer appName;

    /**
     * app类型
     */
    private Integer appType;

    /**
     * qudao
     */
    private String channel;





}