package com.xiaoniu.walking.web.api.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * @author lihoujing
 * @date 2019/5/6 14:17
 */
@Data
public class SysDictVO implements Serializable {

    private static final long serialVersionUID = 112234234233234534L;
    /**
     * 字典值
     */
    private String value;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;


}
