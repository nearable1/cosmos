package com.xiaoniu.walking.web.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 热搜关键词返回对象
 *
 * @author lihoujing
 * @date 2019/7/9 13:36
 */
@Data
public class HotSearchKeywordVO implements Serializable {

    private static final long serialVersionUID = 3332264521535546827L;


    /**
     * 唯一标识
     */
    private String id;

    /**
     * 热搜关键词
     */
    private String keyWord;

    /**
     * 排序值
     */
    private Integer orders;
}
